package com.zoli.cruciascore2.score.decorators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zoli.cruciascore2.R;

public class FirstColumnDivider extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private int firstChildId = -1;

    public FirstColumnDivider(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.score_line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildCount() > 0) {
            View child = parent.getChildAt(parent.getChildCount() - 1);
            View child0 = parent.getChildAt(0);
            if(firstChildId == -1) {
                firstChildId = child0.getBottom();
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = firstChildId + 2 * params.bottomMargin + 8;
            int right = left + divider.getIntrinsicHeight();
            int top = 0;
            int bottom = child.getBottom() + params.bottomMargin;

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}
