package com.zoli.cruciascore2.score;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zoli.cruciascore2.R;
import com.zoli.cruciascore2.score.decorators.FirstColumnDivider;
import com.zoli.cruciascore2.score.decorators.FirstLineDivider;

import java.util.ArrayList;

public class TableScoreView extends AppCompatActivity {

    private ScoreAdapter scoreAdapter;
    private ArrayList<ListViewItem> scoreList;
    private int rowCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int mode = getIntent().getIntExtra("mode", 0);

        scoreList = new ArrayList<>();
        scoreAdapter = new ScoreAdapter(scoreList, mode);

       setUpRecyclerView();
       setUpFirstRow(mode);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.score_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                scoreList.add(new ListViewItem(Integer.toString(rowCount)+". ","1", "2", "3", "", mode));
                updateRecycleView();
                rowCount++;
            }
        });
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

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.score_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(scoreAdapter);
        recyclerView.addItemDecoration(new FirstLineDivider(getBaseContext()));
        recyclerView.addItemDecoration(new FirstColumnDivider(getBaseContext()));
    }

    private void setUpFirstRow(int mode) {
        if (mode == 0) {
            scoreList.add(new ListViewItem("","Player1", "Player2", "", "Type", mode));
            updateRecycleView();
        } else {
            scoreList.add(new ListViewItem("", "Player1", "Player2", "Player3", "Type", mode));
            updateRecycleView();
        }
    }

    private void incRoundTimes() {
        ListViewItem listViewItem = scoreList.get(scoreList.size() - 1);
        listViewItem.setRoundTimes("10");
    }

    private void updateRecycleView() {
        scoreAdapter.setScoreList(scoreList);
    }
}
