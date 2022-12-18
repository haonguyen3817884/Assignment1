package com.company;

public class VisitedPositions {
    private boolean[][] positions;
    private int MAX_POSITION = 2001;

    public VisitedPositions() {
        positions = new boolean[MAX_POSITION][MAX_POSITION];
        for (int i = 0; i < positions.length; ++i) {
            for (int j = 0; j < positions.length; ++j) {
                positions[i][j] = false;
            }
        }
    }

    // keep track all visited positions
    public void visitPosition(Position position) {
        int col = 0;
        int row = 0;

        col = position.getX() + (int) (MAX_POSITION / 2);
        row = position.getY() + (int) (MAX_POSITION / 2);

        positions[row][col] = true;
    }

    // check whether a position is visited or not
    public boolean isPositionVisited(Position position) {
        boolean isMoved = false;
        int col = 0;
        int row = 0;

        col = position.getX() + (int) (MAX_POSITION / 2);
        row = position.getY() + (int) (MAX_POSITION / 2);

        isMoved = positions[row][col];
        return isMoved;
    }
}
