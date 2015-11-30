package com.fr.mines.strategy;

import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Cell;

import java.util.Map;

public interface MinesStrategy {

    Map<Coordinate, Cell> generateCells(Coordinate max, int mines);
}
