package com.example.habitize;

import static android.app.Activity.RESULT_CANCELED;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class ViewHabitImageFragment extends Fragment {
    private ImageView imageView;
    private Button addImageBtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    public ViewHabitImageFragment(){
    }



    // get image from fragment to send to the database
    public Uri getImage(){
        return this.imageUri;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActivityResultLauncher<Intent> forActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()!= RESULT_CANCELED) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imageView.setImageURI(imageUri);
                        }
                    }
                }
        );



        View root = inflater.inflate(R.layout.fragment_view_habit_image, container, false);
        imageView = root.findViewById(R.id.FragmentViewHabitNewImage);
        addImageBtn = root.findViewById(R.id.FragmentViewHabitNewImageBtn);
        addImageBtn.setEnabled(false);


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                forActivityResult.launch(gallery);
            }
        });
        return root;
    }

    public void setEditable(){
        addImageBtn.setEnabled(true);
    }
    public void setNotEditable(){
        addImageBtn.setEnabled(false);
    }

}
