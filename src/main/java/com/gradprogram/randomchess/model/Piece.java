package com.gradprogram.randomchess.model;

import lombok.Getter;

@Getter
public abstract class Piece {

  private final boolean white;

  public Piece(boolean white) {
    this.white = white;
  }

  public boolean invalidEndSquare(Square start, Square end) {
    // check for actual movement
    if ((start.getX() == end.getX()) && (start.getY() == end.getY())) {
      return false;
    }
    return end.getPiece().isWhite() == start.getPiece().isWhite();
  }

  public boolean noPiecesInTheWay(Board board, Square start, Square end) {
    // TODO logic here
    return true;
  }

  public boolean kingNotInCheck(Board board, Square start, Square end) {
    // TODO logic here
    return true;
  }

  public abstract boolean canMove(Board board, Square start, Square end);

}
