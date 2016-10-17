package com.andrija.clustering.neighborhood;

import com.andrija.clustering.interfaces.Shakeable;
import com.andrija.clustering.names.NeighborhoodTypes;
import com.andrija.clustering.neighborhood.impl.ClosestClusterNeighborhood;
import com.andrija.clustering.neighborhood.impl.ImaginaryCentroidNeighborhood;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;

public abstract class Neighborhood implements Shakeable {
	
	public static Neighborhood createVnsNeighborhood() {
		return createNeighborhood(ConfigProperties.getConfigProperties().getVnsNeighborhoodType());
	}

	public static Neighborhood createLocalSearchNeighborhood() {
		return createNeighborhood(ConfigProperties.getConfigProperties().getLocalSearchNeighborhoodType());
	}

	private static Neighborhood createNeighborhood(NeighborhoodTypes neighborhood) {
		switch (neighborhood) {
		case IMAGINARY_CENTROID:
			return new ImaginaryCentroidNeighborhood();
		case CLOSEST_CLUSTER:
			return new ClosestClusterNeighborhood();
		default:
			throw new IllegalArgumentException("No such neighborhood found.");
		}
	}
	
	public abstract int getNeighborhoodNumber();

	public abstract void setNeighborhoodNumber(int neighborhoodNumber);
	
	protected abstract Solution reorganizeClusterIfNeeded(int targetClusterIndex, Solution solution);

}
