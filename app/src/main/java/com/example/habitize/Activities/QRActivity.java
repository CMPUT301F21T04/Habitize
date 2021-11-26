package com.example.habitize.Activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.Controllers.QRMaker;
import com.example.habitize.R;


public class QRActivity extends AppCompatActivity {

    private ImageView qrCode;

    public static int WHITE = 0xFFFFFFFF;
    public static int BLACK = 0xFF000000;
    public final static int WIDTH = 500;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        qrCode = findViewById(R.id.qrCodeImage);

        QRMaker.setQRIMAGE(DatabaseManager.getUser(), qrCode);
    }
}


