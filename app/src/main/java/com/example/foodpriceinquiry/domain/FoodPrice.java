package com.example.foodpriceinquiry.domain;

import android.provider.BaseColumns;

public final class FoodPrice {
    private FoodPrice(){}

    public static class FoodPriceEntity implements BaseColumns {
        public static final String TABLE = "food_price";
        public static final String COLUMN_EXAMIN_DE = "examin_de";//조사 일
        public static final String COLUMN_EXAMIN_AREA_NM = "examin_area_nm";//조사 지역 명
        public static final String COLUMN_PRDLST_NM = "prdlst_nm";//품목 명
        public static final String COLUMN_PRDLST_DETAIL_NM = "prdlst_detail_nm";//품목 상세 명
        public static final String COLUMN_EXAMIN_AMT = "examin_amt";//조사 가격
        public static final String COLUMN_BFRT_EXAMIN_AMT = "bfrt_examin_amt";//전일 조사 가격
        public static final String COLUMN_STNDRD = "stndrd";//거래단위
        public static final String COLUMN_DISTB_STEP = "distb_step";//유통 단계
    }

}
