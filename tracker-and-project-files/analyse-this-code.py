import numpy as np

# Step 1: Initialize a random number generator
# Fixed seed ensures reproducible results
rng = np.random.default_rng(42)

# Step 2: Generate 100 embeddings, each with 64 dimensions
embeddings = rng.normal(size=(100, 64))

# Step 3: Generate one query embedding with 64 dimensions
query_embedding = rng.normal(size=64)

# Step 4: Calculate dot products between each embedding and query
dot_products = embeddings @ query_embedding

# Step 5: Calculate L2 norms
embedding_norms = np.linalg.norm(embeddings, axis=1)
query_norm = np.linalg.norm(query_embedding)

# Step 6: Calculate cosine similarity
denominators = embedding_norms * query_norm

# Handle zero-length vectors safely
similarities = np.divide(
    dot_products,
    denominators,
    out=np.zeros_like(dot_products),
    where=denominators != 0
)

# Step 7: Get indices of the five highest cosine similarities
top_5_indices = np.argsort(similarities)[-5:][::-1]

# Step 8: Retrieve the five most similar embeddings
top_5_embeddings = embeddings[top_5_indices]

# Step 9: Display results
print("Embeddings shape:", embeddings.shape)
print("Query shape:", query_embedding.shape)

print("\nTop 5 most similar embeddings:")

for rank, index in enumerate(top_5_indices, start=1):
    print(
        f"Rank {rank}: Embedding {index}, "
        f"Cosine Similarity = {similarities[index]:.4f}"
    )

print("\nTop 5 embeddings shape:", top_5_embeddings.shape)
