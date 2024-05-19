package com.ghostwalker18.RSPLS;

import android.view.View;
import androidx.annotation.NonNull;

public class TwoPlayersRemoteStrategy extends GameStrategy {
   public TwoPlayersRemoteStrategy(@NonNull MainActivity context) {
      super(context);
   }
   @Override
   public String getName(){
      return context.getResources().getStringArray(R.array.game_mode_entries)[2];
   }

   @Override
   public int[] getPlayersNamesIds() {
      return new int[] {R.string.player_one, R.string.player_two};
   }

   @Override
   public void restart() {

   }

   @Override
   public void onClick(View v) {

   }
}