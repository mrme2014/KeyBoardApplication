package com.example.mrs.keyboardapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by mrs on 2017/4/1.
 */

public class KeyboradHelper {

    private static int curContentHeight = 0;

    public static void addOnGlobalLayoutListener(final Activity mActivity,onSoftInputChangedListener listener1) {
        listener = listener1;
        mActivity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //应用程序 界面高度
                curContentHeight = r.bottom - r.top;
                //屏幕总高度-状态栏-虚拟导航栏
                int realScreenHeight = getScreenheight(mActivity) - getStatusBarHeight(mActivity) - getSoftButtonsBarHeight(mActivity);
                //软键盘高度
                int softInputHeight = getSoftInputHeight(mActivity, r);
                listener.onKeyboardModeChanged(curContentHeight == realScreenHeight, softInputHeight);
                Log.e("键盘弹起", curContentHeight + "--" + realScreenHeight + "--" + r.toString());
            }
        });
    }

    private static int getSoftInputHeight(Activity mActivity, Rect r) {
        int contentHeight = getScreenheight(mActivity);
        int softInputHeight = contentHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 18) {
            softInputHeight = softInputHeight - getSoftButtonsBarHeight(mActivity);
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity mActivity) {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    public static int getScreenheight(Activity activity) {
        return activity.getWindow().getDecorView().getHeight();
    }

    public static void showSoftInput(View fouceView) {
        InputMethodManager manager = (InputMethodManager) fouceView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(fouceView, 0);
    }

    public static void hideSoftInput(View fouceView) {
        InputMethodManager manager = (InputMethodManager) fouceView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(fouceView.getWindowToken(), 0);
    }

    public static  void updateSoftInpuMode(Activity activity, int softInputMode) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.softInputMode = softInputMode;
        activity.getWindow().setAttributes(params);
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return (int) context.getResources().getDimensionPixelSize(identifier);
    }

    public static onSoftInputChangedListener listener;

    public interface onSoftInputChangedListener {
        void onKeyboardModeChanged(boolean open, int keyboardHeight);
    }

    public void addOnSizeChangedListener(onSoftInputChangedListener listener1) {
        this.listener = listener1;
    }
}
