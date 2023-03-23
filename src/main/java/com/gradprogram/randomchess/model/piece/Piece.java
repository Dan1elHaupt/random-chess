package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Piece {

  private final boolean white;

  @Setter
  private int x;

  @Setter
  private int y;

  @Setter
  private boolean hasMoved;

  public Piece(boolean white, int x, int y) {
    this.white = white;
    this.x = x;
    this.y = y;
    this.hasMoved = false;
  }

  public abstract boolean legalMovePattern(Point start, Point end, boolean verbose);

}
