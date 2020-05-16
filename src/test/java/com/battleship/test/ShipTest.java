package com.battleship.test;


import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.battleship.BattleShipBoard;
import com.battleship.Ship;


public class ShipTest {

    @Test
    public void getTopLeftTest() {
        Assert.assertTrue("Is the top-left square returned",isTopLeftCoordsReturned());
    }

    private boolean isTopLeftCoordsReturned() {

        BattleShipBoard board = new BattleShipBoard();
        Ship ship1 = board.getShips().get(0);
        Ship ship2 = board.getShips().get(1);
        ship2.setVertical(true);

        board.placeShip(ship1, 2, 2);
        board.placeShip(ship2, 6, 4);

        return Arrays.equals(ship1.getTopLeft(), new int[] { 2, 2 })
                && Arrays.equals(ship2.getTopLeft(), new int[] { 6, 4 });
    }
}
