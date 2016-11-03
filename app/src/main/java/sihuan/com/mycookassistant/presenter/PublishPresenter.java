package sihuan.com.mycookassistant.presenter;

import sihuan.com.mycookassistant.base.RxPresenter;
import sihuan.com.mycookassistant.presenter.contract.PublishContract;
import sihuan.com.mycookassistant.view.PublishView;


/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-02.
 */

public class PublishPresenter extends RxPresenter implements PublishContract.Presenter {
    public PublishPresenter(PublishView view){
        mView = view;

    }

}
