package com.danielleone.collinear;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segs;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (hasNull(points)) {
            throw new IllegalArgumentException("Array contains a null");
        }
        if (hasDuplicatePoints(points)) {
            throw new IllegalArgumentException("Cannot have duplicate points.");
        }
        ArrayList<LineSegment> tmp = new ArrayList<>();
        int length = points.length;
        // examines 4 points at a time and checks whether they all lie on the same
        // line segment, returning all such line segments.
        // To check whether the 4 points p, q, r, and s are collinear, check whether
        // the three slopes between p and q, between p and r, and between p and s are
        // all equal
        for (int p = 0; p < length; p++) {
            for (int q = p + 1; q < length; q++) {
                double slopeToQ = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < length; r++) {
                    double slopeToR = points[p].slopeTo(points[r]);
                    if (slopeToQ == slopeToR) {
                        for (int s = r + 1; s < length; s++) {
                            double slopeToS = points[p].slopeTo(points[s]);
                            if (slopeToQ == slopeToS) {
                                Point[] tuple = new Point[] {points[p], points[q], points[r], points[s]};
                                Arrays.sort(tuple);
                                tmp.add(new LineSegment(tuple[0], tuple[3]));
                            }
                        }
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

    public static void main(String[] args) {
        BruteCollinearPoints b = new BruteCollinearPoints(new Point[] { new Point(1, 2), new Point(2, 3) });
        assert b.numberOfSegments() == 0;
        assert b.segments().length == 0;
    }
}
