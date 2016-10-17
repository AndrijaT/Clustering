package com.andrija.clustering.test.measure;

import org.junit.Before;
import org.junit.Test;

import com.andrija.clustering.measure.impl.WithinClusterPairsOfPointsDistancesMeasure;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.test.model.SolutionTestModel;

import junit.framework.TestCase;


public class WithinClusterParsOfPointsDistancesMeasureTest extends TestCase{
	private Solution solution;
	public static final double AVARGE_PAIRS_OF_POINTS_DISTANCES_SUM = 1.934861981;
	
	   @Before 
	   public void setUp() {
		   solution = new SolutionTestModel().getSolution();
	   }
	
	@Test
	public void testMeasure() {
		WithinClusterPairsOfPointsDistancesMeasure measure = new WithinClusterPairsOfPointsDistancesMeasure();
		
		double sum = measure.calculate(solution);
		assertTrue(Math.abs(sum - AVARGE_PAIRS_OF_POINTS_DISTANCES_SUM) < 0.00001 );
	}
}