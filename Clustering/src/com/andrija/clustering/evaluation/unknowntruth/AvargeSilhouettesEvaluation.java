package com.andrija.clustering.evaluation.unknowntruth;

import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

public class AvargeSilhouettesEvaluation implements ClusterEvaluation {

	private List<Point> points;
	private int numOfPoints;
	private int numOfClusters;
	private int precision;

	public AvargeSilhouettesEvaluation(Solution finalSolution, int precision) {
		this.points = finalSolution.getPoints();
		this.numOfPoints = finalSolution.getNumOfPoints();
		this.numOfClusters = finalSolution.getNumOfClusters();
		if (precision == 0) {
			throw new IllegalArgumentException("Precision is zero");
		} else if (precision > numOfPoints / (numOfClusters * 2)) {
			throw new IllegalArgumentException("Precision is too big");
		} else {
			this.precision = precision;
		}
	}

	public AvargeSilhouettesEvaluation(Solution finalSolution) {
		this(finalSolution, 1);
	}

	@Override
	public double evaluate() {
		double overallSilhouetteSum = 0;
		for (int clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex += precision) {
			overallSilhouetteSum += averageClusterSilhouette(clusterIndex);
		}
		return overallSilhouetteSum / numOfClusters;
	}

	/**
	 * Calculates average distance (dissimilarity) from specified point to
	 * specified cluster cluster. If target point is element of target cluster,
	 * this measures within-cluster dissimilarity. If target point is not
	 * element of target cluster, this measures between-cluster dissimilarity.
	 * 
	 * @param pointIndex
	 *            - target point index
	 * @param clusterIndex
	 *            - target cluster index
	 * @return average distance from target point to every point of target
	 *         cluster dissimilar to target point
	 */
	private double avergeDissimilarity(int pointIndex, int clusterIndex) {
		if (pointIndex >= numOfPoints)
			throw new IllegalArgumentException("Invalid point index");
		if (clusterIndex >= numOfClusters)
			throw new IllegalArgumentException("Invalid cluster index");
		Point targetPoint = points.get(pointIndex);
		double avargeDistance = 0;
		int clusterWeight = 0;
		for (Point point : points) {
			if (point.getClusterIndex() == clusterIndex && point.getIndex() != targetPoint.getIndex()) {
				avargeDistance += targetPoint.distance(point);
				clusterWeight++;

			}
		}
		/**
		 * When cluster A contains only a single object it is unclear
		 * how u(i) should be defined, and then we simply set s(i) equal
		 * to zero. This choice is of course arbitrary, but a value of
		 * zero appears to be most neutral. I
		 */
		if (points.get(pointIndex).getClusterIndex() == clusterIndex && clusterWeight == 0 ||
				points.get(pointIndex).getClusterIndex() != clusterIndex && clusterWeight < 2)
			return 0;
		avargeDistance = avargeDistance / clusterWeight;
		return avargeDistance;
	}

	/**
	 * Finds smallest average distance (dissimilarity) to cluster. In another
	 * words finds best neighbor cluster for target point.
	 * 
	 * @param pointIndex
	 *            - target point index
	 * @return minimum average distance from target point to every point of a
	 *         cluster dissimilar to target point's cluster
	 */
	private double avergeDissimilarityToClosestCluster(int pointIndex) {
		int clusterIndex = 0;
		if (clusterIndex == points.get(pointIndex).getClusterIndex())
			clusterIndex++;
		double minAverageDissimilarity = avergeDissimilarity(pointIndex, clusterIndex);
		for (clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex++) {
			if (clusterIndex != points.get(pointIndex).getClusterIndex()) {
				double tempAvergeDissimilarity = avergeDissimilarity(pointIndex, clusterIndex);
				if (tempAvergeDissimilarity < minAverageDissimilarity)
					minAverageDissimilarity = tempAvergeDissimilarity;
			}
		}
		return minAverageDissimilarity;
	}

	/**
	 * Calculates average cluster distance (dissimilarity) to nearest clusters
	 * 
	 * @param clusterIndex
	 *            - target cluster index
	 * @return average of all dissimilarities between each target's cluster
	 *         point and it's closest cluster other than target cluster
	 */
	private double averageClusterSilhouette(int clusterIndex) {
		double clusterSilhouetteSum = 0;
		int clusterWeigth = 0;
		for (Point point : points) {
			if (point.getClusterIndex() == clusterIndex) {
				double withinClusterDissimilarity = avergeDissimilarity(point.getIndex(), point.getClusterIndex());
				double distanceToClosestCluster = avergeDissimilarityToClosestCluster(point.getIndex());
				double maxDissimilarity = Math.max(distanceToClosestCluster, withinClusterDissimilarity);
				clusterSilhouetteSum += (distanceToClosestCluster - withinClusterDissimilarity) / maxDissimilarity;
				clusterWeigth++;
			}
		}
		return clusterSilhouetteSum / clusterWeigth;
	}

	protected int getNumOfClusters() {
		return numOfClusters;
	}

	protected void setNumOfClusters(int numOfClusters) {
		this.numOfClusters = numOfClusters;
	}
}
