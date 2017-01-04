package zjj.com.mycookassistant.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.base.BaseActivity;
import zjj.com.mycookassistant.presenter.LoginPresenter;
import zjj.com.mycookassistant.view.LoginView;

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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
