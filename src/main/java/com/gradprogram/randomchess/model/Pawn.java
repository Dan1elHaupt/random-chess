package com.gradprogram.randomchess.model;

public class Pawn extends Piece {

  public Pawn(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end, boolean skipChecks) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    // TODO double move logic and dont allow backwards moves
    if ((xDiff == 0) && (yDiff == 1)) {
      return ((end.getPiece() == null) && kingNotInCheck(board, start, end));
    } else if ((xDiff == 1) && (yDiff == 1)) {
      if (end.getPiece() == null) {
        // TODO en passant logic here
        return false;
      }
      return ((end.getPiece().isWhite() != start.getPiece().isWhite())
          && (skipChecks || kingNotInCheck(board, start, end)));
    }
    return false;
  }
}
