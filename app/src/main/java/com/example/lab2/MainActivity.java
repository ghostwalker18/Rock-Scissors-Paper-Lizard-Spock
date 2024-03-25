package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int computerScore;
    private TextView computerScoreTextView;

    private int playerScore;
    private TextView playerScoreTextView;
    private int stepsLimit = 3;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private Random AI = new Random();
    private int[][] gameMatrix = new int[][]{
            {0, -1, 1},
            {1, 0, -1},
            {-1, 1, 0}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        playerScoreTextView = findViewById(R.id.playerScoreTextView);
        computerScoreTextView = findViewById(R.id.computerScoreTextView);
        ImageButton stoneButton = (ImageButton)findViewById(R.id.stoneButton);
        stoneButton.setOnClickListener(this);
        ImageButton scissorsButton = (ImageButton)findViewById(R.id.scissorsButton);
        scissorsButton.setOnClickListener(this);
        ImageButton paperButton = (ImageButton)findViewById(R.id.paperButton);
        paperButton.setOnClickListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceChangeListener = (sharedPreferences, s) -> {
            if(s.equals("pointsNumber")){
                stepsLimit = Integer.parseInt(sharedPreferences.getString(s, "3"));
                computerScore = 0;
                computerScoreTextView.setText("0");
                playerScore = 0;
                playerScoreTextView.setText("0");
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_info:
                startActivity(new Intent(this, InfoActivity.class));
                break;
        };
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int playerStep = 0;
        CharSequence message = "0";
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
        }
        int computerStep = AI.nextInt(3);
        int result = gameMatrix[playerStep][computerStep];
        switch (result){
            case -1:
                computerScore++;
                message = getText(R.string.computer_won);
                break;
            case 1:
                playerScore++;
                message = getText(R.string.player_won);
                break;
            case 0:
                message = getText(R.string.draw);
                break;
        };
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        playerScoreTextView.setText(String.valueOf(playerScore));
        computerScoreTextView.setText(String.valueOf(computerScore));
        if(playerScore == stepsLimit || computerScore == stepsLimit){
            String finalMessage =  getText(R.string.game_over).toString() + " : " + (playerScore > computerScore ?  getText(R.string.you).toString() : getText(R.string.computer).toString());
            Toast toast1 = Toast.makeText(getApplicationContext(), finalMessage, Toast.LENGTH_SHORT);
            computerScore = 0;
            computerScoreTextView.setText("0");
            playerScore = 0;
            playerScoreTextView.setText("0");
            toast1.show();
        }
    }
}