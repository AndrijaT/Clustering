package com.andrija.clustering.measure.impl;

import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.names.MeasureTypes;
import com.andrija.clustering.solution.Solution;

public class IntraInterDistancesMeasure extends DistanceToCentroidSumMeasure {

	private final MeasureTypes measureType = MeasureTypes.INTRA_INTER_DISTANCES;
	private Double value;

	@Override
	public double calculate(Solution solution) {
		value = super.calculate(solution) / interClusterDistances(solution.getClusters());
		return value;
	}

	private double interClusterDistances(List<Cluster> clusters) {
		double interClusterDistanceSum = 0;
		try {
			Iterator<PairIndices> iterator = new PairIndicesIterator(clusters.size());
			while (iterator.hasNext()) {
				PairIndices indices = iterator.next();
				interClusterDistanceSum += clusters.get(indices.getFirstIndex()).getCentroid()
						.distance(clusters.get(indices.getSecondIndex()).getCentroid());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return interClusterDistanceSum;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public MeasureTypes getMeasureType() {
		return measureType;
	}

	@Override
	public Measure copyMeasure() {
		return new IntraInterDistancesMeasure();
	}

}
