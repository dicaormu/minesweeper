package com.fr.mines.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Random;


public class Coordinate {

    private int coordX;
    private int coordY;

    public Coordinate(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public static Coordinate getRandomCoordinate(Coordinate max) {
        Random random = new Random();
        return new Coordinate(random.nextInt(max.getCoordX()) + 1, random.nextInt(max.getCoordY()) + 1);
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcBuilder = new HashCodeBuilder();
        return hcBuilder.append(coordX).append(coordY).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
