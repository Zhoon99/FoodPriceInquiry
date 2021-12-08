package com.example.foodpriceinquiry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextView tvCurrentAddress = (TextView)findViewById(R.id.tv2);

        Intent intent = getIntent();
        String currentAddress = intent.getStringExtra("주소");
        tvCurrentAddress.setText(currentAddress);
    }
}
