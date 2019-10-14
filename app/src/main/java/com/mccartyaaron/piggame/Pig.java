package com.mccartyaaron.piggame;

import java.util.Random;
import android.util.Log;

public class Pig {
        private Random rand = new Random();
        public int player1Score = 0;
        public int player2Score = 0;
        public int pointTotal = 0;
        private String player1Name = "";
        private String player2Name = "";
        private int turn;

    final private int WINNING_SCORE = 100;

    public Pig()
        {
        player1Score = 0;
        player2Score = 0;
        pointTotal = 0;
        turn = 1; // player 1 goes first
    }

    public Pig(int p1Score, int p2Score, int pTotal, int t)
    {
        player1Score = p1Score;
        player2Score = p2Score;
        pointTotal = pTotal;
        turn = t;
    }

    public void setPlayer1Name(String n)
    {
        player1Name = n;
    }

    public String getPlayer1Name()
    {
        return player1Name;
    }

    public void setPlayer2Name(String n)
    {
        player2Name = n;
    }

    public String getPlayer2Name()
    {
        return player2Name;
    }

    public int getTurn() {
        return turn;
    }

    public int getPlayer1Score()
    {
        return player1Score;
    }


    public int getPlayer2Score()
    {
        return player2Score;
    }
    public int getPointTotal() {
        return pointTotal;
    }

    public void resetGame()
    {
        player1Score = 0;
        player2Score = 0;
        pointTotal = 0;
        turn = 1;
    }

    public int rollDie()
    {
        int roll = rand.nextInt(8) + 1;

        if(roll != 8)
        {
            pointTotal += roll;
        }
        else
        {
            pointTotal = 0;
            changeTurn();
        }

        return roll;
    }


    public String getCurrentPlayer()
    {
        if(turn % 2 == 1)
            return player1Name;
        else
            return player2Name;
    }

    public int changeTurn()
    {
        if(turn % 2 == 1)
            player1Score += pointTotal;
        else
            player2Score += pointTotal;

        pointTotal = 0;

        turn++;
        return turn;
    }

    public String checkForWinner()
    {
        String winnerMessage = "";
        if (player1Score >= WINNING_SCORE || player2Score >= WINNING_SCORE) {
            if (player2Score > player1Score) {
                winnerMessage = String.format("%s wins!", player2Name);
            }
            // Player 1 can only win after player 2 has had thier turn
            // This is so both players can have an equal number of turns
            else if (player1Score > player2Score && turn % 2 == 1) {
                winnerMessage = String.format("%s wins!", player1Name);
            } else if (player1Score == player2Score) {
                winnerMessage = "Tie";
            }
        }
        return winnerMessage;
    }

}


