package sihuan.com.mycookassistant.presenter.contract;

import sihuan.com.mycookassistant.base.BasePresenter;
import sihuan.com.mycookassistant.base.BaseView;

/**
 * sihuan.com.mycookassistant.presenter.contract
 * Created by sihuan on 2016/10/27.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        String PASSWORD_VIEW = "password_view";
        String USERNAME_VIEW = "username_view";

        void setLoginInfo();

        void showProgress(boolean b);

        void navigateToActivty(Class clz);

        void SetUsernameError(String error);

        void SetPasswordError(String error);

        String getUsername();

        String getPassword();

        String getString(int res);

        void requestViewFocus(String view);

        void showToast(String s);

    }

    interface Presenter extends BasePresenter {
        void attemptLogin();

    }

}
