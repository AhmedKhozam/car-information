package com.ahmedabdelmajeedkhozam.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarViewHolder extends RecyclerView.ViewHolder {
    ImageView iv;
    TextView tv_model,tv_color,tv_dpl;


    public CarViewHolder(@NonNull View itemView) {
        super(itemView);
        iv=itemView.findViewById(R.id.custom_car_iv);
        tv_model=itemView.findViewById(R.id.custom_car_tv_model);
        tv_color=itemView.findViewById(R.id.custom_car_tv_color);
        tv_dpl=itemView.findViewById(R.id.custom_car_tv_dpl);

       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int id = (int) iv.getTag();
               CarRecyclerAdapter.listener.onItemClicked(id);
           }
       });


    }
}
