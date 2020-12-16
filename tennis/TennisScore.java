package tennis;

/*	Programm : TennisScore . java
*	Autoren : Philipp Riefer, Domenic Heidemann
*	Datum : 10.12.2020
*/

import java.util.concurrent.ThreadLocalRandom;

public class TennisScore implements TennisScoring {

    //TODO switch service doesnt work

    //TODO finish match doesnt work

    private int currentSet;
    private boolean gameIsFinished;
    private int[] points = new int[3];
    private String[] score = new String[3];
    private int winner;
    private boolean[] hasService = new boolean[3];
    private int[][] gamesInSet = new int[3][100];
    private int[] wonSets = new int[3];

    public TennisScore() {
        newMatch();
    }

    public void randomizeService(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
        switch (randomNum) {
            case 0:
                hasService[1] = true;
                hasService[2] = false;
                break;
            case 1:
                hasService[1] = false;
                hasService[2] = true;
                break;
            default:
                break;
        }
    }

    public void switchService(){
        hasService[1] = !hasService[1];
        hasService[2] = !hasService[2];
    }

    @Override
    public void newMatch() {// start new match when newMatchButton is pressed
        currentSet = 1;
        gameIsFinished = false;
        for (int i = 1; i < 3; i++) {
            points[i] = 0;
            score[i] = "Love";
            wonSets[i] = 0;
            for (int j = 0; j < 100; j++) {
                gamesInSet[i][j] = 0;
            }
        }
        randomizeService();
    }
    
    @Override
    public void pointFor(int player){// add a point for the player whose button is pressed // int player is equal to 1 if player 1 scored or 2 if player 2 scored
        points[player]++;
        for (int i = 1; i < 3; i++){
            if (points[i] > 3) {
                checkWin(player);
            } else {
                switch (points[i]) {
                    case 0:
                        score[i] = "Love";
                        break;
                    case 1:
                        score[i] = "15";
                        break;
                    case 2:
                        score[i] = "30";
                        break;
                    case 3:
                        score[i] = "40";
                        if (points[1] == points[2]) {
                            score[1] = "deuce";
                            score[2] = "deuce";
                        }
                        break;
                    default:
                        break;    
                }
            }
        }
    }

    public void checkWin(int i){// check if a player has actually won or if it's "Einstand" or "Vorteil"
        
        //mögliche variationen:
        //- punktemacher gewinnt weil 40:30 || 40:15 || 40:Love
        //- 40:40
        //- einstand
        //- vorteil punktemacher
        //- vorteil gegner
        
        if (points[1] == points[2]) {// Einstand
            for (int j = 1; j < 3; j++) { score[j] = "deuce"; }
        }
        if (points[1] - points[2] >= 2) {// player 1 wins the game
            winner = 1;
            gameIsFinished = true;
        }
        if (points[2] - points[1] >= 2) {// player 2 wins the game
            winner = 2;
            gameIsFinished = true;
        }
        if (points[1] - points[2] == 1) {// player 1 has advantage
            if (hasService[1]) {
                score[1] = "Ad-In";
                score[2] = "40";
            }else{
                score[1] = "Ad-Out";
                score[2] = "40";
            }
        }
        if (points[2] - points[1] == 1) {// player 2 has advantage
            if (hasService[2]) {
                score[2] = "Ad-In";
                score[1] = "40";
            }else{
                score[2] = "Ad-Out";
                score[1] = "40";
            }
        }

        if (isFinished()) {
            
            gamesInSet[i][currentSet]++;

            gameIsFinished = false;
            
            for (int j = 1; j < 3; j++) {
                points[j] = 0;
                score[j] = "Love";
            }

            switchService();

            if (gamesInSet(i, currentSet) >= 6) {
                if (gamesInSet(1, currentSet) - gamesInSet(2, currentSet) >= 2) {
                    wonSets[1]++;
                }
                if (gamesInSet(2, currentSet) - gamesInSet(1, currentSet) >= 2) {
                    wonSets[2]++;
                }
                currentSet++;
            }
        }
        winner();
    }

    @Override
    public boolean isFinished(){// determine if a match is finished or not
        return gameIsFinished;
    }
    
    @Override
    public int winner(){// declare a winner
        for (int i = 1; i < 3; i++) {
            if (wonSets[i] >= 3) {
                winner = i;
            }
        }
        return winner;
    }
    
    @Override
    public int currentSet(){// returns number of current set
        return this.currentSet;
    }
    
    @Override
    public int gamesInSet(int player, int set){// returns number of sets a player has won in a game
        return gamesInSet[player][set];
    }
    
    @Override
    public int wonSets(int player){// returns number of sets a player has won at the moment
        return wonSets[player];
    }
    
    @Override
    public boolean hasService(int player){// returns whether the player has to serve or not
        return this.hasService[player];
    }
    
    @Override
    public String scoreInGame(int player){// returns the points text of the current game
        return score[player];
    }
}
