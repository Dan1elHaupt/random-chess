package com.gradprogram.randomchess.model;

public class CheckLogic {

  public static boolean notInCheckAfterKingMove(Board board, Square start, Square end) {
    Piece king = start.getPiece();
    Piece oldEndPiece = end.getPiece();

    end.setPiece(king);
    start.setPiece(null);
    boolean notInCheck =  notInCheck(board, end);
    start.setPiece(king);
    end.setPiece(oldEndPiece);

    return notInCheck;
  }

  public static boolean notInCheckAfterMove(Board board, Square start, Square end) {
    Square king;
    Piece oldEndPiece = end.getPiece();
    Piece movedPiece = start.getPiece();

    if (movedPiece.isWhite()) {
      king = board.getWhiteKing();
    } else {
      king = board.getBlackKing();
    }
    end.setPiece(start.getPiece());
    start.setPiece(null);
    boolean notInCheck = notInCheck(board, king);
    start.setPiece(end.getPiece());
    end.setPiece(oldEndPiece);

    return notInCheck;
  }

  private static boolean notInCheck(Board board, Square king) {
    boolean kingIsWhite = king.getPiece().isWhite();
    for (Square[] squareRow : board.getSquares()) {
      for (Square square : squareRow) {
        Piece piece = square.getPiece();
        if ((piece != null) && (piece.isWhite() != kingIsWhite)) {
          if (piece.legalMovePattern(board, square, king)) {
            return false;
          }
        }
      }
    }
    return true;
  }

}
