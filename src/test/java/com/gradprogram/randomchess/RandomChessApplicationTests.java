package com.gradprogram.randomchess;

import com.gradprogram.randomchess.client.RunGame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RandomChessApplicationTests {

  @Test
  void input1() {
    String input = "X1";
    boolean valid = RunGame.validInput(input);
    assertEquals(false, valid);
  }

  @Test
  void input2() {
    String input = "a1";
    boolean valid = RunGame.validInput(input);
    assertEquals(true, valid);
  }

  @Test
  void input3() {
    String input = "G5";
    boolean valid = RunGame.validInput(input);
    assertEquals(true, valid);
  }

  @Test
  void input4() {
    String input = "H8";
    boolean valid = RunGame.validInput(input);
    assertEquals(true, valid);
  }

  @Test
  void input5() {
    String input = "i7";
    boolean valid = RunGame.validInput(input);
    assertEquals(false, valid);
  }

  @Test
  void input6() {
    String input = "H87";
    boolean valid = RunGame.validInput(input);
    assertEquals(false, valid);
  }

  @Test
  void input7() {
    String input = "7a";
    boolean valid = RunGame.validInput(input);
    assertEquals(false, valid);
  }

}