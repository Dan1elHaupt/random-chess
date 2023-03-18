package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import lombok.Getter;

@Getter
public abstract class Piece {

  private final boolean white;

  public Piece(boolean white) {
    this.white = white;
  }

  @Override
  public String toString() {
    return white ? "W" : "B";
  }

  public abstract boolean legalMovePattern(Point start, Point end);

}
