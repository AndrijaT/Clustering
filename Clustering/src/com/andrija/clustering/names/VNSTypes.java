package com.andrija.clustering.names;


public enum VNSTypes {
	SKEWED_VNS("SkewedVNS"),
	BASIC_VNS("BasicVNS"),
	GENERAL_VNS("GeneralVNS"),
	REDUCED_VNS("ReducedVNS"),
	VND("VND")
	;
	
	private final String name;
	
	private VNSTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static VNSTypes fromString(String name) {
		name = name.trim();
		if (name != null) {
			for (VNSTypes vnsEnum : VNSTypes.values()) {
				if (name.equalsIgnoreCase(vnsEnum.getName())) {
					return vnsEnum;
				}
			}
		}
		throw new IllegalArgumentException("No VNS with " + name + " name found");
	}
}
