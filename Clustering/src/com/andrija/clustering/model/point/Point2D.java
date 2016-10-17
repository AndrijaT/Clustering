package com.andrija.clustering.model.point;

import com.andrija.clustering.model.Point;

public class Point2D extends Point {

	private double x;
	private double y;
	private int dimensionNum;
	private int index;
	private Point2D closestPoint;
	private double distanceToClosestPoint;
	private int clusterIndex;
	private boolean isVisited;
	
	public Point2D(double l, double m) {
		this.x = l;
		this.y = m;
	}

	public Point2D() {
	}

	public Point2D(Point2D p) {
		this(p.getX(), p.getY());
		this.setIndex(p.getIndex());
		this.setDistanceToClosestPoint(p.getDistanceToClosestPoint());
		this.setClusterIndex(p.getClusterIndex());
		this.setVisited(p.isVisited());
		this.setClosestPoint(p.getClosestPoint());
	}

	@Override
	public double distance(Point p) {
		Point2D point = (Point2D) p;
		return Math.sqrt((this.x - point.getX()) * (this.x - point.getX())
				+ (this.y - point.getY()) * (this.y - point.getY()));
	}
	
	@Override
	public Point copyPoint() {
		return new Point2D(this);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public int getDimensionNum() {
		return dimensionNum;
	}

	public void setDimensionNum(int dimensionNum) {
		this.dimensionNum = dimensionNum;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	public Point2D getClosestPoint() {
		return closestPoint;
	}

	@Override
	public void setClosestPoint(Point closestPoint) {
		this.closestPoint = (Point2D) closestPoint;
	}

	@Override
	public double getDistanceToClosestPoint() {
		return distanceToClosestPoint;
	}

	@Override
	public void setDistanceToClosestPoint(double distanceToClosestPoint) {
		this.distanceToClosestPoint = distanceToClosestPoint;
	}

	@Override
	public int getClusterIndex() {
		return clusterIndex;
	}

	@Override
	public void setClusterIndex(int clusterIndex) {
		this.clusterIndex = clusterIndex;
	}

	@Override
	public boolean isVisited() {
		return isVisited;
	}

	@Override
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append(String.format( "%.2f", x)).append(",");
		stringBuffer.append(String.format( "%.2f", y)).append(",");
		stringBuffer.setLength(stringBuffer.length() - 1);
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

}