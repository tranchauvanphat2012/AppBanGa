package com.example.boga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boga.R;
import com.example.boga.model.GaMoi;

import java.text.DecimalFormat;
import java.util.List;

public class GaNoiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GaMoi> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public GaNoiAdapter(Context context, List<GaMoi> array) {
        this.context = context;
        this.array = array;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ganoi, parent, false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            GaMoi ga = array.get(position);
            myViewHolder.tenga.setText(ga.getTengamoi());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giaga.setText("Giá: "+decimalFormat.format(Double.parseDouble(ga.getGiaga()))+ "Đ");
            myViewHolder.mota.setText(ga.getMota());
            myViewHolder.idga.setText(ga.getId() +"");
            Glide.with(context).load(ga.getHinhanh()).into(myViewHolder.hinhanh);
        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public  class LoadingViewHolder extends  RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tenga, giaga, mota, idga;
        ImageView hinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenga = itemView.findViewById(R.id.itemgn_ten);
            giaga = itemView.findViewById(R.id.itemgn_gia);
            mota = itemView.findViewById(R.id.itemgn_mota);
            idga = itemView.findViewById(R.id.itemgn_idga);
            hinhanh = itemView.findViewById(R.id.itemgn_image);
        }
    }
}
