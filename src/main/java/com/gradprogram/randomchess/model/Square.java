package com.gradprogram.randomchess.model;

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

}
