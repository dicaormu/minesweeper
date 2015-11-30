package com.fr.mines.domain;


import com.fr.mines.strategy.MinesStrategy;

import java.util.Map;

import static com.fr.mines.domain.Cell.Status.COVERED;
import static com.fr.mines.domain.Cell.Status.UNCOVERED;

public class Grid {

    private Coordinate maxCoordinate;
    private Map<Coordinate, Cell> cells;
    private int mines;
    private int uncoveredCells;
    private boolean gameLost;

    private Grid(Coordinate max, int mines, Map<Coordinate, Cell> cells) {
        this.maxCoordinate = max;
        this.cells = cells;
        this.mines = mines;
        this.uncoveredCells = 0;
        gameLost = false;
    }

    public Coordinate getMaxCoordinate() {
        return maxCoordinate;
    }

    public Cell getCellAt(Coordinate coor) {
        return cells.get(coor);
    }

    public int getMines() {
        return mines;
    }

    public void uncoverCell(Coordinate coord) {
        if (cells.get(coord).isMinePlanted())
            gameLost = true;
        if (cells.get(coord).getStatus().equals(COVERED))
            uncoveredCells++;
        cells.get(coord).withStatus(UNCOVERED);

    }

    public boolean isCellAvailable(Coordinate coord) {
        return cells.get(coord).getStatus().equals(COVERED);
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public boolean isGameWin() {
        return (uncoveredCells + mines) == cells.size();
    }


    public static class GridBuilder {
        private Coordinate maxCoordinate;

        private GridBuilder(Coordinate max) {
            this.maxCoordinate = max;
        }

        public static GridBuilder gridWithMaxCoordinate(Coordinate max) {
            return new GridBuilder(max);
        }

        public Grid build(int mines, MinesStrategy strategy) {
            return new Grid(maxCoordinate, mines, strategy.generateCells(maxCoordinate, mines));
        }
    }

}
