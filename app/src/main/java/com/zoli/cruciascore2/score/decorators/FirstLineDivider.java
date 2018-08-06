package com.zoli.cruciascore2.score.decorators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zoli.cruciascore2.R;

public class FirstLineDivider extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private long firstChildId = -1;

    public FirstLineDivider(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.score_line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + 8;
        int right = parent.getWidth() - parent.getPaddingRight() - 8;

        if(parent.getChildCount() > 0) {
            View child = parent.getChildAt(0);
            if(firstChildId == -1) {
                firstChildId = parent.getChildAdapterPosition(child);
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            if(firstChildId == parent.getChildAdapterPosition(child)) {
                divider.draw(c);
            }
        }
    }
}
