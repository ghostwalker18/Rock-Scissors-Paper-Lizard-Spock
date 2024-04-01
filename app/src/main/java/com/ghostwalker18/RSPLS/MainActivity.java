package com.ghostwalker18.RSPLS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener, EndGameFragment.OnNavigationButtonClickListener {
    private SharedPreferences prefs;
    private GameStrategy gameStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        gameStrategy = getStrategy(prefs);
        registerStrategy(gameStrategy);
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "pointsNumber":
                int stepsLimit = Integer.parseInt(sharedPreferences.getString(key, "3"));
                gameStrategy.setStepsLimit(stepsLimit);
                gameStrategy.restart();
                break;
            case "gameMode":
                gameStrategy = getStrategy(prefs);
                registerStrategy(gameStrategy);
        }
    }

    private void registerStrategy(GameStrategy gameStrategy){
        findViewById(R.id.stoneButton).setOnClickListener(gameStrategy);
        findViewById(R.id.scissorsButton).setOnClickListener(gameStrategy);
        findViewById(R.id.paperButton).setOnClickListener(gameStrategy);
        findViewById(R.id.lizardButton).setOnClickListener(gameStrategy);
        findViewById(R.id.spokButton).setOnClickListener(gameStrategy);
    }

    private GameStrategy getStrategy(SharedPreferences prefs){
        String gameMode = prefs.getString("gameMode", "AI");
        switch (gameMode){
            case "AI":
                return new OnePlayerStrategy(MainActivity.this);
            case "local":
                return new TwoPlayersLocalStrategy(MainActivity.this);
            case "BT":
                return new TwoPlayersRemoteStrategy(MainActivity.this);
            default:
                return null;
        }
    }

    public void showWinner(String winner, int playerOneScore, int playerTwoScore){
        Bundle args = new Bundle();
        args.putString("winner", winner);
        args.putInt("playerOneScore", playerOneScore);
        args.putInt("playerTwoScore", playerTwoScore);
        EndGameFragment endGameFragment = new EndGameFragment();
        endGameFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(endGameFragment, "endGameFragment")
                .commit();
    }

    @Override
    public void onNavigationButtonClicked(String action) {
        switch (action){
            case "exit":
                this.finish();
                break;
            case "replay":
                gameStrategy.restart();
                break;
        }
    }
}