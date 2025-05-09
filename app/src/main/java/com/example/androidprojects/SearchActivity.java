package com.example.androidprojects;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidprojects.Models.Car;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView inputBrand, inputModel;
    private EditText inputYearFrom, inputYearTo, inputCostFrom, inputCostTo;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputBrand = findViewById(R.id.inputBrand);
        inputModel = findViewById(R.id.inputModel);
        inputYearFrom = findViewById(R.id.inputYearFrom);
        inputYearTo = findViewById(R.id.inputYearTo);
        inputCostFrom = findViewById(R.id.inputCostFrom);
        inputCostTo = findViewById(R.id.inputCostTo);
        buttonSearch = findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(v -> {
            List<Car> filteredCars = filterCars();
            if (!filteredCars.isEmpty()) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("filteredCars", new ArrayList<>(filteredCars));
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Нічого не знайдено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Car> filterCars() {
        String brand = inputBrand.getText().toString().trim();
        String model = inputModel.getText().toString().trim();
        String yearFromStr = inputYearFrom.getText().toString().trim();
        String yearToStr = inputYearTo.getText().toString().trim();
        String costFromStr = inputCostFrom.getText().toString().trim();
        String costToStr = inputCostTo.getText().toString().trim();

        int yearFrom = parseIntOrDefault(yearFromStr, 0);
        int yearTo = parseIntOrDefault(yearToStr, Integer.MAX_VALUE);
        int costFrom = parseIntOrDefault(costFromStr, 0);
        int costTo = parseIntOrDefault(costToStr, Integer.MAX_VALUE);

        CarRepository carRepository = (CarRepository) getApplication();

        List<Car> result = new ArrayList<>();
        for (Car car : carRepository.getCars()) {
            if (!brand.isEmpty() && !car.getBrand().toLowerCase().contains(brand.toLowerCase())) continue;
            if (!model.isEmpty() && !car.getModel().toLowerCase().contains(model.toLowerCase())) continue;
            if (car.getYear() < yearFrom || car.getYear() > yearTo) continue;
            if (car.getCost() < costFrom || car.getCost() > costTo) continue;

            result.add(car);
        }
        return result;
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
