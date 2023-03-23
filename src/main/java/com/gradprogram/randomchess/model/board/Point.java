package com.gradprogram.randomchess.model.board;

import com.google.common.collect.Streams;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Point(int x, int y) {

  public static Stream<Point> getBetween(Point start, Point end) {
    if (start.y() == end.y()) {
      return getBetween(start.x(), end.x()).map(xValue -> new Point(xValue, start.y()));
    } else if (start.x() == end.x()) {
      return getBetween(start.y(), end.y()).map(yValue -> new Point(start.x(), yValue));
    }
    return Streams.zip(getBetween(start.x(), end.x()), getBetween(start.y(), end.y()), Point::new);
  }

  private static Stream<Integer> getBetween(int start, int end) {
    boolean reverse = start > end;
    Stream<Integer> stream = IntStream.range(Math.min(start, end), Math.max(start, end)).skip(1)
        .boxed();
    if (reverse) {
      stream = stream.sorted((a, b) -> -Integer.compare(a, b));
    }
    return stream;
  }

  @Override
  public String toString() {
    return x + " " + y;
  }

}
