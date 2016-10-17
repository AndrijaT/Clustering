package com.andrija.clustering.utility.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andrija.clustering.model.Point;
import com.andrija.clustering.model.Problem;
import com.andrija.clustering.model.point.Point3D;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.utility.PointUtility;

public class PointUtility3DImpl extends PointUtility {

	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Problem generateProblem() {
		List<Point> points = new ArrayList<Point>();
		ConfigProperties configProperties = ConfigProperties.getConfigProperties();
		long pointCunter = 0;
		long counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(configProperties.getDatasetFilePath()))) {
			for (String line; (line = br.readLine()) != null;) {
				String[] stringCoords = line.trim().split(",");
				double x = Double.parseDouble(stringCoords[0]);
				double y = Double.parseDouble(stringCoords[1]);
				double z = Double.parseDouble(stringCoords[2]);

				points.add(new Point3D(x, y, z));
				if (pointCunter == 10000 - 1) {
					counter++;
					pointCunter = 0;
					log.info("Reading point: " + counter + "0.000");
				}
				pointCunter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		log.info("Done reading");
		int k = 0;
		counter = 0;
		pointCunter = 0;
		for (k = 0; k < points.size(); k++) {
			if (k % 10000 == 0) {
				counter++;
				pointCunter = 0;
				log.info("Assigning index point: " + counter + "0.000");
			}
			points.get(k).setIndex(k);
			pointCunter++;
		}
		log.info("Done assigning indices");
		if (k > 1000) {
			log.warn("Big dataset, not finding closest points.");
		} else {
			points = findClosestPoints(points);
			log.info("Done finding closest point");
		}
		return new Problem(points);
	}

	@Override
	public Point calculateCentroid(List<Point> points) {
		if (points.isEmpty())
			return null;
		long x = 0;
		long y = 0;
		long z = 0;
		long k = 0;
		for (Point point : points) {
			x += ((Point3D) point).getX();
			y += ((Point3D) point).getY();
			z += ((Point3D) point).getZ();
			k++;
		}
		return new Point3D(x / k, y / k, z / k);
	}

	@Override
	public double calculateSum(List<Point> points, Point c) {
		long sum = 0;
		Point3D p;
		Point3D centroid = (Point3D) c;
		if (points != null && points.size() != 0 && centroid != null) {
			for (int i = 0; i < points.size(); i++) {
				p = (Point3D) points.get(i);
				sum += (p.getX() - centroid.getX()) * (p.getX() - centroid.getX())
						+ (p.getY() - centroid.getY()) * (p.getY() - centroid.getY())
						+ (p.getZ() - centroid.getZ()) * (p.getZ() - centroid.getZ());
			}
		}
		return sum;
	}
	
	private List<Point> findClosestPoints(List<Point> points) {
		int pointsSize = points.size();
		int closestPointIndex = 0;
		double closesPointDistance;
		for (int i = 0; i < pointsSize; i++) {
			closesPointDistance = 0;
			for (int j = 0; j < pointsSize; j++) {
				if (i != j
						&& (points.get(j).getClosestPoint() == null
								|| points.get(i).getIndex() != points.get(j).getClosestPoint().getIndex())
						&& ((closesPointDistance != 0 && points.get(i).distance(points.get(j)) < closesPointDistance)
								|| closesPointDistance == 0)) {
					closesPointDistance = points.get(i).distance(points.get(j));
					closestPointIndex = j;
				}
			}
			points.get(i).setClosestPoint(points.get(closestPointIndex));
			points.get(i).setDistanceToClosestPoint(closesPointDistance);
		}
		return points;
	}
}
