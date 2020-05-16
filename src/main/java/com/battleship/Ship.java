package com.battleship;

import java.io.Serializable;
import java.util.ArrayList;

public class Ship implements Serializable {

    private ShipType shipType;
    private ArrayList<Square> squares;
    private boolean verticalPlacement;
    private int shipHealth;


    public Ship(ShipType type) {
        this.shipType = type;
        this.verticalPlacement = false;
        this.shipHealth = type.length;
        squares = new ArrayList<Square>();
    }

    public int getLength() {
        return shipType.length;
    }

    public ShipType getType() {
        return shipType;
    }

    public boolean isVertical() {
        return verticalPlacement;
    }

    public void setVertical(boolean b) {
        this.verticalPlacement = b;
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public void setSquare(Square square) {
        this.squares.add(square);
    }

    public void clearSquares() {
        squares.clear();
    }

    public void gotHit() {
        shipHealth--;
    }

    public boolean isSunk() {
        return (shipHealth == 0);
    }

    public void sink() {
        shipHealth = 0;
    }

    public int[] getTopLeft() {
        Square firstSquare = squares.get(0);
        int[] tl = { firstSquare.getX(), firstSquare.getY() };
        for (int i = 1; i < squares.size(); ++i) {
            Square s = squares.get(i);
            if (s.getX() < tl[0]) {
                tl[0] = s.getX();
            }
            if (s.getY() < tl[1]) {
                tl[1] = s.getY();
            }
        }
        return tl;
    }


    public enum ShipType {
        AIRCRAFT_CARRIER(5, "aircraft carrier"), BATTLESHIP(4, "battleship"), SUBMARINE(
                3, "submarine"), DESTROYER(3, "destroyer"), PATROL_BOAT(2,
                "patrol boat");

        private int length;
        private String name;

        ShipType(int length, String name) {
            this.length = length;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void updateSquareReferences(BattleShipBoard board) {
        ArrayList<Square> newSquares = new ArrayList<>();
        for (Square s : squares) {
            newSquares.add(board.getSquare(s.getX(), s.getY()));
        }
        this.squares = newSquares;
    }

}
