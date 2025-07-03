package com.example.fifteentwentygame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;



public class GamePlayActivity extends AppCompatActivity {
    Spinner spinnerLeft, spinnerRight, spinnerGuess;
    Button btnSubmit;
    TextView tvOpponent, tvResult;

    Integer[] handOptions = {0, 5, 10, 15};
    Integer[] guessOptions = {0, 5, 10, 15, 20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play_activity);

        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        spinnerGuess = findViewById(R.id.spinnerGuess);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvOpponent = findViewById(R.id.tvOpponent);
        tvResult = findViewById(R.id.tvResult);

        // 假設對手名字從Intent帶過來
        String opponentName = getIntent().getStringExtra("opponentName");
        tvOpponent.setText("opponent：" + opponentName);

        // 初始化 Spinner
        ArrayAdapter<Integer> adapterHand = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, handOptions);
        adapterHand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeft.setAdapter(adapterHand);
        spinnerRight.setAdapter(adapterHand);

        ArrayAdapter<Integer> adapterGuess = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, guessOptions);
        adapterGuess.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuess.setAdapter(adapterGuess);

        btnSubmit.setOnClickListener(v -> playerTurn());
    }

    private void playerTurn() {
        int left = (Integer) spinnerLeft.getSelectedItem();
        int right = (Integer) spinnerRight.getSelectedItem();
        int guess = (Integer) spinnerGuess.getSelectedItem();

        // 這裡呼叫API取得對手的出拳與猜測
        getOpponentMove(left, right, guess);
    }

    // 這裡示範用AsyncTask，你可用 OkHttp、Retrofit 或其他網路庫
    private void getOpponentMove(int playerLeft, int playerRight, int playerGuess) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://assign-mobileasignment-ihudikcgpf.cn-hongkong.fcapp.run");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    return new JSONObject(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(JSONObject opponentJson) {
                if (opponentJson != null) {
                    int opponentLeft = opponentJson.optInt("left");
                    int opponentRight = opponentJson.optInt("right");
                    // Ignore opponent's guess for player's turn
                    int total = playerLeft + playerRight + opponentLeft + opponentRight;
                    boolean win = (playerGuess == total);
                    String result = "Your guess: " + playerGuess +
                            "\nActual total: " + total +
                            "\nYou " + (win ? "win!" : "did not guess correctly.");
                    tvResult.setText(result);
                    // TODO: Show "Continue" button for next round if needed
                } else {
                    tvResult.setText("Network error, please try again.");
                }
            }
        }.execute();
    }
}