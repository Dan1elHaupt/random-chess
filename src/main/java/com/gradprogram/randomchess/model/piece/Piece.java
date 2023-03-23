package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
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


  @Override
  public String toString() {
    return white ? "W" : "B";
  }

  public abstract boolean legalMovePattern(Point start, Point end, Board board, boolean verbose);

}
