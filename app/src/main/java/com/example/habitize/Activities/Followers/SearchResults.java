package com.example.habitize.Activities.Followers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.Controllers.CustomListOfSearchResults;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;

import java.util.ArrayList;

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