package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;

public class Rook extends Piece {

  public Rook(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern(Point start, Point end) {
    if (!Valid.validSquareLocation(end)) {
      return false;
    }
    return Valid.legalHorizontalOrVerticalMove(start, end);
  }

  @Override
  public String toString() {
    return super.toString() + "R";
  }
}
