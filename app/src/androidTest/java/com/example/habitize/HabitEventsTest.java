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

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

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
import java.util.ArrayList;

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


    @Test
    public void Test_1_RecordHabitActivity(){
        // This test only test creation of record with just a comment, the only required one.
        // There will be a seperate test for images and location
        //check record activity is displayed
        onView(withId(R.id.addHabitCard)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
        onView(withId(R.id.fragmentStartDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()));
        SystemClock.sleep(3000);
        onView(withId(R.id.allHabitCard)).perform(scrollTo(),click());
        SystemClock.sleep(3000);

        //create a record
        onView(withId(R.id.completeHabit)).perform(click());
        SystemClock.sleep(3000);
        // only record is required
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

    }

    @After
    public void tearDown() {
        Intents.release();
    }
}

