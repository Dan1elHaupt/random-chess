package com.gradprogram.randomchess.model;

public class Queen extends Piece {

  public Queen(boolean white) {
    super(white);
  }

  @Override
  public boolean canMove(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    if ((xDiff == 0) || (yDiff == 0) || (xDiff == yDiff)) {
      return (noPiecesInTheWay(board, start, end) && kingNotInCheck(board, start, end));
    }
    return false;
  }
}
