package com.example.tictaetoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // to track whose turn is this (cross or zero)
    // 1 for cross, 0 for zero
    private static int elementCounter = 1;

    // to track current board condition
    // 1 for cross, 0 for zero and 2 for empty
    // initially, whole board is empty ;)
    private static int[] boardStats = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // winning board positions
    private static int[][] winningBoardPositions = {
            // Horizontals
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},

            // Verticals
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},

            // Diagonals
            {0, 4, 8},
            {2, 4, 6}
    };

    public static boolean isGameActive = true;

    public void clickFunction(View view) {
        boolean isDraw = true;
        boolean isFull = true;
        // boolean winnerFound = false

        // initially no winner; 0 for zero and 1 for cross
        int winner = -1;

        // second one in if checks if the square has been tapped before or not
        // We've not implemented the setOnClickListener() way, since it was troublesome ;)
        if(isGameActive && boardStats[Integer.parseInt(String.valueOf(view.getTag()))] == 2) {
            // and not = findViewById(R.id.crossImageView21)
            // because we want imageView to be equal to
            // the image tapped on
            final ImageView imageView = (ImageView) view;

            Button playAgainButton = findViewById(R.id.playAgainButton);

            TextView winnerTextView =  findViewById(R.id.winningTextView);

            /*
            // avoid multiple clicks on crosses and zeroes at our board
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setEnabled(false);
                }
            });
             */

            if (MainActivity.elementCounter == 1) {
                // not crossImageView21 = findViewById(R.drawable.cross); as error
                imageView.setImageResource(R.drawable.cross);
                boardStats[Integer.parseInt(imageView.getTag().toString())] = 1;
                elementCounter = 0;
            } else {
                imageView.setImageResource(R.drawable.zero);
                boardStats[Integer.parseInt(imageView.getTag().toString())] = 0;
                elementCounter = 1;
            }

            for (int[] winningPosition : winningBoardPositions) {
                if (boardStats[winningPosition[0]] == boardStats[winningPosition[1]] && boardStats[winningPosition[1]] == boardStats[winningPosition[2]] && boardStats[winningPosition[0]] != 2) {
                    winner = boardStats[winningPosition[0]];

                    if (winner == 1) {
                        winnerTextView.setText(R.string.cross_won);
                        winnerTextView.setVisibility(View.VISIBLE);
                        winnerTextView.setAlpha(1f);
                        playAgainButton.setVisibility(View.VISIBLE);
                    } else if(winner == 0) {
                        winnerTextView.setText(R.string.zero_won);
                        winnerTextView.setVisibility(View.VISIBLE);
                        winnerTextView.setAlpha(1f);
                        playAgainButton.setVisibility(View.VISIBLE);
                    }
                    isGameActive = false;
                    return;
                }
            }

            for(int squareValue: boardStats) {
                if(squareValue == 2) {
                    isFull = false;
                    isDraw = false;
                    break;
                }
            }

            if(isFull && !isGameActive){
                isDraw = true;
            }

                if(isDraw){
                    winnerTextView.setText(R.string.draw);
                    Log.i("Draw", "Game Draw!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    winnerTextView.setAlpha(1f);
                    playAgainButton.setVisibility(View.VISIBLE);

                    isGameActive = false;
                    return;
                }
        }
    }

    public void playAgain(View view) {
        MainActivity.boardStats = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerTextView = (TextView) findViewById(R.id.winningTextView);

        playAgainButton.setVisibility(View.INVISIBLE);

        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {

            final ImageView counter = (ImageView) gridLayout.getChildAt(i);

            /*
            // Re-enabling clicks
            counter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter.setEnabled(false);
                }
            });

             */

            if(!counter.getResources().getResourceEntryName(counter.getId()).equals("boardImageView")) {
                counter.setImageResource(android.R.color.transparent);
            }
            // viewToUse.setImageResource(android.R.color.transparent)
            //Log.i("Info", String.valueOf(counter.getId()));
        }

        elementCounter = 1;

        isGameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
