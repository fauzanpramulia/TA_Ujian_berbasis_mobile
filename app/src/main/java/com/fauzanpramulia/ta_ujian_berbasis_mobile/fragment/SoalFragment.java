package com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.OptionAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.SoalAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SoalFragment extends Fragment {
    @BindView(R.id.textNo) TextView textNo;
    @BindView(R.id.soalUjian) TextView soalUjian;
    Session session ;
    OptionAdapter optionAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    ArrayList<OptionModel> listOption;

    public SoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_soal, container, false);
        ButterKnife.bind(this,rootView);


        Bundle bundle = this.getArguments();
        textNo.setText("No : "+String.valueOf(bundle.getInt("id")));
        soalUjian.setText(bundle.getString("soal"));
        listOption = (ArrayList<OptionModel>)getArguments().getSerializable("options");

        optionAdapter = new OptionAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        optionAdapter.setDataOption(listOption);
        recyclerView.setAdapter(optionAdapter);


        return rootView;
    }

}
