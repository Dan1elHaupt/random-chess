package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;

public class Knight extends Piece {

  public Knight(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end, Board board) {
    if (!Valid.validSquareLocation(end)) {
      return false;
    }

    int xDiff = start.x() - end.x();
    int yDiff = start.y() - end.y();
    if (Math.abs(xDiff * yDiff) == 2) {
      return board.notInCheckAfterMove(start, end);
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + "N";
  }
}
