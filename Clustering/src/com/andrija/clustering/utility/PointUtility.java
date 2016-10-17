package com.andrija.clustering.utility;

import java.util.List;

import com.andrija.clustering.model.Point;
import com.andrija.clustering.model.Problem;

public abstract class PointUtility {
	
	public abstract Problem generateProblem();
	
	public abstract Point calculateCentroid(List<Point> points);
	
	public abstract double calculateSum(List<Point> points, Point c);
}
