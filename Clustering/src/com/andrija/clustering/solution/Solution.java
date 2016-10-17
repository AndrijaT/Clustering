package com.andrija.clustering.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.andrija.clustering.measure.Measure;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.model.Problem;
import com.andrija.clustering.properties.ConfigProperties;

public abstract class Solution {
	
	public static Solution generateInitialSolution(Problem problem) {
		ConfigProperties config = ConfigProperties.getConfigProperties();
		int numOfClusters = config.getNumOfClusters();
		List<Point> solutionPoints = new ArrayList<Point>(problem.getPoints().size());
		for (Point point : problem.getPoints()) {
			solutionPoints.add(point.copyPoint());
		}
		List<Point> centroids = chooseRandomCentroids(solutionPoints, numOfClusters);
		Solution initialSolution;

		switch (config.getVnsNeighborhoodType()) {
//		case CLOSEST_POINT:
//			solutionPoints = PointBasedSolution.addPointsToCentroids(solutionPoints, centroids);
//			initialSolution = new PointBasedSolution(Measure.createMeasure(), solutionPoints);
//			break;
//		case REORGANIZED_CLUSTER:
//			initialSolution = new ClusterBasedSolution(Measure.createMeasure(),
//					ClusterBasedSolution.addPointsToCentroids(solutionPoints, centroids));
//			break;
//		case REORGANIZED_CLUSTERS_ACCORDING_OBJECTIVE_FUNCTION:
//			initialSolution = new ClusterBasedSolution(Measure.createMeasure(),
//					ClusterBasedSolution.addPointsToCentroids(solutionPoints, centroids));
//			break;
		case IMAGINARY_CENTROID:
			initialSolution = new ClusterBasedSolution(Measure.createMeasure(),
					ClusterBasedSolution.addPointsToCentroids(solutionPoints, centroids));
			break;
		default:
			return null;
		}
		initialSolution.reorganize();
		return initialSolution;
	}

	public static Solution generateOptimalSolution(Problem problem) {
		ConfigProperties config = ConfigProperties.getConfigProperties();
		int[] clusterSizes = config.getOptimalClusterSizes();
		if (clusterSizes == null)
			return null;
		int numOfClusters = clusterSizes.length;
		List<Cluster> clusters = new ArrayList<Cluster>();
		for (int i = 0; i < numOfClusters; i++) {
			clusters.add(new Cluster());
		}
		int clusterIndex = 0;
		int clusterWeightCounter = 0;
		for (Point point : problem.getPoints()) {
			if (clusterWeightCounter < clusterSizes[clusterIndex]) {
				clusters.get(clusterIndex).addPoint(point.copyPoint());
			} else {
				clusterWeightCounter = 0;
				clusterIndex++;
				clusters.get(clusterIndex).addPoint(point.copyPoint());
			}
			clusterWeightCounter++;
		}
		Solution solution = new ClusterBasedSolution(Measure.createMeasure(), clusters);
		solution.reorganize();
		solution.updateValues();

		return solution;
	}

	private static List<Point> chooseRandomCentroids(List<Point> solutionPoints, int numOfClusters) {
		Random randomGenerator = new Random();
		int numberOfPoints = solutionPoints.size();
		int clusterCounter = 0;
		List<Integer> alreadyChosenPoints = new ArrayList<Integer>();
		int randomPointIndex = randomGenerator.nextInt(numberOfPoints - 1);
		List<Point> centroids = new ArrayList<>(numOfClusters);

		while (clusterCounter < numOfClusters) {
			while (alreadyChosenPoints.contains(randomPointIndex)) {
				randomPointIndex = randomGenerator.nextInt(numberOfPoints - 1);
			}
			alreadyChosenPoints.add(randomPointIndex);
			solutionPoints.get(randomPointIndex).setClusterIndex(clusterCounter);
			centroids.add(solutionPoints.get(randomPointIndex));
			clusterCounter++;
		}
		return centroids;
	}

	public abstract Solution copySolution();

	public abstract int getNumOfPoints();

	public abstract int getNumOfClusters();

	public abstract List<Point> getPoints();

	public abstract Point getPoint(int pointIndex);

	public abstract List<Cluster> getClusters();

	public abstract Cluster getCluster(int i);

	public abstract void addCluster(Cluster cluster);

	public abstract void addPointToCluster(Point point, int clusterIndex);

	public abstract double getValue();

	public abstract void updateValues();

	public abstract void reorganize();

	public abstract Measure getMeasure();

	public abstract void setMeasure(Measure measure);

	public abstract void log();

	public abstract void detailLog();
}
