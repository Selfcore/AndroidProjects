package com.example.androidprojects;

import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidprojects.classes.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MIN_AGE = 21;
    private static final int MAX_AGE = 40;
    private static final int MIN_SALARY = 1000;
    private static final int MAX_SALARY = 2000;
    private static final int MIN_THRESHOLD = 10;

    private SeekBar salarySeekBar;
    private TextView salaryValueTextView;
    private LinearLayout questionsLayout, summaryLayout;

    private CheckBox[] checkBoxesAnswers;
    private List<Question> questions;
    private List<RadioGroup> radioGroups;

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
        questionsLayout = findViewById(R.id.questionsLayout);
        summaryLayout = findViewById(R.id.summaryLayout);

        CheckBox experienceCheckBox = findViewById(R.id.experienceCheckBox);
        CheckBox teamworkCheckBox = findViewById(R.id.teamworkSkillCheckBox);
        CheckBox readyForTravelCheckBox = findViewById(R.id.readyForTravelCheckBox);

        checkBoxesAnswers = new CheckBox[] {
                experienceCheckBox,
                teamworkCheckBox,
                readyForTravelCheckBox
        };

        questions = getQuestions();

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

        radioGroups = new ArrayList<>();

        for (Question question : questions) {
            TextView questionText = new TextView(this);
            questionText.setText(question.getQuestionText());
            questionText.setTextSize(16f);
            questionsLayout.addView(questionText);

            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.VERTICAL);
            for (int j = 0; j < question.getAnswers().size(); j++) {
                RadioButton rb = new RadioButton(this);
                rb.setText(question.getAnswers().get(j));
                radioGroup.addView(rb);
            }

            radioGroups.add(radioGroup);
            questionsLayout.addView(radioGroup);
        }
    }

    private boolean isAgeValid(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }


    public void submitAnswers(View view) {
        int questionResult = calculateQuestionGrade();
        int checkBoxesResult = calculateCheckBoxGrade();

        int summary = questionResult + checkBoxesResult;

        if(summary < MIN_THRESHOLD) {
            Toast.makeText(this, "Нажаль Ви не пройшли", Toast.LENGTH_LONG).show();
            return;
        }

        summaryLayout.setVisibility(View.VISIBLE);
    }

    private int calculateQuestionGrade() {
        int result = 0;

        for (int index = 0; index < questions.size(); index++) {
            RadioGroup group = radioGroups.get(index);
            int selectedId = group.getCheckedRadioButtonId();

            if (selectedId == -1) continue;

            View selectedRadioButton = group.findViewById(selectedId);
            int selectedIndex = group.indexOfChild(selectedRadioButton);

            if (selectedIndex == questions.get(index).getCorrectAnswerIndex()) {
                result += 2;
            }
        }

        return result;
    }

    private int calculateCheckBoxGrade() {
        int result = 0;

        if(checkBoxesAnswers[0].isChecked())
            result += 2;

        if(checkBoxesAnswers[1].isChecked())
            result += 1;

        return result;
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "Що таке JVM?",
                Arrays.asList("Java Visual Machine", "Java Virtual Machine", "Java Verified Mode"),
                1
        ));

        questions.add(new Question(
                "Що таке OOP?",
                Arrays.asList("Об'єктно-орієнтоване програмування", "Оптимізоване обчислення процесів", "Операційне обслуговування програми"),
                0
        ));

        questions.add(new Question(
                "Який тип даних у Java для дробових чисел?",
                Arrays.asList("int", "String", "double"),
                2
        ));

        questions.add(new Question(
                "Що таке Android Activity?",
                Arrays.asList("Файл ресурсів", "Екран або вікно", "Метод класу"),
                1
        ));

        questions.add(new Question(
                "Що означає XML в Android?",
                Arrays.asList("X-Markup Language", "Extensible Markup Language", "Extended Machine Language"),
                1
        ));

        return questions;
    }
}