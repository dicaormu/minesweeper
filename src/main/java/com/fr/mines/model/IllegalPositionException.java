package com.fr.mines.model;


import com.fr.mines.domain.Coordinate;

public class IllegalPositionException extends RuntimeException {
    public IllegalPositionException(Throwable cause) {
        super(cause);
    }
}
