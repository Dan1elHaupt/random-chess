package com.gradprogram.randomchess;

import com.gradprogram.randomchess.client.RunGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RandomChessApplication {

  public static void main(String[] args) {
    SpringApplication.run(RandomChessApplication.class, args);


    System.out.println("Welcome to Random Chess! The first ever chass game that's actually somewhat fun to play!\n" +
      "Each turn the piece you have to move gets chosen randomly, and all you have to do is decide where to put it.\n" +
      "Move input can be made in the form \"e4\", which will move the piece to the e4 square (assuming that is a legal move).\n" +
      "If all hope is lost, simply type \"resign\" to end your turn and concede the match. Good luck!");


    //move input syntax: e2 e3
    // (moves piece from square e2 to square e3)
    RunGame.startGame();
  }

}
