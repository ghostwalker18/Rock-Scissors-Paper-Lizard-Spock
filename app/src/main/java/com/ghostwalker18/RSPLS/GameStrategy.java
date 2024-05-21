package com.ghostwalker18.RSPLS;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import static androidx.core.content.ContextCompat.getSystemService;

public abstract class GameStrategy implements View.OnClickListener {
   public enum AUDIOMODE{
      SILENT, VIBRO, SOUND
   }
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
   protected AUDIOMODE audioMode = AUDIOMODE.SILENT;
   protected Vibrator vibrator;
   protected MediaPlayer mediaPlayer;

   public GameStrategy(@NonNull MainActivity context){
      this.context = context;
      vibrator  = getSystemService(this.context, Vibrator.class);
      playerOneScoreTextView = context.findViewById(R.id.playerScoreTextView);
      playerTwoScoreTextView = context.findViewById(R.id.computerScoreTextView);
   }

   protected void gameoverPerfomance(boolean isWin){
      int songId;
      if(isWin){
         songId = R.raw.win_sound;
      }
      else{
         songId = R.raw.fail_sound;
      }
      switch (audioMode){
         case SOUND:
            if(mediaPlayer != null)
               mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(context, songId);
            mediaPlayer.start();
            break;
      }
   }

   @Override
   public void onClick(View view){
      view.clearAnimation();
      AnimatorSet animation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.button_pressed);
      animation.setTarget(view);
      animation.start();
      switch (audioMode){
         case SOUND:
            if(mediaPlayer != null)
               mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(context, R.raw.button_click_sound);
            mediaPlayer.start();
            break;
         case VIBRO:
            VibrationEffect effect = VibrationEffect.createOneShot(100, 128);
            vibrator.vibrate(effect);
            break;
      }
   }

   public void setAudioMode(AUDIOMODE mode){
      this.audioMode = mode;
   }

   public void setStepsLimit(int stepsLimit){
      this.stepsLimit = stepsLimit;
   }

   public abstract void restart();

   public abstract String getName();

   public abstract int[] getPlayersNamesIds();
}