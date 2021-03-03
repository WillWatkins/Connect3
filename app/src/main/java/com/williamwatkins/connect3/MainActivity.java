package com.williamwatkins.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //When gameIsActive is false. No player can place a counter
    boolean gameIsActive = true;

    //0 = red, 1 = yellow.
    int activePlayer = 0;

    //2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //Possible winning positions in O's and X's
    int[][] winningPositions = {{0,1,2}, {3,4,5,}, {6,7,8,},
                                {0,3,6}, {1,4,7}, {2,5,8},
                                {0,4,8}, {2,4,6}};

    //The user selects a grid position to place their counter in.
    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

        counter.setTranslationY(-1000f);

        if (activePlayer == 0){
            counter.setImageResource(R.drawable.red);
            activePlayer =1;
        }
        else {
            counter.setImageResource(R.drawable.yellow);
            activePlayer =0;
        }
        counter.animate().translationYBy(1000f).rotation(360).setDuration(1500);

        for (int[] winningPosition : winningPositions){

            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[2]] !=2) {

                String winner = "Yellow";
                gameIsActive = false;
                if (gameState[winningPosition[0]] == 0) {
                    winner = "Red";
                }

                endGameMessage(winner);

            } else {

                boolean gameIsOver = true;

                for (int counterState : gameState){
                    if (counterState == 2)
                        gameIsOver = false;
                }

                if (gameIsOver) {
                    endGameMessage();
                }
            }
        }
        }
    }

    public void endGameMessage(String winner){
        TextView winnerMessage = findViewById(R.id.winnerMessage);
        winnerMessage.setText(winner + " has won!");

        LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.VISIBLE);
    }

    public void endGameMessage(){
        TextView winnerMessage = findViewById(R.id.winnerMessage);
        winnerMessage.setText("It's a draw");

        LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.VISIBLE);
    }


    //Resets the game
    public void playAgain(View view){

        //Removes the winning message from the screen
        LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.INVISIBLE);
        gameIsActive = true;

        //Resets the game values
        activePlayer = 0;

        Arrays.fill(gameState, 2);

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }
}