package com.andrija.clustering.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;

public class PointBasedSolution extends Solution {

	private Logger log = Logger.getLogger(this.getClass());
	private Measure measure;
	private List<Point> points;
	private int numOfClusters;

	public PointBasedSolution(Measure measure) {
		this.measure = measure.copyMeasure();
		this.points = new ArrayList<>();
	}

	public PointBasedSolution(Measure measure, List<Point> points) {
		this(measure);
		this.points = this.copyPoints(points);
		this.numOfClusters = this.calculateNumOfClusters(this.points);
	}

	public PointBasedSolution(Measure measure, List<Point> points, int numOfClusters) {
		this(measure);
		this.points = this.copyPoints(points);
		this.numOfClusters = numOfClusters;
	}

	@Override
	public Solution copySolution() {
		return new PointBasedSolution(this.measure, this.points, this.numOfClusters);
	}

	@Override
	public int getNumOfPoints() {
		return points.size();
	}

	@Override
	public int getNumOfClusters() {
		return numOfClusters;
	}

	@Override
	public List<Point> getPoints() {
		return points;
	}

	@Override
	public Point getPoint(int pointIndex) {
		return points.get(pointIndex);
	}

	@Override
	public List<Cluster> getClusters() {
		List<Cluster> clusters = new ArrayList<>(this.numOfClusters);
		for (int i = 0; i < this.numOfClusters; i++) {
			clusters.add(new Cluster());
		}
		for (Point point : points) {
			clusters.get(point.getClusterIndex()).addPoint(point.copyPoint());
		}
		for (Cluster cluster : clusters) {
			if (!cluster.getPoints().isEmpty())
				cluster.calculateCentroid();
		}
		return clusters;
	}

	@Override
	public Cluster getCluster(int clusterIndex) {
		List<Point> clusterPoints = new ArrayList<>();
		for (Point point : points) {
			if (point.getClusterIndex() == clusterIndex)
				clusterPoints.add(point.copyPoint());
		}
		if (points.isEmpty()) {
			System.out.println("prazni points");
		}
		Cluster cluster = new Cluster(clusterPoints);
		if (!clusterPoints.isEmpty())
			cluster.calculateCentroid();
		return cluster;
	}

	@Override
	public void addCluster(Cluster cluster) {
		for (Point clusterPoint : cluster.getPoints()) {
			for (Point point : points) {
				if (clusterPoint.getClusterIndex() == point.getIndex())
					point.setClusterIndex(point.getClusterIndex());
			}
		}
	}

	@Override
	public void addPointToCluster(Point point, int clusterIndex) {
		if (clusterIndex >= numOfClusters)
			throw new IllegalArgumentException("Invalid cluster index");
		if (point.getIndex() >= points.size())
			throw new IllegalArgumentException("Invalid point index");
		points.get(point.getIndex()).setClusterIndex(clusterIndex);
	}

	@Override
	public double getValue() {
		return (measure.getValue() != null) ? measure.getValue() : measure.calculate(this);
	}

	@Override
	public void updateValues() {
		measure.calculate(this);
	}

	@Override
	public void reorganize() {
	}

	@Override
	public Measure getMeasure() {
		return measure;
	}

	@Override
	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	@Override
	public void log() {
		log.info("Value: " + this.getValue());
		StringBuffer clusterWehightString = new StringBuffer("Cluster Weights [");
		List<Cluster> clusters = this.getClusters();
		for (Cluster cluster : clusters) {
			clusterWehightString.append(cluster.size() + ",");
		}
		clusterWehightString.append("]");
		log.info(clusterWehightString);
		log.info("Call counter = " + measure.getCallCounter());
	}

	private List<Point> copyPoints(List<Point> oldPoints) {
		List<Point> points = new ArrayList<>();
		for (Point oldPoint : oldPoints) {
			points.add(oldPoint.copyPoint());
		}
		return points;
	}

	private int calculateNumOfClusters(List<Point> points) {
		List<Integer> clusterIndices = new ArrayList<>();
		for (Point point : points) {
			if (!clusterIndices.contains(point.getClusterIndex()))
				clusterIndices.add(point.getClusterIndex());
		}
		return clusterIndices.size();
	}

	public static List<Point> addPointsToCentroids(List<Point> solutionPoints, List<Point> centroids) {
		List<Integer> centroidIndices = new ArrayList<>();
		for (Point point : centroids) {
			centroidIndices.add(point.getIndex());
		}
		double minDistanceToCentroid = 0;
		int centroidIndex = 0;
		for (Point point : solutionPoints) {
			if (!centroidIndices.contains(new Integer(point.getIndex()))) {
				minDistanceToCentroid = 0;
				centroidIndex = 0;
				for (int i = 0; i < centroids.size(); i++) {
					Point centroid = centroids.get(i);
					if (((minDistanceToCentroid != 0 && point.distance(centroid) < minDistanceToCentroid)
							|| minDistanceToCentroid == 0)) {
						minDistanceToCentroid = point.distance(centroid);
						centroidIndex = i;
					}
				}
				point.setClusterIndex(centroidIndex);
			}
		}
		return solutionPoints;
	}

	@Override
	public void detailLog() {
		for (Point point : points) {
			log.info("pt: " + point.getIndex() + ", cl: " + point.getClusterIndex() + ", coords: " + point.toString());
		}
	}
}
