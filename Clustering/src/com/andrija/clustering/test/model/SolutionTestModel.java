package com.andrija.clustering.test.model;

import java.util.ArrayList;
import java.util.List;

import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.model.point.PointND;
import com.andrija.clustering.solution.ClusterBasedSolution;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.utility.Utility;

public class SolutionTestModel {

	private final Solution solution;
	
	public SolutionTestModel() {
		solution = createSolution();
	}
	
	private Solution createSolution() {
		Utility.setDimension("N");
	      List<Point> pointsCluster1 = new ArrayList<>();
	      List<Point> pointsCluster2 = new ArrayList<>();
	      List<Point> pointsCluster3 = new ArrayList<>();
	      PointND point1Cluster1 = new PointND(4);
	      PointND point2Cluster1 = new PointND(4);
	      PointND point3Cluster1 = new PointND(4);
	      PointND point4Cluster1 = new PointND(4);
	      PointND point1Cluster2 = new PointND(4);
	      PointND point2Cluster2 = new PointND(4);
	      PointND point3Cluster2 = new PointND(4);
	      PointND point1Cluster3 = new PointND(4);
	      PointND point2Cluster3 = new PointND(4);
	      point1Cluster1.setAttributes(new double[]{5.1,3.5,1.4,0.2});
	      point2Cluster1.setAttributes(new double[]{4.9,3.0,1.4,0.2});
	      point3Cluster1.setAttributes(new double[]{4.7,3.2,1.3,0.2});
	      point4Cluster1.setAttributes(new double[]{4.6,3.1,1.5,0.2});
	      
	      point1Cluster2.setAttributes(new double[]{5.5,2.6,4.4,1.2});
	      point2Cluster2.setAttributes(new double[]{6.1,3.0,4.6,1.4});
	      point3Cluster2.setAttributes(new double[]{5.8,2.6,4.0,1.2});
	      
	      point1Cluster3.setAttributes(new double[]{6.4,3.1,5.5,1.8});
	      point2Cluster3.setAttributes(new double[]{6.0,3.0,4.8,1.8});
	      
	      pointsCluster1.add(point1Cluster1);
	      pointsCluster1.add(point2Cluster1);
	      pointsCluster1.add(point3Cluster1);
	      pointsCluster1.add(point4Cluster1);
	      pointsCluster2.add(point1Cluster2);
	      pointsCluster2.add(point2Cluster2);
	      pointsCluster2.add(point3Cluster2);
	      pointsCluster3.add(point1Cluster3);
	      pointsCluster3.add(point2Cluster3);
	      List<Cluster> clusters = new ArrayList<>();
	      clusters.add(new Cluster(pointsCluster1));
	      clusters.add(new Cluster(pointsCluster2));
	      clusters.add(new Cluster(pointsCluster3));
	      int index = 0;
	      for(Cluster c : clusters)
	    	  for(Point p: c.getPoints())
	    		  p.setIndex(index++);

	      Solution s = new ClusterBasedSolution(Measure.createMeasure(), clusters);
	 
	      s.reorganize();
	      s.updateValues();
	      return s;
	}

	public Solution getSolution() {
		return solution;
	}
}
