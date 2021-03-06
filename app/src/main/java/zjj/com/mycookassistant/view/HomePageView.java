package zjj.com.mycookassistant.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Preconditions;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.adapter.HomePageRvAdapter;
import zjj.com.mycookassistant.base.RootView;
import zjj.com.mycookassistant.bean.MyDomain;
import zjj.com.mycookassistant.bean.Works;
import zjj.com.mycookassistant.net.GlideImageLoader;
import zjj.com.mycookassistant.presenter.contract.HomePageContract;


/**
 * sihuan.com.mycookassistant.view
 * Created by Jessica on 2016/10/25.
 */

public class HomePageView extends RootView<HomePageContract.Presenter> implements HomePageContract.View {
    private List<Works> worksList = new ArrayList<>();

    private int skip = 0;

    //    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.xrv_home_page_view)
    XRecyclerView home_page_xrv;

    HomePageRvAdapter home_page_xrvAdapter;

    public HomePageView(Context context) {
        super(context);
    }

    public HomePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fmt_home_page_view, this);
    }


    @Override
    protected void initView() {
        View view = inflate(mContext, R.layout.banner_head_view, null);//加载banner视图
        mBanner = (Banner) view.findViewById(R.id.banner);
        home_page_xrv.addHeaderView(view);//banner作为headview

        home_page_xrv.setHasFixedSize(true);
        home_page_xrv.setLayoutManager(new LinearLayoutManager(mContext));
        home_page_xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        home_page_xrv.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        LoadEvent();//上拉加载、下拉刷新
        home_page_xrvAdapter = new HomePageRvAdapter(worksList, mContext);
        home_page_xrv.setAdapter(home_page_xrvAdapter);
        home_page_xrv.setRefreshing(true);
    }

    /**
     * 上拉加载、下拉刷新
     */
    private void LoadEvent() {
        home_page_xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        worksList = mPresenter.getRvData(0);
                        home_page_xrvAdapter.setData(worksList);
                        home_page_xrv.refreshComplete();//下拉刷新完成
                    }
                }, 1500);
                skip = 0;
            }

            @Override
            public void onLoadMore() {
                // load more data here
                skip++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getRvData(skip);
                        home_page_xrvAdapter.notifyDataSetChanged();
                        home_page_xrv.loadMoreComplete();//加载更多完成
                    }
                }, 1500);
            }
        });


    }


    @Override
    protected void initEvent() {

    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setBanner(List list) {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MyDomain domain = (MyDomain) list.get(i);
            images.add(domain.getImgUrl());
            titles.add(domain.getDate() + " , " + domain.getTitle());
        }

        mBanner.setImages(images)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .start();

        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
//                Intent intent = new Intent(mContext, DetailPageActivity.class);
//                intent.putExtra("itemObjectId", worksList.get(position).getObjectId());
//                mContext.startActivity(intent);
            }
        });
    }


}
