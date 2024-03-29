package com.ghostwalker18.RSPLS;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import java.util.Random;

public class OnePlayerStrategy extends GameStrategy {
    private Random AI = new Random();
    private int playerScore = 0;
    private int computerScore = 0;
    public OnePlayerStrategy(Activity context) {
        super(context);
    }
    @Override
    public void restart() {
        playerScore = 0;
        playerScoreTextView.setText("0");
        computerScore = 0;
        computerScoreTextView.setText("0");
    }
    @Override
    public void onClick(View view) {
        int playerStep = 0;
        CharSequence message = "0";
        switch (view.getId()){
            case R.id.stoneButton:
                playerStep = 0;
                break;
            case R.id.scissorsButton:
                playerStep = 1;
                break;
            case R.id.paperButton:
                playerStep = 2;
                break;
            case R.id.lizardButton:
                playerStep = 3;
                break;
            case R.id.spokButton:
                playerStep = 4;
                break;
        }
        int computerStep = AI.nextInt(5);
        int result = gameMatrix[playerStep][computerStep];
        switch (result){
            case -1:
                computerScore++;
                message = context.getText(R.string.computer_won);
                break;
            case 1:
                playerScore++;
                message = context.getText(R.string.player_won);
                break;
            case 0:
                message = context.getText(R.string.draw);
                break;
        };
        Toast toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        playerScoreTextView.setText(String.valueOf(playerScore));
        computerScoreTextView.setText(String.valueOf(computerScore));
        if(playerScore == stepsLimit || computerScore == stepsLimit){
            String finalMessage =  context.getText(R.string.game_over).toString() + " : " + (playerScore > computerScore
                    ?  context.getText(R.string.you).toString() : context.getText(R.string.computer).toString());
            Toast toast1 = Toast.makeText(context.getApplicationContext(), finalMessage, Toast.LENGTH_SHORT);
            computerScore = 0;
            computerScoreTextView.setText("0");
            playerScore = 0;
            playerScoreTextView.setText("0");
            toast1.show();
        }
    }
}