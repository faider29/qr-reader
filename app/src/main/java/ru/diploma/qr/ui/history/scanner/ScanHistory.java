package ru.diploma.qr.ui.history.scanner;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import ru.diploma.qr.R;
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
    private Bitmap bitmap;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_scanner_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        recyclerView = view.findViewById(R.id.rv_scanner_history);
        scanAdapter = new ScanAdapter(scanHistoryList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scanAdapter);

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskListScanner", null);
        Type type = new TypeToken<ArrayList<ScanModel>>() {
        }.getType();
        scanHistoryList = gson.fromJson(json, type);
        if (scanHistoryList == null) {
            scanHistoryList = new ArrayList<>();
        }
    }

    private void removeSelectedItem() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scanHistoryList);
        editor.putString("taskListScanner", json);
        editor.apply();
    }

    private void saveQrCode(ScanModel scanModel) {
        QRGEncoder qrgEncoder = new QRGEncoder(scanModel.getText().trim(),null, QRGContents.Type.TEXT, 300);
        bitmap = qrgEncoder.getBitmap();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                boolean save = new QRGSaver().save(savePath, scanModel.getText().trim(),bitmap, QRGContents.ImageType.IMAGE_JPEG);
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
            case 123: {
                saveQrCode(scanHistoryList.get(item.getGroupId()));
                return true;
            }
            case 124: {
                scanAdapter.removeItem(item.getGroupId());
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
