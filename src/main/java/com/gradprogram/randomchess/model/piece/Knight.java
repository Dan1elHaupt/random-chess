package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;

public class Knight extends Piece {

  public Knight(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end) {
    if (!Valid.validSquareLocation(end)) {
      return false;
    }

    int xDiff = Math.abs(start.x() - end.x());
    int yDiff = Math.abs(start.y() - end.y());
    return (xDiff * yDiff) == 2;
  }

  @Override
  public String toString() {
    return super.toString() + "N";
  }
}
