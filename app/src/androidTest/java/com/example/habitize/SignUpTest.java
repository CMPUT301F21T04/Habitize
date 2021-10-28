package com.example.habitize;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);




    /**
     public void setUp() {
     Intents.init();
     }
     **/
    @Before
    public void initValidString() {
        onView(withId(R.id.RegisterBTN)).perform(click());
        String testFirstName = "Rick";
        String testLastName = "Grimes";
        String testEmail = "rick0grimes301@gmail.com";
        String testUsername = "Rick";
        String testPassword = "12345678";
        String testConPassword = "12345678";
    }
    /**@After
    public void tearDown() {
    Intents.release();
    }
     **/
    @Test
    public void checkCorrectInputSignUp(){
        onView(withId(R.id.email))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.create_button)).perform(click());
        onView(withId(R.id.email)).check(matches(hasErrorText("Enter an email please!")));
    }

}



