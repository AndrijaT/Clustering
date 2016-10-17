package com.andrija.clustering.evaluation.unknowntruth.internalmeasure;

import java.util.ArrayList;
import java.util.List;

import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

public class ScatterMeasure {

	private int numOfClusters;
	private List<Cluster> clusters;
	
	/**
	 * Array of within-cluster dispersion. Each k-th element is sum of distances
	 * of points in cluster Ck to Ck's centroid. Total dispersion is sum of all
	 * array's elements.
	 */
	private double[] WGSS;

	/**
	 * Total dispersion is sum of all WGSS array's elements.
	 */
	private double totalWGSS;

	/**
	 * Centroid of entire dataset.
	 */
	private Point barycenter;

	/**
	 * Sum of all distances from k-th cluster's centroid to dataset barycenter.
	 */
	private double BGSS;

	public ScatterMeasure(Solution solution) {
		this.numOfClusters = solution.getNumOfClusters();
		this.WGSS = new double[this.numOfClusters];
		this.clusters = solution.getClusters();
	}

	public double getWGSS() {
		if (totalWGSS == 0) {
			for (int i = 0; i < numOfClusters; i++) {
				if (WGSS[i] == 0) {
					calculateWGSS(i);
				}
				totalWGSS += WGSS[i];
			}
		}
		return totalWGSS;
	}

	public double getWGSS(int clusterIndex) {
		if (clusterIndex >= numOfClusters)
			throw new IllegalArgumentException("Irregular cluster index.");
		if (WGSS[clusterIndex] == 0)
			calculateWGSS(clusterIndex);
		return WGSS[clusterIndex];
	}

	public double getBGSS() {
		if (BGSS == 0)
			calculateBGSS();
		return BGSS;
	}

	private void calculateWGSS(int clusterIndex) {
		if (clusterIndex >= numOfClusters)
			throw new IllegalArgumentException("Irregular cluster index.");
		double targetWGSS = 0;
		Cluster cluster = clusters.get(clusterIndex);
		Point centroid = cluster.getCentroid();
		for (Point point : cluster.getPoints()) {
			targetWGSS += Math.pow(point.distance(centroid), 2);
		}
		WGSS[clusterIndex] = targetWGSS;
	}

	private void calculateBarycenter() {
		List<Point> allPoints = new ArrayList<>();
		for (Cluster cluster : clusters) {
			allPoints.addAll(cluster.getPoints());
		}
		Cluster dataset = new Cluster(allPoints);
		dataset.calculateCentroid();
		barycenter = dataset.getCentroid();
	}

	private void calculateBGSS() {
		BGSS = 0;
		if (barycenter == null)
			calculateBarycenter();
		for (Cluster cluster : clusters) {
			BGSS += cluster.size() * Math.pow(barycenter.distance(cluster.getCentroid()), 2);
		}
	}
}
