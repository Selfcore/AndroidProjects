package com.example.androidprojects;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView workingsTextView, resultTextView;
    private String workings = "";
    private String lastOperator = "";
    private String fullExpression = "";
    private double firstNumber = 0;
    private boolean isOperatorPressedLast = false;

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

        workingsTextView = findViewById(R.id.workingsTextView);
        resultTextView = findViewById(R.id.resultTextView);
    }

    public void clear(View view) {
        workings = "";
        fullExpression = "";
        lastOperator = "";
        firstNumber = 0;
        isOperatorPressedLast = false;
        workingsTextView.setText("");
        resultTextView.setText("");
    }

    public void handleOperationClick(View view) {
        String operator = ((TextView) view).getText().toString();

        if (!workings.isEmpty()) {
            try {
                firstNumber = Double.parseDouble(workings);
                lastOperator = operator;
                isOperatorPressedLast = true;

                fullExpression = workings + operator;
                workingsTextView.setText(fullExpression);
                resultTextView.setText("");
            } catch (NumberFormatException e) {
                resultTextView.setText("ERROR");
            }
        }
    }

    public void numberClick(View view) {
        String value = ((TextView) view).getText().toString();

        if (isOperatorPressedLast) {
            workings = "";
            isOperatorPressedLast = false;
        }

        workings += value;
        fullExpression += value;
        workingsTextView.setText(fullExpression);
    }

    public void handleEquals(View view) {
        if (lastOperator.isEmpty() || workings.isEmpty())
            return;

        Double secondNumber = parseNumber(workings);
        if (secondNumber == null) {
            resultTextView.setText("ERROR");
            return;
        }

        Double result = calculateResult(firstNumber, secondNumber, lastOperator);
        if (result == null) {
            resultTextView.setText("ERROR div by zero!!");
            return;
        }

        String resultStr = removeTrailingZero(result);
        workingsTextView.setText(fullExpression + "=" + resultStr);
        resultTextView.setText(resultStr);

        workings = resultStr;
        fullExpression = resultStr;
        lastOperator = "";
        isOperatorPressedLast = true;
    }

    private Double parseNumber(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double calculateResult(double first, double second, String operator) {
        switch (operator) {
            case "+": {
                return first + second;
            }
            case "-": {
                return first - second;
            }
            case "x": {
                return first * second;
            }
            case "/": {
                if (second == 0)
                    return null;
                return first / second;
            }
            case "^": {
                return Math.pow(first, second);
            }
            default:
                return null;
        }
    }

    private String removeTrailingZero(Double value) {
        if (value == value.longValue()) {
            return String.format("%d", value.longValue());
        } else {
            return String.valueOf(value);
        }
    }
}