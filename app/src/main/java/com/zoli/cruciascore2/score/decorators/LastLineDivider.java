package com.zoli.cruciascore2.score.decorators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zoli.cruciascore2.R;

public class LastLineDivider extends RecyclerView.ItemDecoration {

    private Drawable divider;

    public LastLineDivider(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.score_line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + 8;
        int right = parent.getWidth() - parent.getPaddingRight() - 8;

        if (parent.getChildCount() > 0) {
            View child = parent.getChildAt(parent.getChildCount() - 1);
            TextView textView = (TextView) child.findViewById(R.id.round_number);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getTop() + params.topMargin;
            int bottom = top + divider.getIntrinsicHeight();

            Log.d("ASD",textView.getText()+"");
            divider.setBounds(left, top, right, bottom);
            if(textView.getText().equals("Sum")) {
                divider.draw(c);
            }
        }
    }
}