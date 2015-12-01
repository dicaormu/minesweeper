package com.fr.mines.model;


import com.fr.mines.domain.Cell;
import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Grid;
import com.fr.mines.strategy.MinesStrategy;

public class GameModel {


    final Grid grid;
    private GameStatus gameStatus;

    public GameModel(int maxX, int maxY, int mines, MinesStrategy strategy) {
        this.gameStatus = GameStatus.PLAYING;
        grid = Grid.GridBuilder.gridWithMaxCoordinate(new Coordinate(maxX, maxY))
                .build(mines, strategy);
    }

    public Grid getGrid() {
        return grid;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isGameWin() {
        return grid.isGameWin();
    }

    public boolean isGameLost() {
        return grid.isGameLost();
    }

    public boolean isGamePlaying() {
        return gameStatus.equals(GameStatus.PLAYING);
    }

    public void uncoverCell(Coordinate coordinate) {
        if (!grid.isValidCoordinate(coordinate)) {
            throw new PositionOutOfBoundsException(coordinate);
        }
        grid.uncoverCell(coordinate);
        final Cell cell = grid.getCellAt(coordinate);
        if (cell.noAdjacentMines() && !cell.isMinePlanted())
            uncoverSurroundingCells(coordinate);
    }

    private void uncoverSurroundingCells(Coordinate base) {
        for (int i = base.getCoordX() - 1; i <= base.getCoordX() + 1; i++) {
            for (int j = base.getCoordY() - 1; j <= base.getCoordY() + 1; j++) {
                final Coordinate coordinate = new Coordinate(i, j);
                uncoverSurroundingCellsFrom(coordinate, grid);
            }
        }
    }

    private void uncoverSurroundingCellsFrom(Coordinate coordinate, Grid grid) {
        final Cell cell = grid.getCellAt(coordinate);
        if (cell != null
                && !cell.isMinePlanted()
                && grid.isCellAvailable(coordinate)) {
            if (cell.noAdjacentMines()) {
                uncoverCell(coordinate);
            } else {
                grid.uncoverCell(coordinate);
            }
        }
    }

    public void playCoordinate(Coordinate uncover) {
        try {
            uncoverCell(uncover);
            if (isGameLost()) {
                gameStatus = GameStatus.LOST;
            } else if (isGameWin()) {
                gameStatus = GameStatus.WIN;
            }
        } catch (PositionOutOfBoundsException | NullPointerException e) {
            throw new IllegalPositionException(e);
        }
    }

    public enum GameStatus {
        PLAYING, LOST, WIN
    }

}
