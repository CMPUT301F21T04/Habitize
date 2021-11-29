
package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import android.os.SystemClock;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



public class HabitEventTest {
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
        testEmail = "maya123@gmail.com";
        testPassword = "12345678";
        testTitle = "Learn Arabic";
        testEditTitle = "Learn English";
        testDesc = "Go on DuoLingo";
        testStartDate = "11/05/2021";
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
        SystemClock.sleep(1000);
        onView(withId(R.id.fragmentMonday)).perform(click());
        onView(withId(R.id.fragmentTuesday)).perform(click());
        onView(withId(R.id.fragmentWednesday)).perform(click());
        onView(withId(R.id.fragmentThursday)).perform(click());
        onView(withId(R.id.fragmentFriday)).perform(click());
        onView(withId(R.id.fragmentSaturday)).perform(click());
        onView(withId(R.id.fragmentSunday)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.create_habit_tabs)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()));SystemClock.sleep(3000);
        onView(withId(R.id.allHabitCard)).perform(scrollTo(),click());
        SystemClock.sleep(3000);

        //create a record
        onView(withId(R.id.completeHabit)).perform(click());
        SystemClock.sleep(3000);
        // only record comment is required
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