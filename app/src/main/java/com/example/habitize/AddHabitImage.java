package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.net.Uri;


import androidx.appcompat.app.AppCompatActivity;

public class AddHabitImage extends AppCompatActivity {
    ImageView imageView;
    Button addImageBtn;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    /**
     * Initialize activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_image);

        //find views
        imageView = findViewById(R.id.new_image);
        addImageBtn = findViewById(R.id.new_image_btn);

        //create a listener to check if add image button was clicked
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(); //opens user's photo album
            }
        });
    }

    //opens gallery in another screen
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}