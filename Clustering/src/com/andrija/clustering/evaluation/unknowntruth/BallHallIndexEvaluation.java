package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.internalmeasure.ScatterMeasure;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class BallHallIndexEvaluation implements ClusterEvaluation {

	private int[] clusterWeigths;
	private ScatterMeasure measure;

	public BallHallIndexEvaluation(Solution solution) {
		this.measure = new ScatterMeasure(solution);
		clusterWeigths = new int[solution.getNumOfClusters()];
		for (int i = 0; i < solution.getNumOfClusters(); i++) {
			clusterWeigths[i] = solution.getCluster(i).getPoints().size();
		}
	}

	@Override
	public double evaluate() {
		double theBallHallIndex = 0;
		for (int i = 0; i < clusterWeigths.length; i++) {
			theBallHallIndex = measure.getWGSS(i) / clusterWeigths[i];
		}
		theBallHallIndex = theBallHallIndex / clusterWeigths.length;
		return theBallHallIndex;
	}
}
