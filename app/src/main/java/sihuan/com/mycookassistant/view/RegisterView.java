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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.activity.RegisterActivity;
import sihuan.com.mycookassistant.base.RootView;
import sihuan.com.mycookassistant.presenter.contract.RegisterContract;

/**
 * Created by Jessica0906zjj on 2016-10-27.
 * RegisterView
 */

public class RegisterView extends RootView<RegisterContract.Presenter> implements RegisterContract.View, TextView.OnEditorActionListener {

    @BindView(R.id.toolbar_login)
    Toolbar mToolbar_Rgst;

    @BindView(R.id.register_username)
    AutoCompleteTextView mUsername_Rgst;

    @BindView(R.id.register_password)
    EditText mPassword_Rgst;

    @BindView(R.id.register_form)
    View mRegisterFormView;

    @BindView(R.id.register_button)
    Button mRegisterBtn;

    RegisterActivity mRegisterActivity;
    ProgressDialog mPgDialog;

    public RegisterView(Context context) {
        super(context);
    }


    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegisterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.act_register_view, this);
    }

    @Override
    protected void initView() {
        mRegisterActivity = (RegisterActivity) mContext;
        mRegisterActivity.setSupportActionBar(mToolbar_Rgst);
        mRegisterActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegisterActivity.getSupportActionBar().setTitle(mContext.getString(R.string.register));
    }

    @Override
    protected void initEvent() {
        mRegisterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.attemptRegister();
            }
        });
        mPassword_Rgst.setOnEditorActionListener(this);

    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (textView.getId() == R.id.register || i == EditorInfo.IME_NULL) {
            mPresenter.attemptRegister();
            return true;
        }
        return false;
    }

    @Override
    public void showProgress(boolean b) {
        mRegisterFormView.setVisibility(VISIBLE);
        if (b) {
            showDialog();
            mRegisterFormView.setVisibility(GONE);
        } else {
            hideDialog();
        }

    }

    private void showDialog() {
        if (mPgDialog == null) {
            mPgDialog = new ProgressDialog(mRegisterActivity);
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
    public void navigateToActivity(Class clz) {
        mRegisterActivity.startActivity(new Intent(mRegisterActivity, clz));
        mRegisterActivity.finish();

    }

    @Override
    public void SetUserNameError(String error) {
        mUsername_Rgst.setError(error);

    }

    @Override
    public void SetPasswordError(String error) {
        mPassword_Rgst.setError(error);

    }

    @Override
    public String getUsername() {
        return mUsername_Rgst.getText().toString();

    }

    @Override
    public String getPassword() {
        return mPassword_Rgst.getText().toString();
    }

    @Override
    public String getString(int res) {
        return mRegisterActivity.getString(res);
    }

    @Override
    public void requestViewFocus(String view) {
        switch (view) {
            case USERNAME_VIEW:
                mUsername_Rgst.requestFocus();
                break;
            case PASSWORD_VIEW:
                mUsername_Rgst.requestFocus();
                break;
        }

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(mRegisterActivity, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

    }


}
