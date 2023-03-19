package com.gradprogram.randomchess;

import com.gradprogram.randomchess.client.RunGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RandomChessApplication {

  public static void main(String[] args) {
    SpringApplication.run(RandomChessApplication.class, args);

    //move input syntax: e2 e3
    // (moves piece from square e2 to square e3)
    RunGame.startGame();
  }

}
