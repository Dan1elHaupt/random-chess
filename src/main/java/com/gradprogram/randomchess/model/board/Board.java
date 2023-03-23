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

@Getter
public class Board {

  private final Piece[][] squares;

  public List<Piece> whitePieces;

  public List<Piece> blackPieces;

  @Setter
  private Point whiteKing;

  @Setter
  private Point blackKing;

  public Board() {
    whitePieces = new ArrayList<>();
    blackPieces = new ArrayList<>();
    squares = new Piece[8][8];

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

      squares[x][0] = whitePieces.get(x);
      squares[x][1] = whitePieces.get(x + 8);
      squares[x][2] = null;
      squares[x][3] = null;
      squares[x][4] = null;
      squares[x][5] = null;
      squares[x][6] = blackPieces.get(x + 8);
      squares[x][7] = blackPieces.get(x);
    }

    whiteKing = new Point(4, 0);
    blackKing = new Point(4, 7);
  }

  public void printBoard() {
    for (int y = 7; y >= 0; y--) {
      System.out.println("    ___ ___ ___ ___ ___ ___ ___ ___");
      System.out.print(y + 1 + " ");
      for (int x = 0; x < 8; x++) {
        String outpuString = squares[x][y] == null ? " |  " : " | " + squares[x][y];
        System.out.print(outpuString);
      }
      System.out.println(" | ");
    }
    System.out.println("    ___ ___ ___ ___ ___ ___ ___ ___");
    System.out.println("     a   b   c   d   e   f   g   h");
  }

    public boolean isLegalMove(Point start, Point end, boolean isWhite, Move previousMove, boolean verbose, boolean firstIteration) {
        Piece startingPiece = getPiece(start);
        if (startingPiece == null || startingPiece.isWhite() != isWhite) {
            if (verbose) {
              System.out.println("Illegal move: must move one of your own pices.");
            }
            return false;
        }
        if (startingPiece.legalMovePattern(start, end, verbose) && notInCheckAfterMove(start, end, verbose, firstIteration)) {
            if (startingPiece instanceof King) {
                return canKingMove(start, end, verbose, firstIteration);
            } else if (startingPiece instanceof Pawn) {
                return canPawnMove(start, end, previousMove, verbose, firstIteration);
            } else if (startingPiece instanceof Knight) {
                return canKnightMove(start, end, verbose);
            } else {
                return canMove(start, end, verbose);
            }
        }
        return false;
    }

    private boolean canKnightMove(Point start, Point end, boolean verbose) {
        Piece endLocationPiece = getPiece(end);
        if (endLocationPiece != null && (endLocationPiece.isWhite() == getPiece(start).isWhite())) {
          if (verbose) {
            System.out.println("Illegal move: cannot capture your own piece.");
          }
          return false;
        }
        return  true;
    }

    private boolean canKingMove(Point start, Point end, boolean verbose, boolean firstIteration) {
        if (castlingMove(start, end)) {
            if (squares[start.x()][start.y()].isHasMoved()) {
              if (verbose) {
                System.out.println("Illegal move: cannot castle as your king has already moved.");
              }
              return false;
            }
            final int rookX = (end.x() == 2) ? 0 : 7;
            if (!(squares[rookX][start.y()] instanceof Rook) || (squares[rookX][start.y()].isHasMoved())) {
              if (verbose) {
                System.out.println("Illegal move: must have a rook that hasn't moved to castle to.");
              }
              return false;
            }
            if (piecesInTheWay(start, end)) {
              if (verbose) {
                System.out.println("Illegal move: cannot castle through other pieces.");
              }
                return false;
            }
            boolean piecesUnderAttack = Point.getBetween(start, end).map(point -> this.notInCheckAfterMove(start, point, verbose, firstIteration)).reduce(false, (a, b) -> a || b);
            if ( piecesUnderAttack) {
              if (verbose) {
                System.out.println("Illegal move: cannot castle through check.");
              }
                return false;
            }
        }
        return canMove(start, end, verbose);
    }

    private boolean canPawnMove(Point start, Point end, Move previousMove, boolean verbose, boolean firstIteration) {
        if (Valid.legalDiagonalMove(start, end)) {
            if (Math.abs(start.x() - end.x()) != 1) {
              if (verbose) {
                System.out.println("Illegal move: pawns can only capture one square diagonally.");
              }
              return false;
            }
            Piece endLocationPiece = getPiece(end);
            if (endLocationPiece == null) {
                if (previousMove == null) {
                  if (verbose) {
                    System.out.println("Illegal move: pawns can only move diagonally to capture.");
                  }
                  return false;
                }
                if (!enPassantMove(start, end, previousMove)) {
                  if (verbose) {
                    System.out.println("Illegal en passant move.");
                  }
                  return false;
                }
                return true;
            } else {
                if (endLocationPiece.isWhite() == getPiece(start).isWhite()) {
                  if (verbose) {
                    System.out.println("Illegal move: cannot capture your own piece.");
                  }
                  return false;
                }
                return true;
            }
        } else {
            return !piecesInTheWay(start, end) && getPiece(end) == null && this.notInCheckAfterMove(start, end, verbose ,firstIteration);
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

    private boolean canMove(Point start, Point end, boolean verbose) {
        Piece endLocationPiece = getPiece(end);
        if ((endLocationPiece != null) && (endLocationPiece.isWhite() == getPiece(start).isWhite())) {
          if (verbose) {
            System.out.println("Illegal move: cannot capture one of your own pieces.");
          }
          return false;
        }
        if (piecesInTheWay(start, end)) {
          if (verbose) {
            System.out.println("Illegal move: cannot travel through other pieces.");
          }
          return false;
        }
        return true;
    }

    public boolean castlingMove(Point start, Point end) {
        return Math.abs(start.x() - end.x()) > 1;
    }

  private boolean piecesInTheWay(Point start, Point end) {
    return !Point.getBetween(start, end).map(this::getPiece).map(Objects::isNull)
        .reduce(true, (a, b) -> a && b);
  }

  public Piece getPiece(Point point) {
      return squares[point.x()][point.y()];
  }

  public void setPiece(Point point, Piece piece) {
      squares[point.x()][point.y()] = piece;
  }

  public boolean notInCheckAfterMove(Point start, Point end, boolean verbose, boolean firstIteration) {
    Point king;

    Move previousMove = new Move(start, end);

    Piece movedPiece = getPiece(start);
    Piece oldEndPiece = getPiece(end);

    if (movedPiece.isWhite()) {
      if (movedPiece instanceof King) {
        this.setWhiteKing(end);
      }
      king = this.getWhiteKing();
    } else {
      if (movedPiece instanceof King) {
        this.setBlackKing(end);
      }
      king = this.getBlackKing();
    }

    movedPiece.setX(end.x());
    movedPiece.setY(end.y());

    setPiece(end, movedPiece);
    setPiece(start, null);

    boolean notInCheck = notInCheck(king, previousMove, verbose, firstIteration);

    movedPiece.setX(start.x());
    movedPiece.setY(start.y());
    setPiece(start, movedPiece);
    setPiece(end, oldEndPiece);

    if (movedPiece.isWhite()) {
      if (movedPiece instanceof King) {
        this.setWhiteKing(start);
      }
    } else {
      if (movedPiece instanceof King) {
        this.setBlackKing(start);
      }
    }

    return notInCheck;
  }

  private boolean notInCheck(Point king, Move previuousMove, boolean verbose, boolean firstIteration) {
    if (!firstIteration) {
      return true;
    }
    
    List<Piece> opponentPieces;
    if (this.getPiece(king).isWhite()) {
      opponentPieces = blackPieces;
    } else {
      opponentPieces = whitePieces;
    }
    for (Piece piece : opponentPieces) {
      Point start = new Point(piece.getX(), piece.getY());
      if (this.isLegalMove(start, king, piece.isWhite(), previuousMove, false, false)) {
        if (verbose) {
          System.out.println("Illegal move: " + (this.getPiece(king).isWhite() ? "White in check"
              : "Black in check"));
        }
        return false;
      }
    }
    return true;
  }

}
