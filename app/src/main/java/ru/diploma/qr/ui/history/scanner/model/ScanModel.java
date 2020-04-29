package ru.diploma.qr.ui.history.scanner.model;

import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Created by Maxim Andrienko
 * 4/28/20
 */
public class ScanModel {
    private String text;
    private QRGEncoder img;

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



    public ScanModel(){}
}
