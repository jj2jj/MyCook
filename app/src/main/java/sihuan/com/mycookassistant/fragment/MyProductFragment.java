package sihuan.com.mycookassistant.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.adapter.MyProductAdapter;
import sihuan.com.mycookassistant.bean.Works;

/**
 * sihuan.com.mycookassistant.fragment
 * Created by sihuan on 2016/10/25.
 */
public class MyProductFragment extends Fragment {
    private int skip = 0;
    private XRecyclerView mRecyclerView;
    private MyProductAdapter myProductAdapter;
    private List<Works> mlist = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_my_product_view, container, false);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);
        //setHasFixedSize()方法用来使RecyclerView保持固定的大小
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
       // mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        LoadEvent();

        myProductAdapter = new MyProductAdapter(mlist,getActivity());
        mRecyclerView.setAdapter(myProductAdapter);
        mRecyclerView.setRefreshing(true);
      //  mRecyclerView.setPullRefreshEnabled(true);
        return view;
    }

    private void LoadEvent() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mlist.clear();
                        getData(0);
                        mRecyclerView.refreshComplete();//下拉刷新完成
                    }
                },1500);
                skip = 0;
            }
            @Override
            public void onLoadMore() {
                // load more data here

                     skip++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData(skip);
                            mRecyclerView.loadMoreComplete();//加载更多完成
                        }
                    },1500);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData(int skip) {
        AVQuery<Works> query = AVObject.getQuery(Works.class);
        query.orderByDescending("createdAt");
        String user = AVUser.getCurrentUser().getObjectId();
        query.whereEqualTo("owner", AVObject.createWithoutData("_User", user));
        int limit = 5;
        query.limit(limit);
        query.skip(limit *skip);

        query.findInBackground(new FindCallback<Works>() {
            @Override
            public void done(List<Works> list, AVException e) {

                if (e == null) {
                    mlist.addAll(list);
                    myProductAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }


            }
        });
    }

}
