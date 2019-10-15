package com.mccartyaaron.piggame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.KeyEvent;
import android.graphics.drawable.Drawable;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;




import android.content.SharedPreferences;
import android.util.Log;

import java.util.Locale;

public class MainActivity extends Activity
    implements OnClickListener, OnEditorActionListener {


    private Pig game;
    private EditText playerOneText;
    private EditText playerTwoText;
    private TextView scorePlayerOne;
    private TextView scorePlayerTwo;
    private ImageView dieView;
    private TextView currentPlayerView;
    private TextView turnPointsText;
    private Button rollDieButton;
    private Button endTurnButton;
    private Button newGameButton;

    //these string are used to store the desired name

    private String playerOneName;
    private String playerTwoName;

    private SharedPreferences savedValues;




    // Define SharedPreferences object

    //savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
    // For logging and debugging
    private static final String TAG = "MainActivity";

    private void displayScores() {
        scorePlayerOne.setText(String.format(Locale.US, "%d", game.getPlayer1Score()));
        scorePlayerTwo.setText(String.format(Locale.US, "%d", game.getPlayer2Score()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneText = (EditText)findViewById(R.id.playerOneText);
        playerTwoText = (EditText)findViewById(R.id.playerTwoText);
        currentPlayerView = (TextView)findViewById(R.id.currentPlayerView);
        scorePlayerOne = (TextView)findViewById(R.id.scorePlayerOne);
        scorePlayerTwo = (TextView)findViewById(R.id.scorePlayerTwo);
        turnPointsText = (TextView)findViewById(R.id.turnPointsText);
        dieView = (ImageView)findViewById(R.id.dieView);
        rollDieButton = (Button)findViewById(R.id.rollDieButton);
        endTurnButton = (Button)findViewById(R.id.endTurnButton);
        newGameButton = (Button)findViewById(R.id.newGameButton);


        game = new Pig();
        currentPlayerView.setText(playerOneName);
        rollDieButton.setFocusable(false);
        endTurnButton.setFocusable(false);
        newGameButton.setFocusable(false);

        if (savedInstanceState != null) {
            game.player1Score = savedInstanceState.getInt("player1Score", 0);
            game.player2Score = savedInstanceState.getInt("player2Score", 0);
            playerOneText.setText(savedInstanceState.getString("player1Name", ""));
            playerTwoText.setText(savedInstanceState.getString("player2Name", ""));
            game.pointTotal = savedInstanceState.getInt("pointTotal", 0);
        }

        endTurnButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);
        rollDieButton.setOnClickListener(this);
        currentPlayerView.setOnEditorActionListener(this);
        scorePlayerTwo.setOnEditorActionListener(this);
        scorePlayerOne.setOnEditorActionListener(this);
        displayScores();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("player1Score", game.getPlayer1Score());
        savedInstanceState.putInt("player2Score", game.getPlayer2Score());
        savedInstanceState.putString("player1Name", playerOneText.getText().toString());
        savedInstanceState.putString("player2Name", playerTwoText.getText().toString());
        savedInstanceState.putInt("pointTotal", game.pointTotal);
        super.onSaveInstanceState(savedInstanceState);
    }

    //Methods to use
    private void UpdateImage(int rolled) {
        int referenceID = 0;
        switch (rolled) {
            case 1: {
                referenceID = R.drawable.die8side1;
                break;
            }
            case 2: {
                referenceID = R.drawable.die8side2;
                break;
            }
            case 3: {
                referenceID = R.drawable.die8side3;
                break;
            }
            case 4: {
                referenceID = R.drawable.die8side4;
                break;
            }
            case 5: {
                referenceID = R.drawable.die8side5;
                break;
            }
            case 6: {
                referenceID = R.drawable.die8side6;
                break;
            }
            case 7: {
                referenceID = R.drawable.die8side7;
                break;
            }
            case 8: {
                referenceID = R.drawable.die8side8;
                break;
            }
            default: {
                referenceID = -1;
                break;
            }
        }
        Drawable drawableImage = getResources().getDrawable(referenceID);
        this.dieView.setImageDrawable(drawableImage);
    }



    private void ResetPlayerView() {
        this.currentPlayerView.setText("Nobody");
    }

    private void UpdateCurrentPlayer() {
        //this is the default updateCurrentPlayer method and will automatically set the UI to reflect the currentPlayerName
        this.currentPlayerView.setText(this.game.getCurrentPlayer() + "'s turn");
    }

    private void EnableEndTurnButton() {
        this.endTurnButton.setEnabled(true);
    }

    private void DisableEndTurnButton() {
        this.endTurnButton.setEnabled(false);
    }

    private void EnableRollButton() {
        this.rollDieButton.setEnabled(true);
    }

    private void DisableRollButton() {
        this.rollDieButton.setEnabled(false);
    }

    private void ResetScoreLabels() {
        this.scorePlayerOne.setText("0");
        this.scorePlayerTwo.setText("0");
    }
    public void displayPointTotal(int total) {
        turnPointsText.setText(String.valueOf(total));
    }
    public void CurrentPlayers() {
        this.playerOneName = this.playerOneText.getText().toString();
        this.playerTwoName = this.playerTwoText.getText().toString();
    }

    //Start new game
    public void startNewGame() {
        game.resetGame();
        displayScores();
        displayPointTotal(0);
        currentPlayerView.setText("Player 1");
        EnableRollButton();
        EnableEndTurnButton();

    }
    //end turn for current player
    public void endPlayerTurn() {
        // Check to see if there are points then add to player
        if (game.getPointTotal() != 0 && game.getCurrentPlayer() == playerOneName) {
            game.player1Score += game.getPointTotal();
           scorePlayerOne.setText(String.valueOf(game.player1Score));
        } else if (game.getPointTotal() != 0 && game.getCurrentPlayer() == playerTwoName) {
            game.player2Score += game.getPointTotal();
            scorePlayerTwo.setText((String.valueOf(game.player2Score)));
        }
        // Resets counter for next player
        displayPointTotal(game.pointTotal);
        game.checkForWinner();
        game.getCurrentPlayer();
    }
    public void rollGameDie() {
        // Random roll
        int dieRoll = game.rollDie();
        // Show die value
        UpdateImage(dieRoll);
        displayPointTotal(game.getPointTotal());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionID, KeyEvent event) {
        // Closes the soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        return false;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rollDieButton:
                // Roll the die
                Log.d(TAG, "roll button pressed");

                rollGameDie();

                break;

            case R.id.endTurnButton:
                // end the turn and switch to the other player
                Log.d(TAG, "endTurn button pressed");
                endPlayerTurn();
                break;

            case R.id.newGameButton:
                // start a new game
                Log.d(TAG, "newGame button pressed");

                startNewGame();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
