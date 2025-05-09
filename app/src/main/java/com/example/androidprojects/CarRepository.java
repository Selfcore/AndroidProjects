package com.example.androidprojects;

import android.app.Application;

import com.example.androidprojects.Models.Car;

import java.util.ArrayList;
import java.util.List;

public class CarRepository extends Application {
    private List<Car> cars;

    @Override
    public void onCreate() {
        super.onCreate();
        cars = getInitialCars();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    private List<Car> getInitialCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("BMW", "X5", 2020, "Luxury SUV", 30000, R.drawable.bmw_x5));
        cars.add(new Car("BMW", "X5", 2020, "Luxury SUV", 45000, R.drawable.bmw_x5));
        return cars;
    }
}
