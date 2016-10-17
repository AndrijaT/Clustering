package com.andrija.clustering.test.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.CSMeasureEvaluation;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;

public class CSMeasureEvaluationTest extends TestCase {
	private Solution solution;
	private double CS_MEASURE_INEDX;
	private ClusterEvaluation evaluation;

	@Before
	public void setUp() {
		solution = new SolutionTestModel().getSolution();
		CS_MEASURE_INEDX = calculateIndex();
		evaluation = new CSMeasureEvaluation(solution);
	}

	private double calculateIndex() {
		Point C1 = solution.getCluster(0).getCentroid();
		Point C2 = solution.getCluster(1).getCentroid();
		Point C3 = solution.getCluster(2).getCentroid();
		Point c11 = solution.getCluster(0).getPoint(0); // 0
		Point c12 = solution.getCluster(0).getPoint(1); // 1
		Point c13 = solution.getCluster(0).getPoint(2); // 2
		Point c14 = solution.getCluster(0).getPoint(3); // 3
		Point c21 = solution.getCluster(1).getPoint(0); // 4
		Point c22 = solution.getCluster(1).getPoint(1); // 5
		Point c23 = solution.getCluster(1).getPoint(2); // 6
		Point c31 = solution.getCluster(2).getPoint(0); // 7
		Point c32 = solution.getCluster(2).getPoint(1); // 8
		
		List<Double> d_c11 = new ArrayList<>();
		d_c11.add(c11.distance(c12));
		d_c11.add(c11.distance(c13));
		d_c11.add(c11.distance(c14));
		double max_c11 = Collections.max(d_c11);

		List<Double> d_c12 = new ArrayList<>();
		d_c12.add(c12.distance(c11));
		d_c12.add(c12.distance(c13));
		d_c12.add(c12.distance(c14));
		double max_c12 = Collections.max(d_c12);

		List<Double> d_c13 = new ArrayList<>();
		d_c13.add(c13.distance(c12));
		d_c13.add(c13.distance(c11));
		d_c13.add(c13.distance(c14));
		double max_c13 = Collections.max(d_c13);

		List<Double> d_c14 = new ArrayList<>();
		d_c14.add(c14.distance(c12));
		d_c14.add(c14.distance(c13));
		d_c14.add(c14.distance(c11));
		double max_c14 = Collections.max(d_c14);

		List<Double> d_c21 = new ArrayList<>();
		d_c21.add(c21.distance(c22));
		d_c21.add(c21.distance(c23));
		double max_c21 = Collections.max(d_c21);

		List<Double> d_c22 = new ArrayList<>();
		d_c22.add(c22.distance(c21));
		d_c22.add(c22.distance(c23));
		double max_c22 = Collections.max(d_c22);

		List<Double> d_c23 = new ArrayList<>();
		d_c23.add(c23.distance(c21));
		d_c23.add(c23.distance(c22));
		double max_c23 = Collections.max(d_c23);

		List<Double> d_c31 = new ArrayList<>();
		d_c31.add(c31.distance(c32));
		double max_c31 = Collections.max(d_c31);

		List<Double> d_c32 = new ArrayList<>();
		d_c32.add(c32.distance(c31));
		double max_c32 = Collections.max(d_c32);

		double min_C1 = Math.min(C1.distance(C2), C1.distance(C3));
		double min_C2 = Math.min(C2.distance(C1), C2.distance(C3));
		double min_C3 = Math.min(C3.distance(C2), C3.distance(C1));
		
		System.out.println("" + min_C1 + min_C2 + min_C3);
		double CS = (
				(max_c11 + max_c12 + max_c13 + max_c14) / 4 +
				(max_c21 + max_c22 + max_c23) / 3 + 
				(max_c31 + max_c32) / 2) / 
				(min_C1 + min_C2 + min_C3);		
		
		return CS;

	}

	@Test
	public void testMeasure() {
		assertTrue(Math.abs(evaluation.evaluate() - CS_MEASURE_INEDX) < 0.00001);
	}
}