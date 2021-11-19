package com.example.foodpriceinquiry;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private String foodPrice_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=findViewById(R.id.tv1);
        tv1.setMovementMethod(new ScrollingMovementMethod());

        /**
         * 식품 물가 가져와서 출력하기
         */
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O) //지정한 API 보다 낮은 API에서 함수를 호출하기 위해 사용
            @Override
            public void run() {

                foodPrice_data= getFoodPriceData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText(foodPrice_data);
                    }
                });
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getFoodPriceData(){
        StringBuffer buffer=new StringBuffer();

        /**
         * delng_de(조회일자)에 들어가는 현재 날짜를 형식에 맞게 변환
         */
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);

        String numOfRows = "10000";
        String pageNo = "1";
        String delng_de = formatedNow;
        String area_code = "056";
        String std_prdlst_code = "101201";

        String queryUrl = "http://apis.data.go.kr/B552895/LocalGovPriceInfoService/getItemPriceResearchSearch?serviceKey=EhHp2jOVnNudSTpeWjjgBwaz2wuMIRtjEOKPWMZhtaFGNBjgM%2BvDUwd08w5UQSqmrRJpYRLFVJSqzwQE9hi6FQ%3D%3D&numOfRows=10000&pageNo=1&_returnType=xml,json&examin_de=20211110";

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            xpp.next();
            int eventType= xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ; //첫번째 검색결과
                        else if(tag.equals("examin_de")) {
                            buffer.append("조사 일 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("examin_area_cd")) {
                            buffer.append("조사 지역 코드 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("examin_area_nm")) {
                            buffer.append("조사 지역 명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("prdlst_detail_nm")) {
                            buffer.append("품목 상세 명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("prdlst_cd")) {
                            buffer.append("품목 코드 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("prdlst_nm")) {
                            buffer.append("품목 명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("examin_amt")) {
                            buffer.append("조사 가격 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("bfrt_examin_amt")) {
                            buffer.append("전일 조사 가격 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("stndrd")) {
                            buffer.append("거래 단위 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("distb_step")) {
                            buffer.append("유통 단계 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();//StringBuffer 문자열 객체 반환
    }

    public void ClickBtn(View view){

    }
}