package com.example.habitize.Activities.ViewHabit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.habitize.Activities.ViewRecord.ViewRecordBase;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.Controllers.RecordAdapter;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;

import java.util.ArrayList;

public class ViewRecordsFragment extends Fragment implements RecordAdapter.recordViewer {

    private ArrayList<Record> records;
    private String user;
    private ListView list;
    private RecordAdapter recordAdapter;
    private Habit habit;
    private ArrayList<Habit> habits;

    View root;

    public ViewRecordsFragment(Habit habit,ArrayList<Habit> habits){
        this.habit = habit;
        this.habits = habits;
    }

    public ViewRecordsFragment(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.fragment_view_records,container,false);
        records = new ArrayList<>();

        list = root.findViewById(R.id.record_list);
        recordAdapter = new RecordAdapter(this, R.layout.record_list_content, records);
        list.setAdapter(recordAdapter);
        DatabaseManager.getRecord(habit.getRecordAddress(), records, recordAdapter);
        return root;

    }

    /**
     * Updates the record adapter. */
    @Override
    public void onResume() {
        super.onResume();
        records.clear();
        recordAdapter.notifyDataSetChanged();
        DatabaseManager.getRecord(habit.getRecordAddress(), records, recordAdapter);
    }

    /** Allows user to view the record of their habti using bundle. */
    @Override
    public void viewRecord(int position) {
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit", habit); // pass down the habit at the position
        habitBundle.putSerializable("habits", habits);
        habitBundle.putSerializable("record", records.get(position));
        habitBundle.putSerializable("index", position);
        habitBundle.putSerializable("records", records);
        Intent intent = new Intent(getContext(), ViewRecordBase.class);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }
}
