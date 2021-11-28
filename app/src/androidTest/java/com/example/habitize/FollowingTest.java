package com.example.habitize;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.habitize.Activities.AddHabit.AddHabitTabsBase;
import com.example.habitize.Activities.Followers.FollowRequests;
import com.example.habitize.Activities.Followers.FollowingActivity;
import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Activities.SignupAndLogin.Login_Activity;
import com.example.habitize.Activities.ViewHabitLists.AllHabitsActivity;
import com.example.habitize.Activities.ViewHabitLists.TodaysHabitsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FollowingTest {

    String testEmail;
    String testPassword;
    String testUsername;
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
    //other user
    String testEmailOtherUser;
    String testPasswordOtherUser;
    String testUsernameOtherUser;


    @Rule
    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);

    @Before
    public void initValidate(){
        Intents.init();
        testEmail = "rick0grimes301@gmail.com";
        testPassword = "12345678";
        testUsername = "RickGrimes";
        testTitle = "Learn French";
        testEditTitle = "Learn English";
        testDesc = "Go on DuoLingo and practice French";
        testStartDate = "11/05/2021";
        testMRec = true;
        testTRec = false;
        testWRec = true;
        testThRec = false;
        testFRec = true;
        testSRec = false;
        testSuRec = false;

        //other user
        testEmailOtherUser = "negen0evil301@gmail.com";
        testPasswordOtherUser= "12345678";
        testUsernameOtherUser = "negenEvil";

    }

    @Before
    public void successLoginToAddHabitTravelTest(){
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);

    }

    //Make sure that its signed in
    @Test
    public void Test_0_successLogin() {

        intended(hasComponent(MainActivity.class.getName()));
    }
    @Test
    public void Test_1_followingActivity(){
        //add new habit first
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        intended(hasComponent(FollowingActivity.class.getName()));
    }

    @Test
    public void Test_2_searchFollowingActivity(){
        //add new habit first
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.searchButton)).perform(click());
        intended(hasComponent(FollowingActivity.class.getName()));
    }
    @Test
    public void Test_3_FollowReqActivity(){
        //add new habit first
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.followingReq)).perform(click());
        intended(hasComponent(FollowRequests.class.getName()));
    }
    @Test
    public void Test_4_searchCorrectAndSendFollowReqActivity(){
        //signing out and then signing in with otherUser
        //DatabaseManager.getFollowing(existingFollowers, CustomListOfExistingFollowersAdapter);
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmailOtherUser));
        onView(withId(R.id.password_login)).perform(replaceText(testPasswordOtherUser));
        SystemClock.sleep(2000);
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(10000);
        intending(hasComponent(MainActivity.class.getName()));
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(testUsername));

        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.sendRequestButton)).perform(click());

        intended(hasComponent(FollowingActivity.class.getName()));
    }
    @Test
    public void Test_5_FollowReqFunction(){
        //other user send follow req
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmailOtherUser));
        onView(withId(R.id.password_login)).perform(replaceText(testPasswordOtherUser));
        SystemClock.sleep(2000);
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(testUsername));
        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.sendRequestButton)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());


        //logout and log in
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);

        //check
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.followingReq)).perform(click());
        onView(withId(R.id.requestedFollowerName)).check(matches(withText(testUsernameOtherUser)));
    }
    @Test
    public void Test_6_FollowersFunction(){
        //other user send follow req
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmailOtherUser));
        onView(withId(R.id.password_login)).perform(replaceText(testPasswordOtherUser));
        SystemClock.sleep(2000);
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(testUsername));
        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.sendRequestButton)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());


        //logout and log in
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);

        //accept
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.followingReq)).perform(click());
        onView(withId(R.id.acceptFollowRequestButton)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());

        //check
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        //onView(withId(R.id.deleteExistingFollowerButton)).perform(click());
        //onView(withId(R.id.requestedFollowerName)).check(doesNotExist());
        onView(withId(R.id.existingFollowerName)).check(matches(withText(testUsernameOtherUser)));
        onView(withId(R.id.deleteExistingFollowerButton)).perform(click());
        onView(withId(R.id.requestedFollowerName)).check(doesNotExist());
    }
    @Test
    public void Test_7_deleteFollowerFunction(){
        //other user send follow req
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmailOtherUser));
        onView(withId(R.id.password_login)).perform(replaceText(testPasswordOtherUser));
        SystemClock.sleep(2000);
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(testUsername));
        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.sendRequestButton)).perform(scrollTo(),click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());


        //logout and log in
        onView(withId(R.id.logoutCard)).perform(scrollTo(),click());
        onView(withId(R.id.email_login)).perform(replaceText(testEmail));
        onView(withId(R.id.password_login)).perform(replaceText(testPassword));
        SystemClock.sleep(2000);
        onView(withId(R.id.LoginBTN)).perform(click());
        SystemClock.sleep(5000);

        //accept
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        SystemClock.sleep(2000);
        onView(withId(R.id.followingReq)).perform(click());
        onView(withId(R.id.acceptFollowRequestButton)).perform(scrollTo(),click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());


        //delete
        onView(withId(R.id.followingCard)).perform(scrollTo(),click());
        onView(withId(R.id.deleteExistingFollowerButton)).perform(scrollTo(),click());
        onView(withId(R.id.requestedFollowerName)).check(doesNotExist());

    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
