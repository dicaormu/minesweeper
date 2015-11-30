package com.fr.mines.domain;

public class Cell {

    private final int NO_MINES_PLANTED = 0;
    private final int ONE_MINE_PLANTED = 1;

    private Status status;
    private int adjacentMines;
    private boolean minePlanted;

    public Cell() {
        status = Status.COVERED;
        adjacentMines = 0;
        minePlanted = false;
    }

    public Status getStatus() {
        return status;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public boolean noAdjacentMines() {
        return getAdjacentMines() == 0;
    }

    public boolean isMinePlanted() {
        return minePlanted;
    }

    public void addAdjacentMine(int numberOfMines) {
        adjacentMines += numberOfMines;
    }

    public void withStatus(Status status) {
        this.status = status;
    }

    public int plantMine() {
        if (minePlanted) return NO_MINES_PLANTED;
        minePlanted = true;
        return ONE_MINE_PLANTED;
    }

    public enum Status {
        UNCOVERED(" "), COVERED("#");

        String representation;

        Status(String representation) {
            this.representation = representation;
        }

        public String getRepresentation() {
            return representation;
        }
    }


}
