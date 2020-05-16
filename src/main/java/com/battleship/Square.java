package com.battleship;

import java.io.Serializable;

public class Square implements Serializable {
	
    private Ship ship;
    private boolean assumption;
    private int row, column;
    private State state;

    public Square(int x, int y, boolean ownBoard) {
        this.ship = null;
        this.assumption = false;
        this.row = x;
        this.column = y;
        this.state = (ownBoard) ? State.NO_SHIP : State.UNKNOWN;
    }

    public boolean isShip() {
        return (ship != null);
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        this.state = State.CONTAINS_SHIP;
    }

    public boolean isGuessed() {
        return assumption;
    }

    public void setGuessed(boolean b) {
        if (ship != null)
            ship.gotHit();
        assumption = b;
    }

    public boolean guess() {
        assumption = true;
        if (ship != null) {
            ship.gotHit();
            return true;
        }
        return false;
    }

    public void update(boolean hit, Ship shipSunk) {
        this.assumption = true;
        if (this.state == State.UNKNOWN) {
            this.state = (hit) ? State.CONTAINS_SHIP : State.NO_SHIP;
        } else if (this.ship != null) {
            ship.gotHit();
        }
        if (this.ship == null) {
            this.ship = shipSunk;
        }
    }

    public int getX() {
        return row;
    }

    public int getY() {
        return column;
    }

    public State getState() {
        return state;
    }

    public enum State {
        CONTAINS_SHIP, NO_SHIP, UNKNOWN
    }

}