package com.gradprogram.randomchess.model;

public class Pawn extends Piece {

  public Pawn(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    // TODO double move logic and dont allow backwards moves
    if ((xDiff == 0) && (yDiff == 1)) {
      return ((end.getPiece() == null) && board.notInCheckAfterMove(start, end));
    } else if ((xDiff == 1) && (yDiff == 1)) {
      if (end.getPiece() == null) {
        // TODO en passant logic here
        return false;
      }
      return ((end.getPiece().isWhite() != start.getPiece().isWhite())
          && board.notInCheckAfterMove(start, end));
    }
    return false;
  }
}