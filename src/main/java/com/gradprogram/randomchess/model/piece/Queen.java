package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Queen extends Piece {

  public Queen(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end, boolean verbose) {
    if (!Valid.legalHorizontalOrVerticalMove(start, end) && !Valid.legalDiagonalMove(start, end)) {
      if (verbose) {
        log.info("Illegal queen move.");
      }
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + "Q";
  }
}
