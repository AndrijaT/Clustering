package com.andrija.clustering.vnsvariants.impl;

import org.apache.log4j.Logger;

import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.localsearch.impl.VNDLocalSearch;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.StoppingCondition;
import com.andrija.clustering.vnsvariants.VNS;

public class GeneralVNS extends VNS {

	private Logger log = Logger.getLogger(this.getClass());
	private LocalSearch localSearch;
	private Solution initialSolution;
	private Neighborhood neighborhood;
	private int neighborhoodStructureNoMax;
	private StoppingCondition stoppingCondition;

	public GeneralVNS(Solution initialSolution, Neighborhood neighborhood, int neighborhoodStructureNoMax,
			int localSearchNeighborhoodStructureNoMax) {
		this.stoppingCondition = StoppingCondition.createStoppingCondition();
		this.localSearch = new VNDLocalSearch(localSearchNeighborhoodStructureNoMax);
		this.initialSolution = initialSolution.copySolution();
		this.initialSolution.reorganize();
		this.initialSolution.updateValues();
		this.neighborhood = neighborhood;
		this.neighborhoodStructureNoMax = neighborhoodStructureNoMax;
	}

	@Override
	public Solution execute() {
		log.info("############## GeneralVNS");
		while (!stoppingCondition.isStopppingConditionMet()) {
			stoppingCondition.log();
			int neighborhoodStructureNo = 1;
			while (neighborhoodStructureNo <= neighborhoodStructureNoMax) {
				Solution solution_prime = neighborhood.shake(initialSolution, neighborhoodStructureNo);
				Solution solution_double_prime = localSearch.find(solution_prime, neighborhoodStructureNo);
				if (solution_double_prime.getValue() < initialSolution.getValue()) {
					/**
					 * Good Neighbor.
					 */
					initialSolution = solution_double_prime;
					neighborhoodStructureNo = 1;
				} else {
					/**
					 * Bad Neighbor.
					 */
					neighborhoodStructureNo++;
				}
			}
			log.info("END OF ITERATION");
			initialSolution.log();
			stoppingCondition.next(initialSolution);
		}
		return initialSolution;
	}

	@Override
	public StoppingCondition getStoppingCondition() {
		return stoppingCondition;
	}

	@Override
	public LocalSearch getLocalSearch() {
		return localSearch;
	}

}
