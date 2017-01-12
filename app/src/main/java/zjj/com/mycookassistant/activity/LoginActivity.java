package zjj.com.mycookassistant.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.base.BaseActivity;
import zjj.com.mycookassistant.presenter.LoginPresenter;
import zjj.com.mycookassistant.view.LoginView;

/**
 * zjj.com.mycookassistant.activity
 * Created by zjj on 2016/10/27.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_view)
    LoginView mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);//绑定视图
        mPresenter = new LoginPresenter(mLoginView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
