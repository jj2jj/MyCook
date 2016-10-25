package sihuan.com.mycookassistant.presenter;

import sihuan.com.mycookassistant.base.RxPresenter;
import sihuan.com.mycookassistant.presenter.contract.CookBookContract;
import sihuan.com.mycookassistant.view.CookBookView;

/**
 * sihuan.com.mycookassistant.presenter
 * Created by sihuan on 2016/10/25.
 */

public class CookBookPresenter extends RxPresenter implements CookBookContract.Presenter {

    CookBookView mView;


    public CookBookPresenter(CookBookView view) {
        mView = view;
        mView.initNavigationDrawer();
//        getTabData();
    }

//    @Override
//    public void getTabData() {
//
//    }


}
