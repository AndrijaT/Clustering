/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrija.clustering.model;

import java.util.ArrayList;
import java.util.List;

import com.andrija.clustering.utility.Utility;

public class Cluster {
    
    private List<Point> points;
    private Point centroid;

    public Cluster(List<Point> points) {
    	this(points, null);
	}
    
    public Cluster(List<Point> points, Point centroid) {
    	this.points = new ArrayList<Point>();
    	if(points != null) {
    		for (Point point : points) {
    			this.points.add(point.copyPoint());
    		}
    		if(centroid == null) {
    			calculateCentroid();
    		} else {
    			this.centroid = centroid.copyPoint();
    		}
    	} else {
    		System.out.println("WARN: kreira se prazna lista tacaka!!!");
    		this.centroid = (Point) new Object();
    	}
    	
    }
    
    public Cluster(Cluster c) {
    	this(c.getPoints(), c.getCentroid());
//		this.centroid = c.getCentroid() != null ? c.getCentroid() : new Point();
    }
    
    public Cluster() {
    	this.points = new ArrayList<Point>();
		//this.centroid = (Point) new Object();
    }

	public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
    
    public Point getPoint(int i) {
    	return points.get(i);
    }
    
    public void removePoint(int i) {
    	points.remove(i);
    }
    
    public void addPoint(Point p) {
    	points.add(p);
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public void calculateCentroid() {
    	this.centroid = Utility.getPointUtility().calculateCentroid(points);
    }
    
    public int size() {
    	return points != null ? points.size() : 0;
    }
    
}
