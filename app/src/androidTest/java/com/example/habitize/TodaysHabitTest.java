//package com.example.habitize;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//import android.os.SystemClock;
//
//import androidx.test.espresso.intent.Intents;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(AndroidJUnit4.class)
//public class TodaysHabitTest {
//    String email;
//    String password;
//
//    @Rule
//    public ActivityScenarioRule<Login_Activity> activityRule = new ActivityScenarioRule<Login_Activity>(Login_Activity.class);
//
//    @Before
//    public void getToScreen(){
//        Intents.init();
//        email = "rasoulpo@ualberta.ca";
//        password = "password";
//        onView(withId(R.id.email_login)).perform(replaceText(email));
//        onView(withId(R.id.password_login)).perform(replaceText(password));
//        onView(withId(R.id.LoginBTN)).perform(click());
//        SystemClock.sleep(5000);
//    }
//
//    @After
//    public void tearDown() {
//        Intents.release();
//    }
//
//    @Test
//    public void recurrenceTest(){
////        onView(withId(R.id.email_login)).perform(replaceText(email));
////        onView(withId(R.id.password_login)).perform(replaceText(password));
////        onView(withId(R.id.LoginBTN)).perform(click());
////        SystemClock.sleep(5000);
//        intended(hasComponent(MainActivity.class.getName()));
//        //onView(withId(R.id.todaysHabits)).perform(click());
//
//    }
//
//}
