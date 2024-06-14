package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private TextView player1Score, player2Score, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int player1ScoreCount, player2ScoreCount, roundCount;
    Boolean activePlayer;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);
        playerStatus = findViewById(R.id.playerStatus);
        resetGame = findViewById(R.id.resetBtn);

        for (int i = 0; i < buttons.length; i++) {
            String buttonId = "btn" + (i + 1);
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        player1ScoreCount = 0;
        player2ScoreCount = 0;
        roundCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().equals("")) {
            return;
        }

        String buttonId = ((Button) view).getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length() - 1, buttonId.length())) - 1;

        if (activePlayer) {
            ((Button) view).setText("X");
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                player1ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                player2ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(roundCount == 9){
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();

        }
        else {
            activePlayer = !activePlayer;
        }

        if(player1ScoreCount > player2ScoreCount){
            playerStatus.setText("Player One is Winning!");
        } else if (player2ScoreCount > player1ScoreCount) {
            playerStatus.setText("Player Two is Winning!");
        } else{
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                player1ScoreCount = 0;
                player2ScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public Boolean checkWinner() {
        boolean winnerResult = false;

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[1]] != 2) {
                winnerResult = true;
            }
        }

        return winnerResult;
    }

    public void updatePlayerScore() {
        player1Score.setText(Integer.toString(player1ScoreCount));
        player2Score.setText(Integer.toString(player2ScoreCount));
    }

    public void playAgain() {
        roundCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }

}