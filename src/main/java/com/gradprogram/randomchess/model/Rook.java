package com.gradprogram.randomchess.model;

public class Rook extends Piece {

  public Rook(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    if ((xDiff == 0) ^ (yDiff == 0)) {
      return (noPiecesInTheWay(board, start, end) &&
          board.notInCheckAfterMove(start, end));
    }
    return false;
  }
}
