package com.gradprogram.randomchess.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {

  private final Square[][] squares;

  public List<Piece> whitePieces;

  public List<Piece> blackPieces;

  @Setter
  private Square whiteKing;

  @Setter
  private Square blackKing;

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

    king.setX(end.getX());
    king.setY(end.getY());
    end.setPiece(king);
    start.setPiece(null);
    boolean notInCheck = notInCheck(end);
    king.setX(start.getX());
    king.setY(start.getY());
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

    movedPiece.setX(end.getX());
    movedPiece.setY(end.getY());
    end.setPiece(movedPiece);
    start.setPiece(null);
    boolean notInCheck = notInCheck(king);
    movedPiece.setX(start.getX());
    movedPiece.setY(start.getY());
    start.setPiece(movedPiece);
    end.setPiece(oldEndPiece);

    return notInCheck;
  }

  private boolean notInCheck(Square king) {
    List<Piece> opponentPieces;
    if (king.getPiece().isWhite()) {
      opponentPieces = blackPieces;
    } else {
      opponentPieces = whitePieces;
    }
    for (Piece piece : opponentPieces) {
      Square square = this.getSquares()[piece.getX()][piece.getY()];
      if (piece.legalMovePattern(this, square, king)) {
        return false;
      }
    }
    return true;
  }

}
