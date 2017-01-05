package zjj.com.mycookassistant.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.adapter.MyCollectionAdapter;
import zjj.com.mycookassistant.bean.Works;

/**
 * MyCollectionFragment
 */
public class MyCollectionFragment extends Fragment {
    private int skip = 0;
    private XRecyclerView mRecyclerView;
    private List<Works> mlist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_my_collection_view, container, false);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycler_collect);
        mRecyclerView.setHasFixedSize(true);
        //setHasFixedSize()方法用来使RecyclerView保持固定的大小
//        设置网格布局管理器

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);

        LoadEvent();

        mRecyclerView.setAdapter(new MyCollectionAdapter(mlist, getActivity()));
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
                        getData(skip);
                        mRecyclerView.loadMoreComplete();//加载更多完成
                    }
                }, 1500);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData(int skip) {
        //关联属性查询
        AVQuery<AVObject> collectionQuery = new AVQuery<>("Collections");
        collectionQuery.whereEqualTo("collector", AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId()));
        final int limit = 5;
        collectionQuery.limit(limit);
        collectionQuery.skip(limit * skip);
        collectionQuery.include("worksObjectId");
        collectionQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject collections : list) {
                    AVObject works = collections.getAVObject("worksObjectId");
                    mlist.add((Works) works);
                }
            }
        });
    }

}
