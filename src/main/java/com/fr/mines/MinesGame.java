package com.fr.mines;


import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Cell;
import com.fr.mines.domain.Grid;
import com.fr.mines.service.MinesWeeperService;
import com.fr.mines.strategy.RandomMinesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class MinesGame {

    private final Logger logger = LoggerFactory.getLogger(MinesGame.class);

    final MinesWeeperService service;


    public MinesGame(int maxX, int maxY, int mines) {
        service = new MinesWeeperService(maxX, maxY, mines, new RandomMinesStrategy());
    }

    public void drawGame() {
        Grid game = service.getGame();
        IntStream.rangeClosed(1, game.getMaxCoordinate().getCoordY()).forEach(
                y -> {
                    System.out.print(y + " ");
                    IntStream.rangeClosed(1, game.getMaxCoordinate().getCoordX()).forEach(
                            x -> {
                                System.out.print(getCellPrint(game.getCellAt(new Coordinate(x, y))));
                            }
                    );
                    System.out.println();
                }
        );
    }

    public String getCellPrint(Cell cell) {
        if (cell.getStatus() == Cell.Status.UNCOVERED)
            return cell.isMinePlanted() ? "@" : "" + cell.getAdjacentMines();
        return cell.getStatus().getRepresentation();
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

    public boolean isValidCoordinate(Coordinate coordinate) {
        if (coordinate != null && coordinate.getCoordX() <= service.getGame().getMaxCoordinate().getCoordX() &&
                coordinate.getCoordY() <= service.getGame().getMaxCoordinate().getCoordY())
            return true;
        return false;
    }

    public void play() {
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                drawGame();
                System.out.println("Cell to uncover: x,y ");
                Coordinate uncover = transformToCoordinate(in.readLine());
                if (isValidCoordinate(uncover)) {
                    if (!service.uncoverCell(uncover)) {
                        System.out.println("\n It was bomb! sorry :'(");
                        drawGame();
                        return;
                    }
                    if (service.isGameWin()) {
                        System.out.println("\n You won!!! :D");
                        drawGame();
                        return;
                    }
                } else
                    System.out.println("\n bad coordinate, please enter a valid one");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

}
