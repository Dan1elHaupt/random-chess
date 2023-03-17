package com.gradprogram.randomchess.model;

public class Queen extends Piece {

  public Queen(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    if ((xDiff == 0) || (yDiff == 0) || (xDiff == yDiff)) {
      if (noPiecesInTheWay(board, start, end)) {
        return board.notInCheckAfterMove(start, end);
      }
    }
    return false;
  }
}
