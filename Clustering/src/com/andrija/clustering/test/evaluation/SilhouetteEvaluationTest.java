package com.andrija.clustering.test.evaluation;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.AvargeSilhouettesEvaluation;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;

public class SilhouetteEvaluationTest extends TestCase {
	private Solution solution;
	private double SILHOUETTE_INEDX;
	private ClusterEvaluation evaluation;

	@Before
	public void setUp() {
		solution = new SolutionTestModel().getSolution();
		SILHOUETTE_INEDX = calculateIndex();
		evaluation = new AvargeSilhouettesEvaluation(solution);
	}

	private double calculateIndex() {
		Point c11 = solution.getCluster(0).getPoint(0); // 0
		Point c12 = solution.getCluster(0).getPoint(1); // 1
		Point c13 = solution.getCluster(0).getPoint(2); // 2
		Point c14 = solution.getCluster(0).getPoint(3); // 3
		Point c21 = solution.getCluster(1).getPoint(0); // 4
		Point c22 = solution.getCluster(1).getPoint(1); // 5
		Point c23 = solution.getCluster(1).getPoint(2); // 6
		Point c31 = solution.getCluster(2).getPoint(0); // 7
		Point c32 = solution.getCluster(2).getPoint(1); // 8
		
		
		
		double a_c11 = (
				c11.distance(c12) +
				c11.distance(c13) +
				c11.distance(c14)) / 3;
		
		double a_c12 = (
				c12.distance(c11) +
				c12.distance(c13) +
				c12.distance(c14)) / 3;
		
		double a_c13 = (
				c13.distance(c12) +
				c13.distance(c11) +
				c13.distance(c14)) / 3;
		
		double a_c14 = (
				c14.distance(c12) +
				c14.distance(c13) +
				c14.distance(c11)) / 3;
		
		double a_c21 = (
				c21.distance(c22) +
				c21.distance(c23)) / 2;
		
		double a_c22 = (
				c22.distance(c21) +
				c22.distance(c23)) / 2;
		
		double a_c23 = (
				c23.distance(c21) +
				c23.distance(c22)) / 2;
		
		double a_c31 = (
				c31.distance(c32));
		
		double a_c32 = (
				c32.distance(c31));
		
		// 1-2
		double b_c11_2 = (
				c11.distance(c21) +
				c11.distance(c22) +
				c11.distance(c23)) / 3; 
		
		double b_c12_2 = (
				c12.distance(c21) +
				c12.distance(c22) +
				c12.distance(c23)) / 3; 
		
		double b_c13_2 = (
				c13.distance(c21) +
				c13.distance(c22) +
				c13.distance(c23)) / 3; 
		
		double b_c14_2 = (
				c14.distance(c21) +
				c14.distance(c22) +
				c14.distance(c23)) / 3; 
		
		// 1-3
		double b_c11_3 = (
				c11.distance(c31) +
				c11.distance(c32)) / 2; 
		
		double b_c12_3 = (
				c12.distance(c31) +
				c12.distance(c32)) / 2; 
		
		double b_c13_3 = (
				c13.distance(c31) +
				c13.distance(c32)) / 2; 
		
		double b_c14_3 = (
				c14.distance(c31) +
				c14.distance(c32)) / 2; 
		
		// 2-1
		double b_c21_1 = (
				c21.distance(c11) +
				c21.distance(c12) +
				c21.distance(c13) +
				c21.distance(c14)) / 4; 
		
		double b_c22_1 = (
				c22.distance(c11) +
				c22.distance(c12) +
				c22.distance(c13) +
				c22.distance(c14)) / 4; 
		
		double b_c23_1 = (
				c23.distance(c11) +
				c23.distance(c12) +
				c23.distance(c13) +
				c23.distance(c14)) / 4; 
		
		// 2-3
		double b_c21_3 = (
				c21.distance(c31) +
				c21.distance(c32)) / 2; 
		
		double b_c22_3 = (
				c22.distance(c31) +
				c22.distance(c32)) / 2; 
		
		double b_c23_3 = (
				c23.distance(c31) +
				c23.distance(c32)) / 2; 
		
		// 3-1
		double b_c31_1 = (
				c31.distance(c11) +
				c31.distance(c12) +
				c31.distance(c13) +
				c31.distance(c14)) / 4; 
		
		double b_c32_1 = (
				c32.distance(c11) +
				c32.distance(c12) +
				c32.distance(c13) +
				c32.distance(c14)) / 4;
			
		// 3-2
		double b_c31_2 = (
				c31.distance(c21) +
				c31.distance(c22) +
				c31.distance(c23)) / 3; 

		double b_c32_2 = (
				c32.distance(c21) +
				c32.distance(c22) +
				c32.distance(c23)) / 3; 
			
				
		
		double b_c11 = Math.min(b_c11_2, b_c11_3); // 0
		double b_c12 = Math.min(b_c12_2, b_c12_3); // 1
		double b_c13 = Math.min(b_c13_2, b_c13_3); // 2
		double b_c14 = Math.min(b_c14_2, b_c14_3); // 3
		double b_c21 = Math.min(b_c21_1, b_c21_3); // 4
		double b_c22 = Math.min(b_c22_1, b_c22_3); // 5
		double b_c23 = Math.min(b_c23_1, b_c23_3); // 6
		double b_c31 = Math.min(b_c31_1, b_c31_2); // 7
		double b_c32 = Math.min(b_c32_1, b_c32_2); // 8

		double silhouette = (
				(b_c11 - a_c11) / Math.max(b_c11, a_c11) +
				(b_c12 - a_c12) / Math.max(b_c12, a_c12) +
				(b_c13 - a_c13) / Math.max(b_c13, a_c13) +
				(b_c14 - a_c14) / Math.max(b_c14, a_c14)) / 4 + 
				(
				(b_c21 - a_c21) / Math.max(b_c21, a_c21) +
				(b_c22 - a_c22) / Math.max(b_c22, a_c22) +
				(b_c23 - a_c23) / Math.max(b_c23, a_c23)) / 3 + 
				(
				(b_c31 - a_c31) / Math.max(b_c31, a_c31) +
				(b_c32 - a_c32) / Math.max(b_c32, a_c32)) / 2;
		
		
		return silhouette / 3;

	}

	@Test
	public void testMeasure() {
		assertTrue(Math.abs(evaluation.evaluate() - SILHOUETTE_INEDX) < 0.00001);
	}
}