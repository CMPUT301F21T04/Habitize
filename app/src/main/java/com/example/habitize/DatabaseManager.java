package com.example.habitize;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    // db is shared across all instances of the manager
    private static final FirebaseFirestore db;
    private static CollectionReference users;
    private static Context loginContext;
    private static Context signUpContext;

    private static onRegistrationLoginListener registrationListener;
    private static onLoginListener loginListener;

    // these user credentials are set in the constructor.
    private static String user;
    private static String inputEmail;
    private static String inputPassword;
    private static String first;
    private static String last;



    // we initialize the firestore ONCE. Many objects but all will refer to the same instance
    static {
        db = FirebaseFirestore.getInstance();
        users = db.collection("Users");
    }


    // We need communication between the authenticator class and the activity we login from

    public static void setLoginContext(Context context){
        loginContext = context;
    }
    public static void setsignUpContext(Context context){
        signUpContext = context;
    }
    public static String getInputEmail(){
        return inputEmail;
    }
    public static String getInputPassword(){
        return inputPassword;
    }


    // this occurs when we click the login button. We initialize the username
    public static void setInfoForLogin(String email,String password){
        inputEmail = email;
        inputPassword = password;
    }
    public static void signInUser(){

        db.collection("EmailToUser").document(inputEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = (String) documentSnapshot.get("user"); // we set the appropriate user source in database
                loginListener.loginUser(); // user data is set. Open main activity with signed in user


            }
        });

    }


    // This occurs when we click the registration button. We initialize  the variables to this information
    public static void setInfoForRegistration(String username,String email,String firstName,String lastName,String password){
        user = username;
        inputEmail = email;
        first = firstName;
        last = lastName;
        inputPassword = password;
    }
    // this runs first. We check is the username input exists and then run the authenticator if true
    public static void checkUsernameAndRegister(){
        db.collection("Users").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(!documentSnapshot.exists()){
                    Authentication.checkEmailBeforeRegistering(); // user doesn't exist, continue with registration
                }
                else{ // do nothing

                }

            }
        });
    }

    // this runs third. We create the user document and launch the mainactivity to login.
    public static void createUserDocumentAndLogin(){
        HashMap<String,Object> userNameField = new HashMap<>();
        HashMap<String,Object> nameField = new HashMap<>();
        HashMap<String,Object> lastNameField = new HashMap<>();
        HashMap<String,Object> emailField = new HashMap<>();
        HashMap<String, Object> pointField = new HashMap<>();
        HashMap<String, Object> progressField = new HashMap<>();
        userNameField.put("userName",user);
        nameField.put("firstName",first);
        lastNameField.put("lastName",last);
        emailField.put("email",inputEmail);
        pointField.put("points", 0L);
        progressField.put("progress",0L);
        // adding Data to User collection
        db.collection("Users").document(user).set(userNameField);
        db.collection("Users").document(user).update(nameField);
        db.collection("Users").document(user).update(lastNameField);
        db.collection("Users").document(user).update(emailField);
        db.collection("Users").document(user).update(pointField);
        db.collection("Users").document(user).update(progressField);
        // adding Data to UsersHabits collection
        HashMap<String,Object> habits = new HashMap<>();
        habits.put("habits",new ArrayList<Habit>());
        db.collection("Users").document(user).update(habits);
        // adding Data to followers Collection
        HashMap<String,Object> followList = new HashMap<>();
        followList.put("followers",new ArrayList<String>());
        db.collection("Users").document(user).update(followList);
        // adding Data to following Collection
        HashMap<String,Object> followingList = new HashMap<>();
        followingList.put("following",new ArrayList<String>());
        db.collection("Users").document(user).update(followingList);
        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put("user",user);
        db.collection("EmailToUser").document(inputEmail).set(emailMap);

        registrationListener.loginUser();// log the user in. signup will implement this

    }



    // get all habits and put them into a list. Then notify the habitAdapter
    public static void getAllHabits(ArrayList<Habit> recievingList, CustomAdapter habitAdapter) {
        //
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList = (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
                for (int i = 0; i < mappedList.size(); i++) { // get each item one by one
                    Map<String, Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = (String) habitFields.get("name");
                    String description = (String) habitFields.get("description");
                    String date = (String) habitFields.get("startDate");
                    boolean mondayRec = (boolean) habitFields.get("mondayR");
                    boolean tuesdayRec = (boolean) habitFields.get("tuesdayR");
                    boolean wednesdayRec = (boolean) habitFields.get("wednesdayR");
                    boolean thursdayRec = (boolean) habitFields.get("thursdayR");
                    boolean fridayRec = (boolean) habitFields.get("fridayR");
                    boolean saturdayRec = (boolean) habitFields.get("saturdayR");
                    boolean sundayRec = (boolean) habitFields.get("sundayR");
                    Habit newHabit = new Habit(name, description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
                habitAdapter.notifyDataSetChanged();
            }
        });
    }

    // get all the habits and put into a list, but don't update a habitAdapter
    public static void getAllHabits(ArrayList<Habit> recievingList) {

        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList = (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
                for (int i = 0; i < mappedList.size(); i++) { // get each item one by one
                    Map<String, Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = (String) habitFields.get("name");
                    String description = (String) habitFields.get("description");
                    String date = (String) habitFields.get("startDate");
                    boolean mondayRec = (boolean) habitFields.get("mondayR");
                    boolean tuesdayRec = (boolean) habitFields.get("tuesdayR");
                    boolean wednesdayRec = (boolean) habitFields.get("wednesdayR");
                    boolean thursdayRec = (boolean) habitFields.get("thursdayR");
                    boolean fridayRec = (boolean) habitFields.get("fridayR");
                    boolean saturdayRec = (boolean) habitFields.get("saturdayR");
                    boolean sundayRec = (boolean) habitFields.get("sundayR");
                    Habit newHabit = new Habit(name, description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
            }
        });
    }

    public static void createUserWithCredentialsAndLogin(){

    }


    public static void updateHabits(ArrayList<Habit> updatedHabits) {
        HashMap<String, Object> listMap = new HashMap<>();
        listMap.put("habits", updatedHabits);
        db.collection("Users").document(user).update(listMap);
    }

    public static void updateHabitsAtDay(String user, ArrayList<Habit> updatedHabits, String day) {

    }

    public static boolean userExists() {
        return true;
    }

    // pulls the habits in the list labelled to the weekday.
    // user is the current user. recievingList is the list we want to pull into. habitAdapter is the adapter we want to
    // notify when we fill it
    public static void getTodaysHabits(String user, ArrayList<Habit> recievingList, CustomAdapter habitAdapter) {
        // we get the date
        Date date = new Date();
        String dayWeekText = new SimpleDateFormat("EEEE").format(date);
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList = (ArrayList<Habit>) value.get(dayWeekText); // retrieving habits scheduled for today
                recievingList.clear();
                for (int i = 0; i < mappedList.size(); i++) { // get each item one by one
                    Map<String, Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = (String) habitFields.get("name");
                    String description = (String) habitFields.get("description");
                    String date = (String) habitFields.get("startDate");
                    boolean mondayRec = (boolean) habitFields.get("mondayR");
                    boolean tuesdayRec = (boolean) habitFields.get("tuesdayR");
                    boolean wednesdayRec = (boolean) habitFields.get("wednesdayR");
                    boolean thursdayRec = (boolean) habitFields.get("thursdayR");
                    boolean fridayRec = (boolean) habitFields.get("fridayR");
                    boolean saturdayRec = (boolean) habitFields.get("saturdayR");
                    boolean sundayRec = (boolean) habitFields.get("sundayR");
                    Habit newHabit = new Habit(name, description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
                habitAdapter.notifyDataSetChanged();
            }

        });


    }

    // the authenticator class will become the listener and be forced to implement procedure

    // returns the user's habits if the user is found.
    // returns null if user does not exist
    public ArrayList<Habit> findUserHabits(String user) {
        return new ArrayList<>();
    }

    // setting listeners
    public static void setRegistrationListener(Context context){
        registrationListener = (onRegistrationLoginListener) context;
    }

    // setting listeners
    public static void setLoginListener(Context context){
        loginListener = (onLoginListener) context;

    }

    // signup activity will be forced to implement logging in
    interface onRegistrationLoginListener{
        void loginUser();
    }
    // login activity will be forced to implement logging in
    interface onLoginListener{
        void loginUser();
    }




}
