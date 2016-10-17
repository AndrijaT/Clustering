package com.andrija.clustering.evaluation.helper;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PairIndicesIterator implements Iterator<PairIndices> {

	private final int setSize;
	private PairIndices indices;

	public PairIndicesIterator(int setSize) {
		if(setSize < 1) throw new IllegalArgumentException("Set is empty.");
		this.setSize = setSize;
	}

	@Override
	public boolean hasNext() {
		if(setSize < 2)
			return false;
		if(indices == null) {
			return true;
		} else if (indices.getSecondIndex() < setSize && indices.getFirstIndex() < indices.getSecondIndex()
				&& indices.getFirstIndex() < setSize - 2) {
			return true;
		}
		return false;
	}

	@Override
	public PairIndices next() {
		if (!hasNext()) { 
			throw new NoSuchElementException();
		} else {
			if(indices == null) {
				indices = new PairIndices(0, 1);
			} else if (indices.getSecondIndex() == setSize - 1) {
				indices.setFirstIndex(indices.getFirstIndex() + 1);
				indices.setSecondIndex(indices.getFirstIndex() + 1);
			} else {
				indices.setSecondIndex(indices.getSecondIndex() + 1);
			}
		} 
		return indices;
	}
}
