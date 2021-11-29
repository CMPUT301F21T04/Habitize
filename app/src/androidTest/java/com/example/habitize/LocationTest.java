package com.example.habitize;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LocationTest {
    private String testEmail,testPassword,testTitle,testDesc,testEditTitle,recordComment,testStartDate;
    private boolean testMRec,testTRec,testWRec,testThRec,testFRec,testSRec,testSuRec;
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
    public void setUp(){
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
    }

    @Test
    public void locationNotNull(){
        // create a habit
        onView(withId(R.id.addHabitCard)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
        onView(withId(R.id.fragmentStartDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.fragmentMonday)).perform(click());
        onView(withId(R.id.fragmentTuesday)).perform(click());
        onView(withId(R.id.fragmentWednesday)).perform(click());
        onView(withId(R.id.fragmentThursday)).perform(click());
        onView(withId(R.id.fragmentFriday)).perform(click());
        onView(withId(R.id.fragmentSaturday)).perform(click());
        onView(withId(R.id.fragmentSunday)).perform(click());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()));
        SystemClock.sleep(3000);
        onView(withId(R.id.allHabitCard)).perform(scrollTo(),click());
        SystemClock.sleep(3000);

        // create a record event
        //create a record
        onView(withId(R.id.completeHabit)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.recordComment)).perform(replaceText(recordComment));
        SystemClock.sleep(1000);
        // check if location is not null
        onView(withText("Location")).perform(click());
        onView(withId(R.id.addressView)).check(matches(not(withText(""))));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
