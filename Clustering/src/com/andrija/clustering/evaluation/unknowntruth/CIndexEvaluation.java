package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.internalmeasure.PairsOfPointsMeasure;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class CIndexEvaluation implements ClusterEvaluation {

	private PairsOfPointsMeasure pairsMeasure;
	private double distances;
	private double distancesMin;
	private double distancesMax;

	public CIndexEvaluation(Solution solution) {
		this.pairsMeasure = new PairsOfPointsMeasure(solution);
	}

	@Override
	public double evaluate() {
		if (distances == 0)
			distances = pairsMeasure.getWithinSum();
		if (distancesMin == 0)
			distancesMin = pairsMeasure.getWithinSumMin();
		if (distancesMax == 0)
			distancesMax = pairsMeasure.getWithinSumMax();
		return (distances - distancesMin) / (distancesMax - distancesMin);
	}

}
