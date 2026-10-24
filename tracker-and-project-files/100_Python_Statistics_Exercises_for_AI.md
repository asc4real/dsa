# 100 Python Hands-On Statistics Exercises for Artificial Intelligence

> A progressive practice workbook from descriptive statistics to rigorous evaluation of ML, LLM, RAG, and generative AI systems.

**Total:** 100 exercises across 10 phases.  **Format:** Check off each exercise as you finish.

## Recommended Python toolkit

- **Core:** Python 3, NumPy, Pandas, SciPy, Matplotlib, Seaborn
- **Statistical modeling:** statsmodels, scikit-learn
- **Optional:** PyTorch and specialized experimentation or time-series libraries
- **Reproducibility:** Use `rng = np.random.default_rng(42)` for synthetic experiments; record library versions and random seeds.

## How to complete each exercise

1. Define the **question, estimand, and assumptions** before coding.
2. Implement the statistical calculation yourself when practical; then compare with a trusted library.
3. Create visualizations, test edge cases, and report **effect sizes and uncertainty**, not just p-values.
4. Explain how the result changes an actual AI development or evaluation decision.
5. Save code, plots, and a brief written conclusion with the exercise.

## Progress checklist

- [ ] **Phase 1: Descriptive Statistics and Data Quality** (Exercises 01–10; Beginner)
- [ ] **Phase 2: Exploratory Data Analysis and Statistical Relationships** (Exercises 11–20; Beginner → Intermediate)
- [ ] **Phase 3: Sampling, Estimation, and Confidence Intervals** (Exercises 21–30; Intermediate)
- [ ] **Phase 4: Hypothesis Testing, Effect Sizes, and Statistical Power** (Exercises 31–40; Intermediate)
- [ ] **Phase 5: Experimental Design and A/B Testing for AI** (Exercises 41–50; Intermediate → Advanced)
- [ ] **Phase 6: Regression, Statistical Modeling, and Diagnostics** (Exercises 51–60; Intermediate → Advanced)
- [ ] **Phase 7: Predictive Model Evaluation and Reliability** (Exercises 61–70; Intermediate → Advanced)
- [ ] **Phase 8: Feature Statistics, Robustness, and Data Leakage** (Exercises 71–80; Advanced)
- [ ] **Phase 9: Time-Series Statistics and Model Monitoring** (Exercises 81–90; Advanced)
- [ ] **Phase 10: Advanced Statistics for LLMs, Retrieval, and Causal AI** (Exercises 91–100; Advanced / Capstone)

---

## Phase 1 — Descriptive Statistics and Data Quality

**Level:** Beginner  
**Exercises:** 01–10  
**Goal:** Summarize numerical and categorical data, quantify spread, and detect problems before training an AI model.

- [ ] **001. Build an AI-ready dataset.** Create a synthetic dataset of 1,000 examples containing age, session duration, clicks, conversions, and a binary label. Inspect data types, sample count, missing values, duplicates, and class proportions using Pandas.

- [ ] **002. Implement measures of central tendency.** Compute arithmetic mean, median, and mode manually and with NumPy/Pandas. Add extreme values and explain when each statistic becomes misleading.

- [ ] **003. Calculate weighted averages.** Compute an overall performance score from weighted subgroup scores. Compare with the unweighted average and explain how group size influences aggregation.

- [ ] **004. Measure dispersion.** Implement range, population and sample variance, standard deviation, interquartile range (IQR), and median absolute deviation (MAD). Verify the relevant degrees-of-freedom conventions.

- [ ] **005. Calculate quantiles and percentiles.** Find quartiles, deciles, and 95th/99th percentiles of simulated response times. Explain how percentiles reveal tail behavior hidden by the average.

- [ ] **006. Detect outliers using IQR and z-scores.** Identify unusual values in both symmetric and skewed data. Compare results from IQR rules, ordinary z-scores, and robust MAD-based scores.

- [ ] **007. Profile missing and duplicate data.** Inject missing values and duplicate rows. Report missingness by column and subgroup, detect duplicate records, and document what would be removed or retained.

- [ ] **008. Summarize categorical variables.** Compute frequency tables, proportions, rare-category counts, and contingency tables for device type, region, and outcome. Visualize class imbalance.

- [ ] **009. Analyze subgroup statistics.** Group by region or device and calculate counts, means, medians, standard deviations, and conversion rates. Distinguish micro-averaged from macro-averaged summaries.

- [ ] **010. Implement streaming mean and variance.** Process one million synthetic observations in chunks using Welford's algorithm. Compare the online mean and sample variance with a full-data NumPy calculation.

---

## Phase 2 — Exploratory Data Analysis and Statistical Relationships

**Level:** Beginner → Intermediate  
**Exercises:** 11–20  
**Goal:** Discover distributions, dependencies, skewness, and misleading patterns in datasets.

- [ ] **011. Visualize distributions correctly.** Plot histograms with different bin widths, KDE estimates, and box plots for Gaussian, skewed, and multimodal datasets. Explain what each visualization may conceal.

- [ ] **012. Implement an empirical CDF.** Construct an empirical cumulative distribution function from sorted data and compare percentiles derived from the ECDF with NumPy quantiles.

- [ ] **013. Calculate skewness and kurtosis.** Compute sample skewness and excess kurtosis for normal, lognormal, and heavy-tailed samples. Compare formulas and SciPy results, noting estimator conventions.

- [ ] **014. Create Q–Q plots.** Build normal Q–Q plots for different distributions. Explain how tail deviations and sample size affect interpretation without treating normality as an absolute requirement.

- [ ] **015. Explore transformations of skewed data.** Apply log, log1p, square-root, and Box–Cox or Yeo–Johnson transformations where valid. Compare skewness before and after transformation.

- [ ] **016. Compare Pearson and Spearman correlation.** Generate linear, monotonic nonlinear, and outlier-contaminated relationships. Calculate both coefficients and explain their differences.

- [ ] **017. Build covariance and correlation matrices.** Generate a 10-feature dataset, compute pairwise covariance and correlation matrices, plot heatmaps, and interpret scaling effects and strong dependencies.

- [ ] **018. Demonstrate Simpson's paradox.** Construct grouped data where the within-group association differs from the pooled association. Explain how a confounding variable can reverse an apparent trend.

- [ ] **019. Measure association between categorical variables.** Create a contingency table, calculate chi-square association statistics and Cramér's V, and interpret why statistical significance is not effect size.

- [ ] **020. Investigate multicollinearity in EDA.** Create redundant numerical features, inspect scatter matrices and correlation patterns, and calculate singular values or condition numbers to identify redundant directions.

---

## Phase 3 — Sampling, Estimation, and Confidence Intervals

**Level:** Intermediate  
**Exercises:** 21–30  
**Goal:** Learn how sampling uncertainty affects model metrics and population estimates.

- [ ] **021. Implement simple random sampling.** Sample records with and without replacement from a known synthetic population. Compare population and sample means across repeated experiments.

- [ ] **022. Implement stratified sampling.** Sample a rare-positive classification dataset proportionally and with deliberate oversampling. Show how sampling weights recover population-level prevalence estimates.

- [ ] **023. Compare cluster and simple random sampling.** Generate clustered observations with correlated members. Sample entire clusters versus individual observations and compare estimator variability.

- [ ] **024. Simulate selection bias.** Preferentially include high-engagement users in a survey. Quantify how the resulting sample mean differs from the true population mean.

- [ ] **025. Build a sampling distribution of the mean.** Draw thousands of fixed-size samples from a known population and plot the distribution of sample means as the sample size increases.

- [ ] **026. Demonstrate the Central Limit Theorem.** Repeat the sample-mean experiment for uniform, exponential, and heavy-tailed data. Compare standardized means and discuss finite-sample limitations.

- [ ] **027. Estimate standard errors.** Calculate the standard error of a mean analytically and by repeated simulation. Compare standard deviation of raw observations with standard error of the mean.

- [ ] **028. Construct z- and t-based confidence intervals.** Estimate a population mean using known-variance z intervals and unknown-variance Student-t intervals under appropriate simulation assumptions.

- [ ] **029. Bootstrap means and medians.** Generate percentile bootstrap confidence intervals for a sample mean and median. Compare their widths and behavior on skewed data.

- [ ] **030. Simulate confidence-interval coverage.** Across 2,000 simulated datasets, calculate nominal 95% confidence intervals and estimate actual coverage. Compare methods and sample sizes.

---

## Phase 4 — Hypothesis Testing, Effect Sizes, and Statistical Power

**Level:** Intermediate  
**Exercises:** 31–40  
**Goal:** Separate evidence, effect magnitude, and uncertainty when comparing AI systems.

- [ ] **031. Define and simulate hypothesis tests.** Formulate null and alternative hypotheses for whether a model's accuracy exceeds a baseline. Compute an appropriate test statistic on synthetic independent observations.

- [ ] **032. Implement a one-sample t-test.** Test whether average response latency differs from a target. Calculate the t statistic manually, obtain a p-value, and compare with SciPy.

- [ ] **033. Implement a paired t-test.** Compare old and new model errors measured on the same test examples. Test the mean paired difference rather than treating measurements as independent.

- [ ] **034. Implement Welch's two-sample t-test.** Compare two independent groups with different variances and sample sizes. Calculate the effect estimate, confidence interval, and Welch p-value.

- [ ] **035. Use a nonparametric group comparison.** Compare skewed distributions with Mann–Whitney U; interpret what the test does and does not establish about differences in medians.

- [ ] **036. Test categorical independence.** Use a chi-square test to investigate association between device type and error category. Inspect expected counts and residual contributions.

- [ ] **037. Apply Fisher's exact test.** Create a sparse 2×2 contingency table and compare Fisher's exact test with a chi-square approximation.

- [ ] **038. Simulate false-positive rates.** Run thousands of null-hypothesis experiments and show the empirical Type I error rate for significance thresholds 0.01 and 0.05.

- [ ] **039. Estimate statistical power.** Simulate tests under increasing effect sizes and sample sizes. Plot the fraction of correctly rejected false null hypotheses and the associated Type II error.

- [ ] **040. Correct for multiple hypothesis tests.** Test 100 synthetic features, then apply Bonferroni and Benjamini–Hochberg corrections. Compare family-wise error and false-discovery-rate control.

---

## Phase 5 — Experimental Design and A/B Testing for AI

**Level:** Intermediate → Advanced  
**Exercises:** 41–50  
**Goal:** Design valid experiments when launching models, prompts, and recommender systems.

- [ ] **041. Plan sample size for an A/B test.** Specify a baseline conversion rate, minimum detectable effect, significance level, and power. Estimate required sample size for a two-sided two-proportion test.

- [ ] **042. Simulate randomization and an A/A test.** Randomly assign synthetic users to two identical systems. Check baseline balance and empirical false-positive rates across repeated A/A experiments.

- [ ] **043. Estimate a treatment effect for conversion.** Calculate conversion-rate difference, relative lift, and an appropriate confidence interval from independent user-level Bernoulli outcomes.

- [ ] **044. Analyze an AI recommendation A/B test.** Simulate click-through and downstream purchases for control versus treatment. Distinguish statistical significance from practical or business significance.

- [ ] **045. Demonstrate the peeking problem.** Repeatedly check an ordinary fixed-horizon p-value as data accumulates. Estimate the inflated false-positive rate under the null.

- [ ] **046. Implement a valid sequential experiment.** Use a predeclared limited set of interim looks with a conservative alpha-spending rule (or a suitable sequential-testing library) and simulate Type I error.

- [ ] **047. Bootstrap a user-level ratio metric.** Estimate clicks per user from repeated user-level observations. Resample users, not individual clicks, and construct a bootstrap interval.

- [ ] **048. Implement CUPED variance reduction.** Generate pre-experiment and in-experiment user outcomes. Adjust the outcome using a pre-treatment covariate and compare treatment-effect standard errors.

- [ ] **049. Detect sample-ratio mismatch.** Simulate expected 50/50 random assignment and a biased assignment mechanism. Use a chi-square goodness-of-fit test to flag mismatches.

- [ ] **050. Design model-launch guardrails.** Simulate an experiment with quality, latency, and cost metrics. Define primary/guardrail endpoints in advance and produce a deployment decision with uncertainty and multiple-testing considerations.

---

## Phase 6 — Regression, Statistical Modeling, and Diagnostics

**Level:** Intermediate → Advanced  
**Exercises:** 51–60  
**Goal:** Explain relationships, quantify uncertainty in coefficients, and diagnose unreliable models.

- [ ] **051. Fit simple linear regression.** Generate noisy linear data and fit ordinary least squares. Interpret the estimated slope, intercept, fitted values, and residuals.

- [ ] **052. Fit multiple linear regression.** Predict a continuous target from several numerical features. Compare estimated coefficients with known synthetic coefficients.

- [ ] **053. Diagnose regression residuals.** Plot residuals against fitted values, residual distributions, and Q–Q plots. Investigate nonlinearity, nonconstant variance, and extreme points.

- [ ] **054. Handle heteroskedasticity.** Generate errors whose variance rises with a feature. Compare conventional OLS standard errors with heteroskedasticity-robust HC3 standard errors.

- [ ] **055. Interpret coefficient confidence intervals.** Compute and plot 95% confidence intervals for regression coefficients. Explain why a confidence interval for a mean prediction differs from a prediction interval.

- [ ] **056. Fit and interpret interaction terms.** Create data where one predictor's effect depends on another variable. Fit an interaction model and visualize conditional slopes.

- [ ] **057. Diagnose regression multicollinearity.** Calculate variance inflation factors (VIFs), inspect coefficient instability, and compare predictions as features become more correlated.

- [ ] **058. Encode categorical regressors.** Fit a model using one-hot encoded categories with a reference level. Interpret coefficients and avoid perfect dummy-variable redundancy.

- [ ] **059. Interpret logistic-regression effects.** Fit logistic regression to binary labels, compute odds ratios and confidence intervals, and distinguish odds from probabilities.

- [ ] **060. Compare regularized regression models.** Evaluate ridge and lasso against unregularized regression using a preprocessing pipeline and cross-validation. Explain shrinkage and feature-selection effects.

---

## Phase 7 — Predictive Model Evaluation and Reliability

**Level:** Intermediate → Advanced  
**Exercises:** 61–70  
**Goal:** Make trustworthy comparisons of machine-learning models on unseen data.

- [ ] **061. Build leakage-safe train/validation/test splits.** Generate a prediction dataset, split it before learning preprocessing parameters, and demonstrate the optimism caused by fitting a scaler on all data.

- [ ] **062. Implement K-fold cross-validation.** Compare single holdout estimates with K-fold and repeated K-fold metric distributions for a regression or classification model.

- [ ] **063. Apply stratified cross-validation.** Evaluate an imbalanced classifier with K-fold versus StratifiedKFold and compare positive-class representation and score variability.

- [ ] **064. Perform nested cross-validation.** Tune hyperparameters in inner folds and estimate generalization in outer folds. Compare the result with the biased practice of reporting the best tuning score.

- [ ] **065. Bootstrap model-performance uncertainty.** Given held-out predictions, resample evaluation examples to estimate a confidence interval for a chosen metric; note when grouped dependence requires grouped resampling.

- [ ] **066. Calculate confusion-matrix metrics.** Implement accuracy, sensitivity, specificity, precision, recall, and F1 from a confusion matrix. Show how severe class imbalance changes their usefulness.

- [ ] **067. Compare ROC and precision–recall curves.** Evaluate the same model under different positive-class prevalences. Compute ROC AUC and average precision and explain their different interpretations.

- [ ] **068. Evaluate probabilistic calibration.** Create reliability diagrams, Brier scores, and log loss. Compare a well-calibrated model with an overconfident one.

- [ ] **069. Optimize a classification threshold.** Choose a threshold from a validation set using specified false-positive and false-negative costs, then assess the locked threshold on an untouched test set.

- [ ] **070. Audit subgroup model performance.** Compute recall, false-positive rate, calibration, and confidence intervals across subgroups. Flag small-sample uncertainty rather than overinterpreting noisy gaps.

---

## Phase 8 — Feature Statistics, Robustness, and Data Leakage

**Level:** Advanced  
**Exercises:** 71–80  
**Goal:** Build preprocessing and feature-selection workflows that generalize rather than overfit.

- [ ] **071. Simulate missing-data mechanisms.** Generate MCAR, MAR, and MNAR missingness and compare observed versus complete-case estimates. Explain why MNAR cannot generally be diagnosed from observed values alone.

- [ ] **072. Compare imputation approaches safely.** Evaluate mean, median, and KNN imputation inside cross-validation pipelines. Never estimate imputation parameters on held-out folds.

- [ ] **073. Measure outlier influence.** Insert influential observations into a regression dataset and compare coefficient estimates before and after robust regression or justified winsorization.

- [ ] **074. Compare feature scaling methods.** Apply standard, min-max, and robust scaling to skewed and outlier-contaminated features. Compare resulting distributions and model behavior.

- [ ] **075. Rank features using different statistics.** Compare Pearson correlation, Spearman correlation, and mutual information for linear versus nonlinear relationships, including irrelevant noise features.

- [ ] **076. Perform leakage-safe feature selection.** Use SelectKBest inside a cross-validation pipeline. Compare with preselecting features on the entire dataset to expose selection bias.

- [ ] **077. Analyze permutation importance.** Calculate permutation importance on held-out data, then add correlated predictors to investigate how shared information affects importance.

- [ ] **078. Measure feature distribution shift.** Compare reference and current feature distributions using KS tests and population stability index (PSI). Investigate effects of binning, sample size, and practical magnitude.

- [ ] **079. Encode high-cardinality categories.** Implement out-of-fold target encoding and compare with naive full-data target encoding. Demonstrate why naive encoding leaks label information.

- [ ] **080. Build a deliberate leakage challenge.** Insert a post-outcome feature or duplicate entities across splits. Measure implausibly high validation performance and then fix the split and feature definitions.

---

## Phase 9 — Time-Series Statistics and Model Monitoring

**Level:** Advanced  
**Exercises:** 81–90  
**Goal:** Work with temporal dependence, forecasting uncertainty, and changes in deployed AI data.

- [ ] **081. Resample timestamped events.** Generate event-level observations and aggregate them into daily or hourly counts. Handle missing timestamps and distinguish missing intervals from real zeros.

- [ ] **082. Decompose trend and seasonality.** Create a seasonal time series and use moving averages or STL decomposition to investigate trend, seasonal patterns, and residuals.

- [ ] **083. Compute autocorrelation.** Estimate the autocorrelation function of white noise and an AR(1) series. Explain why random shuffling breaks temporal structure.

- [ ] **084. Investigate stationarity.** Compare stationary and trending simulated series using plots, rolling statistics, and the Augmented Dickey–Fuller test. State its null hypothesis and limitations.

- [ ] **085. Detect look-ahead bias.** Create a forecasting dataset with accidentally future-informed rolling averages. Measure inflated accuracy and repair the feature construction.

- [ ] **086. Create lag and rolling-window features.** Build lagged targets and past-only moving statistics, then measure their usefulness in a forecasting model.

- [ ] **087. Implement walk-forward validation.** Evaluate a forecasting model with TimeSeriesSplit and compare it with random cross-validation. Include a temporal gap when the label horizon requires it.

- [ ] **088. Compare forecast baselines.** Evaluate mean, last-value, seasonal-naive, and a fitted statistical forecast using MAE and RMSE on chronological holdouts.

- [ ] **089. Estimate forecast interval coverage.** Construct simple residual-based forecast intervals from appropriate training or calibration residuals. Test empirical coverage and identify dependence-related weaknesses.

- [ ] **090. Monitor deployed model drift.** Simulate incoming feature and label distributions changing over months. Track data shift, prediction rates, delayed ground-truth performance, and alert thresholds.

---

## Phase 10 — Advanced Statistics for LLMs, Retrieval, and Causal AI

**Level:** Advanced / Capstone  
**Exercises:** 91–100  
**Goal:** Design statistically rigorous evaluation for real AI products, not just mathematical toy examples.

- [ ] **091. Quantify uncertainty in LLM benchmark scores.** Simulate correct/incorrect responses on a benchmark and compute Wilson confidence intervals for accuracy across sample sizes. Discuss finite-benchmark generalization.

- [ ] **092. Compare two LLMs with paired bootstrap.** Evaluate two models on the same prompts, resample prompts as paired units, and calculate a confidence interval for their score difference.

- [ ] **093. Measure human-annotation agreement.** Generate two annotators' relevance labels and calculate raw agreement and Cohen's kappa. Examine prevalence effects and adjudicate disagreements.

- [ ] **094. Analyze response-quality distributions.** Simulate LLM response length, latency, refusal rate, and user ratings. Report tail percentiles and compare subgroup distributions rather than relying on a single mean.

- [ ] **095. Examine embedding feature statistics.** Generate 384-dimensional synthetic embeddings. Investigate feature means, covariance structure, PCA explained variance, and differences before and after L2 normalization.

- [ ] **096. Investigate nearest-neighbor hubness.** Generate embeddings across several dimensions and measure how often each item occurs in others' nearest-neighbor lists. Study distance concentration and hubness trends.

- [ ] **097. Evaluate a contextual bandit policy offline.** Simulate logged actions, rewards, and known behavior-policy propensities. Implement inverse propensity scoring, check overlap, and explore weight clipping bias–variance tradeoffs.

- [ ] **098. Simulate confounding and causal adjustment.** Generate a treatment, outcome, and common cause. Compare naive treatment differences with regression-adjusted estimates and discuss identification assumptions.

- [ ] **099. Implement difference-in-differences.** Simulate treated and control groups before and after a change. Estimate a difference-in-differences effect and demonstrate how violating parallel trends introduces bias.

- [ ] **100. Capstone: statistically evaluate an AI assistant.** Create a synthetic evaluation of two assistant versions over prompts, annotators, task categories, and time. Produce EDA, reliability checks, paired effect estimates with confidence intervals, multiple-testing-aware subgroup analyses, calibration where applicable, drift monitoring, and a defensible deployment recommendation.

---

## Final mastery checklist

- [ ] I can describe a statistic, its estimand, its assumptions, and when its interpretation fails.
- [ ] I can visualize and investigate distributions, missing data, outliers, and dependence.
- [ ] I can distinguish effect size, confidence intervals, p-values, power, and multiple testing.
- [ ] I can design A/B experiments and detect issues such as peeking and sample-ratio mismatch.
- [ ] I can evaluate AI models using leakage-safe validation, reliable metrics, and subgroup analyses.
- [ ] I can compare LLMs using paired evaluation and report statistically defensible conclusions.

## Notes

These exercises intentionally use **synthetic data** wherever private or real-world data is unnecessary. For exercises involving inferential procedures, check the model assumptions, the unit of independence, and the sampling mechanism. For repeated and time-dependent measurements, ordinary i.i.d. formulas may be invalid; use appropriate cluster-aware or time-aware approaches.
