package com.gradprogram.randomchess.model.board;

import com.gradprogram.randomchess.model.movement.Valid;
import com.gradprogram.randomchess.model.piece.Bishop;
import com.gradprogram.randomchess.model.piece.King;
import com.gradprogram.randomchess.model.piece.Knight;
import com.gradprogram.randomchess.model.piece.Pawn;
import com.gradprogram.randomchess.model.piece.Piece;
import com.gradprogram.randomchess.model.piece.Queen;
import com.gradprogram.randomchess.model.piece.Rook;
import lombok.Getter;

import java.util.Objects;

public class Board {

    @Getter
    private final Square[][] squares;

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

    public boolean isLegalMove(Point start, Point end, boolean isWhite) {
        Piece startingPosition = getPiece(start);
        Piece endingPosition = getPiece(end);
        if (startingPosition == null) {
            return false;
        }
        if (startingPosition.isWhite() != isWhite) {
            return false;
        }
        if (startingPosition.legalMovePattern(start, end)) {
            if (!(startingPosition instanceof Knight) && piecesInTheWay(start, end)) {
                return false;
            }
            if (startingPosition instanceof Pawn) {
                if (Valid.legalDiagonalMove(start, end) && endingPosition == null) {
                    return false;
                } else {
                    return endingPosition == null;
                }
            }
            if (endingPosition != null) {
                return !(endingPosition instanceof King) || endingPosition.isWhite() != startingPosition.isWhite();
            }
            return true;
        }
        return false;

    }

    private boolean piecesInTheWay(Point start, Point end) {
        return Point.getBetween(start, end).map(this::getPiece).map(Objects::isNull).reduce(false, (a, b) -> a || b);
    }

    public Piece getPiece(Point point) {
        return getSquare(point).getPiece();
    }

    public Square getSquare(Point point) {
        return squares[point.x()][point.y()];
    }


}
