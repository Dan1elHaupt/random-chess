package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import com.gradprogram.randomchess.model.piece.King;
import com.gradprogram.randomchess.model.piece.Piece;
import com.gradprogram.randomchess.model.board.Square;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Game {

  private Board board;
  private boolean whiteToPlay;
  private GameStatus gameStatus;

  public Game() {
    board = new Board();
    whiteToPlay = true;
    gameStatus = GameStatus.ACTIVE;
  }

  public boolean makeMove(Point start, Point end) {
    if (!Valid.validSquareLocation(start) || !Valid.validSquareLocation(end)) {
      return false;
    }

    if (!board.isLegalMove(start, end, whiteToPlay)) {
      log.info("Invalid move");
      return false;
    }

    Piece destPiece = end.getPiece();
    if (destPiece instanceof King) {
      if (whiteToPlay) {
        setGameStatus(GameStatus.WHITE_WIN);
        System.out.println("White Won!");
      }
      else {
        setGameStatus(GameStatus.BLACK_WIN);
        System.out.println("Black Won!");
      }
    }

    // remove taken piece from list
    if (end.getPiece() != null) {
      if (whiteToPlay) {
        board.blackPieces.remove(end.getPiece());
      } else {
        board.whitePieces.remove(end.getPiece());
      }
    }

    // move piece from the stat box to end box
    end.setPiece(start.getPiece());
    start.setPiece(null);
    end.getPiece().setX(end.getX());
    end.getPiece().setY(end.getY());

    // update location of king
    if (end.getPiece() instanceof King) {
      if (end.getPiece().isWhite()) {
        board.setWhiteKing(end);
      } else {
        board.setBlackKing(end);
      }
    }

    whiteToPlay = !whiteToPlay;

    return true;

  }

}
