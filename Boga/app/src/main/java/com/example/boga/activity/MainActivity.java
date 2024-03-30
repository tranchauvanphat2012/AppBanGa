package com.example.boga.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.boga.R;
import com.example.boga.adapter.GaMoiAdapter;
import com.example.boga.adapter.LoaiGaAdapter;
import com.example.boga.model.GaMoi;
import com.example.boga.model.LoaiGa;
import com.example.boga.retrofit.ApiBanGa;
import com.example.boga.retrofit.RetrofitClient;
import com.example.boga.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;

    LoaiGaAdapter loaiGaAdapter;
    List<LoaiGa> mangloaiga;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanGa apiBanGa;
    List<GaMoi> mangGaMoi;
    GaMoiAdapter gaMoiAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanGa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanGa.class);
        Anhxa();
        ActionBar();
        ActionViewFlipper();
        if(isConnected(this)){

            ActionViewFlipper();
            getLoaiGa();
            getGaMoi();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(), "Không có internet, vui long kết nối", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent gatre = new Intent(getApplicationContext(), GaTreActivity.class);
                        startActivity(gatre);
                        break;
                    case 2:
                        Intent ganoi = new Intent(getApplicationContext(), GaNoiActivity.class);
                        ganoi.putExtra("loai", 3);
                        startActivity(ganoi);
                        break;
                }
            }
        });
    }

    private void getGaMoi() {
        compositeDisposable.add(apiBanGa.getGaMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gaMoiModel -> {
                            if (gaMoiModel.isSuccess()){
                                mangGaMoi = gaMoiModel.getResult();
                                gaMoiAdapter = new GaMoiAdapter(getApplicationContext(), mangGaMoi);
                                recyclerViewManHinhChinh.setAdapter(gaMoiAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được với server"+throwable.getMessage(), Toast.LENGTH_LONG ).show();
                        }
                ));
    }

    private void getLoaiGa() {
        compositeDisposable.add(apiBanGa.getLoaiGa()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiGaModel -> {
                            if(loaiGaModel.isSuccess()){
                               mangloaiga = loaiGaModel.getResult();
                               loaiGaAdapter = new LoaiGaAdapter(getApplicationContext(),mangloaiga);
                               listViewManHinhChinh.setAdapter(loaiGaAdapter);
                            }
                        }
                ));

    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://traigaminhtri.com/wp-content/uploads/2022/11/Kelso_chuoi.jpg");
        mangquangcao.add("https://traigaminhtri.com/wp-content/uploads/2022/11/Sweater_dieu.jpg");
        mangquangcao.add("https://traigaminhtri.com/wp-content/uploads/2022/11/Ga-Hacht-2.jpg");
        for (int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_night);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_night);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void  Anhxa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        //khoi tao List
        mangloaiga = new ArrayList<>();
        mangGaMoi = new ArrayList<>();


    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}