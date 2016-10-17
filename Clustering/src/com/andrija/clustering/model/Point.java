package com.andrija.clustering.model;

public abstract class Point {

	public abstract Point copyPoint();

	public abstract double distance(Point point);

	public abstract Point getClosestPoint();

	public abstract void setClosestPoint(Point closestPoint);

	public abstract double getDistanceToClosestPoint();

	public abstract void setDistanceToClosestPoint(double distanceToClosestPoint);

	public abstract int getIndex();

	public abstract void setIndex(int index);

	public abstract boolean isVisited();

	public abstract void setVisited(boolean isVisited);

	public abstract int getClusterIndex();

	public abstract void setClusterIndex(int clusterIndex);
}
