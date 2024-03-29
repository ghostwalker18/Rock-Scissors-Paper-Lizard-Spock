package com.ghostwalker18.RSPLS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity{
    private SharedPreferences prefs;
    private GameStrategy gameStrategy;
    private SharedPreferences.
            OnSharedPreferenceChangeListener preferenceChangeListener = (shPrefs, s) -> {
        switch (s){
            case "pointsNumber":
                int stepsLimit = Integer.parseInt(shPrefs.getString(s, "3"));
                gameStrategy.setStepsLimit(stepsLimit);
                gameStrategy.restart();
                break;
            case "gameMode":
                String gameMode = shPrefs.getString(s, "AI");
                switch (gameMode){
                    case "AI":
                        gameStrategy = new OnePlayerStrategy(MainActivity.this);
                        break;
                    case "local":
                        gameStrategy = new TwoPlayersLocalStrategy(MainActivity.this);
                        break;
                    case "BT":
                        gameStrategy = new TwoPlayersRemoteStrategy(MainActivity.this);
                        break;
                }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        gameStrategy = new OnePlayerStrategy(this);

        findViewById(R.id.stoneButton).setOnClickListener(gameStrategy);
        findViewById(R.id.scissorsButton).setOnClickListener(gameStrategy);
        findViewById(R.id.paperButton).setOnClickListener(gameStrategy);
        findViewById(R.id.lizardButton).setOnClickListener(gameStrategy);
        findViewById(R.id.spokButton).setOnClickListener(gameStrategy);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
}