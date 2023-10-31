package com.raees.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class eczarbold extends androidx.appcompat.widget.AppCompatTextView {



    public eczarbold(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Eczar-SemiBold.ttf");
        this.setTypeface(face);
    }

    public eczarbold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Eczar-SemiBold.ttf");
        this.setTypeface(face);
    }

    public eczarbold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Eczar-SemiBold.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}