package ru.diploma.qr.ui.history.generate.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import ru.diploma.qr.R;
import ru.diploma.qr.ui.history.generate.model.GenerateModel;

/**
 * Created by Maxim Andrienko
 * 4/27/20
 */
public class GenerateAdapter  extends RecyclerView.Adapter<GenerateAdapter.ViewHolder> {

    List<GenerateModel> generateModelList;
    private Context context;
    private  Bitmap bitmap;

    public GenerateAdapter(List<GenerateModel> generateModelList, Context context) {
        this.generateModelList = generateModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_generate, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenerateModel generateModel = generateModelList.get(position);
        holder.textView.setText(generateModel.getText());
        bitmap  = generateModel.getImg().getBitmap();
        holder.imageView.setImageBitmap(bitmap);
        holder.imageView.requestLayout();
    }

    @Override
    public int getItemCount() {
        return generateModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv_generate);
            imageView = itemView.findViewById(R.id.item_iv_generate);
        }
    }
}
