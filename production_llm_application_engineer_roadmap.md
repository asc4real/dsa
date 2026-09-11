# Production-Grade LLM Application Developer / Applied AI Engineer Roadmap

## Executive thesis

The target role is **not** “person who knows how to call an LLM API.” It is a software/AI engineering role that owns the path from an ambiguous product problem to a measurable, secure, observable production system.

A strong LLM Application Engineer can:

1. decide whether an LLM is appropriate at all;
2. translate product requirements into deterministic and probabilistic components;
3. choose models based on capability, latency, cost, privacy, reliability, context, tool use, and deployment constraints;
4. design prompts and context deliberately;
5. build retrieval, tool-use, memory, and workflow/agent systems from first principles;
6. implement the backend, storage, queues, caching, streaming, auth, and tenant isolation;
7. create evaluation datasets and regression gates;
8. secure the system against prompt injection, data leakage, excessive agency, and unsafe tool execution;
9. deploy and operate the system with tracing, metrics, fallbacks, cost controls, and incident-debugging discipline;
10. evolve the system safely as models and frameworks change.

This roadmap therefore treats **software engineering, retrieval, evaluation, reliability, security, and production architecture as first-class skills**. Frameworks such as LangChain, LangGraph, model-provider SDKs, vector databases, agent SDKs, and observability products are implementation choices—not the profession.

> **Recommended target depth:** become a Level 3–4 engineer in Python/backend/retrieval/RAG/evaluation; Level 3 in transformers, security, distributed systems, LLMOps, and system design; Level 2 in model training/fine-tuning/GPU serving; Level 1–2 in CUDA and foundation-model pretraining.

---

# Part I — What role are you actually becoming?

## 1. Role taxonomy

| Role | Core responsibility | Model-building depth | Software/system depth | Typical work |
|---|---|---:|---:|---|
| Software Engineer | General software systems | Low | High | APIs, services, data, products |
| Backend Engineer | Server-side systems | Low | Very high | APIs, DBs, queues, auth, scalability |
| AI Application Developer | Product features powered by AI | Low–medium | High | AI features, integration, UX, evals |
| LLM Application Developer | LLM-centric application systems | Medium | High | RAG, tools, prompts, agents, evals |
| Generative AI Engineer | Broad GenAI product engineering | Medium | High | LLMs + multimodal + orchestration |
| Applied AI Engineer | Applies models to real business problems | Medium–high | High | experiments → production, evals, product trade-offs |
| AI Engineer | Ambiguous market title | Varies | Varies | often Applied AI/LLM app engineering today |
| Machine Learning Engineer | Production ML systems | High | High | training/inference pipelines, feature/data/model ops |
| Deep Learning Engineer | Neural-network systems | Very high | Medium–high | PyTorch, training, inference, optimization |
| NLP Engineer | Language-focused ML systems | High | Medium–high | tokenization, classifiers, embeddings, sequence models, LLMs |
| LLM Engineer | Ambiguous: application or model/inference | Medium–very high | High | RAG/tooling **or** model serving/fine-tuning |
| LLM Infrastructure Engineer | LLM serving/platform | High | Very high | GPUs, distributed inference, serving, KV cache, batching |
| AI Platform Engineer | Internal platform for AI teams/apps | Medium | Very high | model gateways, eval infra, SDKs, governance, observability |
| MLOps Engineer | ML lifecycle/platform operations | Medium–high | Very high | training/deploy/versioning/monitoring |
| LLMOps Engineer | LLM app lifecycle operations | Medium | Very high | prompts/models/evals/traces/cost/releases |
| Research Engineer | Implements/experiments with research ideas | Very high | High | papers, training, benchmarks, prototypes |
| AI Research Scientist | Creates new algorithms/models | Specialist | Medium | novel research, experiments, publications |
| Prompt Engineer | Prompt/context behavior specialization | Medium | Medium | prompt design, testing, evaluation; rarely sufficient alone |

### Where your target sits

Your target is roughly:

```text
Backend / Product Engineering
           +
Applied ML / LLM Understanding
           +
Retrieval / Search
           +
Evaluation / Reliability
           +
Production AI Operations
           =
Production LLM Application / Applied AI Engineer
```

You do **not** need to become a foundation-model researcher first. You **do** need enough ML/DL/transformer knowledge to understand model behavior, embeddings, tokenization, inference, fine-tuning trade-offs, and why probabilistic systems fail.

### Seven increasingly deep levels of LLM work

```text
1. Call an LLM API
   ↓
2. Build an LLM-powered feature
   ↓
3. Engineer a production LLM system   ← PRIMARY TARGET
   ↓
4. Fine-tune/adapt models             ← WORKING KNOWLEDGE
   ↓
5. Serve open-weight models           ← WORKING KNOWLEDGE / SPECIALIZATION
   ↓
6. Train foundation models            ← AWARENESS
   ↓
7. Conduct frontier LLM research      ← OPTIONAL SPECIALIZATION
```

Recent 2026 job postings for Applied AI/LLM roles emphasize production software engineering, retrieval/RAG, evaluation harnesses, observability, cloud/backend systems, safeguards, and end-to-end ownership—evidence that the role is increasingly broader than prompt writing alone.[12][13][14]

---

# Part II — Depth targets and competency map

## 2. Depth scale

- **Level 1 — Awareness:** explain terminology and recognize use cases.
- **Level 2 — Working knowledge:** implement standard cases with documentation.
- **Level 3 — Strong professional competence:** design, debug, optimize, review, and operate independently.
- **Level 4 — Specialist:** reason from first principles, benchmark alternatives, solve unusual failures, teach others.

## 3. Target depth for an excellent LLM Application Engineer

| Domain | Target | Why |
|---|---:|---|
| Python | 3–4 | Primary AI/backend ecosystem; async, typing, testing and packaging matter constantly |
| Data structures / algorithms | 2–3 | Needed for competent coding, retrieval structures, system reasoning; not competitive-programming obsession |
| Backend engineering | 3 | Production APIs, auth, streaming, queues, reliability |
| FastAPI / Pydantic | 3 | Common Python API stack; useful for typed AI services |
| SQL / PostgreSQL | 3 | Core system-of-record, metadata, permissions, conversations, eval datasets |
| Redis / caching | 2–3 | rate limiting, cache, state, queues/locks where appropriate |
| Distributed systems | 3 | partial failures, retries, idempotency, queues, scaling |
| ML foundations | 2–3 | metrics, generalization, embeddings, evaluation reasoning |
| Deep learning | 2–3 | enough to understand transformers/fine-tuning/inference |
| PyTorch | 2 | inspect models, embeddings, simple training/fine-tuning |
| Classical NLP | 2 | useful history + lexical retrieval/text processing |
| Transformers | 3 | conceptual + moderately technical understanding |
| Foundation-model pretraining | 1–2 | understand; do not specialize initially |
| LLM API engineering | 4 | central daily skill |
| Prompt/context engineering | 3–4 | systematic specification + evaluation, not tricks |
| Embeddings | 3 | retrieval foundation |
| Information retrieval | 3–4 | one of the highest-value durable skills |
| Vector indexes | 3 | understand ANN and filtering trade-offs |
| RAG | 4 | major specialization for enterprise LLM apps |
| RAG evaluation | 4 | separates engineering from demos |
| Tool calling | 4 | central bridge between model and deterministic systems |
| Agents/workflows | 3 | useful but should remain controlled |
| LangChain | 2–3 | productive abstraction; replaceable |
| LangGraph | 3 | stateful/durable orchestration where justified |
| Evaluation | 4 | core production competency |
| Observability | 3 | essential for debugging probabilistic systems |
| Security | 3 | non-negotiable in enterprise AI |
| LLMOps | 3 | versioning, release, monitoring, feedback |
| Docker | 3 | basic production packaging |
| Kubernetes | 2 | enough to deploy/debug; deeper only for platform roles |
| Cloud | 3 | compute, network, IAM, storage, secrets, managed services |
| GPU inference | 2 | understand latency/throughput/VRAM/batching |
| CUDA programming | 1 | safely postpone |
| Fine-tuning | 2–3 | know when/how; not default solution |
| Product thinking | 3–4 | determines when AI should/shouldn't be used |
| System design | 3–4 | critical at senior+ |

---

# Part III — Complete skill tree

## 4. Track A — Software Engineering

### Python: MUST KNOW NOW

Focus on professional Python, not beginner syntax repetition.

**Core:** types and mutability, comprehensions, exceptions, dataclasses, `typing`, protocols, generics, iterators/generators, decorators, context managers, modules/packages, `pathlib`, `datetime`, serialization, environment/config management.

**Especially important for AI apps:**

- `async`/`await`, tasks, cancellation, timeouts and bounded concurrency;
- type-driven schemas with Pydantic;
- generators/async generators for streaming tokens/events;
- context managers for DB/session/network resources;
- decorators/middleware for tracing, auth, retries;
- protocols/ABCs for model-provider abstraction;
- structured exception taxonomies;
- profiling and memory awareness for document ingestion and inference clients;
- test doubles/mocks for expensive nondeterministic external APIs.

**Professional tooling:** `pytest`, `ruff`, type checker (Pyright/mypy), dependency management (`uv` or equivalent), pre-commit, structured logging, OpenTelemetry-compatible instrumentation, CI.

### Backend engineering: MUST KNOW NOW → STRONG COMPETENCE

Learn HTTP semantics, status codes, headers, caching, TLS, cookies, CORS, idempotency, REST trade-offs, WebSockets, SSE and streaming HTTP.

FastAPI/Pydantic topics:

- routers and dependency injection;
- request/response models;
- lifespan hooks;
- middleware;
- authentication and authorization;
- OAuth2/OIDC concepts;
- JWT validation—not merely decoding;
- exception mapping;
- streaming responses;
- background jobs vs real queues;
- OpenAPI contracts;
- test clients and integration tests.

FastAPI's async guidance remains straightforward: use `async def` when the I/O library is awaitable; blocking work must not be allowed to block the event loop.[4]

### How LLM backends differ

Ordinary request:

```text
client → API → DB → response       ~10–300 ms
```

LLM request:

```text
client
  → auth/quota
  → retrieval/tools
  → model provider (seconds+)
  → streamed partial output
  → post-validation
  → persistence/trace/eval hooks
```

Consequences:

- connections live longer;
- streaming UX matters;
- cancellation must propagate;
- timeouts need per-stage budgets;
- provider rate limits are first-class;
- retries can multiply cost or duplicate side effects;
- tool calls require idempotency;
- queues are needed for long document ingestion/evaluation jobs;
- concurrency must be bounded;
- backpressure matters;
- users need progress/failure states rather than frozen requests.

### Databases

**PostgreSQL — Level 3.** Learn schema design, keys, constraints, transactions, isolation, indexes, query plans, pagination, JSONB, full-text search, row-level security concepts, connection pools and migrations.

**Redis — Level 2–3.** Cache, short-lived state, rate limit counters, locks carefully, pub/sub/streams if justified. Do not turn Redis into a permanent system of record by accident.

**Object storage — Level 3 conceptually.** Raw uploads, parsed artifacts, images, large files, model/eval artifacts.

**Document store — Level 2.** Use when document-shaped access genuinely fits; don't choose merely because input documents are JSON.

**Graph DB — Level 1–2 initially.** Useful when relationships and path traversal are primary requirements; not a prerequisite for normal RAG.

---

# Part IV — AI/ML foundations without a multi-year detour

## 5. ML foundations

### Understand deeply

- train/validation/test separation;
- data leakage;
- overfitting vs underfitting;
- bias/variance intuition;
- loss vs task metric;
- precision/recall/F1 trade-offs;
- calibration and threshold selection;
- embeddings and similarity;
- distribution shift;
- baseline design;
- experiment reproducibility.

### Working intuition is enough initially

- regression/classification families;
- regularization families;
- ROC-AUC mathematics;
- feature engineering traditions;
- clustering algorithms beyond basic intuition.

### Mostly researcher/specialist territory initially

- statistical learning theory proofs;
- advanced optimization convergence proofs;
- novel objective derivation;
- research-level generalization theory.

### Minimal conceptual model

Supervised learning:

```text
examples (x, y)
   ↓
model fθ(x)
   ↓
prediction ŷ
   ↓
loss(ŷ, y)
   ↓
gradient updates θ
```

LLM application relevance: even when you do not train the model, **evaluation discipline comes from ML thinking**—held-out data, metrics, error buckets, regression tests, and avoiding evaluation contamination.

## 6. Deep-learning foundations

Learn:

- neuron = affine transform + nonlinearity;
- layers compose representations;
- forward pass computes predictions;
- loss quantifies training error;
- backprop uses chain rule to compute gradients;
- optimizers update parameters;
- batches trade memory/gradient noise/throughput;
- epochs are dataset passes;
- dropout and normalization are regularization/stability techniques;
- PyTorch tensors, autograd, modules, datasets/loaders, optimizer loop.

**Exit target:** implement a tiny classifier and a toy language model, inspect gradients, overfit a tiny dataset intentionally, and explain why it works.

---

# Part V — Minimum effective mathematics

## 7. Math curriculum

| Concept | Why you need it | Depth | Intuition | LLM connection |
|---|---|---:|---|---|
| Vector | embeddings/features | 3 | ordered direction + magnitude | token/document embedding |
| Matrix | learned transforms | 3 | many vectors / linear map | projection layers, Q/K/V |
| Tensor | multidimensional data | 2–3 | nested arrays with math meaning | batch × sequence × hidden dim |
| Dot product | similarity/attention | 3 | alignment between directions | attention scores, dense retrieval |
| Norm | magnitude/normalization | 2–3 | vector length | cosine similarity, normalization |
| Cosine similarity | retrieval | 3 | angle similarity independent of magnitude | embedding search |
| Matrix multiplication | neural nets | 3 | batched linear transformation | nearly every transformer layer |
| Projection | representation change | 2 | map into useful subspace | Q/K/V, low-rank adaptation |
| Eigenvalues/vectors | representation intuition | 1–2 | dominant directions of transform | PCA/spectral intuition; not daily |
| Probability | uncertain outputs | 3 | quantified uncertainty | token distributions |
| Conditional probability | language modeling | 3 | probability given context | P(next token \| prefix) |
| Expectation/variance | metrics/sampling | 2–3 | average and spread | eval variance, sampling behavior |
| Bayes theorem | inference reasoning | 2 | update beliefs with evidence | probabilistic interpretation |
| Entropy | uncertainty | 2–3 | spread/uncertainty of distribution | token uncertainty |
| Cross-entropy | training objective | 3 | penalty for wrong probability mass | next-token loss |
| KL divergence | distribution difference | 2 | how one distribution diverges from another | alignment/fine-tuning objectives |
| Sampling | generation | 3 | choose outcomes from distribution | temperature/top-p decoding |
| Derivative | optimization | 2–3 | local slope | parameter update direction |
| Partial derivative | many parameters | 2 | slope wrt one variable | deep nets |
| Gradient | optimization | 3 | vector of partial derivatives | backprop |
| Chain rule | backprop | 3 conceptually | propagate effect through composition | gradient flow |

**Do not postpone building until you finish this table.** Learn each item when it first appears in code.

---

# Part VI — NLP → Transformers → LLMs

## 8. NLP foundations

| Topic | Relevance today |
|---|---|
| Tokenization | **Very high.** Context length, cost, chunk boundaries, multilingual behavior |
| Text normalization | **High.** Retrieval and ingestion quality |
| Stemming/lemmatization | Medium; still useful in lexical IR/search pipelines |
| Bag of words | Historical + useful intuition |
| TF-IDF | Useful; classical retrieval baseline |
| n-grams | Useful for search, language modeling intuition, character matching |
| Word2Vec/GloVe | Historical but excellent embedding intuition |
| Semantic similarity | **Core** |
| RNN/LSTM | Mostly historical foundation; understand why recurrence struggled with long dependencies/parallelization |
| Sequence-to-sequence | Important architecture intuition |

## 9. Transformers from first principles

### Intuition first

A transformer repeatedly lets each token build a better representation by looking at other relevant tokens.

```text
text
 ↓ tokenize
[token ids]
 ↓ embedding lookup + position information
[vectors]
 ↓ transformer block × N
   ├─ attention: what other tokens matter to me?
   └─ feed-forward: transform what I learned
 ↓ final hidden state
 ↓ vocabulary projection
[logits]
 ↓ softmax / sampling
next token
 ↓ append token and repeat
```

### Q, K, V intuition

For a token:

- **Query:** what am I looking for?
- **Key:** what information do I advertise?
- **Value:** what content should be passed if I am relevant?

Attention conceptually computes:

```text
similarity(Query, Keys)
   → normalize scores
   → weighted combination of Values
```

Scaled dot-product attention:

```text
Attention(Q,K,V) = softmax(QKᵀ / √d_k) V
```

You should understand why scaling exists and how matrix shapes flow; you do not need to derive every gradient by hand.

### Multi-head attention

Multiple attention heads learn different relation patterns in parallel. Heads are concatenated and projected.

### Other block components

- feed-forward network: per-token nonlinear transformation;
- residual connection: preserves/combines earlier representation and improves training;
- normalization: stabilizes scale/training;
- causal mask: prevents decoder-only models from seeing future tokens.

### Model families

**BERT-style encoder-only:** bidirectional representation; historically excellent for classification/embedding-like tasks. BERT introduced deep bidirectional Transformer pretraining for language understanding.[20]

**GPT-style decoder-only:** causal next-token prediction; dominant general generative architecture. GPT-3 illustrated strong few-shot behavior from scaling autoregressive language models.[21]

**T5-style encoder-decoder:** encode input then decode output; natural for text-to-text transformation.

### From user prompt to generated token

1. prompt is serialized into model-specific message/text format;
2. tokenizer converts text to token IDs;
3. IDs map to embeddings;
4. positional information is applied;
5. transformer blocks repeatedly contextualize token representations;
6. final hidden state is projected into vocabulary logits;
7. logits become a probability distribution;
8. decoding strategy chooses next token;
9. new token is appended;
10. model repeats, reusing KV cache during efficient autoregressive inference.

## 10. Decoding concepts

- **greedy:** highest probability token; deterministic-ish but may be brittle;
- **temperature:** rescales logits; lower usually concentrates distribution;
- **top-k:** sample among k candidates;
- **top-p:** sample from smallest candidate set whose cumulative probability reaches p;
- **beam search:** explores multiple sequences; useful in some structured sequence tasks, less central for chat-style LLMs;
- **reasoning/inference-time compute:** additional model-side computation can improve difficult tasks, trading latency/cost for quality.

---

# Part VII — Foundation-model concepts

## 11. What to know

**Pretraining:** large-scale self-supervised objective such as next-token prediction.

**Instruction tuning / SFT:** train on input→desired-response examples.

**RLHF:** learn preferences/reward and optimize behavior. InstructGPT is a canonical demonstration of SFT plus preference-driven RLHF.[23]

**DPO:** directly optimize from preference pairs without a separate RL reward-model/PPO loop; understand conceptually, not as a first implementation task.[22]

**Scaling laws:** capability/loss relationships with model/data/compute; useful for understanding why larger training runs historically improved models, but not a product-engineering decision rule by itself.[19]

**Mixture of Experts:** activate subsets of parameters per token to increase model capacity without activating the entire parameter set.

**Multimodal:** models that jointly process text plus image/audio/video/document representations.

**Open-weight vs hosted/closed:** changes privacy, serving, capital/ops burden, customization, latency and control.

### Model selection scorecard

Evaluate candidates on a fixed task-specific eval set using:

| Dimension | Question |
|---|---|
| Quality | Does it actually solve *your* task? |
| Reliability | How often does it violate constraints? |
| Tool use | Does it select/use tools correctly? |
| Structured output | Schema adherence? |
| Retrieval synthesis | Faithful use of provided evidence? |
| Latency | p50/p95 time-to-first-token and completion latency |
| Throughput | concurrent workload / tokens per second |
| Context | practical useful context, not marketing maximum alone |
| Multimodality | required modalities and quality |
| Privacy | retention/training/data residency controls |
| Cost | input/output/tool/infrastructure total cost |
| Deployment | hosted API, region, VPC, self-hosted, edge |

Never select “largest model” before benchmarking a smaller model on the same eval set.

---

# Part VIII — LLM API engineering

## 12. Production API skills

Learn a provider SDK **without** hiding it behind LangChain first.

Build manually:

- message/instruction construction;
- streaming;
- structured output using JSON schema;
- custom function/tool calling;
- timeouts;
- exponential backoff with jitter for retryable failures;
- bounded concurrency/semaphores;
- rate-limit handling;
- usage/token accounting;
- request IDs and trace correlation;
- model fallback;
- cache keys;
- cost logging;
- provider abstraction.

Current OpenAI Responses API supports text/image inputs, structured JSON outputs, built-in tools, MCP tools and custom function calls, illustrating the modern “model + tools + structured output” API shape.[5]

### Production request wrapper

```text
Application request
  ↓
validate input + auth + quota
  ↓
construct context/instructions
  ↓
apply timeout budget
  ↓
model gateway
  ├─ provider A
  ├─ provider B fallback
  └─ smaller model route
  ↓
stream / tool loop
  ↓
validate output
  ↓
write trace + usage + result
```

### Retry policy rule

Retry **transport/rate-limit/transient provider failures**, not arbitrary bad model answers. For model-quality failure, use a deliberate correction/fallback path with a bounded budget.

---

# Part IX — Prompt and context engineering

## 13. Prompt engineering as specification

A strong prompt contains:

```text
Role/purpose
Task definition
Relevant context
Allowed tools/data
Constraints and policies
Output contract/schema
Examples when useful
Failure/abstention behavior
```

### Prompt development loop

```text
failure examples
   ↓
label failure categories
   ↓
hypothesis about cause
   ↓
change prompt/context/tool/schema
   ↓
run fixed eval suite
   ↓
compare metrics + regressions
   ↓
ship only if net improvement
```

### Prompting vs other interventions

| Problem | Better first response |
|---|---|
| Model does not know private/current fact | Retrieval/tool |
| Exact calculation | Code/calculator |
| Need transactional action | Tool + authorization |
| Knowledge changes frequently | Retrieval/tool, not fine-tuning |
| Consistent response schema | Structured output/constrained generation |
| Consistent house style/behavior at scale | Prompt first, then consider fine-tuning |
| Model too weak for reasoning task | Better model / decomposition / tools |
| Product accepts only exact truth | Often deterministic software, not free-form LLM |

Prompt injection cannot be “solved” by a stronger system prompt. Treat external text as untrusted data and enforce capabilities/authorization outside the model. OWASP continues to rank prompt injection and excessive agency among major GenAI risks.[8][9]

---

# Part X — Embeddings and information retrieval

## 14. Embeddings

An embedding model maps an input to a vector such that task-relevant semantic relationships become geometrically useful.

Learn:

- cosine similarity, dot product and Euclidean distance;
- normalization;
- dimensionality and storage cost;
- query/document asymmetry;
- multilingual embeddings;
- domain-specific retrieval;
- batch embedding;
- embedding-version migration;
- index rebuild strategy.

### How to select an embedding model

Do not choose from leaderboard popularity alone. Build a domain retrieval dataset:

```text
query → set of relevant document/chunk IDs
```

Benchmark candidate embeddings with Recall@K/MRR/NDCG plus latency/cost. Then test downstream answer quality.

## 15. Information retrieval — one of your deepest tracks

### Lexical search

**Inverted index:** token → documents/positions. Foundation of scalable term-based search.

**BM25:** ranking function balancing term frequency, inverse document frequency and document length. Learn conceptually and use it as a serious baseline—not as obsolete history.

### Dense retrieval

A dual-encoder embeds query and document separately; nearest-neighbor search finds semantically similar candidates. Dense Passage Retrieval is a canonical reference for dense retrieval in open-domain QA.[18]

### Sparse learned retrieval

Represents text in a high-dimensional sparse space; can combine lexical interpretability with learned matching.

### Hybrid retrieval

Combine lexical + dense candidate scores/ranks. Useful when exact names, identifiers, acronyms and semantics all matter.

### Reranking

Two-stage search:

```text
cheap retriever → top 50/100 candidates
                 ↓
          expensive reranker
                 ↓
              top 5–10
```

Cross-encoders jointly inspect query + candidate and can improve relevance at additional latency/cost.

### Fusion

Reciprocal Rank Fusion (RRF) combines ranked lists without needing directly comparable raw scores.

### Query techniques

- spelling/normalization;
- query classification;
- query rewrite;
- acronym/entity expansion;
- multi-query retrieval;
- decomposition for multi-hop questions;
- metadata/ACL filtering;
- relevance feedback where suitable.

**Core principle:** a vector database is an indexing/storage component. It is not “RAG.”

---

# Part XI — Vector indexes and databases

## 16. ANN concepts

Exact nearest-neighbor search maximizes recall but can become expensive at scale.

**HNSW:** graph-based ANN; strong speed/recall, higher memory/build cost.

**IVF:** partitions vector space into coarse lists/clusters, searches selected partitions.

**PQ:** compresses vectors to reduce memory and accelerate search at some accuracy cost.

With pgvector, exact search is default, while HNSW and IVFFlat provide approximate search; HNSW generally offers stronger speed/recall trade-off at higher memory/build cost, while IVFFlat is cheaper to build and tune differently.[7]

### Default architectural rule

Start with:

```text
PostgreSQL
  + relational metadata / permissions
  + full-text search
  + pgvector
```

Move to a dedicated search/vector engine only when measurements justify it: scale, filtering/search feature requirements, operational isolation, indexing throughput, special ranking features, or latency/availability constraints.

### Critical multi-tenant lesson

Filtering and ANN interact. Tenant/document ACL filtering must be designed into retrieval; never retrieve cross-tenant candidates and “hope” the prompt ignores them. pgvector explicitly documents filtering and multitenancy considerations, including partitioning/separate tables for isolation/recall behavior.[7]

---

# Part XII — RAG from ingestion to verified answer

## 17. What RAG solves

RAG addresses a class of problems where the model needs **external, changing, private, attributable, or domain-specific evidence** at inference time. The original RAG work combined parametric model knowledge with retrieved non-parametric memory for knowledge-intensive tasks.[17]

RAG does **not** automatically solve reasoning, bad source documents, permissions, ambiguous questions, or hallucination.

## 18. Ingestion pipeline

```text
source
  ↓
acquire + version
  ↓
parse / OCR if required
  ↓
clean / normalize
  ↓
extract structure + metadata
  ↓
chunk with lineage
  ↓
embed
  ↓
index lexical + vector
  ↓
quality checks
  ↓
publish searchable version
```

### Document types

- **PDF:** distinguish born-digital text, layout-heavy, tables, figures, scanned pages;
- **DOCX:** preserve headings, lists, tables, references;
- **HTML:** remove navigation/noise while preserving semantic hierarchy;
- **Markdown:** headings and code blocks are valuable structure;
- **Spreadsheets:** model rows/columns/tables explicitly; generic text chunks often destroy meaning;
- **Presentations:** slide title/body/speaker notes/images need structure and slide lineage;
- **Images/scans:** OCR is fallible; preserve coordinates/page/image link and confidence if available.

### Chunking strategies

| Strategy | Use | Risk |
|---|---|---|
| Fixed token | baseline | cuts semantic units |
| Recursive | general docs | still heuristic |
| Sentence/paragraph | prose | variable size |
| Structure-aware | contracts/manuals/docs | parser complexity |
| Semantic | topic shifts | cost/instability |
| Parent-child | retrieve precise child, return larger parent | more storage/index logic |
| Hierarchical | long structured corpora | more pipeline complexity |
| Table-aware | spreadsheets/reports | specialized parser required |

Store lineage:

```text
chunk_id
source_document_id
source_version
page/slide/sheet
section_path
character/token offsets
access-control metadata
parser_version
chunker_version
embedding_version
```

## 19. Query-time RAG

```text
user query
  ↓
auth + tenant/document scope
  ↓
query classification
  ↓
rewrite/decompose if needed
  ↓
parallel lexical + dense retrieval
  ↓
ACL/metadata filters
  ↓
rank fusion
  ↓
rerank
  ↓
context selection / deduplication
  ↓
generation with explicit citation contract
  ↓
faithfulness / citation checks
  ↓
answer + sources + trace
```

## 20. Advanced RAG decision table

| Technique | Solves | Use when | Avoid when |
|---|---|---|---|
| Hybrid search | lexical+dense blind spots | IDs, names, domain terms + semantics | simple corpus where dense baseline already strong |
| Reranking | weak ordering | top candidates are relevant but best ones rank poorly | tiny datasets / latency critical and no measured gain |
| Query rewrite | bad user phrasing | conversational/ambiguous queries | model rewrite changes intent |
| Multi-query | recall | one formulation misses relevant docs | cost-sensitive straightforward queries |
| HyDE | query-document representation mismatch | zero/few-shot retrieval experiments show gain | unsupported domain, hallucinated pseudo-doc harms retrieval |
| Contextual compression | too much context | long redundant chunks | compression may drop critical details |
| Parent-document retrieval | context fragmentation | small chunks retrieve well but lack surrounding evidence | short independent records |
| Hierarchical retrieval | very long structured corpora | book/manual/policy hierarchy | small corpus |
| Graph-assisted RAG | relation/path questions | entities/relationships are core | generic document Q&A |
| Self-query retrieval | metadata-heavy filtering | natural language maps cleanly to schema filters | security filters must never rely on model alone |
| Multi-hop retrieval | answer needs several sources | research/composite questions | single-fact QA |
| Corrective RAG | low-confidence retrieval | system can detect weak evidence | detection signal is unvalidated |
| Adaptive RAG | heterogeneous query complexity | routing saves cost/latency | premature complexity |
| Agentic RAG | retrieval plan requires tools/iterations | open-ended research | normal enterprise Q&A; failure surface grows rapidly |

---

# Part XIII — RAG evaluation

## 21. Retrieval metrics

For query `q` with known relevant items:

- **Recall@K:** fraction of relevant items found in top K;
- **Precision@K:** fraction of top K that are relevant;
- **MRR:** reciprocal rank of first relevant result, averaged;
- **NDCG:** position-sensitive graded relevance ranking metric.

For enterprise QA, prioritize **Recall@K + rank quality + access-control correctness** before tweaking answer prompts.

## 22. End-to-end metrics

- answer correctness;
- groundedness / faithfulness to retrieved evidence;
- answer relevance;
- completeness;
- citation correctness;
- citation coverage;
- abstention correctness;
- harmful/forbidden-action rate;
- latency/cost.

### Golden dataset structure

```text
question
user/tenant role
expected answer or rubric
relevant source IDs
optional relevant chunk IDs
required facts
forbidden claims
expected abstention? yes/no
metadata filters
```

### Evaluation stack

```text
deterministic checks
  + retrieval metrics
  + LLM judge rubric
  + sampled human review
  + production feedback
```

Treat LLM judges as noisy measurement instruments. Calibrate them against human-labeled examples, use blinded/pairwise comparison when useful, and monitor judge-model/version changes.

---

# Part XIV — Tools, workflows and agents

## 23. Precise terminology

**Pipeline:** fixed ordered processing stages.

**Chain:** mostly linear model/tool sequence.

**Router:** chooses a branch.

**Workflow/state machine:** explicit steps/states/transitions, mostly deterministic.

**Tool-using LLM:** model can choose a function/API call.

**Agent:** model participates in deciding successive actions based on observations until a bounded stop condition.

**Autonomous agent:** broader unsupervised control over plans/actions; significantly higher risk.

**Multi-agent system:** several model-driven roles/workers; justified only when decomposition/parallel specialization measurably helps.

Canonical ReAct research interleaves reasoning and actions/observations, while Toolformer explored learning when/how to call external APIs.[24][25]

### Preferred escalation ladder

```text
deterministic function
   ↓ only if needed
pipeline
   ↓
router/state machine
   ↓
workflow graph with LLM decisions at selected nodes
   ↓
tool-using agent with bounded loop
   ↓
multi-agent system only with demonstrated benefit
```

## 24. Tool architecture

```text
LLM proposes:
  {tool_name, arguments}
          ↓
schema validation
          ↓
authentication + authorization
          ↓
policy / risk check
          ↓
optional human approval
          ↓
idempotency / transaction boundary
          ↓
deterministic executor
          ↓
normalized observation
          ↓
LLM decides next step
```

Never let natural-language model output directly authorize money movement, permission changes, deletion, production changes, or sensitive data access.

## 25. Memory

Distinguish:

- conversation transcript;
- working context;
- summary memory;
- structured user preferences;
- semantic memory (retrievable facts);
- episodic memory (past events/interactions);
- application state/checkpoints.

Safe architecture:

```text
conversation
  ↓ candidate memory extraction
  ↓ validation / sensitivity policy
  ↓ structured persistence + provenance + expiry
  ↓ retrieval subject to user/tenant scope
  ↓ context injection
```

Deletion, retention, provenance and user visibility must be designed, not added later.

---

# Part XV — LangChain, LangGraph and framework independence

## 26. LangChain learning strategy

Current LangChain v1 focuses on agent/model/tool abstractions, while legacy functionality moved to `langchain-classic`.[6]

Learn each abstraction in this order:

```text
1. problem in plain Python
2. manual implementation
3. framework abstraction
4. benchmark whether abstraction helps
```

| LangChain concept | Plain Python equivalent | When useful | When harmful |
|---|---|---|---|
| model interface | provider client protocol | provider swapping | unnecessary wrapper around one simple call |
| prompt/messages | strings/typed messages | templates/versioning | tiny one-off call |
| output parser | Pydantic/json validation | typed boundaries | provider already guarantees schema and wrapper duplicates it |
| tool | typed callable + schema | agent integration | hides authorization/execution policy |
| retriever | function query→documents | standard interface | masks retrieval internals you need to tune |
| runnable/chain | functions/coroutines | composition | abstraction soup |
| callbacks/tracing | middleware/hooks | observability | duplicate tracing stack |

## 27. LangGraph

LangGraph is currently positioned as a lower-level runtime for long-running/stateful workflows with durable execution, streaming, human-in-the-loop and persistence.[2]

Learn progressively:

```text
StateGraph
  → nodes + edges
  → TypedDict/Pydantic-like state
  → reducers
  → conditional routing
  → loops with explicit stop bounds
  → checkpointing
  → persistence
  → interrupts / approval
  → retries / error states
  → subgraphs
  → streaming
  → production observability
```

LangGraph checkpoints enable memory, fault tolerance, replay/time travel and human-in-the-loop; interrupts require idempotent thinking because a resumed node can restart from the beginning.[1][3]

### Why graphs can be better than uncontrolled agents

A graph makes state transitions and allowed paths visible. Put probabilistic decisions only where they add value; keep policy, permissions, invariants and side effects deterministic.

## 28. Alternatives to evaluate

- plain Python + provider SDK;
- OpenAI Agents SDK or equivalent provider agent SDK;
- PydanticAI-style typed agent frameworks;
- LlamaIndex/Haystack-style retrieval frameworks;
- workflow engines (Temporal-like durable workflows);
- queue-driven orchestration;
- custom state machines;
- DSPy-style program/prompt optimization approaches.

Current OpenAI Agents SDK, for example, provides an Agent/Runner abstraction, tools, handoffs, guardrails and tracing; the documentation explicitly distinguishes SDK-managed loops from using the Responses API directly when you want to own orchestration.[10][11]

### Framework-independence test

If LangChain vanished tomorrow, you still need:

```text
model invocation
prompt/context construction
structured outputs
tool schemas
retrieval interfaces
state
routing
retries
persistence
observability
evaluation
security
```

If your knowledge survives framework deletion, it is durable.

---

# Part XVI — Multimodal AI

## 29. Core skills

**Images:** image understanding, spatial/layout limitations, grounding, metadata.

**Documents:** layout-aware parsing, tables, figures, page references, OCR fallback.

**Audio:** streaming speech-to-text, diarization concepts, VAD concepts, text-to-speech latency.

**Video:** frame/sample selection, transcript alignment, temporal questions, storage/cost.

**Multimodal RAG:** index extracted text + captions/visual embeddings/structural metadata; preserve links back to original evidence.

### Practical project

Build a document analyst that accepts a PDF with tables/figures, extracts structured sections, answers questions with page citations, and exposes low-confidence extraction warnings.

---

# Part XVII — Reliable probabilistic systems

## 30. Structured outputs

Use schemas whenever downstream code expects structure. Modern APIs can constrain output to JSON schema; this is materially stronger than “please output JSON.”[5]

Pattern:

```text
LLM
 ↓ schema-constrained output
parser
 ↓ semantic validation
business rules
 ↓ authorization / invariants
executor
```

### Reliability controls

- JSON schema/Pydantic;
- enums and bounded numeric ranges;
- semantic validators;
- post-processing;
- explicit abstention;
- confidence only when calibrated/meaningful;
- deterministic fallbacks;
- bounded retry/correction;
- circuit breakers;
- human approval for consequential actions.

**Rule:** model output is a proposal until deterministic code validates it.

---

# Part XVIII — Evaluation as a first-class engineering discipline

## 31. Evaluation loop

```text
requirements
  ↓
eval dataset + rubrics
  ↓
baseline
  ↓
change model/prompt/retrieval/tool logic
  ↓
offline evaluation
  ↓
integration + adversarial tests
  ↓
staged rollout
  ↓
online metrics + human feedback
  ↓
new failures → dataset
```

### Evaluation layers

1. **Unit tests:** deterministic helpers, parsers, ACL filters, tool schemas.
2. **Contract tests:** provider adapters/tool APIs.
3. **Retrieval evals:** Recall@K/MRR/NDCG.
4. **Model-task evals:** accuracy/rubric/schema adherence.
5. **Agent trajectory evals:** tool choice, path length, success, forbidden action rate.
6. **Security evals:** injection, data exfiltration, cross-tenant, unsafe tools.
7. **Performance evals:** latency, TTFT, tokens, concurrency, cost.
8. **Human evals:** sampled difficult/subjective cases.

Current OpenAI eval tooling includes grader types such as string checks, similarity and model-based graders; treat any platform as an implementation choice around the durable idea of versioned datasets + graders + reproducible runs.[15]

---

# Part XIX — Observability and LLMOps

## 32. Trace model

Capture a trace like:

```text
HTTP request
 ├─ auth
 ├─ retrieval
 │   ├─ rewrite
 │   ├─ dense search
 │   ├─ lexical search
 │   └─ reranker
 ├─ model call #1
 ├─ tool call
 ├─ model call #2
 ├─ output validation
 └─ persistence
```

Log/measure:

- trace/request/user-safe IDs;
- prompt/template version;
- model/provider/version;
- token counts;
- cache hits;
- TTFT and total latency;
- retrieval candidates/scores;
- tool arguments/results with sensitive-data policy;
- trajectory/step count;
- structured-output validation errors;
- cost estimate;
- user feedback;
- eval/regression version.

Provider/framework tracing is useful but should map into an organization-wide observability model. For example, OpenAI Agents SDK currently traces agent turns, generations, tool calls, handoffs and guardrails.[16]

## 33. LLMOps vs DevOps vs MLOps

**DevOps:** application build/deploy/operate.

**MLOps:** datasets/features/training/model registry/deployment/drift.

**LLMOps:** adds prompt/context versions, provider/model versions, evaluation datasets, agent trajectories, retrieval versions, token/cost tracking, safety regressions and model-behavior release gates.

Version:

```text
code
prompt
retrieval config
chunker/parser
embedding model
reranker
model routing policy
eval dataset
judge/rubric
```

A release is not reproducible if these are invisible.

---

# Part XX — Security and enterprise concerns

## 34. Threat model

Current OWASP GenAI guidance highlights prompt injection, sensitive-information disclosure, supply-chain/data poisoning, improper output handling, excessive agency, vector/embedding weaknesses, misinformation and unbounded consumption among key risks.[8]

### Secure architecture

```text
UNTRUSTED INPUT / DOCUMENT / WEB PAGE
              ↓
       classify + normalize
              ↓
       scoped retrieval/tool policy
              ↓
             LLM
              ↓
       structured validation
              ↓
 authorization + business invariants
              ↓
   approval for sensitive operations
              ↓
      deterministic execution
              ↓
         immutable audit log
```

### Production controls

- least-privilege service identities;
- tenant and document-level ACLs before retrieval;
- row-level security/partitioning as appropriate;
- separate secrets from prompts;
- redaction policies for traces;
- egress restrictions for code/tools;
- sandboxed code execution;
- per-tool authorization;
- short-lived credentials;
- idempotency keys;
- rate/quota/token limits;
- audit logs;
- retention/deletion policy;
- provider data-handling review;
- adversarial eval suite;
- approval gates for destructive/high-impact actions.

NIST's Generative AI Profile is a useful governance/risk-management reference alongside application-level engineering controls.[26]

---

# Part XX-A — AI product thinking and when **not** to use an LLM

## 35A. Product judgment is an engineering skill

An excellent Applied AI Engineer does not maximize “AI-ness.” The goal is to build the simplest system that reliably creates user value.

### Deterministic vs probabilistic task test

Ask:

1. **Is there one objectively correct operation that ordinary code can compute?** Use code.
2. **Is the task fundamentally language/vision interpretation with fuzzy boundaries?** An LLM may help.
3. **Does the answer require current/private data?** Add retrieval or tools.
4. **Can an incorrect answer cause money movement, deletion, access changes, medical/legal/financial harm, or compliance failure?** Put deterministic policy and often human approval after the model.
5. **Can the system detect when it lacks sufficient evidence?** If not, design abstention/escalation before shipping.

### Good LLM tasks

- summarize messy text;
- classify ambiguous natural-language intent;
- extract structured facts from heterogeneous documents;
- synthesize several retrieved sources;
- draft language for human review;
- map natural language to a constrained tool/schema;
- help users search or navigate complex knowledge.

### Tasks that should often remain deterministic

- permission checks;
- monetary calculations when formulas exist;
- primary-key lookups;
- enforcing compliance rules;
- invoice totals;
- access-control decisions;
- database constraints;
- workflow invariants;
- retry/idempotency logic.

### UX for uncertainty

Do not hide uncertainty behind confident prose. Depending on use case, expose:

- citations/source previews;
- “I don’t have enough evidence” states;
- confidence only if calibrated and meaningful;
- human review queues;
- proposed action → approval → execution;
- undo/reversal for reversible actions;
- clear distinction between generated draft and committed action;
- graceful fallback to search/forms/manual workflow if AI is unavailable.

### Graceful degradation ladder

```text
full model + retrieval + tools
        ↓ provider/model problem
fallback model + same retrieval
        ↓ tool unavailable
answer from retrieval only / draft without action
        ↓ retrieval degraded
keyword search + source links
        ↓ AI unavailable
ordinary deterministic product workflow
```

A robust AI product should often remain partially useful when the model provider is down.

---

# Part XXI — Production architecture

## 35. Reference architecture

```text
┌─────────────────────────────────────────────────────────────┐
│                        Client/UI                            │
│ web/mobile/IDE/chat + streaming + citations + approvals    │
└───────────────────────┬─────────────────────────────────────┘
                        │
                 API Gateway / WAF
                        │
┌───────────────────────▼─────────────────────────────────────┐
│                     Backend API                            │
│ auth • RBAC • quota • sessions • request validation        │
└──────────────┬────────────┬──────────────┬──────────────────┘
               │            │              │
               │            │              └── Queue / workers
               │            │                    ingestion/evals
               │            │
               │      AI Orchestrator
               │      state/workflows/tools
               │            │
        ┌──────▼─────┐  ┌───▼────────────┐
        │ Retrieval │  │ Model Gateway  │
        │ lexical   │  │ routing        │
        │ vector    │  │ fallback       │
        │ reranker  │  │ budgets        │
        └──────┬────┘  └───┬────────────┘
               │            │
     ┌─────────▼───┐    ┌───▼────────────┐
     │Postgres/    │    │Hosted / local │
     │Search index │    │LLMs/embeddings│
     └─────────────┘    └────────────────┘
               │
          Tool services
   CRM • DB • Search • Files • APIs

Cross-cutting: tracing • metrics • evals • security • audit • cost
```

### Service-boundary rule

Start as a modular monolith unless independent scaling, security boundaries, team ownership or failure isolation justify microservices.

### Sync vs async

- interactive answer → synchronous streaming request;
- ingestion/indexing → background queue;
- long research task → durable job/state + progress events;
- batch evaluations → queued workers;
- side-effecting tools → transactional/idempotent service call.

### Time-budget example

```text
API SLA: 12 s
  retrieval:       1.0 s
  reranking:       0.7 s
  model TTFT:      2.0 s
  generation:      7.0 s
  validation/etc:  0.8 s
  reserve:          0.5 s
```

Design retry budgets inside the SLA rather than adding retries afterward.

---

# Part XXII — Model gateway and multi-model architecture

## 36. Gateway responsibilities

- provider-neutral request model;
- model aliases rather than hardcoded model IDs everywhere;
- routing by task/capability/data sensitivity;
- timeout/retry policy;
- rate-limit normalization;
- cost accounting;
- fallbacks;
- observability;
- prompt caching hooks;
- experiment routing.

### Example routing

```text
classification/extraction → small fast model
simple FAQ + strong retrieval → small/medium model
difficult synthesis → larger/reasoning model
image request → multimodal model
restricted data → approved provider/self-hosted route
```

Introduce a gateway when you have multiple models/providers, governance requirements, centralized quotas/costs, or many teams. Do not create a platform service for a two-endpoint prototype.

---

# Part XXIII — Performance and cost engineering

## 37. Latency decomposition

Measure separately:

```text
DNS/TLS/network
retrieval
reranking
queue wait
provider queue
TTFT
output tokens / sec
tool calls
validation
persistence
```

### Techniques

- async I/O;
- connection pooling;
- bounded parallel retrieval/tool calls;
- stream as early as possible;
- prompt/prefix caching;
- result caching for deterministic/idempotent cases;
- semantic caching only with rigorous correctness boundaries;
- reduce unnecessary context;
- rerank smaller candidate sets;
- batch embeddings/offline jobs;
- model cascade/router;
- cap agent steps/output tokens.

For self-hosting, prefix/KV caching can reuse computation for shared prompt prefixes; vLLM documents hash-based automatic prefix caching.[27]

## 38. Cost model

For request `r`:

```text
LLM_cost(r)
 = input_tokens/1e6  × input_price
 + output_tokens/1e6 × output_price
 + tool/provider fees

retrieval_cost(r)
 = embedding_query
 + search
 + rerank

allocated_infra_cost(r)
 = monthly_shared_infra / monthly_requests

observability_cost(r)
 = logs + traces + storage allocation

Total_request_cost
 = LLM + retrieval + tools + infra + observability
```

Then:

```text
cost_per_user_month = avg_requests_per_user × avg_request_cost
cost_per_1000_users = 1000 × cost_per_user_month
```

Track p50/p95 cost because agent loops can create a long tail.

---

# Part XXIV — Open-weight models and fine-tuning

## 39. Self-hosted inference

Learn concepts:

- model weights/checkpoints;
- tokenizer compatibility;
- quantization;
- tensor/pipeline parallel concepts;
- GPU VRAM;
- KV cache;
- continuous/dynamic batching;
- throughput vs latency;
- context-memory growth;
- inference server APIs;
- model warmup and autoscaling.

Tools to know: Hugging Face ecosystem, vLLM, llama.cpp for local/edge/smaller-model scenarios, vendor/container serving platforms.

### Self-host when

- privacy/data residency requires it;
- traffic is high/stable enough for favorable economics;
- you need a specific open model/customization;
- latency/locality/control matter;
- organization can operate GPU infrastructure reliably.

Avoid self-hosting simply to “avoid API fees” without accounting for GPUs, idle capacity, engineering, availability and upgrades.

## 40. Fine-tuning decision tree

```text
Does model lack current/private factual knowledge?
  → RAG / tool first

Does it know facts but repeatedly fail a stable behavior/style/task?
  → improve prompt/schema/examples
  → if persistent and enough high-quality data: consider SFT/LoRA

Need domain language adaptation at deeper level?
  → consider continued pretraining (specialist territory)

Need preference/style alignment?
  → preference optimization may apply

Need exact business rule?
  → code, not fine-tuning
```

LoRA freezes base weights and trains low-rank adapters, reducing trainable parameters; PEFT frameworks provide LoRA/QLoRA-style parameter-efficient adaptation.[28][29]

Fine-tuning is not a factual database and does not guarantee hallucination elimination.

---

# Part XXV — Cloud, Docker, Kubernetes, distributed systems

## 41. Cloud concepts

Learn provider-neutral concepts first:

| Concept | AWS example | Azure example | GCP example |
|---|---|---|---|
| Compute VM | EC2 | VM | Compute Engine |
| Containers | ECS/EKS | Container Apps/AKS | Cloud Run/GKE |
| Object storage | S3 | Blob Storage | Cloud Storage |
| Managed Postgres | RDS/Aurora | Azure Database for PostgreSQL | Cloud SQL/AlloyDB |
| Queue | SQS | Service Bus | Pub/Sub |
| Secrets | Secrets Manager | Key Vault | Secret Manager |
| Identity | IAM | Entra/IAM | IAM |
| Monitoring | CloudWatch | Azure Monitor | Cloud Monitoring |

You need architecture/operations competence, not a memorized catalog.

## 42. Docker

Learn images, containers, layers, Dockerfile, `.dockerignore`, non-root users, multi-stage builds, health checks, volumes, networks, Compose, secrets, image scanning and reproducible builds.

AI-specific concerns:

- model weights can make images enormous—usually mount/download separately;
- GPU runtime and drivers;
- cache directories;
- native parsing/OCR dependencies;
- do not bake API keys into images/layers.

Docker documentation specifically recommends secrets rather than exposing passwords/API keys through image/source or broad environment-variable handling where stronger secret mechanisms are available.[30]

## 43. Kubernetes — only realistic depth

Must know:

- Pods;
- Deployments;
- Services;
- Gateway/Ingress concept;
- ConfigMaps/Secrets;
- liveness/readiness/startup probes;
- resource requests/limits;
- HPA/autoscaling;
- logs/metrics/traces;
- rolling deploy/rollback.

Postpone:

- writing operators;
- deep scheduler internals;
- CNI implementation;
- service-mesh internals;
- cluster administration unless your role requires it.

Kubernetes now recommends Gateway for new development over the frozen Ingress API; know Ingress because existing systems still use it.[31]

## 44. Distributed-systems principles tied to AI

| Principle | LLM example |
|---|---|
| Partial failure | model succeeds, CRM tool times out |
| Retry | provider 503; must avoid duplicated tool side effect |
| Idempotency | retrying “create ticket” cannot create 3 tickets |
| Eventual consistency | ingestion updates index after source DB commit |
| Backpressure | embedding queue grows faster than provider quota |
| Timeout budget | retrieval + tools + model all compete for request SLA |
| Circuit breaker | provider outage; fail over/degrade gracefully |
| Cache consistency | invalidate summaries after source changes |
| Distributed trace | correlate API → retriever → model → tool |
| Horizontal scaling | stateless API replicas; externalize state |

---

# Part XXVI — System-design playbook

## 45. Common system-design questions

### A. ChatGPT-like conversation storage

Requirements: message ordering, branching/editing, attachments, tool events, token usage, retention/deletion, multi-device concurrency.

Design: PostgreSQL conversations/messages/run items; object store attachments; optional summaries; immutable raw event lineage; access-control by user/org; streaming via SSE/WebSocket; indexes on conversation/user/time.

Trade-off: store provider-native response objects for debugging vs normalize to internal canonical schema; usually do both with bounded retention.

### B. Enterprise RAG assistant

Core: document connectors → ingestion workers → parser/chunker → lexical+vector index → ACL-aware retrieval → reranker → generation with citations → eval/trace.

Hard parts: permissions freshness, parsing quality, source versioning, citation correctness, multi-tenancy—not the chat UI.

### C. AI coding assistant

Context sources: open files, repo search, symbol/index graph, diffs, tests, compiler/LSP, git history. Tools must operate in sandbox/repo scope. Evaluation requires real coding tasks and test pass rate, not prose judges alone.

### D. Customer-support agent

Knowledge retrieval + customer/account tools + ticket/order actions. Use deterministic workflow for refunds/escalations, explicit approvals, tool scopes, audit. Track containment, resolution, escalation correctness, CSAT, policy violations.

### E. Document-analysis platform

Async ingestion, parser/OCR pipeline, structured extraction, page-level provenance, human review, versioned schemas, batch processing, resumable jobs.

### F. Text-to-SQL

Schema retrieval, semantic layer, query generation, SQL parser/validator, read-only credentials, row/column security, cost/time limits, explain-plan checks, sandbox execution. Never give model raw privileged DB credentials.

### G. AI research assistant

Search/retrieval tools, source deduplication, evidence ledger, citation verification, task state/checkpoints, bounded planning. Separate “claims” from “sources” in state.

### H. Multi-tenant AI SaaS

Tenant-aware auth/RBAC, RLS or explicit tenant keys, isolated object prefixes, scoped retrieval, quotas, per-tenant keys/config, audit, encryption, deletion, model routing, cost attribution.

---

# Part XXVII — Reusable design patterns

## 46. Pattern catalog

| Pattern | Use | Avoid |
|---|---|---|
| Classification | route/label | exact rule already exists |
| Extraction | unstructured→schema | deterministic parser is reliable |
| Structured generation | produce typed artifact | free-form creative task |
| Router | choose model/tool/workflow | one path handles everything |
| Retrieval | fetch evidence | knowledge already small/stable in code |
| RAG | evidence-grounded answers | deterministic lookup suffices |
| Tool calling | access current/external/action data | model can answer safely without tool |
| Parallel workers | independent subtasks | shared-state ordering matters |
| Map-reduce | large corpus aggregation | global interactions dominate |
| Planner-executor | open-ended multi-step tasks | known workflow |
| Evaluator-optimizer | iterative quality task | no reliable evaluator / cost too high |
| Reflection/revision | catch correctable errors | can cause loops/self-confirmation |
| Human approval | high-impact actions | low-risk read-only tasks |
| Model cascade | save cost/latency | routing errors outweigh savings |
| Fallback model | provider resilience | semantic behavior mismatch is unacceptable |
| Retrieval cascade | cheap recall then expensive rerank | tiny corpus |

---

# Part XXVIII — Anti-patterns

## 47. Things to reject in design reviews

1. **Agents for everything:** turns known workflows into nondeterministic loops.
2. **Vector DB by reflex:** adds another datastore and operational surface without proof of need.
3. **Blind fixed chunks:** destroys headings/tables/semantic boundaries.
4. **Huge prompts instead of retrieval:** cost, stale knowledge, context dilution.
5. **Trusting model output:** schemas are necessary but business validation still matters.
6. **No eval dataset:** cannot distinguish improvement from anecdote.
7. **LLM judge = truth:** judge bias/version drift can silently change scores.
8. **Excessive framework abstraction:** debugging becomes archaeology.
9. **Largest model for every call:** wasteful and often slower without measurable gain.
10. **Unbounded agent loops:** runaway cost, latency and side effects.
11. **Secrets in prompts:** leakage/tracing/provider-retention risk.
12. **Authorization by model:** security policy cannot depend on probabilistic text.
13. **No latency budget:** system works in demo and fails UX/SLA.
14. **No cost attribution:** agent/retrieval changes silently destroy unit economics.
15. **No observability:** impossible to know whether retrieval, model, tool or orchestration failed.
16. **Fine-tune before baseline:** expensive solution to an undefined problem.
17. **Multi-agent theater:** role-playing agents with no measurable decomposition benefit.

---

# Part XXIX — Dependency-aware roadmap by phase

The phases below overlap intentionally. Build while learning theory.

## Phase 0 — Orientation and system anatomy

- **Purpose:** understand the profession and end-to-end architecture.
- **Prerequisites:** professional software experience.
- **Concepts:** LLM app lifecycle, probabilistic vs deterministic components, tokens, context, retrieval, tools, evals.
- **Depth:** Level 1–2.
- **Exercises:** draw 3 architectures; label data/trust boundaries.
- **Mini-project:** one direct LLM API CLI.
- **Resources:** provider API quickstart; this roadmap.
- **Exit:** explain RAG vs tools vs fine-tuning vs workflow.
- **Common mistake:** framework-first learning.
- **Interview:** “When should you not use an LLM?”

## Phase 1 — Python for AI engineering

- **Purpose:** write idiomatic, typed, async production Python.
- **Prereq:** programming experience.
- **Concepts:** typing, Pydantic, iterators/generators, decorators, context managers, exceptions, async, packaging, testing.
- **Depth:** Level 3.
- **Exercises:** typed adapters; async HTTP client; retry decorator; streaming generator.
- **Mini-project:** resilient async API client library.
- **Resources:** Python docs; Effective Python or Fluent Python selectively.
- **Exit:** can explain event loop, cancellation, blocking I/O, context managers, Protocols.
- **Mistake:** translating Java patterns mechanically into Python.
- **Interview:** async vs threading vs multiprocessing.

## Phase 2 — Backend foundations

- **Purpose:** production service boundary around AI functionality.
- **Prereq:** Phase 1.
- **Concepts:** HTTP, FastAPI, Pydantic, auth, middleware, streaming, WebSocket/SSE, queues, rate limiting, idempotency.
- **Depth:** Level 3.
- **Exercises:** auth middleware; SSE endpoint; background worker; idempotent POST.
- **Mini-project:** streaming chat API without RAG.
- **Resources:** FastAPI official docs.[4]
- **Exit:** handle cancellation, timeout, auth, errors, tests.
- **Mistake:** using FastAPI BackgroundTasks for durable jobs.
- **Interview:** design a long-running streaming endpoint.

## Phase 3 — Data systems

- **Purpose:** durable state and data access.
- **Prereq:** SQL basics.
- **Concepts:** Postgres schema/index/transaction/pool; Redis; object storage; migrations.
- **Depth:** Postgres 3, Redis 2.
- **Exercises:** conversation schema; RLS experiment; EXPLAIN; cursor pagination.
- **Mini-project:** multi-user chat history service.
- **Exit:** justify index, transaction and storage choices.
- **Mistake:** dumping every state blob into JSON.
- **Interview:** schema for multi-tenant conversation history.

## Phase 4 — ML + math foundations

- **Purpose:** learn experimental and statistical thinking.
- **Prereq:** Python/numpy basics.
- **Concepts:** train/val/test, overfit, metrics, gradients, vectors, probability.
- **Depth:** 2–3.
- **Exercises:** classifier; metric calculator; cosine similarity from scratch.
- **Mini-project:** semantic-vs-keyword similarity notebook/service.
- **Resources:** ISLR selected chapters; 3Blue1Brown linear algebra/calculus intuition; scikit-learn guides.
- **Exit:** explain leakage, F1, calibration, gradient descent, cosine.
- **Mistake:** equations without experiments.
- **Interview:** precision vs recall; why held-out evals matter.

## Phase 5 — Deep learning + PyTorch

- **Purpose:** understand neural computation/training.
- **Prereq:** Phase 4.
- **Concepts:** tensors, autograd, modules, loss, optimizers, batching, normalization.
- **Depth:** 2.
- **Exercises:** tiny MLP; overfit tiny dataset; inspect gradients.
- **Mini-project:** text classifier in PyTorch.
- **Resources:** PyTorch official tutorials; Dive into Deep Learning selected sections.
- **Exit:** write a training loop without copy-paste.
- **Mistake:** spending months tuning CNNs irrelevant to target role.
- **Interview:** forward/backward pass, batch, optimizer.

## Phase 6 — NLP + Transformers

- **Purpose:** understand LLM internals enough to reason about behavior.
- **Prereq:** phases 4–5.
- **Concepts:** tokenization, embeddings, attention, Q/K/V, masks, residuals, normalization, decoder generation.
- **Depth:** 3.
- **Exercises:** tokenize real prompts; calculate toy attention; implement miniature attention block.
- **Mini-project:** inspect a small open transformer and visualize token probabilities.
- **Resources:** *Attention Is All You Need*.[17T] The Illustrated Transformer; Hugging Face course.
- **Exit:** narrate prompt→token generation technically.
- **Mistake:** memorizing architecture diagrams.
- **Interview:** why causal mask, KV cache, multi-head attention.

## Phase 7 — LLM API engineering

- **Purpose:** use models professionally without framework magic.
- **Prereq:** backend + transformer intuition.
- **Concepts:** messages, structured outputs, tools, streaming, rate limits, token accounting, retries, model routing.
- **Depth:** 4.
- **Exercises:** provider adapter; schema extraction; tool loop manually.
- **Mini-project:** production-grade structured extraction API.
- **Resources:** current provider official docs; OpenAI Responses API as one example.[5]
- **Exit:** robust typed client with tests and usage metrics.
- **Mistake:** notebook-only API calls.
- **Interview:** retry/fallback design and structured output reliability.

## Phase 8 — Prompt/context engineering

- **Purpose:** convert requirements into testable model behavior.
- **Prereq:** phase 7.
- **Concepts:** instruction hierarchy, examples, context, schemas, injection, versioning.
- **Depth:** 3–4.
- **Exercises:** create eval set before editing prompt; ablation tests.
- **Mini-project:** classify/extract 3 business document types with regression suite.
- **Exit:** demonstrate measured improvement.
- **Mistake:** “magic words” and vibe testing.
- **Interview:** prompt vs tool vs RAG vs fine-tune.

## Phase 9 — Embeddings + IR

- **Purpose:** build retrieval intuition independent of vector DB vendors.
- **Prereq:** vectors + text processing.
- **Concepts:** inverted index, TF-IDF, BM25, dense retrieval, hybrid, rerank, RRF, metrics.
- **Depth:** IR 3–4.
- **Exercises:** implement toy TF-IDF/BM25; brute-force vector search; compare metrics.
- **Mini-project:** hybrid search engine.
- **Resources:** Stanford IR book; DPR paper.[18]
- **Exit:** measure Recall@K/MRR/NDCG on labeled queries.
- **Mistake:** only measuring final chat quality.
- **Interview:** BM25 vs dense; reranker vs retriever.

## Phase 10 — Vector search

- **Purpose:** understand ANN/index/storage trade-offs.
- **Prereq:** phase 9.
- **Concepts:** exact NN, HNSW, IVF, PQ, filter interaction, recall/latency.
- **Depth:** 3.
- **Exercises:** pgvector exact vs HNSW benchmark.
- **Mini-project:** searchable document corpus with metadata filters.
- **Resources:** pgvector docs.[7]
- **Exit:** choose index based on measured constraints.
- **Mistake:** using approximate search on tiny data without baseline.
- **Interview:** HNSW memory/recall trade-off.

## Phase 11 — Production RAG

- **Purpose:** design complete ingestion/query pipelines.
- **Prereq:** phases 7–10.
- **Concepts:** parsing, chunking, lineage, hybrid retrieval, rerank, citations, ACL.
- **Depth:** 4.
- **Exercises:** chunking experiment; citation verifier; permission-scoped retrieval.
- **Mini-project:** enterprise document Q&A.
- **Resources:** RAG paper + retrieval literature.[17][18]
- **Exit:** prove retrieval and answer quality with evals.
- **Mistake:** prompt tweaking when retrieval is broken.
- **Interview:** diagnose irrelevant RAG results.

## Phase 12 — Advanced RAG

- **Purpose:** learn advanced techniques selectively.
- **Prereq:** strong baseline RAG.
- **Concepts:** multi-query, decomposition, hierarchical/parent-child, graph-assisted, corrective/adaptive RAG.
- **Depth:** 3–4 for techniques you actually need.
- **Exercises:** ablation per technique.
- **Mini-project:** multi-hop research QA.
- **Exit:** each added technique shows measured gain worth complexity.
- **Mistake:** “advanced RAG” architecture before benchmark baseline.
- **Interview:** justify whether graph RAG is necessary.

## Phase 13 — Tool calling + safe actions

- **Purpose:** connect model reasoning to deterministic systems.
- **Prereq:** phase 7 + backend security.
- **Concepts:** tool schemas, validation, auth, idempotency, approval, audit.
- **Depth:** 4.
- **Exercises:** read vs write tools; failure injection; duplicate-call tests.
- **Mini-project:** support assistant with CRM/order tools.
- **Exit:** model cannot bypass permission policy.
- **Mistake:** exposing broad “run_sql” or “call_api” super-tools.
- **Interview:** secure refund/tool flow.

## Phase 14 — Workflows, LangChain, LangGraph, agents

- **Purpose:** orchestrate multi-step behavior with explicit state.
- **Prereq:** tools/RAG/evals.
- **Concepts:** state machine, graph, reducers, checkpoints, interrupts, bounded loops.
- **Depth:** 3.
- **Exercises:** implement workflow plain Python then LangGraph.
- **Mini-project:** human-approved research workflow.
- **Resources:** LangChain v1/LangGraph docs.[1][2][3][6]
- **Exit:** can explain why each LLM decision exists.
- **Mistake:** agent loop instead of known workflow.
- **Interview:** agent vs state machine.

## Phase 15 — Evaluation engineering

- **Purpose:** turn AI quality into an engineering signal.
- **Prereq:** any meaningful AI app.
- **Concepts:** golden sets, judges, human labels, regression, online/offline evals.
- **Depth:** 4.
- **Exercises:** build reusable eval harness and CI threshold.
- **Mini-project:** eval service for RAG + tool trajectories.
- **Exit:** no significant AI change ships without eval result.
- **Mistake:** judge score with no calibration.
- **Interview:** design eval for customer-support agent.

## Phase 16 — Security + reliability

- **Purpose:** make system safe under hostile/edge inputs.
- **Prereq:** RAG/tools.
- **Concepts:** injection, exfiltration, excessive agency, tenant isolation, output handling, quotas.
- **Depth:** 3.
- **Exercises:** red-team corpus; cross-tenant tests; malicious tool args.
- **Mini-project:** hardened version of earlier assistant.
- **Resources:** OWASP GenAI Top 10, NIST GenAI Profile.[8][26]
- **Exit:** explicit threat model and security tests.
- **Mistake:** “guardrail prompt” as security architecture.
- **Interview:** indirect prompt injection scenario.

## Phase 17 — Observability + LLMOps

- **Purpose:** operate and debug in production.
- **Prereq:** deployed AI app.
- **Concepts:** spans, prompt/model/eval versioning, traces, dashboards, releases, A/B/canary.
- **Depth:** 3.
- **Exercises:** trace every RAG/tool stage; failure dashboard.
- **Mini-project:** add production tracing/cost/eval dashboard.
- **Exit:** answer “why did this response fail?” from trace evidence.
- **Mistake:** logging sensitive prompts indiscriminately.
- **Interview:** model upgrade rollout plan.

## Phase 18 — Deployment/cloud/distributed systems

- **Purpose:** productionize at realistic scale.
- **Prereq:** backend.
- **Concepts:** Docker, cloud primitives, queues, autoscaling, probes, secrets, K8s basics.
- **Depth:** cloud 3, Docker 3, K8s 2.
- **Exercises:** containerize; load test; provider outage simulation.
- **Mini-project:** cloud-deployed RAG API with workers.
- **Resources:** Docker/Kubernetes official docs.[30][31]
- **Exit:** safe CI/CD deploy + rollback + health checks.
- **Mistake:** K8s before one containerized app works.
- **Interview:** design for provider outage and 10× load.

## Phase 19 — Open-weight models + fine-tuning

- **Purpose:** understand when hosted APIs are not enough.
- **Prereq:** transformers + evals.
- **Concepts:** HF, quantization, vLLM, VRAM/KV cache, LoRA/QLoRA, SFT/DPO.
- **Depth:** 2–3.
- **Exercises:** run small local model; benchmark; LoRA small task.
- **Mini-project:** compare hosted vs self-hosted model on same eval/cost profile.
- **Resources:** Hugging Face PEFT/vLLM docs; LoRA paper.[27][28][29]
- **Exit:** defend buy-vs-host/fine-tune decision.
- **Mistake:** fine-tuning before a clean eval dataset.
- **Interview:** RAG vs fine-tuning; batching/KV cache.

## Phase 20 — System design, portfolio, capstone, interviews

- **Purpose:** demonstrate independent end-to-end engineering.
- **Prereq:** all core phases.
- **Concepts:** requirements, SLOs, scale, cost, threat model, eval, product trade-offs.
- **Depth:** 3–4.
- **Exercises:** weekly system-design cases.
- **Mini-project:** capstone below.
- **Exit:** can defend architecture under failure/cost/security questions.
- **Mistake:** demo UI without engineering evidence.
- **Interview:** staff-level trade-off discussions.

---

# Part XXX — Parallel learning tracks

## 48. Six-track curriculum

```text
TRACK A — SOFTWARE
Python → FastAPI → SQL/Postgres → Redis/queues → Distributed systems
      │
      └──────────────────────────────┐
                                     ▼
TRACK B — AI FOUNDATIONS         Production backend
ML → DL/PyTorch → NLP → Transformers → LLM internals
                       │
                       ▼
TRACK C — LLM APPLICATIONS
Provider SDK → Prompt/context → Structured outputs → Tool calling
                       │
                       ├─────────────┐
                       ▼             ▼
TRACK D — RETRIEVAL            TRACK E — ORCHESTRATION
Embeddings → IR → Vector → RAG  Tools → State → Graphs → Agents
                       │             │
                       └──────┬──────┘
                              ▼
TRACK F — PRODUCTION
Testing → Evals → Security → Observability → LLMOps → Cloud/Deployment
                              │
                              ▼
                    Production Applied AI
```

### Learning priority labels

**MUST KNOW NOW:** Python professional basics, HTTP/FastAPI, SQL, LLM APIs, structured outputs, prompt/context basics, token/cost awareness, eval basics.

**LEARN SOON:** embeddings, IR, vector search, RAG, tools, async/concurrency, security, observability.

**LEARN LATER:** LangGraph depth, advanced RAG, Kubernetes, self-hosting, fine-tuning.

**OPTIONAL SPECIALIZATION:** CUDA, distributed training, graph RAG specialization, multimodal video, research-level RL/alignment.

---

# Part XXXI — Checkpoint exams

## Checkpoint A — Software/Backend readiness (after Phase 3)

**Conceptual:** explain async I/O, connection pooling, idempotency, JWT validation, SSE vs WebSocket.

**Coding:** build `/chat/stream` with cancellation, timeout, typed errors and tests.

**Debugging:** find an event-loop blocking call causing 20 concurrent chats to freeze.

**Architecture:** design conversation persistence for 100k users.

**Mini-project:** streaming backend with auth, Postgres history and Redis rate limit.

**Move on when:** you can build it without following a tutorial line-for-line.

## Checkpoint B — AI foundations (after Phase 6)

Explain train/val/test, cross-entropy, gradient, embedding, attention, causal mask, logits, temperature and KV cache. Code cosine similarity and a tiny attention calculation.

**Move on when:** you can narrate prompt→next-token generation and understand tensor dimensions at a moderate level.

## Checkpoint C — Retrieval/RAG (after Phase 12)

Given 100 labeled queries, benchmark lexical, dense, hybrid and reranked retrieval. Diagnose at least five failure classes. Build citations and ACL filtering.

**Move on when:** you improve retrieval through metrics rather than prompt intuition.

## Checkpoint D — Tools/agents (after Phase 14)

Build a three-tool workflow twice: plain Python state machine and graph orchestration. Inject tool timeouts, malformed arguments and duplicate requests.

**Move on when:** you can justify every nondeterministic branch and enforce termination.

## Checkpoint E — Production AI (after Phase 18)

Threat model, eval suite, traces, cost dashboard, load test, provider-failure drill, CI/CD rollback.

**Move on when:** a failed answer can be traced to retrieval/model/tool/orchestration with evidence.

## Checkpoint F — Capstone/job readiness (Phase 20)

45-minute design interview + 90-minute coding exercise + 30-minute RAG debugging case + 30-minute security case. Present capstone metrics and one failed experiment you rejected.

---

# Part XXXII — Project-based curriculum

## Project 1 — Resilient LLM API client

- **Objective:** call a provider cleanly without framework abstraction.
- **Learn:** SDK, tokens, retry/timeout, typing, logging.
- **Requirements:** sync + async methods, timeout, retry, usage capture, mock provider.
- **Architecture:** CLI/library → provider adapter.
- **Stack:** Python, `httpx`/official SDK, Pydantic, pytest.
- **Evaluation:** schema/response fixtures; transient failure tests.
- **Security:** env/secrets, no key logging.
- **Deploy:** package/library + CI.
- **Résumé:** “Built typed, resilient multi-model client with bounded retries, usage telemetry and automated contract tests.”

## Project 2 — Structured information extractor

- **Objective:** turn invoices, résumés, support tickets or policy text into a versioned Pydantic schema.
- **Concepts learned:** schema-constrained generation, validation, null/unknown handling, field provenance, deterministic post-processing.
- **Functional requirements:** single + batch endpoint; schema version; required/optional fields; enum normalization; “unknown” rather than fabrication; per-field source span when possible.
- **Architecture:** FastAPI → extraction service → model gateway → schema validator → Postgres result store; queue for large batches.
- **Recommended stack:** Python, FastAPI, Pydantic, PostgreSQL, provider SDK.
- **Implementation phases:** first prompt baseline → constrained schema → validators → batch worker → versioned evals.
- **Testing:** validator unit tests; malformed inputs; empty/contradictory documents; provider timeout; schema migration tests.
- **Evaluation:** 100+ labeled examples; exact match for categorical fields; numeric/date accuracy; precision/recall for optional fields; hallucinated-field rate.
- **Security:** treat document instructions as data; no tools; redact sensitive traces.
- **Deployment:** Dockerized API + worker.
- **Production enhancements:** human review for low-confidence/high-value records, active-learning queue, schema drift dashboard.
- **Résumé:** “Built a schema-constrained LLM extraction service with versioned Pydantic contracts, field-level evaluation and human-review fallbacks.”

## Project 3 — Long-document summarization service

- **Objective:** summarize documents too large or structured for one naïve prompt.
- **Concepts learned:** context budgeting, document hierarchy, map/reduce vs hierarchical summarization, factuality checks.
- **Functional requirements:** short/long summary modes, section summaries, executive summary, preserved source section references, cancellation.
- **Architecture:** upload → parser → structural sections → parallel section summaries → synthesis → factuality/source check.
- **Stack:** FastAPI, async workers, object storage, PostgreSQL.
- **Implementation phases:** single-call baseline → token-aware split → hierarchical summary → source-linked claims → cache.
- **Testing:** deterministic parser tests, empty/duplicate sections, large input, cancellation, retry behavior.
- **Evaluation:** human pairwise preference, required-fact coverage, unsupported-claim rate, latency and token cost.
- **Security:** malicious document text cannot alter system behavior or invoke actions.
- **Deployment:** API plus queue worker.
- **Production enhancements:** summary templates by document type, incremental resummarization after source change.
- **Résumé:** “Designed hierarchical long-document summarization with source-linked synthesis, async processing and factuality regression tests.”

## Project 4 — Production-shaped streaming chatbot

- **Objective:** learn the backend mechanics hidden by chat demos.
- **Concepts learned:** SSE/WebSocket, async generators, cancellation, history truncation/summarization, persistence, rate limits.
- **Functional requirements:** auth, create conversation, stream response, stop generation, retry failed turn, persist usage and latency.
- **Architecture:** client → FastAPI → auth/quota → conversation store → model gateway → SSE stream → persistence/trace.
- **Stack:** FastAPI, PostgreSQL, Redis, provider SDK.
- **Implementation phases:** unauthenticated stream → persisted history → auth → quotas → cancellation → fallback.
- **Testing:** disconnect mid-stream, provider timeout, duplicate request, concurrent messages, long history.
- **Evaluation:** TTFT, p50/p95 total latency, failure rate, average tokens/turn.
- **Security:** ownership checks on conversation IDs, rate limits, safe trace retention.
- **Deployment:** Docker + managed Postgres/Redis.
- **Production enhancements:** per-conversation model routing, context compaction, moderation/policy hooks as required.
- **Résumé:** “Built a cancellable streaming LLM chat backend with persistent conversations, quotas, fallback routing and latency/token telemetry.”

## Project 5 — Semantic search engine

- **Objective:** understand embeddings before adding generation.
- **Concepts learned:** embedding vectors, cosine/dot product, exact vs ANN, Recall@K.
- **Functional requirements:** ingest records; batch embeddings; query; metadata filters; top-K; relevance benchmark.
- **Architecture:** ingestion → embeddings → Postgres/pgvector; query → query embedding → nearest-neighbor search.
- **Stack:** Python, Postgres, pgvector, FastAPI.
- **Implementation phases:** brute-force NumPy baseline → exact pgvector → HNSW → filters → benchmark.
- **Testing:** deterministic embedding mocks, filter correctness, empty corpus, embedding-version mismatch.
- **Evaluation:** Recall@1/5/10, MRR, p50/p95 search latency, index size.
- **Security:** tenant/document scope in SQL filter.
- **Deployment:** single service + managed Postgres initially.
- **Production enhancements:** embedding migration strategy, index-build jobs, relevance-label UI.
- **Résumé:** “Implemented and benchmarked exact and HNSW semantic retrieval with labeled Recall@K/MRR evaluation and metadata filtering.”

## Project 6 — Hybrid lexical + dense search

- **Objective:** learn why semantic search alone is not enough.
- **Concepts learned:** inverted indexes/BM25-style lexical ranking, dense retrieval, RRF, reranking, query classes.
- **Functional requirements:** lexical, dense and hybrid modes; exact identifiers/acronyms; filters; optional cross-encoder reranker.
- **Architecture:** query → lexical retriever + dense retriever in parallel → RRF → reranker → results.
- **Stack:** PostgreSQL full-text or Elasticsearch/OpenSearch plus pgvector; Python.
- **Implementation phases:** lexical baseline → dense baseline → fusion → reranking → query-class analysis.
- **Testing:** IDs, misspellings, acronyms, synonyms, negative queries, permission filters.
- **Evaluation:** Recall@K/MRR/NDCG by query category; latency/cost ablation.
- **Security:** apply mandatory filters within each retriever, not after fusion.
- **Deployment:** search service separated only if scaling justifies it.
- **Production enhancements:** query rewrite, synonyms, adaptive reranking.
- **Résumé:** “Improved domain retrieval using hybrid lexical+dense search, RRF and reranking, validated with query-class NDCG/MRR benchmarks.”

## Project 7 — Production RAG system

- **Objective:** build the first portfolio-grade enterprise RAG application.
- **Concepts learned:** parsing, chunk lineage, hybrid retrieval, context assembly, citations, retrieval vs generation evaluation.
- **Functional requirements:** PDF/DOCX/HTML ingestion; async indexing; source versioning; document ACL; search + chat; citations; abstention.
- **Architecture:** object storage → ingestion queue → parser/chunker → lexical/vector indexes; query → scoped retrieval → fusion/rerank → context → LLM → citation validator.
- **Stack:** FastAPI, PostgreSQL/pgvector, object storage, Redis/queue, optional reranker.
- **Implementation phases:** one format → multiple formats → hybrid retrieval → rerank → citations → ACL → eval harness.
- **Testing:** parser fixtures, source updates, deleted docs, duplicate chunks, permission revocation, injection documents.
- **Evaluation:** Recall@K/MRR/NDCG; answer correctness; groundedness; citation precision/coverage; abstention accuracy.
- **Security:** permissions before retrieval; source text treated as untrusted; no cross-tenant context.
- **Deployment:** API + ingestion workers + DB + object store.
- **Production enhancements:** incremental indexing, connector sync, admin reprocessing, source freshness dashboard.
- **Résumé:** “Built ACL-aware hybrid RAG with structure-preserving ingestion, reranking, citations and retrieval/end-to-end evaluation.”

## Project 8 — Advanced RAG experimentation laboratory

- **Objective:** learn advanced RAG scientifically instead of collecting techniques.
- **Concepts learned:** experiment design, ablations, multi-query, parent-child, hierarchical retrieval, compression, query decomposition.
- **Functional requirements:** feature flags for each technique; frozen eval dataset; comparable cost/latency logs; experiment report.
- **Architecture:** shared corpus/eval harness with pluggable retrieval pipeline stages.
- **Stack:** your Project 7 stack plus experiment config/versioning.
- **Implementation phases:** baseline freeze → one technique at a time → interaction experiments → decision memo.
- **Testing:** config reproducibility, deterministic corpus/version, fallback when rewrite/reranker fails.
- **Evaluation:** quality delta *and* latency/cost delta; significance/variance where sample size permits.
- **Security:** advanced query rewriting cannot weaken mandatory ACL filters.
- **Deployment:** primarily an internal lab; best configuration can promote to Project 7.
- **Production enhancements:** automated nightly benchmark and regression visualization.
- **Résumé:** “Created an ablation-driven RAG benchmark platform comparing query rewriting, parent-child retrieval, reranking and hybrid search under quality/latency/cost constraints.”

## Project 9 — Safe tool-calling assistant

- **Objective:** connect LLM decisions to real actions without surrendering control.
- **Concepts learned:** typed tools, authorization, idempotency, approval, audit, tool error normalization.
- **Functional requirements:** at least three read tools and two write tools; per-tool scopes; confirmation before sensitive action; idempotency key.
- **Architecture:** LLM → proposed tool call → schema validator → policy/auth → approval if required → deterministic executor → observation → model.
- **Stack:** FastAPI, Pydantic, Postgres, provider SDK.
- **Implementation phases:** read-only tool → validated writes → policy layer → approvals → retries/failure injection.
- **Testing:** malformed arguments, duplicate calls, unauthorized user, tool timeout, partial downstream failure.
- **Evaluation:** task success, correct-tool rate, argument accuracy, forbidden-action rate, duplicate-side-effect rate.
- **Security:** least privilege, explicit scopes, audit events, no generic “call arbitrary API” tool.
- **Deployment:** backend service with mock or sandbox enterprise APIs.
- **Production enhancements:** risk-tiered approval policy, tool circuit breakers, per-tool rate limits.
- **Résumé:** “Engineered typed LLM tool execution with least-privilege authorization, idempotency, human approvals and auditable side effects.”

## Project 10 — Text-to-SQL application

- **Objective:** build a constrained natural-language interface to relational data.
- **Concepts learned:** schema retrieval, semantic layer, query generation, SQL validation, sandbox execution.
- **Functional requirements:** read-only queries; approved schemas; row/column policy; row limit; timeout; explanation and result table.
- **Architecture:** question → schema/table retrieval → SQL proposal → parser/AST rules → authorization → EXPLAIN/cost check → read-only execution → answer.
- **Stack:** PostgreSQL, SQL parser, FastAPI, LLM API.
- **Implementation phases:** single schema → schema retrieval → validator → security controls → answer synthesis.
- **Testing:** injection attempts, DDL/DML, expensive cross joins, forbidden columns, ambiguous business terms.
- **Evaluation:** execution accuracy, result correctness, invalid SQL rate, unsafe query rate, latency.
- **Security:** DB role cannot write; server enforces tenant predicates/semantic layer; model never gets privileged credentials.
- **Deployment:** isolated read replica or sandbox DB for portfolio.
- **Production enhancements:** curated metrics layer, query templates, analyst feedback.
- **Résumé:** “Built a read-only text-to-SQL system with schema retrieval, AST validation, query-cost controls and database-enforced access policies.”

## Project 11 — Stateful graph workflow

- **Objective:** learn orchestration where state and resumability matter.
- **Concepts learned:** nodes, state, reducers, conditional edges, checkpoints, interrupts, idempotent resume.
- **Functional requirements:** multi-step case workflow; user can pause/resume; human approval gate; retry after node failure.
- **Architecture:** FastAPI → workflow runtime → durable checkpointer → tool/model nodes → approval UI/API.
- **Stack:** LangGraph or equivalent, PostgreSQL checkpointer, FastAPI.
- **Implementation phases:** plain-Python state machine → graph port → persistence → interrupts → failure recovery.
- **Testing:** node crash, replay/resume, duplicate approval, out-of-order client events, max-step bounds.
- **Evaluation:** workflow completion rate, incorrect branch rate, resume correctness, step count/cost.
- **Security:** state ownership, approval authorization, idempotent side effects.
- **Deployment:** API + durable DB-backed graph state.
- **Production enhancements:** workflow version migration, operator dashboard, dead-letter/recovery path.
- **Résumé:** “Built a resumable human-in-the-loop AI workflow with durable checkpoints, bounded routing and failure recovery.”

## Project 12 — Enterprise knowledge assistant

- **Objective:** move from “documents in a folder” to enterprise knowledge architecture.
- **Concepts learned:** connectors, ACL synchronization, multi-tenancy, source freshness, deletion propagation.
- **Functional requirements:** organizations/users; role-scoped collections; connector sync; citations; admin ingestion; usage quotas; feedback.
- **Architecture:** source connectors → change feed/poller → ingestion → permission-aware index; chat/search API → ACL retrieval → response.
- **Stack:** FastAPI, Postgres/pgvector, object storage, queue, Redis, identity provider mock.
- **Implementation phases:** local docs → tenant model → ACL → connector simulation → stale/deletion handling → admin dashboard.
- **Testing:** access change after indexing, user removal, source deletion, tenant collision, corrupted source.
- **Evaluation:** retrieval quality by department/tenant; cross-tenant leakage = zero in test corpus; source freshness lag.
- **Security:** source permissions are authoritative; deny by default; audit document access.
- **Deployment:** cloud managed services; optionally separate workers/search tier.
- **Production enhancements:** SCIM/group sync simulation, data residency routing, per-tenant encryption/key policy.
- **Résumé:** “Designed a multi-tenant enterprise knowledge assistant with synchronized source ACLs, deletion propagation, hybrid retrieval and auditability.”

## Project 13 — AI customer-support agent/workflow

- **Objective:** combine knowledge, customer context, tools, policy and escalation.
- **Concepts learned:** intent routing, policy RAG, transactional tools, escalation, quality/business metrics.
- **Functional requirements:** classify request; retrieve policy; retrieve account/order; draft answer; perform allowed actions; escalate uncertain/high-risk cases.
- **Architecture:** router → policy retrieval/account tools → deterministic policy checks → action/approval → response/escalation.
- **Stack:** FastAPI, workflow graph, RAG, mock CRM/order APIs, Postgres.
- **Implementation phases:** FAQ only → account reads → ticket creation → constrained refund simulation → escalation.
- **Testing:** angry/adversarial users, conflicting policy, unavailable backend, duplicate refund request, prompt injection inside CRM notes.
- **Evaluation:** correct resolution, containment, escalation precision/recall, policy violation rate, action accuracy, CSAT proxy/human rating.
- **Security:** transaction limits, approval threshold, account ownership, immutable audit.
- **Deployment:** staged with “draft only” mode before enabling actions.
- **Production enhancements:** online review queues, business KPI experiment, policy-change regression suite.
- **Résumé:** “Implemented a support AI combining policy RAG and authorized account tools, with escalation thresholds, audit trails and task-level evaluation.”

## Project 14 — AI research assistant

- **Objective:** build a long-running evidence-gathering system rather than a chat loop.
- **Concepts learned:** task decomposition, search/retrieval iteration, evidence ledger, source deduplication, checkpoints, citation verification.
- **Functional requirements:** research plan; source collection; claim/source ledger; gap detection; pause/resume; final cited report.
- **Architecture:** planner/workflow → search/file tools → source store → evidence extraction → synthesis → citation verifier.
- **Stack:** graph workflow, search/file APIs, Postgres, object storage.
- **Implementation phases:** fixed research workflow → adaptive search branch → evidence ledger → verifier → resumability.
- **Testing:** dead links, contradictory sources, duplicate sources, irrelevant search results, tool outages.
- **Evaluation:** source authority, claim support, citation correctness, coverage, cost, step count, human pairwise quality.
- **Security:** treat web content as hostile; never execute instructions found in sources; egress/tool scopes.
- **Deployment:** durable worker architecture for long tasks.
- **Production enhancements:** source-domain policies, freshness scoring, budget-aware planner.
- **Résumé:** “Built a durable research assistant with evidence-ledger state, iterative retrieval, citation verification and bounded search budgets.”

## Project 15 — Multimodal document analyst

- **Objective:** handle documents where layout, scans, tables and images matter.
- **Concepts learned:** OCR/document vision, page geometry, table extraction, multimodal prompts, provenance.
- **Functional requirements:** native and scanned PDFs; tables/figures; page-level citations; structured extraction; user-visible low-confidence warnings.
- **Architecture:** file → document classifier → native parser and/or OCR/vision → layout structure → multimodal/semantic index → answer/extraction.
- **Stack:** document parser/OCR tooling, multimodal model API, object storage, Postgres/pgvector.
- **Implementation phases:** native PDF → scanned PDF → tables → figures → multimodal retrieval.
- **Testing:** rotated scans, low resolution, multi-column pages, merged table cells, image-only pages.
- **Evaluation:** OCR/extraction accuracy, table cell accuracy, page citation accuracy, grounded QA.
- **Security:** PII handling and secure temporary file processing.
- **Deployment:** asynchronous ingestion with CPU/OCR worker pool.
- **Production enhancements:** human correction interface and re-indexing after correction.
- **Résumé:** “Developed a multimodal document intelligence pipeline for scanned/native PDFs with table-aware extraction, page provenance and grounded Q&A.”

## Project 16 — Multi-tenant production AI SaaS

- **Objective:** prove full-stack production engineering, not a standalone AI demo.
- **Concepts learned:** tenancy, quotas, metering, auth/RBAC, RAG, tools, streaming, LLMOps, security, cost.
- **Functional requirements:** organizations, invitations/roles, projects/workspaces, document ingestion, chat/search, safe tools, feedback, admin usage, deletion/export.
- **Architecture:** frontend → API gateway/FastAPI → auth/tenant layer → orchestrator → retrieval/model/tool gateways → Postgres/Redis/object store/queue → observability.
- **Stack:** Python/FastAPI, Postgres/pgvector, Redis, object storage, queue, React optional, Docker, cloud CI/CD.
- **Implementation phases:** tenant foundation → AI features → eval/security → metering → deployment → resilience/load tests.
- **Testing:** tenant isolation, quota races, deleted user, failed payment-like quota state, provider outage, queue backlog, schema migrations.
- **Evaluation:** combined task quality + latency + reliability + cost dashboards.
- **Security:** RLS/tenant filters, scoped object prefixes, audit, secret management, retention/deletion.
- **Deployment:** production-like cloud architecture; Kubernetes optional, not required for credibility.
- **Production enhancements:** per-tenant model policy, regional routing, SSO mock, SLA dashboard.
- **Résumé:** “Designed and deployed multi-tenant Applied AI SaaS with ACL-aware hybrid RAG, typed tool execution, evaluation gates, tracing, cost attribution and streaming.”

---

# Part XXXIII — Capstone

## 49. Capstone: Enterprise AI Operations Copilot

Build a multi-tenant assistant for a fictional enterprise that supports knowledge Q&A, incident/ticket workflows, database read queries, and human-approved operational actions.

### Functional requirements

- organizations, users, roles;
- upload/connect documents;
- background ingestion;
- PDF/DOCX/HTML parsing;
- lexical + dense search;
- reranking;
- source citations;
- typed tool calls;
- workflow graph with checkpoints;
- approval before write actions;
- streaming answers/events;
- conversation state;
- feedback;
- evaluation dashboard/data;
- trace and token/cost capture;
- model routing/fallback;
- retention/deletion controls.

### Architecture

```text
React/Next UI
   │ SSE
API Gateway
   │
FastAPI
 ├─ Auth/RBAC/tenant policy
 ├─ Conversation service ───── PostgreSQL
 ├─ AI orchestration
 │    ├─ retrieval service ─── PostgreSQL FTS + pgvector
 │    ├─ reranker
 │    ├─ model gateway ─────── hosted models / optional vLLM
 │    └─ tool gateway
 │          ├─ ticket API
 │          ├─ read-only SQL
 │          └─ mock enterprise API
 ├─ approval service
 └─ audit service

Queue ── ingestion/eval workers
Object storage ── source files/parsed artifacts
Redis ── rate limit/cache/ephemeral coordination
Observability ── traces/metrics/logs
CI/CD ── tests → eval gate → deploy → canary
```

### Suggested database design

```text
organizations(id, name, created_at)
users(id, ...)
memberships(user_id, org_id, role)
conversations(id, org_id, user_id, ...)
messages(id, conversation_id, role, content, ...)
runs(id, conversation_id, model_route, prompt_version, cost, latency, ...)
run_steps(id, run_id, type, payload_ref, status, ...)
documents(id, org_id, source_uri, version, acl_policy, ...)
document_chunks(id, document_id, embedding, text, section_path, page, ...)
tools(id/name/config ...)
tool_executions(id, run_id, tool, idempotency_key, approval_id, ...)
approvals(id, user_id, action_hash, decision, ...)
feedback(id, run_id, rating, category, ...)
eval_cases(id, suite_id, input, rubric, expected ...)
eval_runs(id, code_version, model_version, prompt_version, metrics ...)
audit_events(id, org_id, actor, action, resource, timestamp ...)
```

### API sketch

```text
POST   /v1/auth/login
POST   /v1/conversations
GET    /v1/conversations/{id}
POST   /v1/conversations/{id}/responses:stream
POST   /v1/documents
GET    /v1/documents/{id}/status
POST   /v1/search
POST   /v1/approvals/{id}/decision
GET    /v1/runs/{id}/trace-summary
POST   /v1/feedback
POST   /v1/admin/evals/run
GET    /v1/admin/evals/{run_id}
```

### Directory structure

```text
app/
  api/
  auth/
  config/
  db/
    models/
    repositories/
  ai/
    models/
    prompts/
    structured_outputs/
    routing/
    retrieval/
    reranking/
    tools/
    workflows/
    memory/
    evals/
  ingestion/
    parsers/
    chunkers/
    workers/
  observability/
  security/
  services/
  workers/
tests/
  unit/
  integration/
  retrieval/
  evals/
  security/
infra/
  docker/
  terraform/
  k8s/  # optional initially
scripts/
docs/
```

### Milestones

1. backend skeleton + auth + Postgres;
2. direct model streaming;
3. document ingestion;
4. lexical+dense retrieval;
5. reranker + citations;
6. golden RAG eval suite;
7. typed tools;
8. graph workflow + approval;
9. security hardening;
10. traces/cost/perf;
11. Docker/cloud deploy;
12. CI eval gate + canary;
13. load/failure tests;
14. polished demo + engineering report.

### Evaluation plan

- 150+ RAG questions across easy/exact/multi-hop/negative cases;
- labeled relevant sources/chunks;
- Recall@5/10, MRR/NDCG;
- correctness, groundedness, citation precision;
- 50 adversarial prompt-injection cases;
- 30 cross-tenant access cases;
- 50 tool tasks with expected tool/args/result;
- load test p50/p95 TTFT and end latency;
- cost distribution per task type.

### Testing strategy

- unit: parsers, filters, schemas, routing policy;
- integration: DB + retrieval + tools;
- contract: provider adapters;
- eval: fixed model task suite;
- security: injection, ACL, tool auth, secret leakage;
- chaos: provider 429/500/timeout, reranker down, queue backlog;
- performance: concurrency/load.

### README structure

1. problem statement;
2. architecture diagram;
3. why AI is justified;
4. threat model;
5. retrieval design;
6. agent/workflow design;
7. eval methodology + actual results;
8. latency/cost benchmarks;
9. failure modes and rejected approaches;
10. local setup;
11. deployment;
12. roadmap.

### Résumé bullets

- Designed and deployed a multi-tenant enterprise AI copilot using FastAPI, PostgreSQL/pgvector, Redis and asynchronous workers, supporting streaming responses, ACL-aware hybrid retrieval and source-grounded citations.
- Built a versioned evaluation harness measuring retrieval Recall@K/MRR/NDCG, answer groundedness, citation accuracy and tool-task success; integrated regression thresholds into CI/CD.
- Implemented typed, authorized tool execution with idempotency, human approval and audit trails, plus tracing and per-request latency/token/cost attribution.

### Interview discussion points

Be ready to defend: why Postgres+pgvector; why hybrid search; why rerank; where state lives; failure behavior; model routing; prompt-injection defenses; tenant isolation; why graph workflow vs agent loop; eval design; cost per request; scaling bottleneck; what you would change at 100× traffic.

---

# Part XXXIV — Portfolio strategy

## 50. Credible portfolio

You do **not** need 15 polished repositories. Build 4–6 strong public artifacts:

1. **Retrieval laboratory** — classical+dense+hybrid benchmarks.
2. **Production RAG** — citations, ACL, evals, ingestion.
3. **Tool/workflow system** — safe tool execution + graph + approval.
4. **Capstone SaaS** — full production concerns.
5. Optional **open-weight/fine-tuning benchmark**.
6. Optional **multimodal document system**.

### Tutorial clone vs production portfolio

| Tutorial clone | Production-quality |
|---|---|
| “chat with PDF” | versioned ingestion, parsing, ACL, eval corpus |
| vector search only | lexical+dense+rerank benchmark |
| one happy path | failure injection and retries |
| screenshots | live/demo + architecture + metrics |
| no tests | unit/integration/eval/security tests |
| API key in `.env.example` only | real secret-management design |
| no latency/cost | measured p50/p95 + per-request cost |
| “agent” loop | bounded state graph + approval + audit |
| README setup only | architecture decisions + trade-offs + failed experiments |

GitHub should show clean commits, typed code, reproducible setup, tests, architecture diagrams, eval datasets (sanitized/synthetic), CI and a clear license.

---

# Part XXXV — Interview preparation

## 51. Interview map by domain and depth

| Domain | Beginner | Intermediate | Senior | Staff+ |
|---|---|---|---|---|
| Python | generator vs list? | async cancellation? | design provider abstraction | set org-wide Python service standards |
| Backend | HTTP status/REST | SSE vs WS | idempotent tool API | multi-region AI API architecture |
| DB | index basics | JSONB vs normalized | tenant isolation/pagination | schema/platform evolution |
| ML | overfit? | precision/recall trade-off | eval leakage/calibration | measurement strategy across products |
| DL | gradient? | attention flow | inference bottlenecks | decide where deeper model work pays off |
| Transformers | token/logit? | Q/K/V, mask | KV cache/context trade-offs | model architecture implications for platform |
| LLM APIs | basic call | streaming/retry/schema | gateway/fallback/budget | multi-provider governance |
| Prompting | zero/few-shot | versioned eval loop | context architecture | prompt standards and release governance |
| Embeddings | cosine | query/doc encoders | embedding migration | retrieval platform strategy |
| IR | keyword vs semantic | BM25/hybrid/rerank | metric/latency tuning | search architecture across tenants/products |
| RAG | what is it? | chunking/rerank | ACL/citations/evals | enterprise knowledge platform design |
| Agents | tool call? | workflow vs agent | checkpoints/approval | autonomy boundary and platform policy |
| Evals | test examples | golden set/judge | online/offline gating | org-wide quality governance |
| Security | injection | indirect injection/tool auth | tenant isolation/audit | threat model/platform controls |
| Observability | logs | traces/token cost | debug trajectories | AI SLOs and incident strategy |
| LLMOps | prompt versions | eval release gate | canary/model migration | platform lifecycle architecture |
| Cloud | container basics | queues/autoscaling | outage/failover | platform/multi-region/cost strategy |
| System design | simple chatbot | enterprise RAG | multi-tenant agent SaaS | build-vs-buy/platform/team boundary |

### Coding exercises

- bounded async map with retries;
- LRU/cache key design;
- SSE stream endpoint;
- parse/validate tool call;
- chunk text while preserving sections;
- cosine/top-k brute-force retrieval;
- merge ranked lists with RRF;
- idempotent action endpoint;
- conversation/message schema;
- simple state-machine agent loop.

---

# Part XXXVI — Debugging playbook

## 52. “The answer hallucinated”

```text
Was required fact present in source corpus?
  no → source/data problem
  yes
   ↓
Did retrieval return it?
  no → retrieval problem
  yes
   ↓
Was correct evidence retained in final context?
  no → context assembly/rerank problem
  yes
   ↓
Did prompt require evidence/abstention/citation?
  no → prompt contract problem
  yes
   ↓
Did model ignore/misreason over evidence?
  yes → model/task limitation; try better model/decomposition/tool
```

## 53. “RAG returns irrelevant documents”

Check in order:

1. relevance labels/eval query are correct;
2. parsing did not corrupt text;
3. chunk boundaries and headers;
4. metadata/ACL filters;
5. lexical baseline;
6. embedding model/domain fit;
7. similarity metric/normalization;
8. query wording/rewrite;
9. K too low/high;
10. ANN index recall settings;
11. hybrid fusion;
12. reranker.

## 54. “Agent keeps looping”

Check:

- no explicit max steps/time/token budget;
- state does not record completed work;
- tool returns ambiguous result;
- tool error is interpreted as “try again forever”;
- termination prompt is vague;
- graph edge routes back unconditionally;
- same tool allowed repeatedly without dedupe;
- model never sees authoritative success signal.

Add deterministic stop conditions before prompt tweaks.

## 55. “Latency exploded”

Trace: retrieval → rerank → model TTFT → output length → tool waits → sequential calls → retries → queue. Optimize the dominant stage, not the one you personally find interesting.

## 56. “Cost exploded”

Look for context bloat, repeated history, too many retrieved chunks, verbose tool observations, high output token caps, agent loops, expensive model route, retry storms, no prompt/prefix cache.

---

# Part XXXVII — Resources

## 57. Primary / secondary / deep-dive sequence

### Python
- **Primary:** Python official docs + typing/asyncio docs.
- **Secondary:** *Effective Python*.
- **Deep dive:** selected *Fluent Python* chapters.

### FastAPI/backend
- **Primary:** FastAPI official docs.[4]
- **Secondary:** HTTP semantics / MDN; PostgreSQL official docs.
- **Deep dive:** *Designing Data-Intensive Applications* (distributed/data-system thinking).

### ML
- **Primary:** *Introduction to Statistical Learning* selected chapters.
- **Secondary:** scikit-learn user guide.
- **Deep dive:** CS229 notes selectively.

### Deep learning/PyTorch
- **Primary:** PyTorch tutorials.
- **Secondary:** *Dive into Deep Learning*.
- **Deep dive:** CS231n neural-network fundamentals selectively.

### Transformers/LLMs
- **Primary:** *Attention Is All You Need* + a high-quality visual explainer.[17T]
- **Secondary:** Hugging Face course.
- **Deep dive:** annotated transformer implementation.

### Information retrieval
- **Primary:** *Introduction to Information Retrieval* (Manning et al.) selected chapters.
- **Secondary:** Elasticsearch/OpenSearch relevance docs or equivalent search-engine docs.
- **Deep dive:** DPR and reranking papers.[18]

### RAG
- **Primary:** original RAG paper + build your own baseline.[17]
- **Secondary:** provider/framework retrieval guides only after baseline.
- **Deep dive:** selected modern retrieval/reranking literature.

### Agents/workflows
- **Primary:** manual tool loop + state machine.
- **Secondary:** LangGraph docs.[1][2][3]
- **Deep dive:** ReAct/Toolformer.[24][25]

### Security
- **Primary:** OWASP GenAI Security Project.[8][9]
- **Secondary:** NIST GenAI Profile.[26]
- **Deep dive:** organization-specific threat modeling/cloud IAM guidance.

### Open-weight/fine-tuning
- **Primary:** Hugging Face Transformers/PEFT docs.[28][29]
- **Secondary:** vLLM docs.[27]
- **Deep dive:** LoRA/DPO papers.[22]

**Outdated-risk flag:** framework API tutorials, model-specific snippets, vendor pricing/model names, agent abstractions and “best vector DB” articles age quickly. Re-check official docs whenever implementing them.

---

# Part XXXVIII — Paper-reading sequence

## 58. Minimal high-value reading list

| # | Paper | Why read it | Prerequisites | First-pass sections | Safely skip initially |
|---|---|---|---|---|---|
| 1 | **Attention Is All You Need**[17T] | Transformer architecture and attention vocabulary | vectors, matrix multiplication, softmax | abstract, intro, model architecture, attention figures | detailed optimizer/training schedule and every experiment |
| 2 | **BERT**[20] | Understand encoder-only bidirectional representation learning | transformer basics | intro, architecture/pretraining tasks, fine-tuning setup | exhaustive benchmark tables |
| 3 | **Language Models are Few-Shot Learners (GPT-3)**[21] | Autoregressive scaling and in-context learning | decoder-only transformer | intro, approach, few-shot framing, limitations | most individual task tables |
| 4 | **Scaling Laws for Neural Language Models**[19] | Why model/data/compute scaling became central | loss/cross-entropy intuition | abstract, main empirical relationships, discussion | curve-fitting derivations |
| 5 | **Dense Passage Retrieval**[18] | Dual-encoder dense retrieval and retrieval evaluation | embeddings, dot product, IR metrics | architecture, negatives/training intuition, evaluation | training hyperparameter minutiae |
| 6 | **Retrieval-Augmented Generation**[17] | Canonical parametric + retrieved-memory architecture | transformers + retrieval | motivation, RAG architecture, retrieval/generation interaction | full training derivations |
| 7 | **InstructGPT**[23] | SFT → preference labels → reward/RLHF pipeline | basic fine-tuning | pipeline figure, data collection, evaluation | PPO implementation details first pass |
| 8 | **LoRA**[28] | Parameter-efficient adaptation intuition | linear algebra, transformer layers | method, low-rank update idea, results | rank-deficiency theory details |
| 9 | **DPO**[22] | Preference optimization without full RLHF loop | SFT/RLHF concept, probability/log-probs | motivation, high-level objective, experiments | derivation on first read |
| 10 | **ReAct**[24] | Tool/action/observation loops and why they help | prompting + tools | method diagrams, examples, failure analysis | benchmark details |
| 11 | **Toolformer**[25] | Historical/technical perspective on learned API use | language modeling + tools | method overview, API-call examples, conclusions | detailed self-supervised filtering math |

### How to read papers efficiently

Use three passes:

```text
Pass 1: abstract → figures → conclusion → “what problem?”
Pass 2: method → inputs/outputs → evaluation design → limitations
Pass 3: equations/implementation only when needed for your work
```

Write a one-page note for each paper with five headings: **problem, key idea, architecture, evidence, what still matters in 2026**. This prevents paper reading from becoming passive collection.

---

# Part XXXIX — What to postpone

## 59. THINGS YOU CAN SAFELY POSTPONE

- writing custom CUDA kernels;
- distributed foundation-model pretraining;
- advanced tensor/pipeline parallel implementation;
- RL theory beyond preference/alignment intuition;
- transformer architecture research variants in exhaustive detail;
- advanced measure-theoretic probability;
- proofs of optimization convergence;
- building a vector database from scratch;
- writing Kubernetes operators;
- service mesh internals;
- multi-agent systems before mastering single-agent/workflows;
- knowledge graphs unless your problem is relation-centric;
- fine-tuning large models before you have strong evals;
- training your own embedding model unless retrieval experiments justify it.

**Do not postpone:** SQL, async, retrieval metrics, auth, testing, evaluation, prompt injection, observability and cost measurement.

---

# Part XL — Marketability and durability

## 60. Skill ranking (5 = highest)

| Skill | Employability | Durability | Difficulty | Time to competence | Senior importance |
|---|---:|---:|---:|---:|---:|
| Python | 5 | 5 | 3 | 3 | 5 |
| Backend/API engineering | 5 | 5 | 4 | 4 | 5 |
| SQL/Postgres | 5 | 5 | 3 | 3 | 5 |
| Async/distributed reliability | 5 | 5 | 4 | 4 | 5 |
| LLM API/tool integration | 5 | 4 | 3 | 2 | 5 |
| Evaluation | 5 | 5 | 4 | 4 | 5 |
| IR/RAG | 5 | 5 | 4 | 4 | 5 |
| Security | 5 | 5 | 4 | 4 | 5 |
| Observability | 5 | 5 | 3 | 3 | 5 |
| Cloud/Docker | 5 | 5 | 3 | 3 | 5 |
| Transformers | 4 | 5 | 4 | 4 | 4 |
| LangGraph | 4 | 3 | 3 | 2 | 3 |
| LangChain | 4 | 2–3 | 2 | 2 | 2–3 |
| Vector DB product APIs | 4 | 2–3 | 2 | 2 | 3 |
| Fine-tuning | 3–4 | 4 | 4 | 4 | 3–4 |
| GPU serving | 3–4 | 4 | 5 | 4 | 4 for infra roles |
| Multi-agent frameworks | 3 | 2 | 3 | 2 | 2–3 |
| Prompt “tricks” | 2 | 1 | 1 | 1 | 1 |

### Durable vs transient

**Durable:** data modeling, HTTP, concurrency, distributed systems, IR, ranking, evaluation, security, measurement, product judgment.

**Medium-lived implementation knowledge:** FastAPI, Postgres/pgvector, PyTorch, Docker, Kubernetes, OpenTelemetry.

**Fast-changing:** model names, provider endpoint shapes, agent SDK APIs, framework decorators, benchmark leaderboards, pricing.

---

# Part XLI — Daily/weekly plans

## 61. 5 hours/week

```text
Tue 45m theory
Thu 45m coding drill
Sat 2h project
Sun 1h project + 30m review
Ratio: 30% theory / 55% building / 15% review-eval
```

Expect ~12–18 months to traverse the full roadmap deeply; job-relevant capability can appear much earlier if projects are focused.

## 62. 10 hours/week

```text
Mon 1h theory
Wed 1h coding
Fri 1h theory/eval
Sat 4h project
Sun 2h project + 1h review
Ratio: 30% theory / 60% build / 10% review
```

Strong pace for working professionals.

## 63. 15 hours/week

```text
Mon–Thu 1.5h/day = 6h
Fri 1h review
Sat 4h project
Sun 4h project
Ratio: 25% theory / 65% building / 10% review
```

At this pace, a serious first job-ready portfolio can be built in roughly 6–9 focused months depending on existing backend/cloud strength.

---

# Part XLII — Exactly what to do first

## 64. First 7 days

**Goal:** finish a small production-shaped LLM backend, not a notebook.

### Day 1
- map role/architecture;
- set up Python project (`uv`/venv), lint, typing, pytest;
- make one raw provider API call.

### Day 2
- learn message roles/instructions, tokens, context, temperature/sampling;
- implement typed provider wrapper with timeout and error taxonomy.

### Day 3
- implement structured extraction into Pydantic model;
- add 20-case eval dataset;
- measure schema/task accuracy.

### Day 4
- FastAPI endpoint around extraction;
- async provider call;
- request validation; exception mapping.

### Day 5
- implement streaming chat endpoint;
- learn cancellation and bounded concurrency;
- record token usage + latency.

### Day 6
- PostgreSQL conversation/message schema;
- persist runs and usage;
- add tests.

### Day 7
- Dockerize;
- README architecture;
- write one-page retrospective: failure modes, cost, latency, what you would improve.

**Skip this week:** LangChain, agents, fine-tuning, Kubernetes, vector DB product comparison.

## 65. First 30 days

### Week 1
LLM API + FastAPI + structured outputs + streaming.

### Week 2
ML/math essentials in parallel with embeddings. Implement cosine similarity and brute-force semantic search. Create a 50-query relevance set.

### Week 3
Learn lexical IR/BM25, PostgreSQL full-text + pgvector. Build lexical, dense and hybrid retrieval; measure Recall@K/MRR.

### Week 4
Build RAG v1: ingestion → chunks → retrieval → rerank optional → citations → eval. Add 50–100 RAG questions and trace retrieval/model latency.

**30-day milestone:** you can build and evaluate a small RAG service from first principles without LangChain.

## 66. First 90 days

### Days 31–45
- deepen transformer/ML foundations;
- build safe tool calling manually;
- auth + idempotent write tool + approval.

### Days 46–60
- learn LangChain concepts by mapping them to code already written;
- build LangGraph workflow with state/checkpoint/interrupt;
- add failure/retry tests.

### Days 61–75
- evaluation harness;
- RAG metrics, answer rubric, LLM judge calibration;
- OWASP threat model and injection tests;
- observability/traces/cost dashboard.

### Days 76–90
- cloud deploy using Docker;
- queue/worker for ingestion;
- load test and provider-outage fallback;
- polish one portfolio project with architecture/eval/security/cost sections.

**90-day milestone:** one deployed, measurable, secure-ish production-shaped AI system—not merely a demo.

---

# Part XLIII — Production readiness checklist

## 67. Before calling a system production-ready

### Identity and data
- [ ] Authentication
- [ ] Authorization/RBAC
- [ ] Tenant isolation
- [ ] Document/row-level permissions
- [ ] Encryption in transit/at rest
- [ ] Retention policy
- [ ] User/org deletion workflow
- [ ] Data residency/provider review

### API/reliability
- [ ] Input validation
- [ ] Rate limits/quotas
- [ ] Per-stage timeouts
- [ ] Retry policy
- [ ] Idempotency for side effects
- [ ] Circuit breaker/fallback
- [ ] Graceful degradation
- [ ] Cancellation
- [ ] Queue/backpressure plan

### LLM reliability
- [ ] Structured outputs where appropriate
- [ ] Semantic output validation
- [ ] Abstention policy
- [ ] Bounded loops/steps/tokens
- [ ] Model-routing policy
- [ ] Prompt/version tracking

### Retrieval
- [ ] Source versioning
- [ ] Parser/chunker versions
- [ ] ACL before retrieval
- [ ] Retrieval eval dataset
- [ ] Recall/ranking metrics
- [ ] Citation verification

### Security
- [ ] Direct prompt-injection tests
- [ ] Indirect prompt-injection tests
- [ ] Malicious document tests
- [ ] Tool authorization
- [ ] Human approval for sensitive actions
- [ ] Secret leakage tests
- [ ] Cross-tenant tests
- [ ] Sandboxed code execution where applicable
- [ ] Immutable audit trail

### Evaluation
- [ ] Golden dataset
- [ ] Regression suite
- [ ] Human-labeled calibration subset
- [ ] Deterministic checks
- [ ] LLM-judge calibration
- [ ] Adversarial tests
- [ ] CI/CD quality gate

### Observability
- [ ] Trace ID end-to-end
- [ ] Model/provider/version
- [ ] prompt/retrieval/tool versions
- [ ] token counts
- [ ] TTFT + p50/p95 latency
- [ ] error taxonomy
- [ ] retrieval trace
- [ ] tool trace
- [ ] user feedback
- [ ] cost tracking
- [ ] alerting/SLOs

### Operations
- [ ] CI/CD
- [ ] rollback
- [ ] canary/staged deployment
- [ ] backup/restore
- [ ] disaster recovery expectations
- [ ] dependency/model provider outage runbook
- [ ] load test
- [ ] capacity/cost forecast

---

# Part XLIV — Final skill matrix

## 68. Beginner → Staff+

| Skill | Beginner | Intermediate | Job Ready | Senior | Staff+ |
|---|---|---|---|---|---|
| Python | syntax | typed modules | async/tested services | performance/concurrency design | standards/platform libraries |
| FastAPI/backend | endpoints | auth/DB | streaming/retries/queues | SLO/failure architecture | platform/service boundaries |
| SQL/Postgres | CRUD | indexes/transactions | production schemas/pools | scale/RLS/query tuning | data architecture governance |
| ML | terms | train/eval/metrics | sound experiments | metric/product trade-offs | org measurement strategy |
| DL/PyTorch | tensors | train small net | inspect/adapt models | inference/fine-tune decisions | deep specialization optional |
| Transformers | tokens | attention | moderate internals | inference trade-offs | architecture strategy |
| LLM APIs | simple call | streaming/tools | robust gateway client | routing/fallback/cost | provider platform/governance |
| Prompting | instructions | few-shot/schema | versioned eval-driven | context architecture | standards across products |
| Embeddings | use API | similarity | benchmark models | migrations/domain tuning | retrieval platform |
| IR | keyword search | BM25/dense | hybrid/rerank/evals | specialized tuning | search strategy |
| Vector search | insert/query | ANN basics | index/filter tuning | capacity/recall design | platform-scale indexing |
| RAG | basic QA | chunk+retrieve | secure hybrid RAG | advanced diagnosis/evals | enterprise knowledge platform |
| Tools | function call | schema | auth/idempotency | safe action systems | capability governance |
| Agents | loop concept | tool agent | bounded workflow | durable HITL systems | autonomy/platform policy |
| LangChain | copy example | understand abstractions | use selectively | remove/replace abstraction | framework choice standards |
| LangGraph | simple graph | state/routes | persistence/HITL | resilient orchestration | shared orchestration platform |
| Evals | examples | basic judge | golden/regression suite | calibrated online/offline | org-wide eval infrastructure |
| Security | awareness | injection | threat model + tests | enterprise controls | governance/platform guardrails |
| Observability | logs | model traces | end-to-end trace/cost | AI SLO/debugging | observability platform |
| LLMOps | prompt version | basic runs | gated deployments | canary/experiments | lifecycle platform |
| Docker | run image | Dockerfile | secure reproducible build | optimize/runtime policy | platform standards |
| Kubernetes | concept | deploy service | debug/probes/resources | scaling/reliability | platform specialization |
| Cloud | service names | basic deploy | IAM/network/storage/queue | resilience/cost | multi-region/platform |
| Fine-tuning | know term | run small LoRA | choose correctly | data/eval pipeline | specialist/platform |
| System design | components | small design | production AI design | cross-system trade-offs | org/platform architecture |
| Product judgment | use AI | identify uncertainty | design graceful UX | business/quality trade-offs | portfolio/platform strategy |

---

# Part XLV — Job-ready and senior definitions

## 69. “Job ready” means demonstrated capabilities

You can credibly apply for serious LLM/GenAI/Applied AI engineering roles when you can demonstrate all of the following:

- build a typed Python/FastAPI AI backend with auth, DB and streaming;
- use at least one major model API directly, including structured outputs and tools;
- explain transformer inference from tokenization to sampling;
- implement and evaluate lexical, dense and hybrid retrieval;
- build RAG with citations and document-level permissions;
- create a golden eval set and automated regression harness;
- build a safe tool-calling workflow with validation/idempotency/approval;
- explain workflow vs agent and implement bounded stateful orchestration;
- threat-model prompt injection, cross-tenant leakage and excessive agency;
- trace model/retrieval/tool calls and diagnose failures;
- measure p50/p95 latency and per-request cost;
- containerize and deploy one system;
- defend architecture and trade-offs in an interview;
- show 2–4 strong projects with actual metrics, tests and engineering writeups.

You do **not** need to train a transformer from scratch, write CUDA or publish papers.

## 70. Level progression

### Junior
Can implement scoped AI features with guidance. Understands API/RAG/tool basics and tests deterministic code.

### Mid-level
Owns features end-to-end, diagnoses retrieval/model/tool failures, writes evals, operates services and makes reasonable model/cost choices.

### Senior
Defines architecture, quality metrics, threat model, observability and rollout strategy; manages ambiguous requirements; mentors engineers; prevents needless agent/framework complexity.

### Staff
Designs shared patterns/platforms across teams: model gateway, eval infrastructure, retrieval platform, governance, cost/SLO standards. Influences product and architecture across multiple systems.

### Principal
Sets technical direction across organization/business units; balances build-vs-buy, research/product/platform investment, security/compliance, long-term architecture and organizational leverage.

---

# Part XLVI — Knowledge graph

## 71. Dependency graph

```text
                           ┌─────────────────┐
                           │ Product thinking│
                           └────────┬────────┘
                                    │
                                    ▼
┌─────────┐   ┌──────────┐   ┌───────────────┐
│ Python  ├──►│ FastAPI  ├──►│Backend systems │
└────┬────┘   └────┬─────┘   └──────┬────────┘
     │             │                │
     │             ▼                ▼
     │       SQL/Postgres      Async/queues/cache
     │             │                │
     └─────────────┴────────┬───────┘
                            ▼
                      LLM API layer
                            │
          ┌─────────────────┼─────────────────┐
          ▼                 ▼                 ▼
   Structured output    Prompt/context     Tool calling
          │                 │                 │
          └──────────┬──────┴────────┬────────┘
                     │               │
                     ▼               ▼

Linear algebra → Embeddings → Dense retrieval ─┐
                                               ├→ Hybrid retrieval → RAG → RAG evals
NLP/IR → Inverted index → BM25 ────────────────┘

ML → Deep Learning → Transformers → LLM internals
                              │          │
                              │          └→ Fine-tuning / open-weight serving
                              ▼
                         Model selection

RAG + Tools + State
        │
        ▼
Workflow / Graph orchestration
        │
        ▼
Bounded agents (when justified)

EVERYTHING ABOVE
   │
   ├→ Evaluation
   ├→ Security
   ├→ Observability
   ├→ Cost/Performance
   ├→ LLMOps
   └→ Cloud/Distributed systems
             │
             ▼
      Production Applied AI
```

---

# Part XLVII — One-page master roadmap

## 72. Compressed map

```text
FOUNDATION
Professional Python ── HTTP/FastAPI ── SQL/Postgres ── Async/Queues/Redis
       │                                      │
       ├──────────────────────┬───────────────┘
       │                      ▼
       │               Production Backend
       │
       ▼
AI UNDERSTANDING
Vectors/Probability → ML Basics → PyTorch/DL → NLP → Transformers → LLM Internals
       │                                                    │
       └───────────────┬────────────────────────────────────┘
                       ▼
LLM APPLICATION CORE
Provider SDK → Prompt/Context → Structured Output → Tool Calling
                       │                    │
                       │                    ▼
                       │                Safe Actions
                       │                    │
                       ▼                    ▼
RETRIEVAL          ORCHESTRATION
Embeddings         State machines
   ↓                   ↓
BM25 + Dense       Graph workflows
   ↓                   ↓
Hybrid/Rerank      Checkpoints/HITL
   ↓                   ↓
Vector indexes     Bounded agents
   ↓                   │
RAG ────────────────┬───┘
   ↓                │
RAG Evals           │
   └────────────────┘
          ▼
PRODUCTION DISCIPLINE
Evaluation → Security → Observability → Cost/Latency → LLMOps
          ↓
Docker/Cloud → Distributed systems → CI/CD → Scaling/DR
          ↓
ADVANCED / SELECTIVE
Multimodal • Open-weight serving • LoRA/QLoRA • Advanced RAG • K8s
          ↓
SYSTEM DESIGN + CAPSTONE + INTERVIEW
          ↓
PRODUCTION LLM APPLICATION / APPLIED AI ENGINEER
```

---

# Part XLVIII — The shortest practical recommendation

## 73. The order that maximizes career ROI

If you remember only one sequence, use this:

```text
1. Professional Python
2. FastAPI + SQL + async backend
3. Direct LLM APIs + structured outputs + streaming
4. Minimal ML/math + transformer understanding in parallel
5. Embeddings + classical IR
6. Vector search + hybrid retrieval + reranking
7. Production RAG + RAG evals
8. Safe tool calling
9. Workflows/state/LangGraph
10. Agents only after workflows
11. Evals + security + observability throughout
12. Docker/cloud/distributed systems
13. Open-weight/fine-tuning only after strong baselines
14. System design + capstone + portfolio
```

The most important mental shift is this:

> **An LLM application is a distributed software system containing one or more probabilistic components.**
>
> Your job is not to make the probabilistic component look magical. Your job is to constrain it, measure it, give it the right evidence and tools, protect the surrounding system, and engineer graceful behavior when it is wrong.

---

# Sources and current-technology references

The roadmap emphasizes durable concepts. Tool/API notes below were checked against current sources in September 2026.

1. LangGraph, **Persistence** — checkpointing, memory, time travel and fault tolerance. https://docs.langchain.com/oss/python/langgraph/persistence
2. LangGraph, **Overview** — durable execution, streaming, human-in-the-loop and low-level orchestration. https://docs.langchain.com/oss/python/langgraph/overview
3. LangGraph, **Interrupts** — pause/resume semantics and idempotency considerations. https://docs.langchain.com/oss/python/langgraph/interrupts
4. FastAPI, **Concurrency and async/await**. https://fastapi.tiangolo.com/async/
5. OpenAI API, **Responses API: create a model response** — structured output and tools. https://developers.openai.com/api/reference/cli/resources/responses/methods/create
6. LangChain, **What’s new in LangChain v1**. https://docs.langchain.com/oss/python/releases/langchain-v1
7. pgvector, **official repository/documentation** — exact search, HNSW, IVFFlat, filtering and multitenancy. https://github.com/pgvector/pgvector
8. OWASP GenAI Security Project, **Top 10 for LLM and GenAI**. https://genai.owasp.org/initiatives/top-10-for-llm-and-genai/
9. OWASP, **LLM06: Excessive Agency** / related current risk pages. https://genai.owasp.org/llmrisk/llm062025-excessive-agency/
10. OpenAI Agents SDK, **Agents**. https://openai.github.io/openai-agents-python/agents/
11. OpenAI Agents SDK, **Agent orchestration**. https://openai.github.io/openai-agents-python/multi_agent/
12. Dscout, **Applied AI Engineer – India** (current 2026 posting) — production LLM features, context engineering, eval harnesses, safeguards. https://job-boards.greenhouse.io/dscout/jobs/4370258009
13. Anthropic, **Applied AI Engineer, Enterprise Tech** — production LLMs, agents, evaluations, deployment. https://job-boards.greenhouse.io/anthropic/jobs/5057647008
14. Atomicwork, **Applied AI Engineer** — evaluation pipelines, RAG/embeddings, production systems. https://job-boards.greenhouse.io/atomicwork/jobs/5073381008
15. OpenAI API, **Graders/Evals**. https://platform.openai.com/docs/api-reference/graders
16. OpenAI Agents SDK, **Tracing**. https://openai.github.io/openai-agents-python/tracing/
17. Lewis et al., **Retrieval-Augmented Generation for Knowledge-Intensive NLP Tasks** (2020). https://arxiv.org/abs/2005.11401
17T. Vaswani et al., **Attention Is All You Need** (2017). https://arxiv.org/abs/1706.03762
18. Karpukhin et al., **Dense Passage Retrieval for Open-Domain Question Answering** (2020). https://arxiv.org/abs/2004.04906
19. Kaplan et al., **Scaling Laws for Neural Language Models** (2020). https://arxiv.org/abs/2001.08361
20. Devlin et al., **BERT: Pre-training of Deep Bidirectional Transformers for Language Understanding** (2018/2019). https://arxiv.org/abs/1810.04805
21. Brown et al., **Language Models are Few-Shot Learners** (2020). https://arxiv.org/abs/2005.14165
22. Rafailov et al., **Direct Preference Optimization** (2023). https://arxiv.org/abs/2305.18290
23. Ouyang et al., **Training Language Models to Follow Instructions with Human Feedback** (2022). https://arxiv.org/abs/2203.02155
24. Yao et al., **ReAct: Synergizing Reasoning and Acting in Language Models** (2022/2023). https://arxiv.org/abs/2210.03629
25. Schick et al., **Toolformer: Language Models Can Teach Themselves to Use Tools** (2023). https://arxiv.org/abs/2302.04761
26. NIST, **AI Risk Management Framework: Generative Artificial Intelligence Profile** (NIST AI 600-1). https://www.nist.gov/publications/artificial-intelligence-risk-management-framework-generative-artificial-intelligence
27. vLLM, **Automatic Prefix Caching** design documentation. https://docs.vllm.ai/ (see prefix caching design docs)
28. Hu et al., **LoRA: Low-Rank Adaptation of Large Language Models** (2021). https://arxiv.org/abs/2106.09685
29. Hugging Face, **PEFT documentation**. https://huggingface.co/docs/peft/en/index
30. Docker, **Manage secrets securely in Docker Compose**. https://docs.docker.com/compose/how-tos/use-secrets/
31. Kubernetes, **Ingress** — Kubernetes recommends Gateway for new development; Ingress API is frozen. https://kubernetes.io/docs/concepts/services-networking/ingress/

