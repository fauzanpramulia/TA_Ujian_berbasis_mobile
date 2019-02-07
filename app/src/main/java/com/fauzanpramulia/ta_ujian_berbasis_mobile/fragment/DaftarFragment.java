package com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.DaftarSoalActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.UjianClient;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.SoalAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.RetrofitClientInstance;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fauzanpramulia.ta_ujian_berbasis_mobile.DaftarSoalActivity.EXTRA_KODE;
import static com.fauzanpramulia.ta_ujian_berbasis_mobile.DaftarSoalActivity.EXTRA_NAMA_MK;
import static com.fauzanpramulia.ta_ujian_berbasis_mobile.DaftarSoalActivity.EXTRA_UJIAN_ID;

public class DaftarFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    SoalAdapter soalAdapter;
    ArrayList<SoalModel> absenList;
    Bundle bundle;

    public DaftarFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daftar, container,false);
        ButterKnife.bind(this,rootView);

        absenList = (ArrayList<SoalModel>)getArguments().getSerializable("daftarSoal");

        soalAdapter = new SoalAdapter(getActivity());
        soalAdapter.setHandler((SoalAdapter.OnItemClicked) getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        soalAdapter.setDataSoal(absenList);
        recyclerView.setAdapter(soalAdapter);

        return rootView;
    }

//    @Override
//    public void clik(SoalModel m) {
//        Toast.makeText(getActivity(), "Item yang dipilih : "+m.getId(), Toast.LENGTH_SHORT).show();
//        bundle = new Bundle();
//        bundle.putInt("id",m.getId());
//        bundle.putString("soal",m.getSoal());
//        bundle.putInt("tipe_soal",m.getTipe_soal());
//        bundle.putSerializable("options", (Serializable) m.getOptions());
//        soalFragment();
//        Intent i = new Intent(getActivity().getBaseContext(),
//                DaftarSoalActivity.class);
//
//        //PACK DATA
//        i.putExtra("data", "dataSoal");
//        i.putExtra("id", m.getId());
//
//        //START ACTIVITY
//        getActivity().startActivity(i);

//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        if(fm.getBackStackEntryCount()>0) {
//            fm.popBackStack();
//        }

//    }


}
