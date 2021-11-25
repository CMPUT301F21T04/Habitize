package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;
import com.example.habitize.Activities.ViewHabit.ViewHabitTabsBase;
import com.example.habitize.Activities.ViewHabitLists.AllHabitsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)

public class ViewAndEditTest {
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
    public void Test_1_AllHabitsActivity(){
        onView(withId(R.id.allHabits)).perform(click());
        SystemClock.sleep(4000);
        intended(hasComponent(AllHabitsActivity.class.getName()));
    }
    //check if we can access view habits
    @Test
    public void Test_2_ViewActivity(){
//        //deleting the habit
//        onView(withId(R.id.allHabits)).perform(click());
//        onView(withText("VIEW")).perform(click());
//        SystemClock.sleep(4000);
//        onView(withText("DELETE")).perform(click());
//        intended(hasComponent(AllHabitsActivity.class.getName()));
//
//        //add new habit first
//
//        onView(isRoot()).perform(ViewActions.pressBack());
//        onView(withId(R.id.addHabit)).perform(click());
//        SystemClock.sleep(1000);
//        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
//        SystemClock.sleep(1000);
//        onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
//        onView(withId(R.id.fragmentStartDate)).perform(click());
//        onView(withText("OK")).perform(click());
//        onView(withId(R.id.create_habit_tabs)).perform(click());
        //go to view habits
        onView(withId(R.id.allHabits)).perform(click());
        onView(withText("VIEW")).perform(click());
        SystemClock.sleep(4000);
        intended(hasComponent(ViewHabitTabsBase.class.getName()));
    }


    @Test
    public void Test_4_DeleteHabitActivity(){
        onView(withId(R.id.allHabits)).perform(click());
        onView(withText("VIEW")).perform(click());
        SystemClock.sleep(4000);
        onView(withText("DELETE")).perform(click());
        intended(hasComponent(AllHabitsActivity.class.getName()));
    }
    @Test
    public void Test_3_EditHabitActivity(){
        //add new habit first
        onView(withId(R.id.addHabit)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitTitle)).perform(replaceText(testTitle));
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentHabitDescription)).perform(replaceText(testDesc));
        onView(withId(R.id.fragmentStartDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        //editing start here
        onView(withId(R.id.allHabits)).perform(click());
        onView(withText("VIEW")).perform(click());
        SystemClock.sleep(4000);
        onView(withId(R.id.AllowEditing)).perform(click());
        SystemClock.sleep(1000);
        onView(withText(testTitle)).perform(replaceText(testEditTitle));
        onView(withId(R.id.ConfirmEdit)).perform(click());

        SystemClock.sleep(1000);
        onView(withText("VIEW")).perform(click());
        SystemClock.sleep(1000);
        onView(withText(testEditTitle)).check(matches(withText(testEditTitle)));



    }
    @After
    public void tearDown() {
        Intents.release();
    }
}

