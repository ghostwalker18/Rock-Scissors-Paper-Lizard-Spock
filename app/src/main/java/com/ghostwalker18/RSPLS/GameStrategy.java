package com.ghostwalker18.RSPLS;

import android.view.View;
import android.widget.TextView;

public abstract class GameStrategy implements View.OnClickListener {
   protected final MainActivity context;
   protected int stepsLimit = 3;
   protected String winner = "";
   protected final TextView playerOneScoreTextView;
   protected final TextView playerTwoScoreTextView;
   protected int[][] gameMatrix = new int[][]{
           {0, 1, -1, 1, -1},
           {-1, 0, 1, 1, -1},
           {1, -1, 0, -1, 1},
           {-1, -1, 1, 0, 1},
           {1, 1, -1, -1, 0}
   };

   public GameStrategy(MainActivity context){
      this.context = context;
      playerOneScoreTextView = context.findViewById(R.id.playerScoreTextView);
      playerTwoScoreTextView = context.findViewById(R.id.computerScoreTextView);
   }

   public void setStepsLimit(int stepsLimit){
      this.stepsLimit = stepsLimit;
   }

   public abstract void restart();
}