package com.example.habitize;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewRecordsFragment extends Fragment {

    private ArrayList<Record> records;
    private String user;
    private ListView list;
    private RecordAdapter recordAdapter;

    public ViewRecordsFragment(){

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_view_records,container,false);
        records = new ArrayList<>();
        records.add(new Record("2002-02-01","yo"));
        records.add(new Record("2002-02-01","yo"));
        records.add(new Record("2002-02-01","yo"));
        records.add(new Record("2002-02-01","yo"));
        records.add(new Record("2002-02-01","yo"));
        records.add(new Record("2002-02-01","yo"));


        list = root.findViewById(R.id.record_list);
        recordAdapter = new RecordAdapter(getActivity(),R.layout.record_list_content,records);
        list.setAdapter(recordAdapter);

        return root;

    }







}
