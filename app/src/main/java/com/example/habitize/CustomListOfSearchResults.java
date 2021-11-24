package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomListOfSearchResults extends ArrayAdapter<String> {

    private final List<String> users;
    private final Context context;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String currentLoggedInUser;

    private FloatingActionButton sendRequest;
    public CustomListOfSearchResults(Context context, ArrayList<String> users){
        super(context,0, users);
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_of_search_results,parent,false);
        }


        sendRequest = view.findViewById(R.id.sendRequestButton);

        String user = users.get(position);
        TextView nameField = view.findViewById(R.id.userName);

        nameField.setText(user);


        nameField.setClickable(false);
        sendRequest.setFocusable(false);
        sendRequest.setFocusableInTouchMode(false);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");
        Query currentUserDocQuery = collectionReference.whereEqualTo("email", fAuth.getCurrentUser().getEmail());

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserDocQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                currentLoggedInUser = document.getString("userName");
                                collectionReference.document(user).update("followers", FieldValue.arrayUnion(currentLoggedInUser));
                            }
                        }
                    }
                });
            }
        });

        return view;


    }

}