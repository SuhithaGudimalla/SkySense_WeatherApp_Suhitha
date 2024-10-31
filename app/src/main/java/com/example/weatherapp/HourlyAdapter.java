package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {

    private List<HourlyData> hourlyDataList;

    public HourlyAdapter(List<HourlyData> hourlyDataList) {
        this.hourlyDataList = hourlyDataList;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hourly, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        HourlyData data = hourlyDataList.get(position);
        holder.hourTxt.setText(data.getHour());
        holder.tempTxt.setText(data.getTemperature() + "°C");
        // You can set an appropriate icon based on weather conditions here
        // holder.imageView.setImageResource(...);
    }

    @Override
    public int getItemCount() {
        return hourlyDataList.size();
    }

    static class HourlyViewHolder extends RecyclerView.ViewHolder {
        TextView hourTxt;
        TextView tempTxt;
        ImageView imageView;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTxt = itemView.findViewById(R.id.hourTxt);
            tempTxt = itemView.findViewById(R.id.tempTxt);
            imageView = itemView.findViewById(R.id.imageView4);
        }
    }
}
