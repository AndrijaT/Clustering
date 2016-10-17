package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 * Returns number of clusters best suited for the Problem
 */
public class NumberOfClustersAvargeSilhouettesEvaluation extends AvargeSilhouettesEvaluation {
	private int minNumOfClusters;
	private int maxNumOfClusters;

	public NumberOfClustersAvargeSilhouettesEvaluation(Solution finalSolution, int precision, int numOfClustersRange) {
		super(finalSolution, precision);
		this.minNumOfClusters = super.getNumOfClusters() - numOfClustersRange;
		if (minNumOfClusters < 0)
			minNumOfClusters = 0;
		this.maxNumOfClusters = super.getNumOfClusters() + numOfClustersRange;
	}

	@Override
	public double evaluate() {
		double maxOverallAvargeSilhouetteWidth = super.evaluate();
		int originalNumOfClusters = super.getNumOfClusters();
		int optimalNumOfClusters = super.getNumOfClusters();
		for (int numOfClusters = minNumOfClusters; numOfClusters <= maxNumOfClusters; numOfClusters++) {
			if (numOfClusters != originalNumOfClusters) {
				super.setNumOfClusters(numOfClusters);
				double tempMinSilhouette = super.evaluate();
				if (tempMinSilhouette > maxOverallAvargeSilhouetteWidth) {
					maxOverallAvargeSilhouetteWidth = tempMinSilhouette;
					optimalNumOfClusters = numOfClusters;
				}
			}
		}
		return optimalNumOfClusters;
	}
}
