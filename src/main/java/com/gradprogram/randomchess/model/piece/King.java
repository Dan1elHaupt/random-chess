package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class King extends Piece {

  private boolean castled;
  private boolean hasMoved;

  public King(boolean white) {
    super(white);
    this.castled = false;
  }

  @Override
  public boolean legalMovePattern(Point start, Point end) {
    if (!Valid.validSquareLocation(end)) {
      return false;
    }
    int xDiff = Math.abs(start.x() - end.x());
    int yDiff = Math.abs(start.y() - end.y());
    if (xDiff <= 1 && yDiff <= 1) {
      return true;
    } else {
//      TODO Castling
    }
    return false;
  }

  private boolean canCastle(Board board, Square start, Square end) {
    // TODO logic
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + "K";
  }

}