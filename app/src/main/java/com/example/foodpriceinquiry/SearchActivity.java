package com.example.foodpriceinquiry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextView tvCurrentAddress = (TextView)findViewById(R.id.tv);
        EditText inputFood = (EditText)findViewById(R.id.edit);

        Intent rcvIntent = getIntent();
        String currentAddress = rcvIntent.getStringExtra("주소");
        tvCurrentAddress.setText(currentAddress);

        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = inputFood.getText().toString();
                Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                intent.putExtra("지역", getCurrentLocation(currentAddress));
                intent.putExtra("식품", food);
                startActivity(intent);
            }
        });
    }

    /**
     * 현재주소에서 지역 가져오기 (API 정보가 부족해 지역을 도 단위로 묶음)
     */
    private String getCurrentLocation(String address) {
        String[] splitAddress = address.split(" ");
        String location = splitAddress[1].trim();
        return location;
    }
}
