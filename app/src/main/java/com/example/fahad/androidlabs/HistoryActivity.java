package com.example.fahad.androidlabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Fahad on 16/04/2016.
 */
public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_history);

        ArrayList<QuestionResponseModel> questionResponseModel = (ArrayList<QuestionResponseModel>)getIntent().getSerializableExtra("QuestionResponseArray");

        ListView listView = (ListView) findViewById(R.id.listView);
        HistoryAdapter historyAdapter = new HistoryAdapter(this,questionResponseModel);

        listView.setAdapter(historyAdapter);

    }
}
