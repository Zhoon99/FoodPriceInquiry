package com.example.foodpriceinquiry.repository;

public class FoodPriceVO {
    private String examin_de;
    private String examin_area_nm;
    private String prdlst_nm;
    private String prdlst_detail_nm;
    private String examin_amt;
    private String bfrt_examin_amt;
    private String stndrd;
    private String distb_step;

    public FoodPriceVO() {}

    public String getExamin_de() {
        return examin_de;
    }

    public void setExamin_de(String examin_de) {
        this.examin_de = examin_de;
    }

    public String getExamin_area_nm() {
        return examin_area_nm;
    }

    public void setExamin_area_nm(String examin_area_nm) {
        this.examin_area_nm = examin_area_nm;
    }

    public String getPrdlst_nm() {
        return prdlst_nm;
    }

    public void setPrdlst_nm(String prdlst_nm) {
        this.prdlst_nm = prdlst_nm;
    }

    public String getPrdlst_detail_nm() {
        return prdlst_detail_nm;
    }

    public void setPrdlst_detail_nm(String prdlst_detail_nm) {
        this.prdlst_detail_nm = prdlst_detail_nm;
    }

    public String getExamin_amt() {
        return examin_amt;
    }

    public void setExamin_amt(String examin_amt) {
        this.examin_amt = examin_amt;
    }

    public String getBfrt_examin_amt() {
        return bfrt_examin_amt;
    }

    public void setBfrt_examin_amt(String bfrt_examin_amt) {
        this.bfrt_examin_amt = bfrt_examin_amt;
    }

    public String getStndrd() {
        return stndrd;
    }

    public void setStndrd(String stndrd) {
        this.stndrd = stndrd;
    }

    public String getDistb_step() {
        return distb_step;
    }

    public void setDistb_step(String distb_step) {
        this.distb_step = distb_step;
    }
}

