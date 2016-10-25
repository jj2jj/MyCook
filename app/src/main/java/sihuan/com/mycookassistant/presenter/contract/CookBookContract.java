package sihuan.com.mycookassistant.presenter.contract;

import sihuan.com.mycookassistant.base.BasePresenter;
import sihuan.com.mycookassistant.base.BaseView;

/**
 * sihuan.com.mycookassistant.presenter.contract
 * Created by sihuan on 2016/10/25.
 */
public interface CookBookContract {
    interface View extends BaseView<Presenter> {
        void initNavigationDrawer();

    }


    interface Presenter extends BasePresenter {
        //        void getTabData();

    }
}
