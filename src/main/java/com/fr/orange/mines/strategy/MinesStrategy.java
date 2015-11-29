package com.fr.orange.mines.strategy;


import com.fr.orange.mines.domain.Cell;
import com.fr.orange.mines.domain.Coordinate;

import java.util.Map;

public interface MinesStrategy {

     Map<Coordinate, Cell> generateCells(Coordinate max,int mines);

     //Map<Coordinate, Cell> plantMines(int mines);
}
