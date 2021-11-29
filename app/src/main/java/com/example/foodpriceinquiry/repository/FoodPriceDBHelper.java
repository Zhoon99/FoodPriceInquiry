package com.example.foodpriceinquiry.repository;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.foodpriceinquiry.domain.FoodPrice;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodPriceDBHelper extends SQLiteOpenHelper {
    private ArrayList<HashMap<String, String>> list;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "food_price_data";

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

    private static final String SQL_INSERT_QUERY =
            "insert into "+ FoodPrice.FoodPriceEntity.TABLE + " ( " +
                    "examin_de, examin_area_nm, prdlst_nm, prdlst_detail_nm, examin_amt, bfrt_examin_amt, stndrd, distb_step" + " ) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?, ? )";

    private static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + FoodPrice.FoodPriceEntity.TABLE;

    public FoodPriceDBHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    /**
     * 테이블을 생성하고 DB에 저장
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_QUERY);
        db.execSQL(SQL_CREATE_QUERY);
        Log.d("테이블 생성", "테이블 생성 성공");

        String[] bindArgs = null;
        for(int i = 0; i < list.size(); i++) {
            bindArgs = new String[] {
                    list.get(i).get("examin_de"),
                    list.get(i).get("examin_area_nm"),
                    list.get(i).get("prdlst_nm"),
                    list.get(i).get("prdlst_detail_nm"),
                    list.get(i).get("examin_amt"),
                    list.get(i).get("bfrt_examin_amt"),
                    list.get(i).get("stndrd"),
                    list.get(i).get("distb_step")
            };
            db.execSQL(SQL_INSERT_QUERY, bindArgs);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }

    public void setSrcData(ArrayList<HashMap<String, String>> list){
        this.list = list;
    }
}
