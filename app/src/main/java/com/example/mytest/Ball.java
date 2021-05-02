package com.example.mytest;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Ball extends Drawable {

    private Paint paint;
    private float mx;
    private float my;
    private float mradius;

    public Ball(float x, float y, float radius, int colorFlag){
        mx = x;
        my = y;
        mradius = radius;
        paint = new Paint();
        paint.setColor(colorFlag);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(mx,my,mradius,paint);

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
