# 1,000 Spring Boot Projects — Complete Backend Engineering Practice Curriculum

**Purpose:** progress from building small HTTP endpoints to designing, deploying, and operating production-grade backends.

**How to interpret this list:** these are **1,000 distinct implementation briefs**. Some are standalone applications; others are focused experiments, services, modules, or system-design capstones. You do **not** need 1,000 separate repositories or deployments.

## How to practice

1. **Build the simplest working implementation** first; write down the business rules and expected behavior.
2. Add appropriate **unit, integration, boundary, security, concurrency, and failure tests**.
3. Capture a **README, API contract, architecture diagram, and short decision log** explaining tradeoffs.
4. As your skills grow, add **database migrations, containerization, structured logs, health checks, tracing, metrics, CI**, and reproducible local setup.
5. Do **not** force Kafka, Kubernetes, microservices, or complicated abstractions into beginners' projects. Add technology only when it solves the exercise's problem.
6. For intermediate and advanced exercises, intentionally inject failure: bad inputs, downstream timeouts, concurrent requests, restarts, duplicates, and unavailable dependencies.

**Common stack:** Java, Spring Boot, Spring MVC/RestClient, Spring Data JPA, PostgreSQL, JUnit, Testcontainers, Spring Security, Flyway, Docker. Use Spring Batch, Kafka, RabbitMQ, Redis, Reactor/WebFlux, GraphQL, gRPC, OpenTelemetry, Kubernetes/OpenShift and Spring AI when the corresponding project calls for them. Choose compatible versions using official documentation.

## Curriculum map (40 tracks × 25 projects = 1,000)

| Track | Projects | Focus | Level |
|---:|---:|---|---|
| 01 | 001–0025 | HTTP, Web, and REST Foundations | Beginner |
| 02 | 026–0050 | API Design, Validation, and Compatibility | Beginner |
| 03 | 051–0075 | Spring Core, Configuration, and Build Engineering | Beginner |
| 04 | 076–0100 | JPA and Relational Persistence Fundamentals | Beginner |
| 05 | 101–0125 | SQL, Data Modeling, and Migrations | Intermediate |
| 06 | 126–0150 | Advanced JPA, Transactions, and Concurrency | Intermediate |
| 07 | 151–0175 | PostgreSQL and Advanced Database Features | Intermediate |
| 08 | 176–0200 | NoSQL and Polyglot Persistence | Intermediate |
| 09 | 201–0225 | Authentication and Authorization Basics | Intermediate |
| 10 | 226–0250 | OAuth2, OIDC, Tokens, and Advanced Security | Intermediate |
| 11 | 251–0275 | Unit, Slice, and Integration Testing | Intermediate |
| 12 | 276–0300 | API Quality, Contracts, and Verification | Intermediate |
| 13 | 301–0325 | Spring Batch and Data Processing | Intermediate |
| 14 | 326–0350 | Scheduling, Work Queues, and Orchestration | Intermediate |
| 15 | 351–0375 | Java Concurrency, Async, and Reactive Foundations | Advanced |
| 16 | 376–0400 | Apache Kafka and Event Streaming | Advanced |
| 17 | 401–0425 | RabbitMQ, AMQP, and Work Messaging | Advanced |
| 18 | 426–0450 | Redis, Caching, and Rate Limiting | Advanced |
| 19 | 451–0475 | Distributed Systems Fundamentals | Advanced |
| 20 | 476–0500 | Microservice Design and Decomposition | Advanced |
| 21 | 501–0525 | Resilience and Fault-Tolerant Service Calls | Advanced |
| 22 | 526–0550 | Protocols and External System Integration | Advanced |
| 23 | 551–0575 | Files, Object Storage, and Document Pipelines | Advanced |
| 24 | 576–0600 | Search, Indexing, and Recommendations | Advanced |
| 25 | 601–0625 | Logging, Metrics, Tracing, and Observability | Advanced |
| 26 | 626–0650 | Performance Engineering and JVM Tuning | Advanced |
| 27 | 651–0675 | Docker, Kubernetes, and Cloud Operations | Advanced |
| 28 | 676–0700 | CI/CD, Supply Chain, and Release Engineering | Advanced |
| 29 | 701–0725 | Distributed Data, Consistency, and Transactions | Advanced |
| 30 | 726–0750 | Time, Events, and Workflow State Machines | Advanced |
| 31 | 751–0775 | Banking, Payments, and Financial Systems | Advanced |
| 32 | 776–0800 | SaaS, Multi-Tenancy, and Identity Platforms | Advanced |
| 33 | 801–0825 | Real-Time Apps, Collaboration, and Notifications | Advanced |
| 34 | 826–0850 | Privacy, Compliance, Governance, and Auditing | Advanced |
| 35 | 851–0875 | Architecture Patterns and Maintainability | Advanced |
| 36 | 876–0900 | Networking, Infrastructure, and Platform Fundamentals | Advanced |
| 37 | 901–0925 | Large-Scale System Design Capstones | Expert |
| 38 | 926–0950 | Specialized Spring Ecosystem and Advanced Frameworks | Expert |
| 39 | 951–0975 | AI, Analytics, and Data Engineering Backends | Expert |
| 40 | 976–1000 | Production Reliability, Disaster Recovery, and Expert Challenges | Expert |

## All 1,000 project briefs

### Track 01: HTTP, Web, and REST Foundations (Beginner)

1. **Hello API** — return text, JSON, headers, and status codes.
2. **Personal profile API** — expose GET and PATCH endpoints.
3. **Calculator API** — validate numbers and handle division by zero.
4. **Unit conversion API** — support query parameters and precision.
5. **Quote-of-the-day API** — choose and return randomized quotes.
6. **To-do API** — create, read, update, and delete tasks in memory.
7. **Notes API** — filter notes by tag and creation date.
8. **Contact book API** — search names and phone numbers.
9. **Book catalog API** — model collections and individual resources.
10. **Movie watchlist API** — implement PUT versus PATCH correctly.
11. **Habit tracker API** — record completions using POST.
12. **BMI calculator API** — design predictable request and response DTOs.
13. **Temperature history API** — filter by time range.
14. **URL metadata API** — parse URLs safely without fetching private hosts.
15. **Currency-format API** — apply locale-specific formatting.
16. **HTTP echo server** — inspect method, path, headers, and body.
17. **Response-header playground** — configure cache and content headers.
18. **Content negotiation API** — serve JSON and XML representations.
19. **Conditional GET demo** — return ETag and 304 Not Modified.
20. **File download endpoint** — serve bytes with correct content disposition.
21. **Redirect service** — compare 301, 302, 307, and 308 behavior.
22. **Pagination basics API** — implement page, size, and stable sorting.
23. **Search filter API** — combine optional query parameters.
24. **API error sandbox** — model 400, 404, 405, 409, and 500 responses.
25. **Mini storefront API** — combine products, carts, and basic orders.

### Track 02: API Design, Validation, and Compatibility (Beginner)

26. **Customer registration API** — use Bean Validation constraints.
27. **Address validator API** — validate nested request objects.
28. **Employee directory** — separate domain entities from DTOs.
29. **Inventory adjustments API** — reject invalid quantity transitions.
30. **Reservation input API** — validate date and time rules.
31. **Global exception handler** — standardize errors with Problem Details.
32. **Field-error translator** — produce actionable validation messages.
33. **Correlation-ID API** — propagate request identifiers to responses.
34. **Idempotent create API** — deduplicate repeated POST requests.
35. **API version migration** — support v1 and v2 response contracts.
36. **Sparse-field API** — let callers request selected attributes.
37. **Bulk creation API** — report individual successes and failures.
38. **HTTP PATCH playground** — support partial updates safely.
39. **Optimistic HTTP updates** — enforce If-Match preconditions.
40. **Sort-and-filter DSL** — whitelist allowed filtering fields.
41. **HATEOAS catalog** — add discoverable resource links.
42. **OpenAPI-first inventory API** — implement a written specification.
43. **OpenAPI-generated client demo** — regenerate clients after contract changes.
44. **Multipart form API** — combine JSON fields and attachments.
45. **Internationalized error API** — localize validation messages.
46. **Custom constraint library** — create reusable cross-field validators.
47. **Date-time contract API** — enforce ISO-8601 and UTC conventions.
48. **Request-size guard** — reject oversized payloads gracefully.
49. **Backward-compatibility testbed** — keep old consumers working.
50. **Public API style guide project** — apply uniform status, naming, and error rules.

### Track 03: Spring Core, Configuration, and Build Engineering (Beginner)

51. **Dependency-injection playground** — compare constructor and setter injection.
52. **Bean scopes explorer** — demonstrate singleton, prototype, and request scope.
53. **Conditional bean app** — select implementations by configuration.
54. **Environment-specific greeter** — use dev, test, and production profiles.
55. **Configuration-properties library** — bind and validate typed settings.
56. **Feature-toggle service** — enable behaviors without redeployment.
57. **Application event demo** — publish and consume in-process domain events.
58. **Bean lifecycle tracer** — observe initialization and shutdown callbacks.
59. **Custom starter** — package reusable application auto-configuration.
60. **Auto-configuration inspector** — explain why beans were or were not created.
61. **Maven multi-module service** — split API, core, and persistence modules.
62. **Gradle multi-module service** — establish dependency boundaries.
63. **Reproducible build demo** — pin plugins and enforce dependencies.
64. **Secret-loading app** — inject secrets without committing them.
65. **Local config precedence tester** — compare files, variables, and flags.
66. **Graceful-shutdown demo** — finish in-flight requests before exit.
67. **Custom health indicator** — report dependency readiness safely.
68. **Command-line Spring Boot app** — run domain workflows without HTTP.
69. **Startup data initializer** — bootstrap reference data idempotently.
70. **Embedded server tuner** — configure connectors and timeouts.
71. **Structured logging demo** — produce machine-parseable application logs.
72. **Application argument parser** — use typed startup options.
73. **Configuration refresh simulator** — separate dynamic and immutable settings.
74. **Custom actuator endpoint** — expose safe operational diagnostics.
75. **Modular monolith skeleton** — enforce packages and module boundaries.

### Track 04: JPA and Relational Persistence Fundamentals (Beginner)

76. **Persistent to-do service** — store tasks in PostgreSQL.
77. **Library circulation service** — model books, copies, and borrowers.
78. **Student enrollment API** — persist many-to-many relationships.
79. **Employee–department service** — model one-to-many ownership.
80. **Blog publishing backend** — persist posts, authors, and comments.
81. **Product catalog persistence** — map entities and repository methods.
82. **Customer address book** — compare embedded values and associations.
83. **Wishlist persistence** — store unique user-product memberships.
84. **Expense journal** — persist dates, categories, and amounts.
85. **Recipe collection** — map ingredients and ordered instructions.
86. **Appointment calendar** — persist time slots and participants.
87. **Issue tracker** — manage ticket status and assignments.
88. **Asset registry** — maintain equipment and location relations.
89. **Course prerequisite graph** — model self-referencing relationships.
90. **Document metadata catalog** — persist version and ownership metadata.
91. **Music playlist manager** — preserve track ordering.
92. **Polling backend** — enforce one response per voter and poll.
93. **Store review API** — manage ratings, authors, and products.
94. **Customer preferences store** — persist enumerations and value types.
95. **Order-line manager** — map order headers and ordered items.
96. **Soft-delete demo** — retain and filter archived records explicitly.
97. **Audited inventory store** — auto-populate created and updated timestamps.
98. **Repository query lab** — compare derived queries and JPQL.
99. **Projection read API** — return DTO projections instead of full entities.
100. **Persistent mini-CRM** — combine contacts, activities, and organizations.

### Track 05: SQL, Data Modeling, and Migrations (Intermediate)

101. **Schema migration laboratory** — manage versioned Flyway changes.
102. **Liquibase alternative laboratory** — compare declarative migration styles.
103. **Unique-constraint registration app** — prevent duplicate business keys.
104. **Order summary report** — calculate totals with joins and aggregates.
105. **Leaderboard SQL service** — use window functions and ranking.
106. **Hierarchical category API** — query tree-shaped categories.
107. **Temporal price-list service** — preserve price history and validity.
108. **Ledger schema prototype** — model balanced postings and constraints.
109. **Seat allocation model** — enforce capacity with database invariants.
110. **Subscription renewal schema** — represent billing periods and status.
111. **Coupon eligibility engine** — express database-backed rules.
112. **Employee payroll report** — group salary data by period.
113. **Warehouse picklist query** — optimize joins across stock tables.
114. **Searchable directory** — compare LIKE, indexes, and full text.
115. **Composite-index experiment** — benchmark different index orderings.
116. **Query execution-plan visualizer** — collect and explain EXPLAIN plans.
117. **Database seed-data builder** — generate realistic related fixtures.
118. **Read-model SQL endpoint** — use JDBC for purpose-built projections.
119. **Stored-procedure integration** — invoke and test procedural logic.
120. **Materialized-view dashboard** — refresh precomputed reports safely.
121. **Partitioned event table** — route date-partitioned activity records.
122. **Keyset pagination API** — paginate reliably through large datasets.
123. **Data cleanup utility** — detect and repair orphaned legacy rows.
124. **Zero-downtime migration demo** — perform expand, migrate, contract.
125. **Data-model review kit** — validate constraints against business rules.

### Track 06: Advanced JPA, Transactions, and Concurrency (Intermediate)

126. **Transaction boundary simulator** — compare committed and rolled-back work.
127. **Inventory oversell lab** — reproduce and fix lost updates.
128. **Optimistic-lock booking API** — reject conflicting versions cleanly.
129. **Pessimistic-lock stock reservation** — serialize competing writers.
130. **Deadlock reproduction app** — detect, analyze, and retry safely.
131. **Isolation-level lab** — demonstrate dirty/nonrepeatable/phantom reads.
132. **N+1 query detector** — fix excessive ORM round trips.
133. **Fetch-plan benchmark** — compare joins, entity graphs, and batches.
134. **Lazy-loading failure lab** — design DTO mapping within boundaries.
135. **Cascading-delete safety app** — prevent accidental data loss.
136. **Orphan-removal playground** — enforce parent-child aggregate ownership.
137. **Bulk-update service** — reconcile persistence-context stale state.
138. **Read-only transaction profiler** — examine query and flush behavior.
139. **Cross-service transaction anti-pattern demo** — replace shared transactions.
140. **Transactional outbox starter** — write state and outbound event atomically.
141. **Nested workflow rollback experiment** — compare propagation behavior.
142. **Transaction timeout demo** — handle slow persistence safely.
143. **Audit-envers prototype** — compare historical revisions of entities.
144. **Multi-database persistence app** — separate two transaction managers.
145. **Custom repository fragment** — implement a specialized query layer.
146. **Hibernate batch insert benchmark** — tune batching for ingestion.
147. **Entity equality laboratory** — implement identifiers and hash codes correctly.
148. **Entity lifecycle observer** — trace persist, merge, flush, and detach.
149. **Database advisory-lock worker** — coordinate competing instances.
150. **Concurrent bank-transfer simulator** — preserve balances and invariants.

### Track 07: PostgreSQL and Advanced Database Features (Intermediate)

151. **JSONB attribute catalog** — query flexible product specifications.
152. **PostgreSQL array filter API** — search tags stored as arrays.
153. **Geospatial store locator** — use PostGIS proximity searches.
154. **Full-text document search** — rank records with tsvector indexes.
155. **Time-series sensor archive** — partition high-volume measurements.
156. **Recursive organization chart** — query reporting lines with CTEs.
157. **Advisory-lock leader task** — allow only one active coordinator.
158. **Exclusion-constraint reservation system** — prevent overlapping ranges.
159. **Partial-index order search** — optimize only active orders.
160. **Generated-column pricing report** — compute persisted derived fields.
161. **Upsert-based inventory importer** — use ON CONFLICT safely.
162. **Change-history trigger service** — compare triggers with application audit.
163. **PostgreSQL notification listener** — consume LISTEN/NOTIFY signals.
164. **Logical replication experiment** — publish row changes to a consumer.
165. **Read-replica routing prototype** — handle replica lag visibly.
166. **Connection-pool saturation lab** — size HikariCP under contention.
167. **Lock monitoring API** — surface blocking transactions.
168. **Slow-query incident simulator** — diagnose bad query plans.
169. **BRIN versus B-tree benchmark** — index large append-only tables.
170. **Retention policy worker** — prune partitions without huge deletes.
171. **Row-level-security tenant demo** — verify tenant isolation in SQL.
172. **Database backup restore drill** — test actual recovery correctness.
173. **Encryption-at-rest integration** — use managed storage encryption and controls.
174. **Database failover lab** — retry correctly after leader changes.
175. **PostgreSQL production checklist** — validate schema, indexes, pooling, and recovery.

### Track 08: NoSQL and Polyglot Persistence (Intermediate)

176. **MongoDB product catalog** — store documents with flexible fields.
177. **MongoDB nested comment threads** — model embedded versus referenced data.
178. **MongoDB aggregation reports** — build multi-stage pipelines.
179. **MongoDB change-stream worker** — react to collection changes.
180. **MongoDB TTL session store** — expire ephemeral records.
181. **MongoDB transaction lab** — coordinate related writes with proper guarantees.
182. **DynamoDB-style key-value API** — design partition and sort-key access patterns.
183. **DynamoDB conditional-write demo** — reject stale updates atomically.
184. **Cassandra event archive** — optimize tables for specific queries.
185. **Cassandra time-bucketed metrics** — manage partition growth.
186. **Elasticsearch read-model app** — index relational domain data.
187. **Neo4j recommendation graph** — traverse related people and interests.
188. **Neo4j fraud ring prototype** — discover connected suspicious identities.
189. **Redis-backed ephemeral voting** — use atomic counters and expiry.
190. **Multi-store profile system** — combine SQL data with document preferences.
191. **Polyglot order catalog** — choose stores for transactional and search workloads.
192. **Document schema evolution tester** — migrate heterogeneous historical records.
193. **Eventual-consistency comparison lab** — contrast SQL and NoSQL reads.
194. **Hot-partition load simulator** — identify skewed key distribution.
195. **Secondary-index tradeoff experiment** — measure write/read costs.
196. **NoSQL pagination exercise** — use cursor tokens instead of offsets.
197. **Data synchronization reconciler** — compare two stores for divergence.
198. **Data deletion propagator** — erase subject data across stores.
199. **NoSQL backup restoration drill** — recover collections and validate checksums.
200. **Persistence decision workbook API** — expose workload-driven tradeoffs.

### Track 09: Authentication and Authorization Basics (Intermediate)

201. **Basic-auth secured API** — understand credentials and TLS assumptions.
202. **Password registration service** — hash passwords with strong encoders.
203. **Login-rate limiter** — slow credential-stuffing attempts.
204. **Secure session login** — manage cookies, expiry, and logout.
205. **CSRF protection demo** — test cookie-based write requests.
206. **Roles-and-permissions API** — restrict actions using Spring Security.
207. **Method-security lab** — enforce authorization at service boundaries.
208. **Resource ownership API** — prevent reading another user's records.
209. **Password reset workflow** — issue single-use expiring reset links.
210. **Email verification service** — activate accounts only after proof.
211. **Account lockout manager** — balance abuse prevention and denial risks.
212. **Remember-device feature** — invalidate trusted-device credentials.
213. **Session fixation prevention lab** — rotate sessions on login.
214. **Secure CORS demonstrator** — allow only approved browser origins.
215. **Security header validator** — add and test protection headers.
216. **Input authorization matrix** — test every endpoint-role combination.
217. **Administrator impersonation audit** — require explicit authorization and logging.
218. **Tenant-aware authorization** — combine role and tenant checks.
219. **Secure upload gateway** — prevent path traversal and file spoofing.
220. **API key service** — issue, hash, rotate, and revoke keys.
221. **Credential rotation endpoint** — revoke prior active secrets.
222. **Temporary access grants** — authorize resources until expiration.
223. **Ownership transfer workflow** — confirm both parties' permissions.
224. **Access-denied analysis tool** — distinguish authentication from authorization.
225. **Security regression suite** — prove forbidden requests stay forbidden.

### Track 10: OAuth2, OIDC, Tokens, and Advanced Security (Intermediate)

226. **OAuth2 resource server** — validate signed JWT access tokens.
227. **Opaque-token introspection service** — authorize via introspection responses.
228. **OpenID Connect login app** — authenticate using an external provider.
229. **OAuth2 client credentials demo** — call machine-to-machine APIs.
230. **Authorization-code-with-PKCE demo** — secure a public client flow.
231. **Scope-based API** — map token scopes to granular actions.
232. **Audience restriction API** — reject tokens meant for other services.
233. **JWT key rotation lab** — accept rotated signing keys correctly.
234. **Token expiry simulator** — distinguish expired and malformed credentials.
235. **Refresh-token lifecycle demo** — test rotation and replay handling.
236. **Token revocation service** — invalidate issued refresh credentials.
237. **Multiple identity-provider gateway** — normalize claims safely.
238. **OIDC user provisioning** — link external identities to local accounts.
239. **OAuth2 downstream token relay** — propagate delegated identity correctly.
240. **Token exchange proof-of-concept** — request narrower downstream credentials.
241. **Signed webhook validator** — verify HMAC and replay windows.
242. **Mutual TLS service** — authenticate internal callers with certificates.
243. **Fine-grained policy engine** — evaluate roles, attributes, and context.
244. **Permission hierarchy explorer** — test inherited versus explicit grants.
245. **Step-up authentication flow** — require stronger proof for sensitive actions.
246. **WebAuthn passkey relying-party prototype** — implement registration and assertion.
247. **Secure logout with OIDC** — coordinate local and provider logout.
248. **JWK cache failure lab** — handle identity-provider outage deliberately.
249. **Security audit event stream** — record auth decisions without secrets.
250. **Authorization server sandbox** — explore issuing standards-based tokens safely.

### Track 11: Unit, Slice, and Integration Testing (Intermediate)

251. **JUnit domain-rules suite** — exercise state transitions without Spring context.
252. **Mockito service isolation** — verify collaborator behavior and failures.
253. **Parameterized pricing tests** — cover numeric and boundary combinations.
254. **Property-based validation tests** — generate broad input populations.
255. **Spring MVC slice tests** — verify controllers with MockMvc.
256. **JSON serialization contract tests** — detect unexpected field changes.
257. **Repository slice tests** — validate real mapping and queries.
258. **PostgreSQL Testcontainers integration** — test against production-like SQL.
259. **Redis Testcontainers integration** — verify cache operations.
260. **MongoDB Testcontainers integration** — test document behavior.
261. **Security test identities** — cover roles and unauthenticated access.
262. **Application context smoke suite** — detect broken bean wiring.
263. **Transaction rollback testbed** — prove failed workflows leave no changes.
264. **Clock-controlled expiry tests** — make time-dependent rules deterministic.
265. **Deterministic random generator tests** — fix seeds and replay failures.
266. **Fixture builder library** — avoid brittle test setup.
267. **Test data lifecycle manager** — isolate records across test methods.
268. **Exception mapping suite** — assert consistent API error responses.
269. **File upload integration tests** — check content, size, and filename rules.
270. **Concurrency correctness test** — synchronize racers to reproduce conflicts.
271. **Database migration test suite** — verify clean and upgrade paths.
272. **Mutation testing exercise** — identify assertions that miss bugs.
273. **Coverage gate build** — report branch coverage with meaningful thresholds.
274. **Flaky-test laboratory** — eliminate shared state and timing dependence.
275. **End-to-end order journey** — exercise HTTP, SQL, and messaging locally.

### Track 12: API Quality, Contracts, and Verification (Intermediate)

276. **Consumer-driven contract producer** — publish Pact-style expectations.
277. **Consumer-driven contract client** — verify compatibility on change.
278. **Spring Cloud Contract stub service** — simulate provider behavior.
279. **OpenAPI diff checker** — detect breaking schema changes.
280. **REST Docs generation** — derive accurate API examples from tests.
281. **API fuzz testing sandbox** — send malformed inputs safely.
282. **Boundary-value test generator** — target lengths, ranges, and nulls.
283. **Error taxonomy verification** — enforce documented error codes.
284. **Idempotency contract test** — replay requests and compare results.
285. **Retry contract simulator** — inject 429 and 5xx status sequences.
286. **API latency budget test** — fail on unacceptable regressions.
287. **Static code quality gate** — integrate Checkstyle and SpotBugs.
288. **Dependency vulnerability scanner** — report known vulnerable packages.
289. **Secret leak prevention hook** — detect committed credentials.
290. **Software bill-of-materials generator** — export dependency inventory.
291. **Load-testing starter** — drive HTTP users with k6 or Gatling.
292. **Soak-test harness** — detect resource growth under long workloads.
293. **Chaos test gateway** — inject slow and reset connections.
294. **WireMock partner API** — stub downstream variants deterministically.
295. **HTTP recording and replay demo** — sanitize and reuse interactions.
296. **Snapshot API tests** — detect intentional versus accidental schema change.
297. **Accessibility-independent API contract suite** — test pure service semantics.
298. **Backward-compatible database test** — run older service against expanded schema.
299. **Test parallelism benchmark** — accelerate suites without cross-test collisions.
300. **Quality dashboard app** — combine test, coverage, and contract reports.

### Track 13: Spring Batch and Data Processing (Intermediate)

301. **CSV-to-database importer** — process records in chunks.
302. **Database-to-CSV exporter** — stream large result sets to disk.
303. **JSON-to-CSV converter** — flatten nested partner API responses.
304. **Excel employee importer** — validate rows and emit rejection reports.
305. **Nightly invoice generator** — calculate and persist invoice summaries.
306. **Bulk email campaign preparer** — stage recipients without sending duplicates.
307. **Payroll calculator batch** — process periods with auditable totals.
308. **Interest accrual batch** — apply daily account rules reproducibly.
309. **Failed-record skip demo** — distinguish skips from retries.
310. **Restartable batch experiment** — resume after controlled failure.
311. **Batch partitioning demo** — split data by ranges across workers.
312. **Multithreaded chunk benchmark** — examine throughput and thread safety.
313. **Remote chunking prototype** — distribute processing to workers.
314. **Remote partitioning prototype** — coordinate independent partitions.
315. **Incremental ETL pipeline** — import only changed upstream records.
316. **SFTP settlement importer** — validate file manifest and checksums.
317. **Daily reconciliation processor** — compare source and destination totals.
318. **PII masking batch** — redact sensitive exported fields.
319. **Duplicate record deduplicator** — preserve canonical entries.
320. **Historic archive mover** — migrate old rows to cold storage.
321. **Batch job explorer API** — inspect executions, steps, and failures.
322. **Parameterized replay service** — rerun a specific processing window.
323. **Backpressure-aware bulk writer** — throttle downstream storage calls.
324. **Batch retention cleaner** — prune stale execution metadata safely.
325. **Enterprise partner pipeline** — fetch JSON, transform CSV, and deliver reliably.

### Track 14: Scheduling, Work Queues, and Orchestration (Intermediate)

326. **Daily reminder scheduler** — trigger an action at a configured time.
327. **Cron expression tester** — expose upcoming execution times.
328. **Timezone-aware notification scheduler** — honor recipient local time.
329. **Missed-run recovery app** — detect and recover missed schedules.
330. **Cluster-safe cleanup job** — avoid duplicate runs across instances.
331. **Lease-based scheduled worker** — renew and expire job ownership.
332. **Retryable task registry** — persist attempts and next-run time.
333. **Exponential backoff scheduler** — spread failed retries over time.
334. **Jitter implementation lab** — reduce synchronized retry storms.
335. **Job cancellation API** — stop pending work safely.
336. **Priority work queue** — process urgent items before routine items.
337. **Delayed task service** — schedule per-record future execution.
338. **DAG workflow executor** — honor prerequisite task dependencies.
339. **Report generation orchestrator** — fan out tasks then aggregate results.
340. **Long-running task monitor** — expose progress and cancellation state.
341. **Scheduler misfire simulator** — compare skip and catch-up policies.
342. **Maintenance-window scheduler** — block execution during prohibited periods.
343. **Business-calendar worker** — skip holidays and closed banking days.
344. **Distributed cron dashboard** — track execution across nodes.
345. **Retry exhaustion handler** — route permanently failed tasks for review.
346. **Job deduplication key service** — enforce exactly-one logical execution goal.
347. **Human approval workflow** — pause execution until an approver acts.
348. **Workflow compensation runner** — undo completed reversible steps.
349. **Scheduled external API poller** — persist cursors and avoid gaps.
350. **Quartz-backed production scheduler** — persist triggers and cluster coordination.

### Track 15: Java Concurrency, Async, and Reactive Foundations (Advanced)

351. **CompletableFuture fan-out API** — call three suppliers concurrently.
352. **Timeout-aware aggregator** — return partial results with clear semantics.
353. **Bounded executor demonstration** — protect memory under load.
354. **Thread-pool tuning benchmark** — measure queueing and saturation.
355. **Virtual-thread HTTP service** — compare blocking throughput and resource use.
356. **Async email dispatcher** — decouple requests from slow delivery.
357. **Race-condition counter lab** — demonstrate atomic versus unsafe increments.
358. **Concurrent inventory processor** — guarantee correct shared updates.
359. **Semaphore-limited API client** — cap parallel outbound calls.
360. **Scheduled future cancellation demo** — cancel safely under load.
361. **Reactive WebFlux catalog** — expose Mono and Flux endpoints.
362. **Reactive streaming price feed** — stream server-sent events.
363. **Reactive database access demo** — use R2DBC and explicit transactions.
364. **Reactive versus MVC comparison** — benchmark equivalent workloads.
365. **Backpressure laboratory** — test slow subscribers and bounded buffers.
366. **Reactor error recovery demo** — use fallback and retry operators.
367. **Reactive context propagation app** — preserve trace identifiers.
368. **Blocking-call detector** — find accidental blocking in event loops.
369. **Parallel file checksum service** — process multiple large files concurrently.
370. **Bulkhead isolation lab** — keep slow partners from starving fast ones.
371. **Structured concurrency prototype** — coordinate child tasks where supported.
372. **Actor-like mailbox worker** — serialize each account's commands.
373. **Concurrent cache stampede demo** — coalesce duplicate loading work.
374. **WebClient response streaming** — process large responses incrementally.
375. **Async transaction boundary experiment** — show context limitations explicitly.

### Track 16: Apache Kafka and Event Streaming (Advanced)

376. **Kafka producer playground** — publish structured domain events.
377. **Kafka consumer worker** — process partitioned events and commit offsets.
378. **Order-created event stream** — decouple checkout and fulfillment.
379. **Consumer group lab** — scale readers across partitions.
380. **Partition-key ordering app** — guarantee order for a given customer key.
381. **Event schema registry demo** — validate Avro or Protobuf evolution.
382. **Kafka JSON compatibility tester** — detect malformed payloads.
383. **Retry-topic implementation** — separate transient failures from poison records.
384. **Dead-letter topic inspector** — triage messages that cannot be processed.
385. **Idempotent Kafka consumer** — ignore previously completed event IDs.
386. **Transactional producer experiment** — study broker-level guarantees.
387. **Kafka outbox relay** — publish committed SQL outbox records.
388. **CDC event feed** — route database changes through Debezium.
389. **Inventory projection consumer** — build a read model from events.
390. **Event replay tool** — rebuild projections from a historical offset.
391. **Lag monitoring service** — alert on growing consumer delay.
392. **Partition rebalance test** — measure processing during worker churn.
393. **Large-message redesign** — replace payload transfer with references.
394. **Event ordering anomaly simulator** — handle cross-partition races.
395. **Kafka Streams aggregation** — calculate rolling order totals.
396. **Windowed fraud counter** — count attempts over time windows.
397. **Compacted topic profile store** — reconstruct latest key state.
398. **Schema breaking-change simulator** — enforce compatibility checks.
399. **Kafka outage recovery drill** — absorb broker interruption and resume.
400. **Event-driven order pipeline** — run checkout, payment, shipping, and projections.

### Track 17: RabbitMQ, AMQP, and Work Messaging (Advanced)

401. **RabbitMQ hello producer-consumer** — publish and acknowledge messages.
402. **Work queue processor** — distribute jobs among competing consumers.
403. **Direct exchange routing service** — route by exact routing key.
404. **Topic exchange notification bus** — route wildcard categories.
405. **Fanout audit broadcaster** — copy events to multiple queues.
406. **Durable queue recovery demo** — restart broker without losing durable messages.
407. **Publisher confirms lab** — detect broker acceptance failures.
408. **Consumer acknowledgement simulator** — requeue before successful processing.
409. **Negative acknowledgement policy** — distinguish discard from retry.
410. **Dead-letter exchange handler** — quarantine poison jobs.
411. **TTL-delayed message workflow** — execute actions after expiry.
412. **Priority queue experiment** — benchmark priority under competing loads.
413. **Prefetch tuning lab** — protect slow consumers against overload.
414. **Idempotent invoice worker** — deduplicate redelivered commands.
415. **Request-reply over AMQP** — correlate asynchronous replies.
416. **Notification dispatch router** — select email, SMS, and push workers.
417. **Image thumbnail queue** — scale CPU-bound transformations.
418. **Document conversion farm** — handle heterogeneous file jobs.
419. **Broker outage resilience app** — buffer and recover publisher requests.
420. **Message tracing demo** — correlate end-to-end asynchronous work.
421. **Retry with exponential delay queues** — avoid hot-loop retries.
422. **Poison message investigation console** — inspect safely sanitized payloads.
423. **Saga command dispatcher** — exchange workflow commands and outcomes.
424. **Message contract migration demo** — run old and new consumers together.
425. **Enterprise task queue** — combine observability, retries, DLQ, and scaling.

### Track 18: Redis, Caching, and Rate Limiting (Advanced)

426. **Cache-aside product API** — load and cache frequently read products.
427. **TTL configuration laboratory** — expire different data categories correctly.
428. **Cache invalidation on update** — prevent stale product details.
429. **Cached pagination API** — evaluate safe cache key construction.
430. **Cache stampede protection** — single-flight hot-key misses.
431. **Negative-result caching** — avoid repeated nonexistent lookups.
432. **Redis-backed HTTP session store** — share sessions across replicas.
433. **Fixed-window request limiter** — count per-client calls.
434. **Sliding-window rate limiter** — smooth burst enforcement.
435. **Token-bucket rate limiter** — enforce burst and sustained quotas.
436. **Distributed concurrency semaphore** — protect scarce downstream capacity.
437. **Redis leaderboard** — use sorted sets for rank and score.
438. **Redis shopping cart** — expire abandoned carts with persistence tradeoffs.
439. **Presence tracker** — maintain online-user heartbeat state.
440. **Atomic inventory counter lab** — compare Redis and SQL truth.
441. **Redis pub/sub notification** — observe ephemeral delivery semantics.
442. **Redis Streams task consumer** — use consumer groups and acknowledgments.
443. **Cache warmup service** — preload popular keys after deploy.
444. **Key eviction experiment** — compare policies under memory pressure.
445. **Hot-key detection service** — identify concentrated read demand.
446. **Multi-level cache app** — combine local and remote caching.
447. **Tenant-aware cache isolation** — prevent key collisions across tenants.
448. **Distributed lock correctness lab** — study lease expiry and fencing.
449. **Cache consistency analyzer** — compare authoritative and cached values.
450. **Failure-tolerant cache client** — keep the API working when cache is down.

### Track 19: Distributed Systems Fundamentals (Advanced)

451. **Two-node counter service** — explore conflicting distributed updates.
452. **Request deduplication gateway** — suppress repeated command execution.
453. **Idempotent payment-command simulator** — persist responses by request key.
454. **Distributed clock-ordering lab** — compare timestamps and logical clocks.
455. **Leader election experiment** — lease one coordinator among replicas.
456. **Heartbeat failure detector** — classify suspected node failures.
457. **Quorum write demonstration** — model replica acknowledgement thresholds.
458. **Eventual consistency simulator** — expose stale reads under replication delay.
459. **Read-your-writes session demo** — route or wait for fresh reads.
460. **Network partition harness** — cut connections between collaborating services.
461. **Retry storm simulator** — show load amplification during outage.
462. **Duplicate-message lab** — preserve effects under at-least-once delivery.
463. **Out-of-order event handler** — reconcile versions and sequence numbers.
464. **Clock skew expiry lab** — test security and scheduling assumptions.
465. **Split-brain lock experiment** — explore fencing-token protection.
466. **Consistent hashing router** — redistribute key ownership after node changes.
467. **Distributed state machine** — replicate deterministic commands.
468. **Gossip membership prototype** — spread peer availability information.
469. **Distributed snapshot demo** — capture coherent multi-worker diagnostics.
470. **Distributed rate quota service** — enforce global quotas approximately or strictly.
471. **CAP tradeoff simulator** — document availability and consistency behavior.
472. **Backpressure across services** — propagate overload signals upstream.
473. **Regional failover demo** — switch traffic and handle duplicate requests.
474. **Distributed correctness testbed** — inject delays, drops, duplicates, and reorders.
475. **Failure-mode handbook service** — catalog and demonstrate recovery behaviors.

### Track 20: Microservice Design and Decomposition (Advanced)

476. **User service** — own profiles and public user identifiers.
477. **Catalog service** — own product records and search metadata.
478. **Cart service** — own transient shopping selections.
479. **Pricing service** — calculate discounts without owning orders.
480. **Order service** — manage order lifecycle and invariants.
481. **Inventory service** — reserve and release stock independently.
482. **Payment orchestration service** — coordinate external payment status.
483. **Shipping service** — create shipments and track delivery states.
484. **Notification service** — subscribe to domain changes asynchronously.
485. **Review service** — validate ownership and publish ratings.
486. **API gateway prototype** — route calls and apply shared request policies.
487. **Service discovery lab** — resolve changing internal service locations.
488. **Centralized configuration demo** — distribute approved environment settings.
489. **Backend-for-frontend API** — aggregate mobile-specific response shapes.
490. **Domain boundary refactoring** — split a monolith along business capabilities.
491. **Database-per-service demonstrator** — prevent cross-service table ownership.
492. **Synchronous versus event-driven checkout** — compare coupling explicitly.
493. **Service contract registry** — catalog ownership and API versions.
494. **Internal authorization service** — centralize policy decisions without bypassing checks.
495. **Service dependency graph** — discover call chains from trace data.
496. **Anti-corruption layer adapter** — isolate a legacy customer model.
497. **Strangler migration exercise** — redirect one legacy route at a time.
498. **Cross-service reporting projection** — combine events without joining service databases.
499. **Microservice cost dashboard** — count operational and latency overhead.
500. **Reunification exercise** — merge over-split services into a modular monolith.

### Track 21: Resilience and Fault-Tolerant Service Calls (Advanced)

501. **Connect-timeout laboratory** — fail fast on unreachable hosts.
502. **Read-timeout laboratory** — cap stalled response waits.
503. **Retry classifier** — retry transient errors but not most 4xx responses.
504. **429 Retry-After handler** — respect server-declared delay hints.
505. **Resilience4j retry service** — configure backoff, jitter, and limits.
506. **Circuit-breaker testbed** — observe closed, open, and half-open behavior.
507. **Bulkhead isolation prototype** — partition resources by dependency.
508. **Time-limiter wrapper** — bound completion time and cancellation expectations.
509. **Fallback semantics demo** — return degraded data without hiding failures.
510. **Retry budget policy** — cap retries across a fleet.
511. **Cascading failure simulator** — model an overloaded dependency chain.
512. **Partial response aggregator** — succeed with explicitly missing sections.
513. **Partner failover client** — rotate endpoints using validated rules.
514. **DNS change recovery lab** — test stale connection resolution.
515. **Connection-pool exhaustion demo** — measure contention and mitigation.
516. **Rate-limit response service** — return 429 with clear quota metadata.
517. **Admission control gateway** — reject work before queue collapse.
518. **Load-shedding recommendation API** — preserve critical endpoints under pressure.
519. **Hedged request experiment** — compare reduced tail latency with extra load.
520. **Outage-aware task queue** — delay jobs while a dependency is unhealthy.
521. **Graceful dependency shutdown** — drain work on termination.
522. **Compensation error handler** — record incomplete rollback attempts.
523. **Recovery probe dashboard** — expose resilience state and event history.
524. **Fault injection proxy** — emulate loss, jitter, and half-open connections.
525. **One-call daily scheduler hardening** — ensure retries, alerts, and replay safety.

### Track 22: Protocols and External System Integration (Advanced)

526. **RestClient partner connector** — implement typed outbound HTTP requests.
527. **WebClient partner connector** — make nonblocking HTTP calls.
528. **HTTP proxy integration** — respect corporate routing and credentials.
529. **HTTP/2 multiplexing experiment** — compare concurrent request behavior.
530. **gRPC greeting service** — define unary Protobuf contracts.
531. **gRPC streaming telemetry** — implement server streaming and backpressure.
532. **gRPC deadline propagator** — enforce caller deadlines through services.
533. **GraphQL product API** — fetch nested data in one query.
534. **GraphQL mutation service** — handle validation and domain errors.
535. **GraphQL DataLoader experiment** — eliminate N+1 resolver access.
536. **SOAP contract-first adapter** — consume a legacy WSDL service.
537. **XML signature verification sandbox** — validate signed integration payloads.
538. **SFTP inbound connector** — resume and verify secure file downloads.
539. **SFTP outbound delivery worker** — stage and atomically publish files.
540. **Webhook delivery engine** — sign, retry, and log outbound notifications.
541. **Webhook receiver** — authenticate senders and deduplicate deliveries.
542. **Third-party SDK wrapper** — hide supplier-specific error models.
543. **EDI message translator** — map a sample business interchange format.
544. **HL7 FHIR client prototype** — retrieve synthetic healthcare resources.
545. **ISO 20022 parser** — validate sample payment message structures.
546. **GraphQL federation exploration** — combine ownership-aware schemas.
547. **HTTP content compression demo** — benchmark compressed and raw payloads.
548. **HTTP cache proxy** — honor cache directives and validators.
549. **Callback-to-polling adapter** — reconcile webhooks with status polling.
550. **Enterprise integration hub** — normalize REST, SOAP, SFTP, and events.

### Track 23: Files, Object Storage, and Document Pipelines (Advanced)

551. **Secure profile image upload** — validate MIME, size, and content.
552. **Resumable upload API** — assemble chunked parts with integrity checks.
553. **S3-compatible object uploader** — write objects and metadata.
554. **Presigned URL service** — grant short-lived direct download access.
555. **File antivirus pipeline** — quarantine until scan completion.
556. **Image thumbnail processor** — produce bounded derivative sizes.
557. **PDF invoice generator** — render data into consistent invoices.
558. **Spreadsheet report exporter** — write large XLSX files efficiently.
559. **CSV dialect detector** — handle delimiters, encoding, and quotes.
560. **CSV validation gateway** — reject schema and data inconsistencies.
561. **Object version browser** — list and restore prior document versions.
562. **File checksum registry** — detect duplicate and corrupted uploads.
563. **Multipart streaming download** — avoid loading giant files into memory.
564. **File range response server** — serve partial downloads correctly.
565. **Archive extraction sandbox** — prevent traversal and decompression bombs.
566. **Document approval workflow** — track versions and review state.
567. **Retention-based object deletion** — apply lifecycle policies safely.
568. **Private document sharing** — issue revocable time-bound links.
569. **File processing event bus** — emit scan, transform, and publish states.
570. **Large-data export worker** — generate asynchronous reports in object storage.
571. **NAS file drop connector** — write to a safely mounted network path.
572. **IBM Sterling handoff simulator** — publish and reconcile transfer manifests.
573. **Transfer acknowledgement reconciler** — distinguish sent from delivered.
574. **File integrity audit** — verify source and destination hashes.
575. **Production document exchange** — validate, transform, transfer, retry, and audit.

### Track 24: Search, Indexing, and Recommendations (Advanced)

576. **Book keyword search** — index title, author, and tags.
577. **Product facet search** — filter by category, brand, and price.
578. **Autocomplete suggestion API** — return prefix-based candidates.
579. **Typo-tolerant name search** — configure fuzzy matching.
580. **Search relevance playground** — boost meaningful fields.
581. **Elasticsearch indexing pipeline** — project SQL changes into search.
582. **OpenSearch alternative implementation** — compare search engine behavior.
583. **Search reindex coordinator** — rebuild indexes without downtime.
584. **Alias-based index cutover** — atomically switch search generations.
585. **Search pagination lab** — compare deep offsets with search_after.
586. **Synonym expansion service** — support domain vocabulary.
587. **Multilingual analyzer search** — tokenize multiple languages correctly.
588. **Geospatial business search** — rank by distance and relevance.
589. **Search query analytics** — record anonymized no-result queries.
590. **Trending topic calculator** — blend recency and query frequency.
591. **Popular products recommender** — rank using aggregate activity.
592. **Collaborative-filtering prototype** — suggest items from user-item patterns.
593. **Content-based article suggestions** — compare metadata similarity.
594. **Recently viewed personalization** — maintain bounded interaction history.
595. **Hybrid keyword-vector search** — blend lexical and semantic recall.
596. **Vector similarity API** — store embeddings and query top neighbors.
597. **Search permissions filter** — prevent unauthorized documents from appearing.
598. **Search freshness monitor** — quantify lag behind primary SQL data.
599. **Search performance load test** — tune analyzers, caches, and shards.
600. **Marketplace discovery backend** — combine facets, geo, ranking, and suggestions.

### Track 25: Logging, Metrics, Tracing, and Observability (Advanced)

601. **Structured log schema** — emit consistent JSON operational events.
602. **Request-ID propagation filter** — link inbound and outbound logs.
603. **Sensitive-field logging guard** — mask tokens and personal fields.
604. **Micrometer request metrics** — expose latency and request counts.
605. **Custom business metrics** — count approved and rejected transactions.
606. **Prometheus scrape demo** — expose standard metrics endpoints.
607. **Grafana service dashboard** — graph rate, errors, and latency.
608. **OpenTelemetry trace exporter** — collect end-to-end spans.
609. **Distributed tracing checkout** — follow calls across three services.
610. **Trace-to-log correlation** — navigate one request across signals.
611. **Database query metrics exporter** — track pool waits and slow calls.
612. **Kafka consumer lag dashboard** — observe backlog and processing delay.
613. **Scheduler execution metrics** — track duration, outcome, and overdue runs.
614. **Resilience4j telemetry** — visualize retries, breaker events, and fallbacks.
615. **SLI calculator service** — compute availability and latency indicators.
616. **SLO burn-rate alert demo** — distinguish fast and slow budget burn.
617. **Alert deduplication router** — suppress repeated duplicate incidents.
618. **On-call diagnostic endpoint** — show safe readiness and dependency state.
619. **Health versus readiness lab** — avoid routing traffic to unready pods.
620. **Business-funnel observability** — link API events to customer outcomes.
621. **Cardinality explosion simulator** — avoid unbounded metric labels.
622. **Sampling strategy lab** — compare head and tail sampling.
623. **Log retention policy service** — manage volume and privacy.
624. **Synthetic API monitor** — periodically test external behavior.
625. **Production operations console** — unify dashboards, traces, alerts, and runbooks.

### Track 26: Performance Engineering and JVM Tuning (Advanced)

626. **Baseline load test** — establish request throughput and latency.
627. **p50-versus-p99 analysis** — inspect tail behavior under load.
628. **JVM heap pressure lab** — reproduce and diagnose allocation spikes.
629. **Garbage collection benchmark** — compare GC settings on one workload.
630. **Heap dump leak hunt** — track intentionally retained objects.
631. **Thread dump incident lab** — diagnose blocked and waiting threads.
632. **JFR profiling project** — capture CPU and allocation profiles.
633. **Database pool right-sizing** — correlate threads with SQL wait time.
634. **HTTP connection reuse benchmark** — compare pooled and fresh clients.
635. **JSON serialization benchmark** — optimize large response encoding.
636. **Compression tradeoff test** — measure CPU versus network savings.
637. **Memory-safe CSV streaming** — export millions of rows incrementally.
638. **Query index tuning benchmark** — quantify improvement against a baseline.
639. **Batch size optimizer** — tune throughput without long locks.
640. **CPU versus I/O concurrency lab** — choose suitable executors.
641. **Load-adaptive queue** — reject before worker backlog explodes.
642. **Virtual threads versus platform threads** — benchmark I/O-heavy endpoints.
643. **Reactive versus servlet performance** — evaluate using realistic load.
644. **In-memory cache versus remote cache** — measure latency distributions.
645. **JMH business-rule microbenchmark** — avoid misleading microbenchmarks.
646. **Startup-time analyzer** — remove unnecessary initialization work.
647. **Native image startup demo** — evaluate AOT and GraalVM tradeoffs.
648. **Response payload optimizer** — reduce overfetching and serialization.
649. **Performance regression gate** — compare CI load-test baselines.
650. **Capacity planning simulator** — estimate replicas using measured service demand.

### Track 27: Docker, Kubernetes, and Cloud Operations (Advanced)

651. **Dockerized REST API** — package Spring Boot with a minimal runtime image.
652. **Docker Compose stack** — run application, PostgreSQL, and Redis.
653. **Multi-stage image builder** — separate compile and runtime layers.
654. **Container secret injection** — load credentials from external configuration.
655. **Kubernetes deployment** — run and expose a stateless API.
656. **Kubernetes ConfigMap and Secret app** — externalize environment settings.
657. **Kubernetes readiness probe** — prevent early traffic to an unready pod.
658. **Kubernetes liveness drill** — recover a stuck process safely.
659. **Horizontal pod autoscaler lab** — scale with realistic metrics.
660. **Resource requests and limits benchmark** — understand throttling and OOM.
661. **Rolling deployment exercise** — replace replicas without customer downtime.
662. **Canary deployment lab** — progressively shift traffic to new versions.
663. **Blue-green deployment lab** — perform reversible environment cutovers.
664. **Kubernetes CronJob exporter** — run a scheduled report container.
665. **Kubernetes Job retry demonstration** — make job execution idempotent.
666. **Persistent volume exercise** — store durable generated artifacts.
667. **Ingress TLS configuration** — terminate HTTPS and route hosts.
668. **Service mesh observability demo** — compare mesh and application telemetry.
669. **OpenShift deployment lab** — configure routes, probes, and restricted identities.
670. **Kubernetes NetworkPolicy exercise** — restrict east-west communications.
671. **Cloud object storage migration** — replace local files with object storage.
672. **Managed database connection demo** — connect securely to a cloud SQL instance.
673. **Infrastructure-as-code environment** — provision a reproducible test stack.
674. **Multi-environment promotion demo** — isolate dev, staging, and production.
675. **Kubernetes recovery exercise** — replace dead pods and validate safe restart.

### Track 28: CI/CD, Supply Chain, and Release Engineering (Advanced)

676. **Maven build pipeline** — compile, test, and package on every change.
677. **Gradle build pipeline** — cache artifacts and speed repeated builds.
678. **Pull request verification** — require unit and integration tests.
679. **Container image publishing** — tag and push immutable builds.
680. **Image vulnerability gate** — reject critical exposed dependencies.
681. **SBOM publication workflow** — attach dependency inventory to release.
682. **Dependency update bot exercise** — evaluate automated version upgrades.
683. **Artifact signature verifier** — confirm build provenance.
684. **Secret scanning pipeline** — prevent tokens from reaching repositories.
685. **Static analysis CI** — fail on agreed maintainability defects.
686. **API breaking-change gate** — block incompatible OpenAPI diffs.
687. **Database migration deployment stage** — validate schema before app cutover.
688. **Feature-flag release experiment** — decouple deployment from exposure.
689. **Automated staging smoke tests** — verify essential postdeploy behavior.
690. **Canary metrics gate** — stop rollout on adverse signals.
691. **One-click rollback pipeline** — restore application version safely.
692. **GitOps deployment repository** — reconcile desired cluster state.
693. **Helm package project** — template and validate Kubernetes resources.
694. **Release notes generator** — summarize changes from commits and issues.
695. **Build reproducibility verifier** — compare artifacts across clean builds.
696. **Ephemeral preview environment** — deploy isolated pull request versions.
697. **Performance regression CI** — detect slower critical endpoints.
698. **Disaster recovery pipeline** — automate restore verification in a sandbox.
699. **Multi-service coordinated release** — manage compatible schema and API changes.
700. **Production release simulation** — combine approvals, rollout, monitoring, and rollback.

### Track 29: Distributed Data, Consistency, and Transactions (Advanced)

701. **Atomic order insert-and-outbox** — coordinate SQL and event intent.
702. **Outbox publisher idempotency** — prevent duplicate business effects.
703. **Inbox pattern consumer** — record processed external messages.
704. **Inventory reservation saga** — hold and release stock with compensations.
705. **Checkout choreography saga** — react to autonomous domain events.
706. **Checkout orchestration saga** — coordinate steps with an explicit controller.
707. **Compensation idempotency lab** — retry cancellation safely.
708. **Payment-versus-order reconciliation** — detect split-brain business states.
709. **CQRS read-model builder** — derive query data asynchronously.
710. **CQRS lag dashboard** — expose read-model staleness to operators.
711. **Dual-write failure simulator** — prove why naive state/event writes diverge.
712. **Transactional messaging comparison** — document SQL, broker, and end-to-end guarantees.
713. **Distributed lock fencing example** — reject work from expired lease holders.
714. **Reservation expiry worker** — release abandoned holds consistently.
715. **Optimistic workflow versioning** — reject stale distributed commands.
716. **Conflict resolution service** — reconcile competing concurrent changes.
717. **Eventually consistent counter** — merge commutative updates.
718. **Multi-region order read model** — expose region replication lag.
719. **Read-repair processor** — compare replicas and correct divergences.
720. **Cross-store delete workflow** — coordinate erasure among services.
721. **Cross-service uniqueness coordinator** — reserve scarce global identifiers.
722. **Transactional audit publishing** — prevent committed events from being lost.
723. **Distributed invariant monitor** — verify aggregate totals continuously.
724. **Saga timeout recovery** — resume incomplete processes after restart.
725. **Transaction correctness suite** — stress concurrency, retries, and failure injection.

### Track 30: Time, Events, and Workflow State Machines (Advanced)

726. **Domain event taxonomy** — distinguish facts from commands.
727. **Event envelope library** — standardize IDs, timestamps, and versions.
728. **Event version upcaster** — read older events into a new model.
729. **Event-sourced bank account** — rebuild balances from append-only events.
730. **Event snapshotting experiment** — reduce expensive aggregate replays.
731. **Event-sourced shopping cart** — compare streams with conventional SQL state.
732. **Event correction workflow** — append corrections instead of rewriting history.
733. **Event replay isolation** — prevent replays from resending real emails.
734. **Event timeline inspector** — present a per-aggregate chronological history.
735. **State machine order service** — enforce legal status transitions.
736. **Loan application workflow** — track review, approval, and rejection.
737. **Insurance claim workflow** — model deadlines and human decisions.
738. **Subscription lifecycle engine** — schedule renewals, pauses, and cancellations.
739. **Workflow timer service** — persist and recover deadline events.
740. **Business time calculator** — handle weekends, holidays, and cutoffs.
741. **Timezone and DST lab** — test skipped and repeated local times.
742. **Temporal validity API** — query values at an effective date.
743. **Bitemporal price-history experiment** — distinguish valid and recorded time.
744. **Long-running approval orchestration** — resume after process restart.
745. **Workflow version migration** — evolve definitions with active instances.
746. **Audit replay versus event replay** — compare business and diagnostic history.
747. **Event causation tracker** — link originating commands to downstream events.
748. **Late-arriving event reconciler** — handle messages after business cutoff.
749. **State transition fuzz tester** — explore invalid and repeated commands.
750. **Event-sourced workflow engine capstone** — replay, snapshot, compensate, and observe.

### Track 31: Banking, Payments, and Financial Systems (Advanced)

751. **Double-entry ledger API** — require every journal entry to balance.
752. **Account opening backend** — manage application and approval states.
753. **Beneficiary management service** — validate ownership and change controls.
754. **Bank transfer simulator** — debit and credit atomically within one ledger.
755. **Cross-bank transfer workflow** — coordinate asynchronous settlement states.
756. **Payment initiation service** — persist an idempotent payment instruction.
757. **Payment callback receiver** — authenticate and deduplicate status events.
758. **Payment status reconciler** — compare internal state with provider truth.
759. **Refund management engine** — cap refunds at captured amounts.
760. **Partial capture simulator** — handle authorization and multiple captures.
761. **Card authorization sandbox** — approve, decline, and reverse synthetic holds.
762. **Merchant settlement calculator** — compute payable balances and fees.
763. **Fee and tax engine** — use decimal-safe monetary arithmetic.
764. **Interest calculation engine** — handle day-count conventions and rounding.
765. **Loan amortization service** — generate schedules and apply payments.
766. **Loan delinquency workflow** — track days past due and status changes.
767. **KYC case-management sandbox** — process synthetic identity evidence.
768. **AML rules simulator** — flag synthetic suspicious transaction patterns.
769. **Fraud velocity detector** — evaluate repeated transfers and thresholds.
770. **Sanctions screening prototype** — match synthetic names with explainable results.
771. **Bank statement generator** — produce consistent period snapshots.
772. **Daily core-banking reconciliation** — investigate missing and duplicated postings.
773. **End-of-day banking scheduler** — enforce cutoffs and rerun safety.
774. **Financial data audit vault** — make corrections attributable and traceable.
775. **Digital banking backend capstone** — combine identity, ledger, transfer, fraud, and audit.

### Track 32: SaaS, Multi-Tenancy, and Identity Platforms (Advanced)

776. **SaaS organization registry** — provision organizations and administrators.
777. **Shared-schema tenant service** — enforce row-level tenant isolation.
778. **Schema-per-tenant application** — route SQL connections to tenant schemas.
779. **Database-per-tenant prototype** — provision and migrate separate databases.
780. **Tenant onboarding workflow** — configure plans, defaults, and billing.
781. **Tenant suspension engine** — freeze access without deleting records.
782. **Tenant offboarding export** — package customer-owned data for retrieval.
783. **Workspace member invites** — validate expiring, one-use invitations.
784. **Organization role management** — define owner, admin, editor, and reader permissions.
785. **Custom role designer** — create scoped permissions per organization.
786. **Tenant feature entitlement service** — gate modules by subscription plan.
787. **Per-tenant rate limit controller** — apply fair-use budgets.
788. **Per-tenant encryption key interface** — segregate logical key management.
789. **Tenant data residency router** — direct storage to approved regions.
790. **SaaS usage metering** — collect billable API and storage activity.
791. **Seat-based subscription manager** — enforce licensed member counts.
792. **Usage-based invoicing** — calculate charges from deduplicated meters.
793. **Trial expiration workflow** — notify and transition account access.
794. **Delegated support access** — require consent and audit expiry.
795. **Organization SSO login** — map identity-provider groups to memberships.
796. **SCIM provisioning receiver** — create and deactivate enterprise users.
797. **Tenant-aware job scheduler** — prevent one tenant monopolizing workers.
798. **Noisy-neighbor protection** — isolate resources for heavy tenants.
799. **Tenant migration orchestrator** — move one tenant without losing writes.
800. **Enterprise SaaS control plane** — combine provisioning, metering, isolation, and SSO.

### Track 33: Real-Time Apps, Collaboration, and Notifications (Advanced)

801. **WebSocket echo service** — establish authenticated bidirectional connections.
802. **Live chat backend** — persist rooms, participants, and messages.
803. **Chat read receipts** — track per-user delivery and read state.
804. **Presence heartbeat service** — distinguish online from recently active.
805. **Typing indicator channel** — expire transient events automatically.
806. **Server-sent event stock ticker** — broadcast one-way updates.
807. **Collaborative task board** — push card movements to connected clients.
808. **Optimistic collaboration API** — reject conflicting record edits.
809. **Operational transform sandbox** — merge simple concurrent text edits.
810. **CRDT-based notes prototype** — reconcile independently edited content.
811. **Live polling backend** — broadcast updated vote counts.
812. **Multiplayer turn manager** — validate ordered player actions.
813. **Auction bidding backend** — handle concurrency and closing deadlines.
814. **Sports score fan-out simulator** — publish feed updates to subscriptions.
815. **Real-time shipment tracking** — stream synthetic delivery coordinates.
816. **Push notification gateway** — manage device tokens and retries.
817. **Email template and dispatch backend** — support preview and suppression lists.
818. **SMS delivery status processor** — reconcile callbacks and failed sends.
819. **Notification preference center** — honor channel-level user choices.
820. **Digest notification builder** — coalesce many events into one summary.
821. **Notification quiet-hours engine** — respect recipient timezone and urgency.
822. **WebSocket cluster broadcast** — distribute messages across replicas.
823. **Offline inbox sync** — catch clients up after reconnecting.
824. **Real-time moderation pipeline** — apply rule-based checks before publish.
825. **Collaborative workspace capstone** — combine chat, presence, updates, and persistence.

### Track 34: Privacy, Compliance, Governance, and Auditing (Advanced)

826. **Immutable-style audit event service** — record actor, action, and context.
827. **Tamper-evident audit log** — hash-chain event records and verify integrity.
828. **Data classification API** — tag sensitive and nonsensitive attributes.
829. **Field-level encryption demonstrator** — encrypt selected application values.
830. **Envelope encryption integration** — use a key-management boundary.
831. **Secret rotation service** — roll credentials without downtime.
832. **User consent registry** — record specific consent versions and timestamps.
833. **Consent withdrawal workflow** — stop future processing when withdrawn.
834. **Right-to-access export service** — gather subject records across modules.
835. **Erasure request orchestrator** — coordinate deletion with lawful retention exceptions.
836. **Data retention engine** — age out records according to policy.
837. **Legal hold manager** — prevent policy deletion when preservation is required.
838. **Pseudonymization pipeline** — replace identifiers with restricted tokens.
839. **Token vault sandbox** — separate sensitive raw values from references.
840. **Data lineage tracker** — record source and transformation steps.
841. **Sensitive access review** — report who accessed protected resources.
842. **Four-eyes approval service** — require independent maker and checker.
843. **Segregation-of-duties validator** — block conflicting permission grants.
844. **Regulatory report assembler** — produce traceable synthetic compliance extracts.
845. **Evidence collection API** — attach test and approval records to controls.
846. **Dependency license inventory** — report distribution restrictions.
847. **Security incident timeline** — correlate changes, alerts, and impacted records.
848. **Backup access control project** — protect restoration credentials and access.
849. **Privacy impact workflow** — document data flows, purpose, and safeguards.
850. **Audit-ready enterprise backend** — unify retention, consent, review, and evidence.

### Track 35: Architecture Patterns and Maintainability (Advanced)

851. **Layered CRUD refactor** — separate controller, service, and persistence concerns.
852. **Hexagonal architecture catalog** — isolate ports from adapters.
853. **Clean architecture task service** — enforce inward dependency rules.
854. **Domain-driven order aggregate** — preserve invariants through methods.
855. **Value object library** — model money, email, and identifiers immutably.
856. **Specification-pattern search engine** — compose business filter rules.
857. **Strategy-pattern pricing engine** — swap discount algorithms by policy.
858. **Chain-of-responsibility notification pipeline** — run pluggable delivery handlers.
859. **Factory-based provider connector** — select integrations safely.
860. **Template-method import processor** — share file workflow skeletons.
861. **Decorator-based HTTP client** — add metrics, logging, and caching.
862. **Adapter-pattern legacy bridge** — translate inconsistent partner data.
863. **Facade-pattern checkout interface** — simplify multistep business APIs.
864. **Observer-pattern domain events** — notify in-process subscribers.
865. **Command-pattern job executor** — encapsulate replayable operations.
866. **State-pattern loan application** — centralize valid transitions.
867. **Repository abstraction comparison** — test persistence substitution tradeoffs.
868. **Anti-corruption layer CRM bridge** — protect domain language from vendor models.
869. **Spring Modulith order system** — verify module boundaries and events.
870. **Modulith event publication recovery** — replay failed internal event delivery.
871. **Architecture fitness tests** — enforce dependency directions with ArchUnit.
872. **Feature-sliced package architecture** — group code by domain behavior.
873. **Legacy monolith extraction lab** — isolate one bounded context first.
874. **Plugin-style rule engine** — load approved business-rule modules.
875. **Reference backend architecture** — document tradeoffs, ADRs, and boundaries.

### Track 36: Networking, Infrastructure, and Platform Fundamentals (Advanced)

876. **TCP echo gateway in Java** — inspect sockets and framing before HTTP.
877. **HTTP protocol laboratory** — capture and analyze raw requests and responses.
878. **TLS certificate explorer** — inspect chain, hostname, and expiry checks.
879. **Mutual TLS reverse proxy** — verify client identity before forwarding.
880. **DNS caching and failure demo** — observe resolution TTL and outages.
881. **NAT and load-balancer testbed** — trace client address forwarding safely.
882. **Reverse proxy Spring stack** — configure Nginx and trusted forwarded headers.
883. **Connection keep-alive test** — measure setup overhead and reuse.
884. **HTTP request smuggling defense lab** — test conflicting framing via hardened proxy.
885. **Slow-client protection demo** — enforce timeouts and request limits.
886. **Network bandwidth profiler** — compare payload shapes on constrained links.
887. **gRPC versus REST benchmark** — measure serialization and connection costs.
888. **Linux process signal handler** — observe SIGTERM and graceful shutdown.
889. **File descriptor leak sandbox** — monitor sockets and open-file pressure.
890. **Linux permissions exercise** — deploy as nonroot with least privilege.
891. **Container namespace explorer** — inspect process and network isolation.
892. **Service-to-service firewall lab** — restrict allowed outgoing destinations.
893. **Internal DNS service registry** — track service target changes.
894. **TLS termination comparison** — test edge versus application termination.
895. **Certificate renewal simulation** — rotate without breaking live traffic.
896. **Distributed configuration drift checker** — detect inconsistent service settings.
897. **Infrastructure resource monitor** — expose CPU, memory, and filesystem alerts.
898. **Network fault laboratory** — reproduce packet loss and delayed responses.
899. **OpenShift security context demo** — design for nonroot and restricted policies.
900. **Platform readiness checker** — validate DNS, TLS, storage, and outbound access.

### Track 37: Large-Scale System Design Capstones (Expert)

901. **URL shortener** — generate collision-safe keys and high-read redirects.
902. **Paste sharing platform** — expire content and guard abuse.
903. **Social media feed** — fan out posts and paginate timelines.
904. **Photo sharing backend** — combine uploads, feeds, and visibility rules.
905. **Video platform API** — coordinate uploads, encoding jobs, and playback metadata.
906. **Food delivery platform** — orchestrate restaurants, orders, and couriers.
907. **Ride-hailing dispatch** — match drivers using geospatial proximity.
908. **Hotel booking engine** — prevent double booking under contention.
909. **Airline seat reservation** — hold, expire, and confirm scarce seats.
910. **Ticketing sale backend** — control spikes and fair queue admission.
911. **E-commerce marketplace** — isolate catalog, orders, payments, and merchants.
912. **Warehouse management system** — reconcile stock movements and locations.
913. **Logistics tracking system** — process shipment events and route milestones.
914. **Learning management backend** — enrollments, progress, and assessments.
915. **Healthcare appointment system** — secure slots, reminders, and records.
916. **Online examination platform** — timed attempts and answer submissions.
917. **Digital library** — lending, waitlists, discovery, and royalties.
918. **Cloud drive backend** — uploads, sharing, versions, and quotas.
919. **Collaborative wiki** — revision history, search, and access control.
920. **Ad impression tracker** — deduplicate events and aggregate usage.
921. **IoT device fleet** — ingest telemetry and manage device commands.
922. **Stock market paper-trading backend** — quotes, simulated fills, and portfolios.
923. **Expense-splitting platform** — net balances and track settlement history.
924. **Enterprise workflow automation** — rules, approvals, integrations, and audit.
925. **Global commerce capstone** — integrate inventory, checkout, payments, fraud, and operations.

### Track 38: Specialized Spring Ecosystem and Advanced Frameworks (Expert)

926. **Spring Integration file poller** — process files through message channels.
927. **Spring Integration HTTP-to-SFTP flow** — transform and route partner payloads.
928. **Spring Cloud Stream producer** — publish through a messaging binder.
929. **Spring Cloud Stream consumer** — change broker bindings via configuration.
930. **Spring Cloud Gateway filter** — enforce routing, auth, and quotas.
931. **Spring Cloud Config client** — consume centralized controlled configuration.
932. **Spring Cloud CircuitBreaker adapter** — compare framework abstractions.
933. **Spring Cloud Kubernetes integration** — consume platform configuration.
934. **Spring Authorization Server integration** — support a standards-based identity setup.
935. **Spring Session clustered login** — persist servlet sessions remotely.
936. **Spring HATEOAS hypermedia API** — advertise next valid actions.
937. **Spring Data JDBC aggregate** — compare explicit aggregate persistence with JPA.
938. **Spring Data R2DBC pipeline** — build reactive SQL flows.
939. **Spring GraphQL subscription API** — push domain event updates.
940. **Spring gRPC service integration** — expose protobuf contracts with Spring.
941. **Spring Web Services contract-first server** — serve a legacy SOAP interface.
942. **Spring Shell operations CLI** — inspect and trigger safe workflows.
943. **Spring Modulith application events** — isolate modules and recover publications.
944. **Spring Batch job repository operations** — restart and interrogate executions.
945. **Spring Kafka transactional consumer lab** — study commit and publish coordination.
946. **Spring for Apache Pulsar consumer** — explore an alternative event broker.
947. **Spring REST Docs suite** — publish documentation from tested examples.
948. **Spring AOT runtime demo** — measure ahead-of-time optimization.
949. **GraalVM native Spring Boot build** — audit reflection and resource configuration.
950. **Spring ecosystem compatibility matrix** — test upgrades across dependencies.

### Track 39: AI, Analytics, and Data Engineering Backends (Expert)

951. **LLM prompt gateway** — hide provider keys and meter request budgets.
952. **Streaming chatbot backend** — deliver token streams to clients.
953. **Spring AI chat integration** — call configured language model services.
954. **Document embedding importer** — chunk text and store vector embeddings.
955. **Retrieval-augmented Q&A API** — search documents before response generation.
956. **RAG citation verifier** — return source references with answers.
957. **Semantic search backend** — query embeddings with tenant filters.
958. **AI content moderation gateway** — combine deterministic and model-based rules.
959. **Prompt template registry** — version and audit prompt revisions.
960. **LLM response cache** — key outputs by safe contextual inputs.
961. **Token usage accounting** — aggregate costs by user and project.
962. **AI rate and concurrency limiter** — isolate expensive model workloads.
963. **Asynchronous document summarizer** — queue large summarization jobs.
964. **Speech transcription job API** — track uploaded audio processing.
965. **Image analysis workflow** — validate uploads and process responses.
966. **Model failover router** — switch providers based on health and policy.
967. **AI evaluation harness** — score responses against a fixed test dataset.
968. **Prompt injection defense prototype** — isolate untrusted retrieved content.
969. **Model output schema validator** — enforce structured response contracts.
970. **Human review queue for AI** — approve high-impact generated actions.
971. **Feature store API** — serve consistent online ML features.
972. **Batch feature engineering pipeline** — compute nightly aggregations.
973. **Real-time anomaly detection gateway** — score synthetic event streams.
974. **Experiment assignment backend** — run stable A/B allocation.
975. **AI-enabled support platform** — combine RAG, evaluations, human review, and audit.

### Track 40: Production Reliability, Disaster Recovery, and Expert Challenges (Expert)

976. **Broken deployment rescue** — diagnose an app that fails only in production profile.
977. **Memory leak incident** — reproduce, profile, patch, and verify.
978. **Thread starvation incident** — isolate synchronous blocking bottlenecks.
979. **Database deadlock incident** — locate cycles and fix lock ordering.
980. **Connection storm incident** — protect PostgreSQL from excessive clients.
981. **Cache outage exercise** — serve degraded but correct responses.
982. **Message broker outage drill** — buffer, recover, and reconcile delivery.
983. **Dependency 429 storm** — honor backoff and enforce retry budgets.
984. **Stale replica incident** — restore read consistency for critical requests.
985. **Malformed partner file incident** — quarantine input and generate correction report.
986. **Duplicate payment incident** — trace replay and apply idempotency repairs.
987. **Missing outbox event incident** — rebuild safe publication from committed records.
988. **Multi-region failover exercise** — route users and reconcile divergent writes.
989. **Backup-and-restore game day** — meet tested recovery objectives.
990. **Disaster recovery runbook** — automate verification after data restoration.
991. **Zero-downtime schema upgrade** — deploy old and new app versions together.
992. **Credential compromise response** — rotate secrets and revoke affected sessions.
993. **Dependency CVE emergency patch** — assess exposure and ship validated remediation.
994. **Traffic spike game day** — scale, shed load, and preserve key endpoints.
995. **Production trace investigation** — identify root cause across ten services.
996. **SLO ownership exercise** — define budgets and alert on meaningful failure.
997. **Incident postmortem generator** — capture timeline, impact, and corrective actions.
998. **Chaos engineering campaign** — test pod, network, database, and broker failures.
999. **Production readiness audit** — verify recovery, security, capacity, and observability.
1000. **Final banking-grade backend** — ship, test, deploy, break, recover, and document a full system.

## Suggested mastery checkpoints

- **After 1–100:** confidently build and explain Spring MVC endpoints, validation, configuration, repositories, and entity relationships.
- **After 101–250:** understand SQL constraints, transactions, concurrency anomalies, persistence choices, Spring Security, OAuth2 and token validation.
- **After 251–400:** be able to write reliable tests, build batch/scheduled workflows, reason about asynchronous execution, and publish/consume events.
- **After 401–550:** design resilient integrations, caches, microservices and distributed workflows with clear failure behavior.
- **After 551–750:** ship a service with storage, search, metrics, tracing, load tests, deployment automation, and eventual consistency patterns.
- **After 751–1000:** solve domain-specific banking/SaaS challenges, perform incident drills, and complete several production-grade capstones.

## Portfolio strategy

Keep focused laboratories small. Build a small number of bigger capstone applications to showcase end-to-end backend capability. For each major capstone, document requirements, API contracts, database schema, consistency boundaries, threat model, concurrency test, failure scenarios, dashboards, performance results, CI/CD deployment and recovery procedure.

**Recommended starting sequence:** 1 → 6 → 23 → 26 → 31 → 51 → 76 → 101 → 126 → 201 → 251 → 276 → 301 → 326 → 351 → 376 → 426 → 451 → 501 → 601 → 651 → 676 → 751 → 976 → 1000.

## Primary references

- Spring Boot: https://docs.spring.io/spring-boot/reference/
- Spring Security: https://docs.spring.io/spring-security/reference/
- Spring projects index: https://spring.io/projects/
- Spring guides: https://spring.io/guides
- Spring Boot releases and upgrade information: https://spring.io/projects/spring-boot
