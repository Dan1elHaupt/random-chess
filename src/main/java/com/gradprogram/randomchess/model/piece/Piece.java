package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Piece {

  private final boolean white;
  private boolean hasMoved;

  public Piece(boolean white) {
    this.white = white;
    this.hasMoved = false;
  }

  @Override
  public String toString() {
    return white ? "W" : "B";
  }

  public abstract boolean legalMovePattern(Point start, Point end);

}
