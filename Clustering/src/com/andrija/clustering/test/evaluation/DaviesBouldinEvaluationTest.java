package com.andrija.clustering.test.evaluation;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.DaviesBouldinIndexEvaluation;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;

public class DaviesBouldinEvaluationTest extends TestCase {
	private Solution solution;
	private double DAVIES_BOULDIN_INEDX;
	private ClusterEvaluation evaluation;

	@Before
	public void setUp() {
		solution = new SolutionTestModel().getSolution();
		DAVIES_BOULDIN_INEDX = calculateIndex();
		evaluation = new DaviesBouldinIndexEvaluation(solution);
	}

	private double calculateIndex() {
		int K = solution.getNumOfClusters();
		Point C1 = solution.getCluster(0).getCentroid();
		Point C2 = solution.getCluster(1).getCentroid();
		Point C3 = solution.getCluster(2).getCentroid();
		Point c11 = solution.getCluster(0).getPoint(0);
		Point c12 = solution.getCluster(0).getPoint(1);
		Point c13 = solution.getCluster(0).getPoint(2);
		Point c14 = solution.getCluster(0).getPoint(3);
		Point c21 = solution.getCluster(1).getPoint(0);
		Point c22 = solution.getCluster(1).getPoint(1);
		Point c23 = solution.getCluster(1).getPoint(2);
		Point c31 = solution.getCluster(2).getPoint(0);
		Point c32 = solution.getCluster(2).getPoint(1);
		
		double d1 = (
				C1.distance(c11) + 
				C1.distance(c12) + 
				C1.distance(c13) + 
				C1.distance(c14)) / 4;
				
		double d2 = (		
				C2.distance(c21) + 
				C2.distance(c22) + 
				C2.distance(c23)) / 3;
		
		double d3 = (
				C3.distance(c31) + 
				C3.distance(c32)) / 2;
		
		double D12 = C1.distance(C2);
		double D13 = C1.distance(C3);
		double D23 = C2.distance(C3);
		
		double M1 = Math.max((d1 + d2) / D12, (d1 + d3) / D13);
		double M2 = Math.max((d2 + d1) / D12, (d2 + d3) / D23);
		double M3 = Math.max((d3 + d1) / D13, (d3 + d2) / D23);
		
		System.out.println("M1: " + M1 + "M2: " + M2 + "M3: " + M3);
		return (M1 + M2 + M3) / K;
	}

	@Test
	public void testMeasure() {
		assertTrue(Math.abs(evaluation.evaluate() - DAVIES_BOULDIN_INEDX) < 0.00001);
	}
}