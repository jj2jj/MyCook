package zjj.com.mycookassistant.presenter.contract;


import zjj.com.mycookassistant.base.BasePresenter;
import zjj.com.mycookassistant.base.BaseView;

/**
 *
 * Created by Jessica0906zjj on 2016-10-27.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        String PASSWORD_VIEW = "password_view";
        String USERNAME_VIEW = "username_view";


        void showProgress(boolean b);

        void navigateToActivity(Class clz);

        void SetUserNameError(String error);

        void SetPasswordError(String error);

        String getUsername();

        String getPassword();

        String getString(int res);

        void requestViewFocus(String view);

        void showToast(String s);


        //
    }
    interface Presenter extends BasePresenter {
        void attemptRegister();
    }

}
