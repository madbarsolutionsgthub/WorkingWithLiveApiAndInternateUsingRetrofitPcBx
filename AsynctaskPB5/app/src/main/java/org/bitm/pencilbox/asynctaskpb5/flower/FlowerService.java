package org.bitm.pencilbox.asynctaskpb5.flower;

import org.bitm.pencilbox.asynctaskpb5.current.CurrentWeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mobile App on 6/2/2018.
 */

public interface FlowerService {
    @GET("feeds/flowers.json")
    Call<List<Flower>> getAllFlowers();

    @GET("weather?lat=23.7509&lon=90.3933&units=metric&appid=380199723cebdb85ef2e16cc30cee5b6")
    Call<CurrentWeatherResponse>getCurrentWeatherData();
}
