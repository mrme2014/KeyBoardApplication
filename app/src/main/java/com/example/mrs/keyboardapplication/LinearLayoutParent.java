package com.example.mrs.keyboardapplication;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by mrs on 2017/4/1.
 */

public class LinearLayoutParent extends LinearLayout {
    private boolean keyboardShow = false;
    onSizeChangedListener listener;

    public interface onSizeChangedListener {
        void onSizeChanged(boolean kbOpened, int kbHeight);
    }

    public void addOnSizeChangedListener(onSizeChangedListener listener1) {
        this.listener = listener1;
    }

    public LinearLayoutParent(Context context) {
        super(context);
    }

    public LinearLayoutParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutParent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (oldh != 0) {
            if (listener != null)
                listener.onSizeChanged(h < oldh, Math.abs(oldh - h));
        }

        Log.e("onSizeChanged", h + "--" + oldh);
    }
}
