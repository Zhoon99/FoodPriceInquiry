package com.example.foodpriceinquiry;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpriceinquiry.repository.FoodPriceDBHelper;
import com.example.foodpriceinquiry.service.FoodPriceAdapter;

public class ResultActivity extends AppCompatActivity {

    private FoodPriceDBHelper dbHelper = new FoodPriceDBHelper(this);
    private RecyclerView foodPriceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent rcvIntent = getIntent();
        String location = rcvIntent.getStringExtra("지역");
        String food = rcvIntent.getStringExtra("식품").trim();

        foodPriceList = findViewById(R.id.foodPrice_list);
        foodPriceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        FoodPriceAdapter adapter = new FoodPriceAdapter(dbHelper.getLocalFoodPriceData(locationSearchQuery(location), food));
        foodPriceList.setAdapter(adapter);
    }

    private String locationSearchQuery(String location) {
        if(location.contains("서울")){
            return " WHERE examin_area_nm like '서울%'";
        }else if(location.contains("부산")) {
            return " WHERE examin_area_nm like '부산%'";
        }else if(location.contains("대구")) {
            return " WHERE examin_area_nm like '대구%'";
        }else if(location.contains("인천")) {
            return " WHERE examin_area_nm like '인천%";
        }else if(location.contains("광주")) {
            return " WHERE examin_area_nm like '광주%'";
        }else if(location.contains("대전")) {
            return " WHERE examin_area_nm like '대전%'";
        }else if(location.contains("울산")) {
            return " WHERE examin_area_nm like '울산%'";
        }else if(location.contains("세종")) {
            return " WHERE examin_area_nm like '세종%'";
        }else if(location.contains("경기")) {
            return " WHERE examin_area_nm like '경기%'";
        }else if(location.contains("강원")) {
            return " WHERE examin_area_nm like '강원%'";
        }else if(location.contains("충청북도")) {
            return " WHERE examin_area_nm like '충북%'";
        }else if(location.contains("충청남도")) {
            return " WHERE examin_area_nm like '충남%'";
        }else if(location.contains("경상북도")) {
            return " WHERE examin_area_nm like '경북%'";
        }else if(location.contains("경상남도")) {
            return " WHERE examin_area_nm like '경남%'";
        }else if(location.contains("전라북도")) {
            return " WHERE examin_area_nm like '전북%'";
        }else if(location.contains("전라남도")) {
            return " WHERE examin_area_nm like '전남%'";
        }else if(location.contains("제주")) {
            return " WHERE examin_area_nm like '제주%'";
        }
        return " WHERE examin_area_nm like '서울%'";
    }

}
