package com.gradprogram.randomchess.model;

public class Bishop extends Piece {

  public Bishop(boolean white, int x, int y) {
    super(white, x ,y);
  }

  @Override
  public boolean legalMovePattern(Board board, Square start, Square end) {
    if (invalidEndSquare(start, end)) {
      return false;
    }

    int xDiff = Math.abs(start.getX() - end.getX());
    int yDiff = Math.abs(start.getY() - end.getY());

    if (xDiff == yDiff) {
      return (noPiecesInTheWay(board, start, end) && board.notInCheckAfterMove(start, end));
    }
    return false;
  }
}
