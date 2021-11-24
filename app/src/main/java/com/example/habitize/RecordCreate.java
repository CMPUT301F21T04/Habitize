package com.example.habitize;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.type.LatLng;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


public class RecordCreate extends Fragment{
    // Ui components




    private Button RecordLocBTN, RecordImgBTN;
    private EditText comment;
    private String commentText;
    private ImageView imageViewer;
    private TextView locViewer;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private Location lastLocation;
    private String imgPath ="";

    public RecordCreate() {
        this.commentText = null;
        // Required empty public constructor
    }
    public RecordCreate(String comment) {
        this.commentText = comment;
    }

    /**
     * This method starts the activity
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_record_create,container,false);

        // Link UI components to the its respective pairs in XML
        comment = view.findViewById(R.id.recordComment);
        if(commentText != null){
            comment.setText(commentText);
            comment.setEnabled(false);
        }

        return view;
    }


    /**
     * This method takes the uploaded image from the storage and handles it here.
     * Extract the info from the imageView and convert it to a bitmap.
     */
    public String getComment(){
        return comment.getText().toString();
    }


}