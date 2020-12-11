package tennis;

import java.awt.event.ActionEvent;

public class TennisScore implements TennisScoring{

    private int currentSet;
    private boolean isFinished;
    private int[] points;
    private String[] score;
    private int winner;

    @Override
    public void newMatch(ActionEvent ae){// start new match when newMatchButton is pressed
        System.out.println("ActionEvent " + ae);
        if (ae.getSource() == newMatchButton) {
            currentSet = 1;
            isFinished = false;
            points[0] = 0;// points player 1
            points[1] = 0;// points player 2
        }
    }
    
    @Override
    public void pointFor(int player){// add a point for the player whose button is pressed
        points[player-1]++;
        for (int i = 0; i < 2; i++){
            switch (points[i]) {
                case 0:
                    score[i] = "0";
                    break;
                case 1:
                    score[i] = "15";
                    break;
                case 2:
                    score[i] = "30";
                    break;
                case 3:
                    score[i] = "40";
                    break;
                default:
                    checkWin(i);
                    break;
            }
        }
    }

    @Override
    public void checkWin(int i){// check if a player has actually won or if it's "Einstand" or "Vorteil"

        /*
        mögliche variationen:
        - punktemacher gewinnt weil 40:30 || 40:15 || 40:0
        - 40:40
        - einstand 
        - vorteil punktemacher 
        - vorteil gegner 
        */ 
 
        // Vorteil 
        if(score[0] == score[1]){// bei Einstand und 40:40
            score[i] = "Vorteil";
        }
        if(score[i] == "Vorteil"){// player i hatte Vorteil und hat nun gewonnen
            winner = i;
            isFinished = true;
        }
        switch (i) {// player not i hatte Vorteil und nun ist Einstand
            case 0:
                if(score[1] == "Vorteil"){
                    score[0] = "Einstand";
                    score[1] = "Einstand";
                }
                break;
            case 1:
                if(score[0] == "Vorteil"){
                    score[0] = "Einstand";
                    score[1] = "Einstand";
                }
                break;
            default:
                break;
        }
        switch (i) {// player not i hat weniger als 40 punkte, player i hatte 40 und gewinnt demnach nun
            case 0:
                if(points[1] < 3){
                    winner = i;
                    isFinished = true;
                }
                break;
            case 1:
                if(points[0] < 3){
                    winner = i;
                    isFinished = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isFinished(){// determine if a match is finished or not
        return isFinished;
    }
    
    @Override
    public int winner(){// declare a winner
        if (isFinished()) {
            return this.winner;
        }
    }
    
    @Override
    public int currentSet(){
        return this.currentSet;
        // returns number of current set
    }
    
    @Override
    public int gamesInSet(int player, int set){
        // returns number of games a player has won in the given set
    }
    
    @Override
    public int wonSets(int player){
        // returns number of sets a player has won at the moment
    }
    
    @Override
    public boolean hasService(int player){
        // returns whether the player has to serve or not
    }
    
    @Override
    public String scoreInGame(int player){
        // returns the points text of the current game
    }
}
