# Phase 8 acceptance gates — evidence status

| Stage | Source provided | Executed here? | Production ready? |
|---|---|---|---|
| 8.1 Maven foundation | Yes | Static XML check only | No |
| 8.2 Shared primitives/errors | Yes | Java syntax parsed | No |
| 8.3 Identity/organization | Yes | Java syntax parsed | No |
| 8.4 JWT/permissions | Yes | Java syntax parsed | No |
| 8.5 Resource/catalog CRUD | Yes | Java syntax parsed | No |
| 8.6 PostgreSQL reservations | Yes, exclusion migration | Not executed | No |
| 8.7 Availability engine | Initial implementation | Not tested | No |
| 8.8 Idempotency | Atomic coordinator | Not tested | No |
| 8.9 Booking lifecycle | Initial implementation | Not tested | No |
| 8.10 Audit/outbox | Initial implementation | Not tested | No |
| 8.11 Notifications | SMTP adapter and queue | Not tested | No |
| 8.12 Workers | Hold/outbox/reminder/notification | Not tested | No |
| 8.13 REST API | 67/67 handler mappings | Static matched; semantic drift remains | No |
| 8.14 Observability | Actuator/Prometheus/OTLP config | Not exercised | No |
| 8.15 Integration verification | Test sources | Not executed; Docker unavailable | No |
| 8.16 Release packaging | Dockerfile, Compose, K8s templates, CI | YAML parsed; images not built | No |

**Do not interpret source coverage as feature verification.** Production readiness is blocked until compilation, migration execution, security, concurrency, availability, contract, container and rollout tests pass.
