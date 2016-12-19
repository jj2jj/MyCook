package sihuan.com.mycookassistant.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import sihuan.com.mycookassistant.base.RxPresenter;
import sihuan.com.mycookassistant.bean.MyDomain;
import sihuan.com.mycookassistant.bean.Works;
import sihuan.com.mycookassistant.presenter.contract.HomePageContract;

/**
 * sihuan.com.mycookassistant.presenter.contract
 * Created by sihuan on 2016/10/25.
 */

public class HomePagePresenter extends RxPresenter implements HomePageContract.Presenter {
    private HomePageContract.View mView;
    private List<Works> rvList = new ArrayList<>();



    public HomePagePresenter(@NonNull HomePageContract.View view) {
        mView = view;
        mView.setPresenter(this);
        getBannerData();

    }

    @Override
    public void getBannerData() {
        List<MyDomain> list = getBannerAd();
        mView.setBanner(list);
    }

    @Override
    public List<Works> getRvData(int skip) {

        AVQuery<Works> avQuery = new AVQuery<>("Works");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");//include是指AVObject中的内容
        int limit = 5;
        avQuery.limit(limit);
        avQuery.skip(limit * skip);
        avQuery.findInBackground(new FindCallback<Works>() {
            @Override
            public void done(List<Works> list, AVException e) {
                if (e == null) {
                    rvList.addAll(list);
                } else {
                    e.printStackTrace();
                }
            }
        });
        Log.i("888888",rvList.toString());
        return rvList;
    }




    /**
     * 模拟
     * 轮播广播模拟数据
     *
     * @return
     */
    public static List<MyDomain> getBannerAd() {
        List<MyDomain> adList = new ArrayList<MyDomain>();

        MyDomain myDomain = new MyDomain();
        myDomain.setId("108078");
        myDomain.setDate("10月1日");
        myDomain.setTitle("国庆第一天");
        myDomain.setTopicFrom("AA");
        myDomain.setTopic("诱人美食图片大集合");
        myDomain.setImgUrl("http://img1.cache.netease.com/house/2012/10/9/2012100913411682189.jpg");
        adList.add(myDomain);

        MyDomain myDomain2 = new MyDomain();
        myDomain2.setId("108078");
        myDomain2.setDate("10月2日");
        myDomain2.setTitle("国庆第二天");
        myDomain2.setTopicFrom("BB");
        myDomain2.setTopic("诱人美食图片大集合");
        myDomain2.setImgUrl("http://img5.cache.netease.com/house/2012/10/9/2012100913405760784.jpg");
        adList.add(myDomain2);

        MyDomain myDomain3 = new MyDomain();
        myDomain3.setId("108078");
        myDomain3.setDate("10月3日");
        myDomain3.setTitle("国庆第三天");
        myDomain3.setTopicFrom("CC");
        myDomain3.setTopic("诱人美食图片大集合");
        myDomain3.setImgUrl("http://img2.cache.netease.com/house/2012/10/9/20121009134124551e5.jpg");
        adList.add(myDomain3);

        MyDomain myDomain4 = new MyDomain();
        myDomain4.setId("108078");
        myDomain4.setDate("10月4日");
        myDomain4.setTitle("国庆第四天");
        myDomain4.setTopicFrom("DD");
        myDomain4.setTopic("诱人美食图片大集合");
        myDomain4.setImgUrl("http://img2.cache.netease.com/house/2012/10/9/2012100913411889676.jpg");
        adList.add(myDomain4);
        MyDomain myDomain5 = new MyDomain();
        myDomain5.setId("108078");
        myDomain5.setDate("10月5日");
        myDomain5.setTitle("国庆第五天");
        myDomain5.setTopicFrom("EE");
        myDomain5.setTopic("诱人美食图片大集合");
        myDomain5.setImgUrl("http://img6.cache.netease.com/house/2012/10/9/20121009134100ee144.jpg");
        adList.add(myDomain5);
        return adList;
    }

}
