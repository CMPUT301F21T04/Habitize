package com.example.habitize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
public class MainActivityTests {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void correctViewTest(){
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
    }

    @Test
    public void switchAllHabitsTest(){
        solo.clickOnButton("TODAY'S HABITS");
        solo.assertCurrentActivity("Wrong activity",AllHabitsActivity.class);
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }



}