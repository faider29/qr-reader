package ru.diploma.qr.ui.history.generate.model;

import android.graphics.Bitmap;

import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Created by Maxim Andrienko
 * 4/27/20
 */
public class GenerateModel {
    private String text;
    private QRGEncoder img;
    public GenerateModel(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QRGEncoder getImg() {
        return img;
    }

    public void setImg(QRGEncoder img) {
        this.img = img;
    }
}
