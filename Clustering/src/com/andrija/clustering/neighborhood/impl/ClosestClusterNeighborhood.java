package com.andrija.clustering.neighborhood.impl;

import java.util.ArrayList;
import java.util.List;

import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.neighborhood.LocalSearchNeighborhood;
import com.andrija.clustering.solution.Solution;

public class ClosestClusterNeighborhood extends ImaginaryCentroidNeighborhood implements LocalSearchNeighborhood {

	private int vnsNeighborhoodStructureNo;
	
	public ClosestClusterNeighborhood() {

	}

	@Override
	public Solution findBestNeighbor(Solution initialSolution, int neighborhoodStructureNo) {
		Solution optimalSolution = initialSolution.copySolution();
		optimalSolution.reorganize();
		int numberOfClusters = initialSolution.getClusters().size();

		/**
		 * Maximal neighborhood number for this neighborhood structure can't be
		 * greater than number of clusters minus 1.
		 */
		if (neighborhoodStructureNo <= numberOfClusters - 1) {
			List<int[]> neighborsStructures = findAllSubsets(optimalSolution, neighborhoodStructureNo);
			for (int[] neighbor : neighborsStructures) {
				List<Integer> clusterIndices = new ArrayList<>();
				for (int i : neighbor) {
					clusterIndices.add(new Integer(i));
				}
				Solution nextSolution = super.reorganize(initialSolution.copySolution(), clusterIndices);

				if (nextSolution.getValue() < optimalSolution.getValue()) {
					optimalSolution = nextSolution.copySolution();
					optimalSolution.updateValues();
				} else {
				}
			}
		}
		return optimalSolution;
	}

	private List<int[]> findAllSubsets(Solution solution, int localSearchNeighborhoodNo) {
		List<int[]> neighbors = new ArrayList<>();
		int clusterIndex = 0;
		for (Cluster cluster : solution.getClusters()) {
			int[] closestClustersIndices = new int[localSearchNeighborhoodNo + vnsNeighborhoodStructureNo - 1];
			double[] closestClusterDistances = new double[closestClustersIndices.length];
			double distance;
			int targetClusterIndex = 0;
			for (Cluster targetCluster : solution.getClusters()) {
				distance = cluster.getCentroid().distance(targetCluster.getCentroid());
				if (distance != 0) {
					for (int i = 0; i < localSearchNeighborhoodNo; i++) {
						if (closestClusterDistances[i] > distance || closestClusterDistances[i] == 0) {
							for (int j = closestClustersIndices.length - 1; j > i; j--) {
								closestClustersIndices[j] = closestClustersIndices[j - 1];
								closestClusterDistances[j] = closestClusterDistances[j - 1];
							}
							closestClusterDistances[i] = distance;
							closestClustersIndices[i] = targetClusterIndex;
							break;
						}
					}
				}
				targetClusterIndex++;
			}
			List<int[]> closestClusterIndicesCombinations = super.findAllSubsets(
					closestClustersIndices.length, vnsNeighborhoodStructureNo - 1);
			if (closestClusterIndicesCombinations != null) {
				for (int[] combination : closestClusterIndicesCombinations) {
					int[] neighborClusterIndices = new int[vnsNeighborhoodStructureNo];
					neighborClusterIndices[0] = clusterIndex;
					for (int combinationIndex = 0; combinationIndex < combination.length; combinationIndex++) {
						neighborClusterIndices[combinationIndex
								+ 1] = closestClustersIndices[combination[combinationIndex]];
					}
					neighbors.add(neighborClusterIndices);
				}
			} else {
				int[] neighborClusterIndices = new int[1];
				neighborClusterIndices[0] = clusterIndex;
				neighbors.add(neighborClusterIndices);
			}
			clusterIndex++;
		}
		return neighbors;
	}

	@Override
	public void setVnsNeighborhoodStructureNo(int vnsNeighborhoodStructureNo) {
		this.vnsNeighborhoodStructureNo = vnsNeighborhoodStructureNo;
	}
}
