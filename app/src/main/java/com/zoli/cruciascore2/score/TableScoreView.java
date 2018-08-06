package com.zoli.cruciascore2.score;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zoli.cruciascore2.R;

import java.util.ArrayList;

public class TableScoreView extends AppCompatActivity {

    private ScoreAdapter scoreAdapter;
    private ArrayList<ListViewItem> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scoreList = new ArrayList<>();

        int mode = getIntent().getIntExtra("mode", 0);


        scoreAdapter = new ScoreAdapter(scoreList, mode);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.score_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(scoreAdapter);



        if (mode == 0) {
            scoreList.add(new ListViewItem("","Player1", "Player2", "Double Round", "Type", mode));
            scoreList.add(new ListViewItem("1.","1", "0", "2", "2", mode));
            scoreList.add(new ListViewItem("2.","1", "0", "2", "1", mode));
            scoreList.add(new ListViewItem("3.","1", "0", "2", "2", mode));
            scoreList.add(new ListViewItem("4.","1", "0", "2", "3", mode));
            updateRecycleView();
        } else {
            scoreList.add(new ListViewItem("", "Player1", "Player2", "Player3", "Type", mode));
            scoreList.add(new ListViewItem("1.","1", "-1", "2", "2", mode));
            scoreList.add(new ListViewItem("2.","1", "-1", "2", "2", mode));
            scoreList.add(new ListViewItem("3.","1", "-1", "2", "2", mode));
            scoreList.add(new ListViewItem("4.","1", "-1", "2", "2", mode));
            updateRecycleView();
        }

        incRoundTimes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_difficulty_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Clicked Settings in table", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void incRoundTimes() {
        ListViewItem listViewItem = scoreList.get(scoreList.size() - 1);
        listViewItem.setRoundTimes("10");
    }

    private void updateRecycleView() {
        scoreAdapter.setScoreList(scoreList);
    }
}
