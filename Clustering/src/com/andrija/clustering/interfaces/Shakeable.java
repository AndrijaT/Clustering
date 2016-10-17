package com.andrija.clustering.interfaces;

import com.andrija.clustering.solution.Solution;

public interface Shakeable {

	/**
	 * @param initialSolution
	 * @param neighborhoodStructureNo
	 * @return
	 */
	public Solution shake(Solution initialSolution, int neighborhoodStructureNo);
	
	public Solution findBestNeighbor(Solution initialSolution, int neighborhoodStructureNo);
	
	public Solution findFirstImprovement(Solution initialSolution, int neighborhoodStructureNo);
}
