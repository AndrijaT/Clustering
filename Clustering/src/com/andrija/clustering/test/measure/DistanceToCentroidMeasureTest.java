package com.andrija.clustering.test.measure;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.measure.impl.DistanceToCentroidSumMeasure;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;


public class DistanceToCentroidMeasureTest extends TestCase{
	private Solution solution;
	public static final double DISTANCE_TO_CENTROID_SUM = 3.06552;
	
	   @Before 
	   public void setUp() {
		solution = new SolutionTestModel().getSolution();
	   }
	
	@Test
	public void testMeasure() {
		DistanceToCentroidSumMeasure distanceToCentroidSumMeasure = new DistanceToCentroidSumMeasure();
		
		double sum = distanceToCentroidSumMeasure.calculate(solution);
		assertTrue(Math.abs(sum - DISTANCE_TO_CENTROID_SUM) < 0.00001 );
	}
}