package com.ghostwalker18.RSPLS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener, EndGameFragment.OnNavigationButtonClickListener {
    private SharedPreferences prefs;
    private GameStrategy gameStrategy;
    private EndGameFragment endGameFragment = null;
    private RoundInfoFragment roundInfoFragment = null;

    private Toast notification = null;

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
        roundInfoFragment = RoundInfoFragment.newInstance(gameStrategy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.roundInfoContainer, roundInfoFragment)
                .commit();
        endGameFragment = (EndGameFragment) getSupportFragmentManager().findFragmentById(R.id.gameContainer);
        if(endGameFragment != null){
            findViewById(R.id.gameField).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(notification != null)
            notification.cancel();
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
        roundInfoFragment = RoundInfoFragment.newInstance(gameStrategy);
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

    public void showWinner(int winnerStringId, int playerOneScore, int playerTwoScore){
        Bundle args = new Bundle();
        args.putInt("winner", winnerStringId);
        args.putInt("playerOneScore", playerOneScore);
        args.putInt("playerTwoScore", playerTwoScore);
        endGameFragment = new EndGameFragment();
        endGameFragment.setArguments(args);
        findViewById(R.id.gameField).setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.gameContainer, endGameFragment)
                .commit();
    }

    public void showRoundWinner(CharSequence message){
        if(notification != null)
            notification.cancel();
        notification = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        notification.setGravity(Gravity.BOTTOM, 0, 0);
        //notification.show();
    }

    public void showCurrentStep(int id){
        roundInfoFragment.setCurrentStep(id);
    }
    public void setPlayerOneFigure(int id){
        roundInfoFragment.setPlayerOneFigure(id);
    }

    public void setPlayerTwoFigure(int id){
        roundInfoFragment.setPlayerTwoFigure(id);
    }

    public void showFigures(){
        roundInfoFragment.showFigures();
    }
    @Override
    public void onNavigationButtonClicked(String action) {
        switch (action){
            case "exit":
                this.finish();
                break;
            case "replay":
                endGameFragment = (EndGameFragment) getSupportFragmentManager().findFragmentById(R.id.gameContainer);
                gameStrategy.restart();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(endGameFragment)
                        .commitNow();
                findViewById(R.id.gameField).setVisibility(View.VISIBLE);
                break;
        }
    }
}