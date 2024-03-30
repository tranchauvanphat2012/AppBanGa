package com.example.boga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boga.R;
import com.example.boga.model.GaMoi;

import java.text.DecimalFormat;
import java.util.List;

public class GaMoiAdapter extends RecyclerView.Adapter<GaMoiAdapter.MyViewHolder> {
    Context context;
    List<GaMoi> array;

    public GaMoiAdapter(Context context, List<GaMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ga_moi, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GaMoi gaMoi = array.get(position);
        holder.txtten.setText(gaMoi.getTengamoi());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: "+decimalFormat.format(Double.parseDouble(gaMoi.getGiaga()))+ "Đ");
        Glide.with(context).load(gaMoi.getHinhanh()).into(holder.imghinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtgia, txtten;
        ImageView imghinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemga_gia);
            txtten = itemView.findViewById(R.id.itemga_ten);
            imghinhanh = itemView.findViewById(R.id.itemga_image);
        }
    }
}
