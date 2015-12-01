package com.fr.mines;


import com.fr.mines.domain.Cell;
import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Grid;
import com.fr.mines.model.GameModel;
import com.fr.mines.model.IllegalPositionException;
import com.fr.mines.strategy.RandomMinesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MinesGame {

    final GameModel service;
    private final Logger logger = LoggerFactory.getLogger(MinesGame.class);

    private final Map<GameModel.GameStatus, String> messages = new HashMap<>();

    public MinesGame(int maxX, int maxY, int mines) {
        service = new GameModel(maxX, maxY, mines, new RandomMinesStrategy());

        messages.put(GameModel.GameStatus.WIN, "\nYou won!!! :D");
        messages.put(GameModel.GameStatus.LOST, "\n It was bomb! sorry :'(");
    }

    private void drawGame() {
        Grid game = service.getGrid();
        for (int y = 1; y <= game.getMaxCoordinate().getCoordY(); y++) {
            System.out.print(y + " ");
            for (int x = 1; x <= game.getMaxCoordinate().getCoordX(); x++) {
                System.out.print(getCellPrint(game.getCellAt(new Coordinate(x, y))));
            }
            System.out.println();
        }
    }

    private String getCellPrint(Cell cell) {
        if (cell.getCellStatus() == Cell.CellStatus.UNCOVERED)
            return cell.isMinePlanted() ? "@" : "" + cell.getAdjacentMines();
        return cell.getCellStatus().getRepresentation();
    }

    public Coordinate transformToCoordinate(String line) {
        String regex = "[0-9]*,[0-9]*";
        if (line.matches(regex)) {
            int x = Integer.parseInt(line.split(",")[0]);
            int y = Integer.parseInt(line.split(",")[1]);
            return new Coordinate(x, y);
        }
        return null;
    }


    public void play(BufferedReader in) {
        drawGame();
        while (service.isGamePlaying()) {
            try {
                System.out.println("Cell to uncover: x,y ");
                Coordinate uncover = transformToCoordinate(in.readLine());
                try {
                    service.playCoordinate(uncover);
                } catch (IllegalPositionException e) {
                    System.out.println("\n bad coordinate, please enter a valid one");
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            drawGame();
        }
        final GameModel.GameStatus gameStatus = service.getGameStatus();
        System.out.println(messages.getOrDefault(gameStatus, ""));
    }

}
