package com.example.habitize;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;
import com.example.habitize.Activities.ViewHabitLists.TodaysHabitsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TodaysHabitTest {
    String testEmail;
    String testPassword;
    String testTitle;
    String testDesc;
    String testStartDate;

    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    //create a habit before testing Today's Habit Screen
    @Before
    public void getToScreenAndMakeAHabit(){
        Intents.init();
        testEmail = "rick0grimes301@gmail.com";
        testPassword = "12345678";
        testTitle = "Learn French";
        testDesc = "Go on DuoLingo";
        testStartDate = "12/05/2021";
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        SystemClock.sleep(1000);
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.addHabitCard)).perform(scrollTo(),click());
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
    }

    //make a habit and make sure recurrence is made on system's day
    //if it was made correctly, then go to todays habit screen and check if its there
    @Test
    public void recurrenceTest(){
        onView(withId(R.id.todayHabitCard)).perform(scrollTo(),click());
        onView(withText("Learn French")).perform(click());
        SystemClock.sleep(1000);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

}
