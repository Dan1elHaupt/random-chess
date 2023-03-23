package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Rook extends Piece {

  public Rook(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern(Point start, Point end, boolean verbose) {
    if (!Valid.legalHorizontalOrVerticalMove(start, end)) {
      if (verbose) {
        log.info("Illegal rook move.");
      }
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + "R";
  }
}
