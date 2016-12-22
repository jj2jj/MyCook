package sihuan.com.mycookassistant.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.adapter.SearchAdapter;
import sihuan.com.mycookassistant.base.BaseActivity;
import sihuan.com.mycookassistant.bean.Works;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-12-20.
 */

public class SearchActivity extends BaseActivity {
    private XRecyclerView mRecyclerView;
    private int skip = 0;
    private SearchAdapter myAdapter;
    private List<Works> worksList = new ArrayList<>();
    Toolbar mToolbar;
    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
//        String id = getIntent().getStringExtra("dishes_type");

        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra("search_result"));

        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_search);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        LoadEvent();
        myAdapter = new SearchAdapter(worksList, this);
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

    /**
     * OR查询 获取数据，搜索的关键词与title、material、describe 三个字段进行匹配，
     * 若满足其中一个则列出来
     * @param skip 跳转页面页数
     */
    private void getData(int skip) {
        String searchResult = getIntent().getStringExtra("search_result");

        AVQuery<Works> search_title_Query = AVObject.getQuery(Works.class);
        search_title_Query.whereEqualTo("title",searchResult);

        AVQuery<Works> search_material_Query = AVObject.getQuery(Works.class);
        search_material_Query.whereEqualTo("materials",searchResult);

        AVQuery<Works> search_describe_Query = AVObject.getQuery(Works.class);
        search_describe_Query.whereEqualTo("describe",searchResult);

        AVQuery<Works> searchQuery = AVQuery.or(Arrays
                .asList(search_title_Query,search_material_Query,search_describe_Query));
                int limit = 5;
                searchQuery.limit(limit);
                searchQuery.skip(limit * skip);
//        searchQuery.include("owner");
        searchQuery.findInBackground(new FindCallback<Works>() {
            @Override
            public void done(List<Works> list, AVException e) {
                if (e == null) {
                    worksList.addAll(list);
                    Log.e("zjj",worksList.size()+"worklist");
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
