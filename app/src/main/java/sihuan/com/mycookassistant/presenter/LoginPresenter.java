package sihuan.com.mycookassistant.presenter;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.activity.CookBookActivity;
import sihuan.com.mycookassistant.base.RxPresenter;
import sihuan.com.mycookassistant.presenter.contract.LoginContract;
import sihuan.com.mycookassistant.view.LoginView;

/**
 * sihuan.com.mycookassistant.presenter
 * Created by sihuan on 2016/10/27.
 */

public class LoginPresenter extends RxPresenter implements LoginContract.Presenter {
    LoginView mView;

    SharedPreferences mSP;
    SharedPreferences.Editor editor;

    public LoginPresenter(LoginView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void attemptLogin() {
        mView.SetUsernameError(null);
        mView.SetPasswordError(null);
        String username = mView.getUsername();
        String password = mView.getPassword();
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mView.SetPasswordError(mView.getString(R.string.error_invalid_password));
            mView.requestFocus(LoginContract.View.PASSWORD);
            return;
        }
        if (TextUtils.isEmpty(username)) {
            //这个是必填项
            mView.SetUsernameError(mView.getString(R.string.error_field_required));
            mView.requestFocus(LoginContract.View.USERNAME);
            return;
        }
        mView.showProgress(true);

        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    mView.navigateToActivty(CookBookActivity.class);
                } else {
                    mView.showProgress(false);
                    mView.showToast(e.getMessage());
                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
