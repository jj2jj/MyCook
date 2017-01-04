package zjj.com.mycookassistant.view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.common.base.Preconditions;

import zjj.com.mycookassistant.base.RootView;
import zjj.com.mycookassistant.presenter.contract.PublishContract;


/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-02.
 */

public class PublishView extends RootView<PublishContract.Presenter> implements PublishContract.View{
    public PublishView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setPresenter(PublishContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void getLayout() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

}
