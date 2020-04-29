package ru.diploma.qr.ui.history.scanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.diploma.qr.R;
import ru.diploma.qr.ui.history.generate.adapter.GenerateAdapter;
import ru.diploma.qr.ui.history.generate.model.GenerateModel;
import ru.diploma.qr.ui.history.scanner.adapter.ScanAdapter;
import ru.diploma.qr.ui.history.scanner.model.ScanModel;

/**
 * Created by Maxim Andrienko
 * 4/27/20
 */
public class ScanHistory extends Fragment {
    private List<ScanModel> scanHistoryList;
    private RecyclerView recyclerView;
    private ScanAdapter scanAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return  inflater.inflate(R.layout.fragment_scanner_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        recyclerView = view.findViewById(R.id.rv_scanner_history);
        scanAdapter= new ScanAdapter(scanHistoryList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scanAdapter);

    }

    private void loadData(){
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskListScanner", null);
        Type type = new TypeToken<ArrayList<ScanModel>>() {}.getType();
        scanHistoryList = gson.fromJson(json, type);
        if(scanHistoryList == null) {
            scanHistoryList = new ArrayList<>();
        }
    }
}
