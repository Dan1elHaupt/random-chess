package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class King extends Piece {

  private boolean castled;
  private boolean hasMoved;

  public King(boolean white, int x, int y) {
    super(white, x, y);
    this.castled = false;
  }

  @Override
  public boolean legalMovePattern(Point start, Point end, boolean verbose) {
    int xDiff = Math.abs(start.x() - end.x());
    int yDiff = Math.abs(start.y() - end.y());
    if ((xDiff > 1 || yDiff > 1) && !canCastle(end)) {
        if (verbose) {
          System.out.println("Illegal king move.");
        }
        return false;
    }
    return true;
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
    return (this.isWhite() ? "\033[0;33m" : "\033[0;31m") + "K\033[0m";
  }

}