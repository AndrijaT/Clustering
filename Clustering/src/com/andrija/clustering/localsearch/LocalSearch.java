package com.andrija.clustering.localsearch;

import com.andrija.clustering.localsearch.impl.FindBestNeighborLocalSearch;
import com.andrija.clustering.localsearch.impl.FindFirstImprovementLocalSearch;
import com.andrija.clustering.localsearch.impl.VNDLocalSearch;
import com.andrija.clustering.names.LocalSearchTypes;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;

public abstract class LocalSearch {

	private int neighborhoodMax;
	private int localSearchNeighborhoodMax;
	private int[] neighborhoodSearchNum;
	private int[] neighborhoodSuccessfulSearchNum;
	private int[] localSearchNeighborhoodSearchNum;
	private int[] localSearchNeighborhoodSuccessfulSearchNum;

	public LocalSearch() {
		this.neighborhoodMax = ConfigProperties.getConfigProperties().getNeighborhoodMax();
		this.localSearchNeighborhoodMax = ConfigProperties.getConfigProperties().getNeighborhoodLocalSearchMax();
		this.neighborhoodSearchNum = new int[neighborhoodMax];
		this.neighborhoodSuccessfulSearchNum = new int[neighborhoodMax];
		this.localSearchNeighborhoodSearchNum = new int[localSearchNeighborhoodMax];
		this.localSearchNeighborhoodSuccessfulSearchNum = new int[localSearchNeighborhoodMax];
	}

	public abstract Solution find(Solution solution_prime, int neighborhoodStructureNo);

	public static LocalSearch createLocalSearch(Neighborhood localSearchNeighborhood,
			int localSearchNeighborhoodStructureNoMax) {

		LocalSearchTypes localSearch = ConfigProperties.getConfigProperties().getLocalSearchType();

		switch (localSearch) {
		case VND:
			return new VNDLocalSearch(localSearchNeighborhoodStructureNoMax);
		case FIND_FIRST_IMPROVEMENT:
			return new FindFirstImprovementLocalSearch(localSearchNeighborhood);
		case FIND_BEST_NEIGHBOR:
			return new FindBestNeighborLocalSearch(localSearchNeighborhood);
		default:
			return null;
		}
	}

	protected void neighborhoodSearced(int neighborhoodNum, int localSearchNeighborhoodNum, boolean successful) {
		if (neighborhoodNum < neighborhoodMax) {
			neighborhoodSearchNum[neighborhoodNum - 1]++;
			if (successful) {
				neighborhoodSuccessfulSearchNum[neighborhoodNum - 1]++;
			}
		}
		if (localSearchNeighborhoodNum < localSearchNeighborhoodMax) {
			localSearchNeighborhoodSearchNum[localSearchNeighborhoodNum -1 ]++;
			if(successful) {
				localSearchNeighborhoodSuccessfulSearchNum[localSearchNeighborhoodNum - 1]++;
			}
		}
	}

	protected void neighborhoodSearced(int neighborhoodNum, boolean successful) {
		neighborhoodSearced(neighborhoodNum, 1, successful);
	}

	public int[] getNeighborhoodSearchNum() {
		return neighborhoodSearchNum;
	}

	public int[] getNeighborhoodSuccessfulSearchNum() {
		return neighborhoodSuccessfulSearchNum;
	}

	public int[] getLocalSearchNeighborhoodSearchNum() {
		return localSearchNeighborhoodSearchNum;
	}

	public int[] getLocalSearchNeighborhoodSuccessfulSearchNum() {
		return localSearchNeighborhoodSuccessfulSearchNum;
	}

}
