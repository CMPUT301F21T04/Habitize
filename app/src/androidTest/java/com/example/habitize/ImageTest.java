package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.habitize.Controllers.EspressoTestsMatchers.withDrawable;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.MediaStore;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.habitize.Activities.AddHabit.AddHabitImageFragment;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ImageTest {
    private Instrumentation.ActivityResult mActivityResult;
    private String testEmail = "rick0grimes301@gmail.com";
    private String testPassword = "12345678";

    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void gotoaddimg(){
        Intents.init();
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.addHabitCard)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Image")).perform(click());
    }

    @After
    public void tearDown() {
        Intents.release();
    }


    @Test
    public void imageViewisDisplayed(){
        onView(withId(R.id.new_image)).check(matches(isDisplayed()));
    }

    @Test
    public void noImage(){
        boolean isTrue = AddHabitImageFragment.getIMGStat();
        assertThat(isTrue,is(true));
        onView(withId(R.id.new_image)).check(matches(not(withDrawable(R.drawable.ic_baseline_sentiment_dissatisfied_24))));
    }

    @Test
    public void withGallery(){
        // checks if the intent matches when app asks to access gallery
        SystemClock.sleep(1000);
        onView(withId(R.id.new_image_btn)).perform(click());
        SystemClock.sleep(8000);
        intending(CoreMatchers.allOf(hasAction((Intent.ACTION_PICK)), hasData(MediaStore.Images.Media.INTERNAL_CONTENT_URI)));
    }

    @Test
    public void withCam(){
        // check if the intent matches when app asks to access gallery
        SystemClock.sleep(1000);
        onView(withId(R.id.new_camera_btn)).perform(click());
        SystemClock.sleep(8000);
        intending(CoreMatchers.allOf(hasAction((MediaStore.ACTION_IMAGE_CAPTURE))));
    }
}
