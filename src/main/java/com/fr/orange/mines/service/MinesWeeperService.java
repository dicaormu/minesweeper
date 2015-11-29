package com.fr.orange.mines.service;


import com.fr.orange.mines.domain.Cell;
import com.fr.orange.mines.domain.Coordinate;
import com.fr.orange.mines.domain.Grid;
import com.fr.orange.mines.strategy.MinesStrategy;

import static com.fr.orange.mines.domain.Grid.GridBuilder.gridWithMaxCoordinate;

public class MinesWeeperService {
    final Grid game;

    public MinesWeeperService(int maxX, int maxY, int mines, MinesStrategy strategy) {
        game = gridWithMaxCoordinate(new Coordinate(maxX, maxY))
                .build(mines, strategy);
    }

    public Grid getGame() {
        return game;
    }

    public boolean uncoverCell(Coordinate cellCoordinate) {
        game.uncoverCell(cellCoordinate);
        Cell cell = game.getCell(cellCoordinate);
        if (cell.getAdjacentMines() == 0 && !cell.isMinePLanted())
            uncoverSurroundingCells(cellCoordinate.getCoordX(), cellCoordinate.getCoordY());
        return !game.isGameLost();
    }

    public void uncoverSurroundingCells(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                Coordinate cellCoordinate = new Coordinate(i, j);
                Cell cell = game.getCell(cellCoordinate);
                if (cell != null && !cell.isMinePLanted() && game.isCellAvailable(cellCoordinate)) {
                    if (cell.getAdjacentMines() == 0) {
                        uncoverCell(cellCoordinate);
                    } else
                        game.uncoverCell(cellCoordinate);
                }
            }
        }
    }

    public boolean isGameWin(){
        return game.isGameWin();
    }

}
