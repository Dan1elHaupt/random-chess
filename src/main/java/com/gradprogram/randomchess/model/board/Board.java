package com.gradprogram.randomchess.model.board;

import com.gradprogram.randomchess.model.movement.Valid;
import com.gradprogram.randomchess.model.piece.Bishop;
import com.gradprogram.randomchess.model.piece.King;
import com.gradprogram.randomchess.model.piece.Knight;
import com.gradprogram.randomchess.model.piece.Pawn;
import com.gradprogram.randomchess.model.piece.Piece;
import com.gradprogram.randomchess.model.piece.Queen;
import com.gradprogram.randomchess.model.piece.Rook;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Board {

  private final Square[][] squares;

  public List<Piece> whitePieces;

  public List<Piece> blackPieces;

  @Setter
  private Point whiteKing;

  @Setter
  private Point blackKing;

  public Board() {
    whitePieces = new ArrayList<>();
    blackPieces = new ArrayList<>();
    squares = new Square[8][8];

    // White pieces
    whitePieces.add(new Rook(true, 0, 0));
    whitePieces.add(new Knight(true, 1, 0));
    whitePieces.add(new Bishop(true, 2, 0));
    whitePieces.add(new Queen(true, 3, 0));
    whitePieces.add(new King(true, 4, 0));
    whitePieces.add(new Bishop(true, 5, 0));
    whitePieces.add(new Knight(true, 6, 0));
    whitePieces.add(new Rook(true, 7, 0));

    // Black pieces
    blackPieces.add(new Rook(false, 0, 7));
    blackPieces.add(new Knight(false, 1, 7));
    blackPieces.add(new Bishop(false, 2, 7));
    blackPieces.add(new Queen(false, 3, 7));
    blackPieces.add(new King(false, 4, 7));
    blackPieces.add(new Bishop(false, 5, 7));
    blackPieces.add(new Knight(false, 6, 7));
    blackPieces.add(new Rook(false, 7, 7));

    for (int x = 0; x < 8; x++) {
      whitePieces.add(new Pawn(true, x, 1));
      blackPieces.add(new Pawn(false, x, 6));

      squares[x][0] = new Square(whitePieces.get(x), x, 0);
      squares[x][1] = new Square(whitePieces.get(x + 8), x, 1);
      squares[x][2] = new Square(null, x, 2);
      squares[x][3] = new Square(null, x, 3);
      squares[x][4] = new Square(null, x, 4);
      squares[x][5] = new Square(null, x, 5);
      squares[x][6] = new Square(blackPieces.get(x + 8), x, 6);
      squares[x][7] = new Square(blackPieces.get(x), x, 7);
    }

    whiteKing = new Point(4, 0);
    blackKing = new Point(4, 7);
  }

  public void printBoard() {
    for (int y = 7; y >= 0; y--) {
      System.out.print(y + 1 + " ");
      for (int x = 0; x < 8; x++) {
        System.out.print(squares[x][y]);
      }
      System.out.println("|");
    }
    System.out.println("   a  b  c  d  e  f  g  h");
  }

    public boolean isLegalMove(Point start, Point end, boolean isWhite, Move previousMove) {
        Piece startingPosition = getPiece(start);
        if (startingPosition == null || startingPosition.isWhite() != isWhite) {
            return false;
        }

        if (startingPosition.legalMovePattern(start, end, this)) {
            if (startingPosition instanceof King) {
                return canKingMove(start, end);
            } else if (startingPosition instanceof Pawn) {
                return canPawnMove(start, end, previousMove);
            } else if (startingPosition instanceof Knight) {
                return canKnightMove(start, end);
            } else {
                return canMove(start, end);
            }
        }
        return false;
    }

    private boolean canKnightMove(Point start, Point end) {
//        TODO check logic
        Piece endLocationPiece = getPiece(end);
        boolean endPieceLocationAccessible = endLocationPiece == null ||
                (endLocationPiece.isWhite() != getPiece(start).isWhite());
        return  endPieceLocationAccessible;
    }

    private boolean canKingMove(Point start, Point end) {
        if (castlingMove(start, end)) {
            final int rookX;
            if (end.x() == 1) {
                rookX = 0;
            } else {
                rookX = 7;
            }
            if (!(squares[rookX][start.y()].getPiece() instanceof Rook && !squares[rookX][start.y()].getPiece().isHasMoved())) {
                return false;
            }
//                        TODO getcheck logic here
            if (piecesInTheWay(start, end)) {
                return false;
            }
        }
        return canMove(start, end);

    }

    private boolean canPawnMove(Point start, Point end, Move previousMove) {
        if (Valid.legalDiagonalMove(start, end)) {
            Piece endLocationPiece = getPiece(end);
            if (endLocationPiece == null) {
                return enPassantMove(start, end, previousMove);
            } else {
                return endLocationPiece.isWhite() != getPiece(start).isWhite();
            }
        } else {
            return !piecesInTheWay(start, end) && getPiece(end) == null;
//            TODO add check in here
        }
    }

    public boolean enPassantMove(Point start, Point end, Move previousMove) {
        return previousMovePawnTwoForward(previousMove) && startNextEndBehindPawnTake(start, end, previousMove);
    }

    private boolean startNextEndBehindPawnTake(Point start, Point end, Move previousMove) {
        Point previousMoveEnd = previousMove.end();
        boolean starrNextTo = previousMoveEnd.y() == start.y() && Math.abs(previousMoveEnd.x() - start.x()) == 1;
        boolean endBehind = previousMoveEnd.x() == end.x() && Math.abs(previousMoveEnd.y() - end.y()) == 1;
        return starrNextTo && endBehind;
    }

    private boolean previousMovePawnTwoForward(Move previousMove) {
        Piece piece = getPiece(previousMove.end());
        return piece instanceof Pawn && previousMove.twoForward();
    }

    private boolean canMove(Point start, Point end) {
//        TODO add check
        Piece endLocationPiece = getPiece(end);
        boolean endPieceLocationAccessible = (endLocationPiece == null) ||
                (endLocationPiece.isWhite() != getPiece(start).isWhite());
        return !piecesInTheWay(start, end) && endPieceLocationAccessible;
    }

    public boolean castlingMove(Point start, Point end) {
        return Math.abs(start.x() - end.x()) != 1;
    }

  private boolean piecesInTheWay(Point start, Point end) {
    return !Point.getBetween(start, end).map(this::getPiece).map(Objects::isNull)
        .reduce(true, (a, b) -> a && b);
  }

  public Piece getPiece(Point point) {
    return getSquare(point).getPiece();
  }

  public Square getSquare(Point point) {
    return squares[point.x()][point.y()];
  }

  public boolean notInCheckAfterMove(Point start, Point end) {
    Point king;

    Move previousMove = new Move(start, end);

    Square startSquare = this.getSquare(start);
    Square endSquare = this.getSquare(end);

    Piece movedPiece = startSquare.getPiece();
    Piece oldEndPiece = endSquare.getPiece();

    if (movedPiece.isWhite()) {
      if (movedPiece instanceof King) {
        this.setWhiteKing(end);
      }
      king = this.getWhiteKing();
      blackPieces.remove(oldEndPiece);
    } else {
      if (movedPiece instanceof King) {
        this.setBlackKing(end);
      }
      king = this.getBlackKing();
      whitePieces.remove(oldEndPiece);
    }

    movedPiece.setX(end.x());
    movedPiece.setY(end.y());
    endSquare.setPiece(movedPiece);
    startSquare.setPiece(null);

    boolean notInCheck = notInCheck(king, previousMove);

    movedPiece.setX(start.x());
    movedPiece.setY(start.y());
    startSquare.setPiece(movedPiece);
    endSquare.setPiece(oldEndPiece);

    if (movedPiece.isWhite()) {
      if (movedPiece instanceof King) {
        this.setWhiteKing(start);
      }
      if (oldEndPiece != null) {
        blackPieces.add(oldEndPiece);
      }
    } else {
      if (movedPiece instanceof King) {
        this.setBlackKing(start);
      }
      if (oldEndPiece != null) {
        whitePieces.add(oldEndPiece);
      }
    }

    return notInCheck;
  }

  private boolean notInCheck(Point king, Move previuousMove) {
    List<Piece> opponentPieces;
    if (this.getPiece(king).isWhite()) {
      opponentPieces = blackPieces;
    } else {
      opponentPieces = whitePieces;
    }
    for (int i = 0; i < opponentPieces.size(); i++) {
      Piece piece = opponentPieces.get(i);
      Point start = new Point(piece.getX(), piece.getY());

      if (this.isLegalMove(start, king, piece.isWhite(), previuousMove)) {
        log.info(this.getPiece(king).isWhite() ? "White in check" : "Black in check");
        return false;
      }
    }
    return true;
  }

}
