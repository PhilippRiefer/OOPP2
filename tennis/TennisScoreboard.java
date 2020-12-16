package tennis;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author henning
 *
 */
public class TennisScoreboard extends JFrame implements ActionListener {
  
  // Attributes
  private TennisScoring score;    // the object that manages the score
  private JButton playerButton[]; // buttons to press if a player won a point
  private JButton newMatchButton; // button to press if a new match shall be started
  private JLabel label[][];       // bunch of labels for the score
  private JCheckBox service[];    // checkboxes to show who has to serve

  // Constructor
  /**
   * Constructs a simple window showing the current score.
   * 
   * @param score a TennisScoring object containing the scoring information. 
   */
  public TennisScoreboard(TennisScoring score){
    super("Tennis Scoreboard"); // Title of the window
    this.score=score;
    
    // creating the GUI elements
    playerButton = new JButton[2];
    label = new JLabel[2][7];
    service = new JCheckBox[2];

    // construction of the window
    setLayout(new GridLayout(0,9,10,10)); // Layout-Style defined: 9 columns, 10p distance
    setMinimumSize(new Dimension(700,300)); // should be large    

    // create the three buttons
    newMatchButton = new JButton("Start new Match");
    playerButton[0] = new JButton("Player 1");
    playerButton[1] = new JButton("Player 2");
    
    // listen to the buttons!
    newMatchButton.addActionListener(this);
    for (int i=0; i<2; i++) {
      playerButton[i].addActionListener(this);
    }
    
    // now put all elements into the window panel 
    // the order is from left to right - from top to bottom
    
    // first row
    add(newMatchButton);
    add(new JLabel("Set 1"));
    add(new JLabel("Set 2"));
    add(new JLabel("Set 3"));
    add(new JLabel("Set 4"));
    add(new JLabel("Service"));
    add(new JLabel("Sets"));
    add(new JLabel("Games"));
    add(new JLabel("Points"));

    // 2nd and 3rd rows
    for (int i=0; i<=1; i++) {
      add(playerButton[i]);
      for (int j=0; j<4; j++) {
        label[i][j] = new JLabel("");
        add(label[i][j]);
      }
      service[i] = new JCheckBox("",i==0);
      service[i].setEnabled(false);
      add(service[i]);
      for (int j=4; j<7; j++) {
        label[i][j] = new JLabel("");
        add(label[i][j]);
      }
    }

    
    setDefaultCloseOperation(EXIT_ON_CLOSE); // close-button behaviour
    setSize(800, 500); // start with this
    
    // showtime!
    setVisible(true);
    updateLabels();
    updateButtons();

  }

  
  /**
   * Simplest main possible: Start just one scoreboard window.
   * 
   * @param args not used here
   */
  public static void main(String[] args) {
    new TennisScoreboard(new TennisScore());
  }

  /**
   * This method is called by the system when a button is pressed.
   */
  @Override
  public void actionPerformed(ActionEvent event) {
    if (event.getSource()==newMatchButton) { // someone pressed "New match"
      score.newMatch();
    } else if (event.getSource()==playerButton[0]) { // someone pressed "P1 scored"
      score.pointFor(1);
    } else if (event.getSource()==playerButton[1]) { // someone pressed "P2 scored"
      score.pointFor(2);
    }
    // update the GUI
    updateLabels();
    updateButtons();
  }

  /**
   * Update the buttons.
   * 
   * The buttons for the players shall only be enabled when the game
   * is not finished. 
   */
  private void updateButtons() {
    for (int i=0; i<2; i++) {
      playerButton[i].setEnabled(!score.isFinished());
    }
  }

  /**
   * Update the labels.
   * 
   * Gathers all the various elements from the scoring object and
   * puts them into the right place.  
   */
  private void updateLabels() {
    // which set is played at the moment?
    int currentSet = score.currentSet();
    
    // for all players:
    for (int i=0; i<2; i++) {
      int player =i+1;
      
      // for all sets:
      for (int j=1; j<5; j++) {
        
        // for all sets which are already finished:
        if (j<currentSet) {
          label[i][j-1].setText(""+score.gamesInSet(player,j));
        } else { // these sets are not finished
          label[i][j-1].setText("-");
        }
      }
      // who has to serve
      service[i].setSelected(score.hasService(player));
      
      // sets won by the players
      label[i][4].setText(""+score.wonSets(player));
      
      // games won by the players in the current set
      label[i][5].setText(""+score.gamesInSet(player,Math.min(currentSet,5)));
      
      // score of current game
      label[i][6].setText(""+score.scoreInGame(player));
    }
  }
}
