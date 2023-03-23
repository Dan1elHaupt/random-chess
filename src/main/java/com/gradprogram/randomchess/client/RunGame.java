package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Move;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.piece.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunGame {

  private static int[] coordinateConverter(String input) {
    char[] letterCoordinates = input.toCharArray();

    int[] numberCoordinates = {letterCoordinates[0] - 97, Character.getNumericValue(letterCoordinates[1]) - 1};

    return numberCoordinates;
  };

  public static boolean validInput(String input) {
    input = input.toLowerCase();
    if (input.length() == 2) {
      String letter1 = String.valueOf(input.charAt(0));
      Pattern pattern = Pattern.compile("^[a-h]+$");
      Matcher matcher = pattern.matcher(letter1);
      boolean digit = Character.isDigit(input.charAt(1));
      return (matcher.matches() && digit);
    }
    return false;
  }

  public static Point startingPoint(Game game) {
    List<Piece> currentPieces;
    if( game.isWhiteToPlay() ) {
      currentPieces = game.getBoard().whitePieces;
    }
    else {
      currentPieces = game.getBoard().blackPieces;
    }
    Collections.shuffle(currentPieces);

    System.out.println(currentPieces.get(0));

    for (Piece currentPiece : currentPieces) {
      for (int x = 0; x < 8; x++) {
        for (int y = 0; x < 8; x++) {
          Move previousMove =  game.getMoves().size() != 0 ? game.getMoves().get(game.getMoves().size() - 1) : null;
          if (game.getBoard().isLegalMove(new Point(currentPiece.getX(),currentPiece.getY()), new Point(x,y), game.isWhiteToPlay(), previousMove)) {
            System.out.println("HERE");
            return new Point(x,y);
          }
        }
      }
    }


    return null;
  }

  public static void startGame() {
    Point start, end;
    int[] points;

    Game game = new Game();
    Scanner scanner = new Scanner(System.in);

    game.getBoard().printBoard();

    while (game.getGameStatus() == GameStatus.ACTIVE) {
      start = startingPoint(game);
      String input = scanner.nextLine();
      if (!validInput(input)) {
        System.out.println("Please enter a valid block. Letter followed by a number, eg C2");
        continue;
      }

      if (input.equals("resign")) {
        if (game.isWhiteToPlay()) {
          game.setGameStatus(GameStatus.BLACK_WIN);
        } else {
          game.setGameStatus(GameStatus.WHITE_WIN);
        }
        break;
      }

      points = coordinateConverter(input);

      end = new Point(points[0], points[1]);
      game.makeMove(start, end);

      game.getBoard().printBoard();
    }

    scanner.close();

  }

}
