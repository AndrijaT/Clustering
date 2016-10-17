package com.andrija.clustering.evaluation.knowntruth;

import java.util.ArrayList;
import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class ClusterSizesEvaluation implements ClusterEvaluation{

	private Solution optimalSolution;
	private Solution finalSolution;
	private int numOfPoints;
	private int numOfClusters;
	private int avargeClusterSize;

	public ClusterSizesEvaluation(Solution optimalSolution, Solution finalSolution) {
		this.optimalSolution = optimalSolution;
		this.finalSolution = finalSolution;

		for (Cluster cluster : optimalSolution.getClusters()) {
			numOfPoints += cluster.getPoints().size();
		}
		numOfClusters = finalSolution.getClusters().size();
		avargeClusterSize = numOfPoints / numOfClusters;
	}

	/**
	 * Compares optimal and final solution by cluster sizes and returns
	 * difference percentage.
	 * 
	 * @return double percent value of difference between final and optimal
	 *         solution.
	 */
	@Override
	public double evaluate() {
		double overallClusterSizeDifference = 0;
			
		List<Cluster> finalSolutionClusters = finalSolution.getClusters();
		List<Integer> usedIndexes = new ArrayList<>(); 
		for (Cluster cluster : optimalSolution.getClusters()) {
			int optimalClusterSize = cluster.size();
			int indexOfMatchingCluster = findMatchClusterIndex(finalSolutionClusters, optimalClusterSize, usedIndexes);
			int finalClusterSize = finalSolutionClusters.get(indexOfMatchingCluster).size();
			usedIndexes.add(Integer.valueOf(indexOfMatchingCluster));
			
			double clusterSizeDifference = Integer.valueOf(Math.abs(optimalClusterSize - finalClusterSize))
					.doubleValue() / avargeClusterSize;
			overallClusterSizeDifference += clusterSizeDifference;
		}
		overallClusterSizeDifference = overallClusterSizeDifference / numOfClusters;
		return overallClusterSizeDifference;
	}
	
	private int findMatchClusterIndex(List<Cluster> clusters, int size, List<Integer> usedIndexes) {
		int clustersSize = clusters.size();
		int indexOfCluster = 0;
		int indexOfMatchingCluster = 0;
		int minSizeDifference = size - clusters.get(0).getPoints().size();
		while(indexOfCluster < clustersSize) {
			if (usedIndexes.contains(new Integer(indexOfCluster))) {
				indexOfCluster++;
				continue;
			}
			int currentSizeDifference = size - clusters.get(indexOfCluster).getPoints().size();
			if(currentSizeDifference < minSizeDifference) {
				minSizeDifference = currentSizeDifference;
				indexOfMatchingCluster = indexOfCluster;				
			}
			indexOfCluster++;
		}
		return indexOfMatchingCluster;
	}
}
