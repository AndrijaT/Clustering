package com.andrija.clustering.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;

public class ClusterBasedSolution extends Solution {

	private Logger log = Logger.getLogger(this.getClass());
	private Measure measure;
	private List<Cluster> clusters;
	private int numOfPoints;

	public ClusterBasedSolution(Measure measure) {
		if (measure == null)
			throw new IllegalArgumentException("Measure is null.");
		this.measure = measure.copyMeasure();
		this.clusters = new ArrayList<>();
	}

	public ClusterBasedSolution(Measure measure, List<Cluster> clusters) {
		this(measure);
		if (clusters == null || clusters.isEmpty())
			throw new IllegalArgumentException("Cluster list is null or empty.");
		this.clusters = this.copyClusters(clusters);
		this.numOfPoints = calculateNumOfPoints(clusters);
	}

	@Override
	public Solution copySolution() {
		return new ClusterBasedSolution(this.measure, this.clusters);
	}

	@Override
	public int getNumOfPoints() {
		return numOfPoints;
	}

	@Override
	public int getNumOfClusters() {
		return clusters.size();
	}

	@Override
	public List<Point> getPoints() {
		List<Point> points = new ArrayList<>();
		int clusterIndex = 0;
		for (Cluster cluster : clusters) {
			for (Point point : cluster.getPoints()) {
				Point newPoint = point.copyPoint();
				newPoint.setClusterIndex(clusterIndex);
				points.add(newPoint);
			}
			clusterIndex++;
		}
		Collections.sort(points, new Comparator<Point>() {

			@Override
			public int compare(Point left, Point right) {
				if (left.getIndex() < right.getIndex()) {
					return -1;
				} else if (left.getIndex() == right.getIndex()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return points;
	}

	@Override
	public Point getPoint(int pointIndex) {
		for (Cluster cluster : clusters) {
			for (Point point : cluster.getPoints()) {
				if (point.getIndex() == pointIndex)
					return point;
			}
		}
		throw new IllegalArgumentException("Point not found, Irregular point index.");
	}

	@Override
	public List<Cluster> getClusters() {
		return clusters;
	}

	@Override
	public Cluster getCluster(int clusterIndex) {
		return clusters.get(clusterIndex);
	}

	@Override
	public void addCluster(Cluster cluster) {
		clusters.add(cluster);
	}

	@Override
	public void addPointToCluster(Point point, int clusterIndex) {
		clusters.get(clusterIndex).addPoint(point);
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
		for (Cluster c : this.getClusters()) {
			c.calculateCentroid();
		}
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
		for (Cluster cluster : clusters) {
			clusterWehightString.append(cluster.size() + ",");
		}
		clusterWehightString.append("]");
		log.info(clusterWehightString);
		log.info("Call counter = " + measure.getCallCounter());
	}

	@Override
	public void detailLog() {
		for (Point point : this.getPoints()) {
			log.info("pt: " + point.getIndex() + ", cl: " + point.getClusterIndex() + ", coords: " + point.toString());
		}
	}

	private List<Cluster> copyClusters(List<Cluster> oldClusters) {
		List<Cluster> clusters = new ArrayList<>();
		for (Cluster oldCluster : oldClusters) {
			if (oldCluster == null)
				throw new IllegalArgumentException("Cluster is null");
			Cluster cluster = new Cluster();
			if (oldCluster.getCentroid() == null) {
				cluster.setCentroid(null);
			} else {
				cluster.setCentroid(oldCluster.getCentroid().copyPoint());
			}
			List<Point> points = new ArrayList<>();
			if (!(oldCluster.getPoints() == null || oldCluster.getPoints().isEmpty())) {
				for (Point point : oldCluster.getPoints()) {
					if (point == null)
						throw new IllegalArgumentException("Point is empty");
					points.add(point.copyPoint());
				}
			}
			cluster.setPoints(points);
			clusters.add(cluster);
		}
		return clusters;
	}

	private int calculateNumOfPoints(List<Cluster> clusters) {
		int numOfPoints = 0;
		for (Cluster cluster : clusters) {
			numOfPoints += cluster.size();
		}
		return numOfPoints;
	}

	static List<Cluster> addPointsToCentroids(List<Point> solutionPoints, List<Point> centroids) {
		List<Point> points = new ArrayList<>();
		for (Point point : solutionPoints) {
			points.add(point.copyPoint());
		}
		List<Cluster> clusters = new ArrayList<>(centroids.size());
		List<Integer> centroidIndices = new ArrayList<>();
		for (Point centroid : centroids) {
			Cluster cluster = new Cluster();
			cluster.setCentroid(centroid.copyPoint());
			cluster.addPoint(centroid);
			clusters.add(cluster);
			centroidIndices.add(centroid.getIndex());
		}

		for (Point point : points) {
			if (!centroidIndices.contains(new Integer(point.getIndex()))) {
				Cluster closestCluster = clusters.get(0);
				double closestCentroidDistance = point.distance(closestCluster.getCentroid());
				for (Cluster cluster : clusters) {
					if (point.distance(cluster.getCentroid()) < closestCentroidDistance) {
						closestCentroidDistance = point.distance(cluster.getCentroid());
						closestCluster = cluster;
					}
				}
				point.setClusterIndex(closestCluster.getCentroid().getClusterIndex());
				closestCluster.addPoint(point.copyPoint());
			}
		}
		return clusters;
	}

}
