package com.example.foodpriceinquiry.service;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpriceinquiry.R;
import com.example.foodpriceinquiry.repository.FoodPriceVO;

public class FoodPriceViewHolder extends RecyclerView.ViewHolder {

    private TextView examin_area_nm, prdlst_nm, examin_amt, bfrt_examin_amt, stndrd, distb_step;

    public FoodPriceViewHolder(@NonNull View itemView) {
        super(itemView);
        examin_area_nm = itemView.findViewById(R.id.examin_area_nm);
        prdlst_nm = itemView.findViewById(R.id.prdlst_nm);
        examin_amt = itemView.findViewById(R.id.examin_amt);
        bfrt_examin_amt = itemView.findViewById(R.id.bfrt_examin_amt);
        stndrd = itemView.findViewById(R.id.stndrd);
        distb_step = itemView.findViewById(R.id.distb_step);
    }

    public void bind(FoodPriceVO vo) {
        examin_area_nm.setText(vo.getExamin_area_nm());
        prdlst_nm.setText(vo.getPrdlst_nm());
        examin_amt.setText(vo.getExamin_amt());
        bfrt_examin_amt.setText(vo.getBfrt_examin_amt());
        stndrd.setText(vo.getStndrd());
        distb_step.setText(vo.getDistb_step());
    }
}

