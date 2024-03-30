package com.example.boga.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.boga.R;
import com.example.boga.adapter.GaNoiAdapter;
import com.example.boga.model.GaMoi;
import com.example.boga.retrofit.ApiBanGa;
import com.example.boga.retrofit.RetrofitClient;
import com.example.boga.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GaNoiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanGa apiBanGa;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    GaNoiAdapter adapterGn;
    List<GaMoi> gaMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ga_noi);
        apiBanGa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanGa.class);
        loai = getIntent().getIntExtra("loai", 3);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isloading == false){
                    if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == gaMoiList.size()-1){
                        isloading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                gaMoiList.add(null);
                adapterGn.notifyItemInserted(gaMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remover null
                gaMoiList.remove(gaMoiList.size()-1);
                adapterGn.notifyItemRemoved(gaMoiList.size());
                page = page +1;
                getData(page);
                adapterGn.notifyDataSetChanged();
                isloading = false;
            }
        }, 2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanGa.getGa(page, loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   gaMoiModel -> {
                       if (gaMoiModel.isSuccess()){
                           if (adapterGn == null){
                               gaMoiList = gaMoiModel.getResult();
                               adapterGn = new GaNoiAdapter(getApplicationContext(), gaMoiList);
                               recyclerView.setAdapter(adapterGn);
                           }else {
                               int vitri = gaMoiList.size()-1;
                               int soluongadd = gaMoiModel.getResult().size();
                               for (int i =0; i<soluongadd; i++){
                                   gaMoiList.add(gaMoiModel.getResult().get(i));
                               }
                               adapterGn.notifyItemRangeInserted(vitri, soluongadd);
                           }

                       }else {
                           Toast.makeText(getApplicationContext(), "Hết dữ liệu rồi", Toast.LENGTH_LONG).show();
                           isloading = true;
                       }
                   },
                   throwable -> {
                       Toast.makeText(getApplicationContext(), "Khong ket noi server", Toast.LENGTH_LONG).show();
                   }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_gn);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        gaMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}