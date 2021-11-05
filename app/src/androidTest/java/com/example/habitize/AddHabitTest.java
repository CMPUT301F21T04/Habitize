package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class AddHabitTest {
    String testEmail;
    String testPassword;
    String testTitle;
    String testDesc;
    String testStartDate;
    Boolean testMRec;
    Boolean testTRec;
    Boolean testWRec;
    Boolean testThRec;
    Boolean testFRec;
    Boolean testSRec;
    Boolean testSuRec;

    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void initValidate(){
        Intents.init();
        testEmail = "rasoulpo@ualberta.ca";
        testPassword = "password";
        testTitle = "Learn French";
        testDesc = "Go on DuoLingo and practice French";
        testStartDate = "11/05/2021";
        testMRec = true;
        testTRec = false;
        testWRec = true;
        testThRec = false;
        testFRec = true;
        testSRec = false;
        testSuRec = false;
    }

    @Before
    public void successLoginToAddHabitTravelTest(){
//        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
//        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
//        onView(withId(R.id.LoginBTN)).perform(click());
//        SystemClock.sleep(5000);
//        intended(hasComponent(MainActivity.class.getName()));
//        onView(withId(R.id.addHabit)).perform(click());
//        intended(hasComponent(AddHabitTabsBase.class.getName()));
    }

    //Make sure title not empty
    @Test
    public void emptyTitleTest() {
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(200000);
        intended(hasComponent(MainActivity.class.getName()));
//        onView(withId(R.id.addHabit)).perform(click());
//        intended(hasComponent(AddHabitTabsBase.class.getName()));
//        onView(withId(R.id.fragmentHabitTitle)).perform(typeText(testTitle), (ViewAction) closeSoftKeyboard());
//        onView(withId(R.id.create_habit_tabs)).perform(click());
//        onView(withId(R.id.fragmentHabitTitle)).check(matches(hasErrorText("Enter a habit title")));
    }

    @After
    public void tearDown() {
        Intents.release();
    }






}



