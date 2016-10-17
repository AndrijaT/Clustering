package com.andrija.clustering.vnsvariants;

import com.andrija.clustering.interfaces.Executable;
import com.andrija.clustering.localsearch.LocalSearch;
import com.andrija.clustering.names.VNSTypes;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.StoppingCondition;
import com.andrija.clustering.vnsvariants.impl.BasicVNS;
import com.andrija.clustering.vnsvariants.impl.GeneralVNS;
import com.andrija.clustering.vnsvariants.impl.ReducedVNS;
import com.andrija.clustering.vnsvariants.impl.SkewedVNS;
import com.andrija.clustering.vnsvariants.impl.VND;

public abstract class VNS implements Executable {
	
	private static VNS vns;

	public static VNS createVNS(Solution initialSolution) {
		ConfigProperties config = ConfigProperties.getConfigProperties();
		int neighborhoodStructureNoMax = config.getNeighborhoodMax();
		int localSearchNeighborhoodStructureNoMax = config.getNeighborhoodLocalSearchMax();
		Neighborhood vnsNeighborhood = Neighborhood.createVnsNeighborhood();
		VNSTypes vnsType = config.getVnsType();
		LocalSearch localSearch;
		Neighborhood localSearchNeighborhood;

		switch (vnsType) {
		case VND:
			vns = new VND(initialSolution, vnsNeighborhood, neighborhoodStructureNoMax);
			break;
		case REDUCED_VNS:
			vns = new ReducedVNS(initialSolution, vnsNeighborhood, neighborhoodStructureNoMax);
			break;
		case GENERAL_VNS:
			vns = new GeneralVNS(initialSolution, vnsNeighborhood, neighborhoodStructureNoMax,
					localSearchNeighborhoodStructureNoMax);
			break;
		case BASIC_VNS:
			localSearchNeighborhood = Neighborhood.createLocalSearchNeighborhood();
			localSearch = LocalSearch.createLocalSearch(localSearchNeighborhood,
					localSearchNeighborhoodStructureNoMax);
			vns = new BasicVNS(localSearch, initialSolution, vnsNeighborhood, neighborhoodStructureNoMax);
			break;
		case SKEWED_VNS:
			localSearchNeighborhood = Neighborhood.createLocalSearchNeighborhood();
			localSearch = LocalSearch.createLocalSearch(localSearchNeighborhood,
					localSearchNeighborhoodStructureNoMax);
			vns = new SkewedVNS(localSearch, initialSolution, vnsNeighborhood, neighborhoodStructureNoMax, config.getSkewedParameter());
			break;
		}
		return vns;
	}

	public static VNS getVns() {
		return vns;
	}

	public abstract StoppingCondition getStoppingCondition();

	public abstract LocalSearch getLocalSearch();
}
