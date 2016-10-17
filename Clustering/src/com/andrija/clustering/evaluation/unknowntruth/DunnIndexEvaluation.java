package com.andrija.clustering.evaluation.unknowntruth;

import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

public class DunnIndexEvaluation implements ClusterEvaluation {

	private int numOfClusters;
	private List<Cluster> clusters;

	public DunnIndexEvaluation(Solution solution) {
		this.clusters = solution.getClusters();
		this.numOfClusters = clusters.size();
	}

	@Override
	public double evaluate() {
		double minDistance = minInterClusterDistance();
		double maxDiameter = maxClusterDiameter();
		return minDistance / maxDiameter;
	}

	private double interClusterDistance(int firstClusterIndex, int secondClusterIndex) {
		Cluster firstCluster = clusters.get(firstClusterIndex);
		Cluster secondCluster = clusters.get(secondClusterIndex);

		double interClusterDistance = firstCluster.getPoint(0).distance(secondCluster.getPoint(0));
		for (Point firstPoint : firstCluster.getPoints()) {
			for (Point secondPoint : secondCluster.getPoints()) {
				double tempInterClusterDistance = firstPoint.distance(secondPoint);
				if (tempInterClusterDistance < interClusterDistance)
					interClusterDistance = tempInterClusterDistance;
			}
		}
		return interClusterDistance;
	}

	private double minInterClusterDistance() {
		Iterator<PairIndices> iterator = new PairIndicesIterator(numOfClusters);
		PairIndices clusterIndices = iterator.next();
		double minClusterDistance = interClusterDistance(clusterIndices.getFirstIndex(),
				clusterIndices.getSecondIndex());

		while (iterator.hasNext()) {
			clusterIndices = iterator.next();
			double tempClusterDistance = interClusterDistance(clusterIndices.getFirstIndex(),
					clusterIndices.getSecondIndex());
			if (tempClusterDistance < minClusterDistance)
				minClusterDistance = tempClusterDistance;
		}
		return minClusterDistance;
	}

	private double clusterDiameter(int clusterIndex) {
		Cluster cluster = clusters.get(clusterIndex);
		int clusterSize = cluster.size();
		if (clusterSize < 2)
			return 0;
		Iterator<PairIndices> iterator = new PairIndicesIterator(clusterSize);
		PairIndices pointIndices = iterator.next();
		double clusterDiameter = cluster.getPoint(pointIndices.getFirstIndex())
				.distance(cluster.getPoint(pointIndices.getSecondIndex()));
		while (iterator.hasNext()) {
			pointIndices = iterator.next();
			double tempClusterDiameter = cluster.getPoint(pointIndices.getFirstIndex())
					.distance(cluster.getPoint(pointIndices.getSecondIndex()));
			if (tempClusterDiameter > clusterDiameter)
				clusterDiameter = tempClusterDiameter;
		}
		return clusterDiameter;
	}

	private double maxClusterDiameter() {
		double maxDiameter = 0;
		for (int clusterIndex = 0; clusterIndex < clusters.size(); clusterIndex++) {
			double tempDiameter = clusterDiameter(clusterIndex);
			if (tempDiameter > maxDiameter)
				maxDiameter = tempDiameter;
		}
		return maxDiameter;
	}
}
