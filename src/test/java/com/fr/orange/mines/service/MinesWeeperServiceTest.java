package com.fr.orange.mines.service;

import com.fr.orange.mines.domain.Cell;
import com.fr.orange.mines.domain.Coordinate;
import com.fr.orange.mines.domain.Grid;
import com.fr.orange.mines.strategy.MinesStrategy;
import com.fr.orange.mines.strategy.RandomMinesStrategy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.fr.orange.mines.domain.Cell.Status.COVERED;
import static com.fr.orange.mines.domain.Cell.Status.UNCOVERED;
import static org.junit.Assert.*;


public class MinesWeeperServiceTest {

    @Test
    public void should_create_onexone_grid() {
        MinesWeeperService service = new MinesWeeperService(1, 1, 0, new RandomMinesStrategy());
        Grid grid = service.getGame();
        assertEquals(grid.getMaxCoordinate().getCoordX(), 1);
        assertEquals(grid.getMaxCoordinate().getCoordY(), 1);
        assertEquals(grid.getMines(), 0);
        assertNotNull(grid.getCell(grid.getMaxCoordinate()));
    }

    @Test
    public void should_create_threexfour_grid() {
        MinesWeeperService service = new MinesWeeperService(3, 4, 1, new RandomMinesStrategy());
        Grid grid = service.getGame();
        assertEquals(grid.getMaxCoordinate().getCoordX(), 3);
        assertEquals(grid.getMaxCoordinate().getCoordY(), 4);
        assertEquals(grid.getMines(), 1);
        assertNotNull(grid.getCell(grid.getMaxCoordinate()));
    }

    @Test
    public void should_discover_one_cell_with_mine() {
        MinesWeeperService service = new MinesWeeperService(3, 4, 1, new TestStrategy(testGrid(3, 4, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGame();
        assertFalse(service.uncoverCell(new Coordinate(1, 1)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(3, 4)).getStatus());
    }

    @Test
    public void should_discover_all_cells() {
        MinesWeeperService service = new MinesWeeperService(3, 4, 1, new TestStrategy(testGrid(3, 4, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGame();
        assertTrue(service.uncoverCell(new Coordinate(2, 2)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(3, 4)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
    }


    @Test
    public void should_discover_one_cell_with_count() {
        MinesWeeperService service = new MinesWeeperService(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGame();
        assertTrue(service.uncoverCell(new Coordinate(2, 2)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(3, 3)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
    }

    @Test
    public void should_discover_all_cells_with_count() {
        MinesWeeperService service = new MinesWeeperService(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGame();
        assertTrue(service.uncoverCell(new Coordinate(1, 3)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(3, 3)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
    }

    @Test
    public void should_discover_all_cells_with_count_bottom_corner() {
        MinesWeeperService service = new MinesWeeperService(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(3, 3)))));
        Grid grid = service.getGame();
        assertTrue(service.uncoverCell(new Coordinate(1, 3)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(3, 3)).getStatus());
    }

    @Test
    public void should_win_the_game() {
        MinesWeeperService service = new MinesWeeperService(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGame();
        assertTrue(service.uncoverCell(new Coordinate(1, 3)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(3, 3)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
        assertTrue(service.isGameWin());
    }

    @Test
    public void should_not_win() {
        MinesWeeperService service = new MinesWeeperService(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(2, 3)))));
        Grid grid = service.getGame();
        assertTrue(service.uncoverCell(new Coordinate(1, 1)));
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(2, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 2)).getStatus());
        assertEquals(UNCOVERED, grid.getCell(new Coordinate(1, 1)).getStatus());
        assertEquals(COVERED, grid.getCell(new Coordinate(1, 3)).getStatus());
        assertFalse(service.isGameWin());
        assertTrue(service.uncoverCell(new Coordinate(3, 1)));
        assertFalse(service.isGameWin());
        assertTrue(service.uncoverCell(new Coordinate(3, 1)));
        assertFalse(service.isGameWin());
    }

    private Map<Coordinate, Cell> testGrid(int maxX, int maxY, List<Coordinate> mines) {
        Map<Coordinate, Cell> cells = new HashMap<>();
        IntStream.range(0, maxX).forEach(x -> {
            IntStream.range(0, maxY).forEach(y -> {
                Cell cell = new Cell();
                Coordinate coord = new Coordinate(x + 1, y + 1);
                if (mines.contains(coord)) {
                    cell.plantMine();
                }
                cells.put(coord, cell);
            });
        });
        return cells;
    }

    private Map<Coordinate, Cell> testGridWithCount(int maxX, int maxY, List<Coordinate> mines) {
        Map<Coordinate, Cell> cells = testGrid(maxX, maxY, mines);
        mines.forEach(x -> {
            IntStream.rangeClosed(x.getCoordX() - 1, x.getCoordX() + 1).forEach(
                    i -> {
                        IntStream.rangeClosed(x.getCoordY() - 1, x.getCoordY() + 1).forEach(
                                j -> {
                                    cells.getOrDefault(new Coordinate(i, j), new Cell()).addAdjacentMine(1);
                                }
                        );
                    }
            );
        });
        return cells;
    }

    public static class TestStrategy implements MinesStrategy {
        final Map<Coordinate, Cell> testCase;

        TestStrategy(Map<Coordinate, Cell> testCase) {
            this.testCase = testCase;
        }

        @Override
        public Map<Coordinate, Cell> generateCells(Coordinate max, int mines) {
            return testCase;
        }
    }

}