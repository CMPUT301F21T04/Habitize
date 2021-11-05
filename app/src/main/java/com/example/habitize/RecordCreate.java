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
import android.net.Uri;
import android.os.Bundle;

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

import java.io.ByteArrayOutputStream;

public class RecordCreate extends DialogFragment implements CustomAdapter.habitCheckListener {
    // Ui components
    private Button RecordLocBTN, RecordImgBTN;
    private EditText comment;
    private ImageView imageViewer;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String imgPath ="";

    public RecordCreate() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_record_create,null);

        // Access arguments
        if (getArguments()!=null){
            Bundle args =getArguments();

        }

        // Link UI components to the its respective pairs in XML
        RecordLocBTN = view.findViewById(R.id.recordLocBTN);
        RecordImgBTN = view.findViewById(R.id.recordImgBTN);
        comment = view.findViewById(R.id.recordComment);
        imageViewer = view.findViewById(R.id.recordImg);


        RecordLocBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goto MapsActivity class
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });

        RecordImgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setTitle("New Record")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: how to handle adding this record to the firebase
                    }
                }).create();
    }

    @Override
    public void recordEvent(int position) {

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= RESULT_CANCELED) {
            if (requestCode==PICK_IMAGE) {
                imageUri = data.getData();
                imageViewer.setImageURI(imageUri);
                uploadImg();
            }
        }
    }

    private void uploadImg() {
        // get the data from an ImageView as bytes
        // create a storage reference from our app
        imageViewer.setDrawingCacheEnabled(true);
        imageViewer.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageViewer.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // connect to habit
    }
}