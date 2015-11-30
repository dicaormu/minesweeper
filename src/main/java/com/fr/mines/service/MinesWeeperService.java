package com.fr.mines.service;


import com.fr.mines.domain.Cell;
import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Grid;
import com.fr.mines.strategy.MinesStrategy;

public class MinesWeeperService {
    final Grid game;

    public MinesWeeperService(int maxX, int maxY, int mines, MinesStrategy strategy) {
        game = Grid.GridBuilder.gridWithMaxCoordinate(new Coordinate(maxX, maxY))
                .build(mines, strategy);
    }

    public Grid getGame() {
        return game;
    }

    public boolean isGameWin() {
        return game.isGameWin();
    }

    public boolean uncoverCell(Coordinate coordinate) {
        game.uncoverCell(coordinate);
        final Cell cell = game.getCellAt(coordinate);
        if (cell.noAdjacentMines() && !cell.isMinePlanted())
            uncoverSurroundingCells(coordinate);
        return !game.isGameLost();
    }

    private void uncoverSurroundingCells(Coordinate base) {
        for (int i = base.getCoordX() - 1; i <= base.getCoordX() + 1; i++) {
            for (int j = base.getCoordY() - 1; j <= base.getCoordY() + 1; j++) {
                final Coordinate coordinate = new Coordinate(i, j);
                uncoverSurroundingCellsFrom(coordinate, game);
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

}
