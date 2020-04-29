package ru.diploma.qr.ui.history.scanner.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import ru.diploma.qr.R;
import ru.diploma.qr.ui.history.scanner.model.ScanModel;

/**
 * Created by Maxim Andrienko
 * 4/27/20
 */
public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {

    List<ScanModel> scanModelList;
    private Context context;
    private Bitmap bitmap;

    public ScanAdapter(List<ScanModel> scanModelList, Context context) {
        this.scanModelList = scanModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scanner, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScanModel scanModel = scanModelList.get(position);
        holder.textView.setText(scanModel.getText());
        String inputValue = scanModel.getText();
        generate(inputValue, holder);
    }

    @Override
    public int getItemCount() {
        return  scanModelList.size();
    }

    private void generate(String inputValue, ViewHolder holder){

        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, 300);
        try {
            bitmap = qrgEncoder.getBitmap();
            holder.imageView.setImageBitmap(bitmap);
            holder.imageView.requestLayout();
        } catch (Exception e){

        }

    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv_scanner);
            imageView = itemView.findViewById(R.id.item_iv_scanner);
        }
    }
}
