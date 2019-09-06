package com.ahmedabdelmajeedkhozam.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedabdelmajeedkhozam.myapplication.model.Car;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private List<Car> cars;
    public  static OnRecyclerViewItemClickListener listener;
Context context;
    public CarRecyclerAdapter(Context context,List<Car> cars, OnRecyclerViewItemClickListener listener) {
        this.cars = cars;
        this.listener = listener;
        this.context=context;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout, parent, false);


        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {

        Car c = cars.get(position);
        if (c.getImage() != null && !c.getImage().isEmpty())
          //  holder.iv.setImageURI(Uri.parse(c.getImage()));
        Picasso.with(context).load(c.getImage()).into(holder.iv);
        else {
           // holder.iv.setImageResource(R.drawable.car_2);
            Picasso.with(context).load(R.drawable.car_1).into(holder.iv);
        }
        holder.tv_model.setText(c.getModel());
        holder.tv_color.setText(c.getColor());
        try {
            holder.tv_color.setTextColor(Color.parseColor(c.getColor()));
        }catch (Exception e){

        }

        holder.tv_dpl.setText(String.valueOf(c.getDpl()));
        holder.iv.setTag(c.getId());

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
