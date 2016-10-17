package com.andrija.clustering.measure.impl;

import java.util.List;

import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.names.MeasureTypes;
import com.andrija.clustering.solution.Solution;

public class DistanceToCentroidSumMeasure extends Measure {

	private final MeasureTypes measureType = MeasureTypes.DISTANCE_TO_CENTROID_SUM;
	private Double value;
	static long callCounter = 0;

	@Override
	public double calculate(Solution solution) {
		if (solution == null)
			throw new IllegalArgumentException();
		value = 0d;
		List<Cluster> clusters = solution.getClusters();
		if (clusters == null || clusters.isEmpty())
			throw new IllegalArgumentException();
		for (Cluster cluster : clusters) {
			if (cluster == null || cluster.getCentroid() == null || cluster.getPoints() == null
					|| cluster.getPoints().isEmpty())
				if (solution == null)
					throw new IllegalArgumentException();

			if (cluster.size() > 1) {
				for (Point point : cluster.getPoints()) {
					if (point == null)
						throw new IllegalArgumentException();
					value += Math.pow(point.distance(cluster.getCentroid()), 2);
				}
			} else {
				value += findLargestSquareDistanceToCentroid(clusters);
			}
		}
		callCounter++;
		return value;
	}

	private Double findLargestSquareDistanceToCentroid(List<Cluster> clusters) {
		double maxDistance = 0;
		for (Cluster cluster : clusters) {
			Point centroid = cluster.getCentroid();
			for (Point point : cluster.getPoints()) {
				if (centroid.distance(point) > maxDistance)
					maxDistance = centroid.distance(point);
			}
		}
		return maxDistance;
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
		return new DistanceToCentroidSumMeasure();
	}

	@Override
	public long getCallCounter() {
		return callCounter;
	}

}
