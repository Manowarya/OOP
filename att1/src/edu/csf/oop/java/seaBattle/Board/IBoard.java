package edu.csf.oop.java.seaBattle.Board;

import edu.csf.oop.java.seaBattle.Ship.Ship;

public interface IBoard {
    boolean shoot(int x, int y);
    boolean placeShip(Ship ship);
}
