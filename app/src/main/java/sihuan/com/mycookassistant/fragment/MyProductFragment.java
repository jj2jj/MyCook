package sihuan.com.mycookassistant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.adapter.MyRecyclerAdapter;

/**
 * sihuan.com.mycookassistant.fragment
 * Created by sihuan on 2016/10/25.
 */
// TODO: 2016-11-09 本周：上拉加载，下拉刷新


public class MyProductFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter mRecyclerAdapter;
    private List<AVObject> mList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fmt_my_product_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);
        //setHasFixedSize()方法用来使RecyclerView保持固定的大小
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerAdapter = new MyRecyclerAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Works");
        avQuery.orderByDescending("createdAt");
        String user = AVUser.getCurrentUser().getObjectId();
        avQuery.whereEqualTo("owner",AVObject.createWithoutData("_User",user));
        Log.i("user",user);
        //avQuery.include("owner");//include是指AVObject中的内容
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
