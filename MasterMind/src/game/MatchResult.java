package game;

public class MatchResult {
  public final int noMatches;
  public final int matches;
  public final int positionalMatches;

  public MatchResult(int none, int match, int positional) {
    noMatches = none;
    matches = match;
    positionalMatches = positional;
  }
}
