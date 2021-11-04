package com.example.habitize;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    interface registrationListener{
        void continueRegistration();
    }

    // db is shared across all instances of the manager
    private static FirebaseFirestore db;
    private static String user;
    private static registrationListener listener;

    private static SimpleDateFormat simpleDateFormat;

    // we initialize the firestore ONCE. Many objects but all will refer to the same instance
    static {
        db = FirebaseFirestore.getInstance();
    }


    // activity we initialize this in will have to implement the registration and loggin in
    DatabaseManager(Context context){
        listener = (registrationListener) context;
    }



    public static void logUserIn(String user){


    }




    // get all habits and put them into a list. Then notify the habitAdapter
    public static void getAllHabits(String user,ArrayList<Habit> recievingList,CustomAdapter habitAdapter){

        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
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
                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
                habitAdapter.notifyDataSetChanged();
            }
        });
    }
    // get all the habits and put into a list, but don't update a habitAdapter
    public static void getAllHabits(String user,ArrayList<Habit> recievingList){

        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
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
                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    recievingList.add(newHabit); // add it to the habitList
                }
            }
        });
    }



    // TODO: FInish writing this method
    public static void checkUserNameExistence(String user){
        db.collection("User").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                listener.continueRegistration();
            }
        });

    }


    public static void updateHabits(String user, ArrayList<Habit> updatedHabits){
        HashMap<String,Object> listMap = new HashMap<>();
        listMap.put("habits",updatedHabits);
        db.collection("Users").document(user).update(listMap);

    }

    public static void updateHabitsAtDay(String user, ArrayList<Habit> updatedHabits,String day){

    }

    public static boolean userExists(){
        return true;
    }


    // returns the user's habits if the user is found.
    // returns null if user does not exist
    public ArrayList<Habit> findUserHabits(String user){
        return new ArrayList<>();
    }

//    public static boolean DayOfWeek(String dayOfWeekGiven){
//        simpleDateFormat = new SimpleDateFormat("EEEE");
//        Date d = new Date();
//        //gives the day of the week of the user (if today is actually Monday it will say Monday)
//        String dayOfTheWeek = simpleDateFormat.format(d);
//        if (dayOfTheWeek.equals(dayOfWeekGiven)){
////            System.out.println("Dayoftheweek is~~~~~~ " + dayOfTheWeek);
////            System.out.println("daysofweekGIVEN is~~~~~~~" + dayOfWeekGiven);
//            return true;
//        }
////        System.out.println("Dayoftheweek is~~~~~~ " + dayOfTheWeek);
////        System.out.println("daysofweekGIVEN is~~~~~~~" + dayOfWeekGiven);
//        return false;
//    }


    // get all habits and put them into a list. Then notify the habitAdapter
    public static void getTodaysHabits(String user,ArrayList<Habit> recievingList,CustomAdapter habitAdapter){
        simpleDateFormat = new SimpleDateFormat("EEEE");
        Date d = new Date();
        //gives the day of the week of the user (if today is actually Monday it will say Monday)
        String dayOfTheWeek = simpleDateFormat.format(d);

//        if(dayOfTheWeek.equals("")){
//            System.out.println("YESYESYESYESYEYSEYSEEYYESYEYSEYSYE");
//        }
//        else{
//            System.out.println("NONONONONONONONONONONONONOOOOOOOO");
//        }

        db.collection("Users").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                recievingList.clear();
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
                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information

                    //recievingList.add(newHabit);
                    if ((mondayRec = true) && (dayOfTheWeek.equals("Monday"))){
                        recievingList.add(newHabit); // add it to the habitList
                    }
                    if ((tuesdayRec = true) && (dayOfTheWeek.equals("Tuesday"))){
                        recievingList.add(newHabit);
                    }
                    if ((wednesdayRec = true) && (dayOfTheWeek.equals("Wednesday"))){
                        recievingList.add(newHabit);
                    }
                    if ((thursdayRec = true) && (dayOfTheWeek.equals("Thursday"))){
                        recievingList.add(newHabit);
                    }
                    if ((fridayRec = true) && (dayOfTheWeek.equals("Friday"))){
                        recievingList.add(newHabit);
                    }
                    if ((saturdayRec = true) && (dayOfTheWeek.equals("Saturday"))){
                        recievingList.add(newHabit);
                    }
                    if ((sundayRec = true) && (dayOfTheWeek.equals("Sunday"))){
                        recievingList.add(newHabit);
                    }

                }
                habitAdapter.notifyDataSetChanged();
            }
        });
    }


}
