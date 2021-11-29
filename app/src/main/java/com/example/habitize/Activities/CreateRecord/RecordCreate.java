package com.example.habitize.Activities.CreateRecord;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.habitize.R;

/**
 * RecordCreate contains the logic that governs the creation of a record.
 * Allows comments to be editable and sets the comment for the record to a habit.*/
public class RecordCreate extends Fragment{
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


    public void setEditable(){
        this.comment.setEnabled(true);
    }
    public void setNotEditable(){
        this.comment.setEnabled(false);
    }


    /**
     * This method takes the uploaded image from the storage and handles it here.
     * Extract the info from the imageView and convert it to a bitmap.
     */
    public String getComment(){
        return comment.getText().toString();
    }


}