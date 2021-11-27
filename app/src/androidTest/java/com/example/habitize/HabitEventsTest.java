package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.habitize.Activities.AddHabit.AddHabitTabsBase;
import com.example.habitize.Activities.Followers.FollowingActivity;
import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;
import com.example.habitize.Activities.ViewHabitLists.AllHabitsActivity;
import com.example.habitize.Activities.ViewHabitLists.TodaysHabitsActivity;
import com.example.habitize.Activities.ViewRecord.ViewRecordBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void initValidate(){
        Intents.init();
        testEmail = "rick0grimes301@gmail.com";
        testPassword = "12345678";
        testTitle = "Learn French";
        testEditTitle = "Learn English";
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
    public void Test_0_successLogin() {

        intended(hasComponent(MainActivity.class.getName()));
    }
    //Check the add habits button if it works and if it takes you to
    //the right activity
    @Test
    public void Test_1_RecordHabitActivity(){
        onView(withId(R.id.allHabitCard)).perform(click());

        SystemClock.sleep(1000);
        intended(hasComponent(ViewRecordBase.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}

