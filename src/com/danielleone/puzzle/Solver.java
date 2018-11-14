package com.danielleone.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {

    private final Stack<Board> boards;
    private boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int cachedPriority = -1;

        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        private int priority() {
            if (cachedPriority == -1) {
                cachedPriority = moves + board.manhattan();
            }
            return cachedPriority;
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.priority() < that.priority()) {
                return -1;
            }
            if (this.priority() > that.priority()) {
                return +1;
            }
            return 0;
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        boards = new Stack<>();

        MinPQ<SearchNode> queue = new MinPQ<>();
        MinPQ<SearchNode> queueTwin = new MinPQ<>();
        Board board = initial;
        Board boardTwin = initial.twin();
        SearchNode node = new SearchNode(board, 0, null);
        SearchNode nodeTwin = new SearchNode(boardTwin, 0, null);
        queue.insert(node);
        queueTwin.insert(nodeTwin);
        while (true) {
            node = queue.delMin();
            nodeTwin = queueTwin.delMin();
            board = node.board;
            boardTwin = nodeTwin.board;
            if (boardTwin.isGoal()) {
                isSolvable = false;
                return;
            }
            if (board.isGoal()) {
                isSolvable = true;
                this.boards.push(board);
                while (node.previous != null) {
                    node = node.previous;
                    this.boards.push(node.board);
                }
                return;
            }
            node.moves++;
            nodeTwin.moves++;
            Iterable<Board> neighbors = board.neighbors();
            for (Board neighbor : neighbors) {
                if (node.previous != null && neighbor.equals(node.previous.board)) {
                    continue;
                }
                SearchNode newNode = new SearchNode(neighbor, node.moves, node);
                queue.insert(newNode);
            }
            Iterable<Board> neighborsTwin = boardTwin.neighbors();
            for (Board neighbor : neighborsTwin) {
                if (nodeTwin.previous != null && neighbor.equals(nodeTwin.previous.board)) {
                    continue;
                }
                SearchNode newNode = new SearchNode(neighbor, nodeTwin.moves, nodeTwin);
                queueTwin.insert(newNode);
            }
        }
    }

    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if no solution
     */
    public int moves() {
        return isSolvable() ? boards.size() - 1 : -1;
    }

    /**
     * sequence of boards in a shortest solution; null if no solution
     */
    public Iterable<Board> solution() {
        return isSolvable() ? new ArrayList<>(boards) : null;
    }

    /**
     * solve a slider puzzle (given below)
     *
     * @param args
     */
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
