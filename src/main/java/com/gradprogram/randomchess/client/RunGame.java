package com.gradprogram.randomchess.client;

import com.gradprogram.randomchess.model.Square;

public class RunGame {

  public static void startGame() {

    Game game = new Game();

    game.getBoard().printBoard();

    Square start = game.getBoard().getSquares()[1][0];
    Square end = game.getBoard().getSquares()[2][2];
    boolean x = game.makeMove(start, end);
    System.out.println(x);

    game.getBoard().printBoard();

    start = game.getBoard().getSquares()[0][6];
    end = game.getBoard().getSquares()[0][5];
    x = game.makeMove(start, end);
    System.out.println(x);

    game.getBoard().printBoard();

    start = game.getBoard().getSquares()[2][2];
    end = game.getBoard().getSquares()[1][4];
    x = game.makeMove(start, end);
    System.out.println(x);

    game.getBoard().printBoard();

    start = game.getBoard().getSquares()[0][5];
    end = game.getBoard().getSquares()[1][4];
    x = game.makeMove(start, end);
    System.out.println(x);

    game.getBoard().printBoard();

    start = game.getBoard().getSquares()[1][1];
    end = game.getBoard().getSquares()[1][2];
    x = game.makeMove(start, end);
    System.out.println(x);

    game.getBoard().printBoard();

    start = game.getBoard().getSquares()[1][4];
    end = game.getBoard().getSquares()[1][3];
    x = game.makeMove(start, end);
    System.out.println(x);

    game.getBoard().printBoard();

  }

}
