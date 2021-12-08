package com.example.foodpriceinquiry.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import androidx.annotation.RequiresApi;

import com.example.foodpriceinquiry.domain.FoodPrice;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FoodPriceDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "food-price";

    private static final String SQL_CREATE_QUERY =
            String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                    FoodPrice.FoodPriceEntity.TABLE,
                    FoodPrice.FoodPriceEntity._ID,
                    FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_DE,
                    FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AREA_NM,
                    FoodPrice.FoodPriceEntity.COLUMN_PRDLST_NM,
                    FoodPrice.FoodPriceEntity.COLUMN_PRDLST_DETAIL_NM,
                    FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AMT,
                    FoodPrice.FoodPriceEntity.COLUMN_BFRT_EXAMIN_AMT,
                    FoodPrice.FoodPriceEntity.COLUMN_STNDRD,
                    FoodPrice.FoodPriceEntity.COLUMN_DISTB_STEP
            );

    private static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + FoodPrice.FoodPriceEntity.TABLE;

    public FoodPriceDBHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }

    /**
     * 식품 물가 데이터 DB에 저장
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getFoodPriceData() {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        LocalDate now = LocalDate.now(); //현재 시간을 가져와 형식에 맞게 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);

        String numOfRows = "10000";
        String pageNo = "1";
        String delng_de = formatedNow;

        //api 자체에서 25일 이후부터 전일조사가격(bfrt_examin_amt)을 가져오지 못해서 25일로 고정했습니다.
        String queryUrl = "http://apis.data.go.kr/B552895/LocalGovPriceInfoService/getItemPriceResearchSearch?serviceKey=EhHp2jOVnNudSTpeWjjgBwaz2wuMIRtjEOKPWMZhtaFGNBjgM%2BvDUwd08w5UQSqmrRJpYRLFVJSqzwQE9hi6FQ%3D%3D&numOfRows=10000&pageNo=1&_returnType=xml&examin_de=20211125";

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream(); //입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //InputStream 에서 xml 가져오기

            String tag;
            xpp.next();
            int eventType = xpp.getEventType();

            //
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ; //<item>
                        else if(tag.equals("examin_de")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_DE, xpp.getText());
                        }
                        else if(tag.equals("examin_area_nm")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AREA_NM, xpp.getText());
                        }
                        else if(tag.equals("prdlst_detail_nm")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_PRDLST_DETAIL_NM, xpp.getText());
                        }
                        else if(tag.equals("prdlst_nm")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_PRDLST_NM, xpp.getText());
                        }
                        else if(tag.equals("examin_amt")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AMT, xpp.getText());
                        }
                        else if(tag.equals("bfrt_examin_amt")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_BFRT_EXAMIN_AMT, xpp.getText());
                        }
                        else if(tag.equals("stndrd")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_STNDRD, xpp.getText());
                        }
                        else if(tag.equals("distb_step")) {
                            xpp.next();
                            if(xpp.getText() != null)
                                contentValues.put(FoodPrice.FoodPriceEntity.COLUMN_DISTB_STEP, xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) db.insert(FoodPrice.FoodPriceEntity.TABLE, null, contentValues); //</item>
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 지역 물가 정보 검색
     */
    public Cursor searchLocalData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] localData = {
                BaseColumns._ID,
                FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_DE,
                FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AREA_NM,
                FoodPrice.FoodPriceEntity.COLUMN_PRDLST_NM,
                FoodPrice.FoodPriceEntity.COLUMN_PRDLST_DETAIL_NM,
                FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AMT,
                FoodPrice.FoodPriceEntity.COLUMN_BFRT_EXAMIN_AMT,
                FoodPrice.FoodPriceEntity.COLUMN_STNDRD,
                FoodPrice.FoodPriceEntity.COLUMN_DISTB_STEP
        };

        String selection = FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AREA_NM + " = ?";
        String[] selectionArgs = { "서울" };

        String sortPrice = FoodPrice.FoodPriceEntity.COLUMN_EXAMIN_AMT + " ASC";

        Cursor cursor = db.rawQuery("SELECT * FROM food_price", null);

        return cursor;
    }
}
