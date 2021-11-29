package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.habitize.Activities.SignupAndLogin.Login_Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
//PLEASE RUN FIRST SignUp_ActivityTest OR THIS TEST WILL NOT WORK ALL THE TIME!
//Because this test uses user account that is made in SignUp_ActivityTest
//and if the data base got cleaned then test user account will not exist
@RunWith(AndroidJUnit4.class)
public class AllHabitsActivityTest {

        String testEmail;
        String testPassword;
        String testTitle;
        String testDesc;
        String testTitle2;
        String testDesc2;

        @Rule
        public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

        //create a habit before testing Today's Habit Screen
        @Before
        public void getToScreenAndMakeAHabit(){
            Intents.init();
            testEmail = "rick0grimes301@gmail.com";
            testPassword = "12345678";
            testTitle = "Learn Spanish";
            testDesc = "Go on DuoLingo Today";
            onView(withId(R.id.email_login)).perform(replaceText(testEmail));
            onView(withId(R.id.password_login)).perform(replaceText(testPassword));
            SystemClock.sleep(1000);
            onView(withId(R.id.LoginBTN)).perform(click());
            SystemClock.sleep(5000);
            onView(withId(R.id.addHabitCard)).perform(click());
            SystemClock.sleep(1000);
            onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
            SystemClock.sleep(1000);
            onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
            onView(withId(R.id.fragmentStartDate)).perform(click());
            onView(withText("OK")).perform(click());
            onView(withId(R.id.fragmentThursday)).perform(click());
            onView(withId(R.id.fragmentFriday)).perform(click());
            onView(withId(R.id.fragmentSaturday)).perform(click());
            onView(withId(R.id.fragmentSunday)).perform(click());
            onView(withId(R.id.create_habit_tabs)).perform(click());

            testTitle2 = "Learn Mandarin";
            testDesc2 = "Go on DuoLingo Now";
            onView(withId(R.id.addHabitCard)).perform(click());
            SystemClock.sleep(1000);
            onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle2));
            SystemClock.sleep(1000);
            onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc2));
            onView(withId(R.id.fragmentStartDate)).perform(click());
            onView(withText("OK")).perform(click());
            onView(withId(R.id.fragmentMonday)).perform(click());
            onView(withId(R.id.fragmentTuesday)).perform(click());
            onView(withId(R.id.create_habit_tabs)).perform(click());

        }

        //make a habit and make sure recurrence is made on system's day
        //if it was made correctly, then go to todays habit screen and check if its there
        @Test
        public void HabitsInAllHabitsListTest(){
            onView(withId(R.id.allHabitCard)).perform(click());
            onView(withText("Learn Spanish")).perform(click());
            onView(isRoot()).perform(ViewActions.pressBack());
            onView(withText("Learn Mandarin"));
            SystemClock.sleep(1000);
        }

        @After
        public void tearDown() {
            Intents.release();
        }

}



