package com.example.habitize;

import static androidx.core.content.FileProvider.getUriForFile;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.habitize.Activities.AddHabit.AddHabitTabsBase;
import com.example.habitize.Activities.Followers.FollowingActivity;
import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;
import com.example.habitize.Activities.ViewHabit.ViewRecordsFragment;
import com.example.habitize.Activities.ViewHabitLists.AllHabitsActivity;
import com.example.habitize.Activities.ViewHabitLists.TodaysHabitsActivity;
import com.example.habitize.Activities.ViewRecord.MapFragment;
import com.example.habitize.Activities.ViewRecord.ViewRecordBase;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

public class HabitEventsTest {
    String testEmail;
    String testPassword;
    String testTitle;
    String testEditTitle;
    String testDesc;
    String testStartDate;
    Boolean testMRec;
    Boolean testTRec;
    Boolean testWRec;
    Boolean testThRec;
    Boolean testFRec;
    Boolean testSRec;
    Boolean testSuRec;
    String recordComment;

    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void initValidate(){
        Intents.init();
        testEmail = "rick0grimes301@gmail.com";
        testPassword = "12345678";
        testTitle = "Learn French";
        testEditTitle = "Learn English";
        testDesc = "Go on DuoLingo";
        testStartDate = "11/05/2021";
        testMRec = true;
        testTRec = false;
        testWRec = true;
        testThRec = false;
        testFRec = true;
        testSRec = false;
        testSuRec = false;
        recordComment = "done";
    }

    @Before
    public void successLoginToAddHabitTravelTest(){
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);

    }


    @Before
    public void Test_1_createHabit(){
        onView(withId(R.id.addHabitCard)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
        onView(withId(R.id.fragmentStartDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
    //Check the add habits button if it works and if it takes you to
    //the right activity
    //Make sure that its signed in
    @Test
    public void Test_0_successLogin() {

        intended(hasComponent(MainActivity.class.getName()));
    }
    @Test
    public void Test_1_RecordHabitActivity(){
        //check record activity is displayed
        SystemClock.sleep(3000);
        onView(withId(R.id.allHabitCard)).perform(scrollTo(),click());
        SystemClock.sleep(3000);

        //create a record
        onView(withId(R.id.completeHabit)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.recordComment)).perform(replaceText(recordComment));
        SystemClock.sleep(1000);
        onView(withId(R.id.createRecord)).perform(click());
        onView(withText(testTitle)).perform(click());
        onView(withId(R.id.habitName)).perform(click());
        onView(withId(R.id.FragmentViewHabitTitle)).perform(swipeLeft());
        SystemClock.sleep(1000);
        onView(withId(R.id.FragmentViewHabitNewImage)).perform(swipeLeft());
        SystemClock.sleep(2000);
        //view habit
        onView(withId(R.id.recordDate)).check(matches(isDisplayed())).perform(click());
        onView(withText("Image")).perform(click());
        onView(withText("Location")).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        //delete the habit
        onView(withId(R.id.delete_button_tabs)).perform(click());
        //delete the habit
        onView(withId(R.id.delete_button_tabs)).perform(click());
    }

    @Test
    public void Test_2_TestImageInRecord(){
        //check record activity is displayed
        SystemClock.sleep(3000);
        onView(withId(R.id.allHabitCard)).perform(scrollTo(),click());
        SystemClock.sleep(3000);

        //create a record
        onView(withId(R.id.completeHabit)).perform(click());
        SystemClock.sleep(3000);
        //add comment
        onView(withId(R.id.recordComment)).perform(replaceText(recordComment));
        SystemClock.sleep(1000);
        // add image
        onView(withText("Image")).perform(click());
        Intent resultData = new Intent();
        String filename = "image.jpeg";
        String path = "content://com.android.providers.downloads.documents/document/downloads" + filename;
        File f = new File(path);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Uri contentUri = getUriForFile(context, "com.otsuka.ikigai.fileprovider", f);
        resultData.setData(Uri.parse("content://com.android.providers.downloads.documents/document/downloads/image.jpeg"));
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK,resultData);
        intending(not(isInternal())).respondWith(result);
        onView(withText("Load Image")).perform(click());
//        Matcher<Intent> expectedIntent = AllOf.allOf(IntentMatchers.hasAction(Intent.ACTION_PICK),
//                IntentMatchers.hasData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
//        intending(expectedIntent).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, getGalleryIntent()));


        SystemClock.sleep(8000);
        //create record
        onView(withId(R.id.createRecord)).perform(click());

        // click to view the habit
        onView(withText(testTitle)).perform(click());
        onView(withId(R.id.habitName)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.FragmentViewHabitTitle)).perform(swipeLeft());
        SystemClock.sleep(1000);

        // view the habit event / record
        onView(withId(R.id.recordDate)).check(matches(isDisplayed()));
        onView(withId(R.id.recordDate)).check(matches(isDisplayed())).perform(click());
        onView(withText("Image")).perform(click());
        onView(withText("Location")).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        //delete the habit
        onView(withId(R.id.delete_button_tabs)).perform(click());
    }



    @After
    public void tearDown() {
        Intents.release();
    }
}

