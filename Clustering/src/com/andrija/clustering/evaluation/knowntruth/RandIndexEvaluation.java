package com.andrija.clustering.evaluation.knowntruth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.helper.KnownTruthPointModel;
import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED
 */
public class RandIndexEvaluation implements ClusterEvaluation {

	private List<KnownTruthPointModel> points;
	private int numOfPoints;
	private int truePositiveCount;
	private int trueNegativeCount;
	private int falsePositiveCount;
	private int falseNegativeCount;

	public RandIndexEvaluation(Solution optimalSolution, Solution finalSolution) {
		if (optimalSolution.getNumOfClusters() != finalSolution.getNumOfClusters())
			throw new IllegalArgumentException("Optimal and final solution don't have same number of clusters");
		if (optimalSolution.getNumOfPoints() != finalSolution.getNumOfPoints())
			throw new IllegalArgumentException("Optimal and final solution don't have same number of points");
		this.numOfPoints = optimalSolution.getNumOfPoints();
		if (numOfPoints < 3)
			throw new IllegalArgumentException("Solution has only two points");

		points = new ArrayList<>(numOfPoints);
		mapPoints(optimalSolution, finalSolution);
		count();
	}

	@Override
	public double evaluate() {
		return (truePositiveCount + trueNegativeCount)
				/ (truePositiveCount + trueNegativeCount + falsePositiveCount + falseNegativeCount);
	}

	private void count() {

		Iterator<PairIndices> iterator = new PairIndicesIterator(numOfPoints);
		PairIndices pairIndex = iterator.next();

		while (iterator.hasNext()) {
			KnownTruthPointModel currentPoint = points.get(pairIndex.getFirstIndex());
			KnownTruthPointModel nextPoint = points.get(pairIndex.getSecondIndex());

			boolean isPositive = currentPoint.getClusterIndex() == nextPoint.getClusterIndex();
			boolean isTrue = currentPoint.getClusterIndex() == nextPoint.getClassIndex();

			/**
			 * TN -> isPositive = false and isTrue = false 
			 * FN -> isPositive = false and isTrue = true
			 */
			isTrue = isPositive ? isTrue : !isTrue;

			/**
			 * TP -> t, p
			 * TN -> t, !p
			 * FP -> !t, p
			 * FN -> !t, !p
			 */
			if (isPositive) {
				if (isTrue) {
					truePositiveCount++;
				} else {
					falsePositiveCount++;
				}
			} else {
				if (isTrue) {
					trueNegativeCount++;
				} else {
					falseNegativeCount++;
				}
			}
			pairIndex = iterator.next();
		}
	}

	private void mapPoints(Solution optimalSolution, Solution finalSolution) {
		if (!points.isEmpty())
			points = new ArrayList<>(numOfPoints);
		for (int i = 0; i < numOfPoints; i++) {
			KnownTruthPointModel point = new KnownTruthPointModel();
			point.setClusterIndex(finalSolution.getPoints().get(i).getClusterIndex());
			point.setClassIndex(optimalSolution.getPoints().get(i).getClusterIndex());
			points.add(point);
		}
	}

	public int getTruePositiveCount() {
		return truePositiveCount;
	}

	public int getTrueNegativeCount() {
		return trueNegativeCount;
	}

	public int getFalsePositiveCount() {
		return falsePositiveCount;
	}

	public int getFalseNegativeCount() {
		return falseNegativeCount;
	}
}
