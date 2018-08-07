package com.zoli.cruciascore2.score.decorators;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class GravityCompoundDrawable extends Drawable {

    private final Drawable mDrawable;

    public GravityCompoundDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int halfCanvas= canvas.getHeight() / 2;
        int halfDrawable = mDrawable.getIntrinsicHeight() / 2;

        canvas.save();
        canvas.translate(0, -halfCanvas + halfDrawable);
        mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public void setAlpha(int i) {
        mDrawable.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mDrawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return mDrawable.getOpacity();
    }
}
