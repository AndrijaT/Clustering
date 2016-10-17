package com.andrija.clustering.model.point;

import com.andrija.clustering.model.Point;

public class Point3D extends Point {

	private double x;
	private double y;
	private double z;
	private int dimensionNum;
	private int index;
	private Point3D closestPoint;
	private double distanceToClosestPoint;
	private int clusterIndex;
	private boolean isVisited;
	
	public Point3D(double l, double m, double n) {
		this.x = l;
		this.y = m;
		this.z = n;
	}

	public Point3D() {

	}

	public Point3D(Point3D p) {
		this(p.getX(), p.getY(), p.getZ());
		this.setIndex(p.getIndex());
		this.setDistanceToClosestPoint(p.getDistanceToClosestPoint());
		this.setClusterIndex(p.getClusterIndex());
		this.setVisited(p.isVisited());
		this.setClosestPoint(p.getClosestPoint());
	}

	@Override
	public double distance(Point p) {
		Point3D point = (Point3D) p;
		return Math.sqrt((this.x - point.getX()) * (this.x - point.getX())
				+ (this.y - point.getY()) * (this.y - point.getY())
				+ (this.z - point.getZ()) * (this.z - point.getZ()));
	}

	@Override
	public Point copyPoint() {
		return new Point3D(this);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
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

	public Point3D getClosestPoint() {
		return closestPoint;
	}

	@Override
	public void setClosestPoint(Point closestPoint) {
		this.closestPoint = (Point3D) closestPoint;
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
		stringBuffer.append(String.format( "%.2f", z)).append(",");
		stringBuffer.setLength(stringBuffer.length() - 1);
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

}