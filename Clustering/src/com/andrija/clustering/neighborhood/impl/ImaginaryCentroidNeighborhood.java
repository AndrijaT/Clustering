package com.andrija.clustering.neighborhood.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.andrija.clustering.interfaces.Shakeable;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.neighborhood.Neighborhood;
import com.andrija.clustering.solution.Solution;

public class ImaginaryCentroidNeighborhood extends Neighborhood implements Shakeable {

	private int numberOfRandomClustersToBeReorganized;

	/**
	 * @param initialSolution
	 *            - initial solution
	 * @param neighborhoodStructureNo
	 *            - number of random clusters for which points will be checked
	 *            if they should be reassigned to another cluster
	 * @return - solution with reorganized points in neighborhoodStructureNo+
	 *         clusters
	 */
	@Override
	public Solution shake(Solution initialSolution, int neighborhoodStructureNo) {
		Solution newRandomSolution = initialSolution.copySolution();
		newRandomSolution.reorganize();
		int numberOfClusters = newRandomSolution.getNumOfClusters();

		/**
		 * Maximal neighborhood number for this neighborhood structure can't be
		 * greater than number of clusters minus 1.
		 */
		if (neighborhoodStructureNo <= numberOfClusters - 1) {
			int numberOfRandomClusters = 1;
			Random randomGenerator = new Random();
			List<Integer> alreadyChosenClusterIndices = new ArrayList<>();
			int i;

			while (numberOfRandomClusters <= neighborhoodStructureNo) {
				i = randomGenerator.nextInt(numberOfClusters);
				while (alreadyChosenClusterIndices.contains(new Integer(i))) {
					i = randomGenerator.nextInt(numberOfClusters);
				}
				alreadyChosenClusterIndices.add(i);
				numberOfRandomClusters++;
			}
			newRandomSolution = reorganize(newRandomSolution, alreadyChosenClusterIndices);
		}

		return newRandomSolution;
	}

	@Override
	public Solution findBestNeighbor(Solution initialSolution, int neighborhoodStructureNo) {
		Solution optimalSolution = initialSolution.copySolution();
		optimalSolution.reorganize();
		int numberOfClusters = initialSolution.getClusters().size();

		/**
		 * Maximal neighborhood number for this neighborhood structure can't be
		 * greater than number of clusters minus 1.
		 */
		if (neighborhoodStructureNo <= numberOfClusters - 1) {
			List<int[]> neighborsStructures = findAllSubsets(numberOfClusters, neighborhoodStructureNo);
			for (int[] neighbor : neighborsStructures) {
				List<Integer> clusterIndices = new ArrayList<>();
				for (int i : neighbor) {
					clusterIndices.add(new Integer(i));
				}
				Solution nextSolution = reorganize(initialSolution.copySolution(), clusterIndices);

				if (nextSolution.getValue() < optimalSolution.getValue()) {
					optimalSolution = nextSolution.copySolution();
					optimalSolution.updateValues();
				} else {
					/**
					 * Bad best neighbor;
					 */
				}
			}
		}
		return optimalSolution;
	}

	@Override
	public Solution findFirstImprovement(Solution initialSolution, int neighborhoodStructureNo) {
		Solution betterSolution = initialSolution.copySolution();
		betterSolution.reorganize();
		int numberOfClusters = initialSolution.getClusters().size();

		if (neighborhoodStructureNo <= numberOfClusters - 1) {
			List<int[]> neighborsStructures = findAllSubsets(numberOfClusters, neighborhoodStructureNo);
			for (int[] neighbor : neighborsStructures) {
				List<Integer> clusterIndices = new ArrayList<>();
				for (int i : neighbor) {
					clusterIndices.add(new Integer(i));
				}
				Solution nextSolution = reorganize(initialSolution.copySolution(), clusterIndices);

				if (nextSolution.getValue() < betterSolution.getValue()) {
					betterSolution = nextSolution.copySolution();
					betterSolution.updateValues();
					break;
				}
			}
		}
		return betterSolution;
	}

	@Override
	protected Solution reorganizeClusterIfNeeded(int targetClusterIndex, Solution nextSolution) {
		Solution targetSolution = nextSolution.copySolution();
		Cluster targetCluster = targetSolution.getCluster(targetClusterIndex);
		Set<Integer> reorganizedClustersIndices = new HashSet<Integer>();

		for (Iterator<Point> points = targetCluster.getPoints().iterator(); points.hasNext();) {
			Point point = points.next();
			double originalDistanceToClosestCentroid = point.distance(targetCluster.getCentroid());
			double distanceToClosestCentroid = originalDistanceToClosestCentroid;
			Cluster closestCluster = targetCluster;

			int counter = 0;
			int closestClusterIndex = -1;
			for (Cluster cluster : targetSolution.getClusters()) {
				if (point.distance(cluster.getCentroid()) < distanceToClosestCentroid) {
					distanceToClosestCentroid = point.distance(cluster.getCentroid());
					closestCluster = cluster;
					closestClusterIndex = counter;
				}
				counter++;
			}
			if (distanceToClosestCentroid < originalDistanceToClosestCentroid) {
				closestCluster.addPoint(point);
				reorganizedClustersIndices.add(closestClusterIndex);
				points.remove();
			}
		}
		if (!reorganizedClustersIndices.isEmpty()) {
			reorganizedClustersIndices.add(targetClusterIndex);
		}

		return targetSolution;

	}

	protected Solution reorganize(Solution s, List<Integer> indices) {
		Solution solution = s.copySolution();
		solution.reorganize();

		Point imaginaryCentroid = createImaginaryClusterCentoid(solution, indices);
		for (int clusterIndex : indices) {
			for (Iterator<Point> pointIterator = solution.getCluster(clusterIndex).getPoints().iterator(); pointIterator
					.hasNext();) {
				Point point = pointIterator.next();
				Cluster closestCluster = null;
				int closestClusterIndex = 0;
				double minDistanceToCentroid = imaginaryCentroid.distance(point);
				for (int clusterIndexForCentroid = 0; clusterIndexForCentroid < solution
						.getNumOfClusters(); clusterIndexForCentroid++) {
					if (!indices.contains(clusterIndexForCentroid) && solution.getCluster(clusterIndexForCentroid)
							.getCentroid().distance(point) < minDistanceToCentroid) {
						closestCluster = solution.getCluster(clusterIndexForCentroid);
						closestClusterIndex = clusterIndexForCentroid;
						minDistanceToCentroid = closestCluster.getCentroid().distance(point);
					}
				}
				if (closestCluster != null) {
					Point p = point.copyPoint();
					p.setClusterIndex(closestClusterIndex);
					closestCluster.addPoint(p);
					pointIterator.remove();
				}
			}
		}

		solution = populateEmptyClusters(solution, indices);
		return solution;
	}

	protected Solution populateEmptyClusters(Solution s, List<Integer> indices) {
		Solution solution = s.copySolution();
		solution.reorganize();
		for (int clusterIndex : indices) {
			if (solution.getCluster(clusterIndex).getPoints().isEmpty()) {
				double maxClusterRadius = 0;
				Cluster largestCluster = null;
				Point newCentroid = null;
				for (Cluster cluster : solution.getClusters()) {
					if (!cluster.getPoints().isEmpty()) {
						Point tempNewCentroid = findFarthestPoint(cluster);
						if (tempNewCentroid.distance(cluster.getCentroid()) > maxClusterRadius) {
							largestCluster = cluster;
							maxClusterRadius = tempNewCentroid.distance(cluster.getCentroid());
							newCentroid = tempNewCentroid;
						}
					}
				}
				if (newCentroid != null) {
					for (Iterator<Point> pointIterator = largestCluster.getPoints().iterator(); pointIterator
							.hasNext();) {
						Point point = pointIterator.next();
						if (point.distance(newCentroid) < point.distance(largestCluster.getCentroid())) {
							Point p = point.copyPoint();
							p.setClusterIndex(clusterIndex);
							solution.getCluster(clusterIndex).addPoint(p);
							pointIterator.remove();
						}
					}
					solution.reorganize();
				}
			}
		}
		solution.reorganize();
		solution.updateValues();
		return solution;
	}

	protected Point findFarthestPoint(Cluster cluster) {
		Point farthestPoint = cluster.getPoints().get(0);
		Point centroid = cluster.getCentroid();
		double farthestPointDistance = farthestPoint.distance(centroid);
		for (Point point : cluster.getPoints()) {
			if (point.distance(centroid) > farthestPointDistance) {
				farthestPoint = point;
				farthestPointDistance = point.distance(centroid);
			}
		}
		return farthestPoint;
	}

	protected Point createImaginaryClusterCentoid(Solution solution, List<Integer> indices) {
		List<Point> imaginaryClusterPoints = new ArrayList<>();
		for (int clusterIndex = 0; clusterIndex < indices.size(); clusterIndex++) {
			for (Point point : solution.getCluster(clusterIndex).getPoints()) {
				imaginaryClusterPoints.add(point.copyPoint());
			}
		}
		Cluster imaginaryCluster = new Cluster(imaginaryClusterPoints);
		imaginaryCluster.calculateCentroid();
		return imaginaryCluster.getCentroid();
	}

	@Override
	public int getNeighborhoodNumber() {
		return getNumberOfRandomClustersToBeReorganized();
	}

	@Override
	public void setNeighborhoodNumber(int neighborhoodNumber) {
		setNumberOfRandomClustersToBeReorganized(neighborhoodNumber);
	}

	protected List<int[]> findAllSubsets(int n, int k) {
		if (n == 0 || k == 0)
			return null;
		List<int[]> subsets = new ArrayList<int[]>();
		int[] subset = new int[k];

		if (k <= n) {

			for (int i = 0; (subset[i] = i) < k - 1; i++)
				;
			subsets.add(getSubset(subset));
			for (;;) {
				int i;

				for (i = k - 1; i >= 0 && subset[i] == n - k + i; i--)
					;
				if (i < 0) {
					break;
				} else {
					subset[i]++;
					for (++i; i < k; i++) {
						subset[i] = subset[i - 1] + 1;
					}
					subsets.add(getSubset(subset));
				}
			}
		}

		return subsets;
	}

	private int[] getSubset(int[] subset) {
		int[] result = new int[subset.length];
		for (int i = 0; i < subset.length; i++)
			result[i] = subset[i];
		return result;
	}

	private int getNumberOfRandomClustersToBeReorganized() {
		return numberOfRandomClustersToBeReorganized;
	}

	private void setNumberOfRandomClustersToBeReorganized(int numberOfRandomClustersToBeReorganized) {
		this.numberOfRandomClustersToBeReorganized = numberOfRandomClustersToBeReorganized;
	}
}
