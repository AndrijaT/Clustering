package com.andrija.clustering.localsearch.impl;

import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.solution.Solution;

public class FindBestNeighborLocalSearch extends LocalSearch {

	private Neighborhood neighborhood;

	public FindBestNeighborLocalSearch(Neighborhood neighborhood) {
		super();
		this.neighborhood = neighborhood;
	}

	@Override
	public Solution find(Solution solution_prime, int neighborhoodStructureNo) {
		Solution s = neighborhood.findBestNeighbor(solution_prime, neighborhoodStructureNo);
		super.neighborhoodSearced(neighborhoodStructureNo, s.getValue() < solution_prime.getValue());
		return s;
	}
}
