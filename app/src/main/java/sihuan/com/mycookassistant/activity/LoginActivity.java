package sihuan.com.mycookassistant.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;
import sihuan.com.mycookassistant.presenter.LoginPresenter;
import sihuan.com.mycookassistant.view.LoginView;

/**
 * sihuan.com.mycookassistant.activity
 * Created by sihuan on 2016/10/27.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_view)
    LoginView mLoginView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new LoginPresenter(mLoginView);

        mLoginView.autoLogin();
    }
}
