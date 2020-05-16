package com.battleship;

import java.io.Serializable;
import java.util.ArrayList;

public class BattleShipBoard implements Serializable {
	
    public static final int DIMENSIONS = 10;
    private Square[][] squares;
    private ArrayList<Ship> ships;

    public BattleShipBoard() {
        squares = new Square[DIMENSIONS][DIMENSIONS];

        for (int i = 0; i < DIMENSIONS; i++) {
            for (int j = 0; j < DIMENSIONS; j++) {
                squares[i][j] = new Square(i, j, true);
            }
        }
        ships = new ArrayList<Ship>();
        ships.add(new Ship(Ship.ShipType.AIRCRAFT_CARRIER));
        ships.add(new Ship(Ship.ShipType.BATTLESHIP));
        ships.add(new Ship(Ship.ShipType.DESTROYER));
        ships.add(new Ship(Ship.ShipType.PATROL_BOAT));
        ships.add(new Ship(Ship.ShipType.SUBMARINE));

    }

    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    public boolean placeShip(Ship ship, int x, int y) {
        int end = (ship.isVertical()) ? y + ship.getLength() - 1 : x
                + ship.getLength() - 1;
        if (x < 0 || y < 0 || end >= DIMENSIONS) {
            return false;
        }

        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.isVertical()) {
                if (squares[x][y + i].isShip())
                    return false;
            } else {
                if (squares[x + i][y].isShip())
                    return false;
            }
        }

        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.isVertical()) {
                squares[x][y + i].setShip(ship);
                ship.setSquare(squares[x][y + i]);
            } else if (!ship.isVertical()) {
                squares[x + i][y].setShip(ship);
                ship.setSquare(squares[x + i][y]);
            }
        }

        return true;
    }

    public void pickUpShip(Ship ship) {
        for (Square s : ship.getSquares()) {
            s.setShip(null);
        }
        ship.clearSquares();
    }

    public boolean gameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk())
                return false;
        }
        return true;
    }

    public void printBattleShipBoard(boolean clean) {
        for (int i = 0; i < DIMENSIONS; ++i) {
            for (int j = 0; j < DIMENSIONS; ++j) {
                Square s = squares[j][i];
                Ship ship = s.getShip();
                char ch = '-';
                if (s.isGuessed() && !clean
                        && s.getState() == Square.State.CONTAINS_SHIP) {
                    ch = 'X';
                } else if (s.isGuessed() && !clean) {
                    ch = 'O';
                } else if (ship != null) {
                    switch (ship.getType()) {
                    case AIRCRAFT_CARRIER:
                        ch = 'A';
                        break;
                    case BATTLESHIP:
                        ch = 'B';
                        break;
                    case SUBMARINE:
                        ch = 'S';
                        break;
                    case DESTROYER:
                        ch = 'D';
                        break;
                    case PATROL_BOAT:
                        ch = 'P';
                    }
                }
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }


    public boolean shipPlacementEquals(BattleShipBoard board) {
        for (int y = 0; y < DIMENSIONS; ++y) {
            for (int x = 0; x < DIMENSIONS; ++x) {
                Square s1 = this.getSquare(x, y);
                Square s2 = board.getSquare(x, y);
                if ((s1.isShip() != s2.isShip())) {
                    return false;
                }
                if (s1.getShip() != null && s2.getShip() != null
                        && s1.getShip().getType() != s2.getShip().getType()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSquareNearShip(Square square) {
        for (int x = square.getX() - 1; x <= square.getX() + 1; x++) {
            for (int y = square.getY() - 1; y <= square.getY() + 1; y++) {
                if (isCoordWithinBounds(x, y) && getSquare(x, y).isShip()
                        && !(x == square.getX() && y == square.getY())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCoordWithinBounds(int row, int column) {
        return (row >= 0 && row < 10 && column >= 0 && column < 10);
    }

}
