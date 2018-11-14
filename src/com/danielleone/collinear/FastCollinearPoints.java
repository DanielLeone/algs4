package com.danielleone.collinear;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final LineSegment[] segs;

    /**
     * finds all line segments containing 4 or more points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (hasNull(points)) {
            throw new NullPointerException("Array contains a null");
        }
        if (hasDuplicatePoints(points)) {
            throw new IllegalArgumentException("Cannot have duplicate points.");
        }
        ArrayList<LineSegment> tmp = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] copy = points.clone();
            Arrays.sort(copy, p.slopeOrder());
            for (int j = 0; j < copy.length - 2; j++) {
                Point q = copy[j];
                if (p.compareTo(q) == 0) {
                    continue;
                }
                Point r = copy[j + 1];
                Point s = copy[j + 2];
                double slopeToQ = p.slopeTo(q);
                double slopeToR = p.slopeTo(r);
                double slopeToS = p.slopeTo(s);
                if (slopeToQ == slopeToR && slopeToR == slopeToS) {
                    Point[] tuple = new Point[] {p, q, r, s};
                    Arrays.sort(tuple);
                    if (tuple[0].compareTo(p) == 0) {
                        tmp.add(new LineSegment(tuple[0], tuple[3]));
                    }
                }
            }
        }

        segs = new LineSegment[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            segs[i] = tmp.get(i);
        }
    }

    /**
     * the number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return segs.length;
    }

    /**
     * the line segments
     *
     * @return
     */
    public LineSegment[] segments() {
        return segs.clone();
    }

    private boolean hasDuplicatePoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean hasNull(Point[] points) {
        return Arrays.asList(points).contains(null);
    }
}
