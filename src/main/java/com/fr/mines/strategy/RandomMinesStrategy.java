package com.fr.mines.strategy;

import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RandomMinesStrategy implements MinesStrategy {
    Map<Coordinate, Cell> cells;
    private Coordinate max;

    @Override
    public Map<Coordinate, Cell> generateCells(Coordinate max, int mines) {
        this.max = max;
        cells = new HashMap<>();
        IntStream.range(0, max.getCoordX()).forEach(x -> {
            IntStream.range(0, max.getCoordY()).forEach(y -> {
                Coordinate coord = new Coordinate(x + 1, y + 1);
                cells.put(coord, new Cell());
            });
        });
        return plantMines(mines);
    }

    private Map<Coordinate, Cell> plantMines(int mines) {
        int planted = 0;
        if (cells != null && mines < cells.size())
            while (planted < mines) {
                Coordinate coordToPlant = Coordinate.getRandomCoordinate(max);
                int couldPlant = cells.get(coordToPlant).plantMine();
                planted += couldPlant;
                addAdjacentMines(coordToPlant, couldPlant);
            }
        return cells;
    }

    private void addAdjacentMines(Coordinate coord, int planted) {
        for (int i = coord.getCoordX() - 1; i <= coord.getCoordX() + 1; i++) {
            for (int j = coord.getCoordY() - 1; j <= coord.getCoordY() + 1; j++) {
                Cell cell = cells.get(new Coordinate(i, j));
                if (cell != null) {
                    cell.addAdjacentMine(planted);
                }
            }
        }
    }
}
