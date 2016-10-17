package com.andrija.clustering.evaluation.unknowntruth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class BakerHubertGammaIndexEvaluation implements ClusterEvaluation {

	private int numOfPoints;
	private List<Point> points;
	private Double negativeSum;
	private Double positiveSum;

	public BakerHubertGammaIndexEvaluation(Solution solution) {
		this.numOfPoints = solution.getNumOfPoints();
		this.points = solution.getPoints();

	}

	@Override
	public double evaluate() {
		if (positiveSum == null || negativeSum == null)
			calculate();
		return (positiveSum - negativeSum) / (positiveSum + negativeSum);
	}

	protected void calculate() {
		negativeSum = Double.valueOf(0);
		positiveSum = Double.valueOf(0);
		Iterator<PairIndices> iterator = new PairIndicesIterator(numOfPoints);
		List<Double> withinDistances = new ArrayList<>();
		List<Double> betweenDistances = new ArrayList<>();
		while (iterator.hasNext()) {
			PairIndices indices = iterator.next();
			Point firstPoint = points.get(indices.getFirstIndex());
			Point secondPoint = points.get(indices.getSecondIndex());
			double distance = firstPoint.distance(secondPoint);
			if (firstPoint.getClusterIndex() == secondPoint.getClusterIndex()) {
				withinDistances.add(distance);
			} else {
				betweenDistances.add(distance);
			}
		}
		for (Double betweenDistance : betweenDistances) {
			for (Double withinDistance : withinDistances) {
				if (betweenDistance > withinDistance) {
					positiveSum++;
				} else {
					negativeSum++;
				}
			}
		}
	}

	protected Double getNegativeSum() {
		return negativeSum;
	}

	protected Double getPositiveSum() {
		return positiveSum;
	}
}
