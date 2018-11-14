package com.danielleone.puzzle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void distanceFromGoalShouldBeZero() {
        Board b = new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        });
        assertEquals(0, b.manhattan());
        assertEquals(0, b.hamming());
    }

    @Test
    public void distanceFromGoalShouldBeOne() {
        Board b = new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        });

        assertEquals(1, b.manhattan());
        assertEquals(1, b.hamming());
    }

    @Test
    public void distanceFromGoalShouldBeTwo() {
        Board b = new Board(new int[][]{
                {1, 0, 3},
                {4, 5, 6},
                {7, 8, 2}
        });

        assertEquals(3, b.manhattan());
        assertEquals(1, b.hamming());
    }

    @Test
    public void distanceFromGoalShouldBeMany() {
        Board b = new Board(new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        });

        assertEquals(10, b.manhattan());
        assertEquals(5, b.hamming());
    }

    @Test
    public void boardEqualsAndGoal() {
        Board b1 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assert b1.isGoal();
        Board b2 = new Board(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 8, 1}});
        assert !b2.isGoal();
        Board b3 = new Board(new int[][]{{3, 2, 0}, {4, 5, 6}, {7, 8, 1}});
        assert !b3.isGoal();

        Board b4 = new Board(new int[][]{
                {2, 1, 3},
                {4, 5, 6},
                {7, 8, 0}
        });

        Board b6 = new Board(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 8, 1}});
        assert b6.equals(b2);
        Board b5 = b1.twin();
        assert b4.equals(b5);
    }

}