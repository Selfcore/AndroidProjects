package com.example.androidprojects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojects.Models.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private Context context;
    private List<Car> carList;

    public CarAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_car_layout, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.imageCar.setImageResource(car.getImageResourceId());
        holder.textBrandModel.setText(car.getBrand() + " " + car.getModel());
        holder.textYear.setText("Рік: " + car.getYear());
        holder.textCost.setText("Ціна: $" + car.getCost());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void updateList(List<Car> newList) {
        this.carList = newList;
        notifyDataSetChanged();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCar;
        TextView textBrandModel;
        TextView textYear;
        TextView textCost;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCar = itemView.findViewById(R.id.imageCar);
            textBrandModel = itemView.findViewById(R.id.textBrandModel);
            textYear = itemView.findViewById(R.id.textYear);
            textCost = itemView.findViewById(R.id.textCost);
        }
    }
}
