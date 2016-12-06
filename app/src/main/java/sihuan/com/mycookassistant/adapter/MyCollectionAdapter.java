package sihuan.com.mycookassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.activity.DetailPageActivity;
import sihuan.com.mycookassistant.bean.Works;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-09.
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.MyRecyclerHolder> {
    private Context mContext;
    private List<Works> mList = null;

    public MyCollectionAdapter(List<Works> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_list_plus, parent, false);
        return new MyRecyclerHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, final int position) {

        holder.mTitle.setText(mList.get(position).getTitle());
        // TODO: 2016-12-05 mName 没有显示出来 
        holder.mName.setText(mList.get(position).getUser() == null ? "" : mList.get(position).getUser().getUsername());

        Glide.with(mContext).load(mList.get(position).getImage() == null ? "www" : mList.get(position).getImage().getUrl()).into(holder.mPicture);

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016-11-09 跳转至item详情页
                Intent intent = new Intent(mContext, DetailPageActivity.class);
                intent.putExtra("itemObjectId", mList.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return mList.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class MyRecyclerHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mTitle;
        private CardView mItem;
        private ImageView mPicture;

        MyRecyclerHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name_item);
            mTitle = (TextView) itemView.findViewById(R.id.title_item);
            mPicture = (ImageView) itemView.findViewById(R.id.picture_item);
            mItem = (CardView) itemView.findViewById(R.id.item_recycler_plus);
        }
    }
}
