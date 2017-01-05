package zjj.com.mycookassistant.fragment;

import android.view.LayoutInflater;

import butterknife.BindView;
import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.base.BaseFragment;
import zjj.com.mycookassistant.presenter.HomePagePresenter;
import zjj.com.mycookassistant.view.HomePageView;

/**
 * sihuan.com.mycookassistant.fragment
 * Created by sihuan on 2016/10/25.
 */

public class HomePageFragment extends BaseFragment {
    HomePagePresenter mPresenter;

    @BindView(R.id.home_page_view)
    HomePageView mView;

    @Override
    protected int getLayout() {
        return R.layout.fmt_home_page;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new HomePagePresenter(mView);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
