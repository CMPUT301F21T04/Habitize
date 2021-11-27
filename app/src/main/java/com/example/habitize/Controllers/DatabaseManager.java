package com.example.habitize.Controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    // db is shared across all instances of the manager
    private static final FirebaseFirestore db;
    private static final FirebaseStorage dbs;

    private static CollectionReference users;
    private static Context loginContext;
    private static Context signUpContext;
    private static SimpleDateFormat simpleDateFormat;
    private static StorageReference storageRef;

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
        dbs = FirebaseStorage.getInstance();
        storageRef = dbs.getReference().child("images");
        users = db.collection("Users");
    }


    // We only want to display the checkmark is a habit has not already been complete
    public static void habitComplete(String habitIdentifier, ImageButton button){
        db.collection("Users").document(user).collection("Records").document(habitIdentifier).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                ArrayList<Record> mappedRecords = (ArrayList<Record>) value.get("records");
                if(mappedRecords != null && mappedRecords.size() > 0) {
                    Map<String, Object> hashedRecord = (Map<String, Object>) mappedRecords.get(mappedRecords.size() - 1);
                    String lastCompletionDate = (String) hashedRecord.get("date");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date d = new Date();
                    String currentDate = formatter.format(d);
                    if (currentDate.equals(lastCompletionDate)) {
                        button.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }

    /**
     * takes the data from login and pass it here
     * @param context
     */
    public static void setLoginContext(Context context){
        loginContext = context;
    }

    /**
     * takes the data from signUp and pass it here
     * @param context
     */
    public static void setsignUpContext(Context context){
        signUpContext = context;
    }

    public static String getInputEmail(){
        return inputEmail;
    }
    public static String getInputPassword(){
        return inputPassword;
    }

    // store an image at the given identifier
    public static void storeImage(byte[] image, String imageIdentifier){
        if(image != null) {
            StorageReference imageRef = storageRef.child(imageIdentifier);
            UploadTask uploadTask = imageRef.putBytes(image);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public static void deleteImage(String imageIdentifier){
        StorageReference imageRef = storageRef.child(imageIdentifier);
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // image is deleted
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // image could not be deleted. Probably does not exist
            }
        });
    }

    // retrieve an image from the identifier
    // right now this has to be called every time we refresh the images. WORKAROUND: just add the byte maps
    // to a list stored. The list will be initialized ONCE when we log in, and then we will add to it during runtime
    public static void getAndSetImage(String imageIdentifier, ImageView destination){
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                long TEN_MEGABYTES = 1024*1024*10;
                StorageReference imageRef = storageRef.child(imageIdentifier);
                imageRef.getBytes(TEN_MEGABYTES)
                        .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                if(bytes != null) {
                                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    destination.setImageBitmap(bmp);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });

    }

    /**
     * Takes email and password and set them DataManger
     * @param email
     * @param password
     */
    // this occurs when we click the login button. We initialize the username
    public static void setInfoForLogin(String email,String password){
        inputEmail = email;
        inputPassword = password;
    }

    /**
     * Takes the user data and sign them in
     */
    public static void signInUser(){

        db.collection("EmailToUser").document(inputEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = (String) documentSnapshot.get("user"); // we set the appropriate user source in database
                loginListener.loginUser(); // user data is set. Open main activity with signed in user
            }
        });

    }

    /**
     *This occurs when we click the registration button. We initialize  the variables to this information
     * @param username
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     */

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
                } else { // do nothing

                }

            }
        });
    }

    public static String getUser() {
        return user;
    }

    /**
     * get records for each user
     *
     * @param UUID
     * @param adapter
     */
        /*
        public static void getRecord(String UUID, ArrayList<Record> recievingList, RecordAdapter adapter){
            db.collection("Users").document(user).collection("Records").document(UUID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    recievingList.clear();
                    // get the mapped data of records
                    ArrayList<Record> mappedRecords = (ArrayList<Record>) value.get("records");
                    // retrieving all records
                    if(mappedRecords != null) {
                        for (int i = 0; i < mappedRecords.size(); i++) {
                            Map<String, Object> hashedRecord = (Map<String, Object>) mappedRecords.get(i);
                            String date = (String) hashedRecord.get("date");
                            String description = (String) hashedRecord.get("description");
                            String identifier = (String) hashedRecord.get("recordIdentifier");
                            Double lat = (Double) hashedRecord.get("lat");
                            Double lon = (Double) hashedRecord.get("lon");



                            recievingList.add(new Record(date, description, null,identifier,lat,lon));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }

         */
    public static void getRecord(String UUID, ArrayList<Record> recievingList, RecordAdapter adapter){

        db.collection("Users").document(user).collection("Records").document(UUID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                recievingList.clear();
                adapter.notifyDataSetChanged();
                // get the mapped data of records
                ArrayList<Record> mappedRecords = (ArrayList<Record>) value.get("records");
                // retrieving all records
                if(mappedRecords != null) {
                    for (int i = 0; i < mappedRecords.size(); i++) {

                        Map<String, Object> hashedRecord = (Map<String, Object>) mappedRecords.get(i);
                        String date = (String) hashedRecord.get("date");
                        String description = (String) hashedRecord.get("description");
                        String identifier = (String) hashedRecord.get("recordIdentifier");
                        Double lat = (Double) hashedRecord.get("lat");
                        Double lon = (Double) hashedRecord.get("lon");

                        long TEN_MEGABYTES = 1024 * 1024 * 10;
                        StorageReference imageRef = storageRef.child(identifier);
                        imageRef.getBytes(TEN_MEGABYTES)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        if (bytes != null) {
                                            recievingList.add(new Record(date, description, bytes, identifier, lat, lon));
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                recievingList.add(new Record(date, description, null, identifier, lat, lon));
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
            }
        });

    }

    /**
     * Takes the new records for the user and update them
     * @param UUID a user has a habit record collection. This is the reference for the specific habit
     * @param newRecord
     * there are three cases here. 1 it is our first time making a record so we make a collection and a document
     * 2. it is our first time making a record for that habit so we create a document
     * 3. we are adding a new record so we need to pull and update.
     */
    public static void updateRecord(String UUID,Record newRecord){
        db.collection("Users").document(user).collection("Records").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size() == 0){
                    // no such collection exists. We just create one. since this must be our first time calling this
                    ArrayList<Record> records = new ArrayList<>();
                    records.add(newRecord);
                    HashMap<String,Object> mappedData = new HashMap<>(); // hash the record
                    mappedData.put("records",records); // put it in the record space
                    db.collection("Users").document(user).collection("Records").document(UUID).set(mappedData);
                }
                else{
                    // this collection exists, we must check whether the document exists now.

                    db.collection("Users").document(user).collection("Records").document(UUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            // we get the the document at the UUID
                            if (documentSnapshot.exists()) {
                                ArrayList<Record> mappedRecords = (ArrayList<Record>) documentSnapshot.get("records");
                                ArrayList<Record> updatedRecords = new ArrayList<>();
                                // retrieving all records
                                for (int i = 0; i < mappedRecords.size(); i++) {
                                    Map<String, Object> hashedRecord = (Map<String, Object>) mappedRecords.get(i);
                                    String date = (String) hashedRecord.get("date");
                                    String description = (String) hashedRecord.get("description");
                                    String identifier = (String) hashedRecord.get("recordIdentifier");
                                    Double lat = (Double) hashedRecord.get("lat");
                                    Double lon = (Double) hashedRecord.get("lon");
                                    updatedRecords.add(new Record(date, description,null,identifier,lat,lon));
                                }
                                updatedRecords.add(newRecord);
                                HashMap<String, Object> mappedDate = new HashMap<>();
                                mappedDate.put("records", updatedRecords);
                                db.collection("Users").document(user).collection("Records").document(UUID).set(mappedDate);
                            }
                            else{
                                ArrayList<Record> records = new ArrayList<>();
                                records.add(newRecord);
                                HashMap<String,Object> mappedData = new HashMap<>(); // hash the record
                                mappedData.put("records", records); // put it in the record space
                                db.collection("Users").document(user).collection("Records").document(UUID).set(mappedData);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void updateBecauseDeleted(String UUID, ArrayList<Record> newRecords) {
        for (Record i : newRecords) {
            i.setByteArr(null);
        }
        HashMap<String, Object> mappedData = new HashMap<>();
        mappedData.put("records", newRecords);
        db.collection("Users").document(user).collection("Records").document(UUID).update(mappedData);
    }


    /**
     * Create a new user with all the data collected with their own data collections
     */

    public static void createUserDocumentAndLogin() {
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
        HashMap<String,Object> recordList = new HashMap<>();
        recordList.put("Records",new ArrayList<>());
        db.collection("EmailToUser").document(inputEmail).set(emailMap);
        registrationListener.loginUser();// log the user in. signup will implement this

    }

    /**
     * it perform a search and return users matching the query
     * @param searchQuery
     * @param users
     */

    public static void getMatchingUsers(String searchQuery, ArrayList<String> users, CustomListOfSearchResults adapter){
        //
        Query query = db.collection("Users").whereGreaterThanOrEqualTo("userName",searchQuery)
                .whereLessThanOrEqualTo("userName",searchQuery + "\uf8ff");

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for(int i = 0; i < documents.size(); i++){
                    users.add((String)documents.get(i).get("userName")); // fills a list with all of the users
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * send a follow request to the given username
     * @param user
     */

    public static void sendFollow(String user){

    }

    /**
     * get all the followers
     * puts loggin in users current followers into retrievingList, updates the adapter
     * @param retrievingList
     * @param adapter
     */

    public static void getFollowers(ArrayList<String> retrievingList, ArrayAdapter adapter){
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String> followerList = (ArrayList<String>) value.get("followers");
                retrievingList.clear();
                for(int i = 0; i < followerList.size(); i++){
                    retrievingList.add(followerList.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    // we can get followers and following. If We are following a user and they are following us.
    // we can view their habits.
    public static void getFollowing(ArrayList<String> retrievingList, ArrayAdapter adapter){
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String> followerList = (ArrayList<String>) value.get("following");
                retrievingList.clear();
                for(int i = 0; i < followerList.size(); i++){
                    retrievingList.add(followerList.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    public static void requestFollow(String searchedUser){
        db.collection("Users").document(searchedUser).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> followerList = (ArrayList<String>) documentSnapshot.get("followers");
                if(!followerList.contains(user)){
                    followerList.add(user);
                }
                HashMap<String,Object> hashedData = new HashMap<>();
                hashedData.put("followers",followerList);
                db.collection("Users").document(searchedUser).update(hashedData);
            }
        });
    }



    /**
     *
     * @param friendsList friendsList, the list of strings we want to populate with the usernames of our friends
     * @param adapter adapter, the adapter we want to notify of changes
     */
    public static void getFriends(ArrayList<String> friendsList,ArrayAdapter adapter){
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                friendsList.clear();
                ArrayList<String> followerList = (ArrayList<String>) value.get("following");
                for(int i = 0; i < followerList.size(); i++){
                    String potentialFriend = followerList.get(i);
                    // see if i am followed by my followers
                        db.collection("Users").document(potentialFriend).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ArrayList<String> myFollowersFollowing = (ArrayList<String>) documentSnapshot.get("following");
                            if(myFollowersFollowing.contains(user)){
                                friendsList.add(potentialFriend);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * Recieves data from firebase and populates a recievinglist. then notifies a habitadapter.
     * used to populate lists
     * recievingList, the list we want to recieve into
     * habitAdatper, the habitadapter we want to notify of changes
     * @param recievingList
     * @param habitAdapter
     */
    public static void getAllHabitsRecycler(ArrayList<Habit> recievingList, RecyclerView.Adapter habitAdapter) {
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
                    String UUID = (String)habitFields.get("recordAddress");
                    Long streak = (Long)habitFields.get("streak");
                    boolean visibility = (boolean) habitFields.get("visibility");
                    Habit newHabit = new Habit(name, description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec,new ArrayList<>(),UUID,streak,visibility); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
                habitAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
    @param recievingList, the list we want to to put all of our habits into. This function differs to the previous
    one as it does not assume there is an adapter waiting to be notified.
     */
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
                    String identifier = (String) habitFields.get("recordAddress");
                    boolean visibility = (boolean) habitFields.get("visibility");
                    Habit newHabit = new Habit(name, description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec,new ArrayList<>(),identifier,visibility); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
            }
        });
    }

    /**
    This function pushes an updated habitlist to firebase for the current user
    @param updatedHabits, the new list of habits we want to push to firebase
     */
    public static void updateHabits(ArrayList<Habit> updatedHabits) {
        HashMap<String, Object> listMap = new HashMap<>();
        listMap.put("habits", updatedHabits);
        db.collection("Users").document(user).update(listMap);
    }

    
    /**
     * This function will Get all the habits from the user in firebase, and only add the ones
     * that have their recurrence value on today's date set to true
     * @param recievingList the list we will update
     * @param habitAdapter the adapter we will notify about changes
     * @param posInFirebase the position of the habit in firebase. allows for proper deletion and editing
     */
    public static void getTodaysHabitsRecycler(ArrayList<Habit> recievingList,RecyclerView.Adapter habitAdapter,ArrayList<Integer>
            posInFirebase){
        simpleDateFormat = new SimpleDateFormat("EEEE");
        Date d = new Date();
        //gives the day of the week of the user (if today is actually Monday it will say Monday)
        String dayOfTheWeek = simpleDateFormat.format(d);
        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
                posInFirebase.clear();
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
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
                    String identifier = (String) habitFields.get("recordAddress");
                    Long streak = (Long) habitFields.get("streak");
                    boolean visibility = (boolean) habitFields.get("visibility");
                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec,new ArrayList<>(),identifier,streak,visibility); // create a new habit out of this information

                    //recievingList.add(newHabit);
                    if ((mondayRec == true) && (dayOfTheWeek.equals("Monday"))){
                        recievingList.add(newHabit); // add it to the habitList
                        posInFirebase.add(i);
                    }
                    if ((tuesdayRec == true) && (dayOfTheWeek.equals("Tuesday"))){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }
                    if ((wednesdayRec == true) && (dayOfTheWeek.equals("Wednesday"))){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }
                    if ((thursdayRec == true) && (dayOfTheWeek.equals("Thursday"))){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }
                    if ((fridayRec == true) && (dayOfTheWeek.equals("Friday"))){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }
                    if ((saturdayRec == true) && (dayOfTheWeek.equals("Saturday"))){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }
                    if ((sundayRec == true) && (dayOfTheWeek.equals("Sunday"))){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }

                }
                habitAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * will allow user to get the habits of another user they want to follow or are following
     * @param recievingList the list we will update
     * @param habitAdapter the adapter we will notify about changes
     * @param posInFirebase the position of the habit in firebase
     * @param followingUser the user they want to see
     */
    public static void getPublicHabitsRecycler(ArrayList<Habit> recievingList,RecyclerView.Adapter habitAdapter,ArrayList<Integer>
            posInFirebase, String followingUser){

        db.collection("Users").document(followingUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
                posInFirebase.clear();

                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
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
                    String identifier = (String) habitFields.get("recordAddress");
                    Long streak = (Long) habitFields.get("streak");
                    boolean visibility = (boolean) habitFields.get("visibility");
                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec,new ArrayList<Record>(),identifier,visibility); // create a new habit out of this information

                    //if the habit is public then add to the list to display
                    if (visibility == true){
                        recievingList.add(newHabit);
                        posInFirebase.add(i);
                    }
                }
                habitAdapter.notifyDataSetChanged();
            }
        });
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
    public interface onRegistrationLoginListener {
        void loginUser();
    }

    // login activity will be forced to implement logging in
    public interface onLoginListener {
        void loginUser();
    }


}
