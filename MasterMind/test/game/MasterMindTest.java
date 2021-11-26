package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class MasterMindTest {

  MasterMind masterMind;

  @BeforeEach
  public void setup() {
    masterMind = new MasterMind();

    var selectedColors = List.of(Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.white);

    masterMind.setSelectedColors(selectedColors);
  }

  @Test
  public void Canary() {
    assertTrue(true);
  }

  @Test
  public void guessAllPositionalMatch() {
    var guess = List.of(Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.white);

    var matchResult = masterMind.guess(guess);

    assertEquals(0, matchResult.noMatches);
    assertEquals(0, matchResult.matches);
    assertEquals(6, matchResult.positionalMatches);
  }

  @Test
  public void guessFirstFourPositionalMatch() {
    var guess = List.of(Color.blue, Color.cyan, Color.green, Color.magenta, Color.black, Color.black);

    var matchResult = masterMind.guess(guess);

    assertEquals(2, matchResult.noMatches);
    assertEquals(0, matchResult.matches);
    assertEquals(4, matchResult.positionalMatches);
  }

  @Test
  public void guessLastFourPositionalMatch() {
    var guess = List.of(Color.black, Color.black, Color.green, Color.magenta, Color.orange, Color.white);

    var matchResult = masterMind.guess(guess);

    assertEquals(2, matchResult.noMatches);
    assertEquals(0, matchResult.matches);
    assertEquals(4, matchResult.positionalMatches);
  }

  @Test
  public void guessFirstThreePositionalMatchLastTwoMatch() {
    var guess = List.of(Color.blue, Color.cyan, Color.green, Color.black, Color.white, Color.orange);

    var matchResult = masterMind.guess(guess);

    assertEquals(1, matchResult.noMatches);
    assertEquals(2, matchResult.matches);
    assertEquals(3, matchResult.positionalMatches);
  }

  @Test
  public void guessAllMatch() {
    var guess = List.of(Color.cyan, Color.blue, Color.magenta, Color.green, Color.white, Color.orange);

    var matchResult = masterMind.guess(guess);

    assertEquals(0, matchResult.noMatches);
    assertEquals(6, matchResult.matches);
    assertEquals(0, matchResult.positionalMatches);
  }

  @Test
  public void guess2PositionalMatch456Match() {
    var guess = List.of(Color.black, Color.cyan, Color.black, Color.orange, Color.white, Color.magenta);

    var matchResult = masterMind.guess(guess);

    assertEquals(2, matchResult.noMatches);
    assertEquals(3, matchResult.matches);
    assertEquals(1, matchResult.positionalMatches);
  }

  @Test
  public void guessAllNoMatch() {
    var guess = List.of(Color.black, Color.black, Color.black, Color.black, Color.black, Color.black);

    var matchResult = masterMind.guess(guess);

    assertEquals(6, matchResult.noMatches);
    assertEquals(0, matchResult.matches);
    assertEquals(0, matchResult.positionalMatches);
  }

  @Test
  public void guessFirstColorRepeated() {
    var guess = List.of(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);

    var matchResult = masterMind.guess(guess);

    assertEquals(5, matchResult.noMatches);
    assertEquals(0, matchResult.matches);
    assertEquals(1, matchResult.positionalMatches);
  }

  @Test
  public void guess1stColor2to62ndColor1() {
    var guess = List.of(Color.cyan, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);

    var matchResult = masterMind.guess(guess);

    assertEquals(4, matchResult.noMatches);
    assertEquals(2, matchResult.matches);
    assertEquals(0, matchResult.positionalMatches);
  }

  @Test
  public void guess1stColor2to6NoMatch1() {
    var guess = List.of(Color.black, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);

    var matchResult = masterMind.guess(guess);

    assertEquals(5, matchResult.noMatches);
    assertEquals(1, matchResult.matches);
    assertEquals(0, matchResult.positionalMatches);
  }

  @Test
  public void guessAfterSuccessfulGuess() {
    var guess1 = List.of(Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.white);
    var guess2 = List.of(Color.black, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);

    masterMind.guess(guess1);

    assertThrows(ArithmeticException.class, () -> masterMind.guess(guess2));
  }

  @Test
  public void guessAfter19IncorrectGuesses() {
    var guess = List.of(Color.black, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);

    masterMind.turnNumber = 19;
    masterMind.guess(guess);

    assertEquals(20, masterMind.turnNumber);
  }

  @Test
  public void guessAfter20IncorrectGuesses() {
    var guess = List.of(Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.white);

    masterMind.turnNumber = 20;
    MasterMind.currentGameState = MasterMind.GameState.LOST;

    assertThrows(ArithmeticException.class, () -> masterMind.guess(guess));
  }

  @Test
  public void guessAfter21IncorrectGuesses() {
    var guess = List.of(Color.black, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);

    masterMind.turnNumber = 21;
    MasterMind.currentGameState = MasterMind.GameState.LOST;

    assertThrows(ArithmeticException.class, () -> masterMind.guess(guess));
  }

  @Test
  public void randomListGeneration() {
    var retain = masterMind.selectedColors;
    masterMind = new MasterMind();
    assertNotEquals(retain, masterMind.selectedColors);
  }

  @Test
  public void twoNonSimilarRandomColorLists() {
    MasterMind masterMind2 = new MasterMind();
    assertNotEquals(masterMind.selectedColors, masterMind2.selectedColors);
  }

  @Test
  public void giveUp() {
    masterMind.gameOver();

    assertEquals(MasterMind.currentGameState, MasterMind.GameState.LOST);
  }
}