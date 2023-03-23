package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Point;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunGame {

  private static boolean inputValidator(String input) {
     if (input.equals("resign")) {
      return true;
     }
     if (input.length() != 5) {
      return false;
     }

    char[] letterCoordinates = input.toCharArray();
    if ((int) letterCoordinates[0] < 97 || (int) letterCoordinates[0] > 104 || (int) letterCoordinates[3] < 97 || (int) letterCoordinates[3] > 104) {
      return false;
    }
    if ((int) letterCoordinates[1] < 49 || (int) letterCoordinates[1] > 56 || (int) letterCoordinates[4] < 49 || (int) letterCoordinates[4] > 56) {
      return false;
    }
    return true;
  }

  private static int[] stringToCoordinate(String input) {
    char[] letterCoordinates = input.toCharArray();

    int[] numberCoordinates = {letterCoordinates[0] - 97,
        Character.getNumericValue(letterCoordinates[1]) - 1};

    return numberCoordinates;
  }

  private static String coordinateToString(Point point) {
    return Character.toString(point.x() + 97) + (point.y() + 1);
  }

  public static void startGame() {
    boolean validMove;
    Point start, end;
    int[] points;

    Game game = new Game();
    Scanner scanner = new Scanner(System.in);

    game.getBoard().printBoard();

    while (game.getGameStatus() == GameStatus.ACTIVE) {
      validMove = false;
      start = game.getRandomPointToMove();

      if (start == null) {
        if (game.isWhiteToPlay()) {
          log.info("Black won by checkmate");
          game.setGameStatus(GameStatus.BLACK_WIN);
        } else {
          log.info("White won by checkmate");
          game.setGameStatus(GameStatus.WHITE_WIN);
        }
        break;
      }

      log.info("Player to move piece on " + coordinateToString(start));

      while (!validMove) {
        String input = scanner.nextLine();

        if (input.equals("resign")) {
          if (game.isWhiteToPlay()) {
            log.info("Black won by resignation");
            game.setGameStatus(GameStatus.BLACK_WIN);
          } else {
            log.info("White won by resignation");
            game.setGameStatus(GameStatus.WHITE_WIN);
          }
          break;
        }

        points = stringToCoordinate(input);
        end = new Point(points[0], points[1]);

        validMove = game.makeMove(start, end);
      }

      game.getBoard().printBoard();
    }

    scanner.close();

  }

}
