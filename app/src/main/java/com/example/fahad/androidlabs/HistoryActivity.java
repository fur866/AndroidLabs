package com.example.fahad.androidlabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
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
        listView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view)
            {
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                imageView.setImageBitmap(null);
            }
        });
        HistoryAdapter historyAdapter = new HistoryAdapter(this,questionResponseModel);

        listView.setAdapter(historyAdapter);

    }
}
