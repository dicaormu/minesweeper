package com.fr.mines.domain;

public class Cell {

    private static final int NO_MINES_PLANTED = 0;
    private static final int ONE_MINE_PLANTED = 1;

    private CellStatus cellStatus;
    private int adjacentMines;
    private boolean minePlanted;

    public Cell() {
        cellStatus = CellStatus.COVERED;
        adjacentMines = 0;
        minePlanted = false;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
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

    public void withStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    public boolean isCovered() {
        return cellStatus.equals(CellStatus.COVERED);
    }

    public int plantMine() {
        if (minePlanted) return NO_MINES_PLANTED;
        minePlanted = true;
        return ONE_MINE_PLANTED;
    }

    public enum CellStatus {
        UNCOVERED(" "), COVERED("#");

        String representation;

        CellStatus(String representation) {
            this.representation = representation;
        }

        public String getRepresentation() {
            return representation;
        }
    }


}
