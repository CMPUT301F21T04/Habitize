package com.example.habitize;
import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
public class SignUpTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<SignUp> rule = new ActivityTestRule<>(SignUp.class, true, true);
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Check whether activity correctly switched
     */
    @Test
    public void checkActivity() throws Exception {
        solo.clickOnButton("REGISTER"); //Click ADD CITY Button
        Activity activity = rule.getActivity();
        solo.assertCurrentActivity("Wrong",SignUp.class);
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();


        getAuth()
                .deleteUser(uid)
                .then(() => {
                console.log('Successfully deleted user');
            })
        .catch((error) => {
                console.log('Error deleting user:', error);
            });

    }
}

}

