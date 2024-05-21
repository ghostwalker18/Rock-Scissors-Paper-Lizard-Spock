package com.ghostwalker18.RSPLS;

import android.view.View;
import androidx.annotation.NonNull;

public class TwoPlayersLocalStrategy extends GameStrategy {
   private int playerOneScore = 0;
   private int playerTwoScore = 0;
   int playerOneStep = 0;
   int playerTwoStep = 0;
   private int stepCounter = 0;

   public TwoPlayersLocalStrategy(@NonNull MainActivity context) {
      super(context);
   }

   @Override
   public String getName(){
      return context.getResources().getStringArray(R.array.game_mode_entries)[1];
   }
   @Override
   public int[] getPlayersNamesIds(){
      return new int[]{R.string.player_one, R.string.player_two};
   }

   @Override
   public void restart() {
      stepCounter = 0;
      playerOneScore = 0;
      playerOneScoreTextView.setText("0");
      playerTwoScore = 0;
      playerTwoScoreTextView.setText("0");
      playerOneStep = 0;
      playerTwoStep = 0;
   }

   @Override
   public void onClick(View view) {
      super.onClick(view);
      stepCounter++;
      int step = 0;
      CharSequence message = "";

      switch (view.getId()){
         case R.id.stoneButton:
            step = 0;
            break;
         case R.id.scissorsButton:
            step = 1;
            break;
         case R.id.paperButton:
            step = 2;
            break;
         case R.id.lizardButton:
            step = 3;
            break;
         case R.id.spokButton:
            step = 4;
            break;
      };

      if(stepCounter % 2 == 1) {
         playerOneStep = step;
         context.showCurrentStep(R.string.player_two);
      }
      else {
         playerTwoStep = step;
         context.showCurrentStep(R.string.player_one);
      }

      if(stepCounter % 2 == 0 && stepCounter > 0){
         Result result = gameMatrix[playerOneStep][playerTwoStep];
         context.setPlayerOneFigure(stepToLogo[playerOneStep]);
         context.setPlayerTwoFigure(stepToLogo[playerTwoStep]);
         //Обнуление переменных по итогам раунда
         playerOneStep = 0;
         playerTwoStep = 0;

         switch (result){
            case Lost:
               playerTwoScore++;
               message = context.getText(R.string.player_two_won);
               break;
            case Won:
               playerOneScore++;
               message = context.getText(R.string.player_one_won);
               break;
            case Draw:
               message = context.getText(R.string.draw);
               break;
         };

         context.showFigures();
         context.showRoundWinner(message);

         playerOneScoreTextView.setText(String.valueOf(playerOneScore));
         playerTwoScoreTextView.setText(String.valueOf(playerTwoScore));

         if(playerOneScore == stepsLimit || playerTwoScore == stepsLimit){
            winnerStringId = (playerOneScore > playerTwoScore) ?  R.string.player_one : R.string.player_two;
            gameoverPerfomance(true);
            context.showWinner(winnerStringId, playerOneScore, playerTwoScore);
         };
      }
   }
}