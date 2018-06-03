package org.bitm.pencilbox.asynctaskpb5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String IMAGE_URL = "https://cdn.audubon.org/cdn/farfuture/z1llfSWNUyQF-71q9Bc4vDVhTOl2Hx3ihresEzphlnc/mtime:1517332224/sites/default/files/web_northern_cardinal_7_kk_michele_black.jpg";
    private ImageView imageView;
    private Handler handler;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);
        handler = new Handler();
    }

    public void downloadImage(View view) {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.isConnected()){
            //fetchImage();
            //new MyAsynctask().execute(IMAGE_URL);
            Picasso.get().load(Uri.parse(IMAGE_URL)).into(imageView);
        }else{
            Toast.makeText(this, "please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //runs in background thread
                try {
                    URL url = new URL(IMAGE_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //runs on main thread
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    private class MyAsynctask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            //runs on background thread
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //runs on main thread
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
