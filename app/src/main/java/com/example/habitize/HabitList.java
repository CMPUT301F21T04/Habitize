package com.example.habitize;

import java.util.ArrayList;

public class HabitList {
    private ArrayList<Habit> habits = new ArrayList<>();

    public void addHabit(Habit habit){
        if(habits.contains(habit)){
            throw new IllegalArgumentException();
        }
        habits.add(habit);
    }

    public void deleteHabit(Habit habit){
        if(!habits.contains(habit)){
            throw new IllegalArgumentException();
        }
        habits.remove(habit);
    }

    public boolean contains(Habit habit){
        return habits.contains(habit);
    }


}
