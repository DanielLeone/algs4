
package com.danielleone.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int size;

    private class Node {

        Node(Point2D point, RectHV rect) {
            this.p = point;
            this.rect = rect;
        }

        /**
         * the point for this node
         */
        private Point2D p;

        /**
         * the axis-aligned rectangle corresponding to this node
         */
        private RectHV rect;

        /**
         * the left/bottom subtree
         */
        private Node lb;

        /**
         * the right/top subtree
         */
        private Node rt;

    }


    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        root = insert(root, p, new RectHV(0, 0, 1, 1), true);
    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root, false);
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q);
        return q;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        return size() > 0 ? nearest(root, p, root.p, true) : null;
    }

    private Point2D nearest(Node node, Point2D target, Point2D currentClosest, boolean isComparingX) {
        if (node == null) {
            // if there are no more nodes, return the closest point found so far
            return currentClosest;
        }

        Point2D closest = currentClosest;
        // if the current point is closer than the closest point found so far, update the closest point
        if (node.p.distanceSquaredTo(target) < currentClosest.distanceSquaredTo(target)) {
            closest = node.p;
        }

        // work out which which subtree our target point is in
        boolean isInLeftSubtree = isComparingX && target.x() < node.p.x();
        boolean isInBottomSubtree = !isComparingX && target.y() < node.p.y();
        boolean isInLeftOrButtonSubtree = isInLeftSubtree || isInBottomSubtree;
        Node near = isInLeftOrButtonSubtree ? node.lb : node.rt;
        Node far = isInLeftOrButtonSubtree ? node.rt : node.lb;

        // if the near rectangle is closer to the target than the closest point, check the near subtree
        if (near != null && near.rect.distanceSquaredTo(target) < closest.distanceSquaredTo(target)) {
            // updating the closest point if necessary
            closest = nearest(near, target, closest, !isComparingX);
        }

        // now with the closeset point potentially updated by the near tree, we check if the far subtree
        // could even possibly be closer. If it is, we check that too
        if (far != null && far.rect.distanceSquaredTo(target) < closest.distanceSquaredTo(target)) {
            // updating the closest point if necessary
            closest = nearest(far, target, closest, !isComparingX);
        }

        // hmm, mutable references and recursion.. enjoy!
        return closest;

    }

    private void range(Node node, RectHV rect, Queue<Point2D> q) {
        if (node == null) {
            return;
        }
        // If the current point is in the input rectangle, enqueue that point
        if (rect.contains(node.p)) {
            q.enqueue(node.p);
        }
        // Check the left and right subtrees if the input rectangle intersects the current rectangle
        if (node.lb != null && rect.intersects(node.lb.rect)) {
            range(node.lb, rect, q);
        }
        // Check the left and right subtrees if the input rectangle intersects the current rectangle
        if (node.rt != null && rect.intersects(node.rt.rect)) {
            range(node.rt, rect, q);
        }
    }


    private Node insert(Node root, Point2D point, RectHV rect, boolean isComparingX) {
        if (root == null) {
            // if we've hit the bottom of the tree, we're in the right spot to insert
            size++;
            return new Node(point, rect);
        } else if (root.p.x() == point.x() && root.p.y() == point.y()) {
            // if our current parent is the same as our inserting point, skip!
            return root;
        }

        if (isComparingX) {
            if (point.x() < root.p.x()) {
                RectHV nextRect = new RectHV(rect.xmin(), rect.ymin(), root.p.x(), rect.ymax());
                root.lb = insert(root.lb, point, nextRect, false);
            } else {
                RectHV nextRect = new RectHV(root.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                root.rt = insert(root.rt, point, nextRect, false);
            }
        } else {
            if (point.y() < root.p.y()) {
                RectHV nextRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), root.p.y());
                root.lb = insert(root.lb, point, nextRect, true);
            } else {
                RectHV nextRect = new RectHV(rect.xmin(), root.p.y(), rect.xmax(), rect.ymax());
                root.rt = insert(root.rt, point, nextRect, true);
            }
        }

        return root;
    }

    private boolean contains(Node root, Point2D point, boolean isComparingX) {
        if (root == null) {
            return false;
        }
        if (point.x() == root.p.x() && point.y() == root.p.y()) {
            return true;
        }
        double nodeValue = isComparingX ? point.x() : point.y();
        double rootValue = isComparingX ? root.p.x() : root.p.y();
        if (nodeValue < rootValue) {
            return contains(root.lb, point, !isComparingX);
        } else {
            return contains(root.rt, point, !isComparingX);
        }
    }

    private void draw(Node node, boolean drawHorizontal) {
        if (node == null) {
            return;
        }
        // Draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        if (!drawHorizontal) {
            // Draw vertical line with x-coordinates of the point and y-coordinates of the parent rectangle
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            // Draw horizontal line with y-coordinates of the point and x-coordinates of the parent rectangle
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        // Draw subtrees
        draw(node.lb, !drawHorizontal);
        draw(node.rt, !drawHorizontal);
    }
}

