# PHASE 7 — Repository Architecture & Implementation Manifest

**Project:** `appointment-calendar`  
**Base package:** `com.acme.appointmentcalendar`  
**Architecture:** one deployable Spring Boot modular monolith; PostgreSQL authoritative; OAuth2/OIDC; REST.  
**Manifest freeze date:** 2026-10-18  
**Status:** planned blueprint; not compiled, deployed, or production-verified.  
**Source contract:** the Phase 5 OpenAPI 3.1.2 draft, with **48 distinct paths, 67 operations and 64 reusable schemas**.  
**Scope:** 432 planned repository files; **16 named application modules**; includes source, migrations, tests, deployment, documentation and release controls.

## Reading rules and status model

- `NOT_STARTED` — the actual repository file is not generated.
- `GENERATED` — draft material exists in this handoff; content is **not** yet proven compilable/executable.
- `REVISED` — draft has been modified after review; verification pending.
- `VERIFIED` — validated with the file-appropriate real check (compiler/test/linter/migration/rehearsal), evidence recorded.
- Only **3** manifest/contract artifacts are marked `GENERATED`. Existing OpenAPI draft was supplied; the other two are this Phase 7 manifest and JSON tracker. These are **artifacts**, not a compiled repository.
- The source OpenAPI filename stays `openapi/appointment-calendar.yaml`, but it requires drift fixes as implementation tests identify them. Do not rewrite behavior without updating the API contract and decision records.
- New files are added only through manifest updates; never silently omit a promised file. Splits, merges, removals must be justified in an ADR or manifest change note.

## Scope decisions and reconciliations from Phases 0–6

1. **Spring Boot 4.1.1 + Java 25 LTS + Maven**, subject to normal artifact/BOM compatibility validation when code is generated.
2. **Spring Modulith 2.1.1** uses top-level feature packages for module discovery and named contracts for cross-module use; no cycles allowed.
3. **`reservation` is its own small module.** `availability` reads occupancy through `ReservationReadPort`, while `booking` invokes availability and reservation commands. This avoids `booking ↔ availability` coupling and keeps PostgreSQL exclusion constraints centralized.
4. **`resource` owns staff, location and generic calendar resources.** `availability` owns staff time off and schedule exceptions. HTTP paths don't determine package ownership.
5. **`security` owns membership-based tenant checks; `booking` owns appointment-specific object authorization.** `security` never imports booking internals; shared actor context avoids circular dependencies. Audit HTTP querying lives in `platform` so `audit` stays a pure write/read service without importing security.
6. **`outbox` provides transactional event-write and lease/dispatch infrastructure.** Booking writes outbox and audit in the same transaction; workers perform provider calls later. `notification` can consume events via ports without changing booking state.
7. **`booking` never depends on `integration`; `notification` exports `EmailGateway`; `integration.email` implements it.** A provider failure cannot hold the booking transaction open.
8. **No public reservation, outbox, audit-mutation or token-issuance REST endpoints.** Internal entities are not exposed as writable client DTOs.
9. **Strong ETags, bounded keyset pagination and scoped idempotency keys** are required for applicable Phase 5 commands.
10. **Group capacity >1, payment processing, live external calendar synchronization and public anonymous booking are outside v1.** Placeholder ports do not mean a feature is implemented.
11. **PostgreSQL migrations are strictly ordered.** Reservation migration is applied after appointment and hold tables exist; `btree_gist` is installed through a controlled migration.
12. **Separate runtime and migration DB credentials** are required. No Hibernate `update` or `create-drop` in production.
13. **Tenant RLS remains an explicitly gated choice** until connection pool, DB role, context reset and integration behavior pass real PostgreSQL tests; it is not assumed proven.
14. **Templates, fixtures and local identity users never run automatically in production**. Real credentials/secrets are never committed.

## Full repository tree

```text
appointment-calendar/
├── .dockerignore
├── .editorconfig
├── .env.example
├── .gitattributes
├── .gitignore
├── CHANGELOG.md
├── compose.yaml
├── Dockerfile
├── LICENSE
├── Makefile
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── SECURITY.md
├── .github/
│   ├── CODEOWNERS
│   ├── dependabot.yml
│   ├── pull_request_template.md
│   └── workflows/
│       ├── ci.yml
│       ├── release.yml
│       └── security.yml
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── deploy/
│   ├── k8s/
│   │   ├── README.md
│   │   ├── base/
│   │   │   ├── configmap.yaml
│   │   │   ├── deployment.yaml
│   │   │   ├── external-secret.example.yaml
│   │   │   ├── hpa.yaml
│   │   │   ├── ingress.yaml
│   │   │   ├── kustomization.yaml
│   │   │   ├── network-policy.yaml
│   │   │   ├── pdb.yaml
│   │   │   ├── service-account.yaml
│   │   │   └── service.yaml
│   │   └── overlays/
│   │       ├── local/
│   │       │   └── kustomization.yaml
│   │       ├── production/
│   │       │   └── kustomization.yaml
│   │       └── staging/
│   │           └── kustomization.yaml
│   └── keycloak/
│       ├── README.md
│       └── realm-local.json
├── docs/
│   ├── api.md
│   ├── architecture-review.md
│   ├── architecture.md
│   ├── authorization-matrix.md
│   ├── compliance-readiness.md
│   ├── concurrency.md
│   ├── database-design.md
│   ├── disaster-recovery.md
│   ├── domain-model.md
│   ├── implementation-manifest.json
│   ├── incident-response.md
│   ├── known-limitations.md
│   ├── module-boundaries.md
│   ├── operations.md
│   ├── performance.md
│   ├── privacy.md
│   ├── repository-manifest.md
│   ├── requirements-traceability.md
│   ├── runbook.md
│   ├── security.md
│   ├── tenant-isolation.md
│   ├── testing.md
│   ├── threat-model.md
│   └── adr/
│       ├── 0001-modular-monolith.md
│       ├── 0002-shared-schema-tenancy.md
│       ├── 0003-postgresql-authority.md
│       ├── 0004-exclusion-constraint.md
│       ├── 0005-reservation-module.md
│       ├── 0006-oidc-resource-server.md
│       ├── 0007-authorization-policies.md
│       ├── 0008-time-model.md
│       ├── 0009-hold-expiry.md
│       ├── 0010-idempotency.md
│       ├── 0011-outbox.md
│       ├── 0012-reminder-worker.md
│       ├── 0013-no-redis-kafka.md
│       ├── 0014-api-etags.md
│       ├── 0015-tenant-rls-evaluation.md
│       └── 0016-operational-slo.md
├── load-tests/
│   ├── README.md
│   └── k6/
│       ├── baseline.js
│       ├── config.js
│       ├── hot-slot.js
│       ├── smoke.js
│       ├── spike.js
│       └── stress.js
├── openapi/
│   ├── appointment-calendar.yaml
│   ├── README.md
│   └── examples/
│       ├── availability-search-request.json
│       ├── confirm-hold-response.json
│       ├── create-booking-hold-request.json
│       ├── problem-slot-conflict.json
│       └── reschedule-request.json
├── scripts/
│   ├── backup-restore-rehearsal.md
│   ├── bootstrap-local.ps1
│   ├── bootstrap-local.sh
│   ├── check-migration-safety.sh
│   ├── get-local-token.sh
│   ├── seed-local.sql
│   ├── validate-manifest.py
│   ├── validate-openapi.sh
│   └── wait-for-postgres.sh
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── acme/
    │   │           └── appointmentcalendar/
    │   │               ├── AppointmentCalendarApplication.java
    │   │               ├── audit/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── AuditController.java
    │   │               │   │   └── dto/
    │   │               │   │       └── AuditEventResponse.java
    │   │               │   ├── application/
    │   │               │   │   └── AuditQueryService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── AuditEventCommand.java
    │   │               │   │   ├── AuditReader.java
    │   │               │   │   ├── AuditWriter.java
    │   │               │   │   └── package-info.java
    │   │               │   ├── contract_extra/
    │   │               │   │   └── AuditEventView.java
    │   │               │   ├── domain/
    │   │               │   │   └── AuditEvent.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── AuditEventJpaEntity.java
    │   │               │           └── AuditJpaRepository.java
    │   │               ├── availability/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── AvailabilityExceptionController.java
    │   │               │   │   ├── AvailabilityRuleController.java
    │   │               │   │   ├── AvailabilitySearchController.java
    │   │               │   │   ├── StaffTimeOffController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── AvailabilityExceptionDto.java
    │   │               │   │       ├── AvailabilityRuleDto.java
    │   │               │   │       ├── AvailabilitySearchRequest.java
    │   │               │   │       ├── AvailabilitySearchResponse.java
    │   │               │   │       └── TimeOffDto.java
    │   │               │   ├── application/
    │   │               │   │   ├── AvailabilityAdministrationService.java
    │   │               │   │   ├── AvailabilityIntervalEngine.java
    │   │               │   │   ├── AvailabilitySearchService.java
    │   │               │   │   ├── DaylightSavingPolicy.java
    │   │               │   │   └── SlotCandidateGenerator.java
    │   │               │   ├── contract/
    │   │               │   │   ├── AvailabilityPolicy.java
    │   │               │   │   ├── CandidateResourceAllocator.java
    │   │               │   │   └── package-info.java
    │   │               │   ├── domain/
    │   │               │   │   ├── AvailabilityException.java
    │   │               │   │   ├── AvailabilityRule.java
    │   │               │   │   ├── AvailableSlot.java
    │   │               │   │   ├── LocalAvailabilityWindow.java
    │   │               │   │   └── TimeOff.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── AvailabilityExceptionJpaEntity.java
    │   │               │           ├── AvailabilityExceptionJpaRepository.java
    │   │               │           ├── AvailabilityRuleJpaEntity.java
    │   │               │           ├── AvailabilityRuleJpaRepository.java
    │   │               │           ├── TimeOffJpaEntity.java
    │   │               │           └── TimeOffJpaRepository.java
    │   │               ├── booking/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── AppointmentController.java
    │   │               │   │   ├── AppointmentHistoryController.java
    │   │               │   │   ├── BookingHoldController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── AppointmentHistoryResponse.java
    │   │               │   │       ├── AppointmentResponse.java
    │   │               │   │       ├── BookingHoldResponse.java
    │   │               │   │       ├── CancelAppointmentRequest.java
    │   │               │   │       ├── CreateBookingHoldRequest.java
    │   │               │   │       ├── CreateDirectBookingRequest.java
    │   │               │   │       ├── NoShowRequest.java
    │   │               │   │       └── RescheduleAppointmentRequest.java
    │   │               │   ├── application/
    │   │               │   │   ├── AppointmentCommandService.java
    │   │               │   │   ├── AppointmentConflictPolicy.java
    │   │               │   │   ├── AppointmentQueryService.java
    │   │               │   │   ├── AppointmentStateMachine.java
    │   │               │   │   ├── BookingAuthorizationPolicy.java
    │   │               │   │   ├── BookingHoldApplicationService.java
    │   │               │   │   ├── BookingHoldExpiryService.java
    │   │               │   │   └── BookingResourceSelectionService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── AppointmentSummaryReader.java
    │   │               │   │   ├── BookingMaintenance.java
    │   │               │   │   └── package-info.java
    │   │               │   ├── domain/
    │   │               │   │   ├── Appointment.java
    │   │               │   │   ├── AppointmentBookingPolicy.java
    │   │               │   │   ├── AppointmentParticipant.java
    │   │               │   │   ├── AppointmentStatus.java
    │   │               │   │   ├── AppointmentStatusHistory.java
    │   │               │   │   ├── BookingHold.java
    │   │               │   │   └── BookingHoldStatus.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── AppointmentHistoryJpaEntity.java
    │   │               │           ├── AppointmentHistoryJpaRepository.java
    │   │               │           ├── AppointmentJpaEntity.java
    │   │               │           ├── AppointmentJpaRepository.java
    │   │               │           ├── AppointmentParticipantJpaEntity.java
    │   │               │           ├── BookingHoldJpaEntity.java
    │   │               │           ├── BookingHoldJpaRepository.java
    │   │               │           └── BookingHoldLockRepository.java
    │   │               ├── catalog/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── ServiceCatalogController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── ResourceRequirementDto.java
    │   │               │   │       └── ServiceDto.java
    │   │               │   ├── application/
    │   │               │   │   ├── ServiceCatalogService.java
    │   │               │   │   └── ServiceRequirementService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── package-info.java
    │   │               │   │   ├── ServiceCatalogReader.java
    │   │               │   │   └── ServiceSnapshot.java
    │   │               │   ├── domain/
    │   │               │   │   ├── BookableService.java
    │   │               │   │   ├── ServiceResourceEligibility.java
    │   │               │   │   └── ServiceResourceRequirement.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── ServiceJpaEntity.java
    │   │               │           ├── ServiceJpaRepository.java
    │   │               │           ├── ServiceRequirementJpaEntity.java
    │   │               │           └── ServiceRequirementJpaRepository.java
    │   │               ├── idempotency/
    │   │               │   ├── package-info.java
    │   │               │   ├── application/
    │   │               │   │   ├── IdempotencyService.java
    │   │               │   │   └── RequestFingerprintService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── IdempotencyCoordinator.java
    │   │               │   │   ├── IdempotencyScope.java
    │   │               │   │   ├── IdempotentResult.java
    │   │               │   │   └── package-info.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── IdempotencyJpaRepository.java
    │   │               │           └── IdempotencyRecordJpaEntity.java
    │   │               ├── identity/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── MeController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── MembershipSummaryResponse.java
    │   │               │   │       └── MeResponse.java
    │   │               │   ├── application/
    │   │               │   │   └── IdentityProfileService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── package-info.java
    │   │               │   │   ├── UserIdentityReader.java
    │   │               │   │   └── UserReference.java
    │   │               │   ├── domain/
    │   │               │   │   └── UserProfile.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── UserProfileJpaEntity.java
    │   │               │           └── UserProfileJpaRepository.java
    │   │               ├── integration/
    │   │               │   ├── package-info.java
    │   │               │   ├── calendar/
    │   │               │   │   ├── ExternalCalendarPort.java
    │   │               │   │   └── IcsExportService.java
    │   │               │   ├── email/
    │   │               │   │   ├── SmtpConfiguration.java
    │   │               │   │   └── SmtpEmailGateway.java
    │   │               │   └── http/
    │   │               │       └── ExternalHttpClientFactory.java
    │   │               ├── notification/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── AppointmentNotificationController.java
    │   │               │   │   ├── ReminderDefinitionController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── NotificationResponse.java
    │   │               │   │       └── ReminderDefinitionDto.java
    │   │               │   ├── application/
    │   │               │   │   ├── DeliveryRetryPolicy.java
    │   │               │   │   ├── NotificationDeliveryService.java
    │   │               │   │   ├── NotificationService.java
    │   │               │   │   ├── NotificationTemplateRenderer.java
    │   │               │   │   ├── ReminderDefinitionService.java
    │   │               │   │   └── ReminderInstanceService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── EmailGateway.java
    │   │               │   │   ├── NotificationEventHandler.java
    │   │               │   │   ├── package-info.java
    │   │               │   │   └── ReminderMaintenance.java
    │   │               │   ├── domain/
    │   │               │   │   ├── Notification.java
    │   │               │   │   ├── NotificationDelivery.java
    │   │               │   │   ├── ReminderDefinition.java
    │   │               │   │   └── ReminderInstance.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── NotificationDeliveryJpaEntity.java
    │   │               │           ├── NotificationDeliveryJpaRepository.java
    │   │               │           ├── NotificationJpaEntity.java
    │   │               │           ├── NotificationJpaRepository.java
    │   │               │           ├── ReminderDefinitionJpaEntity.java
    │   │               │           ├── ReminderInstanceJpaEntity.java
    │   │               │           └── ReminderJdbcClaimRepository.java
    │   │               ├── operations/
    │   │               │   ├── package-info.java
    │   │               │   ├── config/
    │   │               │   │   ├── BookingProperties.java
    │   │               │   │   └── WorkerProperties.java
    │   │               │   ├── observability/
    │   │               │   │   ├── BookingMetrics.java
    │   │               │   │   ├── OutboxMetrics.java
    │   │               │   │   ├── ReadinessHealthIndicator.java
    │   │               │   │   └── SchedulingMetrics.java
    │   │               │   └── worker/
    │   │               │       ├── BookingHoldExpiryJob.java
    │   │               │       ├── NotificationRetryJob.java
    │   │               │       ├── OutboxDispatcherJob.java
    │   │               │       └── ReminderDispatchJob.java
    │   │               ├── organization/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── MembershipController.java
    │   │               │   │   ├── MeMembershipController.java
    │   │               │   │   ├── OrganizationController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── MembershipRequest.java
    │   │               │   │       ├── MembershipResponse.java
    │   │               │   │       ├── OrganizationRequest.java
    │   │               │   │       ├── OrganizationResponse.java
    │   │               │   │       └── WeeklyHoursDto.java
    │   │               │   ├── application/
    │   │               │   │   ├── MembershipPolicy.java
    │   │               │   │   ├── MembershipService.java
    │   │               │   │   ├── OrganizationCommandService.java
    │   │               │   │   └── OrganizationQueryService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── MembershipReader.java
    │   │               │   │   ├── OrganizationHoursReader.java
    │   │               │   │   ├── OrganizationProvisioningPort.java
    │   │               │   │   ├── OrganizationReader.java
    │   │               │   │   ├── OrganizationRef.java
    │   │               │   │   └── package-info.java
    │   │               │   ├── domain/
    │   │               │   │   ├── BusinessHours.java
    │   │               │   │   ├── Organization.java
    │   │               │   │   └── OrganizationMembership.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── BusinessHoursJpaEntity.java
    │   │               │           ├── MembershipJpaEntity.java
    │   │               │           ├── MembershipJpaRepository.java
    │   │               │           ├── OrganizationJpaEntity.java
    │   │               │           └── OrganizationJpaRepository.java
    │   │               ├── outbox/
    │   │               │   ├── package-info.java
    │   │               │   ├── application/
    │   │               │   │   ├── OutboxClaimService.java
    │   │               │   │   ├── OutboxDispatchService.java
    │   │               │   │   └── OutboxRetryPolicy.java
    │   │               │   ├── contract/
    │   │               │   │   ├── OutboxMessage.java
    │   │               │   │   ├── OutboxMessageHandler.java
    │   │               │   │   ├── OutboxWriter.java
    │   │               │   │   └── package-info.java
    │   │               │   ├── domain/
    │   │               │   │   └── OutboxEvent.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── OutboxJdbcClaimRepository.java
    │   │               │           ├── OutboxJpaEntity.java
    │   │               │           └── OutboxJpaRepository.java
    │   │               ├── platform/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── PlatformOrganizationController.java
    │   │               │   │   └── TenantAuditController.java
    │   │               │   └── application/
    │   │               │       └── PlatformProvisioningService.java
    │   │               ├── reservation/
    │   │               │   ├── package-info.java
    │   │               │   ├── contract/
    │   │               │   │   ├── package-info.java
    │   │               │   │   ├── ReservationCommandPort.java
    │   │               │   │   ├── ReservationConflictException.java
    │   │               │   │   ├── ReservationReadPort.java
    │   │               │   │   └── ResourceOccupancy.java
    │   │               │   ├── domain/
    │   │               │   │   ├── ReservationInterval.java
    │   │               │   │   └── ResourceReservation.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── ReservationConstraintTranslator.java
    │   │               │           ├── ReservationNativeSqlRepository.java
    │   │               │           ├── ReservationPersistenceAdapter.java
    │   │               │           └── ResourceReservationJpaEntity.java
    │   │               ├── resource/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── CalendarResourceController.java
    │   │               │   │   ├── LocationController.java
    │   │               │   │   ├── StaffController.java
    │   │               │   │   └── dto/
    │   │               │   │       ├── LocationDto.java
    │   │               │   │       ├── ResourceDto.java
    │   │               │   │       └── StaffDto.java
    │   │               │   ├── application/
    │   │               │   │   ├── LocationManagementService.java
    │   │               │   │   ├── ResourceEligibilityPolicy.java
    │   │               │   │   ├── ResourceManagementService.java
    │   │               │   │   └── StaffManagementService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── LocationReader.java
    │   │               │   │   ├── package-info.java
    │   │               │   │   ├── ResourceReader.java
    │   │               │   │   └── StaffReader.java
    │   │               │   ├── domain/
    │   │               │   │   ├── CalendarResource.java
    │   │               │   │   ├── Location.java
    │   │               │   │   ├── ResourceType.java
    │   │               │   │   └── StaffMember.java
    │   │               │   └── infrastructure/
    │   │               │       └── persistence/
    │   │               │           ├── CalendarResourceJpaEntity.java
    │   │               │           ├── LocationHoursJpaEntity.java
    │   │               │           ├── LocationJpaEntity.java
    │   │               │           ├── LocationJpaRepository.java
    │   │               │           ├── ResourceJpaRepository.java
    │   │               │           ├── StaffJpaEntity.java
    │   │               │           └── StaffJpaRepository.java
    │   │               ├── security/
    │   │               │   ├── package-info.java
    │   │               │   ├── api/
    │   │               │   │   ├── ApiAccessDeniedHandler.java
    │   │               │   │   ├── ApiAuthorizationEntryPoint.java
    │   │               │   │   ├── OidcJwtConfiguration.java
    │   │               │   │   └── SecurityConfiguration.java
    │   │               │   ├── application/
    │   │               │   │   ├── AuthenticatedActorResolver.java
    │   │               │   │   ├── SecurityAuditService.java
    │   │               │   │   └── TenantAuthorizationService.java
    │   │               │   ├── contract/
    │   │               │   │   ├── package-info.java
    │   │               │   │   ├── Permission.java
    │   │               │   │   └── TenantAccessPolicy.java
    │   │               │   └── infrastructure/
    │   │               │       ├── JwtClaimValidator.java
    │   │               │       └── TrustedProxyConfiguration.java
    │   │               └── shared/
    │   │                   ├── package-info.java
    │   │                   ├── contract/
    │   │                   │   ├── AuthenticatedActor.java
    │   │                   │   ├── ClockProvider.java
    │   │                   │   ├── CurrentActor.java
    │   │                   │   ├── DomainEventEnvelope.java
    │   │                   │   ├── package-info.java
    │   │                   │   └── PageCursor.java
    │   │                   ├── error/
    │   │                   │   ├── ApiExceptionHandler.java
    │   │                   │   ├── BusinessErrorCode.java
    │   │                   │   ├── DomainException.java
    │   │                   │   └── ProblemResponseFactory.java
    │   │                   └── web/
    │   │                       ├── CorrelationIdFilter.java
    │   │                       └── PaginationSupport.java
    │   └── resources/
    │       ├── application-local.yml
    │       ├── application-prod.yml
    │       ├── application.yml
    │       ├── db/
    │       │   └── migration/
    │       │       ├── V001__extensions_and_global_identity.sql
    │       │       ├── V002__organizations_and_memberships.sql
    │       │       ├── V003__locations_staff_resources.sql
    │       │       ├── V004__services_and_requirements.sql
    │       │       ├── V005__availability_and_time_off.sql
    │       │       ├── V006__holds_and_appointments.sql
    │       │       ├── V007__active_resource_reservations.sql
    │       │       ├── V008__idempotency_and_audit.sql
    │       │       ├── V009__outbox.sql
    │       │       ├── V010__notifications_and_reminders.sql
    │       │       ├── V011__query_indexes.sql
    │       │       └── V012__tenant_integrity_constraints.sql
    │       └── templates/
    │           └── mail/
    │               ├── appointment-cancelled.html
    │               ├── appointment-confirmed.html
    │               └── appointment-reminder.html
    └── test/
        ├── java/
        │   └── com/
        │       └── acme/
        │           └── appointmentcalendar/
        │               ├── api/
        │               │   ├── OpenApiOperationCoverageTest.java
        │               │   ├── PaginationAndFilteringTest.java
        │               │   ├── PreconditionAndEtagTest.java
        │               │   └── Rfc9457ContractTest.java
        │               ├── architecture/
        │               │   ├── ControllerLayerArchitectureTest.java
        │               │   ├── ModulithBoundaryVerificationTest.java
        │               │   └── NoDomainSpringWebDependencyTest.java
        │               ├── availability/
        │               │   ├── AvailabilityIntervalEngineTest.java
        │               │   ├── AvailabilityQueryBoundariesTest.java
        │               │   ├── AvailabilitySearchIntegrationTest.java
        │               │   └── DaylightSavingPolicyTest.java
        │               ├── booking/
        │               │   ├── AdjacentIntervalsIT.java
        │               │   ├── AppointmentStateMachineTest.java
        │               │   ├── BookingApiContractIT.java
        │               │   ├── BookingHoldExpiryRaceIT.java
        │               │   ├── BookingHoldQuotaConcurrencyIT.java
        │               │   ├── BookingHoldStateMachineTest.java
        │               │   ├── MultiResourceAtomicBookingIT.java
        │               │   ├── NoDoubleBooking100ThreadsIT.java
        │               │   └── RescheduleAtomicityIT.java
        │               ├── catalog/
        │               │   └── ServiceRequirementValidationTest.java
        │               ├── idempotency/
        │               │   ├── ConcurrentIdempotencyIT.java
        │               │   ├── IdempotencyConflictIT.java
        │               │   └── IdempotencyReplayAfterCommitIT.java
        │               ├── notification/
        │               │   ├── NotificationFailureRetryIT.java
        │               │   └── ReminderDuplicateDeliveryIT.java
        │               ├── organization/
        │               │   └── MembershipPolicyTest.java
        │               ├── outbox/
        │               │   ├── OutboxClaimConcurrencyIT.java
        │               │   └── OutboxCrashRecoveryIT.java
        │               ├── persistence/
        │               │   ├── CompositeTenantFkIT.java
        │               │   ├── IndexQueryPlanTest.java
        │               │   ├── PostgresMigrationIT.java
        │               │   └── ReservationConstraintIT.java
        │               ├── security/
        │               │   ├── AdminFunctionAuthorizationTest.java
        │               │   ├── AuthenticationEndpointTest.java
        │               │   ├── BookingObjectAuthorizationTest.java
        │               │   ├── JwtValidatorIntegrationTest.java
        │               │   ├── MassAssignmentValidationTest.java
        │               │   ├── PrivilegeRevocationTest.java
        │               │   ├── RedactedErrorResponseTest.java
        │               │   └── TenantIsolationIntegrationTest.java
        │               └── support/
        │                   ├── ContendedBookingRunner.java
        │                   ├── PostgresIntegrationTestBase.java
        │                   ├── TenantFixtureFactory.java
        │                   └── TestIdentityFactory.java
        └── resources/
            ├── application-test.yml
            └── testdata/
                ├── appointment.json
                └── booking-hold.json
```

## Module dependency policy

**Cross-module code may import only a module's stable exported `contract` interfaces/types** (Spring Modulith `@NamedInterface` or explicitly exported root API), never its `application`, `domain`, or `infrastructure` internals. Controllers are HTTP surfaces, not intermodule contracts.

| Module | Allowed outgoing module dependencies | Purpose |
|---|---|---|
| `shared` | None | Transport-neutral actor/clock/error primitives (strictly small). |
| `identity` | `shared` | OIDC subject to application profile references. |
| `organization` | `identity`, `audit`, `shared` | Tenants, membership, opening hours and policies. |
| `security` | `identity`, `organization`, `audit`, `shared` | Validates security context and tenant membership/permissions. |
| `resource` | `organization`, `audit`, `shared` | Staff, locations and reservable resources. |
| `catalog` | `organization`, `resource`, `audit`, `shared` | Services and eligible resource group requirements. |
| `reservation` | `shared` | Authoritative DB occupancy and exclusion-constraint operations. |
| `availability` | `organization`, `resource`, `catalog`, `reservation`, `audit`, `shared` | Rules, exceptions, time off and advisory slot computation. |
| `idempotency` | `shared` | Atomic mutation deduplication and replay. |
| `audit` | `shared` | Append-only business/security events and restricted query. |
| `outbox` | `shared` | Durable event writes and at-least-once worker infrastructure. |
| `booking` | `organization`, `security`, `resource`, `catalog`, `reservation`, `availability`, `idempotency`, `audit`, `outbox`, `shared` | Transactional holds, appointments, lifecycle and resource commands. |
| `notification` | `outbox`, `shared` | Templates, reminders, delivery attempts and deduplication. |
| `integration` | `notification`, `shared` | SMTP provider and future calendar ports/adapters. |
| `operations` | `booking`, `notification`, `outbox`, `shared` | Safe worker schedules, health and business observability. |
| `platform` | `organization`, `security`, `audit`, `shared` | Restricted organization provisioning and platform reporting. |

**Must never occur:** `booking → integration`, `availability → booking`, `reservation → booking`, `security → booking`, `domain → web controller`, `controller → JPA repository`. Database foreign keys may reference tables owned by a different Java module; those are explicit schema contracts maintained in migration order, not Java imports.

Spring Modulith verified module structure requires no cycles and prevents dependencies into other modules' internal packages. The explicit dependency table must be enforced by `ModulithBoundaryVerificationTest.java` and ArchUnit. The `contract` packages need `package-info.java` named-interface annotations to be visible to other modules.

## Phase 5 API coverage — all published operations

| Method | URL | Operation ID | Planned controller |
|---|---|---|---|
| `GET` | `/api/v1/me` | `getCurrentUser` | `identity/api/MeController.java` |
| `GET` | `/api/v1/me/memberships` | `listMyMemberships` | `organization/api/MeMembershipController.java` |
| `POST` | `/api/v1/organizations` | `createOrganization` | `platform/api/PlatformOrganizationController.java` |
| `GET` | `/api/v1/organizations/{orgId}` | `getOrganization` | `organization/api/OrganizationController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}` | `updateOrganization` | `organization/api/OrganizationController.java` |
| `GET` | `/api/v1/organizations/{orgId}/business-hours` | `getOrganizationHours` | `organization/api/OrganizationController.java` |
| `PUT` | `/api/v1/organizations/{orgId}/business-hours` | `replaceOrganizationHours` | `organization/api/OrganizationController.java` |
| `GET` | `/api/v1/organizations/{orgId}/memberships` | `listMemberships` | `organization/api/MembershipController.java` |
| `POST` | `/api/v1/organizations/{orgId}/memberships` | `createMembership` | `organization/api/MembershipController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/memberships/{membershipId}` | `updateMembership` | `organization/api/MembershipController.java` |
| `POST` | `/api/v1/organizations/{orgId}/memberships/{membershipId}/revoke` | `revokeMembership` | `organization/api/MembershipController.java` |
| `GET` | `/api/v1/organizations/{orgId}/locations` | `listLocations` | `resource/api/LocationController.java` |
| `POST` | `/api/v1/organizations/{orgId}/locations` | `createLocation` | `resource/api/LocationController.java` |
| `GET` | `/api/v1/organizations/{orgId}/locations/{locationId}` | `getLocation` | `resource/api/LocationController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/locations/{locationId}` | `updateLocation` | `resource/api/LocationController.java` |
| `POST` | `/api/v1/organizations/{orgId}/locations/{locationId}/deactivate` | `deactivateLocation` | `resource/api/LocationController.java` |
| `GET` | `/api/v1/organizations/{orgId}/staff` | `listStaff` | `resource/api/StaffController.java` |
| `POST` | `/api/v1/organizations/{orgId}/staff` | `createStaffMember` | `resource/api/StaffController.java` |
| `GET` | `/api/v1/organizations/{orgId}/staff/{staffId}` | `getStaffMember` | `resource/api/StaffController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/staff/{staffId}` | `updateStaffMember` | `resource/api/StaffController.java` |
| `POST` | `/api/v1/organizations/{orgId}/staff/{staffId}/deactivate` | `deactivateStaffMember` | `resource/api/StaffController.java` |
| `GET` | `/api/v1/organizations/{orgId}/resources` | `listResources` | `resource/api/CalendarResourceController.java` |
| `POST` | `/api/v1/organizations/{orgId}/resources` | `createResource` | `resource/api/CalendarResourceController.java` |
| `GET` | `/api/v1/organizations/{orgId}/resources/{resourceId}` | `getResource` | `resource/api/CalendarResourceController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/resources/{resourceId}` | `updateResource` | `resource/api/CalendarResourceController.java` |
| `POST` | `/api/v1/organizations/{orgId}/resources/{resourceId}/deactivate` | `deactivateResource` | `resource/api/CalendarResourceController.java` |
| `GET` | `/api/v1/organizations/{orgId}/services` | `listServices` | `catalog/api/ServiceCatalogController.java` |
| `POST` | `/api/v1/organizations/{orgId}/services` | `createService` | `catalog/api/ServiceCatalogController.java` |
| `GET` | `/api/v1/organizations/{orgId}/services/{serviceId}` | `getService` | `catalog/api/ServiceCatalogController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/services/{serviceId}` | `updateService` | `catalog/api/ServiceCatalogController.java` |
| `POST` | `/api/v1/organizations/{orgId}/services/{serviceId}/deactivate` | `deactivateService` | `catalog/api/ServiceCatalogController.java` |
| `GET` | `/api/v1/organizations/{orgId}/locations/{locationId}/opening-hours` | `getLocationOpeningHours` | `resource/api/LocationController.java` |
| `PUT` | `/api/v1/organizations/{orgId}/locations/{locationId}/opening-hours` | `replaceLocationOpeningHours` | `resource/api/LocationController.java` |
| `GET` | `/api/v1/organizations/{orgId}/services/{serviceId}/resource-requirements` | `getServiceResourceRequirements` | `catalog/api/ServiceCatalogController.java` |
| `PUT` | `/api/v1/organizations/{orgId}/services/{serviceId}/resource-requirements` | `replaceServiceResourceRequirements` | `catalog/api/ServiceCatalogController.java` |
| `GET` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules` | `listAvailabilityRules` | `availability/api/AvailabilityRuleController.java` |
| `POST` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules` | `createAvailabilityRule` | `availability/api/AvailabilityRuleController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules/{ruleId}` | `updateAvailabilityRule` | `availability/api/AvailabilityRuleController.java` |
| `DELETE` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules/{ruleId}` | `deleteAvailabilityRule` | `availability/api/AvailabilityRuleController.java` |
| `GET` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-exceptions` | `listAvailabilityExceptions` | `availability/api/AvailabilityExceptionController.java` |
| `POST` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-exceptions` | `createAvailabilityException` | `availability/api/AvailabilityExceptionController.java` |
| `DELETE` | `/api/v1/organizations/{orgId}/resources/{resourceId}/availability-exceptions/{exceptionId}` | `deleteAvailabilityException` | `availability/api/AvailabilityExceptionController.java` |
| `GET` | `/api/v1/organizations/{orgId}/staff/{staffId}/time-off` | `listStaffTimeOff` | `availability/api/StaffTimeOffController.java` |
| `POST` | `/api/v1/organizations/{orgId}/staff/{staffId}/time-off` | `createStaffTimeOff` | `availability/api/StaffTimeOffController.java` |
| `POST` | `/api/v1/organizations/{orgId}/staff/{staffId}/time-off/{timeOffId}/cancel` | `cancelStaffTimeOff` | `availability/api/StaffTimeOffController.java` |
| `POST` | `/api/v1/organizations/{orgId}/availability/search` | `searchAvailability` | `availability/api/AvailabilitySearchController.java` |
| `POST` | `/api/v1/organizations/{orgId}/booking-holds` | `createBookingHold` | `booking/api/BookingHoldController.java` |
| `GET` | `/api/v1/organizations/{orgId}/booking-holds/{holdId}` | `getBookingHold` | `booking/api/BookingHoldController.java` |
| `POST` | `/api/v1/organizations/{orgId}/booking-holds/{holdId}/confirm` | `confirmBookingHold` | `booking/api/BookingHoldController.java` |
| `POST` | `/api/v1/organizations/{orgId}/booking-holds/{holdId}/cancel` | `cancelBookingHold` | `booking/api/BookingHoldController.java` |
| `GET` | `/api/v1/organizations/{orgId}/appointments` | `listAppointments` | `booking/api/AppointmentController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments` | `createDirectBooking` | `booking/api/AppointmentController.java` |
| `GET` | `/api/v1/organizations/{orgId}/me/appointments` | `listMyAppointments` | `booking/api/AppointmentController.java` |
| `GET` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}` | `getAppointment` | `booking/api/AppointmentController.java` |
| `GET` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/history` | `getAppointmentHistory` | `booking/api/AppointmentHistoryController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/reschedule` | `rescheduleAppointment` | `booking/api/AppointmentController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/cancel` | `cancelAppointment` | `booking/api/AppointmentController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/check-in` | `checkinAppointment` | `booking/api/AppointmentController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/start` | `startAppointment` | `booking/api/AppointmentController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/complete` | `completeAppointment` | `booking/api/AppointmentController.java` |
| `POST` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/no-show` | `noshowAppointment` | `booking/api/AppointmentController.java` |
| `GET` | `/api/v1/organizations/{orgId}/reminder-definitions` | `listReminderDefinitions` | `notification/api/ReminderDefinitionController.java` |
| `POST` | `/api/v1/organizations/{orgId}/reminder-definitions` | `createReminderDefinition` | `notification/api/ReminderDefinitionController.java` |
| `PATCH` | `/api/v1/organizations/{orgId}/reminder-definitions/{reminderId}` | `updateReminderDefinition` | `notification/api/ReminderDefinitionController.java` |
| `GET` | `/api/v1/organizations/{orgId}/appointments/{appointmentId}/notifications` | `listAppointmentNotifications` | `notification/api/AppointmentNotificationController.java` |
| `GET` | `/api/v1/organizations/{orgId}/audit-events` | `listAuditEvents` | `platform/api/TenantAuditController.java` |
| `GET` | `/api/v1/platform/organizations` | `listPlatformOrganizations` | `platform/api/PlatformOrganizationController.java` |

> Public URI shape belongs to Phase 5 OpenAPI, **not** to Java module ownership. Phase 8 API coverage tests must report missing handlers, undocumented routes, mismatched requests/response schemas and broken authorization. The drafted operations are inventory, not executable HTTP handlers.

## File-by-file implementation inventory

**Legend**: `P8` = implementation, `P9` = DB migrations, `P10` = tests/performance, `P11` = infrastructure, `P12` = CI, `P13` = observability, `P14` = docs. All entries below contain a path, purpose, implementation phase and current status.

### Root (16 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `pom.xml` | Single Maven build; Boot parent, Modulith BOM, version-managed dependencies and test/verification plugins. | 8 | NOT_STARTED |
| `mvnw` | POSIX Maven wrapper launcher, generated from Maven Wrapper tooling. | 8 | NOT_STARTED |
| `mvnw.cmd` | Windows Maven wrapper launcher for local development. | 8 | NOT_STARTED |
| `.mvn/wrapper/maven-wrapper.properties` | Pinned Maven distribution and checksum/distribution URL for reproducible builds. | 8 | NOT_STARTED |
| `README.md` | First-run, architecture, dependencies, token acquisition, cURL walkthrough and support guide. | 14 | NOT_STARTED |
| `LICENSE` | Repository legal license; must be chosen before distribution; no assumed ownership grant. | 14 | NOT_STARTED |
| `.gitignore` | Ignores build output, IDE files, local secrets and test artifacts. | 8 | NOT_STARTED |
| `.gitattributes` | Consistent line endings/binary classification across Windows and Unix. | 8 | NOT_STARTED |
| `.editorconfig` | Repository-wide whitespace, charset and line-ending conventions. | 8 | NOT_STARTED |
| `.dockerignore` | Prevents secrets, build output, tests and docs from being sent unnecessarily to Docker build context. | 11 | NOT_STARTED |
| `.env.example` | Documented, non-secret development environment-variable names. | 11 | NOT_STARTED |
| `Dockerfile` | Multi-stage OCI build with unprivileged minimal runtime and verifiable base image. | 11 | NOT_STARTED |
| `compose.yaml` | Local PostgreSQL, Keycloak, Mailpit and optional application services. | 11 | NOT_STARTED |
| `Makefile` | Shortcuts for build, test, dependencies and local operations; wrapper-compatible. | 11 | NOT_STARTED |
| `CHANGELOG.md` | Records backward-compatible and breaking API/application changes. | 14 | NOT_STARTED |
| `SECURITY.md` | Vulnerability disclosure channel and supported security policy. | 14 | NOT_STARTED |

### OpenAPI (7 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `openapi/appointment-calendar.yaml` | Canonical Phase 5 OpenAPI 3.1.2 design contract; 48 paths, 67 operations and 64 schemas. | 5 | GENERATED |
| `openapi/README.md` | OpenAPI source-of-truth, review workflow, compatibility/versioning and contract testing conventions. | 5 | NOT_STARTED |
| `openapi/examples/availability-search-request.json` | Example bounded availability search. | 5 | NOT_STARTED |
| `openapi/examples/create-booking-hold-request.json` | Example idempotent hold creation. | 5 | NOT_STARTED |
| `openapi/examples/confirm-hold-response.json` | Example successful confirmation response. | 5 | NOT_STARTED |
| `openapi/examples/reschedule-request.json` | Example ETag and idempotency-based rescheduling request body. | 5 | NOT_STARTED |
| `openapi/examples/problem-slot-conflict.json` | Canonical RFC 9457 booking-conflict example. | 5 | NOT_STARTED |

### Documentation (23 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `docs/repository-manifest.md` | Full file-by-file Phase 7 implementation blueprint, architecture contracts and delivery gates. | 14 | GENERATED |
| `docs/implementation-manifest.json` | Machine-readable file inventory with strict implementation status per file. | 14 | GENERATED |
| `docs/architecture.md` | C4/system-context, deployment, module dataflow and strong/eventual consistency. | 14 | NOT_STARTED |
| `docs/module-boundaries.md` | Allowed public contracts, prohibited dependencies, Spring Modulith rules and ports. | 14 | NOT_STARTED |
| `docs/domain-model.md` | Aggregates, lifecycle, value objects and business invariants. | 14 | NOT_STARTED |
| `docs/database-design.md` | ERD, tenant-safe FKs, reservation exclusion constraints and transaction boundaries. | 14 | NOT_STARTED |
| `docs/api.md` | Endpoint behavior, authentication, ETags, errors, pagination, idempotency and examples. | 14 | NOT_STARTED |
| `docs/security.md` | OIDC, JWT, permission checks, TLS, CORS, CSRF posture and hardening. | 14 | NOT_STARTED |
| `docs/threat-model.md` | STRIDE attack paths, mitigating controls, tests and residual risk. | 14 | NOT_STARTED |
| `docs/authorization-matrix.md` | Actor-by-resource/function/relationship privileges and 401/403/404 behavior. | 14 | NOT_STARTED |
| `docs/tenant-isolation.md` | Tenant lookup discipline, composite FKs and optional RLS evaluation. | 14 | NOT_STARTED |
| `docs/privacy.md` | PII classification, retention, access/export, erasure and log redaction. | 14 | NOT_STARTED |
| `docs/compliance-readiness.md` | Regime-by-regime gap analysis without false compliance assertions. | 14 | NOT_STARTED |
| `docs/concurrency.md` | DB GiST invariant, hold expiry/confirmation races, idempotency race and rescheduling. | 14 | NOT_STARTED |
| `docs/testing.md` | Testing pyramid, Testcontainers strategy, critical tests and CI gates. | 14 | NOT_STARTED |
| `docs/performance.md` | Availability algorithm cost, index workloads, load tests and SLO evidence. | 14 | NOT_STARTED |
| `docs/operations.md` | Production deployment, monitoring, alerting, jobs, feature configuration. | 14 | NOT_STARTED |
| `docs/runbook.md` | Operator responses to DB outage, outbox backlog, IdP failures, stuck holds. | 14 | NOT_STARTED |
| `docs/disaster-recovery.md` | RPO/RTO objectives, backup, PITR, restore rehearsal, key recovery. | 14 | NOT_STARTED |
| `docs/incident-response.md` | Security alert triage, evidence, containment and notification process. | 14 | NOT_STARTED |
| `docs/requirements-traceability.md` | Maps requirements FR/INV/NFR to code, migrations, tests and evidence. | 14 | NOT_STARTED |
| `docs/architecture-review.md` | Phase 16 completed review outcomes, severities and closure evidence. | 14 | NOT_STARTED |
| `docs/known-limitations.md` | Honest unsupported items and deliberate v1 exclusions. | 14 | NOT_STARTED |

### Architecture Decisions (16 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `docs/adr/0001-modular-monolith.md` | Choose modular monolith and boundaries; reasons not to use microservices. | 14 | NOT_STARTED |
| `docs/adr/0002-shared-schema-tenancy.md` | Shared-schema tenancy and tenant-safe integrity. | 14 | NOT_STARTED |
| `docs/adr/0003-postgresql-authority.md` | Database authoritative for reservation correctness. | 14 | NOT_STARTED |
| `docs/adr/0004-exclusion-constraint.md` | GiST exclusion constraint with half-open tstzrange and active-only semantics. | 14 | NOT_STARTED |
| `docs/adr/0005-reservation-module.md` | Extract resource reservation into module to avoid booking ↔ availability cycle. | 14 | NOT_STARTED |
| `docs/adr/0006-oidc-resource-server.md` | External OIDC; no application password storage. | 14 | NOT_STARTED |
| `docs/adr/0007-authorization-policies.md` | RBAC and object/relationship authorization placement. | 14 | NOT_STARTED |
| `docs/adr/0008-time-model.md` | Instant/TIMESTAMPTZ with IANA timezones and DST handling. | 14 | NOT_STARTED |
| `docs/adr/0009-hold-expiry.md` | Transactionally safe hold state transitions, no in-memory timers. | 14 | NOT_STARTED |
| `docs/adr/0010-idempotency.md` | Per-principal/tenant/operation request deduplication and retained result. | 14 | NOT_STARTED |
| `docs/adr/0011-outbox.md` | Transactional outbox and idempotent eventual side effects. | 14 | NOT_STARTED |
| `docs/adr/0012-reminder-worker.md` | DB-backed reminders and multi-worker SKIP LOCKED claims. | 14 | NOT_STARTED |
| `docs/adr/0013-no-redis-kafka.md` | Do not add Redis/Kafka until justified by measurable demand. | 14 | NOT_STARTED |
| `docs/adr/0014-api-etags.md` | Strong ETag/If-Match, immutable DTOs and lifecycle commands. | 14 | NOT_STARTED |
| `docs/adr/0015-tenant-rls-evaluation.md` | RLS adoption gate, transaction-local context and bypass analysis. | 14 | NOT_STARTED |
| `docs/adr/0016-operational-slo.md` | SLOs, RPO/RTO goals, performance budgets and evidence. | 14 | NOT_STARTED |

### Deployment (16 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `deploy/keycloak/realm-local.json` | Dev-only OIDC realm, clients, roles and redirect origins without production secrets. | 11 | NOT_STARTED |
| `deploy/keycloak/README.md` | Local token/client provisioning and realm import steps. | 11 | NOT_STARTED |
| `deploy/k8s/base/kustomization.yaml` | Defines base Kustomize resource set. | 11 | NOT_STARTED |
| `deploy/k8s/base/deployment.yaml` | Stateless application pods, resources, securityContext, probes, termination policy. | 11 | NOT_STARTED |
| `deploy/k8s/base/service.yaml` | ClusterIP HTTP service. | 11 | NOT_STARTED |
| `deploy/k8s/base/service-account.yaml` | Dedicated least-privilege workload identity. | 11 | NOT_STARTED |
| `deploy/k8s/base/configmap.yaml` | Non-secret platform settings. | 11 | NOT_STARTED |
| `deploy/k8s/base/ingress.yaml` | TLS Ingress host/routing template with controlled annotations. | 11 | NOT_STARTED |
| `deploy/k8s/base/network-policy.yaml` | Restrict ingress and egress to required dependencies. | 11 | NOT_STARTED |
| `deploy/k8s/base/hpa.yaml` | Resource-based horizontal scaling with realistic min/max. | 11 | NOT_STARTED |
| `deploy/k8s/base/pdb.yaml` | Disruption budget for multi-replica availability. | 11 | NOT_STARTED |
| `deploy/k8s/base/external-secret.example.yaml` | Provider-neutral illustrative external secret reference, no values. | 11 | NOT_STARTED |
| `deploy/k8s/overlays/local/kustomization.yaml` | Optional local Kubernetes overlay. | 11 | NOT_STARTED |
| `deploy/k8s/overlays/staging/kustomization.yaml` | Staging overlay and staging-safe knobs. | 11 | NOT_STARTED |
| `deploy/k8s/overlays/production/kustomization.yaml` | Production replicas, ingress and policy overlay. | 11 | NOT_STARTED |
| `deploy/k8s/README.md` | Kubernetes apply, validate, rollback, migration strategy and secrets setup. | 11 | NOT_STARTED |

### CI/CD (6 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `.github/workflows/ci.yml` | Compile, format, unit/integration/architecture tests, contract lint and quality checks. | 12 | NOT_STARTED |
| `.github/workflows/security.yml` | Dependency vulnerabilities, secret scan, SBOM and container scan with severity gates. | 12 | NOT_STARTED |
| `.github/workflows/release.yml` | Versioned build, OCI provenance/signing and approval-controlled publication. | 12 | NOT_STARTED |
| `.github/dependabot.yml` | Automated reviewed dependency and GitHub Actions upgrades. | 12 | NOT_STARTED |
| `.github/CODEOWNERS` | Review ownership for security, schema, API and deployments. | 12 | NOT_STARTED |
| `.github/pull_request_template.md` | Checklist for API/DB/security/observability/test changes. | 12 | NOT_STARTED |

### Scripts (9 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `scripts/bootstrap-local.sh` | Dependency bootstrap for Unix developer workstation. | 11 | NOT_STARTED |
| `scripts/bootstrap-local.ps1` | Dependency bootstrap for Windows PowerShell. | 11 | NOT_STARTED |
| `scripts/wait-for-postgres.sh` | Waits for local PostgreSQL readiness with bounded retry. | 11 | NOT_STARTED |
| `scripts/seed-local.sql` | Explicit opt-in development fixtures, never a production Flyway migration. | 11 | NOT_STARTED |
| `scripts/get-local-token.sh` | Documented non-production OAuth token helper, secrets supplied at runtime. | 11 | NOT_STARTED |
| `scripts/validate-openapi.sh` | Lint/check contract syntax, refs, operation IDs and basic policies. | 12 | NOT_STARTED |
| `scripts/validate-manifest.py` | Check duplicate paths, known phases/statuses and manifest consistency. | 12 | NOT_STARTED |
| `scripts/check-migration-safety.sh` | Validate Flyway migration naming/order and prohibit destructive prod defaults. | 12 | NOT_STARTED |
| `scripts/backup-restore-rehearsal.md` | Commands and verification procedures for a controlled restore drill. | 14 | NOT_STARTED |

### Load Tests (7 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `load-tests/README.md` | k6 test strategy, assumptions, credential handling, measurements and use. | 10 | NOT_STARTED |
| `load-tests/k6/config.js` | Environment and performance thresholds for k6 workloads. | 10 | NOT_STARTED |
| `load-tests/k6/smoke.js` | Minimal functional smoke scenario. | 10 | NOT_STARTED |
| `load-tests/k6/baseline.js` | Representative browse/book workload under baseline volume. | 10 | NOT_STARTED |
| `load-tests/k6/hot-slot.js` | Many callers competing for same slot without masking conflict results. | 10 | NOT_STARTED |
| `load-tests/k6/spike.js` | Rapid step-up workload and rate-limit behavior. | 10 | NOT_STARTED |
| `load-tests/k6/stress.js` | Sustained above-target workload, back-pressure and recovery. | 10 | NOT_STARTED |

### Application (1 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `src/main/java/com/acme/appointmentcalendar/AppointmentCalendarApplication.java` | Single Spring Boot launcher and Modulith application root. | 8 | NOT_STARTED |

### shared (13 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for shared; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface exporting only stable cross-module primitives. | 8 | NOT_STARTED |
| `…/contract/AuthenticatedActor.java` | Immutable identity and platform-scoped authority context for use cases. | 8 | NOT_STARTED |
| `…/contract/CurrentActor.java` | Port to resolve verified authenticated actor from request context. | 8 | NOT_STARTED |
| `…/contract/ClockProvider.java` | Injectable UTC clock abstraction for deterministic business tests. | 8 | NOT_STARTED |
| `…/contract/PageCursor.java` | Validated opaque keyset pagination cursor value type. | 8 | NOT_STARTED |
| `…/contract/DomainEventEnvelope.java` | Safe event metadata envelope (ID, type, occurrence, tenant, correlation). | 8 | NOT_STARTED |
| `…/error/DomainException.java` | Typed domain exception base carrying stable error code and safe detail. | 8 | NOT_STARTED |
| `…/error/BusinessErrorCode.java` | Central documented error-code vocabulary, not HTTP-specific. | 8 | NOT_STARTED |
| `…/error/ApiExceptionHandler.java` | RFC 9457 mapping and safe response for framework/domain failures. | 8 | NOT_STARTED |
| `…/error/ProblemResponseFactory.java` | Builds consistent ProblemDetail responses without leaking internals. | 8 | NOT_STARTED |
| `…/web/CorrelationIdFilter.java` | Accepts/generates safe correlation ID and propagates it in responses/logs. | 8 | NOT_STARTED |
| `…/web/PaginationSupport.java` | Bounds page sizes and validates server-side cursors. | 8 | NOT_STARTED |

### identity (11 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for identity; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/MeController.java` | GET /api/v1/me, sanitized application identity view. | 8 | NOT_STARTED |
| `…/api/dto/MeResponse.java` | Current authenticated app profile view. | 8 | NOT_STARTED |
| `…/api/dto/MembershipSummaryResponse.java` | Profile-side membership summary DTO type; membership listing owned by organization. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface allowing other modules to lookup minimal profile identity. | 8 | NOT_STARTED |
| `…/contract/UserIdentityReader.java` | Read-only identity/subject to profile reference port. | 8 | NOT_STARTED |
| `…/contract/UserReference.java` | Minimal user ID/issuer/subject typed response. | 8 | NOT_STARTED |
| `…/application/IdentityProfileService.java` | Maps OIDC issuer+subject to local identity reference. | 8 | NOT_STARTED |
| `…/domain/UserProfile.java` | Identity-linked profile aggregate without passwords. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/UserProfileJpaEntity.java` | ORM mapping for global profile and unique issuer+subject. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/UserProfileJpaRepository.java` | Restricted identity repository keyed by issuer+subject. | 8 | NOT_STARTED |

### organization (27 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for organization; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/OrganizationController.java` | Create/read/update organization and business hours endpoints. | 8 | NOT_STARTED |
| `…/api/MembershipController.java` | List/add/update/revoke tenant memberships. | 8 | NOT_STARTED |
| `…/api/MeMembershipController.java` | GET /api/v1/me/memberships using organization membership records. | 8 | NOT_STARTED |
| `…/api/dto/OrganizationRequest.java` | Administrative creation/update DTO constraints. | 8 | NOT_STARTED |
| `…/api/dto/OrganizationResponse.java` | Public organization output with version. | 8 | NOT_STARTED |
| `…/api/dto/MembershipRequest.java` | Membership assignment/update DTO with role allowlist. | 8 | NOT_STARTED |
| `…/api/dto/MembershipResponse.java` | Membership read projection. | 8 | NOT_STARTED |
| `…/api/dto/WeeklyHoursDto.java` | ISO weekday and local-time window contract. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface for external tenant membership and business-hours read ports. | 8 | NOT_STARTED |
| `…/contract/OrganizationReader.java` | Read tenant scheduling policies, default zone and authorized platform paginated list. | 8 | NOT_STARTED |
| `…/contract/OrganizationProvisioningPort.java` | Restricted organization creation port used by platform provisioning API. | 8 | NOT_STARTED |
| `…/contract/MembershipReader.java` | Check organization-scoped actor roles and active memberships. | 8 | NOT_STARTED |
| `…/contract/OrganizationHoursReader.java` | Read recurring organization opening hours. | 8 | NOT_STARTED |
| `…/contract/OrganizationRef.java` | Minimal tenant identity/timezone record. | 8 | NOT_STARTED |
| `…/application/OrganizationCommandService.java` | Create, update, deactivate org and manage business hours. | 8 | NOT_STARTED |
| `…/application/OrganizationQueryService.java` | Safe organization read model. | 8 | NOT_STARTED |
| `…/application/MembershipService.java` | Membership mutation including last-admin protection and audit. | 8 | NOT_STARTED |
| `…/application/MembershipPolicy.java` | Role grant/revoke rules and membership invariants. | 8 | NOT_STARTED |
| `…/domain/Organization.java` | Tenant aggregate with policies and lifecycle. | 8 | NOT_STARTED |
| `…/domain/OrganizationMembership.java` | Actor/tenant role membership aggregate. | 8 | NOT_STARTED |
| `…/domain/BusinessHours.java` | Recurring weekly organization hours value object. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/OrganizationJpaEntity.java` | Organization JPA persistence mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/MembershipJpaEntity.java` | Membership JPA mapping with tenant constraints. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/OrganizationJpaRepository.java` | Tenant-aware organization access. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/MembershipJpaRepository.java` | Membership and permission query implementation. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/BusinessHoursJpaEntity.java` | ORM mapping of organization hours. | 8 | NOT_STARTED |

### security (13 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for security; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/SecurityConfiguration.java` | Spring Security filter chain, token JWT rules, stateless API security. | 8 | NOT_STARTED |
| `…/api/OidcJwtConfiguration.java` | Trusted issuer/audience/algorithm/JWK cache configuration. | 8 | NOT_STARTED |
| `…/api/ApiAuthorizationEntryPoint.java` | RFC 9457-safe 401 handler. | 8 | NOT_STARTED |
| `…/api/ApiAccessDeniedHandler.java` | RFC 9457-safe 403 handler. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface for permission and tenant access decisions. | 8 | NOT_STARTED |
| `…/contract/TenantAccessPolicy.java` | Checks active membership and scoped authorization. | 8 | NOT_STARTED |
| `…/contract/Permission.java` | Stable permission identifiers used by domain use cases. | 8 | NOT_STARTED |
| `…/application/AuthenticatedActorResolver.java` | Converts verified JWT into stable application actor identity. | 8 | NOT_STARTED |
| `…/application/TenantAuthorizationService.java` | Tenant membership and permissions using OrganizationReader. | 8 | NOT_STARTED |
| `…/application/SecurityAuditService.java` | Security event emission via audit facade where safe. | 8 | NOT_STARTED |
| `…/infrastructure/JwtClaimValidator.java` | Validates issuer, audience, expiration and critical token claims. | 8 | NOT_STARTED |
| `…/infrastructure/TrustedProxyConfiguration.java` | Trusted forwarding/host controls for ingress deployments. | 8 | NOT_STARTED |

### resource (26 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for resource; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/StaffController.java` | All Phase 5 staff create/list/get/update/deactivate endpoints. | 8 | NOT_STARTED |
| `…/api/LocationController.java` | All Phase 5 location and opening-hours endpoints. | 8 | NOT_STARTED |
| `…/api/CalendarResourceController.java` | Generic resource create/list/get/update/deactivate endpoints. | 8 | NOT_STARTED |
| `…/api/dto/StaffDto.java` | Staff create/update/response API records. | 8 | NOT_STARTED |
| `…/api/dto/LocationDto.java` | Location create/update/response API records. | 8 | NOT_STARTED |
| `…/api/dto/ResourceDto.java` | Resource create/update/response API records. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface exporting resource/location/staff reads. | 8 | NOT_STARTED |
| `…/contract/ResourceReader.java` | Read resource eligibility, location and status. | 8 | NOT_STARTED |
| `…/contract/LocationReader.java` | Read location hours, timezone and status. | 8 | NOT_STARTED |
| `…/contract/StaffReader.java` | Resolve staff-to-resource linkage and tenant. | 8 | NOT_STARTED |
| `…/application/StaffManagementService.java` | Staff lifecycle and linked staff resource provisioning. | 8 | NOT_STARTED |
| `…/application/LocationManagementService.java` | Location lifecycle, timezone and hours. | 8 | NOT_STARTED |
| `…/application/ResourceManagementService.java` | Resource lifecycle and safe deactivation. | 8 | NOT_STARTED |
| `…/application/ResourceEligibilityPolicy.java` | Ensures bookable resource belongs to correct tenant/location. | 8 | NOT_STARTED |
| `…/domain/CalendarResource.java` | Reservable resource entity with type/status/location. | 8 | NOT_STARTED |
| `…/domain/Location.java` | Tenant location with business hours and zone. | 8 | NOT_STARTED |
| `…/domain/StaffMember.java` | Staff aggregate optionally linked to app identity. | 8 | NOT_STARTED |
| `…/domain/ResourceType.java` | Resource type enumeration. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/CalendarResourceJpaEntity.java` | Resource JPA mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/LocationJpaEntity.java` | Location JPA mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/StaffJpaEntity.java` | Staff JPA mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ResourceJpaRepository.java` | Tenant-qualified resource queries. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/LocationJpaRepository.java` | Tenant-qualified location queries. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/StaffJpaRepository.java` | Tenant-qualified staff queries. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/LocationHoursJpaEntity.java` | Location recurring hours persistence. | 8 | NOT_STARTED |

### catalog (16 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for catalog; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/ServiceCatalogController.java` | Service list/create/get/update/deactivate and requirements endpoints. | 8 | NOT_STARTED |
| `…/api/dto/ServiceDto.java` | Service request/response records and snapshots. | 8 | NOT_STARTED |
| `…/api/dto/ResourceRequirementDto.java` | Required resource groups and eligible resource DTOs. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface exporting validated service definitions. | 8 | NOT_STARTED |
| `…/contract/ServiceCatalogReader.java` | Read service duration, policies, location eligibility and resource requirement groups. | 8 | NOT_STARTED |
| `…/contract/ServiceSnapshot.java` | Immutable booking-time service policy snapshot. | 8 | NOT_STARTED |
| `…/application/ServiceCatalogService.java` | Service create/update/deactivation and allowed locations. | 8 | NOT_STARTED |
| `…/application/ServiceRequirementService.java` | Atomic replacement and validation of resource requirement groups. | 8 | NOT_STARTED |
| `…/domain/BookableService.java` | Service aggregate with pricing placeholder and booking policies. | 8 | NOT_STARTED |
| `…/domain/ServiceResourceRequirement.java` | Quantity/type requirement grouping. | 8 | NOT_STARTED |
| `…/domain/ServiceResourceEligibility.java` | Allowed concrete resources for a requirement group. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ServiceJpaEntity.java` | Service persistence and version. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ServiceRequirementJpaEntity.java` | Requirement/eligibility persistence. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ServiceJpaRepository.java` | Tenant-scoped service listing and reads. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ServiceRequirementJpaRepository.java` | Requirements/eligibility queries. | 8 | NOT_STARTED |

### reservation (12 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for reservation; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface for resource occupancy reads and transactional reservation commands. | 8 | NOT_STARTED |
| `…/contract/ReservationCommandPort.java` | Insert/convert/release reservation rows atomically under DB constraints. | 8 | NOT_STARTED |
| `…/contract/ReservationReadPort.java` | Load occupied effective intervals efficiently for availability calculation. | 8 | NOT_STARTED |
| `…/contract/ResourceOccupancy.java` | Canonical resource/time interval value object. | 8 | NOT_STARTED |
| `…/contract/ReservationConflictException.java` | Typed DB conflict error surfaced to booking layer. | 8 | NOT_STARTED |
| `…/domain/ResourceReservation.java` | ACTIVE/RELEASED reservation with HOLD/APPOINTMENT owner and buffered range. | 8 | NOT_STARTED |
| `…/domain/ReservationInterval.java` | Half-open Instant range with nonzero duration invariant. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ResourceReservationJpaEntity.java` | Persistence mapping with owner IDs, status, duration and tenant. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ReservationNativeSqlRepository.java` | PostgreSQL-specific range insert/update/read and conflict translation. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ReservationPersistenceAdapter.java` | Implements ReservationCommandPort/ReadPort in one short transaction context. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ReservationConstraintTranslator.java` | Maps SQLSTATE 23P01 to domain slot conflict, without leaking SQL. | 8 | NOT_STARTED |

### availability (29 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for availability; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/AvailabilitySearchController.java` | POST availability/search bounded advisory slot search. | 8 | NOT_STARTED |
| `…/api/AvailabilityRuleController.java` | Resource weekly rule CRUD endpoints. | 8 | NOT_STARTED |
| `…/api/AvailabilityExceptionController.java` | Resource exception endpoints. | 8 | NOT_STARTED |
| `…/api/StaffTimeOffController.java` | Staff time-off listing/create/cancel endpoints. | 8 | NOT_STARTED |
| `…/api/dto/AvailabilitySearchRequest.java` | Bounded date interval, service/location, optional preferred staff and zone. | 8 | NOT_STARTED |
| `…/api/dto/AvailabilitySearchResponse.java` | Available slots, generatedAt and truncated/advisory flags. | 8 | NOT_STARTED |
| `…/api/dto/AvailabilityRuleDto.java` | Weekly day/time/date rule contract. | 8 | NOT_STARTED |
| `…/api/dto/AvailabilityExceptionDto.java` | One-time exception contract. | 8 | NOT_STARTED |
| `…/api/dto/TimeOffDto.java` | Staff time-off contract. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface exposing booking-time availability validation. | 8 | NOT_STARTED |
| `…/contract/AvailabilityPolicy.java` | Validates lead time, horizon, service and resource eligibility. | 8 | NOT_STARTED |
| `…/contract/CandidateResourceAllocator.java` | Determines eligible combinations without exponential unbounded search. | 8 | NOT_STARTED |
| `…/application/AvailabilitySearchService.java` | Batch loads rule/reservation windows and generates slot candidates. | 8 | NOT_STARTED |
| `…/application/AvailabilityAdministrationService.java` | Manages recurring rules, exceptions and time off. | 8 | NOT_STARTED |
| `…/application/AvailabilityIntervalEngine.java` | Merges availability and subtracts occupied intervals and time off. | 8 | NOT_STARTED |
| `…/application/DaylightSavingPolicy.java` | ZoneRules gap/overlap ambiguity behavior. | 8 | NOT_STARTED |
| `…/application/SlotCandidateGenerator.java` | Builds bounded aligned slots with buffers and rules. | 8 | NOT_STARTED |
| `…/domain/AvailabilityRule.java` | Local weekly recurring window + effective dates. | 8 | NOT_STARTED |
| `…/domain/AvailabilityException.java` | Allow/block special availability interval. | 8 | NOT_STARTED |
| `…/domain/TimeOff.java` | Staff unavailable interval with lifecycle. | 8 | NOT_STARTED |
| `…/domain/LocalAvailabilityWindow.java` | Local time window value type. | 8 | NOT_STARTED |
| `…/domain/AvailableSlot.java` | Advisory slot with absolute instant and display offset. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AvailabilityRuleJpaEntity.java` | Recurring rule JPA mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AvailabilityExceptionJpaEntity.java` | Override exception JPA mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/TimeOffJpaEntity.java` | Staff time off mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AvailabilityRuleJpaRepository.java` | Tenant-scoped resource rules. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AvailabilityExceptionJpaRepository.java` | Tenant-scoped exceptions. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/TimeOffJpaRepository.java` | Tenant-scoped staff leave access. | 8 | NOT_STARTED |

### idempotency (9 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for idempotency; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface for mutation deduplication coordinator. | 8 | NOT_STARTED |
| `…/contract/IdempotencyCoordinator.java` | Claims scoped key, compares fingerprint and returns stored result. | 8 | NOT_STARTED |
| `…/contract/IdempotencyScope.java` | Tenant/principal/operation/key scope value object. | 8 | NOT_STARTED |
| `…/contract/IdempotentResult.java` | Stored status/resource response for safe replay. | 8 | NOT_STARTED |
| `…/application/IdempotencyService.java` | Transaction-aware unique claims, in-progress and completion handling. | 8 | NOT_STARTED |
| `…/application/RequestFingerprintService.java` | Canonical method/path/body/precondition hash. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/IdempotencyRecordJpaEntity.java` | Unique scoped key, fingerprint, status, result and expiry. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/IdempotencyJpaRepository.java` | Atomic key acquisition/lookup/cleanup support. | 8 | NOT_STARTED |

### audit (12 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for audit; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface for application audit writes and audit query projections. | 8 | NOT_STARTED |
| `…/contract/AuditWriter.java` | Append critical audit records in caller transaction. | 8 | NOT_STARTED |
| `…/contract/AuditReader.java` | Read bounded audit projections for authorized tenant administrator workflow. | 8 | NOT_STARTED |
| `…/contract/AuditEventCommand.java` | Typed audit event fields with safe minimal metadata. | 8 | NOT_STARTED |
| `…/api/AuditController.java` | GET audit-events, authorized and paginated. | 8 | NOT_STARTED |
| `…/api/dto/AuditEventResponse.java` | Redacted read-only audit view. | 8 | NOT_STARTED |
| `…/application/AuditQueryService.java` | Permission-scoped filters, time boundaries, keyset paging. | 8 | NOT_STARTED |
| `…/contract_extra/AuditEventView.java` | Safe read-only audit projection exported to platform tenant-admin API. | 8 | NOT_STARTED |
| `…/domain/AuditEvent.java` | Append-only business/security fact. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AuditEventJpaEntity.java` | Append-only event mapping and indexed query columns. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AuditJpaRepository.java` | Bounded tenant-scoped audit searches. | 8 | NOT_STARTED |

### outbox (12 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for outbox; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface exporting atomic event writes and delivery registry. | 8 | NOT_STARTED |
| `…/contract/OutboxWriter.java` | Persist event in same transaction as booking mutation. | 8 | NOT_STARTED |
| `…/contract/OutboxMessage.java` | Immutable structured event payload/metadata contract. | 8 | NOT_STARTED |
| `…/contract/OutboxMessageHandler.java` | Handler interface registered by event type for async effects. | 8 | NOT_STARTED |
| `…/application/OutboxClaimService.java` | Batched database claims and lease management. | 8 | NOT_STARTED |
| `…/application/OutboxDispatchService.java` | Dispatch to idempotent handlers with retry/backoff. | 8 | NOT_STARTED |
| `…/application/OutboxRetryPolicy.java` | Classifies permanent/transient processing errors. | 8 | NOT_STARTED |
| `…/domain/OutboxEvent.java` | Durable event publication state and attempts. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/OutboxJpaEntity.java` | Events, statuses, due times, lease token and attempts. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/OutboxJdbcClaimRepository.java` | SKIP LOCKED short transactions with nonblocking leases. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/OutboxJpaRepository.java` | Outbox events and success/failure state persistence. | 8 | NOT_STARTED |

### booking (38 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for booking; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/BookingHoldController.java` | Four hold endpoints: create/get/confirm/cancel. | 8 | NOT_STARTED |
| `…/api/AppointmentController.java` | Direct create, appointment reads/lists, lifecycle command endpoints. | 8 | NOT_STARTED |
| `…/api/AppointmentHistoryController.java` | GET appointment status history. | 8 | NOT_STARTED |
| `…/api/dto/CreateBookingHoldRequest.java` | Self-service hold request with no owner/status override. | 8 | NOT_STARTED |
| `…/api/dto/BookingHoldResponse.java` | Hold state/time/expiry and safe resource summary. | 8 | NOT_STARTED |
| `…/api/dto/CreateDirectBookingRequest.java` | Staff/scheduler delegated booking with authorized customer target. | 8 | NOT_STARTED |
| `…/api/dto/AppointmentResponse.java` | Safe appointment representation and version. | 8 | NOT_STARTED |
| `…/api/dto/RescheduleAppointmentRequest.java` | New time, location, preferred staff and bounded reason. | 8 | NOT_STARTED |
| `…/api/dto/CancelAppointmentRequest.java` | Cancellation reason only. | 8 | NOT_STARTED |
| `…/api/dto/NoShowRequest.java` | No-show reason if applicable. | 8 | NOT_STARTED |
| `…/api/dto/AppointmentHistoryResponse.java` | Append-only lifecycle event projection. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface exposing hold expiration and appointment event references to jobs. | 8 | NOT_STARTED |
| `…/contract/BookingMaintenance.java` | Expire holds in batches with concurrency-safe transitions. | 8 | NOT_STARTED |
| `…/contract/AppointmentSummaryReader.java` | Minimal appointment summary for notification/reminder operations. | 8 | NOT_STARTED |
| `…/application/BookingHoldApplicationService.java` | Self-hold creation/read/confirmation/cancellation; transactional. | 8 | NOT_STARTED |
| `…/application/AppointmentCommandService.java` | Direct book, cancel, reschedule, check-in/start/complete/no-show. | 8 | NOT_STARTED |
| `…/application/AppointmentQueryService.java` | Object-scoped get/list/me/history query handling. | 8 | NOT_STARTED |
| `…/application/BookingAuthorizationPolicy.java` | Owner, staff assignment and delegated booking permissions. | 8 | NOT_STARTED |
| `…/application/BookingHoldExpiryService.java` | Row-lock-safe expiry and opportunistic expired reservation release. | 8 | NOT_STARTED |
| `…/application/BookingResourceSelectionService.java` | Deterministic multi-resource acquisition order and eligibility. | 8 | NOT_STARTED |
| `…/application/AppointmentStateMachine.java` | Explicit status transitions and side effects. | 8 | NOT_STARTED |
| `…/application/AppointmentConflictPolicy.java` | Translate reservation conflict and enforce 409 semantics. | 8 | NOT_STARTED |
| `…/domain/Appointment.java` | Appointment aggregate, snapshots and version. | 8 | NOT_STARTED |
| `…/domain/BookingHold.java` | ACTIVE/CONFIRMED/EXPIRED/CANCELLED aggregate with TTL. | 8 | NOT_STARTED |
| `…/domain/AppointmentParticipant.java` | Participant references and roles. | 8 | NOT_STARTED |
| `…/domain/AppointmentStatusHistory.java` | Append-only transition history. | 8 | NOT_STARTED |
| `…/domain/AppointmentStatus.java` | Appointment lifecycle statuses. | 8 | NOT_STARTED |
| `…/domain/BookingHoldStatus.java` | Hold lifecycle statuses. | 8 | NOT_STARTED |
| `…/domain/AppointmentBookingPolicy.java` | Cancellable/reschedulable policy and notice windows. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AppointmentJpaEntity.java` | Appointment JPA mapping with tenant, customer, snapshots and version. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/BookingHoldJpaEntity.java` | Hold JPA mapping with expiry and version. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AppointmentParticipantJpaEntity.java` | Participant mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AppointmentHistoryJpaEntity.java` | History mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AppointmentJpaRepository.java` | Tenant and actor-scoped appointment data queries. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/BookingHoldJpaRepository.java` | Tenant/owner hold lookups and locking. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/BookingHoldLockRepository.java` | SELECT ... FOR UPDATE / SKIP LOCKED for confirmation-expiry races. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/AppointmentHistoryJpaRepository.java` | Tenant-scoped paged status history. | 8 | NOT_STARTED |

### notification (26 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for notification; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/ReminderDefinitionController.java` | List/create/update reminder definitions. | 8 | NOT_STARTED |
| `…/api/AppointmentNotificationController.java` | Read scoped notifications for appointment. | 8 | NOT_STARTED |
| `…/api/dto/ReminderDefinitionDto.java` | Reminder definition request and response. | 8 | NOT_STARTED |
| `…/api/dto/NotificationResponse.java` | Safe read-only delivery status without PII leak. | 8 | NOT_STARTED |
| `…/contract/package-info.java` | Named interface for delivery gateway and reminder job processing. | 8 | NOT_STARTED |
| `…/contract/EmailGateway.java` | Provider-independent send API with safe message identity. | 8 | NOT_STARTED |
| `…/contract/NotificationEventHandler.java` | Outbox handler for appointment-created/changed event payloads. | 8 | NOT_STARTED |
| `…/contract/ReminderMaintenance.java` | Claim/process due reminder jobs; prevent duplicate sends. | 8 | NOT_STARTED |
| `…/application/NotificationService.java` | Create notification intent, template and deduplication key. | 8 | NOT_STARTED |
| `…/application/NotificationDeliveryService.java` | Attempt provider sends, update statuses, retries. | 8 | NOT_STARTED |
| `…/application/ReminderDefinitionService.java` | Tenant-admin reminder policies and validation. | 8 | NOT_STARTED |
| `…/application/ReminderInstanceService.java` | Persist derived reminder jobs from appointment events. | 8 | NOT_STARTED |
| `…/application/NotificationTemplateRenderer.java` | Escaped subject/text/html rendering with bounded inputs. | 8 | NOT_STARTED |
| `…/application/DeliveryRetryPolicy.java` | Bounded classification/backoff and final failure. | 8 | NOT_STARTED |
| `…/domain/ReminderDefinition.java` | Relative reminder timing policy. | 8 | NOT_STARTED |
| `…/domain/ReminderInstance.java` | Persistent scheduled reminder instance. | 8 | NOT_STARTED |
| `…/domain/Notification.java` | Logical notification aggregate and deduplication. | 8 | NOT_STARTED |
| `…/domain/NotificationDelivery.java` | Per-channel attempt and provider result. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ReminderDefinitionJpaEntity.java` | Definition mapping. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ReminderInstanceJpaEntity.java` | Reminder job due timestamps, leases and state. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/NotificationJpaEntity.java` | Notification identity, payload reference and status. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/NotificationDeliveryJpaEntity.java` | Delivery attempt/state mappings. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/NotificationJpaRepository.java` | Scoped notification status queries. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/ReminderJdbcClaimRepository.java` | Multi-pod SKIP LOCKED due reminders with leases. | 8 | NOT_STARTED |
| `…/infrastructure/persistence/NotificationDeliveryJpaRepository.java` | Claim/send retry statuses and idempotency. | 8 | NOT_STARTED |

### integration (6 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for integration; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/email/SmtpEmailGateway.java` | Adapter implementing notification EmailGateway using local Mailpit/SMTP. | 8 | NOT_STARTED |
| `…/email/SmtpConfiguration.java` | Typed config, timeouts, credentials injection and TLS. | 8 | NOT_STARTED |
| `…/calendar/ExternalCalendarPort.java` | Extension point for ICS/Google/Microsoft providers, no live sync in v1. | 8 | NOT_STARTED |
| `…/calendar/IcsExportService.java` | Optional safe read-only appointment ICS export service (not exposed in v1 API). | 8 | NOT_STARTED |
| `…/http/ExternalHttpClientFactory.java` | Standardized bounded timeouts, allowed hosts and safe retry config. | 8 | NOT_STARTED |

### operations (11 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for operations; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/worker/BookingHoldExpiryJob.java` | Invokes BookingMaintenance in bounded scheduled batches. | 8 | NOT_STARTED |
| `…/worker/OutboxDispatcherJob.java` | Polls and dispatches outbox with leases. | 8 | NOT_STARTED |
| `…/worker/ReminderDispatchJob.java` | Claims/dispatches due reminders safely across pods. | 8 | NOT_STARTED |
| `…/worker/NotificationRetryJob.java` | Processes due delivery retries. | 8 | NOT_STARTED |
| `…/observability/BookingMetrics.java` | Micrometer counters/timers without high-cardinality tags. | 8 | NOT_STARTED |
| `…/observability/OutboxMetrics.java` | Outbox lag and backlog gauges. | 8 | NOT_STARTED |
| `…/observability/ReadinessHealthIndicator.java` | Readiness dependency checks without leaking configuration. | 8 | NOT_STARTED |
| `…/observability/SchedulingMetrics.java` | Availability search timing and truncation metrics. | 8 | NOT_STARTED |
| `…/config/WorkerProperties.java` | Typed hold/outbox/reminder worker batch, interval and lease configuration. | 8 | NOT_STARTED |
| `…/config/BookingProperties.java` | Typed hold TTL, booking horizon and active-hold maximums. | 8 | NOT_STARTED |

### platform (4 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `…/package-info.java` | Spring Modulith metadata for platform; optional allowed dependency list. | 8 | NOT_STARTED |
| `…/api/PlatformOrganizationController.java` | Privileged paginated platform organization listing endpoint. | 8 | NOT_STARTED |
| `…/api/TenantAuditController.java` | GET tenant audit-events; checks tenant admin audit.read permission. | 8 | NOT_STARTED |
| `…/application/PlatformProvisioningService.java` | Controlled org provisioning (POST /organizations) with separate platform privilege. | 8 | NOT_STARTED |

### Resources (18 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `src/main/resources/application.yml` | Environment-agnostic datasource/JPA/security/limits/observability defaults; no secrets. | 8 | NOT_STARTED |
| `src/main/resources/application-local.yml` | Local Keycloak/Mailpit/PostgreSQL settings, dev-only. | 8 | NOT_STARTED |
| `src/main/resources/application-prod.yml` | Production-safe tuning, external secrets and strict transport assumptions. | 13 | NOT_STARTED |
| `src/main/resources/db/migration/V001__extensions_and_global_identity.sql` | Extensions (btree_gist), user identity and app metadata bootstrap. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V002__organizations_and_memberships.sql` | Tenants, business hours, memberships, constraints and FKs. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V003__locations_staff_resources.sql` | Location/staff/resource tables and tenant-safe relationships. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V004__services_and_requirements.sql` | Service and eligible-resource requirement tables. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V005__availability_and_time_off.sql` | Recurring availability, exception and time-off tables. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V006__holds_and_appointments.sql` | Appointment and hold tables, lifecycle/history, participants and version. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V007__active_resource_reservations.sql` | ACTIVE occupancy rows, half-open tstzrange GiST exclusion constraint (CRITICAL). | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V008__idempotency_and_audit.sql` | Scoped idempotency records and append-only audit history. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V009__outbox.sql` | Event publication state, claim leases, retry indices. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V010__notifications_and_reminders.sql` | Reminder definitions/instances, notifications and delivery attempts. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V011__query_indexes.sql` | Workload-driven indexes for availability, list and worker hot paths. | 9 | NOT_STARTED |
| `src/main/resources/db/migration/V012__tenant_integrity_constraints.sql` | Additional tenant composite foreign keys and integrity checks. | 9 | NOT_STARTED |
| `src/main/resources/templates/mail/appointment-confirmed.html` | Escaped booking confirmation HTML template. | 13 | NOT_STARTED |
| `src/main/resources/templates/mail/appointment-cancelled.html` | Escaped booking cancellation HTML template. | 13 | NOT_STARTED |
| `src/main/resources/templates/mail/appointment-reminder.html` | Escaped scheduled reminder HTML template. | 13 | NOT_STARTED |

### Tests (41 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `src/test/java/com/acme/appointmentcalendar/architecture/ModulithBoundaryVerificationTest.java` | ApplicationModules.verify() asserts no cycles/nonexported dependencies. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/architecture/ControllerLayerArchitectureTest.java` | ArchUnit: controller cannot directly inject repositories or persistence types. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/architecture/NoDomainSpringWebDependencyTest.java` | Domain types remain transport/framework decoupled. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/JwtValidatorIntegrationTest.java` | Bad issuer/audience/expiry/signature and correct valid JWT cases. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/AuthenticationEndpointTest.java` | 401/403, cookie/CORS/security header behavior. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/TenantIsolationIntegrationTest.java` | Every tenant-owned resource refuses cross-tenant access. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/BookingObjectAuthorizationTest.java` | Customer A cannot read/change B booking; staff assignment rules. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/AdminFunctionAuthorizationTest.java` | Customer and tenant admin cannot call privileged functions. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/MassAssignmentValidationTest.java` | Unapproved mutable fields are rejected or ignored safely according to contract. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/PrivilegeRevocationTest.java` | Membership revocation effective for still-valid tokens. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/security/RedactedErrorResponseTest.java` | No SQL, stack traces or personal secrets in RFC 9457 errors. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/AppointmentStateMachineTest.java` | Allowed/forbidden lifecycle transitions and terminal states. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/BookingHoldStateMachineTest.java` | Hold ACTIVE→CONFIRMED/EXPIRED/CANCELLED invariants. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/BookingHoldExpiryRaceIT.java` | Confirmation vs expiration at expiry boundary, repeated runs. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/BookingHoldQuotaConcurrencyIT.java` | Concurrent hold requests cannot exceed per-principal limit. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/MultiResourceAtomicBookingIT.java` | Either all required resource reservations exist or none. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/NoDoubleBooking100ThreadsIT.java` | 100 simultaneous attempts: 1 reservation succeeds, 99 conflicts. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/AdjacentIntervalsIT.java` | [start,end) intervals permit exact adjacency, reject 1ms overlap. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/RescheduleAtomicityIT.java` | Conflict during reschedule leaves original appointment intact. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/booking/BookingApiContractIT.java` | Create/confirm/cancel/read REST request/response contract. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/availability/AvailabilityIntervalEngineTest.java` | Weekday/exceptions/buffers/notice/horizon and boundary cases. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/availability/DaylightSavingPolicyTest.java` | UTC, Asia/Kolkata, New York and London DST gap/overlap. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/availability/AvailabilitySearchIntegrationTest.java` | Availability queries exclude active holds and booked occupancy. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/availability/AvailabilityQueryBoundariesTest.java` | Reject date range explosion, unbounded resources and huge results. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/catalog/ServiceRequirementValidationTest.java` | Cross-tenant/invalid resource eligibility rejected. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/organization/MembershipPolicyTest.java` | Role grant/revoke and last-admin protection. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/idempotency/ConcurrentIdempotencyIT.java` | Same key same body has exactly one logical mutation. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/idempotency/IdempotencyConflictIT.java` | Same key different payload returns deterministic conflict. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/idempotency/IdempotencyReplayAfterCommitIT.java` | Lost response retry replays committed result despite advanced version. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/outbox/OutboxCrashRecoveryIT.java` | Committed booking event is dispatched after worker crash/reclaim. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/outbox/OutboxClaimConcurrencyIT.java` | No simultaneous worker lease for same event. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/notification/ReminderDuplicateDeliveryIT.java` | Duplicate trigger/retry does not create multiple logical reminder notifications. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/notification/NotificationFailureRetryIT.java` | Timeout/429/5xx retry classification and final failure behavior. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/api/OpenApiOperationCoverageTest.java` | All documented operationIds mapped to routes, with known intentional exclusions. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/api/Rfc9457ContractTest.java` | Typed problem schema and safe validation errors. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/api/PaginationAndFilteringTest.java` | Authenticated bounded keyset pagination and cursor integrity. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/api/PreconditionAndEtagTest.java` | Missing/stale If-Match; concurrency and replay ordering. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/persistence/PostgresMigrationIT.java` | Flyway clean startup on PostgreSQL 18 and ddl-auto validate. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/persistence/ReservationConstraintIT.java` | Direct SQL proves partial exclusion protection and release behavior. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/persistence/CompositeTenantFkIT.java` | Rejects cross-tenant row references. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/persistence/IndexQueryPlanTest.java` | Optional EXPLAIN critical query plan assertions under seeded scale. | 10 | NOT_STARTED |

### Test Infrastructure (7 files)

| File | Why it exists | Phase | Status |
|---|---|---:|---|
| `src/test/java/com/acme/appointmentcalendar/support/PostgresIntegrationTestBase.java` | Shared Postgres Testcontainers test setup (no H2 substitution). | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/support/TestIdentityFactory.java` | Signed/mock JWT authorities and local membership fixture utilities. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/support/TenantFixtureFactory.java` | Deterministic tenant and resource fixture creation. | 10 | NOT_STARTED |
| `src/test/java/com/acme/appointmentcalendar/support/ContendedBookingRunner.java` | Barriers and threads to force simultaneous booking. | 10 | NOT_STARTED |
| `src/test/resources/application-test.yml` | CI-safe test app config with Testcontainers-backed DB. | 10 | NOT_STARTED |
| `src/test/resources/testdata/booking-hold.json` | Approved request fixture for contract tests. | 10 | NOT_STARTED |
| `src/test/resources/testdata/appointment.json` | Approved response fixture for contract tests. | 10 | NOT_STARTED |


## Critical runtime invariants and file ownership

| Invariant | Owner and authoritative enforcement | Mandatory evidence |
|---|---|---|
| No two ACTIVE rows overlap for same resource | `reservation/infrastructure/persistence/ReservationNativeSqlRepository.java` plus `V007__active_resource_reservations.sql` | `NoDoubleBooking100ThreadsIT`, `ReservationConstraintIT` |
| Multi-resource all-or-nothing | `booking/application/BookingResourceSelectionService.java` + one DB transaction and reservation port | `MultiResourceAtomicBookingIT` |
| Hold confirmation vs expiry race | `booking/application/BookingHoldApplicationService.java`, `BookingHoldExpiryService.java`, lock repository | `BookingHoldExpiryRaceIT` |
| Failed reschedule preserves old booking | `booking/application/AppointmentCommandService.java` + transaction rollback | `RescheduleAtomicityIT` |
| Tenant and customer object isolation | `security/...`, booking policies, tenant-qualified repositories, tenant FKs | `TenantIsolationIntegrationTest`, `BookingObjectAuthorizationTest`, `CompositeTenantFkIT` |
| Safe retry / idempotent result | `idempotency/...` with transactional booking result | `ConcurrentIdempotencyIT`, `IdempotencyReplayAfterCommitIT` |
| No direct email inside booking transaction | `booking` writes `OutboxWriter`; `operations` workers dispatch later | `OutboxCrashRecoveryIT`, module boundary test |
| No double reminder logical delivery | `notification/...` deduplication + worker claim | `ReminderDuplicateDeliveryIT` |
| DST-safe time slots | `availability/application/DaylightSavingPolicy.java` | `DaylightSavingPolicyTest` |
| No secrets/PII in errors | `shared/error` + security and logging | `RedactedErrorResponseTest` |

## Build/dependency policy

- Java **25 LTS**; Maven Wrapper; Spring Boot parent **4.1.1**; Spring Modulith BOM **2.1.1**.
- Core dependencies: Boot web MVC, validation, security, OAuth2 Resource Server, Data JPA, Actuator, Flyway, PostgreSQL JDBC, and **`org.flywaydb:flyway-database-postgresql`**.
- Tests: `spring-boot-starter-test`, required test-specific starter modules, Spring Security test, JUnit Jupiter (managed by Boot), AssertJ, Mockito, PostgreSQL Testcontainers, ArchUnit, Spring Modulith test support.
- OpenAPI: canonical checked-in 3.1.2 YAML; verify springdoc 3.x support for the chosen Boot 4.1 release before adding Swagger UI; Swagger is restricted to local/staging.
- Telemetry: Micrometer/OTLP as managed by Boot; avoid hand-pinning transitive observability dependencies.
- Do not add Kafka, Redis, gRPC, native image, distributed-lock service, complex mapper framework, or payment SDK without a written workload-backed ADR.

## Migration ordering and irreversible changes

1. V001: PostgreSQL extensions/global identities.
2. V002: Tenant/membership/organization business hours.
3. V003: Staff/location/resource.
4. V004: Services, requirements and eligibility.
5. V005: Availability rules, exceptions and time off.
6. V006: Booking holds, appointments and participants.
7. V007: Reservation storage and ACTIVE interval non-overlap exclusion constraint.
8. V008: Idempotency + audit.
9. V009: Transactional outbox.
10. V010: Reminders and notifications.
11. V011: Query/work queue indexes.
12. V012: Additional tenant constraints and retention-integrity hardening.
13. No V013 is reserved without a concrete schema need; future migrations are added when justified.

**Correction for implementation:** V007 must not rely on application boot code executing before Flyway has created the reservations. Migrations are forward-only and production data cleanup/large index builds require an online rollout plan. For brand-new schemas, a one-time full schema migration path is fine; for later releases use expand/migrate/contract discipline.

## Dependency-first file generation sequence (within Phase 8)

1. `pom.xml` + Maven Wrapper + Boot launcher + `application.yml`; get an empty health-checked app compiling.
2. `shared` + identity + organization + security; implement secure JWT/membership resolution.
3. Resource and catalog tables/models/controllers; stable service/resource read ports.
4. Reservation contract and persistence schema; implement the exclusion constraint **before exposing booking mutations**.
5. Availability domain and search engine, including timezone/DST, buffers and resource choices.
6. Audit + idempotency + outbox transactional facilities.
7. Booking holds, confirmations, direct booking, reschedule, cancel and lifecycle commands.
8. Notification/reminder model + SMTP adapter + operation workers.
9. Platform admin endpoints and complete OpenAPI controllers.
10. Attach integration/security/concurrency tests, deployments, CI and documentation before production review.

The published phase sequence says Phase 8 implementation, Phase 9 migrations, Phase 10 tests. That is a **delivery/documentation order**, not a reason to build booking code without running migrations or tests early. Foundational Flyway and tests must be implemented alongside each vertical slice; the later phases finalize comprehensive coverage and review.

## Atomic transaction graph

```
Booking command
  -> validate actor + membership (security/organization)
  -> resolve service policy (catalog)
  -> validate location + resource eligibility (resource)
  -> validate scheduling windows (availability)
  -> BEGIN PostgreSQL transaction
     -> claim idempotency (idempotency)
     -> lock required hold / appointment if needed (booking)
     -> insert/convert/release active rows (reservation)
     -> insert domain state/history (booking)
     -> append audit (audit)
     -> append durable outbox event (outbox)
     -> store idempotency result (idempotency)
  -> COMMIT
  -> later: outbox worker (operations) -> notification -> email adapter (integration)
```

A service may need earlier transaction start to protect quota/hold state; the invariant is that all authoritative writes are atomic and no remote email/calendar call holds that transaction open. Non-overlap is enforced by PostgreSQL, not by an in-memory availability check.

## API and contract acceptance criteria

- Coverage of **every one of the 67 Phase 5 operations**; no accidental undocumented admin/mutation route.
- API request DTOs are immutable, validated, and never mapped directly to arbitrary JPA fields.
- Bearer tokens validated with trusted issuer, audience and signatures; object-level authorization is mandatory.
- Correct 401/403/404 concealment and RFC 9457 problem responses.
- 201 + Location for creations, stable `ETag` for mutable resources, `If-Match` policy for lifecycle mutations.
- Idempotency replay returns the committed logical result even if entity version advanced.
- Bounded filters, stable keyset pagination, case-specific field visibility and rate limits.
- API contract tests verify OpenAPI JSON schemas and HTTP runtime behavior (no unsupported status codes).

## Evidence required to mark VERIFIED

| Asset type | What VERIFIED means |
|---|---|
| Java | `./mvnw clean verify` passes and module contract checks pass |
| Flyway SQL | Fresh PostgreSQL 18 container migrates, Hibernate validate passes; concurrency SQL tests pass |
| OpenAPI | Schema linter/ref checks + endpoint/schema response tests pass |
| Integration tests | PostgreSQL Testcontainers test suite executed, not mocked/H2 |
| Docker/Compose | Fresh local stack boots and sample booking walkthrough succeeds |
| Kubernetes YAML | Manifests render/validate and staging probes/readiness/rollout are exercised |
| CI | Pipeline runs on clean checkout, scans/blocking policies verified |
| Runbooks | Controlled failure drills, restore tests and owner sign-off recorded |
| Security controls | Cross-tenant/IDOR, wrong-audience tokens, mass-assignment and abuse tests demonstrated |
| Performance | k6 scenarios run under stated hardware/load, latency and conflict metrics recorded |

**Do not mark files VERIFIED because the assistant mentally reasoned that they compile.** Actual tool-backed verification is necessary.

## Manifest change-control rules

- File status values: `NOT_STARTED`, `GENERATED`, `REVISED`, `VERIFIED` only.
- Change a file's purpose or remove a critical file only after updating ownership, ADR, API coverage, migration plan and tests.
- Every addition should declare path, module/area, generating phase, functionality, verification and status.
- Code-generation progress is recorded in `docs/implementation-manifest.json`, not guessed from conversation history.
- The codebase is a modular monolith (single Maven module) with independently verified Spring Modulith feature boundaries.
- Security/privacy/availability correctness have priority over completion counts.

## Phase handoff

**COMPLETED:** Phase 7 repository design, exhaustive path inventory, module boundaries, API route ownership, migration/test plans and build order.  
**NEXT:** Phase 8, beginning with actual `pom.xml`, wrapper, Boot application, and a first compiling vertical slice; bring executable migrations and tests forward as dependencies.  
**REMAINING:** Implement, run, test, deploy to staging and conduct production readiness review. This document itself is not evidence the app runs.
