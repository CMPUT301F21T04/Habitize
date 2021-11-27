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

        /*
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
         */
        // Access arguments







        // Link UI components to the its respective pairs in XML

        comment = view.findViewById(R.id.recordComment);
        if(commentText != null){
            comment.setText(commentText);
            comment.setEnabled(false);
        }



        /*
        // Listener for the image button. When the button is clicked, redirect user to the openGallery method
        RecordImgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                forActivityResult.launch(gallery);
            }
        });

         */

/*
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
                        Record newRecord = new Record(currentDate,comment.getText().toString(),null,new UUID(20,10).randomUUID().toString());
                        DatabaseManager.updateRecord(args.getRecordAddress(),newRecord);
                        // retrieve the habit, increment its streak and update the database.
                        passedHabits.get(index).incrementStreak();
                        DatabaseManager.updateHabits(passedHabits);

                        imageViewer.setDrawingCacheEnabled(true);
                        imageViewer.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) imageViewer.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        if(data != null){
                            DatabaseManager.storeImage(data,newRecord.getRecordIdentifier());
                        }

                    }
                }).create();
                */
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