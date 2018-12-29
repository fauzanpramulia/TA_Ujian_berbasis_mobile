package com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.AturanUjianActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.UjianModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionHolder> {
    ArrayList<OptionModel> dataOption;
    Context context;
    public void setDataOption(ArrayList<OptionModel> data) {
        this.dataOption = data;
        notifyDataSetChanged();
    }

    public OptionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OptionAdapter.OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new OptionAdapter.OptionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionAdapter.OptionHolder holder, final int position) {

        OptionModel option = dataOption.get(position);

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm a");
        Date date=null;

        holder.textOption.setText(option.getPilihan());

    }

    @Override
    public int getItemCount() {
        if (dataOption != null) {
            return dataOption.size();
        }
        return 0;
    }

    public class OptionHolder extends RecyclerView.ViewHolder {
        CheckBox textOp;
        TextView textOption;


        public OptionHolder(View itemView) {
            super(itemView);
            textOp = itemView.findViewById(R.id.textOp);
            textOption = itemView.findViewById(R.id.textOption);

        }
    }
}
