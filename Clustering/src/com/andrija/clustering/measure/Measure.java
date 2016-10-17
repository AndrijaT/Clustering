package com.andrija.clustering.measure;

import com.andrija.clustering.measure.impl.DistanceToCentroidSumMeasure;
import com.andrija.clustering.measure.impl.IntraInterDistancesMeasure;
import com.andrija.clustering.measure.impl.WithinClusterPairsOfPointsDistancesMeasure;
import com.andrija.clustering.names.MeasureTypes;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;

public abstract class Measure {
	
	public static Measure createMeasure() {
		MeasureTypes measureType = ConfigProperties.getConfigProperties().getMesureType();
		switch (measureType) {
		case DISTANCE_TO_CENTROID_SUM:
			return new DistanceToCentroidSumMeasure();
		case INTRA_INTER_DISTANCES:
			return new IntraInterDistancesMeasure();
		case WITHIN_CLUSTER_PAIRS_OF_POINT_DISTANCES:
			return new WithinClusterPairsOfPointsDistancesMeasure();
		default:
			throw new IllegalArgumentException("Invalid measure name");
		}
	}

	public abstract double calculate(Solution solution);
	
	public abstract Double getValue();
	
	public abstract MeasureTypes getMeasureType();
	
	public abstract Measure copyMeasure();
	
	public abstract long getCallCounter();
}
