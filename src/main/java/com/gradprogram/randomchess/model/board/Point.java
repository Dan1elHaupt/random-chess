package com.gradprogram.randomchess.model.board;

import com.google.common.collect.Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Point(int x, int y){

    public static Stream<Point> getBetween(Point start, Point end) {
        if (start.y() == end.y()) {
            return getBetween(start.x(), end.x()).map(xValue -> new Point(xValue, start.y()));
        } else if (start.x() == end.x()) {
            return getBetween(start.y(), end.y()).map(yValue -> new Point(start.x(), yValue));
        }
        return Streams.zip(getDiagonal(start.x(), end.x()), getDiagonal(start.y(), end.y()),
            Point::new);
    }

    private static Stream<Integer> getBetween(int start, int end) {
        return IntStream.range(Math.min(start, end), Math.max(start, end)).skip(1).boxed();
    }

    private static Stream<Integer> getDiagonal(int start, int end) {
        Stream<Integer> coordinates;
        if (start < end) {
            coordinates = IntStream.range(start, end).skip(1).boxed();
        } else {
            List<Integer> listX = new ArrayList<>();
            for (int i = start-1; i > end; i--) {
                listX.add(i);
            }
            coordinates = listX.stream();
        }
        return coordinates;
    }

}
