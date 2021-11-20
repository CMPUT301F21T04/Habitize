package com.example.habitize;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ViewHabitImageFragment extends Fragment {
    private ImageView imageView;
    private Button addImageBtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String imageAddr;

    public ViewHabitImageFragment(String imageAddr){
        this.imageAddr = imageAddr;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View root = inflater.inflate(R.layout.fragment_view_habit_image, container, false);
        imageView = root.findViewById(R.id.FragmentViewHabitNewImage);
        addImageBtn = root.findViewById(R.id.FragmentViewHabitNewImageBtn);
        addImageBtn.setEnabled(false);


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        DatabaseManager.getAndSetImage(imageAddr,imageView);
        return root;
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void setEditable(){
        addImageBtn.setEnabled(true);
    }
    public void setNotEditable(){
        addImageBtn.setEnabled(false);
    }

    // get image from fragment to send to the database
    public ImageView getImageView(){
        return this.imageView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


}
