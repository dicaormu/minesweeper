package com.fr.orange.mines.domain;


public class Cell {
    //private Coordinate coordinate;
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

    public boolean isMinePLanted() {
        return minePlanted;
    }

    public void addAdjacentMine(int numberOfMines) {
        adjacentMines += numberOfMines;
    }

    public void withStatus(Status status) {
        this.status = status;
    }

    public int plantMine() {
        if (minePlanted)
            return 0;
        minePlanted = true;
        return 1;
    }


    public enum Status {
        UNCOVERED(" "), COVERED("#");

        String representation;
        private Status (String representation){
            this.representation=representation;
        }

        public String getRepresentation(){
            return representation;
        }
    }


}
