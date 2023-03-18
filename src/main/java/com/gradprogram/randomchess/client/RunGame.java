package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.board.Square;
import java.util.Arrays;
import java.util.Scanner;

public class RunGame {

  public static void startGame() {
    Point start, end;
    boolean x;
    int[] points;

    Game game = new Game();
    Scanner scanner = new Scanner(System.in);

    game.getBoard().printBoard();

    while (game.getGameStatus() == GameStatus.ACTIVE) {
      String input = scanner.nextLine();

      if (input.equals("resign")) {
        if (game.isWhiteToPlay()) {
          game.setGameStatus(GameStatus.BLACK_WIN);
        } else {
          game.setGameStatus(GameStatus.WHITE_WIN);
        }
        break;
      }

      points = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();

      start = new Point(points[0], points[1]);
      end = new Point(points[2], points[3]);
      x = game.makeMove(start, end);
      System.out.println(x);

      game.getBoard().printBoard();
    }

  }

}
