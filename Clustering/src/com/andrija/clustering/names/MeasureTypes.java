package com.andrija.clustering.names;


public enum MeasureTypes {
	DISTANCE_TO_CENTROID_SUM("DistanceToCentroidSum"),
	WITHIN_CLUSTER_PAIRS_OF_POINT_DISTANCES("WithinClusterPairsOfPointDustances"),
	INTRA_INTER_DISTANCES("IntraInterDistances"),
	;
	
	private final String name;
	
	private MeasureTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static MeasureTypes fromString(String name) {
		name = name.trim();
		if (name != null) {
			for (MeasureTypes vnsEnum : MeasureTypes.values()) {
				if (name.equalsIgnoreCase(vnsEnum.getName())) {
					return vnsEnum;
				}
			}
		}
		throw new IllegalArgumentException("No measure with " + name + " name found");
	}
}
