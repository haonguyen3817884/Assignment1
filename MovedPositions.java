package com.company;

public class MovedPositions {
    private Position head;
    private int size;

    public MovedPositions() {
        this.head = new Position(0,0,null);
        this.size = 1;
    }

    // get position from direction
    public Position getPosition(String direction) {
        Position position = null;

        if (direction.equals("LEFT") || direction.equals("UP") || direction.equals("RIGHT") || direction.equals("DOWN")) {
            if (head != null) {
                int x = head.getX();
                int y = head.getY();

                if (direction.equals("LEFT")) {
                    x = x - 1;
                } else if (direction.equals("RIGHT")) {
                    x = x + 1;
                } else if (direction.equals("UP")) {
                    y = y + 1;
                } else {
                    y = y - 1;
                }

                position = new Position(x, y, null);
            }
        }

        return position;
    }

    // keep track current moved positions
    public void goToPlace(Position position) {
        if (head != null) {
            Position nextPosition = new Position(position.getX(), position.getY(), null);

            nextPosition.setNextPosition(head);

            head = nextPosition;
            size = size + 1;
        }
    }

    // pop the stack
    public void getPrevious() {
        if (!isStartPosition()) {
            head = head.getNextPosition();

            size = size - 1;
        }
    }

    public String getPreviousDirection() {
        String direction = "";
        if (!isStartPosition()) {
            direction = getDirection(head, head.getNextPosition());
        }
        return direction;
    }

    // check whether the current position is the first position
    public boolean isStartPosition() {
        return ((head.getX() == 0) && (head.getY() == 0));
    }

    // get direction from a position to another position
    public static String getDirection(Position fromPosition, Position toPosition) {
        String direction = "";
        if (fromPosition.getX() == toPosition.getX()) {
            if (fromPosition.getY() > toPosition.getY()) {
                direction = "DOWN";
            } else {
                direction = "UP";
            }
        } else if (fromPosition.getY() == toPosition.getY()) {
            if (fromPosition.getX() > toPosition.getX()) {
                direction = "LEFT";
            } else {
                direction = "RIGHT";
            }
        }
        return direction;
    }

    public Position getHead() {
        return this.head;
    }

    public int getSize() {
        return this.size;
    }
}
