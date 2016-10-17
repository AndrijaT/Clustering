package com.andrija.clustering.vnsvariants.impl;

import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.localsearch.impl.FindBestNeighborLocalSearch;
import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.StoppingCondition;
import com.andrija.clustering.vnsvariants.VNS;

public class VND extends VNS {

	private Solution initialSolution;
	private LocalSearch localSearch;
	private int neighborhoodStructureNoMax;

	public VND(Solution initialSolution, Neighborhood neighborhood, int neighborhoodStructureNoMax) {
		this.initialSolution = initialSolution;
		this.neighborhoodStructureNoMax = neighborhoodStructureNoMax;
		this.localSearch = new FindBestNeighborLocalSearch(neighborhood);
	}

	@Override
	public Solution execute() {
		if (initialSolution != null) {
			int neighborhoodStructureNo = 1;
			while (neighborhoodStructureNo <= neighborhoodStructureNoMax) {
				Solution solution_prime = localSearch.find(initialSolution, neighborhoodStructureNo);
				if (solution_prime.getValue() < initialSolution.getValue()) {
					/**
					 * Good Neighbor.
					 */
					initialSolution = solution_prime;
					neighborhoodStructureNo = 1;
				} else {
					/**
					 * Bad Neighbor.
					 */
					neighborhoodStructureNo++;
				}
			}
			initialSolution.reorganize();
			return initialSolution;
		} else {
			return null;
		}
	}

	@Override
	public StoppingCondition getStoppingCondition() {
		return new StoppingCondition() {
			
			@Override
			public void next(Solution solution) {
			}
			
			@Override
			public void log() {
			}
			
			@Override
			public boolean isStopppingConditionMet() {
				return true;
			}
			
			@Override
			public StoppingConditionTypes getType() {
				return StoppingConditionTypes.ITERATIONS_UNCHANGED;
			}
			
			@Override
			public int getIteration() {
				return 1;
			}
		};
	}

	@Override
	public LocalSearch getLocalSearch() {
		return localSearch;
	}
}
