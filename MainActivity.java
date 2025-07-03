package com.example.fifteentwentygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnPlay, btnRecords, btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 確認 XML 檔案名稱

        // 下面這一行要確保 Layout 最外層有 android:id="@+id/main"
        // findViewById(R.id.main); // 可選，如果你要設置 padding

        btnPlay = findViewById(R.id.btnPlay);
        btnRecords = findViewById(R.id.btnRecords);
        btnClose = findViewById(R.id.btnClose);

        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OpponentSelectActivity.class);
            startActivity(intent);
        });

        btnRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(intent);
        });

        btnClose.setOnClickListener(v -> finish());
    }
}