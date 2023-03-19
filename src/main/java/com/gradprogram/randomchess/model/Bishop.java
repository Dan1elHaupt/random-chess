package com.gradprogram.randomchess.model;

public class Bishop extends Piece {

  public Bishop(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end, boolean skipChecks) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    if (xDiff == yDiff) {
      return (noPiecesInTheWay(board, start, end) && (skipChecks || kingNotInCheck(board, start, end)));
    }
    return false;
  }
}
