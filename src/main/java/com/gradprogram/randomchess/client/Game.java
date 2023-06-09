package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Move;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import com.gradprogram.randomchess.model.piece.Bishop;
import com.gradprogram.randomchess.model.piece.King;
import com.gradprogram.randomchess.model.piece.Knight;
import com.gradprogram.randomchess.model.piece.Pawn;
import com.gradprogram.randomchess.model.piece.Piece;
import com.gradprogram.randomchess.model.piece.Queen;
import com.gradprogram.randomchess.model.piece.Rook;

import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

  public boolean makeMove(Point start, Point end, Scanner scanner) {
    if (start == null || end == null || !Valid.validSquareLocation(start) || !Valid.validSquareLocation(end)) {
      System.out.println("Illegal move: start or end coordinates are invalid.");
      return false;
    }
    if (start.equals(end)) {
      System.out.println("Illegal move: cannot move to the same square.");
      return false;
    }
    Move previousMove =  moves.size() != 0 ? moves.get(moves.size() - 1) : null;
    if (!board.isLegalMove(start, end, whiteToPlay,previousMove, true, true)) {
      return false;
    }

    updatePieceList(end);

    // move rook after castle or remove pawn taken by en passant
    if (board.getPiece(start) instanceof King) {
      handleCastle(start, end);
    } else if ((previousMove != null) && (board.getPiece(start) instanceof Pawn)) {
      handleEnPassant(start, end, previousMove);
    }

    movePieces(start, end);

    if (board.getPiece(end) instanceof Pawn) {
      handlePromotion(end, scanner);
    }

    updateKingLocation(end);

    whiteToPlay = !whiteToPlay;
    moves.add(new Move(start, end));

    return true;
  }

  private void updatePieceList(Point end) {
    Piece takenPiece = board.getPiece(end);
    if (takenPiece != null) {
      if (whiteToPlay) {
        board.blackPieces.remove(takenPiece);
      } else {
        board.whitePieces.remove(takenPiece);
      }
    }
  }

  private void movePieces(Point start, Point end) {
    board.getPiece(start).setHasMoved(true);
    board.setPiece(end, board.getPiece(start));
    board.setPiece(start, null);
    board.getPiece(end).setX(end.x());
    board.getPiece(end).setY(end.y());
  }

  private void updateKingLocation(Point end) {
    Piece destPiece = board.getPiece(end);
    if (destPiece instanceof King) {
      if (destPiece.isWhite()) {
        board.setWhiteKing(end);
      } else {
        board.setBlackKing(end);
      }
    }
  }

  private void handleCastle(Point start, Point end) {
    if (board.castlingMove(start, end)) {
      if (end.x() == 6) {
        Point rookPoint = new Point(7, start.y());
        Piece rook = board.getPiece(rookPoint);
        rook.setHasMoved(true);
        rook.setX(5);
        board.setPiece(new Point(5, start.y()), rook);
        board.setPiece(rookPoint, null);
      } else if (end.x() == 1) {
        Point rookPoint = new Point(0, start.y());
        Piece rook = board.getPiece(rookPoint);
        rook.setHasMoved(true);
        rook.setX(2);
        board.setPiece(new Point(2, start.y()), rook);
        board.setPiece(rookPoint, null);
      }
    }
  }

  private void handleEnPassant(Point start, Point end, Move previousMove) {
    if (board.enPassantMove(start, end, previousMove)) {
      Piece taken = board.getPiece(new Point(end.x(), start.y()));
      if (taken.isWhite()) {
        board.whitePieces.remove(taken);
      } else {
        board.blackPieces.remove(taken);
      }
      board.setPiece(new Point(end.x(), start.y()), null);
    }
  }

  private void handlePromotion(Point end, Scanner scanner) {
    if ((whiteToPlay && end.y() == 7) || (!whiteToPlay && end.y() == 0)) {
      System.out.println("What piece would you like to promote to (B, K, R, Q)?");
      String promotion = scanner.next();
      Piece promotedPiece = null;
      while (promotedPiece == null) {
        if (promotion.equalsIgnoreCase("B")) {
          promotedPiece = new Bishop(whiteToPlay, end.x(), end.y());
        } else if (promotion.equalsIgnoreCase("K")) {
          promotedPiece = new Knight(whiteToPlay, end.x(), end.y());
        } else if (promotion.equalsIgnoreCase("R")) {
          promotedPiece = new Rook(whiteToPlay, end.x(), end.y());
        } else if (promotion.equalsIgnoreCase("Q")) {
          promotedPiece = new Queen(whiteToPlay, end.x(), end.y());
        } else {
          System.out.println("Input must be of the form B, K, R or Q.");
          promotion = scanner.next();
        }
      }
      if (whiteToPlay) {
        board.whitePieces.remove(board.getPiece(end));
        board.whitePieces.add(promotedPiece);
      } else {
        board.blackPieces.remove(board.getPiece(end));
        board.blackPieces.add(promotedPiece);
      }
      board.setPiece(end, promotedPiece);
    }
  }


}
