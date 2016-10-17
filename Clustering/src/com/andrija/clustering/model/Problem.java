package com.andrija.clustering.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Problem {

	private final List<Point> points;

	public Problem(List<Point> points) {
		List<Point> pointList = new ArrayList<Point>(points.size());
		for (Point point : points) {
			pointList.add(point.copyPoint());
		}
		this.points = Collections.unmodifiableList(pointList);
	}

	public List<Point> getPoints() {
		List<Point> pointsCopy = new ArrayList<>();
		for (Point point : points) {
			pointsCopy.add(point.copyPoint());
		}
		return pointsCopy;
	}
}
