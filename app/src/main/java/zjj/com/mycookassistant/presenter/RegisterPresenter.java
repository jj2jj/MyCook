package zjj.com.mycookassistant.presenter;


import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.activity.CookBookActivity;
import zjj.com.mycookassistant.base.RxPresenter;
import zjj.com.mycookassistant.presenter.contract.RegisterContract;
import zjj.com.mycookassistant.view.RegisterView;

/**
 * RegisterPresenter
 * Created by Jessica0906zjj on 2016-10-27.
 */

public class RegisterPresenter extends RxPresenter implements RegisterContract.Presenter {
    private RegisterView mRegisterView;

    public RegisterPresenter(RegisterView view) {
        mRegisterView = view;
        mRegisterView.setPresenter(this);

        //mRegisterView.attemptRegister();

    }

    @Override
    public void attemptRegister() {
        mRegisterView.SetUserNameError(null);
        mRegisterView.SetPasswordError(null);
        String username = mRegisterView.getUsername();
        String password = mRegisterView.getPassword();

        if (TextUtils.isEmpty(username)) {
            //这个是必填项
            mRegisterView.SetUserNameError(mRegisterView.getString(R.string.error_field_required));
            mRegisterView.requestViewFocus(RegisterContract.View.USERNAME_VIEW);
            return;
        }
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mRegisterView.SetPasswordError(mRegisterView.getString(R.string.error_invalid_password));
            mRegisterView.requestViewFocus(RegisterContract.View.PASSWORD_VIEW);
            return;
        }

        mRegisterView.showProgress(true);


        AVUser user = new AVUser();//新建AVUser对象实例
        user.setUsername(username);// 设置用户名
        user.setPassword(password);// 设置密码
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    mRegisterView.navigateToActivity(CookBookActivity.class);

                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    mRegisterView.showProgress(false);
                    //Toast.makeText(mRegisterView, e.getMessage(), Toast.LENGTH_SHORT).show();
                    mRegisterView.showToast(e.getMessage());
                }
            }
        });

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
