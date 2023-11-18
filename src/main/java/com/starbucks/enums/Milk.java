package com.starbucks.enums;

public enum Milk {
	REGULAR("Regular", 0.0),
	SOY("Soy Milk", 0.20),
	ALMOND("Almond Milk", 0.30),
	OAT("Oat Milk", 0.40);

	private final String label;
	private final double additionalCost;

	Milk(String label, double additionalCost) {
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
