package com.example.mrs.keyboardapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    LinearLayout emojiLayout, moreLayout;
    TextView emoji, more;
    EditText editv;
    private LinearLayoutParent rootLayout;

    private int keyboardHeight;
    private boolean keyboardOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = (LinearLayoutParent) findViewById(R.id.rootLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycleList);
        emojiLayout = (LinearLayout) findViewById(R.id.emojiLayout);
        moreLayout = (LinearLayout) findViewById(R.id.moreLayout);

        emoji = (TextView) findViewById(R.id.emoji);
        more = (TextView) findViewById(R.id.more);
        editv = (EditText) findViewById(R.id.editv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new Adapter(this));
        emoji.setOnClickListener(this);
        more.setOnClickListener(this);
        editv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hidePanel();
                keyboardOpen = true;
                return false;
            }
        });
        rootLayout.addOnSizeChangedListener(new LinearLayoutParent.onSizeChangedListener() {

            @Override
            public void onSizeChanged(boolean kbOpened, int kbHeight) {
                keyboardOpen = kbOpened;
                keyboardHeight = kbHeight;
                recyclerView.scrollToPosition(recyclerView.getChildCount() - 1);
            }
        });

    }

    private void hidePanel() {
        emojiLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboradHelper.updateSoftInpuMode(MainActivity.this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                emojiLayout.setVisibility(View.GONE);
            }
        }, 300);

        moreLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboradHelper.updateSoftInpuMode(MainActivity.this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                moreLayout.setVisibility(View.GONE);
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        if (keyboardOpen) {
            KeyboradHelper.hideSoftInput(editv);
        }
        if (v.getId() == R.id.emoji) {
            switchPanel(emojiLayout, moreLayout);

        } else if (v.getId() == R.id.more) {
            switchPanel(moreLayout, emojiLayout);

        }
    }

    void switchPanel(LinearLayout showLayout, LinearLayout hideLayout) {
        KeyboradHelper.updateSoftInpuMode(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        if (keyboardHeight != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) showLayout.getLayoutParams();
            params.height = keyboardHeight;
            showLayout.setLayoutParams(params);
        }
        showLayout.setVisibility(View.VISIBLE);
        hideLayout.setVisibility(View.GONE);
        keyboardOpen = false;
    }

    @Override
    public void onBackPressed() {
        if (keyboardOpen) {
            KeyboradHelper.updateSoftInpuMode(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            KeyboradHelper.hideSoftInput(editv);
            keyboardOpen =false;
        }
        else
            super.onBackPressed();
    }
}
