# 100 Python Hands-On Probability Exercises for Artificial Intelligence

**From absolute beginner to advanced AI/ML/LLM engineering.**

These 100 exercises help you master the probability concepts underlying:

- Machine Learning and Deep Learning
- Bayesian inference and probabilistic models
- Neural network predictions and uncertainty
- Generative AI and Large Language Models
- Transformers and attention mechanisms
- Monte Carlo simulation and probabilistic sampling
- Classification, anomaly detection, and decision-making

The emphasis is on **understanding the mathematics, implementing it in Python, and seeing where it is used in real AI systems.**

## Learning roadmap

| Phase | Topic | Exercises | Level |
|---|---|---|---|
| 1 | Probability fundamentals | 1–10 | Beginner |
| 2 | Probability distributions | 11–20 | Beginner |
| 3 | Expectation, variance, covariance | 21–30 | Beginner–Intermediate |
| 4 | Conditional and joint probability | 31–40 | Intermediate |
| 5 | Sampling and Monte Carlo methods | 41–50 | Intermediate |
| 6 | Bayesian inference | 51–60 | Intermediate |
| 7 | Information theory | 61–70 | Intermediate–Advanced |
| 8 | Probabilistic machine learning | 71–80 | Advanced |
| 9 | Sequence models and uncertainty | 81–90 | Advanced |
| 10 | Probability in LLMs and GenAI | 91–100 | Advanced |

**Tools:** Python, NumPy, SciPy, Matplotlib, Pandas, and scikit-learn. A few advanced exercises optionally use PyTorch.

For reproducible experiments, use:

```python
import numpy as np

rng = np.random.default_rng(42)
```

---

## Phase 1 — Probability fundamentals

**Exercises 1–10 · Beginner**

**Goal:** Understand randomness, events, sample spaces, and the fundamental rules of probability.

- [ ] **1. Simulate a coin toss.** Toss a fair coin 10, 100, 1,000, and 100,000 times using Python. Calculate the empirical probability of heads and compare it with the theoretical probability of 0.5.
- [ ] **2. Simulate rolling dice.** Roll a fair six-sided die 10,000 times and estimate the probability of each outcome. Visualize the empirical distribution using a bar chart.
- [ ] **3. Construct a sample space.** Simulate rolling two dice and calculate the probabilities that their sum equals 7, exceeds 9, or is even. Verify the results using all 36 equally likely ordered outcomes.
- [ ] **4. Implement the complement rule.** Simulate five independent coin tosses per experiment and estimate the probability of getting at least one head. Compare this with the complement-rule calculation.
- [ ] **5. Verify the addition rule.** Simulate drawing a card from a standard 52-card deck. Calculate the probability of drawing a king or a heart, accounting for overlapping events.
- [ ] **6. Investigate independent events.** Simulate two independent coin tosses and verify that $P(A \cap B) \approx P(A)P(B)$.
- [ ] **7. Calculate conditional probability.** Simulate rolling two dice and estimate the probability that their sum is at least 10, given that the first die is 6.
- [ ] **8. Compare sampling with and without replacement.** Draw colored balls from a virtual bag and compare the probability of drawing two red balls under both sampling strategies.
- [ ] **9. Implement weighted random sampling.** Create five outcomes with different probabilities. Simulate 100,000 selections using `rng.choice()` and compare empirical frequencies with the assigned probabilities.
- [ ] **10. Build a simple spam-filtering experiment.** Assume 20% of emails are spam, a filter catches 90% of spam, and falsely flags 5% of legitimate emails. Calculate the probability that a flagged email is actually spam using Bayes' theorem, then verify through simulation.

## Phase 2 — Random variables and probability distributions

**Exercises 11–20 · Beginner**

**Goal:** Learn how probability distributions represent uncertain values in AI systems.

- [ ] **11. Implement the Bernoulli distribution.** Generate 10,000 binary outcomes with success probability 0.7. Estimate their mean and variance and compare with theoretical values.
- [ ] **12. Implement the Binomial distribution.** Simulate 1,000 experiments, each containing 20 independent Bernoulli trials with success probability 0.3. Plot the number of successes and compare with the theoretical binomial PMF.
- [ ] **13. Explore the Geometric distribution.** Simulate how many independent attempts are required until the first success when success probability is 0.2. Compare the experimental mean with $1/p$.
- [ ] **14. Implement the Poisson distribution.** Simulate the number of incoming requests per minute when the average arrival rate is five requests per minute. Plot frequencies and compare them with the Poisson PMF.
- [ ] **15. Generate uniformly distributed samples.** Generate 10,000 samples from the continuous uniform distribution over $[0,1]$. Calculate the mean, variance, and histogram.
- [ ] **16. Explore the Gaussian distribution.** Generate normal samples with different means and standard deviations. Plot the distributions and investigate how their shapes change.
- [ ] **17. Implement the Exponential distribution.** Simulate waiting times between events in a Poisson process. Estimate the probability that the next event occurs within a chosen time interval.
- [ ] **18. Explore the Beta distribution.** Generate Beta samples for parameter pairs such as (1,1), (2,5), (5,2), and (20,20). Visualize how the distribution models uncertainty about a probability.
- [ ] **19. Implement categorical and multinomial sampling.** Define probabilities for five possible classes. Simulate individual class assignments and repeated batches of class counts.
- [ ] **20. Build a Gaussian mixture distribution.** Generate samples from a weighted combination of two Gaussian distributions. Plot the resulting histogram and investigate its two-component structure.

## Phase 3 — Expectation, variance, covariance, and correlation

**Exercises 21–30 · Beginner → Intermediate**

**Goal:** Understand how AI measures average outcomes, variability, and relationships between random variables.

- [ ] **21. Calculate expected value manually.** Given a discrete random variable with five outcomes and associated probabilities, calculate its expected value using the probability-weighted sum and verify with simulation.
- [ ] **22. Calculate variance and standard deviation.** Implement variance from its mathematical definition, then compare with NumPy calculations on generated random samples.
- [ ] **23. Verify linearity of expectation.** Generate two random variables, calculate $E[X+Y]$, and compare with $E[X]+E[Y]$. Repeat using dependent variables to show independence is unnecessary.
- [ ] **24. Calculate covariance.** Generate pairs of correlated random variables and compute their covariance manually and using `np.cov()`.
- [ ] **25. Calculate Pearson correlation.** Generate positively correlated, negatively correlated, and independent variable pairs. Calculate correlation coefficients and visualize their scatter plots.
- [ ] **26. Study the variance of sums.** Compare $\mathrm{Var}(X+Y)$ with $\mathrm{Var}(X)+\mathrm{Var}(Y)+2\mathrm{Cov}(X,Y)$ using dependent and independent variables.
- [ ] **27. Understand quantiles and percentiles.** Generate 10,000 normal samples and calculate the 10th, 25th, 50th, 75th, and 90th percentiles.
- [ ] **28. Compute conditional expectation.** Generate customer purchase amounts and customer-group labels. Estimate expected purchase value for each group and compare it with the overall average.
- [ ] **29. Verify the law of total expectation.** Construct a mixture of customer groups with different purchase distributions. Calculate the overall expected purchase using group probabilities and conditional means.
- [ ] **30. Verify the law of total variance.** Using the mixture dataset, separate overall variance into within-group and between-group components and verify the mathematical identity numerically.

## Phase 4 — Joint probability, conditional probability, and Bayes' theorem

**Exercises 31–40 · Intermediate**

**Goal:** Understand how multiple uncertain variables interact, especially in classification problems.

- [ ] **31. Construct a joint probability table.** Generate synthetic data containing weather conditions and whether someone carries an umbrella. Calculate the joint probabilities of all combinations.
- [ ] **32. Calculate marginal probabilities.** Use the joint table to calculate the marginal probabilities of rain and umbrella usage.
- [ ] **33. Calculate conditional distributions.** Determine the probability of umbrella usage given rain and given no rain. Verify that each conditional distribution sums to one.
- [ ] **34. Test statistical independence.** Generate independent and dependent categorical variables and compare their joint probabilities with the products of their marginals.
- [ ] **35. Implement Bayes' theorem for imbalanced classification.** Simulate a fraud-detection model where fraud is rare. Calculate the probability that a transaction is fraudulent given a positive alert, using sensitivity, false-positive rate, and fraud prevalence.
- [ ] **36. Update probabilities using multiple pieces of evidence.** Build a Bayesian inference example using two conditionally independent pieces of evidence. Compare the prior probability with the updated posterior.
- [ ] **37. Build a Multinomial Naive Bayes classifier.** Generate a small dataset of spam and legitimate messages represented by word counts. Estimate class probabilities and classify unseen messages.
- [ ] **38. Build a Bernoulli Naive Bayes classifier.** Represent the presence or absence of words as binary features and implement a classifier using Bernoulli likelihoods.
- [ ] **39. Implement Laplace smoothing.** Create a text-classification example containing unseen words. Compare probability estimates with and without additive smoothing.
- [ ] **40. Compare generative class likelihoods.** Model two classes with different Gaussian feature distributions. Calculate class-conditional likelihoods and posterior class probabilities for new observations.

## Phase 5 — Sampling, simulation, and Monte Carlo methods

**Exercises 41–50 · Intermediate**

**Goal:** Learn how AI approximates probabilities and expectations when exact calculations are difficult.

- [ ] **41. Investigate pseudorandom number generation.** Generate random sequences using fixed and varying seeds. Demonstrate reproducibility and explain why independent random generators are useful.
- [ ] **42. Demonstrate the Law of Large Numbers.** Simulate increasingly many Bernoulli trials and plot how the sample mean approaches the theoretical expectation.
- [ ] **43. Demonstrate the Central Limit Theorem.** Draw repeated samples from a skewed exponential distribution, calculate their sample means, and visualize how the distribution of standardized sample means approaches a normal distribution.
- [ ] **44. Implement bootstrap sampling.** Given a synthetic dataset, repeatedly sample with replacement and construct a bootstrap confidence interval for its mean.
- [ ] **45. Estimate π using Monte Carlo simulation.** Generate random points inside a square and estimate π using the fraction that falls inside an inscribed quarter-circle. Plot estimation error against sample size.
- [ ] **46. Approximate an expected value by Monte Carlo integration.** Estimate $E[X^2]$ for $X \sim U(0,1)$ using random samples. Compare with the exact value of $1/3$.
- [ ] **47. Implement inverse-transform sampling.** Generate samples from an Exponential distribution using uniformly distributed random numbers and its inverse CDF.
- [ ] **48. Implement rejection sampling.** Construct a sampler for a triangular probability density using a uniform proposal distribution and a valid rejection bound. Compare the generated histogram with the target density.
- [ ] **49. Implement importance sampling.** Estimate the rare-event probability $P(X>4)$ for a standard normal variable using a shifted normal proposal and importance weights. Compare the estimator with naive Monte Carlo.
- [ ] **50. Implement variance reduction.** Use antithetic variates to estimate $E[e^U]$, where $U \sim U(0,1)$. Compare the estimator's variance with ordinary Monte Carlo sampling.

## Phase 6 — Bayesian inference and probabilistic learning

**Exercises 51–60 · Intermediate**

**Goal:** Understand how AI combines prior knowledge with observed data and quantifies uncertainty.

- [ ] **51. Calculate a likelihood function.** Observe a sequence of coin tosses and calculate the Bernoulli likelihood across candidate values of the coin's head probability. Plot the likelihood curve.
- [ ] **52. Implement maximum likelihood estimation.** Estimate the probability of heads from observed coin tosses using the Bernoulli maximum likelihood estimator.
- [ ] **53. Implement Maximum A Posteriori estimation.** Combine a Beta prior with Bernoulli observations and find the posterior mode, using parameters that produce an interior mode.
- [ ] **54. Perform Beta-Bernoulli Bayesian updating.** Start with a Beta prior, observe coin tosses, calculate the posterior Beta parameters, and visualize the posterior distribution.
- [ ] **55. Calculate posterior predictive probability.** Using the Beta-Bernoulli model, calculate the probability that the next coin toss will be heads, integrating over uncertainty in the coin's bias.
- [ ] **56. Construct Bayesian credible intervals.** Calculate a 95% posterior credible interval for an unknown Bernoulli success probability using Beta distribution quantiles.
- [ ] **57. Compare informative and uninformative priors.** Repeat Bayesian updates using different Beta priors. Study how posterior estimates change as the sample size increases.
- [ ] **58. Implement sequential Bayesian learning.** Update a Beta posterior after every new binary observation and plot the evolution of its posterior mean and credible interval.
- [ ] **59. Build a Bayesian A/B testing simulator.** Simulate conversions for two website versions, estimate their Beta posterior distributions, and calculate the probability that version B's conversion rate exceeds version A's.
- [ ] **60. Build a Bayesian prediction experiment.** Use the Beta-Binomial model to calculate the posterior predictive distribution for the number of conversions in the next 20 visitors. Compare this with predictions based only on a single estimated conversion rate.

## Phase 7 — Information theory for AI

**Exercises 61–70 · Intermediate → Advanced**

**Goal:** Understand the probability mathematics behind classification losses, information gain, and language models.

- [ ] **61. Calculate information content (surprisal).** Given events with different probabilities, compute $-\log_2(p)$ for each event. Explain why unexpected events carry more information.
- [ ] **62. Calculate Bernoulli entropy.** Implement binary entropy and plot it as the success probability varies from 0 to 1.
- [ ] **63. Calculate categorical entropy.** Compare the entropy of a uniform distribution with that of a highly concentrated distribution over five possible outcomes.
- [ ] **64. Implement cross-entropy.** Given true class labels and predicted class probabilities, compute multiclass cross-entropy manually and compare with a library implementation.
- [ ] **65. Implement KL divergence.** Calculate $D_{\mathrm{KL}}(P\|Q)$ between two discrete probability distributions and demonstrate that KL divergence is generally asymmetric.
- [ ] **66. Implement Jensen-Shannon divergence.** Calculate JS divergence between probability distributions and compare its symmetry with KL divergence.
- [ ] **67. Calculate mutual information.** Generate two dependent categorical variables, estimate their joint and marginal probabilities, and compute their mutual information.
- [ ] **68. Implement information gain for decision trees.** Given binary class labels and several candidate categorical features, calculate the entropy reduction from splitting on each feature and select the best split.
- [ ] **69. Explore softmax probability distributions.** Convert model logits into class probabilities and observe how changing temperature affects entropy and concentration.
- [ ] **70. Implement numerically stable log-sum-exp.** Calculate $\log\sum_i e^{x_i}$ for large logits using the maximum-subtraction trick. Compare with the naive implementation and explain how it stabilizes log-softmax and likelihood computations.

## Phase 8 — Probability in machine learning models

**Exercises 71–80 · Advanced**

**Goal:** Build probabilistic classifiers and learn how to evaluate the reliability of predicted probabilities.

- [ ] **71. Implement Gaussian Naive Bayes from scratch.** Generate continuous feature data for three classes and implement class prediction using Gaussian likelihoods and Bayes' theorem.
- [ ] **72. Implement logistic regression using NumPy.** Generate a binary-classification dataset and train logistic regression using the sigmoid function, Bernoulli negative log-likelihood, and gradient descent.
- [ ] **73. Calculate Brier score and log loss.** Create several sets of probabilistic predictions with different confidence levels. Compare their Brier scores and negative log-likelihoods.
- [ ] **74. Build a reliability diagram.** Group binary-classification predictions into probability bins and compare their average predicted confidence with observed outcome frequencies.
- [ ] **75. Implement probability calibration.** Train a classifier, calibrate its output probabilities using Platt scaling or isotonic regression on a separate validation set, and compare its reliability before and after calibration.
- [ ] **76. Compare probabilistic models using log-likelihood.** Fit normal and Laplace distributions to synthetic continuous observations. Calculate held-out log-likelihoods and identify which model better explains the data.
- [ ] **77. Build a probabilistic anomaly detector.** Fit a Gaussian distribution to normal training observations. Flag low-density test observations and measure false-positive and true-positive rates on labeled synthetic anomalies.
- [ ] **78. Implement a Gaussian Mixture Model using EM.** Generate observations from three Gaussian components and implement the Expectation-Maximization algorithm to estimate component probabilities, means, and variances.
- [ ] **79. Implement a probabilistic classifier under class imbalance.** Generate data with 1% positive samples. Compare posterior probabilities, precision-recall behavior, and decision thresholds under different class prevalences.
- [ ] **80. Build a cost-sensitive decision system.** Given calibrated fraud probabilities and the costs of false positives and false negatives, derive an appropriate classification threshold and compare expected decision costs with a default 0.5 threshold.

## Phase 9 — Probabilistic sequence models and uncertainty estimation

**Exercises 81–90 · Advanced**

**Goal:** Understand probability over time, latent states, and uncertainty in model predictions.

- [ ] **81. Build a Markov-chain transition matrix.** Define three weather states and transition probabilities between them. Verify that every row of the matrix sums to one.
- [ ] **82. Simulate a Markov chain.** Generate 10,000 weather-state transitions using the transition matrix and visualize the sequence and state frequencies.
- [ ] **83. Estimate a stationary distribution.** Calculate the stationary probabilities of an ergodic Markov chain and compare them with empirical state frequencies from long simulations.
- [ ] **84. Implement a Hidden Markov Model forward algorithm.** Given hidden states, transition probabilities, emission probabilities, and an observation sequence, calculate the sequence likelihood using a numerically stable forward procedure.
- [ ] **85. Implement the Viterbi algorithm.** Using the same Hidden Markov Model, find the most likely hidden-state sequence for a given set of observations.
- [ ] **86. Implement forward-backward inference.** Calculate the posterior probability of each hidden state at every time step conditioned on the entire observation sequence.
- [ ] **87. Implement Metropolis-Hastings sampling.** Sample from a one-dimensional Gaussian mixture using MCMC. Visualize the sample distribution, trace plot, and acceptance rate.
- [ ] **88. Implement Gibbs sampling.** Sample from a correlated bivariate Gaussian distribution using its conditional Gaussian distributions. Compare empirical moments with the target distribution.
- [ ] **89. Estimate neural-network uncertainty with Monte Carlo dropout.** Train a small network with dropout, keep dropout active during repeated inference, and estimate predictive variation. Discuss why this is only an approximate uncertainty measure.
- [ ] **90. Implement split conformal prediction.** Train a probabilistic classifier and use a separate calibration dataset to construct prediction sets. Evaluate their empirical coverage on independent test data.

## Phase 10 — Probability in LLMs, Transformers, and Generative AI

**Exercises 91–100 · Advanced AI Engineering**

**Goal:** Apply probability directly to how modern language models generate tokens, calculate likelihoods, and represent uncertainty.

- [ ] **91. Implement next-token probability prediction.** Create vocabulary logits for 1,000 tokens, convert them to probabilities with softmax, and sample the next token from the resulting categorical distribution.
- [ ] **92. Implement Top-K and Top-P sampling.** Given next-token probabilities, implement Top-K filtering and nucleus (Top-P) filtering. Renormalize the retained probabilities and sample tokens.
- [ ] **93. Investigate temperature-controlled text generation.** Apply temperatures of 0.2, 0.5, 1.0, and 2.0 to the same token logits. Calculate entropy and analyze how probability concentration changes.
- [ ] **94. Calculate autoregressive sequence probability.** Given conditional probabilities for successive tokens, compute the probability of a complete token sequence using the chain rule. Repeat using log probabilities to avoid numerical underflow.
- [ ] **95. Implement language-model perplexity.** Given a collection of correct-token probabilities, calculate average negative log-likelihood and perplexity. Explain why lower perplexity generally indicates better predictive fit on comparable data.
- [ ] **96. Implement masked token cross-entropy.** Create a batch of synthetic sequence logits and target token IDs, including padding. Calculate average cross-entropy while excluding padding positions.
- [ ] **97. Implement label smoothing.** Replace hard one-hot targets with smoothed target distributions and compare categorical cross-entropy loss and gradients with ordinary one-hot targets.
- [ ] **98. Implement probabilistic self-attention weights.** Generate Query and Key matrices, calculate scaled dot-product scores, apply a causal mask, and convert scores into attention weights using softmax. Verify that valid rows sum to one.
- [ ] **99. Simulate forward diffusion noise.** Implement the Gaussian corruption process $q(x_t\mid x_0)$ for multiple noise levels. Visualize how original samples become increasingly noisy and verify the resulting conditional means and variances.
- [ ] **100. Capstone — Build an uncertainty-aware text classification system.** Generate a synthetic text-classification dataset, represent documents using bag-of-words features, train Multinomial Naive Bayes and logistic regression, produce class probabilities, evaluate cross-entropy and Brier scores, plot reliability diagrams, calibrate probabilities using held-out data, and generate prediction sets using split conformal prediction. Compare classification accuracy, probability calibration, and empirical coverage.

---

## Key probability concepts you will practice

| Concept | Exercise numbers | AI application |
|---|---|---|
| Events, independence, conditional probability | 1–10 | Probabilistic reasoning |
| Discrete and continuous distributions | 11–20 | Data and model distributions |
| Expectation, variance, covariance | 21–30 | Uncertainty and feature analysis |
| Bayes' theorem and joint probability | 31–40 | Naive Bayes, classification |
| Monte Carlo and sampling | 41–50 | Probabilistic approximation |
| Bayesian inference | 51–60 | Posterior prediction and uncertainty |
| Entropy, KL, cross-entropy | 61–70 | ML losses, information theory |
| Probabilistic ML models | 71–80 | Classification and calibration |
| Markov models, MCMC, uncertainty | 81–90 | Sequential learning and inference |
| Token probabilities and generation | 91–100 | LLMs, Transformers, diffusion |

## Recommended approach for each exercise

1. **Understand the probability concept.** Identify the random variables, assumptions, distributions, and mathematical formulas.
2. **Calculate a small example manually.** Use simple numbers to understand the mechanism.
3. **Implement the formula in Python.** Initially avoid relying entirely on prebuilt probability functions.
4. **Verify your implementation.** Compare results with SciPy or another trusted library; check edge cases and numerical stability.
5. **Connect it to AI.** Explain what uncertainty is modeled and how the computation supports a real AI application.

**Goal:** Don't memorize 100 solutions. Learn why an AI model computes a probability, what assumptions make it meaningful, and how to implement and test it yourself.
