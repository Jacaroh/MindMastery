package game;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class MasterMind {
  public List<Color> selectedColors;
  public int turnNumber;
  public enum GameState {PLAYING, WON, LOST}
  public static GameState currentGameState;

  List<Color> shuffleSelectedColors() {
    List<Color> fullColors = new ArrayList<>();

    fullColors.addAll(List.of(Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.red, Color.yellow, Color.pink, Color.black, Color.gray));
    shuffle(fullColors);

    return fullColors.subList(0, 6);
  }

  public MasterMind() {
    selectedColors = shuffleSelectedColors();
    currentGameState = GameState.PLAYING;
    turnNumber = 0;
  }

  void setSelectedColors(List<Color> selectedColors) {
    this.selectedColors = selectedColors;
  }

  public MatchResult guess(List<Color> guessColors) {
    turnNumber++;
    MatchResult matchResult = matchGuess(guessColors);
    gameStateCheck(matchResult.positionalMatches);

    return matchResult;
  }

  public MatchResult matchGuess(List<Color> guessColors) {
    int matches = 0;
    int positionalMatches = 0;
    int noMatches = 0;
    int index = 0;

    for (var color: selectedColors) {
      if(guessColors.contains(color)) {
        if (selectedColors.get(index) == guessColors.get(index)) {
          positionalMatches++;
        }
        else {
          matches++;
        }
      }
      else {
        noMatches++;
      }
      index++;
    }

    return new MatchResult(noMatches, matches, positionalMatches);
  }

  public void gameStateCheck(int positionalMatches) {
    gameOverCheck();

    if(positionalMatches == 6) {
      currentGameState = GameState.WON;
    }
    else if(turnNumber >= 20) {
      currentGameState = GameState.LOST;
    }
  }

  public void gameOverCheck() {
    if(currentGameState != GameState.PLAYING) {
      throw new ArithmeticException("Game already over.");
    }
  }

  public void gameOver() {
    currentGameState = GameState.LOST;
    turnNumber = 20;
  }
}