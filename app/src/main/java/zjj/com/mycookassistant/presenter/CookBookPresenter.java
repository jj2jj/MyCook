package zjj.com.mycookassistant.presenter;

import zjj.com.mycookassistant.base.RxPresenter;
import zjj.com.mycookassistant.presenter.contract.CookBookContract;
import zjj.com.mycookassistant.view.CookBookView;

/**
 * sihuan.com.mycookassistant.presenter
 * Created by sihuan on 2016/10/25.
 */

public class CookBookPresenter extends RxPresenter implements CookBookContract.Presenter {

    private CookBookView mView;


    public CookBookPresenter(CookBookView view) {
        mView = view;
        mView.setPresenter(this);
        mView.initNavigationDrawer();
//        getTabData();
    }

//    @Override
//    public void getTabData() {
//
//    }


}
