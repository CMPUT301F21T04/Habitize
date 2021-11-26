package com.example.habitize.Controllers;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRMaker {
    public final static int WIDTH = 500;
    public static int WHITE = 0xFFFFFFFF;
    public static int BLACK = 0xFF000000;

    public static Bitmap makeQR(String source) throws WriterException {
        BitMatrix result;
        Bitmap bitmap;
        try {
            result = new MultiFormatWriter().encode(source,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        } catch (Exception iae) {
            iae.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static void setQRIMAGE(String source, ImageView imageView) {
        try {
            Bitmap bmp = makeQR(source);
            imageView.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}
