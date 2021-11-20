package com.example.habitize;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.google.android.material.internal.ContextUtils.getActivity;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecordCreateTest {
    String testEmail;
    String testPassword;

    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);


    @Before
    public void initValidate(){
        Intents.init();
        testEmail = "pa@gmail.com";
        testPassword = "12345678";
    }

    @Before
    public void login(){
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
    }

    @Before
    public void gotoRecordCreate(){
        onView(withId(R.id.todaysHabits)).perform(click());
        onView(withId(R.id.completeHabit)).perform(click());
        SystemClock.sleep(2000);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void dialogIsDisplayed(){
        onView(withText("New Record")).check(matches(isDisplayed()));
    }

    @Test
    public void AddCommentSuccess(){
        onView(withId(R.id.recordComment)).perform(replaceText("I ran today but only half a mile."));
        onView(withText("Confirm")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Confirm")).perform(click());
    }

    @Test
    public void NoCommentFail(){
        onView(withText("Confirm")).perform(click());
        onView(hasErrorText("Text is required! Please input comment."));
    }

    @Test
    public void ClickImgButton(){
        onView(withId(R.id.recordImgBTN)).perform(click());
//        intending(hasAction(Intent.ACTION_PICK)).respondWith(getCroppedImageResult());
    }

    @Test
    public void ClickLocButton(){
        onView(withId(R.id.recordLocBTN)).perform(click());
        Intent intent = new Intent();


    }

}
