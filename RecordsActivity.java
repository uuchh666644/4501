package com.example.fifteentwentygame;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        ListView listViewRecords = findViewById(R.id.listViewRecords);

        // 假資料
        ArrayList<String> records = new ArrayList<>();
        records.add("2025-07-01 20:00 opponent:Tom result:Win round:2");
        records.add("2025-07-02 21:05 opponent:Mary result:Lose round:3");
        records.add("2025-07-03 09:30 opponent:John result:Win round:1");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, records);

        listViewRecords.setAdapter(adapter);
    }
}