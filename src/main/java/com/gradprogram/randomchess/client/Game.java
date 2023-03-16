package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.Board;
import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.King;
import com.gradprogram.randomchess.model.Piece;
import com.gradprogram.randomchess.model.Square;
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

  public boolean makeMove(Square start, Square end) {

    Piece sourcePiece = start.getPiece();
    if (sourcePiece == null) {
      log.info("No piece selected for move");
      return false;
    }

    if (sourcePiece.isWhite() != whiteToPlay) {
      log.info("Not this players move");
      return false;
    }

    // valid move?
    if (!sourcePiece.legalMovePattern(board, start, end)) {
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

    // move piece from the stat box to end box
    end.setPiece(start.getPiece());
    start.setPiece(null);

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
