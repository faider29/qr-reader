package ru.diploma.qr.ui.history.generate;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGSaver;
import ru.diploma.qr.R;
import ru.diploma.qr.ui.history.generate.adapter.GenerateAdapter;
import ru.diploma.qr.ui.history.generate.model.GenerateModel;

/**
 * Created by Maxim Andrienko
 * 4/27/20
 */
public class GeneratedHistory extends Fragment {

    private List<GenerateModel> generateModelList;
    private TextView tvPlaceHolder;
    private RecyclerView recyclerView;
    private GenerateAdapter generateAdapter;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_generate_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tvPlaceHolder = view.findViewById(R.id.tv_place_holder);
        recyclerView = view.findViewById(R.id.rv_generate_history);
        generateAdapter = new GenerateAdapter(generateModelList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(generateAdapter);

        if (generateModelList == null) {
            tvPlaceHolder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvPlaceHolder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<GenerateModel>>() {
        }.getType();
        generateModelList = gson.fromJson(json, type);
        if (generateModelList == null) {
            generateModelList = new ArrayList<>();
        }
    }

    private void removeSelectedItem() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(generateModelList);
        editor.putString("taskList", json);
        editor.apply();
    }

    private void saveQrCode(GenerateModel generateModel) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                boolean save = new QRGSaver().save(savePath, generateModel.getText().trim(), generateModel.getImg().getBitmap(), QRGContents.ImageType.IMAGE_JPEG);
                String result = save ? "QR-код сохранен" : "Не удалось сохранить";
                showToast(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 121: {
                saveQrCode(generateModelList.get(item.getGroupId()));
                return true;
            }
            case 122: {
                generateAdapter.removeItem(item.getGroupId());
                removeSelectedItem();
                showToast("QR-код удален из истории");
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
