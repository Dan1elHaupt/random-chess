package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Queen extends Piece {

  public Queen(boolean white, int x, int y) {
    super(white, x, y);
  }

  @Override
  public boolean legalMovePattern( Point start, Point end, Board board) {
    if (!Valid.validSquareLocation(end)) {
      return false;
    }

    if (Valid.legalHorizontalOrVerticalMove(start, end) || Valid.legalDiagonalMove(start, end)) {
      return board.notInCheckAfterMove(start, end);
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + "Q";
  }
}
