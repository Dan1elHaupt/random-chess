package com.gradprogram.randomchess.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class King extends Piece {

  private boolean castled;

  public King(boolean white) {
    super(white);
    this.castled = false;
  }

  @Override
  public boolean canMove(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());
    if (xDiff > 1 && yDiff == 0) {
      return (canCastle(board, start, end) && kingNotInCheck(board, start, end));
    } else if (xDiff <= 1 && yDiff <= 1) {
      return kingNotInCheck(board, start, end);
    }
    return false;
  }

  private boolean canCastle(Board board, Square start, Square end) {
    // TODO logic here
    return false;
  }

}