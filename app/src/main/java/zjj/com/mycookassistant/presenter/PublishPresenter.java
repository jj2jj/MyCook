package zjj.com.mycookassistant.presenter;

import zjj.com.mycookassistant.base.RxPresenter;
import zjj.com.mycookassistant.presenter.contract.PublishContract;
import zjj.com.mycookassistant.view.PublishView;


/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-02.
 */

public class PublishPresenter extends RxPresenter implements PublishContract.Presenter {
    public PublishPresenter(PublishView view){
        mView = view;

    }

}
