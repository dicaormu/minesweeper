package com.fr.mines.strategy;

import com.fr.mines.domain.Cell;
import com.fr.mines.domain.Coordinate;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;


public class RandomMinesStrategyTest {

    @Test
    public void should_generate_random_coordinate() {
        Coordinate coord = Coordinate.getRandomCoordinate(new Coordinate(3, 4));
        assertTrue(coord.getCoordX() > 0);
        assertTrue(coord.getCoordY() > 0);
        assertTrue(coord.getCoordX() <= 3);
        assertTrue(coord.getCoordX() <= 4);
    }

    @Test
    public void should_generate_onexone_cell() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(1, 1), 0);
        assertEquals(coordinateCellMap.size(), 1);
    }

    @Test
    public void should_generate_twoxone_cell() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(2, 1), 0);
        assertEquals(coordinateCellMap.size(), 2);
        assertNotNull(coordinateCellMap.get(new Coordinate(2, 1)));
        assertNotNull(coordinateCellMap.get(new Coordinate(1, 1)));
    }

    @Test
    public void should_generate_threexfour_cell() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 0);
        assertEquals(coordinateCellMap.size(), 12);
        assertNotNull(coordinateCellMap.get(new Coordinate(3, 1)));
        assertNotNull(coordinateCellMap.get(new Coordinate(3, 4)));
        assertNotNull(coordinateCellMap.get(new Coordinate(1, 4)));
        assertNull(coordinateCellMap.get(new Coordinate(4, 4)));
    }

    @Test
    public void should_plant_zero_mines() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 0);
        int plantedMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            if (cells.next().isMinePlanted())
                plantedMines++;
        }
        assertEquals(plantedMines, 0);
    }

    @Test
    public void should_plant_zero_mines_when_mines_greater_than_cells() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 13);
        int plantedMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            if (cells.next().isMinePlanted())
                plantedMines++;
        }
        assertEquals(plantedMines, 0);
    }

    @Test
    public void should_plant_zero_mines_when_mines_equals_cells() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 12);
        int plantedMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            if (cells.next().isMinePlanted())
                plantedMines++;
        }
        assertEquals(plantedMines, 0);
    }

    @Test
    public void should_plant_one_mine() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 1);
        int plantedMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            if (cells.next().isMinePlanted())
                plantedMines++;
        }
        assertEquals(plantedMines, 1);
    }

    @Test
    public void should_plant_two_mines() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 2);
        int plantedMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            if (cells.next().isMinePlanted())
                plantedMines++;
        }
        assertEquals(plantedMines, 2);
    }

    @Test
    public void should_plant_n_minus_one_mines() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(3, 4), 11);
        int plantedMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            if (cells.next().isMinePlanted())
                plantedMines++;
        }
        assertEquals(plantedMines, 11);
    }

    @Test
    public void should_plant_three_mines_to_verify_count() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(2, 2), 3);
        int adjMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            if (!cell.isMinePlanted())
                adjMines = cell.getAdjacentMines();
        }
        assertEquals(adjMines, 3);
    }

    @Test
    public void should_plant_one_mines_to_verify_count() {
        RandomMinesStrategy strategy = new RandomMinesStrategy();
        final Map<Coordinate, Cell> coordinateCellMap = strategy.generateCells(new Coordinate(2, 1), 1);
        int adjMines = 0;
        Iterator<Cell> cells = coordinateCellMap.values().iterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            if (!cell.isMinePlanted())
                adjMines = cell.getAdjacentMines();
        }
        assertEquals(adjMines, 1);
    }

}