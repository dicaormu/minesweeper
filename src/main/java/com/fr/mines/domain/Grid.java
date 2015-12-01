package com.fr.mines.domain;


import com.fr.mines.strategy.MinesStrategy;

import java.util.Map;
import java.util.Objects;

import static com.fr.mines.domain.Cell.CellStatus.UNCOVERED;

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
        this.gameLost = false;
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
        if (cells.get(coord).isCovered())
            uncoveredCells++;
        cells.get(coord).withStatus(UNCOVERED);

    }

    public boolean isCellAvailable(Coordinate coord) {
        return cells.get(coord).isCovered();
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public boolean isGameWin() {
        return (uncoveredCells + mines) == cells.size();
    }

    public boolean isValidCoordinate(Coordinate coordinate) {
        Objects.requireNonNull(coordinate);
        int x = coordinate.getCoordX();
        int y = coordinate.getCoordY();
        int maxX = maxCoordinate.getCoordX();
        int maxY = maxCoordinate.getCoordY();
        return 0 < x && x <= maxX &&
                0 < y && y <= maxY;
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
