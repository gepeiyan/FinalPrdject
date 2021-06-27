package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MarqueTextView(Context context) {
        super (context);
    }
    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override

    public boolean isFocused() {

        return true;
    }
}
