package com.andrija.clustering.names;


public enum LocalSearchTypes {
	VND("VND"),
	FIND_FIRST_IMPROVEMENT("FindFirstImprovement"),
	FIND_BEST_NEIGHBOR("FindBestNeighbor");
	
	private final String name;

	private LocalSearchTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static LocalSearchTypes fromString(String name) {
		name = name.trim();
		if (name != null) {
			for (LocalSearchTypes vnsType : LocalSearchTypes.values()) {
				if (name.equalsIgnoreCase(vnsType.getName())) {
					return vnsType;
				}
			}
		}
		throw new IllegalArgumentException("No local search with " + name + " name found");
	}
}
