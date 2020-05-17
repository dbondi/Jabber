package com.example.jab;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.mapboxsdk.Mapbox;

import java.util.ArrayList;

import adapaters.MapTabAdapter;
import adapaters.SearchAdapter;
import adapaters.SearchColumnAdapter;
import controllers.SearchController;
import custom_class.MapTab;
import custom_class.SearchColumn;
import custom_class.SearchTab;
import models.SearchModel;

public class SearchActivity extends AppCompatActivity {
    private SearchController controller;
    private SearchModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView searchView;
    private SearchAdapter searchColumnAdapter;
    private static final String TAG = "SearchActivity";

    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_search);


        // Mapbox access token is configured here. This needs to be called either in your application

        // This contains the MapView in XML and needs to be called after the access token is configured.


        controller = new SearchController(auth, this);
        model = new SearchModel(auth);

        ArrayList<Object> columnTabs = new ArrayList<Object>();

        MapTab mTab = new MapTab();

        columnTabs.add(mTab);

        SearchColumn col1 = new SearchColumn();
        SearchColumn col2 = new SearchColumn();

        SearchTab ltab1 = new SearchTab();
        ltab1.setName("Left Tab 1");

        SearchTab ltab2 = new SearchTab();
        ltab2.setName("Left Tab 2");

        SearchTab rtab1 = new SearchTab();
        rtab1.setName("Right Tab 1");

        SearchTab rtab2 = new SearchTab();
        rtab2.setName("Right Tab 2");

        col1.setLeftTab(ltab1);
        col1.setRightTab(rtab1);

        col2.setLeftTab(ltab2);
        col2.setRightTab(rtab2);

        columnTabs.add(col1);
        columnTabs.add(col2);

        chatTabBtn = findViewById(R.id.chat_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        storiesTabBtn = findViewById(R.id.stories_tab);

        searchView = findViewById(R.id.searchRec);
        searchColumnAdapter = new SearchAdapter(columnTabs,saveInstanceState,this);
        searchView.setAdapter(searchColumnAdapter);
        searchView.setLayoutManager(new LinearLayoutManager(this));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        chatTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.chatBtn();
            }
        });

        storiesTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.storiesBtn();
            }
        });


    }



}
