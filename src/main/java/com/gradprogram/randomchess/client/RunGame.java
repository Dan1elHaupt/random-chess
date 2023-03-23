package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.GameStatus;
import com.gradprogram.randomchess.model.board.Board;
import com.gradprogram.randomchess.model.board.Move;
import com.gradprogram.randomchess.model.board.Point;
import com.gradprogram.randomchess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunGame {

  private static boolean inputValidator(String input) {
     if (input.equals("resign")) {
      return true;
     }
     if (input.length() != 2) {
      return false;
     }

    char[] letterCoordinates = input.toCharArray();
    if ((int) letterCoordinates[0] < 97 || (int) letterCoordinates[0] > 104) {
      return false;
    }
    if ((int) letterCoordinates[1] < 49 || (int) letterCoordinates[1] > 56) {
      return false;
    }
    return true;
  }

  private static int[] coordinateConverter(String input) {
    char[] letterCoordinates = input.toCharArray();

    int[] numberCoordinates = {letterCoordinates[0] - 97, Character.getNumericValue(letterCoordinates[1]) - 1};

    return numberCoordinates;
  };

  private static String coordinateConverter(int x, int y) {
    char[] letterCoordinates = new char[2];
    letterCoordinates[0] = Character.toChars(x + 97)[0];
    letterCoordinates[1] = Character.toChars(y + 49)[0];
    return new String(letterCoordinates);
  };

  private static Piece randomPiece(Game game, Board board, boolean whiteToPlay) {
    List<Move> previousMoves = game.getMoves();
    Move previousMove =  previousMoves.size() != 0 ? previousMoves.get(previousMoves.size() - 1) : null;
    List<Piece> candidatePieces = whiteToPlay ? board.whitePieces : board.blackPieces;
    List<Piece> validPieces = new ArrayList<>();
    Point startPoint;
    // for (Piece piece : candidatePieces) {
    for (int i = 0; i < candidatePieces.size(); i++) {
      Piece piece = candidatePieces.get(i);
      startPoint = new Point(piece.getX(), piece.getY());
      board_iteration_loop:
      for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
          if (piece.getX() != x || piece.getY() != y) {
            if (board.isLegalMove(startPoint, new Point(x, y), whiteToPlay, previousMove, false)) {
              validPieces.add(piece);
              break board_iteration_loop;
            }
          }
        }
      }
    }
    if (validPieces.size() < 1) {
      //TODO: checkmate logic
      log.info("CHECKMATE");
    }
    Random random = new Random();
    return validPieces.get(random.nextInt(validPieces.size()));
  }

  public static void startGame() {
    Point start, end;
    int[] points;

    Game game = new Game();
    Scanner scanner = new Scanner(System.in);
    String input;

    game.getBoard().printBoard();

    Piece randomPiece;

    while (game.getGameStatus() == GameStatus.ACTIVE) {

      randomPiece = randomPiece(game, game.getBoard(), game.isWhiteToPlay());
      // randomPiece = game.getBoard().getPiece(game.isWhiteToPlay() ? new Point(1, 1) : new Point(6, 6));
      System.out.println("You must move: " + randomPiece.toString() + " on square: " + coordinateConverter(randomPiece.getX(), randomPiece.getY()));

      System.out.print("Enter next move: ");
      input = scanner.nextLine();

      while (!inputValidator(input)) {
        log.info("Input must be of the form: e4 (Moves a piece to square e4)");
        input = scanner.nextLine();
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

      start = new Point(randomPiece.getX(), randomPiece.getY());
      end = new Point(points[0], points[1]);
      game.makeMove(start, end, scanner);

      System.out.println(game.getBoard().whitePieces);
      System.out.println(game.getBoard().blackPieces);

      game.getBoard().printBoard();
    }

    scanner.close();

  }

}
