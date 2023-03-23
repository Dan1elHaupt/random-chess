package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bishop extends Piece {

  public Bishop(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end, boolean verbose) {
    if (!Valid.legalDiagonalMove(start, end)) {
      if (verbose) {
        log.info("Illegal bishop move.");
      }
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + "B";
  }
}
