package com.andrija.clustering.evaluation.unknowntruth;

import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

public class DaviesBouldinIndexEvaluation implements ClusterEvaluation {

	private List<Cluster> clusters;
	private int numOfClusters;

	public DaviesBouldinIndexEvaluation(Solution finalSolution) {
		this.clusters = finalSolution.getClusters();
		this.numOfClusters = clusters.size();
		for (Cluster cluster : clusters) {
			cluster.calculateCentroid();
		}
	}

	@Override
	public double evaluate() {
		double daviesVouldinIndex = 0;
		for (int i = 0; i < numOfClusters; i++) {
			daviesVouldinIndex += maxMeasure(i);
		}
		return daviesVouldinIndex / numOfClusters;
	}

	private double avargeDistanceToCentroid(int clusterIndex) {
		Point centroid = clusters.get(clusterIndex).getCentroid();
		double distanceToCentroidSum = 0;
		for (Point point : clusters.get(clusterIndex).getPoints()) {
			distanceToCentroidSum += point.distance(centroid);
		}
		return distanceToCentroidSum / clusters.get(clusterIndex).size();
	}

	private double distanceBetweenCentroids(int targetClusterIndex, int clusterIndex) {
		return clusters.get(targetClusterIndex).getCentroid().distance(clusters.get(clusterIndex).getCentroid());
	}

	private double maxMeasure(int targetClusterIndex) {
		double targetClusterRadius = avargeDistanceToCentroid(targetClusterIndex);
		int clusterIndex = 0;
		if (clusterIndex == targetClusterIndex)
			clusterIndex++;
		double maxMeasure = (targetClusterRadius + avargeDistanceToCentroid(clusterIndex))
				/ distanceBetweenCentroids(targetClusterIndex, clusterIndex);

		do {
			if (clusterIndex == targetClusterIndex)
				clusterIndex++;
			double tempMeasure = (targetClusterRadius + avargeDistanceToCentroid(clusterIndex))
					/ distanceBetweenCentroids(targetClusterIndex, clusterIndex);
			if (tempMeasure > maxMeasure) {
				maxMeasure = tempMeasure;
			}
			clusterIndex++;
		} while (clusterIndex < numOfClusters - 1);

		return maxMeasure;
	}
}
