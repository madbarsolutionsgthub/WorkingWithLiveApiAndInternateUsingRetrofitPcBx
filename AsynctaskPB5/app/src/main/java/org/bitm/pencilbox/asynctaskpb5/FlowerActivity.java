package org.bitm.pencilbox.asynctaskpb5;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.bitm.pencilbox.asynctaskpb5.flower.Flower;
import org.bitm.pencilbox.asynctaskpb5.flower.FlowerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlowerActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://services.hanselandpetal.com/";
    private FlowerService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FlowerService.class);
        Call<List<Flower>> flowerCall = service.getAllFlowers();
        flowerCall.enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                if(response.code() == 200){
                    List<Flower> flowers = response.body();
                    Toast.makeText(FlowerActivity.this, "size: "+flowers.size(), Toast.LENGTH_SHORT).show();
                    String photo = flowers.get(0).getPhoto();
                    String fullPath = BASE_URL+"photos/"+photo;
                    //Picasso.get().load(Uri.parse(fullPath)).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {

            }
        });
    }
}
