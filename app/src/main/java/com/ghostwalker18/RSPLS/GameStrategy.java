package com.ghostwalker18.RSPLS;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Map;

import androidx.annotation.NonNull;

public abstract class GameStrategy implements View.OnClickListener {
   protected MainActivity context;
   protected int stepsLimit = 3;
   protected int winnerStringId;
   protected final TextView playerOneScoreTextView;
   protected final TextView playerTwoScoreTextView;
   protected enum Result{
      Lost, Draw, Won
   };
   protected Result[][] gameMatrix = new Result[][]{
           {Result.Draw, Result.Won, Result.Lost, Result.Won, Result.Lost},
           {Result.Lost, Result.Draw, Result.Won, Result.Won, Result.Lost},
           {Result.Won, Result.Lost, Result.Draw, Result.Lost, Result.Won},
           {Result.Lost, Result.Lost, Result.Won, Result.Draw, Result.Won},
           {Result.Won, Result.Won, Result.Lost, Result.Lost, Result.Draw}
   };
   protected int[] stepToLogo = new int[]{
           R.drawable.stone, R.drawable.scissors, R.drawable.paper, R.drawable.lizard, R.drawable.spok
   };

   public GameStrategy(@NonNull MainActivity context){
      this.context = context;
      playerOneScoreTextView = context.findViewById(R.id.playerScoreTextView);
      playerTwoScoreTextView = context.findViewById(R.id.computerScoreTextView);
   }

   @Override
   public void onClick(View view){
      view.clearAnimation();
      AnimatorSet animation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.button_pressed);
      animation.setTarget(view);
      animation.start();
   }

   public void setStepsLimit(int stepsLimit){
      this.stepsLimit = stepsLimit;
   }

   public abstract void restart();

   public abstract String getName();

   public abstract int[] getPlayersNamesIds();
}