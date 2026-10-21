package com.example.fonts;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private final String PREFERENCES = "app";
    private final String KEY_INDEX = "index";
    private final String KEY_FONTSIZE = "fontsize";

    private SharedPreferences sharedPreferences;

    private TextView textViewFontSizeValue;
    private TextView textViewString;
    private SeekBar seekBar;
    private Button btnNext;
    private String[] strings;
    private int index;
    private int fontSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences(PREFERENCES, 0);

        textViewFontSizeValue = findViewById(R.id.textViewFontSizeValue);
        textViewString = findViewById(R.id.textViewString);
        seekBar = findViewById(R.id.seekBar);
        btnNext = findViewById(R.id.btnNext);

        fontSize = sharedPreferences.getInt(KEY_FONTSIZE, seekBar.getProgress());
        index = sharedPreferences.getInt(KEY_INDEX, 0);
        strings = new String[]{"Dzień dobry", "Good morning", "Buenos dias"};
        textViewString.setText(strings[index]);
        textViewString.setTextSize(fontSize);
        textViewFontSizeValue.setText(Integer.toString(fontSize));
        seekBar.setProgress(fontSize);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fontSize = progress;
                textViewFontSizeValue.setText(Integer.toString(fontSize));
                textViewString.setTextSize(fontSize);

                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit();
                editor.putInt(KEY_FONTSIZE, fontSize);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnNext.setOnClickListener(v -> next(v));
    }

    private void next(View view) {
        index = ++index % 3;
        textViewString.setText(strings[index]);

        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putInt(KEY_INDEX, index);
        editor.apply();
    }
}