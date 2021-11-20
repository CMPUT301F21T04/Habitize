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
import java.util.Date;


public class RecordCreate extends DialogFragment implements CustomAdapter.habitCheckListener {
    // Ui components
    private Button RecordLocBTN, RecordImgBTN;
    private EditText comment;
    private ImageView imageViewer;
    private TextView locViewer;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String imgPath ="";

    public RecordCreate() {
        // Required empty public constructor
    }

    /**
     * This method starts the activity
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_record_create,null);

        ActivityResultLauncher<Intent> forActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()!= RESULT_CANCELED) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imageViewer.setImageURI(imageUri);
                            uploadImg();
                        }
                    }
                }
        );

        // Access arguments
        Habit args = (Habit) getArguments().getSerializable("habit");

        // Link UI components to the its respective pairs in XML
        RecordLocBTN = view.findViewById(R.id.recordLocBTN);
        RecordImgBTN = view.findViewById(R.id.recordImgBTN);
        comment = view.findViewById(R.id.recordComment);
        imageViewer = view.findViewById(R.id.recordImg);
        locViewer = view.findViewById(R.id.locationView);

        if (getArguments()!=null && getArguments().containsKey("loc")){
            double lat = getArguments().getSerializable("lat").hashCode();
            double lng = getArguments().getSerializable("lng").hashCode();
            String loc = (String) getArguments().getSerializable("loc");
            locViewer.setText(loc);
        }

        // Listener for the location button. When the button is clicked, redirect user to the maps activity.
        RecordLocBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goto MapsActivity class
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });


        // Listener for the image button. When the button is clicked, redirect user to the openGallery method
        RecordImgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                forActivityResult.launch(gallery);
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
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date d = new Date();
                        String currentDate = formatter.format(d);
                        Record newRecord = new Record(currentDate,comment.getText().toString(),null);
                        DatabaseManager.updateRecord(args.getRecordAddress(),newRecord);
                    }
                }).create();
    }

    @Override
    public void recordEvent(int position) {
        // will handle which habit to append the record to.
    }


    /**
     * This method takes the uploaded image from the storage and handles it here.
     * Extract the info from the imageView and convert it to a bitmap.
     */
    private void uploadImg() {
        // get the data from an ImageView as bytes
        // create a storage reference from our app
        imageViewer.setDrawingCacheEnabled(true);
        imageViewer.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageViewer.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    }
}