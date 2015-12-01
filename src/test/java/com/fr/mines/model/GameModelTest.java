package com.fr.mines.model;

import com.fr.mines.domain.Cell;
import com.fr.mines.domain.Coordinate;
import com.fr.mines.domain.Grid;
import com.fr.mines.strategy.MinesStrategy;
import com.fr.mines.strategy.RandomMinesStrategy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.fr.mines.domain.Cell.CellStatus.COVERED;
import static com.fr.mines.domain.Cell.CellStatus.UNCOVERED;
import static com.fr.mines.model.GameModel.GameStatus.*;
import static org.junit.Assert.*;


public class GameModelTest {

    @Test
    public void should_create_onexone_grid() {
        GameModel service = new GameModel(1, 1, 0, new RandomMinesStrategy());
        Grid grid = service.getGrid();
        assertEquals(grid.getMaxCoordinate().getCoordX(), 1);
        assertEquals(grid.getMaxCoordinate().getCoordY(), 1);
        assertEquals(grid.getMines(), 0);
        assertNotNull(grid.getCellAt(grid.getMaxCoordinate()));
    }

    @Test
    public void should_create_threexfour_grid() {
        GameModel service = new GameModel(3, 4, 1, new RandomMinesStrategy());
        Grid grid = service.getGrid();
        assertEquals(grid.getMaxCoordinate().getCoordX(), 3);
        assertEquals(grid.getMaxCoordinate().getCoordY(), 4);
        assertEquals(grid.getMines(), 1);
        assertNotNull(grid.getCellAt(grid.getMaxCoordinate()));
    }

    @Test
    public void should_get_valid_coordinate() {
        GameModel service = new GameModel(3, 3, 0, new RandomMinesStrategy());
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(1, 1)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(1, 2)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(1, 3)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(2, 1)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(2, 2)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(2, 3)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(3, 1)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(3, 2)));
        assertTrue(service.getGrid().isValidCoordinate(new Coordinate(3, 3)));
    }

    @Test
    public void should_get_not_valid_coordinate() {
        GameModel service = new GameModel(3, 3, 0, new RandomMinesStrategy());
        assertFalse(service.getGrid().isValidCoordinate(new Coordinate(0, 0)));
        assertFalse(service.getGrid().isValidCoordinate(new Coordinate(0, 1)));
        assertFalse(service.getGrid().isValidCoordinate(new Coordinate(4, 1)));
    }

    @Test(expected = NullPointerException.class)
    public void should_get_not_valid_coordinate_when_null() {
        GameModel service = new GameModel(3, 3, 0, new RandomMinesStrategy());
        service.getGrid().isValidCoordinate(null);
    }

    @Test
    public void should_discover_one_cell_with_mine() {
        GameModel service = new GameModel(3, 4, 1, new TestStrategy(testGrid(3, 4, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(1, 1));

        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(3, 4)).getCellStatus());
    }

    @Test
    public void should_discover_all_cells() {
        GameModel service = new GameModel(3, 4, 1, new TestStrategy(testGrid(3, 4, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(2, 2));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(3, 4)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
    }


    @Test
    public void should_discover_one_cell_with_count() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(2, 2));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(3, 3)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
    }

    @Test
    public void should_discover_all_cells_with_count() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(1, 3));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(3, 3)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
    }

    @Test
    public void should_discover_all_cells_with_count_bottom_corner() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(3, 3)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(1, 3));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(3, 3)).getCellStatus());
    }

    @Test
    public void should_win_the_game() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(1, 3));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(3, 3)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
        assertTrue(service.isGameWin());
    }

    @Test
    public void should_not_win() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(2, 3)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(1, 1));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 3)).getCellStatus());
        assertFalse(service.isGameWin());
        service.uncoverCell(new Coordinate(3, 1));
        assertFalse(service.isGameWin());
        service.uncoverCell(new Coordinate(3, 1));
        assertFalse(service.isGameWin());
    }

    @Test
    public void should_lose() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(2, 3)))));
        Grid grid = service.getGrid();
        service.uncoverCell(new Coordinate(1, 1));
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(2, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 2)).getCellStatus());
        assertEquals(UNCOVERED, grid.getCellAt(new Coordinate(1, 1)).getCellStatus());
        assertEquals(COVERED, grid.getCellAt(new Coordinate(1, 3)).getCellStatus());
        assertFalse(service.isGameWin());
        service.uncoverCell(new Coordinate(2, 3));
        assertTrue(service.isGameLost());
    }

    @Test
    public void should_play_coordinate_and_lose() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(2, 3)))));
        service.playCoordinate(new Coordinate(2, 3));
        assertEquals(service.getGameStatus(), LOST);
    }

    @Test
    public void should_play_coordinate_and_continue_playing() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(2, 3)))));
        service.playCoordinate(new Coordinate(1, 1));
        assertEquals(service.getGameStatus(), PLAYING);
    }

    @Test
    public void should_play_coordinate_and_win() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 1)))));
        service.playCoordinate(new Coordinate(3, 3));
        assertEquals(service.getGameStatus(), WIN);
    }

    @Test(expected = IllegalPositionException.class)
    public void should_play_coordinate_with_wrong_coordinate() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 2)))));
        service.playCoordinate(new Coordinate(5, 5));
        assertEquals(service.getGameStatus(), WIN);
    }

    @Test(expected = IllegalPositionException.class)
    public void should_play_coordinate_with_null_coordinate() {
        GameModel service = new GameModel(3, 3, 1, new TestStrategy(testGridWithCount(3, 3, Arrays.asList(new Coordinate(1, 2)))));
        service.playCoordinate(null);
        assertEquals(service.getGameStatus(), WIN);
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