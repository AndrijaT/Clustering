package com.andrija.clustering.localsearch.impl;

import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.solution.Solution;

public class FindFirstImprovementLocalSearch extends LocalSearch {

	private Neighborhood neighborhood;

	public FindFirstImprovementLocalSearch(Neighborhood neighborhood) {
		super();
		this.neighborhood = neighborhood;
	}

	@Override
	public Solution find(Solution solution_prime, int neighborhoodStructureNo) {
		Solution s = neighborhood.findFirstImprovement(solution_prime, neighborhoodStructureNo);
		super.neighborhoodSearced(neighborhoodStructureNo, s.getValue() < solution_prime.getValue());
		s.log();
		return s;
	}
}
