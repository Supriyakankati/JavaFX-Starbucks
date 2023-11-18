package com.starbucks.enums;

public enum Flavor {
	NONE("None", 0.0),
	VANILLA("Vanilla", 0.30),
	CARAMEL("Caramel", 0.30),
	HAZELNUT("Hazelnut", 0.40);

	private final String label;
	private final double additionalCost;

	Flavor(String label, double additionalCost) {
		this.label = label;
		this.additionalCost = additionalCost;
	}

	public String getLabel() {
		return label;
	}

	public double getAdditionalCost() {
		return additionalCost;
	}
}