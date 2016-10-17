package com.andrija.clustering.evaluation.unknowntruth;

import java.util.Iterator;
import java.util.List;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.helper.PairIndices;
import com.andrija.clustering.evaluation.helper.PairIndicesIterator;
import com.andrija.clustering.evaluation.unknowntruth.internalmeasure.PairsOfPointsMeasure;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class McClainRaoIndexEvaluation implements ClusterEvaluation {

	private PairsOfPointsMeasure measure;
	private double betweenSum;
	private List<Point> points;

	public McClainRaoIndexEvaluation(Solution solution) {
		this.measure = new PairsOfPointsMeasure(solution);
		this.points = solution.getPoints();
	}

	@Override
	public double evaluate() {
		if (betweenSum == 0)
			calculateBetweenMeasure();
		return (measure.getBetweenNum() / measure.getWithinNum()) * (measure.getWithinSum() / betweenSum);
	}

	private void calculateBetweenMeasure() {
		betweenSum = 0;
		Iterator<PairIndices> iterator = new PairIndicesIterator(points.size());
		while (iterator.hasNext()) {
			PairIndices indices = iterator.next();
			if (indices.getFirstIndex() != indices.getSecondIndex()) {
				betweenSum += points.get(indices.getFirstIndex()).distance(points.get(indices.getSecondIndex()));
			}
		}
	}
}
