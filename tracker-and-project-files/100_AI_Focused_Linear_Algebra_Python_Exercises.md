# 100 Python Hands-On Exercises: Linear Algebra for AI

A progressive, hands-on roadmap for mastering the linear algebra used in **machine learning, deep learning, neural networks, embeddings, transformers, LLMs, and retrieval-augmented generation (RAG)**.

## How to use this guide

- Work through the exercises in order, from beginner to advanced.
- Mark each exercise as complete by replacing `- [ ]` with `- [x]`.
- Where practical, implement the mathematics manually first and then with NumPy.
- Verify results using checks such as `np.allclose()`, examine array shapes, and test edge cases.
- For each exercise, write down **what the mathematics means** and **how it is used in AI**.

**Recommended tools:** Python 3, NumPy, Matplotlib; optionally Pandas and scikit-learn for verification.

**Reproducible randomness:** Use `np.random.default_rng(42)` when generating synthetic datasets.

## Learning roadmap

| Phase | Exercises | Topic | Level |
| --- | --- | --- | --- |
| 1 | 1–10 | Vectors and NumPy fundamentals | Beginner |
| 2 | 11–20 | Vector operations in AI | Beginner → Intermediate |
| 3 | 21–30 | Matrices and fundamental operations | Beginner → Intermediate |
| 4 | 31–40 | Linear transformations and matrix geometry | Intermediate |
| 5 | 41–50 | Vectorized distances, similarity, and feature processing | Intermediate |
| 6 | 51–60 | Linear equations and least-squares optimization | Intermediate |
| 7 | 61–70 | Eigenvalues, eigenvectors, and PCA | Advanced |
| 8 | 71–80 | Singular Value Decomposition and low-rank approximation | Advanced |
| 9 | 81–90 | Linear algebra inside neural networks | Advanced AI Engineering |
| 10 | 91–100 | Linear algebra for LLMs, Transformers, embeddings, and RAG | Advanced AI Engineering |

---

## Phase 1 — Vectors and NumPy fundamentals

**Exercises 1–10 · Beginner**

**Objective:** Understand how AI represents numerical features as vectors.

- [ ] **1. Create a feature vector.** Represent one house using five numerical features: area, bedrooms, bathrooms, age, and distance to the city. Examine its `shape`, `ndim`, `size`, and `dtype`.
- [ ] **2. Create different vector types.** Generate integer, `float32`, and `float64` vectors of 1,000 elements. Compare the memory consumed by each.
- [ ] **3. Manipulate feature values.** Given a vector containing negative and positive values, replace negative values with zero using boolean indexing, `np.maximum()`, and `np.where()`.
- [ ] **4. Calculate vector magnitude.** Implement the L1, L2, and infinity norms manually, then verify using `np.linalg.norm()`.
- [ ] **5. Normalize an embedding vector.** Generate a 128-dimensional random embedding and convert it into a unit vector with an L2 norm of 1.
- [ ] **6. Perform vector arithmetic.** Add, subtract, multiply, and divide two five-dimensional vectors elementwise. Explain why elementwise multiplication differs from the dot product.
- [ ] **7. Calculate the dot product manually.** Implement the dot product of two 10-dimensional vectors using a loop, then compare with `np.dot()` and `@`.
- [ ] **8. Calculate vector distances.** Find the Euclidean, Manhattan, and Chebyshev distances between two 20-dimensional feature vectors.
- [ ] **9. Implement cosine similarity.** Create two 100-dimensional embeddings and compute their cosine similarity using dot products and L2 norms. Handle zero-length vectors safely.
- [ ] **10. Build a simple embedding search.** Generate 100 random embeddings of dimension 64 and one query embedding. Return the five most similar embeddings using cosine similarity.

## Phase 2 — Vector operations in AI

**Exercises 11–20 · Beginner → Intermediate**

**Objective:** Understand how embeddings, vector similarity, and geometric relationships power AI.

- [ ] **11. Calculate angles between vectors.** Given two nonzero feature vectors, calculate the angle between them using the dot product and inverse cosine. Clip floating-point results into the valid range.
- [ ] **12. Compute vector projections.** Project a three-dimensional vector onto another vector. Verify the remaining component is orthogonal to the projection direction.
- [ ] **13. Test orthogonality.** Generate pairs of vectors and identify which are perpendicular by checking whether their dot product is approximately zero.
- [ ] **14. Implement featurewise maximum pooling.** Generate ten embedding vectors of dimension 128 and compute their featurewise maximum without Python loops.
- [ ] **15. Implement mean pooling.** Given 20 token embeddings of dimension 64, calculate the average embedding that represents the entire sentence.
- [ ] **16. Create weighted embeddings.** Combine five word embeddings using five importance weights. Compare the weighted average with the ordinary average.
- [ ] **17. Calculate a batch of dot products.** Given 100 pairs of 128-dimensional vectors, calculate the dot product for every pair using vectorization.
- [ ] **18. Compare similarity and distance.** Create three groups of 2D vectors with different directions and magnitudes. Compare Euclidean distance and cosine similarity to see how they rank neighbors differently.
- [ ] **19. Find the nearest centroid.** Given three class-centroid vectors and one input feature vector, determine the nearest class using Euclidean distance.
- [ ] **20. Build a nearest-centroid classifier.** Generate 300 feature vectors from three artificial classes. Calculate each class centroid and classify new examples using their nearest centroid.

## Phase 3 — Matrices and fundamental operations

**Exercises 21–30 · Beginner → Intermediate**

**Objective:** Understand how matrices represent datasets, images, features, and neural-network weights.

- [ ] **21. Represent an ML dataset as a matrix.** Create a matrix of 100 samples with five features each. Identify rows, columns, and dimensions.
- [ ] **22. Transpose a dataset.** Transpose a `100 × 5` matrix into a `5 × 100` matrix. Explain what the rows and columns now represent.
- [ ] **23. Extract rows and columns.** Select particular samples and features from a matrix using slicing, integer indexing, and boolean indexing.
- [ ] **24. Compute feature statistics.** Calculate the mean, minimum, maximum, and standard deviation of each feature in a `1000 × 10` dataset.
- [ ] **25. Center a data matrix.** Subtract the mean of each feature from every row using NumPy broadcasting. Verify that each column has a mean approximately equal to zero.
- [ ] **26. Perform matrix-vector multiplication.** Given a `4 × 3` feature matrix and a three-dimensional weight vector, calculate predictions manually and with `@`.
- [ ] **27. Implement matrix multiplication.** Multiply a `3 × 4` matrix by a `4 × 2` matrix. First use nested loops, then `np.matmul()`.
- [ ] **28. Understand incompatible dimensions.** Attempt multiplying matrices with incompatible shapes, inspect the error, and fix the operation using correct matrix dimensions.
- [ ] **29. Use identity and diagonal matrices.** Create identity and diagonal matrices. Verify that multiplication by the identity leaves a vector unchanged, while a diagonal matrix scales individual features.
- [ ] **30. Apply a linear layer to a batch.** Create 32 input samples with 10 features each, and a weight matrix mapping 10 features to 6 outputs. Calculate `Y = X @ W + b` using broadcasting.

## Phase 4 — Linear transformations and matrix geometry

**Exercises 31–40 · Intermediate**

**Objective:** Understand how matrices transform data geometrically and how neural networks learn transformations.

- [ ] **31. Implement 2D scaling.** Apply a `2 × 2` scaling matrix to 100 two-dimensional points and visualize how their positions change.
- [ ] **32. Implement 2D rotation.** Construct a rotation matrix and rotate a collection of 2D points by 30°, 60°, and 90°. Visualize the results.
- [ ] **33. Implement shearing.** Create horizontal and vertical shear matrices and apply them to a square-shaped set of points.
- [ ] **34. Combine transformations.** Apply rotation followed by scaling, then reverse the order. Demonstrate with a numerical example that matrix multiplication is generally not commutative.
- [ ] **35. Explore matrix rank.** Construct matrices with independent and dependent columns. Calculate their ranks using `np.linalg.matrix_rank()`.
- [ ] **36. Detect linear dependence.** Create a dataset where one feature is an exact combination of two others. Calculate the rank before and after adding the dependent feature.
- [ ] **37. Verify orthogonal transformations.** Construct a rotation matrix and verify that its transpose times itself equals the identity matrix.
- [ ] **38. Investigate determinants.** Calculate determinants of scaling, rotation, reflection, and singular matrices. Explain what the determinants indicate about area scaling and orientation.
- [ ] **39. Compute matrix inverses.** Create an invertible `3 × 3` matrix, calculate its inverse, and verify `A @ A_inv ≈ I` using `np.allclose()`.
- [ ] **40. Implement change of basis.** Define two different bases in 2D and convert a vector's coordinates from one basis to the other. Verify both coordinate descriptions represent the same geometric vector.

## Phase 5 — Vectorized distances, similarity, and feature processing

**Exercises 41–50 · Intermediate**

**Objective:** Build the core operations used by retrieval systems, clustering algorithms, and embedding models.

- [ ] **41. Calculate pairwise Euclidean distances.** Given 50 embeddings of dimension 32, create a `50 × 50` distance matrix using NumPy broadcasting.
- [ ] **42. Calculate pairwise cosine similarities.** Normalize 100 embeddings and construct their complete cosine-similarity matrix using matrix multiplication.
- [ ] **43. Understand Gram matrices.** Compute `X.T @ X` for a `200 × 5` dataset and investigate its symmetry and positive semidefinite properties.
- [ ] **44. Compare L1 and L2 distances.** Create examples where Manhattan distance and Euclidean distance produce different nearest-neighbor rankings.
- [ ] **45. Implement feature standardization.** Standardize a `1000 × 20` dataset with z-scores. Handle constant columns without dividing by zero.
- [ ] **46. Implement min-max scaling.** Transform feature values into `[0, 1]` and compare the resulting distances with distances from standardized data.
- [ ] **47. Study high-dimensional distances.** Generate datasets with 2, 10, 100, and 1,000 features. Compare the distribution of pairwise Euclidean distances as dimensionality increases.
- [ ] **48. Build a vectorized KNN predictor.** Implement K-nearest-neighbor classification using pairwise distances and majority voting, without relying on scikit-learn's KNN implementation.
- [ ] **49. Implement a similarity-based recommendation system.** Represent ten users and twenty items as embedding vectors. Recommend the five items most similar to one user's vector.
- [ ] **50. Optimize vector similarity search.** Compare a Python-loop implementation and a vectorized matrix-multiplication implementation for searching 10,000 embeddings of dimension 128.

## Phase 6 — Linear equations and least-squares optimization

**Exercises 51–60 · Intermediate**

**Objective:** Understand the mathematical foundations of regression models and optimization.

- [ ] **51. Solve a linear system.** Solve a set of three simultaneous linear equations using `np.linalg.solve()` and verify the solution.
- [ ] **52. Detect singular systems.** Construct a singular matrix, attempt solving its linear system, and explain why a unique solution cannot be guaranteed.
- [ ] **53. Investigate matrix conditioning.** Construct well-conditioned and ill-conditioned matrices. Compare their condition numbers and sensitivity to small input perturbations.
- [ ] **54. Compare inverse-based and direct solving.** Solve `Ax = b` using `np.linalg.solve(A, b)` and an explicit inverse. Compare accuracy and execution time for suitable invertible matrices.
- [ ] **55. Implement least-squares linear regression.** Given 100 samples with three features, estimate regression coefficients using `np.linalg.lstsq()`.
- [ ] **56. Understand residual vectors.** Predict target values with the regression model, calculate residuals, and compute mean squared error.
- [ ] **57. Implement the normal equation.** Estimate linear-regression coefficients using `np.linalg.solve(X.T @ X, X.T @ y)` on a full-column-rank dataset and compare with least squares.
- [ ] **58. Implement ridge regression.** Add L2 regularization to the regression objective and solve the regularized linear system. Avoid penalizing the intercept.
- [ ] **59. Handle redundant features.** Create a dataset with perfectly correlated columns. Compare least-squares, pseudoinverse, and ridge-regression solutions.
- [ ] **60. Implement gradient descent for linear regression.** Optimize the weight vector using the gradient of mean squared error. Compare the learned solution with `np.linalg.lstsq()`.

## Phase 7 — Eigenvalues, eigenvectors, and PCA

**Exercises 61–70 · Advanced**

**Objective:** Understand how AI identifies important directions in high-dimensional data.

- [ ] **61. Calculate eigenvalues and eigenvectors.** Create a symmetric `3 × 3` matrix and calculate its eigenvalues and eigenvectors using `np.linalg.eigh()`.
- [ ] **62. Verify the eigenvector equation.** For every eigenpair, verify numerically that `A @ v ≈ λ * v`.
- [ ] **63. Explore eigenvector geometry.** Transform a collection of 2D vectors using a symmetric matrix. Visualize its eigenvectors and how the matrix stretches or reverses those directions.
- [ ] **64. Build a covariance matrix manually.** Generate a `500 × 4` dataset, center its features, and calculate its covariance matrix using matrix multiplication.
- [ ] **65. Find principal components.** Calculate the eigenvectors and eigenvalues of a covariance matrix, sort them in descending order, and identify the dominant principal directions.
- [ ] **66. Implement PCA from scratch.** Reduce a 500-sample, 10-feature dataset to two principal components using covariance eigendecomposition.
- [ ] **67. Calculate explained variance ratios.** Determine how much variance each principal component captures and find the minimum number needed to preserve at least 95% of total variance.
- [ ] **68. Reconstruct data from PCA.** Reconstruct the original feature space from two, five, and eight principal components. Compare reconstruction mean squared errors.
- [ ] **69. Implement PCA whitening.** Transform full-rank centered data so that the retained principal-component coordinates have approximately unit variance and zero cross-covariance.
- [ ] **70. Build a PCA-based anomaly detector.** Train PCA on normal feature vectors and identify anomalies using reconstruction error.

## Phase 8 — Singular Value Decomposition and low-rank approximation

**Exercises 71–80 · Advanced**

**Objective:** Understand matrix factorization used in recommendation systems, dimensionality reduction, and model compression.

- [ ] **71. Perform Singular Value Decomposition.** Decompose an `8 × 6` matrix into `U`, `S`, and `Vᵀ` using `np.linalg.svd()`.
- [ ] **72. Reconstruct a matrix from SVD.** Multiply the SVD components to recover the original matrix and measure numerical reconstruction error.
- [ ] **73. Examine singular values.** Create full-rank and rank-deficient matrices, inspect their singular values, and estimate numerical rank.
- [ ] **74. Implement truncated SVD.** Retain only the largest `k` singular values to construct rank-1, rank-2, and rank-3 approximations of a matrix.
- [ ] **75. Build an image-compression experiment.** Represent a grayscale image as a matrix and approximate it with truncated SVD. Compare quality at different ranks.
- [ ] **76. Implement a movie recommendation experiment.** Create a user–movie rating matrix with synthetic low-rank structure. Factorize it with SVD and compare reconstructed scores with the original data, noting the limitations of treating missing ratings as zeros.
- [ ] **77. Calculate the pseudoinverse using SVD.** Implement the Moore–Penrose pseudoinverse from singular values, using a numerical tolerance, and compare with `np.linalg.pinv()`.
- [ ] **78. Compare PCA and SVD.** Apply both approaches to the same centered dataset and verify that their principal subspaces agree, accounting for possible sign differences.
- [ ] **79. Implement Latent Semantic Analysis.** Construct a small document-term TF-IDF matrix, apply truncated SVD, and compare document similarity in the reduced space.
- [ ] **80. Denoise a matrix using SVD.** Generate a low-rank signal matrix, add random Gaussian noise, and use truncated SVD to recover an approximation of the underlying signal.

## Phase 9 — Linear algebra inside neural networks

**Exercises 81–90 · Advanced AI Engineering**

**Objective:** Understand the actual matrix computations underlying neural-network training and inference.

- [ ] **81. Implement a single artificial neuron.** Given a feature vector, weight vector, and bias, compute its pre-activation output using a dot product.
- [ ] **82. Implement a fully connected neural-network layer.** Given 64 samples with 20 features, transform them into 10 output features using matrix multiplication and bias broadcasting.
- [ ] **83. Apply ReLU activation.** Implement ReLU using `np.maximum()` and apply it to the output matrix of a neural-network layer.
- [ ] **84. Implement sigmoid activation.** Calculate sigmoid activation for an array of neuron outputs, using a numerically stable implementation.
- [ ] **85. Build a two-layer neural network.** Implement a forward pass with architecture `Input(20) → Hidden(16) → Output(3)` using NumPy.
- [ ] **86. Implement stable softmax.** Convert a batch of model logits into class probabilities. Subtract the rowwise maximum before exponentiation to avoid numerical overflow.
- [ ] **87. Calculate neural-network loss.** Implement mean squared error and multiclass cross-entropy for batched predictions, paying attention to shapes and numerical stability.
- [ ] **88. Derive weight gradients using matrix operations.** For a linear layer `Y = XW + b`, compute the analytical gradients of a scalar mean-squared-error loss with respect to `W` and `b`.
- [ ] **89. Implement backpropagation for a two-layer network.** Apply the chain rule with matrix multiplication to compute gradients through a hidden ReLU layer and an output layer. Verify selected gradients using finite differences.
- [ ] **90. Train a neural network using NumPy.** Generate a nonlinear binary-classification dataset and train a small neural network using batch gradient descent without PyTorch or TensorFlow.

## Phase 10 — Linear algebra for LLMs, Transformers, embeddings, and RAG

**Exercises 91–100 · Advanced AI Engineering**

**Objective:** Apply linear algebra to actual components used in modern language-model and retrieval architectures.

- [ ] **91. Build an embedding lookup layer.** Create an embedding table for 1,000 token IDs, each mapped to a 64-dimensional vector. Convert a sequence of token IDs into an embedding matrix.
- [ ] **92. Implement masked mean pooling.** Given a padded batch of token embeddings, compute one sentence embedding per sequence while excluding padding tokens.
- [ ] **93. Implement sinusoidal positional encodings.** Generate positional encodings for 100 positions with embedding dimension 64, then add them to token embeddings.
- [ ] **94. Build Query, Key, and Value matrices.** Given a token embedding matrix of shape `10 × 64`, create learnable projection matrices and calculate `Q`, `K`, and `V`.
- [ ] **95. Calculate scaled dot-product attention scores.** Compute `Q @ K.T / sqrt(d_k)` and inspect how the scale factor affects the distribution of scores.
- [ ] **96. Implement causal masked attention.** Apply a causal mask, stable rowwise softmax, and attention-weighted value aggregation so each token attends only to itself and earlier tokens.
- [ ] **97. Implement multi-head self-attention.** Split 64-dimensional embeddings into four attention heads, compute attention independently, concatenate the outputs, and apply a final output projection.
- [ ] **98. Build a miniature RAG retrieval engine.** Create 1,000 synthetic document embeddings of dimension 384. Normalize them, embed a synthetic query, and retrieve the top five documents using cosine similarity.
- [ ] **99. Implement a low-rank weight update (LoRA).** Create a frozen weight matrix `W` and low-rank trainable matrices `A` and `B`. Compute `Y = XW + (α/r)XAB`, verify the shapes, and compare the number of trainable parameters with a full weight update.
- [ ] **100. Capstone — Build a miniature transformer encoder block using NumPy.** Combine token embeddings, positional encodings, multi-head self-attention, residual connections, layer normalization, and a feed-forward network. Verify every intermediate tensor shape and test the complete forward pass with random inputs.

---

## Suggested practice workflow

For **each** exercise:

1. **Understand the mathematics.** Write down what is being calculated and why it matters.
2. **Implement it manually.** Where practical, calculate it using basic Python operations or loops.
3. **Implement the NumPy version.** Use vectorization, broadcasting, and linear-algebra operations.
4. **Test your implementation.** Check shapes, boundary cases, mathematical properties, and numerical accuracy.
5. **Identify its AI application.** Explain its role in ML, neural networks, attention, embeddings, or RAG systems.

### Optional per-exercise notes template

Copy this template into your study notes when solving an exercise:

```md
### Exercise N — Title

**Mathematical intuition:**

**Input and expected output shapes:**

**Manual approach:**

**NumPy approach:**

**Tests and edge cases:**

**Where this is used in AI:**

**What I learned / questions I still have:**
```

**Progress:** 0 / 100 completed. Update the checkboxes as you go.
