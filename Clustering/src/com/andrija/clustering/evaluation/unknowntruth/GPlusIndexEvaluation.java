package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class GPlusIndexEvaluation extends BakerHubertGammaIndexEvaluation {

	private Double negativeSum;
	private Double totalSum;

	public GPlusIndexEvaluation(Solution solution) {
		super(solution);
	}

	@Override
	public double evaluate() {
		if (negativeSum == null || totalSum == null) {
			super.calculate();
			negativeSum = super.getNegativeSum();
			totalSum = negativeSum + super.getPositiveSum();
		}
		return 2 * negativeSum / (totalSum * (totalSum - 1));
	}
}
