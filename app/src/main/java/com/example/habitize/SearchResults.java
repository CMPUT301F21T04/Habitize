package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {
    private CustomListOfSearchResults CustomListOfSearch;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        listView = findViewById(R.id.listOfSearchResults);
        List<String> searchResultsDataList = new ArrayList<>();
        searchResultsDataList.add("Kamillah");
        searchResultsDataList.add("Shane");
        searchResultsDataList.add("Stan");

        

        CustomListOfSearch = new CustomListOfSearchResults(SearchResults.this, searchResultsDataList);
        listView.setAdapter(CustomListOfSearch);
    }
}