package com.example.habitize;
import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUp> rule = new ActivityScenarioRule<SignUp>(SignUp.class);

    String testFirstName = "Rick";
    String testLastName = "Grimes";
    String testEmail = "rick0grimes301@gmail.com";
    String testUsername = "Rick";
    String testPassword = "12345678";
    String testConPassword = "12345678";

    @BeforeClass
    public static void checkCorrectInput(){
        
    }
    


}



