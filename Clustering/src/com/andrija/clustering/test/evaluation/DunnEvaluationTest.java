package com.andrija.clustering.test.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.DunnIndexEvaluation;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;

public class DunnEvaluationTest extends TestCase {
	private Solution solution;
	private double DUNN_INEDX;
	private ClusterEvaluation evaluation;

	@Before
	public void setUp() {
		solution = new SolutionTestModel().getSolution();
		DUNN_INEDX = calculateIndex();
		evaluation = new DunnIndexEvaluation(solution);
	}

	private double calculateIndex() {
		Point c11 = solution.getCluster(0).getPoint(0);
		Point c12 = solution.getCluster(0).getPoint(1);
		Point c13 = solution.getCluster(0).getPoint(2);
		Point c14 = solution.getCluster(0).getPoint(3);
		Point c21 = solution.getCluster(1).getPoint(0);
		Point c22 = solution.getCluster(1).getPoint(1);
		Point c23 = solution.getCluster(1).getPoint(2);
		Point c31 = solution.getCluster(2).getPoint(0);
		Point c32 = solution.getCluster(2).getPoint(1);
		
		List<Double> interClusterDistances = new ArrayList<>();
		interClusterDistances.add(c11.distance(c21));
		interClusterDistances.add(c11.distance(c22));
		interClusterDistances.add(c11.distance(c23));
		interClusterDistances.add(c11.distance(c31));
		interClusterDistances.add(c12.distance(c21));
		interClusterDistances.add(c12.distance(c22));
		interClusterDistances.add(c12.distance(c23));
		interClusterDistances.add(c12.distance(c31));
		interClusterDistances.add(c13.distance(c21));
		interClusterDistances.add(c13.distance(c22));
		interClusterDistances.add(c13.distance(c23));
		interClusterDistances.add(c13.distance(c31));
		interClusterDistances.add(c14.distance(c21));
		interClusterDistances.add(c14.distance(c22));
		interClusterDistances.add(c14.distance(c23));
		interClusterDistances.add(c14.distance(c31));
		interClusterDistances.add(c21.distance(c31));
		interClusterDistances.add(c21.distance(c32));
		interClusterDistances.add(c22.distance(c31));
		interClusterDistances.add(c22.distance(c32));
		interClusterDistances.add(c23.distance(c31));
		interClusterDistances.add(c23.distance(c32));
		
		Collections.sort(interClusterDistances);
		double minInterClusterDistance = interClusterDistances.get(0);
		
		List<Double> intraClusterDistances = new ArrayList<>();
		intraClusterDistances.add(c11.distance(c12));
		intraClusterDistances.add(c11.distance(c13));
		intraClusterDistances.add(c11.distance(c14));
		intraClusterDistances.add(c12.distance(c13));
		intraClusterDistances.add(c12.distance(c14));
		intraClusterDistances.add(c13.distance(c14));
		intraClusterDistances.add(c21.distance(c22));
		intraClusterDistances.add(c21.distance(c23));
		intraClusterDistances.add(c22.distance(c23));
		intraClusterDistances.add(c31.distance(c32));
		Collections.sort(intraClusterDistances);
		double maxIntraClusterDistance = intraClusterDistances.get(intraClusterDistances.size() - 1);
				
		return minInterClusterDistance / maxIntraClusterDistance;
	}

	@Test
	public void testMeasure() {
		assertTrue(Math.abs(evaluation.evaluate() - DUNN_INEDX) < 0.00001);
		System.out.println(DUNN_INEDX);
	}
}