package com.ghostwalker18.RSPLS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EntranceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        findViewById(R.id.startGameButton).setOnClickListener(this::onStartGameButtonClicked);
        findViewById(R.id.settingsButton).setOnClickListener(this::onSettingsButtonClicked);
        findViewById(R.id.faqButton).setOnClickListener(this::onInfoButtonClicked);
        findViewById(R.id.exitButton).setOnClickListener(this::onExitButtonClicked);
    }

    private void onStartGameButtonClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void onSettingsButtonClicked(View view){
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void onInfoButtonClicked(View view){
        startActivity(new Intent(this, InfoActivity.class));
    }

    private void onExitButtonClicked(View view){
        this.finish();
    }
}