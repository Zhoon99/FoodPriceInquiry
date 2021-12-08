package com.example.foodpriceinquiry;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.foodpriceinquiry.repository.FoodPriceDBHelper;

public class MainActivity extends AppCompatActivity {

    private FoodPriceDBHelper dbHelper = new FoodPriceDBHelper(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O) //지정한 API 보다 낮은 API에서 함수를 호출하기 위해 사용
            @Override
            public void run() {
                dbHelper.getFoodPriceData();
            }
        }).start();
    }

    /*private void printTable() {
        Cursor cursor = dbHelper.searchLocalData();
        String result1 = "";

        result1 += "row 개수 : " + cursor.getCount() + "\n";
        while (cursor.moveToNext()) {
            String examin_de = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_DE));
            String examin_area_nm = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AREA_NM));
            String prdlst_nm = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_PRDLST_NM));
            String prdlst_detail_nm = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_PRDLST_DETAIL_NM));
            String examin_amt = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AMT));
            String bfrt_examin_amt = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_BFRT_EXAMIN_AMT));
            String stndrd = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_STNDRD));
            String distb_step = cursor.getString(cursor.getColumnIndexOrThrow(FoodPrice.FoodPriceEntity.COLUMN_DISTB_STEP));

            result1 += examin_de+" "+examin_area_nm+" "+prdlst_nm+" "+prdlst_detail_nm+" "+examin_amt+" "+bfrt_examin_amt+" "+stndrd+" "+distb_step+"\n";
        }

        tv2.setText(result1);
        cursor.close();
    }*/

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