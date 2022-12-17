package com.company;

public class DirectionList {
    private String[] directionList;

    public DirectionList() {
        directionList = new String[4];
        directionList[0] = "LEFT";
        directionList[1] = "RIGHT";
        directionList[2] = "UP";
        directionList[3] = "DOWN";
    }

    public String getDirection(int index) {
        String direction = "";
        if (index < getDirectionListLength()) {
            direction = directionList[index];
        }
        return direction;
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
