package tennis;

/**
 * Communication interface between TennisScoreboard and TennisScore.
 * 
 * @author Henning Dierks
 * @date 2020-11-24
 */
public interface TennisScoring {
  
  // Methods to inform TennisScore that a button was pressed
  /**
   * User wants to start a new tennis match.
   */
  public void newMatch();
  
  /**
   * User pressed button that a player has won the last point.
   * 
   * @param player the player who won the last point
   */
  public void pointFor(int player);
  
  // Methods to receive information about the current score.
  /**
   * Is the current match over?
   * @return true when it is finished.
   */
  public boolean isFinished();
  
  /**
   * Determines who the winner is if there is one. 
   * @return 1 - if the first player has won <p>
   *         2 - if the second player has won <p>
   *         0 - if there is no winner yet
   */
  public int winner();
  
  /**
   * Computes the number of the set that is played at the moment.
   * @return the current set (1-5)
   */
  public int currentSet();
  
  /**
   * Returns the games a player has won in a given set. 
   * 
   * @param player the player (1-2)
   * @param set the set (1-5)
   * @return number of games won by the given player in the given set 
   */
  public int gamesInSet(int player, int set);
  
  /**
   * Returns the sets a player has won at the moment.
   * 
   * @param player the player (1-2)
   * @return number of sets won by the given player so far. 
   */
  public int wonSets(int player);
  
  /**
   * Determines whether a player has to serve.
   * 
   * @param player the player (1-2)
   * @return true if the player has the service
   */
  public boolean hasService(int player);
  
  /**
   * Gives the text to appear at the scoreboard to denote the 
   * current standing of the current game.
   * 
   * @param player the player (1-2)
   * @return the current score ("0", "15", "30", "40", "Ad") in a normal game <p>
   *                           and "0", "1", "2", ... in a tie-break
   */
  public String scoreInGame(int player);
}
