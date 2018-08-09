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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zoli.cruciascore2.R;
import com.zoli.cruciascore2.score.decorators.FirstColumnDivider;
import com.zoli.cruciascore2.score.decorators.GravityCompoundDrawable;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;

public class TableScoreView extends AppCompatActivity {

    private ScoreAdapter scoreAdapter;
    private ArrayList<ListViewItem> scoreList;
    private int rowCount = 1;
    private int mode, roundTimes = 2;
    private RecyclerView recyclerView;
    private final int TWO_PLAYER_MODE = 0;
    private Menu menu;
    private int WINNER_SCORE = 21;
    private boolean SHOULD_ANNOUNCE_WINNER = true;
    private boolean SHOWED_DRAW_ALERT = false;

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
        setUpLastRowForFirstUse(mode);
        updateRecycleView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.score_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog(mode);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showInputDialog(final int mode) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableScoreView.this);

        if (mode == TWO_PLAYER_MODE) {  // Two player mode
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.input_score_dialog_two_player, null);
            TextView textViewTitle = (TextView) view.findViewById(R.id.round_title);
            textViewTitle.setText("Round  " + rowCount);

            TextView textViewPlayer1Name = (TextView) view.findViewById(R.id.player1_score);
            textViewPlayer1Name.setText(getPlayer1Name());

            TextView textViewPlayer2Name = (TextView) view.findViewById(R.id.player2_score);
            textViewPlayer2Name.setText(getPlayer2Name());

            alertDialogBuilder.setView(view);
            final AlertDialog alert = alertDialogBuilder.show();

            ArrayList<Integer> entries = new ArrayList<>();
            for (int i = -5; i < 7; i++) {
                entries.add(i);
            }

            final Spinner spinnerP1 = (Spinner) view.findViewById(R.id.spinner_player1);
            final Spinner spinnerP2 = (Spinner) view.findViewById(R.id.spinner_player2);
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, entries);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            spinnerP1.setAdapter(adapter);
            spinnerP1.setSelection(5);

            spinnerP2.setAdapter(adapter);
            spinnerP2.setSelection(5);

            Button buttonAdd = (Button) view.findViewById(R.id.finish_button);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scoreList.get(scoreList.size() - 1).setScoreP1(spinnerP1.getSelectedItem().toString());
                    scoreList.get(scoreList.size() - 1).setScoreP2(spinnerP2.getSelectedItem().toString());
                    scoreList.get(scoreList.size() - 1).setScoreP3("0");
                    recyclerView.scrollToPosition(scoreList.size() - 1);
                    rowCount++;
                    setUpLastRow(mode);
                    setUpDoubleRoundIndicator();
                    scoreList.add(new ListViewItem(Integer.toString(rowCount) + ". ", "", "", "", "", mode));
                    recyclerView.scrollToPosition(scoreList.size() - 1);
                    updateRecycleView();
                    alert.dismiss();
                }
            });

        } else {    // Three player mode
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.input_score_dialog_three_player, null);

            TextView textViewTitle = (TextView) view.findViewById(R.id.round_title);
            textViewTitle.setText("Round  " + rowCount);

            TextView textViewPlayer1Name = (TextView) view.findViewById(R.id.player1_score);
            textViewPlayer1Name.setText(getPlayer1Name());

            TextView textViewPlayer2Name = (TextView) view.findViewById(R.id.player2_score);
            textViewPlayer2Name.setText(getPlayer2Name());

            TextView textViewPlayer3Name = (TextView) view.findViewById(R.id.player3_score);
            textViewPlayer3Name.setText(getPlayer3Name());

            alertDialogBuilder.setView(view);
            final AlertDialog alert = alertDialogBuilder.show();

            ArrayList<Integer> entries = new ArrayList<>();
            for (int i = -5; i < 7; i++) {
                entries.add(i);
            }

            final Spinner spinnerP1 = (Spinner) view.findViewById(R.id.spinner_player1);
            final Spinner spinnerP2 = (Spinner) view.findViewById(R.id.spinner_player2);
            final Spinner spinnerP3 = (Spinner) view.findViewById(R.id.spinner_player3);
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, entries);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            spinnerP1.setAdapter(adapter);
            spinnerP1.setSelection(5);

            spinnerP2.setAdapter(adapter);
            spinnerP2.setSelection(5);

            spinnerP3.setAdapter(adapter);
            spinnerP3.setSelection(5);

            Button buttonAdd = (Button) view.findViewById(R.id.finish_button);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scoreList.get(scoreList.size() - 1).setScoreP1(spinnerP1.getSelectedItem().toString());
                    scoreList.get(scoreList.size() - 1).setScoreP2(spinnerP2.getSelectedItem().toString());
                    scoreList.get(scoreList.size() - 1).setScoreP3(spinnerP3.getSelectedItem().toString());
                    recyclerView.scrollToPosition(scoreList.size() - 1);
                    rowCount++;
                    setUpLastRow(mode);
                    setUpDoubleRoundIndicator();
                    scoreList.add(new ListViewItem(Integer.toString(rowCount) + ". ", "", "", "", "", mode));
                    recyclerView.scrollToPosition(scoreList.size() - 1);
                    updateRecycleView();
                    alert.dismiss();
                }
            });
        }

    }

    private String getPlayer1Name() {
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.score_list_view_header);

        TextView textView = (TextView) headerLayout.findViewById(R.id.score_player1);

        return (String.valueOf(textView.getText())).isEmpty() ? String.valueOf(textView.getHint()) : String.valueOf(textView.getText());
    }

    private String getPlayer2Name() {
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.score_list_view_header);

        TextView textView = (TextView) headerLayout.findViewById(R.id.score_player2);
        return (String.valueOf(textView.getText())).isEmpty() ? String.valueOf(textView.getHint()) : String.valueOf(textView.getText());
    }

    private String getPlayer3Name() {
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.score_list_view_header);

        TextView textView = (TextView) headerLayout.findViewById(R.id.score_player3);
        return (String.valueOf(textView.getText())).isEmpty() ? String.valueOf(textView.getHint()) : String.valueOf(textView.getText());
    }

    private void setUpDoubleRoundIndicator() { // After the player starts a new round the indicator should show X2
        roundTimes = 2;
        MenuItem menuItem = menu.findItem(R.id.action_double);
        menuItem.setTitle("Round X" + roundTimes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_difficulty_selector, menu);
        this.menu = menu;
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
                showResetAlert();
                return true;
            }

            case R.id.action_double: {
                scoreList.get(scoreList.size() - 1).setRoundTimes("X" + String.valueOf(roundTimes));
                updateRecycleView();
                roundTimes++;
                if (roundTimes == 5) {
                    roundTimes = 1;
                }
                item.setTitle("Round X" + roundTimes);

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showResetAlert() {
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
    }

    private void resetGame() {
        rowCount = 1;
        scoreList.clear();
        scoreList.add(new ListViewItem(Integer.toString(rowCount) + ". ", "", "", "", "", mode));
        updateRecycleView();
        setUpLastRow(mode);
        roundTimes = 2;
        SHOULD_ANNOUNCE_WINNER = true;
        SHOWED_DRAW_ALERT = false;
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.score_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(scoreAdapter);
        recyclerView.addItemDecoration(new FirstColumnDivider(getBaseContext()));
        recyclerView.setFocusable(false);
        recyclerView.setFocusableInTouchMode(false);
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void setUpFirstRow(int mode) {
        View row;

        if (mode == TWO_PLAYER_MODE) {
            row = getLayoutInflater().inflate(R.layout.two_player_row, null);

            TextView textViewType = (TextView) row.findViewById(R.id.double_round_indicator);
            textViewType.setText("Type");
            textViewType.setCompoundDrawables(null, null, null, null);

            EditText editTextPlayer1 = (EditText) row.findViewById(R.id.score_player1);
            editTextPlayer1.setHint("Entity1");
            editTextPlayer1.setSelectAllOnFocus(true);
            setTopLeftIcon(editTextPlayer1);
            setOnclickAndOnFocusListener(editTextPlayer1, textViewType);

            EditText editTextPlayer2 = (EditText) row.findViewById(R.id.score_player2);
            editTextPlayer2.setHint("Entity2");
            editTextPlayer2.setSelectAllOnFocus(true);
            setTopLeftIcon(editTextPlayer2);
            setOnclickAndOnFocusListener(editTextPlayer2, textViewType);

            keyboardListener(textViewType);

        } else {
            row = getLayoutInflater().inflate(R.layout.three_player_row, null);

            TextView textViewType = (TextView) row.findViewById(R.id.double_round_indicator);
            textViewType.setText("Type");

            EditText editTextPlayer1 = (EditText) row.findViewById(R.id.score_player1);
            editTextPlayer1.setHint("Entity1");
            editTextPlayer1.setSelectAllOnFocus(true);
            setTopLeftIcon(editTextPlayer1);
            setOnclickAndOnFocusListener(editTextPlayer1, textViewType);

            EditText editTextPlayer2 = (EditText) row.findViewById(R.id.score_player2);
            editTextPlayer2.setHint("Entity2");
            editTextPlayer2.setSelectAllOnFocus(true);
            setTopLeftIcon(editTextPlayer2);
            setOnclickAndOnFocusListener(editTextPlayer2, textViewType);

            EditText editTextPlayer3 = (EditText) row.findViewById(R.id.score_player3);
            editTextPlayer3.setHint("Entity3");
            editTextPlayer3.setSelectAllOnFocus(true);
            setTopLeftIcon(editTextPlayer3);
            setOnclickAndOnFocusListener(editTextPlayer3, textViewType);

            keyboardListener(textViewType);
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.score_list_view_header);
        layout.addView(row, 0);

        // set up first score row
        scoreList.add(new ListViewItem(Integer.toString(rowCount) + ". ", "", "", "", "", mode));
    }

    @SuppressLint("InflateParams")
    private void setUpLastRowForFirstUse(int mode) {    //Sets the sum and 0 scores for the players
        View row;

        if (mode == TWO_PLAYER_MODE) {
            row = getLayoutInflater().inflate(R.layout.two_player_row, null);

            TextView textViewType = (TextView) row.findViewById(R.id.double_round_indicator);
            textViewType.setText("");

            TextView textViewRoundNumber = (TextView) row.findViewById(R.id.round_number);
            textViewRoundNumber.setText(R.string.sum_score_text);

            EditText editTextPlayer1 = (EditText) row.findViewById(R.id.score_player1);
            editTextPlayer1.setText("0");

            EditText editTextPlayer2 = (EditText) row.findViewById(R.id.score_player2);
            editTextPlayer2.setText("0");

        } else {
            row = getLayoutInflater().inflate(R.layout.three_player_row, null);

            TextView textViewType = (TextView) row.findViewById(R.id.double_round_indicator);
            textViewType.setText("");

            TextView textViewRoundNumber = (TextView) row.findViewById(R.id.round_number);
            textViewRoundNumber.setText(R.string.sum_score_text);

            EditText editTextPlayer1 = (EditText) row.findViewById(R.id.score_player1);
            editTextPlayer1.setText("0");

            EditText editTextPlayer2 = (EditText) row.findViewById(R.id.score_player2);
            editTextPlayer2.setText("0");

            EditText editTextPlayer3 = (EditText) row.findViewById(R.id.score_player3);
            editTextPlayer3.setText("0");
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.score_list_sum);
        layout.addView(row, 1);
    }

    private void setUpLastRow(int mode) {   // Calculates the score for the players and updates the textView
        int sumP1 = 0, sumP2 = 0, sumP3 = 0;

        for (ListViewItem listViewItem : scoreList) {
            if (!listViewItem.getRoundTimes().isEmpty()) {
                if (listViewItem.getScoreP1().isEmpty()) {
                    sumP1 += 0;
                } else {
                    sumP1 += Integer.valueOf(listViewItem.getScoreP1()) * Integer.valueOf(listViewItem.getRoundTimes().substring(1));
                }

                if (listViewItem.getScoreP2().isEmpty()) {
                    sumP2 += 0;
                } else {
                    sumP2 += Integer.valueOf(listViewItem.getScoreP2()) * Integer.valueOf(listViewItem.getRoundTimes().substring(1));
                }

                if (listViewItem.getScoreP3().isEmpty()) {
                    sumP3 += 0;
                } else {
                    sumP3 += Integer.valueOf(listViewItem.getScoreP3()) * Integer.valueOf(listViewItem.getRoundTimes().substring(1));
                }
            } else {
                if (listViewItem.getScoreP1().isEmpty()) {
                    sumP1 += 0;
                } else {
                    sumP1 += Integer.valueOf(listViewItem.getScoreP1());
                }

                if (listViewItem.getScoreP2().isEmpty()) {
                    sumP2 += 0;
                } else {
                    sumP2 += Integer.valueOf(listViewItem.getScoreP2());
                }

                if (listViewItem.getScoreP3().isEmpty()) {
                    sumP3 += 0;
                } else {
                    sumP3 += Integer.valueOf(listViewItem.getScoreP3());
                }
            }
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.score_list_sum);

        TextView textViewPlayer1 = (TextView) layout.findViewById(R.id.score_player1);
        textViewPlayer1.setText(String.valueOf(sumP1));

        TextView textViewPlayer2 = (TextView) layout.findViewById(R.id.score_player2);
        textViewPlayer2.setText(String.valueOf(sumP2));

        if (mode != TWO_PLAYER_MODE) {
            TextView textViewPlayer3 = (TextView) layout.findViewById(R.id.score_player3);
            textViewPlayer3.setText(String.valueOf(sumP3));

            int winner = maxScore(sumP1, sumP2, sumP3);

            if (((sumP1 == sumP2 && sumP1 > WINNER_SCORE) || (sumP1 == sumP3 && sumP1 > WINNER_SCORE) || (sumP3 == sumP2 && sumP2 > WINNER_SCORE)) && !SHOWED_DRAW_ALERT) {
                SHOWED_DRAW_ALERT = true;
                drawAlert();
            } else if (winner >= WINNER_SCORE && SHOULD_ANNOUNCE_WINNER && differenceIsGood(sumP1, sumP2, sumP3, winner)) {
                LinearLayout headerLayout = (LinearLayout) findViewById(R.id.score_list_view_header);

                if (winner == sumP1) {
                    winnerAlert(getPlayer1Name());
                } else if (winner == sumP2) {
                    winnerAlert(getPlayer2Name());
                } else {
                    winnerAlert(getPlayer3Name());
                }

            }
        } else { // TWO_PLAYER
            int winner = sumP1 > sumP2 ? sumP1 : sumP2;

            if (sumP1 == sumP2 && sumP1 > WINNER_SCORE && !SHOWED_DRAW_ALERT) {
                SHOWED_DRAW_ALERT = true;
                drawAlert();
            } else if ((winner > WINNER_SCORE) && SHOULD_ANNOUNCE_WINNER && (sumP1 != sumP2)) {
                LinearLayout headerLayout = (LinearLayout) findViewById(R.id.score_list_view_header);

                if (winner == sumP1) {
                    winnerAlert(getPlayer1Name());
                } else {
                    winnerAlert(getPlayer2Name());
                }
            }
        }
    }

    private boolean differenceIsGood(int score1, int score2, int score3, int max) { // After there was a draw the winner will only be announced if he has the highest score
        int counter = 0;                                                            // We will count how many numbers are equal to the max score
        if (max == score1) counter++;
        if (max == score2) counter++;
        if (max == score3) counter++;
        return SHOWED_DRAW_ALERT && (counter == 1);                                 // If we showed the draw alert and there is only one highest number, then we have a winner
    }

    private void drawAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.ResetAlertDialog));
        alertDialogBuilder.setTitle("Draw");
        alertDialogBuilder.setMessage("To determine the winner 1 point difference is needed!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuilder.show();
    }

    private void winnerAlert(String name) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.ResetAlertDialog));
        alertDialogBuilder.setTitle("Winner");
        alertDialogBuilder.setMessage("The winner is - " + name);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("NEW GAME", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        alertDialogBuilder.setNegativeButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SHOULD_ANNOUNCE_WINNER = false;
            }
        });
        alertDialogBuilder.show();
    }

    private int maxScore(int score1, int score2, int score3) {
        int max = Math.max(score1, score2);
        if (max > score2) { //suppose Player1 is max then compare Player1 with Player3 to find winner
            max = Math.max(score1, score3);
        } else { //if y is max then compare y with z to find max number
            max = Math.max(score2, score3);
        }

        return max;
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
                    Log.d("ASD", "no");
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
