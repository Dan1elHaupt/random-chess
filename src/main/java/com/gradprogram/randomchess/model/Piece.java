package com.gradprogram.randomchess.model;

import lombok.Getter;

@Getter
public abstract class Piece {

  private final boolean white;

  public Piece(boolean white) {
    this.white = white;
  }

  public boolean invalidEndSquare(Square start, Square end) {
    if (end.getPiece() == null) {
      return false;
    }
    if ((start.getX() == end.getX()) && (start.getY() == end.getY())) {
      return true;
    }
    return (end.getPiece().isWhite() == start.getPiece().isWhite());
  }

  public boolean noPiecesInTheWay(Board board, Square start, Square end) {
    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    if (xDiff == 0) {
      if (yDiff == 1) {
        return true;
      } else {
        int x = start.getX();
        int first = Math.min(start.getY(), end.getY());
        int last = Math.max(start.getY(), end.getY());
        for (int y = first + 1; y < last; y++) {
          if (board.getSquares()[x][y].getPiece() != null) {
            return false;
          }
        }
      }
    } else if (yDiff == 0) {
      if (xDiff == 1) {
        return true;
      } else {
        int y = start.getY();
        int first = Math.min(start.getX(), end.getX());
        int last = Math.max(start.getX(), end.getX());
        for (int x = first + 1; x < last; x++) {
          if (board.getSquares()[x][y].getPiece() != null) {
            return false;
          }
        }
      }
    } else {
      if (xDiff == 1) {
        return true;
      } else {
        // TODO logic for diagonal
      }
    }
    return true;
  }

  public boolean kingNotInCheck(Board board, Square start, Square end) {
    // TODO logic
    return true;
  }

  public abstract boolean legalMovePattern(Board board, Square start, Square end);

}
