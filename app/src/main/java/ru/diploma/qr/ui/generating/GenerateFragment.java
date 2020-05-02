package ru.diploma.qr.ui.generating;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import ru.diploma.qr.R;
import ru.diploma.qr.ui.history.generate.model.GenerateModel;

import static android.content.Context.WINDOW_SERVICE;

public class GenerateFragment extends Fragment {


    private EditText etMessage;
    private Button btnGenerate;
    private Bitmap bitmap;
    private ImageView qrImage;
    private int qrSize = R.dimen.imageview_height_300;
    private ImageView download;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private List<GenerateModel> generateModelList = new ArrayList<>();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_generate, container, false);
        etMessage = root.findViewById(R.id.et_message);
        btnGenerate = root.findViewById(R.id.btn_generate);
        qrImage = root.findViewById(R.id.iv_generate);
        loadData();
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputValue = etMessage.getText().toString().trim();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;
                    QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);
                    try {
                        bitmap = qrgEncoder.getBitmap();
                        qrImage.getLayoutParams().height = (int) getResources().getDimension(qrSize);
                        qrImage.setImageBitmap(bitmap);
                        qrImage.requestLayout();
                        GenerateModel generateModel = new GenerateModel();
                        generateModel.setText(inputValue);
                        generateModel.setImg(qrgEncoder);
                        generateModelList.add(generateModel);
                        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(generateModelList);
                        editor.putString("taskList", json);
                        editor.apply();
                    } catch (Exception e) {
                        Log.d("TAG", e.toString());
                    }
                } else {
                    Toast.makeText(getContext(), "Ошибка генерирования QR Code", Toast.LENGTH_SHORT).show();
                }


            }
        });
        download = root.findViewById(R.id.iv_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,etMessage.getText().toString().trim(),null);
                        Toast.makeText(getContext(), "Qr-код Сохранен", Toast.LENGTH_SHORT).show();
//                        boolean save = new QRGSaver().save(savePath, etMessage.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
//                        String result = save ? "QR Сохранен" : "Не удалось сохранить";
//                        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
//                        etMessage.setText(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            }
        });
        RadioButton firstSize = root.findViewById(R.id.firstSize);
        firstSize.setOnClickListener(radioButtonClickListener);
        RadioButton secondSize = root.findViewById(R.id.secondSize);
        secondSize.setOnClickListener(radioButtonClickListener);
        RadioButton thirdSize = root.findViewById(R.id.thirdSize);
        thirdSize.setOnClickListener(radioButtonClickListener);
        RadioButton fourthSize = root.findViewById(R.id.fourthSize);
        fourthSize.setOnClickListener(radioButtonClickListener);
        RadioButton fifthSize = root.findViewById(R.id.fifthSize);
        fifthSize.setOnClickListener(radioButtonClickListener);
        return root;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,etMessage.getText().toString().trim(),null);
                Toast.makeText(getContext(), "Qr-код Сохранен", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.firstSize:
                    qrSize = R.dimen.imageview_height_100;
                    break;
                case R.id.secondSize:
                    qrSize = R.dimen.imageview_height_150;

                    break;
                case R.id.thirdSize:
                    qrSize = R.dimen.imageview_height_200;

                    break;
                case R.id.fourthSize:
                    qrSize = R.dimen.imageview_height_250;

                    break;
                case R.id.fifthSize:
                    qrSize = R.dimen.imageview_height_300;

                    break;
                default:
                    break;
            }
        }
    };


}
