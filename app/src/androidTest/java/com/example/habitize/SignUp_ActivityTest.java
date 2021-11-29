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
//PLEASE RUN FIRST SignUp_ActivityTest OR THIS TEST WILL NOT WORK ALL THE TIME!
//Because this test uses user account that is made in SignUp_ActivityTest
//and if the data base got cleaned then test user account will not exist
@RunWith(AndroidJUnit4.class)
public class SignUp_ActivityTest {
    String testFirstName;
    String testLastName;
    String testEmail;
    String testUsername;
    String testPassword;
    String testConPassword;
    String testIncorrectPass;
    //user two
    String testEmailOtherUser;
    String testFirstNameOtherUser;
    String testLastNameOtherUser;
    String testUsernameOtherUser;
    String testPasswordOtherUser;
    String testConPasswordOtherUser;

    // tests fail because of this error
    //https://stackoverflow.com/questions/66338416/internal-error-in-cloud-firestore-22-1-0-when-running-instrumentation-test
    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void initValidate(){
        Intents.init();
        testFirstName = "Rick";
        testLastName = "Grimes";
        testEmail = "rick0grimes301@gmail.com";
        testUsername = "RickGrimes";
        testPassword = "12345678";
        testConPassword = "12345678";
        testIncorrectPass = "123";

        //User two
        testEmailOtherUser = "negen0evil301@gmail.com";
        testFirstNameOtherUser = "negen";
        testLastNameOtherUser = "Evil";
        testUsernameOtherUser = "negenEvil";
        testPasswordOtherUser= "12345678";
        testConPasswordOtherUser = "12345678";

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
        onView(withId(R.id.firstName)).perform(replaceText(testFirstName));
        onView(withId(R.id.lastName)).perform(replaceText(testLastName));
        onView(withId(R.id.userName)).perform(replaceText(testUsername));
        onView(withId(R.id.password)).perform(replaceText(testPassword));
        onView(withId(R.id.conPassword)).perform(replaceText(testPassword));
        onView(withId(R.id.create_button)).perform(click());
        SystemClock.sleep(5000);
        intended(hasComponent(MainActivity.class.getName()));


    }
    @Test
    public void successSignUpWithOtherUser(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email)).perform(replaceText(testEmailOtherUser));
        onView(withId(R.id.firstName)).perform(replaceText(testFirstNameOtherUser));
        onView(withId(R.id.lastName)).perform(replaceText(testLastNameOtherUser));
        onView(withId(R.id.userName)).perform(replaceText(testUsernameOtherUser));
        onView(withId(R.id.password)).perform(replaceText(testPasswordOtherUser));
        onView(withId(R.id.conPassword)).perform(replaceText(testPasswordOtherUser));
        onView(withId(R.id.create_button)).perform(click());
        SystemClock.sleep(5000);
        intended(hasComponent(MainActivity.class.getName()));


    }

    //    Tests if the user failed to login
    @Test
    public void failedLogin(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.email)).perform(replaceText(testEmailOtherUser));
        onView(withId(R.id.password)).perform(replaceText("wrongPass"));
        onView(withId(R.id.create_button)).perform(click());
        onView(hasErrorText("Login Failed"));

    }
    @Test public void NotUniqeUserName(){
        onView(withId(R.id.RegisterBTN)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText(testEmail));

    }

}



