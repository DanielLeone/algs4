package com.danielleone.puzzle;

import java.util.ArrayList;

public class Board {

    private final int n;
    private final int[][] tiles;

    /**
     * construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException("banana");
        }
        n = blocks.length;
        tiles = copy(blocks);
    }

    /**
     * board dimension n
     *
     * @return
     */
    public int dimension() {
        return n;
    }

    /**
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != goalForTile(i, j) && tiles[i][j] != 0) {
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += distanceFromGoal(i, j);
            }
        }
        return sum;
    }

    private int distanceFromGoal(int i, int j) {
        int tile = tiles[i][j];
        int goalRow = (tile - 1) / n;
        int goalCol = (tile - 1) % n;
        // a space doesn't count as a tile, always zero
        return tile == 0 ? 0 : Math.abs(i - goalRow) + Math.abs(j - goalCol);
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != goalForTile(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int goalForTile(int i, int j) {
        if (i == n - 1 && j == n - 1) {
            return 0;
        }
        return j + n * i + 1;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        for (int i = 0; i < n; i++) {
            // leave off the last column since we check if it's null too
            for (int j = 0; j < n - 1; j++) {
                // find the first block that isn't 0 and doesn't have a zero next to it.
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    return new Board(swap(copy(tiles), i, j, i, j + 1));
                }
            }
        }
        return null;
    }

    private static int[][] copy(int[][] board) {
        int[][] copy = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board.length);
        }
        return copy;
    }

    private int[][] swap(int[][] board, int i, int j, int i0, int j0) {
        int tmp = board[i][j];
        board[i][j] = board[i0][j0];
        board[i0][j0] = tmp;
        return board;
    }

    /**
     * does this board equal y?
     *
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (y.getClass() != getClass()) {
            return false;
        }
        Board b = (Board) y;
        if (b.n != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (b.tiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        int zeroJ = 0;
        int zeroI = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }

        boolean canSwapTop = zeroI > 0;
        boolean canSwapRight = zeroJ < n - 1;
        boolean canSwapBottom = zeroI < n - 1;
        boolean canSwapLeft = zeroJ > 0;

        ArrayList<Board> neighbors = new ArrayList<>();
        if (canSwapTop) {
            neighbors.add(new Board(swap(tiles, zeroI, zeroJ, zeroI - 1, zeroJ)));
        }
        if (canSwapRight) {
            neighbors.add(new Board(swap(tiles, zeroI, zeroJ, zeroI, zeroJ + 1)));
        }
        if (canSwapBottom) {
            neighbors.add(new Board(swap(tiles, zeroI, zeroJ, zeroI + 1, zeroJ)));
        }
        if (canSwapLeft) {
            neighbors.add(new Board(swap(tiles, zeroI, zeroJ, zeroI, zeroJ - 1)));
        }

        return neighbors;
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
