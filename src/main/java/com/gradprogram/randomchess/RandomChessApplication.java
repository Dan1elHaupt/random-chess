package com.gradprogram.randomchess;

import com.gradprogram.randomchess.client.RunGame;
import com.gradprogram.randomchess.model.Board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RandomChessApplication {

  public static void main(String[] args) {
    SpringApplication.run(RandomChessApplication.class, args);

    RunGame.startGame();
  }

}
