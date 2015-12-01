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


}
