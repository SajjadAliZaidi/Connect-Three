package com.optimatechnologies.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameActive = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};  //2 means an empty square
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameActive) {
            if (gameState[tappedCounter] == 2) {
                counter.setTranslationY(-1000f);
                gameState[tappedCounter] = activePlayer;
                if (activePlayer == 0)   //yellow counter
                {
                    counter.setImageResource(R.drawable.yellow);
                    activePlayer = 1;
                } else                    //red counter
                {
                    counter.setImageResource(R.drawable.red);
                    activePlayer = 0;
                }
                counter.animate().rotation(360).translationYBy(1000f).setDuration(300);

                boolean playerWon = false;

                for (int[] winningPosition : winningPositions) {

                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                            gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                            gameState[winningPosition[0]] != 2) {
                        System.out.println(gameState[winningPosition[0]]);
                        playerWon = true;
                        //Someone has won
                        gameActive = false;     //Game has stopped
                        // TODO: add draw functionality
                        LinearLayout layout = (LinearLayout) findViewById(R.id.gameWonLayout);
                        TextView message = (TextView) findViewById(R.id.playerWonMessage);
                        if (gameState[winningPosition[0]] == 0) {
                            message.setText("Yellow Player \nWon");
                        } else {
                            message.setText("Red Player \nWon");
                        }
                        layout.setVisibility(View.VISIBLE);
                    }
                }
                if (!playerWon) {        //if no player won then check if the grid is full or not
                    boolean gridFull = true;       //Checking if the game has drawn
                    for (int i = 0; i < gameState.length && gridFull; ++i) {
                        if(gameState[i] == 2)
                            gridFull = false;
                    }
                    if(gridFull == true){
                        LinearLayout layout = (LinearLayout) findViewById(R.id.gameWonLayout);
                        TextView message = (TextView) findViewById(R.id.playerWonMessage);
                        message.setText("DRAW");
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.gameWonLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; ++i)
            gameState[i] = 2;

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gameGridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); ++i)
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
    }

//    public void playAgain(View view) {
//        gameActive = true;
//        LinearLayout layout = (LinearLayout) findViewById(R.id.gameWonLayout);
//        layout.setVisibility(View.INVISIBLE);
//
//        activePlayer = 0;
//
//        for (int i = 0; i < gameState.length; ++i)
//            gameState[i] = 2;
//
//        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
//
////        if(gridLayout == 1)
//
//        //        for (int i = 0; i < gridLayout.getChildCount(); ++i)
////            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
