package sihuan.com.mycookassistant.adapter;


import android.content.Context;
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
import sihuan.com.mycookassistant.bean.Works;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-09.
 */

public class HomePageRvAdapter extends RecyclerView.Adapter<HomePageRvAdapter.MyRecyclerHolder> {
    private Context mContext;
    private List<Works> mList;



    public HomePageRvAdapter(List<Works> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new MyRecyclerHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_list_plus, parent, false));
    }


    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, int position) {

        holder.mTitle.setText(mList.get(position).getTitle());
       // holder.mName.setText(mList.get(position).getUser() == null ? "" : mList.get(position).getUser().getUsername());
        Glide.with(mContext).load(mList.get(position).getImage() == null ? "www" : mList.get(position).getImage().getUrl()).into(holder.mPicture);
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016-11-09 跳转至item详情页
            }
        });
    }
    @Override
    public int getItemCount() {
            return mList.size();
    }

   class MyRecyclerHolder extends RecyclerView.ViewHolder {
       //private TextView mName;
        private TextView mTitle;
        private CardView mItem;
        private ImageView mPicture;
        MyRecyclerHolder(View itemView) {
            super(itemView);
           // mName = (TextView) itemView.findViewById(R.id.name_item);
            mTitle = (TextView) itemView.findViewById(R.id.title_item);
            mPicture = (ImageView) itemView.findViewById(R.id.picture_item);
            mItem = (CardView) itemView.findViewById(R.id.item_recycler);
        }
    }
}
