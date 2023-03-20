package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;

public class Queen extends Piece {

  public Queen(boolean white) {
    super(white);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end) {
    if (!Valid.validSquareLocation(end)) {
      return false;
    }

    return Valid.legalHorizontalOrVerticalMove(start, end) || Valid.legalDiagonalMove(start, end);
  }

  @Override
  public String toString() {
    return super.toString() + "Q";
  }
}
