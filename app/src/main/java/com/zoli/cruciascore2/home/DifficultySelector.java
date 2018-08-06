package com.zoli.cruciascore2.home;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zoli.cruciascore2.R;
import com.zoli.cruciascore2.score.TableScoreView;

public class DifficultySelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_difficulty_selector);
        setLightStatusBar();

        Intent intent = new Intent(this, TableScoreView.class);
        setButtonOnClickListeners(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void setButtonOnClickListeners(final Intent intent) {
        Button twoPlayer = (Button) findViewById(R.id.button_twoPlayer);
        twoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("mode", 0);
                startActivity(intent);
            }
        });

        Button threePlayer = (Button) findViewById(R.id.button_threePlayer);
        threePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("mode", 1);
                startActivity(intent);
            }
        });

        Button fourPlayer = (Button) findViewById(R.id.button_fourPlayer);
        fourPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("mode", 0);
                startActivity(intent);
            }
        });
    }

}
