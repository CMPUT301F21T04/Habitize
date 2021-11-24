package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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
        ArrayList<String> searchResultsDataList = new ArrayList<>();

        Intent intent = getIntent();
        String userSearchQuery = intent.getStringExtra(FollowingActivity.USER_INPUT_SEARCH);
        System.out.println("USER INPUT FOR SEARCH : " + userSearchQuery);

        System.out.println("DATA IN SEARCH RESULTS LIST : " + searchResultsDataList);


        CustomListOfSearch = new CustomListOfSearchResults(SearchResults.this, searchResultsDataList);
        listView.setAdapter(CustomListOfSearch);
        DatabaseManager.getMatchingUsers(userSearchQuery, searchResultsDataList,CustomListOfSearch);


    }
}