package com.example.mytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class BallView extends View {
    private Ball ball;
    private int mLayOutWidth;
    private int mLayOutHeight;
    private int mWidth;
    private int mHeight;
    private float radius;
    public float speedx;
    public float speedy;

    public BallView(Context context, int width, int height, int layOutWidth, int layOutHeight) {
        super(context);
        radius = Math.min(width,height)/2;
        mWidth = width;
        mHeight = height;
        ball = new Ball(this.getTranslationX()+radius,this.getTranslationY()+radius,radius, Color.RED);
        mLayOutWidth = layOutWidth;
        mLayOutHeight = layOutHeight;
        speedx = 0;
        speedy = 0;
    }

    public void moveStep(float x, float y){
        float newTransX = this.getTranslationX()-x;
        float newTransY = this.getTranslationY()+y;
        if (newTransX <= 0){
            this.setTranslationX(0);
            this.speedx = 0;
        }else{
            if (newTransX >= mLayOutWidth - radius*2){
                this.setTranslationX(mLayOutWidth - radius*2);
                this.speedx = 0;
            }else{
                this.setTranslationX(newTransX);
            }
        }

        if (newTransY <= 0){
            this.setTranslationY(0);
            this.speedy = 0;
        }else{
            if (newTransY >= mLayOutHeight - radius*2){
                this.setTranslationY(mLayOutHeight - radius*2);
                this.speedy = 0;
            }else{
                this.setTranslationY(newTransY);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ball.draw(canvas);
    }
}
