package com.example.foodpriceinquiry;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.foodpriceinquiry.repository.FoodPriceDBHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ArrayList<HashMap<String, String>> list;
    private HashMap<String, String> map;
    private FoodPriceDBHelper dbHelper;
    private SQLiteDatabase db;
    private String examin_de, examin_area_nm, prdlst_nm, prdlst_detail_nm, examin_amt, bfrt_examin_amt, stndrd, distb_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());

        list = new ArrayList<HashMap<String, String>>();

        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                dbHelper = new FoodPriceDBHelper(MainActivity.this);
                dbHelper.setSrcData(getFoodPriceData()); //getFoodPriceData로 가져온 list FoodPriceDBHelper에 보내기
                db = dbHelper.getReadableDatabase();
            }
        }.start();
    }

    /**
     * 식품 물가 openAPI에서 데이터 가져오기
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<HashMap<String, String>> getFoodPriceData() {

        LocalDate now = LocalDate.now(); //현재 시간을 가져와 형식에 맞게 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);

        String numOfRows = "10000";
        String pageNo = "1";
        String delng_de = formatedNow;

        String queryUrl = "http://apis.data.go.kr/B552895/LocalGovPriceInfoService/getItemPriceResearchSearch?serviceKey=EhHp2jOVnNudSTpeWjjgBwaz2wuMIRtjEOKPWMZhtaFGNBjgM%2BvDUwd08w5UQSqmrRJpYRLFVJSqzwQE9hi6FQ%3D%3D&numOfRows="+numOfRows+"&pageNo="+pageNo+"&_returnType=xml&examin_de=20211126";

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream(); //입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );

            while(xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if(xpp.getEventType() == XmlPullParser.START_TAG) {
                    if(xpp.getName().equals("examin_de")) {
                        xpp.next();
                        examin_de = xpp.getText();
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getName().equals("examin_area_nm")) {
                                xpp.next();
                                examin_area_nm = xpp.getText();
                                break;
                            }
                        }
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getName().equals("prdlst_nm")) {
                                xpp.next();
                                prdlst_nm = xpp.getText();
                                break;
                            }
                        }
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getName().equals("prdlst_detail_nm")) {
                                xpp.next();
                                prdlst_detail_nm = xpp.getText();
                                break;
                            }
                        }
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getName().equals("examin_amt")) {
                                xpp.next();
                                examin_amt = xpp.getText();
                                break;
                            }
                        }
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getName().equals("bfrt_examin_amt")) {
                                xpp.next();
                                bfrt_examin_amt = xpp.getText();
                                break;
                            }
                        }
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getName().equals("stndrd")) {
                                xpp.next();
                                stndrd = xpp.getText();
                                break;
                            }
                        }
                        while(true) {
                            xpp.next();
                            if(xpp.getEventType()== XmlPullParser.START_TAG && xpp.getName().equals("distb_step")) {
                                xpp.next();
                                distb_step = xpp.getText();
                                break;
                            }
                        }
                        map = new HashMap<String, String>();
                        map.put("examin_de", examin_de);
                        map.put("examin_area_nm", examin_area_nm);
                        map.put("prdlst_nm", prdlst_nm);
                        map.put("prdlst_detail_nm", prdlst_detail_nm);
                        map.put("examin_amt", examin_amt);
                        map.put("bfrt_examin_amt", bfrt_examin_amt);
                        map.put("stndrd", stndrd);
                        map.put("distb_step", distb_step);
                        list.add(map);
                    }
                }
                xpp.next();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}