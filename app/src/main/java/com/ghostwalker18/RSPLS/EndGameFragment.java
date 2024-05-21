package com.ghostwalker18.RSPLS;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EndGameFragment extends Fragment implements View.OnClickListener {

   private String winner;
   private int playerOneScore;
   private int playerTwoScore;
   //Parent activity that must implements OnNavigationButtonClickedListener interface
   private OnNavigationButtonClickListener mListener;

   //Delegation of processing of button`s clicks to parent activity
   @Override
   public void onClick(View v) {
      switch (v.getId()){
         case R.id.exitButton:
            mListener.onNavigationButtonClicked("exit");
            break;
         case R.id.replayButton:
            mListener.onNavigationButtonClicked("replay");
            break;
      }
   }

   public interface OnNavigationButtonClickListener{
      public void onNavigationButtonClicked(String action);
   }

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      winner = getString(getArguments().getInt("winner"));
      playerOneScore = getArguments().getInt("playerOneScore");
      playerTwoScore = getArguments().getInt("playerTwoScore");
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_end_game, container, false);
      TextView winnerTextView = view.findViewById(R.id.winnerTextView);
      winnerTextView.setText(winner);
      String score = String.valueOf(playerOneScore) + " : " + String.valueOf(playerTwoScore);
      TextView scoreTextView = view.findViewById(R.id.scoreTextView);
      scoreTextView.setText(score);
      view.findViewById(R.id.replayButton).setOnClickListener(this);
      view.findViewById(R.id.exitButton).setOnClickListener(this);
      return view;
   }

   @Override
   public void onAttach(@NonNull Context context) {
      super.onAttach(context);
      if (context instanceof OnNavigationButtonClickListener) {
         //Attach listener (parent activity) to fragment
         mListener = (OnNavigationButtonClickListener) context;
      } else {
         throw new ClassCastException(context.toString()
                 + " must implement MyListFragment.OnItemSelectedListener");
      }
   }
}