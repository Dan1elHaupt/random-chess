package com.gradprogram.randomchess.model;

public class Knight extends Piece {

  public Knight(boolean white, int x, int y) {
    super(white, x ,y);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());
    return (((xDiff * yDiff) == 2) && board.notInCheckAfterMove(start, end));
  }
}
