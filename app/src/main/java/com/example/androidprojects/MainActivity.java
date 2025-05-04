package com.example.androidprojects;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private SeekBar salarySeekBar;
    private TextView salaryValueTextView;

    private CheckBox[] checkBoxesAnswers;

    private static final int MIN_AGE = 21;
    private static final int MAX_AGE = 40;
    private static final int MIN_SALARY = 1000;
    private static final int MAX_SALARY = 2000;

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

        salarySeekBar = findViewById(R.id.salarySeekBar);
        salaryValueTextView = findViewById(R.id.salaryValue);

        CheckBox experienceCheckBox = findViewById(R.id.experienceCheckBox);
        CheckBox teamworkCheckBox = findViewById(R.id.teamworkSkillCheckBox);
        CheckBox readyForTravelCheckBox = findViewById(R.id.readyForTravelCheckBox);

        checkBoxesAnswers = new CheckBox[] {
                experienceCheckBox,
                teamworkCheckBox,
                readyForTravelCheckBox
        };

        salarySeekBar.setMin(MIN_SALARY);
        salarySeekBar.setMax(MAX_SALARY);
        salarySeekBar.setProgress(MIN_SALARY);

        salaryValueTextView.setText(salarySeekBar.getProgress() + "$");

        salarySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int step = 100;

                int roundedProgress = (progress / step) * step;

                salarySeekBar.setProgress(roundedProgress);
                salaryValueTextView.setText(roundedProgress + "$");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private boolean isAgeValid(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }
}