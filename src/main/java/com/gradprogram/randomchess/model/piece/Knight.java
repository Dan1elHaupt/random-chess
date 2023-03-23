package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Knight extends Piece {

  public Knight(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end, Board board, boolean verbose) {
    int xDiff = start.x() - end.x();
    int yDiff = start.y() - end.y();
    if (Math.abs(xDiff * yDiff) != 2) {
      if (verbose) {
        log.info("Illegal knight move.");
      }
      return false;
    }
    return board.notInCheckAfterMove(start, end);
  }

  @Override
  public String toString() {
    return super.toString() + "N";
  }
}
