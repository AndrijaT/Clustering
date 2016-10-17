package com.andrija.clustering.names;


public enum NeighborhoodTypes {
	IMAGINARY_CENTROID("ImaginaryCentroid"),
	CLOSEST_CLUSTER("ClosestCluster"),
//	DISTANCE_TO_CLOSEST_CLUSTER("DistanceToClosestCluster"),
//	ASSIGN_POINT_TO_CLOSEST_CENTROID("AssignPointToClosestCentroidWithCentroidMovingInShake"),
//	REORGANIZED_CLUSTERS_ACCORDING_OBJECTIVE_FUNCTION("ReorganizedClustersAccordingObjectiveFunction"),
	;
	
	private final String name;
	
	private NeighborhoodTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static NeighborhoodTypes fromString(String name) {
		name = name.trim();
		if (name != null) {
			for (NeighborhoodTypes vnsEnum : NeighborhoodTypes.values()) {
				if (name.equalsIgnoreCase(vnsEnum.getName())) {
					return vnsEnum;
				}
			}
		}
		throw new IllegalArgumentException("No neighborhood with " + name + " name found");
	}
}
