package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class Login_ActivityTest {
    String testEmail;
    String testPassword;
    String testIncorrectPass;


    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void initValidate(){
        Intents.init();
        testEmail = "lll@gmail.com";
        testPassword = "12345678";
        testIncorrectPass = "123";
    }

    @After
    public void tearDown() {
        Intents.release();
    }


//   Tests if the error handling for email works
    @Test
    public void emptyEmail(){
        onView(withId(R.id.email_login))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.LoginBTN)).perform(click());
        // The screen shouldn't move because invalid input on password
        intending(hasComponent(Login_Activity.class.getName()));

    }
//  Tests if the error handling for password works
    @Test
    public void emptyPass(){
        onView(withId(R.id.email_login))
                .perform(typeText(testEmail),closeSoftKeyboard());
        onView(withId(R.id.LoginBTN)).perform(click());
        // The screen shouldn't move because invalid input on password
        intending(hasComponent(Login_Activity.class.getName()));

    }

//  Tests if the user enters a valid password (>= 8 characters)
    @Test
    public void lessthan8Pass(){
        onView(withId(R.id.email_login))
                .perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.password_login))
                .perform(typeText(testIncorrectPass), closeSoftKeyboard());
        onView(withId(R.id.LoginBTN)).perform(click());
        // The screen shouldn't move because invalid input on password
        intending(hasComponent(Login_Activity.class.getName()));
    }


//    Tests if the user enters an email with valid format
    @Test
    public void testSetup() throws IOException {
        onView(withId(R.id.email_login)).perform(replaceText("username"));
        onView(withId(R.id.password_login)).perform(replaceText("password"));
        onView(withId(R.id.LoginBTN)).perform(click());
        // The screen shouldn't move because invalid input on password
        intending(hasComponent(Login_Activity.class.getName()));
    }

//    Tests if the user successfully login
    @Test
    public void successLogin(){
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
        intended(hasComponent(MainActivity.class.getName()));

    }

//    Tests if the user failed to login
    @Test
    public void failedLogin(){
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText("wrongPass"));
        onView(withId(R.id.LoginBTN)).perform(click());
        onView(hasErrorText("Login Failed"));
        // The screen shouldn't move because invalid input on password
        intending(hasComponent(Login_Activity.class.getName()));
    }
}



