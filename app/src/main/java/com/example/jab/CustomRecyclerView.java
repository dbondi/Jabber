package com.example.jab;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {

    Context context;

    public CustomRecyclerView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public boolean fling(int velocityX, int velocityY) {

        // velocityX *= 0.7; for Horizontal recycler view. comment velocityY line not require for Horizontal Mode.

        return super.fling(velocityX, velocityY);
    }



}