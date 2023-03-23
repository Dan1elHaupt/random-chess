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
    for (Piece piece : candidatePieces) {
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
      log.info("CHECKMATE / STALEMATE");
      return null;
    }
    Random random = new Random();
    return validPieces.get(random.nextInt(validPieces.size()));
  }

  private static boolean isStalemate(Game game, Board board) {
    Point kingPoint;
    List<Piece> enemyPieces;
    List<Move> previousMoves = game.getMoves();
    Move previousMove =  previousMoves.size() != 0 ? previousMoves.get(previousMoves.size() - 1) : null;
    if (game.isWhiteToPlay()) {
      kingPoint = board.getWhiteKing();
      enemyPieces = board.blackPieces;
    } else {
      kingPoint = board.getBlackKing();
      enemyPieces = board.whitePieces;
    }
    for (Piece piece : enemyPieces) {
      if (board.isLegalMove(new Point(piece.getX(), piece.getY()), kingPoint, game.isWhiteToPlay(), previousMove, false)) {
        return false;
      }
    }
    return true;
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
      if (randomPiece == null) {
        if (isStalemate(game, game.getBoard())) {
          game.setGameStatus(GameStatus.STALEMATE);
          System.out.println("Stalemate! Game is a draw.");
        } else if (game.isWhiteToPlay()) {
          game.setGameStatus(GameStatus.BLACK_WIN);
          System.out.println("\033[0;31mCheckmate! Black wins!\033[0m");
        } else {
          game.setGameStatus(GameStatus.WHITE_WIN);
          System.out.println("\033[0;33mCheckmate! White wins!\033[0m");
        }
        break;
      }
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
          System.out.println("\033[0;31mResignation! Black wins!\033[0m");
        } else {
          game.setGameStatus(GameStatus.WHITE_WIN);
          System.out.println("\033[0;33mResignation! White wins!\033[0m");
        }
        break;
      }

      points = coordinateConverter(input);
      start = new Point(randomPiece.getX(), randomPiece.getY());
      end = new Point(points[0], points[1]);

      //disables random piece selction, for testing purposes
      // char[] letterCoordinates = input.toCharArray();
      // int[] points = {letterCoordinates[0] - 97, Character.getNumericValue(letterCoordinates[1]) - 1,
      //   letterCoordinates[3] - 97, Character.getNumericValue(letterCoordinates[4]) - 1};
      // start = new Point(points[0], points[1]);
      // end = new Point(points[2], points[3]);

      game.makeMove(start, end, scanner);
      game.getBoard().printBoard();
    }

    scanner.close();

  }

}
