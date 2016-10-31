package sihuan.com.mycookassistant.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.activity.LoginActivity;
import sihuan.com.mycookassistant.activity.RegisterActivity;
import sihuan.com.mycookassistant.base.RootView;
import sihuan.com.mycookassistant.presenter.contract.LoginContract;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

/**
 * sihuan.com.mycookassistant.view
 * Created by sihuan on 2016/10/27.
 */

public class LoginView extends RootView<LoginContract.Presenter> implements LoginContract.View, OnEditorActionListener {

    @BindView(R.id.toolbar_login)
    Toolbar mToolbar;

    @BindView(R.id.login_username)
    AutoCompleteTextView mUsername;

    @BindView(R.id.login_password)
    EditText mPassword;

    @BindView(R.id.remember_password)
    CheckBox mRbPassWord;

    @BindView(R.id.auto_login)
    CheckBox mAutoLogin;

    @BindView(R.id.login)
    Button mLogin;

    @BindView(R.id.register)
    Button mRegister;

    @BindView(R.id.login_form)
    LinearLayout mLoginFormView;
    LoginActivity mActivity;
    ProgressDialog mPgDialog;


    public LoginView(Context context) {
        super(context);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.act_login_view, this);
    }

    @Override
    protected void initView() {
        mActivity = (LoginActivity) mContext;
        mActivity.setSupportActionBar(mToolbar);
       // mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setTitle(mContext.getString(R.string.login));
    }

    @Override
    protected void initEvent() {
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.attemptLogin();
            }
        });

        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivty(RegisterActivity.class);
            }
        });
        mPassword.setOnEditorActionListener(this);

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showProgress(boolean b) {
        mLoginFormView.setVisibility(VISIBLE);
        if (b) {
            showDialog();
            mLoginFormView.setVisibility(GONE);
        } else {
            hideDialog();
        }
    }


    private void showDialog() {
        if (mPgDialog == null) {
            mPgDialog = new ProgressDialog(mActivity, THEME_DEVICE_DEFAULT_LIGHT);
            mPgDialog.setMessage(getString(R.string.logining));
            mPgDialog.setCancelable(false);
            mPgDialog.setCanceledOnTouchOutside(false);
            mPgDialog.show();
        }
    }

    private void hideDialog() {
        if (mPgDialog != null) {
            mPgDialog.dismiss();
            mPgDialog = null;
        }
    }

    @Override
    public void navigateToActivty(Class clz) {
        mActivity.startActivity(new Intent(mActivity, clz));
        mActivity.finish();
    }


    @Override
    public void SetUsernameError(String error) {
        mUsername.setError(error);
    }

    @Override
    public void SetPasswordError(String error) {
        mPassword.setError(error);
    }

    @Override
    public String getUsername() {
        return mUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public String getString(int res) {
        return mActivity.getString(res);
    }

    @Override
    public void requestFocus(String s) {
        switch (s) {
            case USERNAME:
                mUsername.requestFocus();
                break;
            case PASSWORD:
                mPassword.requestFocus();
                break;
        }
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onEditorAction(TextView view, int i, KeyEvent event) {
        if (view.getId() == R.id.login || i == EditorInfo.IME_NULL) {
            mPresenter.attemptLogin();
            return true;
        }
        return false;
    }

}
