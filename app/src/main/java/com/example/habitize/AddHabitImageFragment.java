package com.example.habitize;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

public class AddHabitImageFragment extends Fragment {
    private ImageView imageView;
    private Button addImageBtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    /*
     * Empty required c onstructor
     */
    public AddHabitImageFragment(){
    }


    /*
     * get image from fragment to send to the database
     */
    public byte[] getImageBytes(){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }


    /**
     * Will instantiate the UI view of the create habit screen
     * @param inflater inflates tge views in the fragment
     * @param container for parent view that the fragment's UI should be attached to
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from a
     *                                    previous state
     * @return returns the View of a the create habit screen fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_add_habit_image, container, false);
        imageView = root.findViewById(R.id.new_image);
        addImageBtn = root.findViewById(R.id.new_image_btn);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        return root;
    }

    /*
     * opens gallery in another screen
     */
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }





}
