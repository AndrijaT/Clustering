package com.andrija.clustering.test.evaluation;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.CalinskiHarabaszIndexEvaluation;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;

public class CalinskiHarabaszEvaluationTest extends TestCase {
	private Solution solution;
	private double CALINSKI_HARABASZ_INEDX;
	private ClusterEvaluation evaluation;

	@Before
	public void setUp() {
		solution = new SolutionTestModel().getSolution();
		CALINSKI_HARABASZ_INEDX = calculateIndex();
		evaluation = new CalinskiHarabaszIndexEvaluation(solution);
	}

	private double calculateIndex() {
		double coef = (solution.getNumOfPoints() - solution.getNumOfClusters()) / (solution.getNumOfClusters() - 1);
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
		Cluster cluster = new Cluster(Arrays.asList(c11, c12, c13, c14, c21, c22, c23, c31, c32));
		cluster.calculateCentroid();
		Point C = cluster.getCentroid();
		
		
		double WGSS = 
				Math.pow(C1.distance(c11), 2) + 
				Math.pow(C1.distance(c12), 2) + 
				Math.pow(C1.distance(c13), 2) + 
				Math.pow(C1.distance(c14), 2) + 
				Math.pow(C2.distance(c21), 2) + 
				Math.pow(C2.distance(c22), 2) + 
				Math.pow(C2.distance(c23), 2) + 
				Math.pow(C3.distance(c31), 2) + 
				Math.pow(C3.distance(c32), 2);
		
		double BGSS =
				4 * Math.pow(C.distance(C1), 2) +
				3 * Math.pow(C.distance(C2), 2) +
				2 * Math.pow(C.distance(C3), 2);		
		
		return coef * (BGSS / WGSS);

	}

	@Test
	public void testMeasure() {
		assertTrue(Math.abs(evaluation.evaluate() - CALINSKI_HARABASZ_INEDX) < 0.00001);
	}
}