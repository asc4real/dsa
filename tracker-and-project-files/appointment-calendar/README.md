# Appointment Calendar — Phase 8 Code Handoff

**Scope:** Java 25 / Spring Boot 4.1.1 modular monolith, PostgreSQL 18, Flyway, Spring Security OAuth2 Resource Server, Keycloak local development, REST API, durable reservation lifecycle, workers, Docker/Kubernetes-ready packaging.

> **Verification notice:** This repository is source code generated in an environment with JDK 21, no Maven, no Docker engine and no access to Maven Central. Java **syntax parsing**, YAML parsing, and static route/migration checks succeeded. Compiling, runtime startup, real Flyway execution, Spring Modulith verification, Testcontainers concurrency tests, security integration, SMTP sending, packaging runtime and load tests have **not** been executed. Do not deploy to production before completing them and addressing the limitations below.

## Implemented source components

- All **67 Phase 5 operations** have explicit REST handler mappings and dispatch to tenant-aware business functions.
- Core booking lifecycle: customer booking holds with 5-minute configurable TTL; transactional three-active-hold quota per membership; confirmation; cancellation; scheduler direct booking; appointment rescheduling/cancel/check-in/start/complete/no-show.
- Database reservation authority: `btree_gist` exclusion constraint over `[start,end)` occupied intervals, including service buffers. All required resources inserted in one transaction.
- Transaction-scoped idempotency table with unique `(organization,principal,operation,key)` and request hashing.
- Conditional appointment updates using `If-Match` strong version ETags.
- Service resource requirements, recurring availability rules, exceptions and time off; advisory bounded slot search with DST offset handling.
- Audit records, transactional outbox, durable notification/reminder tables, PostgreSQL row-lease workers, an optional external SMTP gateway.
- OAuth2 JWT audience + issuer validation, database-owned memberships, strict mutation field allow-lists and object authorization for booking.
- Dockerfile, local Compose for PostgreSQL/Keycloak/Mailpit, Kubernetes template, CI workflow and initial integration tests.

## Prerequisites

- **JDK 25 LTS** and **Maven 3.9+** available in PATH.
- Docker Engine with Compose for local dependencies; Testcontainers integration tests require usable Docker.
- A running OIDC provider with an access token intended for audience **`appointment-calendar-api`**.
- Copy `.env.example` to `.env` and supply local-only passwords; no production secret should be committed.

## Local development

```bash
cp .env.example .env
# Edit .env with LOCAL-only secrets; never commit the file.
docker compose up -d postgres keycloak mailpit
# Wait for Keycloak realm import and Postgres readiness.
export DB_URL='jdbc:postgresql://localhost:5432/appointments'
export DB_USER=appointments
export DB_PASSWORD='<same-local-password-as-.env>'
export OIDC_ISSUER_URI='http://localhost:8081/realms/appointments'
export APP_ALLOWED_ORIGINS='http://localhost:3000' # ONLY local dev; set exact approved HTTPS origins in production
export CURSOR_SIGNING_SECRET=REPLACE_WITH_32_PLUS_BYTE_RANDOM_VALUE
export OIDC_AUDIENCE='appointment-calendar-api'
mvn -B clean verify
mvn spring-boot:run -Dspring-boot.run.profiles=local
curl -f http://localhost:8080/actuator/health
```

The supplied local realm JSON configures a public PKCE client but **does not** create users, public credentials, or an audience mapper. Before calling the API, use Keycloak admin UI to: create local users; configure a Keycloak client scope/audience mapper so access tokens contain `aud=appointment-calendar-api`; and acquire an actual *access* token with the authorization-code-with-PKCE flow. A separate tightly controlled `platform.admin` OAuth scope is required to provision an organization through `POST /api/v1/organizations`. That operation then makes the creating principal the first organization administrator. Additional users need a pre-provisioned local `user_profile` (first valid `/me` call) and an organization membership created by that admin.

For backend machine tests, do not substitute ID tokens for access tokens. Use real issuer/audience/signature verification, or test-only injected validators that reject invalid claims.

## Typical configuration workflow

1. With an organization admin token, create a location and staff members.
2. Create a service with duration, buffer rules and allowed `locationIds`.
3. Create calendar resources (e.g. rooms), then set service resource requirements referencing eligible resources. Staff resources are created with staff members and assigned to the first available location for v1.
4. Create weekly resource availability rules (Monday=1 through Sunday=7; `startsAt` and `endsAt`). Without rules, resources cannot be booked (fail closed).
5. Search availability, create hold with a fresh `Idempotency-Key`, confirm hold with another key.
6. To reschedule/cancel, GET the appointment and send its current `ETag` in `If-Match` plus a new idempotency key.

## Security model

- Only `/actuator/health/**` is publicly readable; business APIs require JWT and appropriate DB tenant membership.
- Tenant IDs in URL do not grant access. Sensitive writes require admin/scheduler permissions; customers can only manage owned bookings.
- Platform administration requires the designated OAuth scope. In production it must also have operational MFA and privileged-access reviews.
- Database runtime permissions are **not** yet separated from Flyway migration permissions in local Compose. Provide a dedicated migration identity and restrict the runtime DB role before production.
- Row-level security remains unimplemented and optional, subject to pool/RLS testing. Tenant-qualified queries and composite foreign keys provide the baseline.
- Gateway WAF/IP-level rate-limits, audit shipping, secret manager, CSP/HTTPS ingress and incident runbooks require environment-specific configuration.

## Quality and limitations — MUST READ

The following are real implementation gaps; they are **not external-secret placeholders**:

1. **No verified build or runtime.** Maven, JDK25 and PostgreSQL could not be executed here.
2. **API contract fidelity incomplete.** Route coverage is 67/67, but some generic administration DTOs may expose extra fields and some filtering/cursor paging options are not yet implemented. Contract diff tests must be added before release.
3. **Resource selection** now uses a bounded backtracking allocator (1,024 search steps, 16 resource units); very large eligibility graphs need load testing, and searches may conservatively report unavailable when the bound is reached.
4. **Expired-hold reclamation** runs both in the worker and opportunistically during resource insertion. A concurrent locked hold can still temporarily cause a conflict; test the race under real PostgreSQL.
5. **Incomplete policies.** Arrival/no-show windows, staff/location-specific delegation, certain enum transitions and notification recipient preference policies need specification and tests.
6. **Outbox and notification processing is at-least-once.** Provider-side delivery deduplication must be addressed for the selected provider.
7. **Keyset pagination is implemented for appointment lists**, but comprehensive filtering/cursors for generic administration listings, input bounds and perfect OpenAPI DTO matching require follow-up work.
8. **Privacy/operational controls** including retention deletion, DLP, admin step-up MFA, audited support access and DB least-privilege are not production-ready.
9. **Real integration tests for idempotency, hold confirmation/expiry races, rescheduling and authorization are incomplete**. A first database 100-way exclusion test is included but has not been executed.
10. The local Keycloak configuration, SMTP, external telemetry endpoint, container registry, Kubernetes secrets, TLS ingress and network policy require actual provider/environment settings. No production credentials have been invented.

## Verification commands

```bash
python3 -m pip install PyYAML  # only for YAML static checker
python3 scripts/validate_openapi.py
python3 scripts/validate_migrations.py
mvn -B clean verify             # MUST succeed with JDK 25 and Docker
mvn -B spring-boot:run
bash scripts/smoke.sh
mvn -B -DskipTests package
# Dockerfile consumes the packaged JAR:
docker build -t appointment-calendar:local .
```

Read `docs/phase8-verification.json` and `docs/phase8-acceptance-matrix.md` before interpreting any file as production-ready. Prior Phase 5 and Phase 7 artifacts are included in `openapi/` and `docs/` as the reference contracts.
