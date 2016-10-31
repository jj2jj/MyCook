package sihuan.com.mycookassistant.presenter.contract;


import sihuan.com.mycookassistant.base.BasePresenter;
import sihuan.com.mycookassistant.base.BaseView;

/**
 * Created by Jessica0906zjj on 2016-10-27.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {

        public static final String PASSWORD = "0";
        public static final String USERNAME = "1";

        void showProgress(boolean b);

        void navigateToActivity(Class clz);

        void SetUserNameError(String error);

        void SetPasswordError(String error);

        String getUsername();

        String getPassword();

        String getString(int res);

        void requestFocus(String s);

        void showToast(String s);


        //
    }
    interface Presenter extends BasePresenter {
        void attemptRegister();
    }

}
