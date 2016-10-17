package com.andrija.clustering.localsearch.impl;

import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.neighborhood.LocalSearchNeighborhood;
import com.andrija.clustering.neighborhood.impl.ClosestClusterNeighborhood;
import com.andrija.clustering.solution.Solution;

public class VNDLocalSearch extends LocalSearch {

	private LocalSearchNeighborhood neighborhood;
	private final int presetNeighborhoodStructureNoMax;

	public VNDLocalSearch(int neighborhoodStructureNoMax) {
		super();
		this.presetNeighborhoodStructureNoMax = neighborhoodStructureNoMax - 1;
		this.neighborhood = new ClosestClusterNeighborhood();
	}

	@Override
	public Solution find(Solution initialSolution, int vnsNeighborhoodStructureNo) {
		if (initialSolution == null) {
			return null;
		} else {
			int neighborhoodStructureNoMax = initialSolution.getNumOfClusters() - vnsNeighborhoodStructureNo - 2;
			if (neighborhoodStructureNoMax > presetNeighborhoodStructureNoMax)
				neighborhoodStructureNoMax = presetNeighborhoodStructureNoMax;
			Solution currentBestSolution = initialSolution.copySolution();
			currentBestSolution.reorganize();
			currentBestSolution.updateValues();
			int neighborhoodStructureNo = 1;
			while (neighborhoodStructureNo <= neighborhoodStructureNoMax) {
				neighborhood.setVnsNeighborhoodStructureNo(vnsNeighborhoodStructureNo);
				Solution solution_prime = neighborhood.findBestNeighbor(currentBestSolution, neighborhoodStructureNo);
				if (solution_prime.getValue() < currentBestSolution.getValue()) {
					super.neighborhoodSearced(vnsNeighborhoodStructureNo, neighborhoodStructureNo, true);
					currentBestSolution = solution_prime;
					neighborhoodStructureNo = 1;
					currentBestSolution.log();
				} else {
					super.neighborhoodSearced(vnsNeighborhoodStructureNo, neighborhoodStructureNo, false);
					neighborhoodStructureNo++;
				}
			}
			currentBestSolution.reorganize();
			return currentBestSolution;
		}
	}
}
