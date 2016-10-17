package com.andrija.clustering.evaluation.knowntruth;

import java.util.ArrayList;
import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.helper.KnownTruthPointModel;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED
 */
public class PurityEvaluation implements ClusterEvaluation {

	private int numOfPoints;
	private int numOfClusters;
	private List<KnownTruthPointModel> points;

	public PurityEvaluation(Solution optimalSolution, Solution finalSolution) {
		if (optimalSolution.getNumOfClusters() != finalSolution.getNumOfClusters())
			throw new IllegalArgumentException("Optimal and final solution don't have same number of clusters");
		if (optimalSolution.getNumOfPoints() != finalSolution.getNumOfPoints())
			throw new IllegalArgumentException("Optimal and final solution don't have same number of points");

		this.numOfPoints = optimalSolution.getNumOfPoints();
		this.numOfClusters = finalSolution.getNumOfClusters();

		if (numOfPoints < 3)
			throw new IllegalArgumentException("Solution has only two points");
		points = new ArrayList<>(numOfPoints);
		mapPoints(optimalSolution, finalSolution);
	}

	@Override
	public double evaluate() {
		int[] dominantClassWeigthPerEachCluster = new int[numOfClusters];
		for (int i = 0; i < numOfClusters; i++) {
			int[] classesWeightInCluster = new int[numOfClusters];
			for (KnownTruthPointModel point : points) {
				if (point.getClusterIndex() == i) {
					classesWeightInCluster[point.getClassIndex()]++;
				}
			}
			int dominantClassWeight = 0;
			for (int indexOfClassInCluster = 0; indexOfClassInCluster < classesWeightInCluster.length; indexOfClassInCluster++) {
				if (classesWeightInCluster[indexOfClassInCluster] > dominantClassWeight) {
					dominantClassWeight = classesWeightInCluster[indexOfClassInCluster];
				}
			}
			dominantClassWeigthPerEachCluster[i] = dominantClassWeight;
		}
		double result = 0;
		for (int i = 0; i < dominantClassWeigthPerEachCluster.length; i++) {
			result += dominantClassWeigthPerEachCluster[i];
		}
		result = result / numOfPoints;
		return result;
	}

	private void mapPoints(Solution optimalSolution, Solution finalSolution) {
		if (!points.isEmpty())
			points = new ArrayList<>(numOfPoints);
		for (int i = 0; i < numOfPoints; i++) {
			KnownTruthPointModel point = new KnownTruthPointModel();
			point.setClusterIndex(finalSolution.getPoints().get(i).getClusterIndex());
			point.setClassIndex(optimalSolution.getPoints().get(i).getClusterIndex());
			points.add(point);
		}
	}
}
