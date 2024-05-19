package com.ghostwalker18.RSPLS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RoundInfoFragment extends Fragment {

    private String gameMode;
    private int playerOneId;
    private int[] colors = new int[]{R.color.player1_color, R.color.player2_color};
    private int currentStepCounter = 0;
    private View view;

    public RoundInfoFragment(GameStrategy strategy) {
        gameMode = strategy.getName();
        playerOneId = strategy.getPlayersNamesIds()[0];
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_round_info, container, false);
        TextView gameModeView = view.findViewById(R.id.game_mod);
        gameModeView.setText(gameMode);
        TextView currentStep = view.findViewById(R.id.current_player);
        currentStep.setText(playerOneId);
        int color = getContext().getColor(colors[currentStepCounter]);
        currentStep.setTextColor(color);
        return view;
    }

    public void setCurrentStep(int playerId){
        currentStepCounter = (currentStepCounter + 1) % 2;
        TextView currentPlayerTextView = view.findViewById(R.id.current_player);
        currentPlayerTextView.setText(playerId);
        int color = getContext().getColor(colors[currentStepCounter]);
        currentPlayerTextView.setTextColor(color);
    }

    public void setPlayerOneFigure(int figureId){
        ImageView playerOneImageView = view.findViewById(R.id.player_one_figure);
        playerOneImageView.setVisibility(View.INVISIBLE);
        playerOneImageView.setImageResource(figureId);
    }

    public void setPlayerTwoFigure(int figureId){
        ImageView playerTwoImageView = view.findViewById(R.id.player_two_figure);
        playerTwoImageView.setVisibility(View.INVISIBLE);
        playerTwoImageView.setImageResource(figureId);
    }
    public void showFigures(){
        Animation fadeout = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        ImageView playerOneImageView = view.findViewById(R.id.player_one_figure);
        ImageView playerTwoImageView = view.findViewById(R.id.player_two_figure);
        playerOneImageView.clearAnimation();
        playerTwoImageView.clearAnimation();
        playerTwoImageView.setVisibility(View.VISIBLE);
        playerTwoImageView.setVisibility(View.VISIBLE);
        playerTwoImageView.startAnimation(fadeout);
        playerOneImageView.startAnimation(fadeout);
    }
}