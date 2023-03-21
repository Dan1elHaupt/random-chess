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

  public King(boolean white) {
    super(white);
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
      return canCastle(end);
    }
  }

  private boolean canCastle(Point end) {
    if (this.isHasMoved()) {
      return false;
    }
    return castlingEndLocations(end);
  }

  private boolean castlingEndLocations(Point end) {
    boolean correctYLocation = this.isWhite() ? end.y() == 0 : end.y() == 7;
    return correctYLocation && (end.x() == 2 || end.x() == 6);
  }



  @Override
  public String toString() {
    return super.toString() + "K";
  }

}