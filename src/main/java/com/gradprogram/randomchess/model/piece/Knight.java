package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Point;

public class Knight extends Piece {

  public Knight(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end, boolean verbose) {
    int xDiff = start.x() - end.x();
    int yDiff = start.y() - end.y();
    if (Math.abs(xDiff * yDiff) != 2) {
      if (verbose) {
        System.out.println("Illegal knight move.");
      }
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return (this.isWhite() ? "\033[0;33m" : "\033[0;31m") + "N\033[0m";
  }
}
