package com.andrija.clustering.evaluation.helper;

public class PairIndices {

	private int firstIndex;
	private int secondIndex;

	public PairIndices(int firstIndex, int secondIndex) {
		super();
		this.firstIndex = firstIndex;
		this.secondIndex = secondIndex;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getSecondIndex() {
		return secondIndex;
	}

	public void setSecondIndex(int secondIndex) {
		this.secondIndex = secondIndex;
	}
}
