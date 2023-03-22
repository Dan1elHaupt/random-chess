package com.gradprogram.randomchess.model.board;

public record Move(Point start, Point end) {

    public boolean twoForward() {
        return start.x() == end.x() && Math.abs(start.y() - end.y()) == 2;
    }

}
