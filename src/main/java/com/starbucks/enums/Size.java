package com.starbucks.enums;

public enum Size {

	TALL("Tall", 0.0),
	GRANDE("Grande", 0.50),
	VENTI("Venti", 1.0);

	private final String label;
	private final double additionalCost;

	Size(String label, double additionalCost) {
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
