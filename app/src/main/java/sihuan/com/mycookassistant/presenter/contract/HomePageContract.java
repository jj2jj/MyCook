package sihuan.com.mycookassistant.presenter.contract;

import java.util.List;

import sihuan.com.mycookassistant.base.BasePresenter;
import sihuan.com.mycookassistant.base.BaseView;
import sihuan.com.mycookassistant.bean.Works;

/**
 * sihuan.com.mycookassistant.presenter.contract
 * Created by sihuan on 2016/10/25.
 */

public interface HomePageContract {
    interface View extends BaseView<Presenter> {
        void setBanner(List list);
    }

    interface Presenter extends BasePresenter {
        void getBannerData();
    // list getdata();
    List<Works> getRvData(int skip);
    }
}
