package com.andrija.clustering.evaluation.knowntruth;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class SolutionValueEvaluation implements ClusterEvaluation {

	private Solution optimalSolution;
	private Solution finalSolution;

	public SolutionValueEvaluation(Solution optimalSolution, Solution finalSolution) {
		this.optimalSolution = optimalSolution;
		this.finalSolution = finalSolution;
	}

	@Override
	public double evaluate() {
		double optimalClusterSum = optimalSolution.getValue();
		double finalClusterSum = finalSolution.getValue();
		double overallClusterSumDifference = finalClusterSum / optimalClusterSum;
		return overallClusterSumDifference;
	}


}
