package com.fr.mines.model;


import com.fr.mines.domain.Coordinate;

public class PositionOutOfBoundsException extends RuntimeException {
    Coordinate coordinate;

    public PositionOutOfBoundsException(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
