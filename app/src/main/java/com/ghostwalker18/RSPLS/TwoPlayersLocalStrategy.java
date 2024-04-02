package com.ghostwalker18.RSPLS;

import android.view.View;
import android.widget.Toast;

class TwoPlayersLocalStrategy extends GameStrategy {
   private int playerOneScore = 0;
   private int playerTwoScore = 0;
   int playerOneStep = 0;
   int playerTwoStep = 0;
   private int stepCounter = 0;

   public TwoPlayersLocalStrategy(MainActivity context) {
      super(context);
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

      if(stepCounter % 2 == 1)
         playerOneStep = step;
      else
         playerTwoStep = step;

      if(stepCounter % 2 == 0 && stepCounter > 0){
         int result = gameMatrix[playerOneStep][playerTwoStep];
         playerOneStep = 0;
         playerTwoStep = 0;

         switch (result){
            case -1:
               playerTwoScore++;
               message = context.getText(R.string.player_two_won);
               break;
            case 1:
               playerOneScore++;
               message = context.getText(R.string.player_one_won);
               break;
            case 0:
               message = context.getText(R.string.draw);
               break;
         };

         Toast toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
         toast.show();

         playerOneScoreTextView.setText(String.valueOf(playerOneScore));
         playerTwoScoreTextView.setText(String.valueOf(playerTwoScore));

         if(playerOneScore == stepsLimit || playerTwoScore == stepsLimit){
            winnerStringId = (playerOneScore > playerTwoScore) ?  R.string.player_one : R.string.player_two;
            context.showWinner(winnerStringId, playerOneScore, playerTwoScore);
         };
      }
   }
}