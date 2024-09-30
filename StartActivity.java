package com.mobilprogramlar.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }



    // Tek Oyunculu (Kolay) Başlatma
    public void startSinglePlayerEasy(View view) {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra("mode", "single");
        intent.putExtra("difficulty", "easy"); // Kolay mod
        startActivity(intent);
    }

    // Tek Oyunculu (Orta) Başlatma
    public void startSinglePlayerMedium(View view) {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra("mode", "single");
        intent.putExtra("difficulty", "medium"); // Orta mod
        startActivity(intent);
    }

    // Tek Oyunculu (Zor) Başlatma
    public void startSinglePlayerHard(View view) {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra("mode", "single");
        intent.putExtra("difficulty", "hard"); // Zor mod
        startActivity(intent);
    }

    // İki Oyunculu Başlatma
    public void startTwoPlayer(View view) {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra("mode", "two");
        startActivity(intent);
    }

    // Dinamik Zorluk Başlatma
    public void startDynamicMode(View view) {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra("mode", "dynamic");
        startActivity(intent);
    }

    // Eğitici Bölüm Başlatma
    public void startTutorialMode(View view) {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra("mode", "tutorial");
        startActivity(intent);
    }













}

