package com.gradprogram.randomchess.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {

  private final Square[][] squares;

  @Setter
  private Square whiteKing;

  @Setter
  private Square blackKing;

  public Board() {
    squares = new Square[8][8];

    // White pieces
    squares[0][0] = new Square(new Rook(true), 0, 0);
    squares[1][0] = new Square(new Knight(true), 1, 0);
    squares[2][0] = new Square(new Bishop(true), 2, 0);
    squares[3][0] = new Square(new Queen(true), 3, 0);
    squares[4][0] = new Square(new King(true), 4, 0);
    squares[5][0] = new Square(new Bishop(true), 5, 0);
    squares[6][0] = new Square(new Knight(true), 6, 0);
    squares[7][0] = new Square(new Rook(true), 7, 0);

    // Black pieces
    squares[0][7] = new Square(new Rook(false), 0, 7);
    squares[1][7] = new Square(new Knight(false), 1, 7);
    squares[2][7] = new Square(new Bishop(false), 2, 7);
    squares[3][7] = new Square(new Queen(false), 3, 7);
    squares[4][7] = new Square(new King(false), 4, 7);
    squares[5][7] = new Square(new Bishop(false), 5, 7);
    squares[6][7] = new Square(new Knight(false), 6, 7);
    squares[7][7] = new Square(new Rook(false), 7, 7);

    for (int x = 0; x < 8; x++) {
      // Pawns
      squares[x][1] = new Square(new Pawn(true), x, 1);
      squares[x][6] = new Square(new Pawn(false), x, 6);

      // Empty squares
      squares[x][2] = new Square(null, x, 2);
      squares[x][3] = new Square(null, x, 3);
      squares[x][4] = new Square(null, x, 4);
      squares[x][5] = new Square(null, x, 5);
    }
    whiteKing = squares[4][0];
    blackKing = squares[4][7];
  }

  public void printBoard() {
    for (int y = 7; y >= 0; y--) {
      System.out.print(y + 1 + " ");
      for (int x = 0; x < 8; x++) {
        Piece p = squares[x][y].getPiece();
        if (p == null) {
          System.out.print("|  ");
        } else {
          System.out.print("|" + (p.isWhite() ? "W" : "B"));
          if (p instanceof Pawn) {
            System.out.print("P");
          } else if (p instanceof Rook) {
            System.out.print("R");
          } else if (p instanceof Knight) {
            System.out.print("N");
          } else if (p instanceof Bishop) {
            System.out.print("B");
          } else if (p instanceof Queen) {
            System.out.print("Q");
          } else if (p instanceof King) {
            System.out.print("K");
          } else {
            System.out.print(" ");
          }
        }
      }
      System.out.println("|");
    }
    System.out.println("   a  b  c  d  e  f  g  h");
  }

  public boolean notInCheckAfterKingMove(Square start, Square end) {
    Piece king = start.getPiece();
    Piece oldEndPiece = end.getPiece();

    end.setPiece(king);
    start.setPiece(null);
    boolean notInCheck =  notInCheck(end);
    start.setPiece(king);
    end.setPiece(oldEndPiece);

    return notInCheck;
  }

  public boolean notInCheckAfterMove(Square start, Square end) {
    Square king;
    Piece oldEndPiece = end.getPiece();
    Piece movedPiece = start.getPiece();

    if (movedPiece.isWhite()) {
      king = this.getWhiteKing();
    } else {
      king = this.getBlackKing();
    }
    end.setPiece(start.getPiece());
    start.setPiece(null);
    boolean notInCheck = notInCheck(king);
    start.setPiece(end.getPiece());
    end.setPiece(oldEndPiece);

    return notInCheck;
  }

  private boolean notInCheck(Square king) {
    boolean kingIsWhite = king.getPiece().isWhite();
    for (Square[] squareRow : this.getSquares()) {
      for (Square square : squareRow) {
        Piece piece = square.getPiece();
        if ((piece != null) && (piece.isWhite() != kingIsWhite)) {
          if (piece.legalMovePattern(this, square, king)) {
            return false;
          }
        }
      }
    }
    return true;
  }

}
