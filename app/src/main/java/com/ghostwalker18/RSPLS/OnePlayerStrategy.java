package com.ghostwalker18.RSPLS;

import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class OnePlayerStrategy extends GameStrategy {
    private Random AI = new Random();
    private int playerScore = 0;
    private int computerScore = 0;
    public OnePlayerStrategy(MainActivity context) {
        super(context);
    }

    @Override
    public String getName(){
        return context.getResources().getStringArray(R.array.game_mode_entries)[0];
    }
    @Override
    public int[] getPlayersNamesIds(){
        return new int[]{ R.string.you, R.string.computer};
    }

    @Override
    public void restart() {
        playerScore = 0;
        playerOneScoreTextView.setText("0");
        computerScore = 0;
        playerTwoScoreTextView.setText("0");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int playerStep = 0;
        CharSequence message = "";

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
        context.setPlayerOneFigure(stepToLogo[playerStep]);
        context.showCurrentStep(R.string.computer);
        int computerStep = AI.nextInt(5);
        context.setPlayerTwoFigure(stepToLogo[computerStep]);

        Result result = gameMatrix[playerStep][computerStep];

        switch (result){
            case Lost:
                computerScore++;
                message = context.getText(R.string.computer_won);
                break;
            case Won:
                playerScore++;
                message = context.getText(R.string.player_won);
                break;
            case Draw:
                message = context.getText(R.string.draw);
                break;
        };
        context.showFigures();
        context.showRoundWinner(message);
        context.showCurrentStep(R.string.you);

        playerOneScoreTextView.setText(String.valueOf(playerScore));
        playerTwoScoreTextView.setText(String.valueOf(computerScore));

        if(playerScore == stepsLimit || computerScore == stepsLimit){
            winnerStringId = (playerScore > computerScore) ?  R.string.you : R.string.computer;
            context.showWinner(winnerStringId, playerScore, computerScore);
        };
    }
}