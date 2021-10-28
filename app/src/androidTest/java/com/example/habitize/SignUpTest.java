package com.example.habitize;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUp> activityRule = new ActivityScenarioRule<SignUp>(SignUp.class);

    String testFirstName = "Rick";
    String testLastName = "Grimes";
    String testEmail = "rick0grimes301@gmail.com";
    String testUsername = "Rick";
    String testPassword = "12345678";
    String testConPassword = "12345678";

    @Test
    public static void checkCorrectInput(){
        onView(withId(R.id.create_button)).perform(click());
        
    }
    


}



