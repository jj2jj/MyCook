package zjj.com.mycookassistant.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import zjj.com.mycookassistant.app.App;

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
        //1，让你的所有Activity都继承一个BaseActivity,然后在BaseActivity的onCreate()方法中加上
        //2，Manifest.xml文件中为所有Activity加上配置属性 android:screenOrientation="portrait"
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
     * @param on 是否开启沉浸式
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
