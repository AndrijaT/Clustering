package com.andrija.clustering.model.point;

import com.andrija.clustering.model.Point;

public class PointND extends Point {

	private double[] attributes;
	private int dimensionNum;
	private int index;
	private PointND closestPoint;
	private double distanceToClosestPoint;
	private int clusterIndex;
	private boolean isVisited;
	
	public PointND(int dimensionNum, double[] attributes) {
		this.dimensionNum = dimensionNum;
		this.attributes = attributes;
	}

	public PointND(int dimensionNum) {
		this.dimensionNum = dimensionNum;
		this.attributes = new double[dimensionNum];
	}

	public PointND(double[] attributes) {
		this.dimensionNum = attributes.length;
		this.attributes = attributes;
	}
	
	public PointND() {

	}

	public PointND(PointND p) {
		this(p.getDimensionNum(), p.getAttributes());
		this.setIndex(p.getIndex());
		this.setDistanceToClosestPoint(p.getDistanceToClosestPoint());
		this.setClusterIndex(p.getClusterIndex());
		this.setVisited(p.isVisited());
		this.setClosestPoint(p.getClosestPoint());
	}

	@Override
	public double distance(Point p) {
		PointND point = (PointND) p;
		double[] pointAttributes = point.getAttributes();
		
		double sum = 0;
		for(int i = 0; i <= dimensionNum - 1; i++) {
			double thisMinusPoint = this.attributes[i] - pointAttributes[i];
			sum += thisMinusPoint * thisMinusPoint;			
		}
		return Math.sqrt(sum);
	}
	
	@Override
	public Point copyPoint() {
		return new PointND(this);
	}

	public double getAttribute(int i) {
		return attributes[i];
	}
	
	public double[] getAttributes() {
		return attributes;
	}

	public void setAttributes(double[] attributes) {
		this.attributes = attributes;
	}

	public int getDimensionNum() {
		return dimensionNum;
	}

	public void setDimensionNum(int dimensionNum) {
		this.dimensionNum = dimensionNum;
	}

	public PointND getClosestPoint() {
		return closestPoint;
	}

	public void setClosestPoint(Point closestPoint) {
		this.closestPoint = (PointND) closestPoint;
	}

	public double getDistanceToClosestPoint() {
		return distanceToClosestPoint;
	}

	public void setDistanceToClosestPoint(double distanceToClosestPoint) {
		this.distanceToClosestPoint = distanceToClosestPoint;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public int getClusterIndex() {
		return clusterIndex;
	}

	public void setClusterIndex(int clusterIndex) {
		this.clusterIndex = clusterIndex;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		for (double attribute : attributes) {
			stringBuffer.append(String.format( "%.2f", attribute)).append(",");
		}
		stringBuffer.setLength(stringBuffer.length() - 1);
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

	
}
