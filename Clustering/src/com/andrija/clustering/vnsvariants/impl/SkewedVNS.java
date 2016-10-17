package com.andrija.clustering.vnsvariants.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.StoppingCondition;
import com.andrija.clustering.vnsvariants.VNS;

public class SkewedVNS extends VNS {

	private Logger log = Logger.getLogger(this.getClass());
	private LocalSearch localSearch;
	private Solution initialSolution;
	private Neighborhood neighborhood;
	private int neighborhoodStructureNoMax;
	private StoppingCondition stoppingCondition;
	private double parameter;

	public SkewedVNS(LocalSearch localSearch, Solution initialSolution, Neighborhood neighborhood,
			int neighborhoodStructureNoMax, double parameter) {
		this.parameter = parameter * initialSolution.getValue();
		this.stoppingCondition = StoppingCondition.createStoppingCondition();
		this.localSearch = localSearch;
		this.initialSolution = initialSolution.copySolution();
		this.initialSolution.reorganize();
		this.initialSolution.updateValues();
		this.neighborhood = neighborhood;
		this.neighborhoodStructureNoMax = neighborhoodStructureNoMax;
	}

	@Override
	public Solution execute() {
		log.info("############## SkewedVNS");
		while (!stoppingCondition.isStopppingConditionMet()) {
			stoppingCondition.log();
			int neighborhoodStructureNo = 1;
			Solution optimalSolution = initialSolution.copySolution();
			while (neighborhoodStructureNo <= neighborhoodStructureNoMax) {
				Solution solution_prime = neighborhood.shake(initialSolution, neighborhoodStructureNo);
				Solution solution_double_prime = localSearch.find(solution_prime, neighborhoodStructureNo);

				if (solution_double_prime.getValue() < optimalSolution.getValue()) {
					/**
					 * Good Neighbor.
					 */
					optimalSolution = solution_double_prime.copySolution();
				}
				if (solution_double_prime.getValue()
						- parameter * solutionDistance(initialSolution, solution_double_prime) < initialSolution
								.getValue()) {
					initialSolution = solution_double_prime.copySolution();
					initialSolution.reorganize();
					initialSolution.updateValues();
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

	private int solutionDistance(Solution s1, Solution s2) {
		List<Integer> s1Sizes = new ArrayList<>();
		List<Integer> s2Sizes = new ArrayList<>();
		int distance = 0;
		for (Cluster c : s1.getClusters()) {
			s1Sizes.add(c.size());
		}

		for (Cluster c : s2.getClusters()) {
			s2Sizes.add(c.size());
		}

		Collections.sort(s1Sizes);
		Collections.sort(s2Sizes);

		for (int i = 0; i < s1Sizes.size(); i++) {
			distance += Math.abs(s1Sizes.get(i) - s2Sizes.get(i));
		}

		return distance / 2 / s1.getNumOfPoints();
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
