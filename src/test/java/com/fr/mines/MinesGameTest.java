package com.fr.mines;

import com.fr.mines.domain.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;


public class MinesGameTest {

    @Test
    public void should_transform_to_coordinate() {
        MinesGame game = new MinesGame(3, 3, 1);
        Coordinate coordinate = game.transformToCoordinate("1,3");
        assertEquals(coordinate.getCoordX(), 1);
        assertEquals(coordinate.getCoordY(), 3);
    }

    @Test
    public void should_not_transform_to_coordinate() {
        MinesGame game = new MinesGame(3, 3, 1);
        Coordinate coordinate = game.transformToCoordinate("aaa");
        assertNull(coordinate);
    }

    @Test
    public void should_get_valid_coordinate() {
        MinesGame game = new MinesGame(3, 3, 1);
        assertTrue(game.isValidCoordinate(new Coordinate(1, 1)));
        assertTrue(game.isValidCoordinate(new Coordinate(1, 2)));
        assertTrue(game.isValidCoordinate(new Coordinate(1, 3)));
        assertTrue(game.isValidCoordinate(new Coordinate(2, 1)));
        assertTrue(game.isValidCoordinate(new Coordinate(2, 2)));
        assertTrue(game.isValidCoordinate(new Coordinate(2, 3)));
        assertTrue(game.isValidCoordinate(new Coordinate(3, 1)));
        assertTrue(game.isValidCoordinate(new Coordinate(3, 2)));
        assertTrue(game.isValidCoordinate(new Coordinate(3, 3)));
    }

    @Test
    public void should_get_not_valid_coordinate() {
        MinesGame game = new MinesGame(3, 3, 1);
        assertFalse(game.isValidCoordinate(new Coordinate(0, 0)));
        assertFalse(game.isValidCoordinate(new Coordinate(0, 1)));
        assertFalse(game.isValidCoordinate(new Coordinate(4, 1)));
    }

    @Test
    public void should_get_not_valid_coordinate_when_null() {
        MinesGame game = new MinesGame(3, 3, 1);
        assertFalse(game.isValidCoordinate(null));
    }
}
