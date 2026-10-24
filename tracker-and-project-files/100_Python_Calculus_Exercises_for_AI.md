# 100 Python Hands-On Calculus Exercises for Artificial Intelligence

> A structured, hands-on learning path from derivatives and integrals to backpropagation, gradient-based optimization, Transformers, LLMs, and generative AI.

## How to use this workbook

- Work through the 10 phases in order; check off an exercise by changing `- [ ]` to `- [x]`.
- First work out the mathematics on paper, then implement it manually in Python/NumPy, and finally verify against a trusted library or numerical approximation.
- Prefer vectorized NumPy operations for batches; use Matplotlib for visualizations.
- Start random experiments with `rng = np.random.default_rng(42)` for reproducibility.
- Use PyTorch for the automatic differentiation and Transformer exercises in phases 9–10; earlier phases primarily need NumPy, SciPy, and Matplotlib.
- For numerical gradient checks use central differences, avoid discontinuities, and compare absolute *and* relative error.

### Recommended libraries

```python
import numpy as np
import matplotlib.pyplot as plt
from scipy import integrate

rng = np.random.default_rng(42)
```

### Learning roadmap

| Phase | Exercises | Level | Subject |
|---|---:|---|---|
| 1 | 1–10 | Beginner | Functions, limits, and single-variable derivatives |
| 2 | 11–20 | Beginner → Intermediate | Partial derivatives, gradients, and vector calculus |
| 3 | 21–30 | Intermediate | Chain rule, activations, and Jacobian products |
| 4 | 31–40 | Intermediate | Gradient descent and first-order optimization |
| 5 | 41–50 | Intermediate → Advanced | Hessians, Taylor series, and second-order methods |
| 6 | 51–60 | Intermediate → Advanced | Backpropagation and neural-network training |
| 7 | 61–70 | Intermediate | Integration, differential equations, and probability |
| 8 | 71–80 | Advanced | Calculus for probabilistic machine learning |
| 9 | 81–90 | Advanced | Automatic differentiation and deep optimization |
| 10 | 91–100 | Advanced AI Engineering | Calculus in Transformers, LLMs, and generative AI |

---

## Phase 1 — Functions, limits, and single-variable derivatives

**Level:** Beginner  
**Goal:** See what a derivative measures: the local rate of change of a function.

- [ ] **1. Visualize AI loss functions.** Plot f(x)=x², f(x)=|x|, and f(x)=log(1+exp(x)) over a common range. Identify increasing, decreasing, flat, and nondifferentiable regions.

- [ ] **2. Approximate a limit numerically.** Calculate (sin(h))/h for successively smaller positive and negative h. Compare with the expected limit and observe floating-point effects for extremely small h.

- [ ] **3. Implement a derivative from first principles.** Approximate the derivative of f(x)=x² at several x values using [f(x+h)−f(x)]/h. Compare with the analytical derivative 2x.

- [ ] **4. Implement central differences.** Estimate derivatives of sin(x), exp(x), and x³ using forward, backward, and central differences. Plot errors against step size h.

- [ ] **5. Differentiate a polynomial.** Write a function returning the exact derivative of a polynomial whose coefficients are stored in a NumPy array. Verify the result against finite differences.

- [ ] **6. Explore the product rule.** For f(x)=x² sin(x), implement its analytical derivative using the product rule and check it numerically across a grid of x values.

- [ ] **7. Explore the quotient rule.** For f(x)=sin(x)/(1+x²), calculate the analytical derivative and verify it numerically; ensure the test points avoid unstable comparisons.

- [ ] **8. Apply the single-variable chain rule.** Differentiate f(x)=(3x²+1)⁵ analytically, implement the result, and verify with central differences at random inputs.

- [ ] **9. Investigate nondifferentiable points.** Plot ReLU(x)=max(0,x) and estimate slopes immediately to the left and right of x=0. Explain how practical deep-learning frameworks choose a subgradient at zero.

- [ ] **10. Build a reusable numerical derivative tester.** Write a central-difference function with configurable step size. Test at least five smooth functions and report absolute and relative errors versus exact derivatives.

---

## Phase 2 — Partial derivatives, gradients, and vector calculus

**Level:** Beginner → Intermediate  
**Goal:** Move from single inputs to multi-feature functions and vector-valued model outputs.

- [ ] **11. Plot a two-variable loss surface.** Create a contour plot and 3D surface of f(x,y)=x²+4y². Mark the minimum and examine why the surface is steeper in one direction.

- [ ] **12. Calculate partial derivatives manually.** For f(x,y)=3x²+2xy+y², implement ∂f/∂x and ∂f/∂y. Verify each using finite differences that change only one variable.

- [ ] **13. Compute a gradient vector.** Implement ∇f for f(x,y)=x²+3y²+xy. Plot gradient arrows on a 2D grid to see the direction of steepest local ascent.

- [ ] **14. Verify a directional derivative.** Choose a unit direction u and calculate ∇f(x)·u. Compare it with the finite-difference slope obtained by stepping along u.

- [ ] **15. Differentiate a dot product.** For f(w)=w·x with fixed x, derive the gradient with respect to w. Confirm that the gradient is x using a coordinatewise numerical gradient.

- [ ] **16. Differentiate squared Euclidean distance.** For f(w)=||w−t||², implement the gradient 2(w−t). Verify it for vectors of dimensions 2, 10, and 100.

- [ ] **17. Differentiate a quadratic form.** For f(w)=wᵀAw, derive and implement ∇f=(A+Aᵀ)w. Test both a symmetric matrix and a nonsymmetric matrix.

- [ ] **18. Calculate feature sensitivity.** Build a toy linear prediction y=w·x+b. Compute ∂y/∂x_i for each feature and compare sensitivities before and after rescaling features.

- [ ] **19. Construct a Jacobian matrix.** For F(x,y)=[x²+y, sin(xy), x−y], derive its 3×2 Jacobian and verify every entry numerically.

- [ ] **20. Explore gradient dimensions in a batch.** For X of shape (32,5) and w of shape (5,), compute y=Xw. Determine and verify the Jacobian shape of y with respect to w.

---

## Phase 3 — Chain rule, activations, and Jacobian products

**Level:** Intermediate  
**Goal:** Practice the differential building blocks that connect layers in deep learning.

- [ ] **21. Differentiate nested compositions.** Implement f(x)=sin(exp(x²)) and its chain-rule derivative; compare analytical and numerical results at moderate x values.

- [ ] **22. Implement the sigmoid derivative.** Calculate sigmoid(x) and its derivative σ(x)[1−σ(x)] using numerically stable methods. Visualize gradient saturation at large |x|.

- [ ] **23. Implement the tanh derivative.** Compute tanh(x) and 1−tanh²(x). Plot activation and derivative to identify where gradients become small.

- [ ] **24. Differentiate softplus.** Implement softplus(x)=log(1+exp(x)) stably and show numerically that its derivative equals sigmoid(x).

- [ ] **25. Compare ReLU and Leaky ReLU gradients.** Implement both activations and their derivatives away from zero. Compare how many units have zero or small gradients under random negative-biased inputs.

- [ ] **26. Derive the softmax Jacobian.** For a four-class logit vector, implement J=diag(p)−ppᵀ and verify entries against numerical differentiation of softmax.

- [ ] **27. Implement a Jacobian-vector product.** Without forming the full softmax Jacobian, compute Jv=p⊙(v−p·v). Compare with explicit J@v.

- [ ] **28. Implement a vector-Jacobian product.** For y=softmax(z) and upstream gradient g, compute Jᵀg efficiently. Verify against explicit Jacobian multiplication.

- [ ] **29. Differentiate log-sum-exp.** Implement a stable log-sum-exp function and verify that its gradient with respect to logits equals softmax.

- [ ] **30. Backpropagate through broadcasting.** For Y=X+b where X has shape (16,8) and b has shape (8,), implement the gradient of sum(Y²) with respect to b, summing contributions across the batch.

---

## Phase 4 — Gradient descent and first-order optimization

**Level:** Intermediate  
**Goal:** Use derivatives to reduce loss rather than just describe its behavior.

- [ ] **31. Minimize a 1D quadratic.** Implement gradient descent on f(w)=(w−3)². Plot w and loss across iterations from different starting positions.

- [ ] **32. Study learning-rate stability.** Run gradient descent on the same quadratic with learning rates below, at, and above the convergence threshold. Explain oscillation and divergence.

- [ ] **33. Optimize a two-parameter function.** Minimize f(x,y)=x²+10y² using gradient descent. Overlay the optimization path on loss contours.

- [ ] **34. Implement batch gradient descent for linear regression.** Generate a synthetic dataset with two features. Derive the MSE gradient with respect to weights and bias and fit parameters without an ML training library.

- [ ] **35. Compare scaled versus unscaled features.** Run gradient descent on linear regression with features on very different scales, then with standardized features. Compare stability and convergence.

- [ ] **36. Explore a nonconvex objective.** Optimize f(x)=x⁴−3x²+0.5x from different starting values. Plot trajectories and show how initial conditions can lead to different local minima.

- [ ] **37. Investigate saddle points.** For f(x,y)=x²−y², plot the surface and gradient field. Investigate what happens to gradient descent started exactly at and near (0,0).

- [ ] **38. Implement momentum optimization.** Add momentum to gradient descent on an elongated quadratic loss. Compare trajectories and iteration counts with vanilla gradient descent.

- [ ] **39. Implement RMSProp.** Maintain an exponential moving average of squared gradients and use it to adapt parameterwise step sizes. Compare convergence with ordinary gradient descent.

- [ ] **40. Implement Adam from scratch.** Implement first and second moments, bias correction, and parameter updates for Adam. Train the toy regression model and inspect its loss curve.

---

## Phase 5 — Hessians, Taylor series, and second-order methods

**Level:** Intermediate → Advanced  
**Goal:** Understand curvature, numerical conditioning, and why optimization can be difficult.

- [ ] **41. Build a Hessian matrix.** For f(x,y)=3x²+2xy+4y², derive the 2×2 Hessian, implement it, and confirm its entries with finite differences of the gradient.

- [ ] **42. Classify critical points.** Analyze the gradient and Hessian of several 2D functions at stationary points. Classify strict minima, strict maxima, and saddle points when the Hessian test is decisive.

- [ ] **43. Explore a first-order Taylor approximation.** Approximate sin(x) near x₀ with a tangent-line formula. Plot approximation error as the evaluation point moves away from x₀.

- [ ] **44. Explore a second-order Taylor approximation.** Approximate exp(x) using first- and second-order Taylor expansions at x₀=0. Compare accuracy over a chosen interval.

- [ ] **45. Measure the curvature of a loss surface.** Calculate Hessian eigenvalues of quadratic losses with different axis scales. Relate the condition number to the shape of their contour plots.

- [ ] **46. Implement Newton's method in one dimension.** Minimize f(x)=x⁴+2x² using first and second derivatives. Compare convergence with gradient descent and avoid division by a near-zero second derivative.

- [ ] **47. Implement multivariate Newton optimization.** For a positive-definite quadratic objective, solve HΔ=∇f and update the parameter vector by −Δ. Verify convergence without explicitly inverting H.

- [ ] **48. Compute a Hessian-vector product.** Implement H@v for a quadratic objective from gradient differences and compare with explicit matrix multiplication.

- [ ] **49. Compare gradient descent and Newton's method.** On a well-conditioned and an ill-conditioned quadratic, compare convergence paths, function evaluations, and final errors under appropriate step choices.

- [ ] **50. Investigate finite-difference precision.** Estimate first and second derivatives using several step sizes in float32 and float64. Plot errors to identify truncation and roundoff effects.

---

## Phase 6 — Backpropagation and neural-network training

**Level:** Intermediate → Advanced  
**Goal:** Differentiate complete model losses and turn gradients into learning updates.

- [ ] **51. Differentiate a linear layer.** Given Y=XW+b and an upstream gradient dL/dY, implement dL/dX, dL/dW, and dL/db using matrix multiplication and batch summation.

- [ ] **52. Backpropagate through a ReLU layer.** Given pre-activations Z and an upstream gradient, implement the ReLU backward pass and verify results away from Z=0.

- [ ] **53. Derive the MSE loss gradient.** For a batch of predictions, implement mean squared error and its derivative with respect to predictions. Check the averaging factor carefully.

- [ ] **54. Differentiate binary cross-entropy with logits.** Implement stable BCE-with-logits and verify that the gradient with respect to each logit is (sigmoid(logit)−target)/batch_size for mean reduction.

- [ ] **55. Differentiate softmax cross-entropy.** For batched class logits and one-hot labels, implement stable softmax cross-entropy and verify its gradient (probabilities−targets)/batch_size.

- [ ] **56. Implement two-layer backpropagation.** Construct X→Linear→ReLU→Linear→MSE and manually calculate gradients of every trainable weight and bias.

- [ ] **57. Build a finite-difference gradient checker.** Flatten a small neural network's parameters and compare every analytical gradient with central-difference estimates. Report relative error.

- [ ] **58. Add mini-batch gradient descent.** Train a two-layer regression network using shuffled mini-batches. Compare gradients from mini-batch, full-batch, and single-example updates.

- [ ] **59. Implement L2 weight decay in a loss.** Add λ||W||²/2 to a regression network's objective, derive the extra λW gradient, and compare learned weights with and without regularization.

- [ ] **60. Train an XOR classifier from scratch.** Implement a two-layer sigmoid or tanh network and train it on XOR using manually derived backpropagation. Verify final predictions and loss.

---

## Phase 7 — Integration, differential equations, and probability

**Level:** Intermediate  
**Goal:** Use integrals and differential equations to model continuous probabilities, expectations, and learning dynamics.

- [ ] **61. Approximate an integral with Riemann sums.** Estimate ∫₀¹x²dx with left, right, and midpoint sums for increasing partition counts. Compare with the exact answer 1/3.

- [ ] **62. Compare numerical integration algorithms.** Integrate sin(x) over [0,π] using trapezoidal and Simpson's rules. Compare errors for increasingly fine grids.

- [ ] **63. Verify a probability density integrates to one.** Numerically integrate a normal PDF over increasingly wide finite intervals and quantify the probability mass left in its tails.

- [ ] **64. Construct a CDF numerically.** Numerically integrate an exponential PDF from 0 to x and compare against the analytical CDF 1−exp(−λx).

- [ ] **65. Calculate expectation by integration.** Evaluate E[X] and E[X²] for X distributed uniformly on [0,2], then derive and verify Var(X).

- [ ] **66. Calculate entropy of a continuous distribution.** Numerically integrate −p(x)log p(x) for a normal density and compare with its analytical differential entropy.

- [ ] **67. Normalize an unnormalized probability density.** Given q(x)=exp(−x⁴) on the real line, numerically approximate its normalizing constant and use it to construct a valid PDF.

- [ ] **68. Marginalize a joint density.** Construct a correlated bivariate Gaussian density on a 2D grid and numerically integrate out one variable. Compare the estimated marginal with the corresponding univariate Gaussian.

- [ ] **69. Numerically compute an expected loss.** For prediction a and X~N(0,1), integrate (a−X)²p(X) over a sufficiently wide interval. Compare with the known expectation a²+1.

- [ ] **70. Solve a gradient-flow differential equation.** For the loss L(w)=(w−3)², solve the ODE dw/dt=−dL/dw using Euler integration and scipy.integrate.solve_ivp. Compare both numerical solutions with the analytical trajectory.

---

## Phase 8 — Calculus for probabilistic machine learning

**Level:** Advanced  
**Goal:** Learn how probability distributions and their gradients drive parameter estimation.

- [ ] **71. Differentiate Bernoulli log-likelihood.** For binary observations, derive and implement d/dp of the Bernoulli log-likelihood on p∈(0,1). Numerically find the maximum and compare with the sample proportion.

- [ ] **72. Derive Gaussian maximum-likelihood estimates.** Given 1D observations, calculate gradients of Gaussian negative log-likelihood with respect to μ and log σ. Optimize both and compare with MLE estimates.

- [ ] **73. Derive a logistic-regression gradient.** Implement the analytical gradient of binary logistic negative log-likelihood with respect to weights and bias. Compare with finite differences.

- [ ] **74. Fit logistic regression with gradient descent.** Train on a two-class synthetic dataset using the previous gradient. Plot loss and decision boundary and inspect predicted probabilities.

- [ ] **75. Differentiate a Bayesian MAP objective.** Combine a Bernoulli likelihood with a Beta prior whose shape parameters exceed one. Optimize negative log posterior in a logit parameterization and compare with the analytical posterior mode.

- [ ] **76. Differentiate KL divergence between categorical distributions.** Parameterize q with softmax logits and minimize D_KL(p||q) against fixed target p. Verify gradients numerically and track convergence.

- [ ] **77. Implement the reparameterization trick.** For z=μ+σϵ with ϵ~N(0,1), estimate derivatives of E[(z−2)²] with respect to μ and log σ via sampled pathwise gradients. Compare with analytical gradients.

- [ ] **78. Implement a score-function gradient estimator.** For a Bernoulli policy choosing between two rewards, estimate the gradient of expected reward with REINFORCE and compare with the exact derivative. Test the effect of a constant baseline.

- [ ] **79. Differentiate a simple normalizing flow.** Transform z~N(0,1) using x=az+b with a>0. Implement the change-of-variables log density and compute parameter gradients, including the log-Jacobian term.

- [ ] **80. Optimize an entropy-regularized policy.** Parameterize categorical action probabilities with logits, maximize expected reward plus an entropy bonus, and examine how the entropy coefficient changes the learned policy.

---

## Phase 9 — Automatic differentiation and deep optimization

**Level:** Advanced  
**Goal:** Connect your manual derivatives to the automatic differentiation used in production ML.

- [ ] **81. Use PyTorch autograd on a scalar function.** Differentiate a composed scalar function with torch.autograd, then compare with a hand-derived formula and central differences.

- [ ] **82. Inspect gradient shapes and broadcasting.** Use autograd for Y=XW+b with batched inputs. Inspect dL/dW and dL/db and explain why bias gradients are summed over the batch.

- [ ] **83. Compare manual backprop with autograd.** Recreate the two-layer network from Exercise 56 in PyTorch and compare its manual parameter gradients with autograd outputs.

- [ ] **84. Calculate Jacobians with automatic differentiation.** Implement a vector-valued function from R³ to R². Use torch.func.jacrev (or an equivalent API) and compare with a hand-derived 2×3 Jacobian.

- [ ] **85. Calculate Hessian-vector products with autograd.** Compute a Hessian-vector product for a smooth scalar loss using automatic differentiation without materializing the full Hessian; check it against a small explicit Hessian.

- [ ] **86. Investigate vanishing and exploding gradients.** Build deep linear/tanh networks with controlled initial weight scales. Backpropagate a loss and plot gradient norms by layer.

- [ ] **87. Implement gradient clipping.** Train a network with intentionally large gradients. Apply global-norm clipping, log gradient norms before and after clipping, and compare stability.

- [ ] **88. Implement gradient accumulation.** Accumulate gradients across four micro-batches and compare the result with one full-batch mean-loss gradient, accounting correctly for unequal batch sizes.

- [ ] **89. Differentiate through an optimization step.** Unroll three gradient-descent steps for a toy quadratic and differentiate final validation loss with respect to the learning rate or initial parameter.

- [ ] **90. Implement a custom autograd function.** Create a custom PyTorch differentiable operation with a manually coded backward pass. Validate it using an automatic gradient-check utility.

---

## Phase 10 — Calculus in Transformers, LLMs, and generative AI

**Level:** Advanced AI Engineering  
**Goal:** Apply calculus to modern AI building blocks and complete a small trainable system.

- [ ] **91. Backpropagate through an embedding lookup.** Create a small trainable embedding table with repeated token IDs. Compute the gradient of a scalar loss and verify that repeated tokens accumulate contributions to shared embedding rows.

- [ ] **92. Analyze softmax temperature gradients.** For fixed logits, define softmax(z/T) and derive or use autograd to inspect gradients of a selected probability with respect to logits and T. Compare behavior across temperatures.

- [ ] **93. Differentiate scaled dot-product attention.** Build Q, K, and V from input embeddings and projection weights. Compute a scalar loss on attention outputs and verify shapes and gradients of all three projections using autograd.

- [ ] **94. Verify causal-mask gradient behavior.** Implement masked attention with a causal mask and show that masked future positions receive zero attention probability and zero gradient with respect to their attention scores.

- [ ] **95. Differentiate layer normalization.** Implement layer normalization over the feature dimension using a stable epsilon. Compare gradients for inputs, scale γ, and shift β against PyTorch's implementation.

- [ ] **96. Differentiate cosine similarity for retrieval.** Compute the gradient of cosine similarity between query and document vectors, including safe norm handling. Use gradient ascent to move a query embedding toward a relevant document.

- [ ] **97. Calculate LoRA parameter gradients.** Freeze a weight matrix W and train low-rank factors A and B in Y=XW+(α/r)XAB. Compare parameter counts and verify gradients flow to the trainable factors but not W.

- [ ] **98. Differentiate autoregressive token loss.** Create batched token logits and next-token targets with padding masks. Implement shifted-label cross-entropy, backpropagate, and verify zero loss contribution from ignored positions.

- [ ] **99. Differentiate a diffusion denoising objective.** Generate x_t=√ᾱ_t x₀+√(1−ᾱ_t)ϵ, train a tiny network to predict ϵ, and calculate gradients of mean-squared noise-prediction loss.

- [ ] **100. Capstone: train a miniature Transformer block.** Build token embeddings, positional encodings, causal multi-head self-attention, residual connections, layer normalization, and a feed-forward network. Train on a tiny next-token prediction dataset; inspect gradient shapes, norms, and loss reduction.

---

## Five-step practice method

1. **Understand:** State the mathematical objective, inputs, outputs, assumptions, and any differentiability restrictions.
2. **Derive:** Work through the derivative, gradient, Jacobian, or integral with small examples by hand.
3. **Implement:** Write the NumPy or PyTorch calculation yourself rather than calling a black-box solver as the initial answer.
4. **Verify:** Compare against analytic results, finite differences, automatic differentiation, or SciPy, as appropriate. Test shapes, edge cases, and numerical stability.
5. **Explain the AI connection:** Write a sentence about the role of the calculation in training, inference, optimization, attention, or probabilistic modeling.

## Study milestone checklist

- [ ] **Exercises 1–20:** I can explain and implement derivatives, partial derivatives, and gradients.
- [ ] **Exercises 21–40:** I understand chain rule, Jacobians, and gradient-based optimization.
- [ ] **Exercises 41–60:** I can use curvature and manually backpropagate through a small neural network.
- [ ] **Exercises 61–80:** I can use integrals and differentiate probabilistic learning objectives.
- [ ] **Exercises 81–100:** I can validate autodiff and reason about gradients in Transformer components.

## Important note

This is a *calculus-for-AI* sequence. It focuses on the parts most directly used in modern machine learning rather than covering every topic in a traditional university calculus syllabus. Exercises use synthetic data and illustrative models; they do not claim to reproduce production-scale LLM training.
