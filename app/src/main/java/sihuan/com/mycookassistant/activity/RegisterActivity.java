package sihuan.com.mycookassistant.activity;

import android.os.Bundle;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;
import sihuan.com.mycookassistant.presenter.RegisterPresenter;
import sihuan.com.mycookassistant.view.RegisterView;

/**
 * Created by Jessica0906zjj on 2016-09-26.
 * 注册界面
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_view)
    RegisterView mRegisterView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inint();
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(mRegisterView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mRegisterView.navigateToActivity(LoginActivity.class);
            RegisterActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mRegisterView.navigateToActivity(LoginActivity.class);
        RegisterActivity.this.finish();

    }
}
