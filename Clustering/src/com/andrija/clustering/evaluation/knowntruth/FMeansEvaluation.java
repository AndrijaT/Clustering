package com.andrija.clustering.evaluation.knowntruth;

import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED
 */
public class FMeansEvaluation extends RandIndexEvaluation {

	private int betaConstant;

	public FMeansEvaluation(Solution optimalSolution, Solution finalSolution, int betaConstant) {
		super(optimalSolution, finalSolution);
		this.betaConstant = betaConstant;
	}

	@Override
	public double evaluate() {
		double TP = super.getTruePositiveCount();
		double FP = super.getFalsePositiveCount();
		double FN = super.getFalseNegativeCount();
		double P = TP / (TP + FP);
		double R = TP / (TP + FN);
		return (betaConstant * betaConstant + 1) * P * R / (betaConstant * betaConstant * P + R);
	}
}
