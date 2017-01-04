package zjj.com.mycookassistant.adapter;


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

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.activity.DetailPageActivity;
import zjj.com.mycookassistant.bean.Works;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-09.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyRecyclerHolder> {
    private Context mContext;
    private List<Works> mList = null;

    public SearchAdapter(List<Works> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_search_result, parent, false);
        return new MyRecyclerHolder(view);
    }

    //将数据与界面进行绑定的操作
    //如果将position改成holder.getAdapterPosition(),则界面下拉刷新会崩溃
    @Override
    public void onBindViewHolder(final MyRecyclerHolder holder, final int position) {

        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mDescribe.setText(mList.get(position).getDescribe());
//        holder.mName.setText(mList.get(position).getAVUser("owner") == null ? "" : mList.get(position).getAVUser("owner").getUsername());
        Glide.with(mContext).load(mList.get(position).getImage() == null ? "www" : mList.get(position).getImage().getUrl()).into(holder.mPicture);

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPageActivity.class);
                intent.putExtra("itemObjectId", mList.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return mList == null ? 0 : mList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class MyRecyclerHolder extends RecyclerView.ViewHolder {
        //        private TextView mName;
        private TextView mTitle;
        private TextView mDescribe;
        private CardView mItem;
        private ImageView mPicture;

        MyRecyclerHolder(View itemView) {
            super(itemView);
//            mName = (TextView) itemView.findViewById(R.id.name_item);
            mTitle = (TextView) itemView.findViewById(R.id.title_item);
            mDescribe = (TextView) itemView.findViewById(R.id.describe_search);
            mPicture = (ImageView) itemView.findViewById(R.id.picture_item);
            mItem = (CardView) itemView.findViewById(R.id.item_recycler_search);
        }
    }
}
