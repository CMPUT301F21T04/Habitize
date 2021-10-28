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
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

import android.app.Activity;
import android.os.SystemClock;
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

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    String testFirstName;
    String testLastName;
    String testEmail;
    String testUsername;
    String testPassword;
    String testConPassword;
    String testIncorrectPass;
    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);


    @Before
    public void initValidate(){
        Intents.init();
        testFirstName = "Rick";
        testLastName = "Grimes";
        testEmail = "rick0grimes301@gmail.com";
        testUsername = "Rick";
        testPassword = "12345678";
        testConPassword = "12345678";
        testIncorrectPass = "123";
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    //   Tests if the error handling for email works
    @Test
    public void emptyEmail(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.create_button)).perform(click());
        onView(withId(R.id.email)).check(matches(hasErrorText("Enter an email please!")));
    }
    //  Tests if the error handling for password works
    @Test
    public void emptyPass(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email))
                .perform(typeText(testEmail),closeSoftKeyboard());
        onView(withId(R.id.create_button)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("Please enter a password!")));
    }

    //  Tests if the user enters a valid password (>= 8 characters)
    @Test
    public void lessthan8Pass(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email))
                .perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(testIncorrectPass), closeSoftKeyboard());
        onView(withId(R.id.create_button)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("Password should be greater than 8 characters")));
    }


    //    Tests if the user enters an email with valid format
    @Test
    public void testSetup() throws IOException {
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email)).perform(replaceText("username"));
        onView(withId(R.id.password)).perform(replaceText("password"));
        onView(withId(R.id.create_button)).perform(click());
    }

    //    Tests if the user successfully login
    @Test
    public void successSignUp(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email)).perform(replaceText(testEmail));
        onView(withId(R.id.password)).perform(replaceText(testPassword));
        onView(withId(R.id.conPassword)).perform(replaceText(testPassword));
        onView(withId(R.id.create_button)).perform(click());
        SystemClock.sleep(5000);
        intended(hasComponent(MainActivity.class.getName()));

    }

    //    Tests if the user failed to login
    @Test
    public void failedLogin(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email)).perform(replaceText(testEmail));
        onView(withId(R.id.password)).perform(replaceText("wrongPass"));
        onView(withId(R.id.create_button)).perform(click());
        onView(hasErrorText("Login Failed"));

    }
}



