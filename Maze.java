package com.company;

public class Maze {
    int rows;
    int cols;
    String[] map;
    int robotRow;
    int robotCol;
    int steps;

    public Maze() {
        // Note: in my real test, I will create much larger
        // and more complicated map
        rows = 30;
        cols = 100;
        map = new String[rows];

        map[0] = "....................................................................................................";
        map[1] = ".                                              ..                                                  .";
        map[2] = ".                                              ..                          ..                      .";
        map[3] = ".                                              ..                          ..                      .";
        map[4] = ".      ..............                          ..                  ...     ..                      .";
        map[5] = ".            .............                     ..                  ...     ..   ....................";
        map[6] = ".                                              ..                  ...     ..   ....................";
        map[7] = ".       ....  .   .  .  .....                                                   ...     ..         .";
        map[8] = ".       .  .  .. ..  .    .            ........                     .........   ...     ..         .";
        map[9] = ".       ....  . . .  .    .            ........                    .........    ...     ..         .";
        map[10] = ".       ..    .   .  .    .            ........                                 ...     ..         .";
        map[11] = ".       . .   .   .  .    .        .   ........                        ..                       ....";
        map[12] = ".       .  .  .   .  .    .        .   ........                        ..                       ....";
        map[13] = ".                                  .                                   ..  ..           .......    .";
        map[14] = ".    ..       ..    ..       ..    .        ..      ..    ...          ..  ..           .......    .";
        map[15] = ".    ....     ..    ....     ..    .        ..      ..    .....        ..  ..                      .";
        map[16] = ".    .. ..    ..    .. ..    ..        .    ..      ..    ..  ...  ..      ..                      .";
        map[17] = ".    ..  ..   ..    ..  ..   ..   .......   ..........    ..   ..  ..      ..                      .";
        map[18] = ".    ..   ..  ..    ..   ..  ..   .......   ..........    ..   ..  ..      ..                      .";
        map[19] = ".    ..    .. ..    ..    .. ..        .    ..      ..    ..  ...          ..                      .";
        map[20] = ".    ..     ....    ..     ....             ..      ..    .....            ..    ..........        .";
        map[21] = ".    ..       ..    ..       ..             ..      ..    ...              ..    ..........        .";
        map[22] = ".                                                                          ..                      .";
        map[23] = ".      .            .           .            .                             ..          X           .";
        map[24] = ".      .     .      .           .            .                             ..                      .";
        map[25] = ".      .     .      .                        .        .                    ..                      .";
        map[26] = ".      .     .      .                        .        .                    ..                      .";
        map[27] = ".      .     .      .           .            .        .                    ..                      .";
        map[28] = ".            .                  .                     .                    ..                      .";
        map[29] = "....................................................................................................";

        robotRow = 2;
        robotCol = 1;
        steps = 0;
    }

    public String go(String direction) {
        if (!direction.equals("UP") &&
                !direction.equals("DOWN") &&
                !direction.equals("LEFT") &&
                !direction.equals("RIGHT")) {
            // invalid direction
            steps++;
            return "false";
        }
        int currentRow = robotRow;
        int currentCol = robotCol;
        if (direction.equals("UP")) {
            currentRow--;
        } else if (direction.equals("DOWN")) {
            currentRow++;
        } else if (direction.equals("LEFT")) {
            currentCol--;
        } else {
            currentCol++;
        }

        // check the next position
        if (map[currentRow].charAt(currentCol) == 'X') {
            // Exit gate
            steps++;
            System.out.println("Steps to reach the Exit gate " + steps);
            return "win";
        } else if (map[currentRow].charAt(currentCol) == '.') {
            // Wall
            steps++;
            return "false";
        } else {
            // Space => update robot location
            steps++;
            robotRow = currentRow;
            robotCol = currentCol;
            return "true";
        }
    }

    public static void main(String[] args) {
        (new Robot()).navigate();
    }
}

class Robot {
    private VisitedPositions visitedPositions;
    private MovedPositions movedPositions;
    private DirectionList list;
    private Positions positions;

    public Robot() {
        visitedPositions = new VisitedPositions();
        visitedPositions.placePosition(new Position(0,0,null));
        movedPositions = new MovedPositions();
        list = new DirectionList();
        positions = new Positions();
        positions.placePosition(new Position(0,0,null));
    }

    // A very simple implementation
    // where the robot just go randomly
    public void navigate() {
        Maze maze = new Maze();
        String result = "";
        while (!result.equals("win")) {
            boolean isIn = false;
            for (int i = 0; i < list.getDirectionListLength(); ++i) {
                String direction = list.getDirection(i);
                Position place = movedPositions.getPosition(direction);
                
                if (!positions.isPositionInPositions(place)) {
                    result = maze.go(direction);
                    positions.placePosition(place);

                    if (!result.equals("false")) {
                        movedPositions.goToPlace(place);
                        list.updateDirectionPriority(i);
                        isIn = true;
                        break;
                    }
                }
            }

            if (!isIn) {
                if (!movedPositions.isStartPosition()) {
                    String direction = "";
                    direction = movedPositions.getPreviousDirection();
                    movedPositions.getPrevious();
                    result = maze.go(direction);
                } else {
                    break;
                }
            }
        }
    }
}

class VisitedPositions {
    private Position head;
    private int size;

    public VisitedPositions(Position head) {
        head.setNextPosition(null);
        this.head = head;
        this.size = 1;
    }

    public VisitedPositions() {
        this.head = null;
        this.size = 0;
    }

    public void placePosition(Position position) {
        if (head != null) {
            Position place = new Position(position.getX(), position.getY(), null);
            place.setNextPosition(head);
            head = place;
            size = size + 1;
        } else {
            head = position;
            size = 1;
        }
    }

    public boolean isPositionInPositions(Position position) {
        boolean isIn = false;
        Position place = head;
        while (place != null) {
            if (place.getX() == position.getX()) {
                if (place.getY() == position.getY()) {
                    isIn = true;
                }
            }
            place = place.getNextPosition();
        }
        return isIn;
    }
}

class Positions {
    private boolean[][] positions;
    private int MAX_POSITION = 2001;

    public Positions() {
        positions = new boolean[MAX_POSITION][MAX_POSITION];
        for (int i = 0; i < positions.length; ++i) {
            for (int j = 0; j < positions.length; ++j) {
                positions[i][j] = false;
            }
        }
    }

    public void placePosition(Position position) {
        int col = 0;
        int row = 0;

        col = position.getX() + (int) (MAX_POSITION / 2);
        row = position.getY() + (int) (MAX_POSITION / 2);

        positions[row][col] = true;
    }

    public boolean isPositionInPositions(Position position) {
        boolean isIn = false;
        int col = 0;
        int row = 0;

        col = position.getX() + (int) (MAX_POSITION / 2);
        row = position.getY() + (int) (MAX_POSITION / 2);

        isIn = positions[row][col];
        return isIn;
    }
}

class MovedPositions {
    private Position head;
    private int size;

    public MovedPositions() {
        this.head = new Position(0,0,null);
        this.size = 1;
    }

    public Position getPosition(String direction) {
        Position place = null;

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

                place = new Position(x, y, null);
            }
        }

        return place;
    }

    public void goToPlace(Position position) {
        if (head != null) {
            Position place = new Position(position.getX(), position.getY(), null);

            place.setNextPosition(head);

            head = place;
            size = size + 1;
        }
    }

    public void getPrevious() {
        if (!isStartPosition()) {
            head = head.getNextPosition();

            size = size - 1;
        }
    }

    public String getPreviousDirection() {
        String place = "";
        if (!isStartPosition()) {
            place = getDirection(head, head.getNextPosition());
        }
        return place;
    }

    public boolean isStartPosition() {
        return ((head.getX() == 0) && (head.getY() == 0));
    }

    public static String getDirection(Position fromPosition, Position toPosition) {
        String place = "";
        if (fromPosition.getX() == toPosition.getX()) {
            if (fromPosition.getY() > toPosition.getY()) {
                place = "DOWN";
            } else {
                place = "UP";
            }
        } else if (fromPosition.getY() == toPosition.getY()) {
            if (fromPosition.getX() > toPosition.getX()) {
                place = "LEFT";
            } else {
                place = "RIGHT";
            }
        }
        return place;
    }

    public Position getHead() {
        return this.head;
    }
}

class Position {
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

class DirectionList {
    private String[] directionList;

    public DirectionList() {
        directionList = new String[4];
        directionList[0] = "LEFT";
        directionList[1] = "RIGHT";
        directionList[2] = "UP";
        directionList[3] = "DOWN";
    }

    public String getDirection(int index) {
        String place = "";
        if (index < getDirectionListLength()) {
            place = directionList[index];
        }
        return place;
    }

    public void updateDirectionPriority(int index) {
        if (index < getDirectionListLength()) {
            String temp = directionList[0];
            directionList[0] = directionList[index];
            directionList[index] = temp;
        }
    }

    public int getDirectionListLength() {
        return this.directionList.length;
    }

}