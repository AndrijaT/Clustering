package com.andrija.clustering.evaluation.unknowntruth.internalmeasure;

import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.solution.Solution;

public class PairsOfPointsMeasure {

	private int numOfPoints;
	private int numOfClusters;
	private int[] clusterSizes;
	private List<Cluster> clusters;

	private int numOfPairsWithin;
	private int numOfPairsBetween;
	private double sumWithin;
	private double sumWithinMin;
	private double sumWithinMax;

	public PairsOfPointsMeasure(Solution solution) {
		this.numOfPoints = solution.getNumOfPoints();
		this.numOfClusters = solution.getNumOfClusters();
		this.clusterSizes = new int[numOfClusters];
		this.clusters = solution.getClusters();
		for (int i = 0; i < numOfClusters; i++) {
			this.clusterSizes[i] = this.clusters.get(i).size();
		}
	}

	public int getBetweenNum() {
		if (numOfPairsBetween == 0)
			calculateBetween();
		return numOfPairsBetween;
	}

	public int getWithinNum() {
		if (numOfPairsWithin == 0)
			calculateWithin();
		return numOfPairsWithin;
	}

	public double getWithinSum() {
		if (sumWithin == 0)
			calculateWithinSum();
		return sumWithin;
	}

	public double getWithinSumMin() {
		if (sumWithinMin == 0)
			calculateWithinSum();
		return sumWithinMin;
	}
	
	public double getWithinSumMax() {
		if (sumWithinMax == 0)
			calculateWithinSum();
		return sumWithinMax;
	}

	public int getTotalNum() {
		return getBetweenNum() + getWithinNum();
	}

	private void calculateWithin() {
		for (int i = 0; i < numOfClusters; i++) {
			numOfPairsWithin += clusterSizes[i] * clusterSizes[i];
		}
		numOfPairsWithin = (numOfPairsWithin  - numOfPoints) / 2;
	}

	private void calculateWithinSum() {
		if (sumWithin != 0 && sumWithinMin != 0)
			return;
		sumWithin = 0;
		sumWithinMin = 0;
		for (Cluster cluster : clusters) {
			Iterator<PairIndices> iterator = new PairIndicesIterator(cluster.size());
			double minDistance = cluster.getPoint(0).distance(cluster.getPoint(1));
			double maxDistance = cluster.getPoint(0).distance(cluster.getPoint(1));
			while (iterator.hasNext()) {
				PairIndices indices = iterator.next();
				double pairDistance = cluster.getPoint(indices.getFirstIndex())
						.distance(cluster.getPoint(indices.getSecondIndex()));
				sumWithin += pairDistance;
				if (pairDistance < minDistance)
					minDistance = pairDistance;
				if (pairDistance > maxDistance)
					maxDistance = pairDistance;
			}
			sumWithinMin += minDistance;
			sumWithinMax += maxDistance;
		}
	}

	private void calculateBetween() {
		for (int i = 0; i < numOfClusters - 1; i++) {
			for (int j = i + 1; j < numOfClusters; j++) {
				numOfPairsBetween += clusterSizes[i] * clusterSizes[j];
			}
		}
	}
}
