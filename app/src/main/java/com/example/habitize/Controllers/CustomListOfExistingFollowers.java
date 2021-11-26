package com.example.habitize.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.habitize.Activities.AddHabit.AddHabitTabsBase;
import com.example.habitize.Activities.Followers.FollowingActivity;
import com.example.habitize.Activities.Followers.PublicHabitsActivity;
import com.example.habitize.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Will populate usernames of existing followers to existingFollowerName TextView
 * inside of activity_custom_list_of_existing_followers.xml UI.
 * */
public class CustomListOfExistingFollowers extends ArrayAdapter<String> {

    private final List<String> followers;
    private final Context context;
    private FloatingActionButton deleteFollowerButton;
    TextView tv;
    private static final String TAG = "doc error";
    private ArrayList<String> usersWhoGrantedAccess;


    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String currentLoggedInUser;

    /**
     * Initializes list of existing follower usernames and context variables.
     * @param context the context to which the followers will be populated.
     * @param followers is the list of existing follower usernames as Strings.
     * */
    public CustomListOfExistingFollowers(Context context, List<String> followers){
        super(context,0, followers);
        this.followers = followers;
        this.context = context;
    }

    /**
     * Sets existing follower usernames as the name field for existing followers
     * inside of custom list.
     * @param position the integer value of a particular existing follower username
     * inside of followers list.
     * @param convertView If possible this is the old view to reuse.
     * @param parent parent of the View to be added.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_of_existing_followers,parent,false);
        }

        deleteFollowerButton = view.findViewById(R.id.deleteExistingFollowerButton);
        String follower = followers.get(position);
        TextView nameField = view.findViewById(R.id.existingFollowerName);
        nameField.setText(follower);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");

        Query currentUserDocQuery = collectionReference.whereEqualTo("email", fAuth.getCurrentUser().getEmail());

        currentUserDocQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        currentLoggedInUser = document.getString("userName");
                    }
                }
            }

        });


        //When you click on another user in list - listener
        //check if user has granted
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                //get the username that was clicked upon and save in Bundle
                bundle.putString("name",follower);
                //bring user to new screen/activity

                System.out.println("USER IS:   " + follower);

                Query queryToFindUsersHabits =  collectionReference.whereEqualTo("userName", follower);

                queryToFindUsersHabits.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            System.out.println("IS SUCCESSFULLLLLLL");
                            //for every document find the ones that match query
                            for(QueryDocumentSnapshot document : task.getResult()) {

                                usersWhoGrantedAccess = (ArrayList<String>) document.get("following");

//                                System.out.println("user looged in: " + currentLoggedInUser);
//                                System.out.println("user click on: " + usersWhoGrantedAccess);
//                                System.out.println("check: " + usersWhoGrantedAccess.contains(currentLoggedInUser));
                                if(usersWhoGrantedAccess.contains(currentLoggedInUser)){
                                    openPublicHabitList(bundle);
                                } else {
                                    System.out.println("User doesn't follow you back :( ");

//                                    AlertDialog alertDialog = new AlertDialog.Builder(FollowingActivity.this).create();
//                                    alertDialog.setTitle("Alert");
//                                    alertDialog.setMessage("Alert message to be shown");
//                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    alertDialog.show();
                                    //showToast(FollowingActivity.this, "Cannot view user's habit because they do not follow you back");
                                    //Toast.makeText(FollowingActivity.this,"Cannot view user's habit because they do not follow you back",Toast.LENGTH_LONG).show();
                                }
                                //collectionReference.document(user).update("followers", FieldValue.arrayUnion(currentLoggedInUser));
                            }
                        } else{
                            //System.out.println("NOOOOOOOOOOOOOO");
                        }
                    }
                });


            }
        });



        nameField.setClickable(false);
        deleteFollowerButton.setFocusable(false);
        deleteFollowerButton.setFocusableInTouchMode(false);


        deleteFollowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query currentUserDocQuery = collectionReference.whereEqualTo("email", fAuth.getCurrentUser().getEmail());
                currentUserDocQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                currentLoggedInUser = document.getString("userName");
                                collectionReference.document(currentLoggedInUser).update("following", FieldValue.arrayRemove(follower));
                            }
                        }
                    }
                });
            }
        });

        return view;

    }
    /**
     * brings to new screen to display public habits of the user clicked on
     * @param bundle bundle to bring to other activity
     */
    public void openPublicHabitList(Bundle bundle) {
        //bring user to Public Habit Activity
        Intent intent = new Intent(this.getContext(), PublicHabitsActivity.class);
        //save the name that was clicked on
        intent.putExtras(bundle);
        context.startActivity(intent);
    }




}
