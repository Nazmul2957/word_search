package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.retrofit.ApiClient;
import com.example.myapplication.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    public ImageView image;
    public ProgressBar progressbar;
    Context context;
    public TextView text, text2;
    LocalDatabase db;
    SQLiteDatabase sqdb_write, sqdb_read;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        get_all_latest_data();

        //Find id
        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);
        progressbar = findViewById(R.id.progressbar);
        image = findViewById(R.id.image);


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/SolaimanLipi.ttf");
        text.setTypeface(font);
        text2.setTypeface(font);

        new Thread(new Runnable() {
            @Override
            public void run() {
                clockwise();
                doWork();
                startApp();
                finish();
            }
        }).start();

    }

    private void get_all_latest_data() {


        //DATABASE

        db = new LocalDatabase(this, "DataDictonary.db");
        sqdb_write = db.getWritableDatabase();
        sqdb_read = db.getReadableDatabase();


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<POJOGetLatestData>> call = apiInterface.get_all_data("0000-00-00");

        call.enqueue(new Callback<List<POJOGetLatestData>>() {
            @Override
            public void onResponse(Call<List<POJOGetLatestData>> call, Response<List<POJOGetLatestData>> response) {

                sqdb_write.execSQL("Delete from tbl_word_data");
                List<POJOGetLatestData> get_all_latest_data = response.body();

                if (response.code() == 200 && get_all_latest_data != null) {

                    final ContentValues values = new ContentValues();

                    for (int i = 0; i < get_all_latest_data.size(); i++) {

                        values.put("id", i);
                        values.put("Correct", get_all_latest_data.get(i).getRightWord().toString());
                        values.put("Wrong", get_all_latest_data.get(i).getWrongWord().toString());
                        long status = sqdb_write.insert("tbl_word_data", null, values);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<POJOGetLatestData>> call, Throwable t) {

                //Toast.makeText(SplashScreen.this, t.toString(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    public void clockwise() {

        @SuppressLint("ResourceType")
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.animation);
        image.startAnimation(animation);


    }


    private void doWork() {
        for (int progress = 0; progress < 100; progress++) {
            try {
                Thread.sleep(30);
                progressbar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startApp() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);

    }

}
