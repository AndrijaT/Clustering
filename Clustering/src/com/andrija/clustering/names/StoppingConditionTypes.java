package com.andrija.clustering.names;


public enum StoppingConditionTypes {
	ITERATIONS("Iterations"),
	ITERATIONS_UNCHANGED("IterationsUnchanged"), 
	ITERATIONS_SMALL_IMPROVEMENT("IterationsSmallImprovement"),
	;
	
	private final String name;
	
	private StoppingConditionTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static StoppingConditionTypes fromString(String name) {
		name = name.trim();
		if (name != null) {
			for (StoppingConditionTypes vnsType : StoppingConditionTypes.values()) {
				if (name.equalsIgnoreCase(vnsType.getName())) {
					return vnsType;
				}
			}
		}
		throw new IllegalArgumentException("No stopping condition with " + name + " name found");
	}
}
