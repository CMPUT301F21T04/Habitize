package com.example.habitize;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//should change title according to which class
@RunWith(AndroidJUnit4.class)
public class AddHabitBaseFragmentTest {
    String testTitle;
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
    public ActivityScenarioRule<AddHabitTabsBase> activityRule = new ActivityScenarioRule<AddHabitTabsBase>(AddHabitTabsBase.class);

    @Before
    public void initValidate() {
        Intents.init();
        testTitle = "Learn French";
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

    @After
    public void tearDown() {
        Intents.release();
    }

    //use activity add habit info xml?
    //Make sure title not empty
    @Test
    public void emptyTitleTest() {

        onView(withId(R.id.fragmentHabitTitle)).perform(typeText(testTitle), (ViewAction) closeSoftKeyboard());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        onView(withId(R.id.fragmentHabitTitle)).check(matches(hasErrorText("Enter a habit title")));
    }

    //Make sure description not empty
    @Test
    public void emptyDescTest() {
        onView(withId(R.id.habitDescription)).perform(typeText(testDesc), closeSoftKeyboard());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        onView(withId(R.id.habitDescription)).check(matches(hasErrorText("Enter a habit title")));
    }

    //Make sure start date not empty
    @Test
    public void emptyStartDateTest() {
        onView(withId(R.id.startDate)).perform(typeText(testStartDate), closeSoftKeyboard());
        onView(withId(R.id.create_habit_tabs)).perform(click());
        onView(withId(R.id.startDate)).check(matches(hasErrorText("Enter a habit title")));
    }

    //Make sure habit title is less than 20 chars

    //Make sure habit description is less than 30 chars

    //Make sure check box selection works
    @Test
    public void recurrenceCheckBoxTest() {

    }

    @Test
    public void habitCreatedSuccess(){

    }

    @Test
    public void habitCreatedFailed(){

    }

    // TO DO: UI TESTING!!!

}



