package com.zoli.cruciascore2.score;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoli.cruciascore2.R;
import com.zoli.cruciascore2.score.decorators.FirstColumnDivider;
import com.zoli.cruciascore2.score.decorators.GravityCompoundDrawable;
import com.zoli.cruciascore2.score.decorators.LastLineDivider;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;

public class TableScoreView extends AppCompatActivity {

    private ScoreAdapter scoreAdapter;
    private ArrayList<ListViewItem> scoreList;
    private int rowCount = 1;
    private int mode, roundTimes = 2;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mode = getIntent().getIntExtra("mode", 0);

        scoreList = new ArrayList<>();
        scoreAdapter = new ScoreAdapter(scoreList, mode);

        setUpRecyclerView();
        setUpFirstRow(mode);
        updateRecycleView();
        setUpLastRow(mode);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.score_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (scoreList.size() > 0) {
                    scoreList.remove(scoreList.size() - 1);
                }
                scoreList.add(new ListViewItem(Integer.toString(rowCount) + ". ", "1", "2", "3", "", mode));
                setUpLastRow(mode);
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

        switch (id) {
            case R.id.action_settings: {
                Toast.makeText(this, "Clicked Settings", Toast.LENGTH_LONG).show();
                return true;
            }

            case R.id.action_reset: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.ResetAlertDialog));
                alertDialogBuilder.setTitle("Reset");
                alertDialogBuilder.setMessage("Reset the Game?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       resetGame();
                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing to do here
                    }
                });
                alertDialogBuilder.show();

                return true;
            }

            case R.id.action_double: {
                if (scoreList.size() > 1) {
                    View view = recyclerView.getLayoutManager().findViewByPosition(scoreList.size() - 2);
                    scoreList.get(scoreList.size() - 2).setRoundTimes(String.valueOf(roundTimes));
                    TextView typeTextView = (TextView) view.findViewById(R.id.double_round_indicator);
                    typeTextView.setText(String.valueOf(roundTimes));
                    roundTimes++;
                } else {
                    Toast.makeText(this, "Not enough rounds played", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetGame() {
        rowCount = 1;
        scoreList.clear();
        setUpLastRow(mode);
        roundTimes = 2;
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.score_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(scoreAdapter);
        recyclerView.addItemDecoration(new FirstColumnDivider(getBaseContext()));
        recyclerView.addItemDecoration(new LastLineDivider(getBaseContext()));
        recyclerView.setFocusable(false);
        recyclerView.setFocusableInTouchMode(false);
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void setUpFirstRow(int mode) {
        View row;

        if (mode == 0) {
            row = getLayoutInflater().inflate(R.layout.two_player_row, null);

            TextView textViewType = (TextView) row.findViewById(R.id.double_round_indicator);
            textViewType.setText("Type");

            EditText editTextPlayer1 = (EditText) row.findViewById(R.id.score_player1);
            editTextPlayer1.setText("Player1");
            setTopLeftIcon(editTextPlayer1);
            setOnclickAndOnFocusListener(editTextPlayer1, textViewType);

            EditText editTextPlayer2 = (EditText) row.findViewById(R.id.score_player2);
            editTextPlayer2.setText("Player2");
            setTopLeftIcon(editTextPlayer2);
            setOnclickAndOnFocusListener(editTextPlayer2, textViewType);


            keyboardListener(textViewType);

        } else {
            row = getLayoutInflater().inflate(R.layout.three_player_row, null);

            TextView textViewType = (TextView) row.findViewById(R.id.double_round_indicator);
            textViewType.setText("Type");

            EditText editTextPlayer1 = (EditText) row.findViewById(R.id.score_player1);
            editTextPlayer1.setText("Player1");
            setTopLeftIcon(editTextPlayer1);
            setOnclickAndOnFocusListener(editTextPlayer1, textViewType);

            EditText editTextPlayer2 = (EditText) row.findViewById(R.id.score_player2);
            editTextPlayer2.setText("Player2");
            setTopLeftIcon(editTextPlayer2);
            setOnclickAndOnFocusListener(editTextPlayer2, textViewType);

            EditText editTextPlayer3 = (EditText) row.findViewById(R.id.score_player3);
            editTextPlayer3.setText("Player3");
            setTopLeftIcon(editTextPlayer3);
            setOnclickAndOnFocusListener(editTextPlayer3, textViewType);


            keyboardListener(textViewType);
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.score_list_view_header);
        layout.addView(row, 0);
    }

    private void setUpLastRow(int mode) {
        int sumP1 = 0, sumP2 = 0, sumP3 = 0;

        for (ListViewItem listViewItem : scoreList) {
            sumP1 += Integer.valueOf(listViewItem.getScoreP1());
            sumP2 += Integer.valueOf(listViewItem.getScoreP2());
            sumP3 += Integer.valueOf(listViewItem.getScoreP3());
        }
        scoreList.add(new ListViewItem("Sum", String.valueOf(sumP1), String.valueOf(sumP2), String.valueOf(sumP3), "", mode));
        updateRecycleView();
    }

    private void setTopLeftIcon(TextView textView) {
        Drawable innerDrawable = getBaseContext().getResources().getDrawable(R.drawable.ic_baseline_create_14px);

        GravityCompoundDrawable gravityDrawable = new GravityCompoundDrawable(innerDrawable);
        innerDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());
        gravityDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());

        textView.setCompoundDrawables(gravityDrawable, null, null, null);
    }

    private void setOnclickAndOnFocusListener(final EditText editText, final TextView textView) {
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (!hasFocus) {
                    if (inputMethodManager != null) {
                        editText.clearFocus();
                        editText.setText(editText.getText());
                        editText.setSelection(0);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                } else {
                    if (inputMethodManager != null) {
                        editText.setSelection(0);
                        textView.requestFocus();
                        textView.performClick();
                        textView.performLongClick();
                        inputMethodManager.showSoftInput(v, 0);
                    }
                }
            }
        });
    }

    private void updateRecycleView() {
        scoreAdapter.setScoreList(scoreList);
    }

    private void keyboardListener(final TextView textView) {
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (!isOpen) {
                    Log.d("ASD","no");
                    textView.requestFocus();
                    textView.performClick();
                    textView.performLongClick();
                    onClick(textView);

                }
            }
        });
    }

    public void onClick(View view) { }
}
