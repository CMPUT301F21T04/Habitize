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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;

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
        testEmail = "rick0grimes301@gmail.com";
        testPassword = "12345678";
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
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);

    }

    //Make sure that its signed in
    @Test
    public void successLogin() {

        intended(hasComponent(MainActivity.class.getName()));
    }
    //Check the add habits button if it works and if it takes you to
    //the right activity
    @Test
    public void addNewHabitAcctivity(){
        onView(withId(R.id.addHabit)).perform(click());
        SystemClock.sleep(1000);
        intended(hasComponent(AddHabitTabsBase.class.getName()));
    }
    //Check the error massage when no data added
    @Test
    public void EmptyInputs(){
        onView(withId(R.id.addHabit)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.create_habit_tabs)).perform(click());
        intended(hasComponent(AddHabitTabsBase.class.getName()));
    }
    //Add title to a new habit without the rest of the data
    @Test
    public void IncompleteInputs(){
        onView(withId(R.id.addHabit)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
        onView(withId(R.id.create_habit_tabs)).perform(click());
        SystemClock.sleep(3000);
        intended(hasComponent(AddHabitTabsBase.class.getName()));
    }

    @Test
    public void createNewHabitSuccessful(){
        onView(withId(R.id.addHabit)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
        onView(withId(R.id.fragmentStartDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));

    }




    @After
    public void tearDown() {
        Intents.release();
    }

}



