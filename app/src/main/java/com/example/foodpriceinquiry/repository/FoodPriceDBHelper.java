package com.example.foodpriceinquiry.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.foodpriceinquiry.domain.FoodPrice;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FoodPriceDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
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
     * 물가 검색
     */
    public ArrayList<FoodPriceVO> getLocalFoodPriceData(String location, String food) {
        ArrayList<FoodPriceVO> foodPriceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FoodPrice.FoodPriceEntity.TABLE + ""+location+" AND prdlst_nm='"+food+"'", null);
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                String examin_de = cursor.getString(cursor.getColumnIndex("examin_de"));
                String examin_area_nm = cursor.getString(cursor.getColumnIndex("examin_area_nm"));
                String prdlst_nm = cursor.getString(cursor.getColumnIndex("prdlst_nm"));
                String prdlst_detail_nm = cursor.getString(cursor.getColumnIndex("prdlst_detail_nm"));
                String examin_amt = cursor.getString(cursor.getColumnIndex("examin_amt"));
                String bfrt_examin_amt = cursor.getString(cursor.getColumnIndex("bfrt_examin_amt"));
                String stndrd = cursor.getString(cursor.getColumnIndex("stndrd"));
                String distb_step = cursor.getString(cursor.getColumnIndex("distb_step"));

                FoodPriceVO vo = new FoodPriceVO();
                vo.setExamin_de(examin_de);
                vo.setExamin_area_nm(examin_area_nm);
                vo.setPrdlst_nm(prdlst_detail_nm + " " +prdlst_nm);
                //vo.setPrdlst_detail_nm(prdlst_detail_nm);
                vo.setExamin_amt(examin_amt);
                vo.setBfrt_examin_amt(bfrt_examin_amt);
                vo.setStndrd(stndrd);
                vo.setDistb_step(distb_step);

                foodPriceList.add(vo);
            }
        } else if (cursor.getCount() == 0) {
            Cursor noDataCursor = db.rawQuery("SELECT * FROM " + FoodPrice.FoodPriceEntity.TABLE + " WHERE examin_area_nm = '서울'", null);

            while(cursor.moveToNext()) {
                String examin_de = noDataCursor.getString(noDataCursor.getColumnIndex("examin_de"));
                String examin_area_nm = noDataCursor.getString(noDataCursor.getColumnIndex("examin_area_nm"));
                String prdlst_nm = noDataCursor.getString(noDataCursor.getColumnIndex("prdlst_nm"));
                String prdlst_detail_nm = noDataCursor.getString(noDataCursor.getColumnIndex("prdlst_detail_nm"));
                String examin_amt = noDataCursor.getString(noDataCursor.getColumnIndex("examin_amt"));
                String bfrt_examin_amt = noDataCursor.getString(noDataCursor.getColumnIndex("bfrt_examin_amt"));
                String stndrd = noDataCursor.getString(noDataCursor.getColumnIndex("stndrd"));
                String distb_step = noDataCursor.getString(noDataCursor.getColumnIndex("distb_step"));

                FoodPriceVO vo = new FoodPriceVO();
                vo.setExamin_de(examin_de);
                vo.setExamin_area_nm(examin_area_nm);
                vo.setPrdlst_nm(prdlst_detail_nm + " " +prdlst_nm);
                vo.setExamin_amt(examin_amt);
                vo.setBfrt_examin_amt(bfrt_examin_amt);
                vo.setStndrd(stndrd);
                vo.setDistb_step(distb_step);

                foodPriceList.add(vo);
            }
        }
        cursor.close();
        db.close();
        return foodPriceList;
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("SELECT * FROM " + FoodPrice.FoodPriceEntity.TABLE, null);
        db.close();
    }
}
