package sihuan.com.mycookassistant.base;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import sihuan.com.mycookassistant.app.App;
import sihuan.com.mycookassistant.R;

/**
 * sihuan.com.mycookassistant.base
 * Created by sihuan on 2016/10/25.
 */

public class BaseActivity<T extends BasePresenter> extends SupportActivity {
    protected Unbinder mUnbinder;
    protected T mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        inint();
    }

    protected void inint() {
        setTranslucentStatus(true);
        App.getInstance().registerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(this.getClass().getName() + "------>onStart");
//        setTitleHeight(getRootView(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d(this.getClass().getName() + "------>onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(this.getClass().getName() + "------>onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(this.getClass().getName() + "------>onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(this.getClass().getName() + "------>onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(this.getClass().getName() + "------>onDestroy");
        App.getInstance().unregisterActivity(this);
        if (mUnbinder != null)
            mUnbinder.unbind();
        mPresenter = null;
    }

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }


}
