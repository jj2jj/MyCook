package zjj.com.mycookassistant.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import zjj.com.mycookassistant.app.App;


/**
 * Description: BaseFragment
 * Creator: yxc
 * date: 2016/9/7 11:40
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment {

    private final String TAG = getClass().getSimpleName();
    protected T mPresenter;
    protected Context mContext;
    protected View rootView;
    protected Unbinder unbinder;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据

    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        Logger.d(getName() + "------>onAttach");
        if (mContext != null) {
            this.mContext = mContext;
        } else {
            this.mContext = getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getName() + "------>onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(getName() + "------>onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
//        EventBus.getDefault().register(this);
//        setTitleHeight(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(getName() + "------>onActivityCreated");
        initEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(getName() + "------>onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(getName() + "------>onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(getName() + "------>onViewCreated");
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(getName() + "------>onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(getName() + "------>onStop");
    }

    @Override
    public void onDestroyView() {
//        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        Logger.d(getName() + "------>onDestroyView");
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
        mPresenter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(getName() + "------>ontroy");
        RefWatcher refWatcher = App.refWatcher;
        refWatcher.watch(this);
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(getName() + "------>onDetach");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d(getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }

    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
        Log.v(TAG, getClass().getName() + "------>lazyFetchData");
    }

    public String getName() {
        return BaseFragment.class.getName();
    }

    protected abstract int getLayout();

    protected void initView(LayoutInflater inflater) {
    }

    protected void initEvent() {
    }


}
