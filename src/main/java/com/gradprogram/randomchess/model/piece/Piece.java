package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class Piece {

  private final boolean white;
  @Setter
  private int x;
  @Setter
  private int y;


  @Override
  public String toString() {
    return white ? "W" : "B";
  }

  public abstract boolean legalMovePattern(Point start, Point end, Board board);

}
