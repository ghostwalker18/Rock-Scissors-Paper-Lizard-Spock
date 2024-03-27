package com.ghostwalker18.RSPLS;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EntranceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        findViewById(R.id.startGameButton).setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        findViewById(R.id.settingsButton).setOnClickListener(view -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
        findViewById(R.id.faqButton).setOnClickListener(view -> {
            startActivity(new Intent(this, InfoActivity.class));
        });
    }
}
