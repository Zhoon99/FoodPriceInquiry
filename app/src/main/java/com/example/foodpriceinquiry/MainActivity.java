package com.example.foodpriceinquiry;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodpriceinquiry.repository.FoodPriceDBHelper;

public class MainActivity extends AppCompatActivity {

    private FoodPriceDBHelper dbHelper = new FoodPriceDBHelper(this);
    private TextView tv;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O) //지정한 API 보다 낮은 API에서 함수를 호출하기 위해 사용
            @Override
            public void run() {
                dbHelper.deleteData();
                dbHelper.getFoodPriceData();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void ClickBtn(View view){
        Intent intent = new Intent(MainActivity.this, GPSActivity.class);
        startActivity(intent);
    }
}