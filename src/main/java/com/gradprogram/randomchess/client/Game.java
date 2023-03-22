package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Move;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import com.gradprogram.randomchess.model.piece.King;
import com.gradprogram.randomchess.model.piece.Piece;
import com.gradprogram.randomchess.model.board.Square;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class Game {

  private Board board;
  private boolean whiteToPlay;
  private GameStatus gameStatus;
  private final List<Move> moves;

  public Game() {
    board = new Board();
    whiteToPlay = true;
    gameStatus = GameStatus.ACTIVE;
    moves = new ArrayList<>();
  }

  public boolean makeMove(Point start, Point end) {
    if (!Valid.validSquareLocation(start) || !Valid.validSquareLocation(end)) {
      return false;
    }
    Move previousMove =  moves.size() != 0 ? moves.get(moves.size() - 1) : null;
    if (!board.isLegalMove(start, end, whiteToPlay,previousMove)) {
      log.info("Invalid move");
      return false;
    }

    // remove taken piece from list
    Piece takenPiece = board.getSquare(end).getPiece();
    if (takenPiece != null) {
      if (whiteToPlay) {
        board.blackPieces.remove(takenPiece);
      } else {
        board.whitePieces.remove(takenPiece);
      }
    }

    // move piece from the stat box to end box
    board.getSquare(end).setPiece(board.getPiece(start));
    board.getSquare(start).setPiece(null);
    board.getPiece(end).setX(end.x());
    board.getPiece(end).setY(end.y());
    board.getPiece(start).setHasMoved(true);

    // update location of king
    Piece destPiece = board.getPiece(end);
    if (destPiece instanceof King) {
      if (destPiece.isWhite()) {
        board.setWhiteKing(end);
      } else {
        board.setBlackKing(end);
      }
    }

    whiteToPlay = !whiteToPlay;
    moves.add(new Move(start, end));

    return true;

  }

}
