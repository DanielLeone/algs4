package com.danielleone.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points;


    /**
     * construct an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }

    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                q.enqueue(p);
            }
        }
        return q;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        Point2D closest = null;
        for (Point2D point : points) {
            if (closest == null || point.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
                closest = point;
            }
        }
        return closest;
    }
}

