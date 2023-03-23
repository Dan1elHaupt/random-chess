package com.gradprogram.randomchess.model.board;

import com.gradprogram.randomchess.model.piece.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Square {

  @Setter
  private Piece piece;
  private int x;
  private int y;

  @Override
  public String toString() {
    return piece == null ? " |  " : " | " + piece;
  }
}
