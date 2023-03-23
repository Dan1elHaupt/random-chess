package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Move;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.movement.Valid;
import com.gradprogram.randomchess.model.piece.King;
import com.gradprogram.randomchess.model.piece.Pawn;
import com.gradprogram.randomchess.model.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Game {

  private final List<Move> moves;
  private Board board;
  private boolean whiteToPlay;
  private GameStatus gameStatus;

  public Game() {
    board = new Board();
    whiteToPlay = true;
    gameStatus = GameStatus.ACTIVE;
    moves = new ArrayList<>();
  }

  public boolean makeMove(Point start, Point end) {
    if ((start.x() == end.x()) && (start.y() == end.y())) {
      return false;
    }
    if (!Valid.validSquareLocation(start) || !Valid.validSquareLocation(end)) {
      return false;
    }
    Move previousMove = moves.size() != 0 ? moves.get(moves.size() - 1) : null;
    if (!board.isLegalMove(start, end, whiteToPlay, previousMove)) {
      log.info("Invalid move");
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

    updateKingLocation(end);

    whiteToPlay = !whiteToPlay;
    moves.add(new Move(start, end));

    return true;
  }

  public Point getRandomPointToMove() {
    Point point;
    Piece piece;
    List<Piece> pieces;
    List<Piece> piecesThatCanMove = new ArrayList<>();
    Random random = new Random();
    Move previousMove = moves.size() != 0 ? moves.get(moves.size() - 1) : null;

    if (whiteToPlay) {
      pieces = board.whitePieces;
    } else {
      pieces = board.blackPieces;
    }

    for (int i = 0; i < pieces.size(); i++) {
      piece = pieces.get(i);
      point = new Point(0, 0);
      while (point != null) {
        if (board.isLegalMove(piece.getPoint(), point, whiteToPlay, previousMove)) {
          piecesThatCanMove.add(piece);
          break;
        }
        point = getNextPoint(point);
      }
    }

    if (piecesThatCanMove.size() == 0) {
      return null;
    }

    return piecesThatCanMove.get(random.nextInt(piecesThatCanMove.size())).getPoint();
  }

  private Point getNextPoint(Point point) {
    int x = point.x();
    int y = point.y();

    if (x == 7) {
      if (y == 7) {
        return null;
      } else {
        x = 0;
        y++;
      }
    } else {
      x++;
    }

    point = new Point(x, y);
    if ((board.getPiece(point) != null) && (whiteToPlay == board.getPiece(point).isWhite())) {
      return getNextPoint(point);
    }
    return point;
  }

  private void updatePieceList(Point end) {
    Piece takenPiece = board.getSquare(end).getPiece();
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
    board.getSquare(end).setPiece(board.getPiece(start));
    board.getSquare(start).setPiece(null);
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
        board.getSquare(new Point(5, start.y())).setPiece(rook);
        board.getSquare(rookPoint).setPiece(null);
      } else if (end.x() == 1) {
        Point rookPoint = new Point(0, start.y());
        Piece rook = board.getPiece(rookPoint);
        rook.setHasMoved(true);
        rook.setX(2);
        board.getSquare(new Point(2, start.y())).setPiece(rook);
        board.getSquare(rookPoint).setPiece(null);
      }
    }
  }

  private void handleEnPassant(Point start, Point end, Move previousMove) {
    if (board.enPassantMove(start, end, previousMove)) {
      Piece taken = board.getSquare(new Point(end.x(), start.y())).getPiece();
      if (taken.isWhite()) {
        board.whitePieces.remove(taken);
      } else {
        board.blackPieces.remove(taken);
      }
      board.getSquare(new Point(end.x(), start.y())).setPiece(null);
    }
  }

}
