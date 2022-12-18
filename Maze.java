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
    private MovedPositions movedPositions;
    private DirectionList list;
    private VisitedPositions visitedPositions;

    public Robot() {
        movedPositions = new MovedPositions();
        list = new DirectionList();
        visitedPositions = new VisitedPositions();
        visitedPositions.visitPosition(new Position(0,0,null));
    }

    // A very simple implementation
    // where the robot just go randomly
    public void navigate() {
        Maze maze = new Maze();
        String result = "";
        while (!result.equals("win")) {
            boolean isMoved = false;
            for (int i = 0; i < list.getDirectionListLength(); ++i) {
                String direction = list.getDirection(i);
                Position nextPosition = movedPositions.getPosition(direction);
                
                if (!visitedPositions.isPositionVisited(nextPosition)) {
                    result = maze.go(direction);
                    visitedPositions.visitPosition(nextPosition);

                    if (!result.equals("false")) {
                        movedPositions.goToPlace(nextPosition);
                        list.updateDirectionPriority(i);
                        isMoved = true;
                        break;
                    }
                }
            }

            if (!isMoved) {
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


        Position[] positions = new Position[movedPositions.getSize()];

        Position nextPosition = movedPositions.getHead();
        int index = movedPositions.getSize() - 1;
        while(nextPosition != null) {
            positions[index] = nextPosition;
            index = index - 1;
            nextPosition = nextPosition.getNextPosition();
        }

        for (int i = 0; i < positions.length - 1; ++i) {
            System.out.print(MovedPositions.getDirection(positions[i], positions[i + 1]));
            System.out.print("\n");
        }
    }
}

