package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.solution.Solution;

public class CSMeasureEvaluation implements ClusterEvaluation {

	private Solution solution;

	public CSMeasureEvaluation(Solution solution) {
		this.solution = solution;
	}

	@Override
	public double evaluate() {
		double sum = 0;
		double maxDist = 0;
		double clusterSum = 0;
		for (Cluster c : solution.getClusters()) {
			clusterSum = 0;
			for (Point p : c.getPoints()) {
				maxDist = 0;
				for (Point p1 : c.getPoints())
					if (p.distance(p1) > maxDist)
						maxDist = p.distance(p1);
				clusterSum += maxDist;
			}
			sum += (clusterSum / c.size());
		}

		double minDistSum = 0;
		double minDist = 0;
		for (int i = 0; i < solution.getNumOfClusters(); i++) {
			minDist = 0;
			for (int j = 0; j < solution.getNumOfClusters(); j++) {
				if (i != j && ((minDist == 0) || solution.getCluster(i).getCentroid()
						.distance(solution.getCluster(j).getCentroid()) < minDist)) {
					minDist = solution.getCluster(i).getCentroid().distance(solution.getCluster(j).getCentroid());
				}
			}
			minDistSum += minDist;
		}

		return sum / minDistSum;
	}

}
