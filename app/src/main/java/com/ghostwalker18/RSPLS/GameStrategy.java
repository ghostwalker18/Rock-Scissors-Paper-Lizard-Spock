package com.ghostwalker18.RSPLS;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public abstract class GameStrategy implements View.OnClickListener {
   protected final Activity context;
   protected int stepsLimit = 3;
   protected final TextView playerScoreTextView;
   protected final TextView computerScoreTextView;
   protected int[][] gameMatrix = new int[][]{
           {0, 1, -1, 1, -1},
           {-1, 0, 1, 1, -1},
           {1, -1, 0, -1, 1},
           {-1, -1, 1, 0, 1},
           {1, 1, -1, -1, 0}
   };
   public GameStrategy(Activity context){
      this.context = context;
      playerScoreTextView = context.findViewById(R.id.playerScoreTextView);
      computerScoreTextView = context.findViewById(R.id.computerScoreTextView);
   }
   public void setStepsLimit(int stepsLimit){
      this.stepsLimit = stepsLimit;
   }
   public abstract void restart();
}