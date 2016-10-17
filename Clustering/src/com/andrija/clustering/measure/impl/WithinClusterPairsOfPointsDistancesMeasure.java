package com.andrija.clustering.measure.impl;

import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.names.MeasureTypes;
import com.andrija.clustering.solution.Solution;

public class WithinClusterPairsOfPointsDistancesMeasure extends Measure {

	private static int callCounter = 0;
	private MeasureTypes measureType = MeasureTypes.WITHIN_CLUSTER_PAIRS_OF_POINT_DISTANCES;
	private Double value;

	@Override
	public Measure copyMeasure() {
		return new WithinClusterPairsOfPointsDistancesMeasure();
	}

	@Override
	public double calculate(Solution solution) {
		if (solution == null)
			throw new IllegalArgumentException();
		value = 0d;
		if (solution.getClusters() == null || solution.getClusters().isEmpty())
			throw new IllegalArgumentException();
		for (Cluster cluster : solution.getClusters()) {
			if (cluster == null || cluster.getPoints() == null || cluster.getPoints().isEmpty())
				throw new IllegalArgumentException();
			double clusterPairsDistancesSum = 0;
			if (cluster.size() > 1) {
				Iterator<PairIndices> iterator = new PairIndicesIterator(cluster.size());
				while (iterator.hasNext()) {
					PairIndices indices = iterator.next();
					clusterPairsDistancesSum += cluster.getPoint(indices.getFirstIndex())
							.distance(cluster.getPoint(indices.getSecondIndex()));
				}
				value += clusterPairsDistancesSum / (cluster.size() * (cluster.size() - 1) / 2);
			} else {
				value += largestPairDistance(solution);
			}
		}
		callCounter++;
		return value;
	}

	private Double largestPairDistance(Solution solution) {
		List<Point> points = solution.getPoints();
		double maxDistance = 0;
		for (int i = 0; i < points.size() - 1; i++)
			for (int j = i + 1; i < points.size(); i++)
				if (points.get(i).distance(points.get(j)) > maxDistance)
					maxDistance = points.get(i).distance(points.get(j));
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
	public long getCallCounter() {
		return callCounter;
	}

}
