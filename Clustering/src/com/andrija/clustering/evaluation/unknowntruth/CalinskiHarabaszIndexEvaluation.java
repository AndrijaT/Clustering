package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.internalmeasure.ScatterMeasure;
import com.andrija.clustering.solution.Solution;

public class CalinskiHarabaszIndexEvaluation implements ClusterEvaluation {

	private ScatterMeasure measure;
	private int numOfPoints;
	private int numOfClusters;

	public CalinskiHarabaszIndexEvaluation(Solution solution) {
		this.measure = new ScatterMeasure(solution);
		this.numOfPoints = solution.getNumOfPoints();
		this.numOfClusters = solution.getNumOfClusters(); 
	}

	@Override
	public double evaluate() {
		double BGSS = measure.getBGSS();
		double WGSS = measure.getWGSS();
		return ((numOfPoints - numOfClusters) / (numOfClusters - 1)) * (BGSS / WGSS);
	}
}
