package com.battleship.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.battleship.BattleShipBoard;
import com.battleship.Ship;

public class BoardTest {

    @Test
    public void isTheShipPlacedTest() {
        Assert.assertTrue("Is the ship placed correctly", isShipCorrect());
    }

    @Test
    public void isTheShipPickedUpTest() {
        Assert.assertTrue("Has the ship been picked up", isShipRemoved());
    }

    @Test
    public void isTheShipPlacementEqualsTest() {
        Assert.assertTrue("Is ship placement equal",getShipPlacement());
    }

    @Test
    public void isSquareNearShipTest() {
        Assert.assertTrue("Is square near ship", isSquareNearShip());
    }

    private boolean isShipCorrect() {

        BattleShipBoard board = new BattleShipBoard();
        Ship ship = board.getShips().get(4); // gets a submarine
        ship.setVertical(true);
        board.placeShip(ship, 3, 2);

        for (int i = 0; i < BattleShipBoard.DIMENSIONS; i++) {
            for (int j = 0; j < BattleShipBoard.DIMENSIONS; j++) {
                if ((i == 3 && (j >= 2 && j <= 4) && !board.getSquare(i, j)
                        .isShip())
                        || (!(i == 3 && (j >= 2 && j <= 4)) && board.getSquare(
                                i, j).isShip())) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isShipRemoved() {

        // creates and places ship on the board
        BattleShipBoard board = new BattleShipBoard();
        Ship ship = board.getShips().get(0);
        board.placeShip(ship, 5, 5);

        // removes ship from board
        board.pickUpShip(ship);

        // checks if board is clear
        for (int i = 0; i < board.DIMENSIONS; i++) {
            for (int j = 0; j < board.DIMENSIONS; j++) {
                if (board.getSquare(i, j).isShip()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isGameOver() {

        BattleShipBoard board = new BattleShipBoard();

        // game should not be over yet
        if (board.gameOver()) {
            return false;
        }

        // sinks all ships
        for (Ship ship : board.getShips()) {
            ship.sink();
        }

        // should return true as all ships have been sunk
        return board.gameOver();
    }

    // checks whether shipPlacementEquals correctly tests board equality
    private boolean getShipPlacement() {
        BattleShipBoard board1 = new BattleShipBoard();
        BattleShipBoard board2 = new BattleShipBoard();

        // places ships on board1
        ArrayList<Ship> board1Ships = board1.getShips();
        board1.placeShip(board1Ships.get(0), 1, 1); // AIRCRAFT_CARRIER
        board1.placeShip(board1Ships.get(1), 0, 3); // BATTLESHIP
        board1.placeShip(board1Ships.get(2), 2, 5); // DESTROYER
        board1Ships.get(3).setVertical(true);
        board1.placeShip(board1Ships.get(3), 1, 7); // PATROL_BOAT
        board1Ships.get(4).setVertical(true);
        board1.placeShip(board1Ships.get(4), 8, 3); // SUBMARINE

        // places ships on board2 in same locations
        ArrayList<Ship> board2Ships = board2.getShips();
        board2.placeShip(board2Ships.get(0), 1, 1); // AIRCRAFT_CARRIER
        board2.placeShip(board2Ships.get(1), 0, 3); // BATTLESHIP
        board2.placeShip(board2Ships.get(2), 2, 5); // DESTROYER
        board2Ships.get(3).setVertical(true);
        board2.placeShip(board2Ships.get(3), 1, 7); // PATROL_BOAT
        board2Ships.get(4).setVertical(true);
        board2.placeShip(board2Ships.get(4), 8, 3); // SUBMARINE

        return board1.shipPlacementEquals(board2);
    }

    private boolean isSquareNearShip() {

        BattleShipBoard board = new BattleShipBoard();
        Ship ship = board.getShips().get(0); // AIRCRAFT_CARRIER
        board.placeShip(ship, 3, 2);

        for (int i = 0; i < board.DIMENSIONS; i++) {
            for (int j = 0; j < board.DIMENSIONS; j++) {
                // checks each square for correct near-ship status
                if ((i >= 2 && i <= 8 && j >= 1 && j <= 3 && !board
                        .isSquareNearShip(board.getSquare(i, j)))
                        || (!(i >= 2 && i <= 8 && j >= 1 && j <= 3) && board
                                .isSquareNearShip(board.getSquare(i, j)))) {
                    return false;
                }
            }
        }

        return true;
    }
}
