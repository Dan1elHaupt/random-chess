package com.gradprogram.randomchess.model.piece;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import com.gradprogram.randomchess.model.movement.Valid;

public class Pawn extends Piece {

    private final int startY;

    public Pawn(boolean white) {
        super(white);
        if (white) {
          startY = 1;
        } else {
          startY = 6;
        }
    }

    @Override
    public boolean legalMovePattern( Point start, Point end) {
        if (!Valid.validSquareLocation(end)) {
            return false;
        }
        if (isAMoveForward(start, end)) {
            if (Valid.legalVerticalMove(start, end)) {
                int diff = Math.abs(start.y() - end.y());
                if (diff == 1) {
                  return true;
                } else if (diff == 2) {
                  return inStartPosition(start);
                } else {
                  return false;
                }
            } else if (Valid.legalDiagonalMove(start, end)) {
              return Math.abs(start.y() - end.y()) == 1;
            }
        }
        return false;
    }

    private boolean inStartPosition(Point current) {
      return current.y() == startY;
    }

    private boolean isAMoveForward(Point start, Point end) {
        if (isWhite()) {
            return start.y() < end.y();
        } else {
            return start.y() > end.y();
        }
    }

    @Override
    public String toString() {
        return super.toString() + "P";
    }
}
