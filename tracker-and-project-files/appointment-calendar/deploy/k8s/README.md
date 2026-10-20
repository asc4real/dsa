# Kubernetes deployment

This is a **secure template**, not an installable production deployment until you provide:
- an immutable image digest and registry; secret manager integration;
- DB_URL, DB_USER, DB_PASSWORD, OIDC_ISSUER_URI, OIDC_AUDIENCE;
- TLS ingress, DNS, restricted NetworkPolicy and egress policy;
- dedicated Flyway job with migration credentials, staged rollout and rollback plan;
- ingress WAF/rate limits, resource sizing, telemetry/exporters and autoscaling validation.
Never enable arbitrary ingress or public access to PostgreSQL.
