package com.gradprogram.randomchess.model.movement;

import com.gradprogram.randomchess.model.board.Point;

public class Valid {

    public static boolean validSquareLocation(int x, int y) {
        return 0 <= x && x <= 7 && 0 <= y && y <= 7;
    }

    public static boolean validSquareLocation(Point point) {
        return validSquareLocation(point.x(), point.y());
    }

    public static boolean legalVerticalMove(int startX, int endX, int startY, int endY) {
        return startX == endX;
    }

    public static boolean legalVerticalMove(Point start, Point end) {
        return legalVerticalMove(start.x(), end.x(), start.y(), end.y());
    }

    public static boolean legalHorizontalMove(int startX, int endX, int startY, int endY) {
        return startY == endY;
    }

    public static boolean legalHorizontalMove(Point start, Point end) {
        return legalHorizontalMove(start.x(), end.x(), start.y(), end.y());
    }

    public static boolean legalDiagonalMove(int startX, int endX, int startY, int endY) {
        return Math.abs(startX - endX) == Math.abs(startY - endY);
    }

    public static boolean legalDiagonalMove(Point start, Point end) {
        return legalDiagonalMove(start.x(), end.x(), start.y(), end.y());
    }

    public static boolean legalHorizontalOrVerticalMove(int startX, int endX, int startY, int endY) {
        return legalHorizontalMove(startX, endX, startY, endY) || legalVerticalMove(startX, endX, startY, endY);
    }

    public static boolean legalHorizontalOrVerticalMove(Point start, Point end) {
        return legalHorizontalOrVerticalMove(start.x(), end.x(), start.y(), end.y());
    }


}
