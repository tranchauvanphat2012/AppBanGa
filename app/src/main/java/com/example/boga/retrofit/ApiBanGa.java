package com.example.boga.retrofit;

import com.example.boga.model.GaMoiModel;
import com.example.boga.model.LoaiGaModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanGa {
    @GET("getloaiga.php")
    Observable<LoaiGaModel> getLoaiGa();

    @GET("getgamoi.php")
    Observable<GaMoiModel> getGaMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<GaMoiModel> getGa(
            @Field("page") int page,
            @Field("loai") int loai
    );

}
