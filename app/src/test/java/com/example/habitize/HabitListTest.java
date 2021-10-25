package com.example.habitize;

import static org.junit.Assert.assertEquals;

import androidx.core.widget.TextViewCompat;

import com.example.habitize.Habit;
import com.example.habitize.HabitList;

import org.junit.Test;

public class HabitListTest {
    private HabitList testHabitList(){
        HabitList habitlist = new HabitList();
        return habitlist;
    }
    private Habit testHabit(){
        Habit habit = new Habit();
        return habit;
    }

    @Test
    public void addHabitTest(){
        HabitList myList = testHabitList();
        Habit myHabit = testHabit();

        myList.addHabit(myHabit);
        assertEquals(true,myList.contains(myHabit));
    }
    @Test
    public void deleteHabitTest(){
        HabitList myList = testHabitList();
        Habit myHabit = testHabit();

        myList.addHabit(myHabit);
        assertEquals(true,myList.contains(myHabit));
        myList.deleteHabit(myHabit);
        assertEquals(false,myList.contains(myHabit));

    }





}
