package zjj.com.mycookassistant.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.adapter.DetailTypeAdapter;
import zjj.com.mycookassistant.base.BaseActivity;
import zjj.com.mycookassistant.bean.Works;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-28.
 */

public class DetailTypeActivity extends BaseActivity {
    private XRecyclerView mRecyclerView;
    private int skip = 0;
    private DetailTypeAdapter myAdapter;
    private List<Works> worksList = new ArrayList<>();
    Toolbar mToolbar;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_type);
//        String id = getIntent().getStringExtra("dishes_type");

        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra("dishes_type"));

        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_type);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        LoadEvent();
        myAdapter = new DetailTypeAdapter(worksList, this);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setRefreshing(true);


    }

    private void LoadEvent() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        worksList.clear();
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

    private void getData(int skip) {
        //关联属性查询
        String dishesType = getIntent().getStringExtra("dishes_type");
        AVQuery<Works> avQuery = AVObject.getQuery(Works.class);
        avQuery.whereEqualTo("dishestype", dishesType);
        int limit = 5;
        avQuery.limit(limit);
        avQuery.skip(limit * skip);
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<Works>() {
            @Override
            public void done(List<Works> list, AVException e) {
                if (e == null) {
                    worksList.addAll(list);
                    myAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressedSupport();
        }
        return super.onOptionsItemSelected(item);
    }
}
