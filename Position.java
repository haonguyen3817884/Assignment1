package com.company;

public class Position {
    private int x;
    private int y;
    private Position nextPosition;

    public Position(int x, int y, Position nextPosition) {
        this.x = x;
        this.y = y;
        this.nextPosition = nextPosition;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position getNextPosition() {
        return this.nextPosition;
    }

    public void setNextPosition(Position nextPosition) {
        this.nextPosition = nextPosition;
    }
}
