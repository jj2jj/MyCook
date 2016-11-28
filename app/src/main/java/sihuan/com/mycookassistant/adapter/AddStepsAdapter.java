package sihuan.com.mycookassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.bean.Steps;

/**
 * Created by Jessica0906zjj on 2016-09-18.
 */
public class AddStepsAdapter extends RecyclerView.Adapter<AddStepsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Steps> mSteps;


    public AddStepsAdapter(List<Steps> steps, Context context) {
        this.mContext = context;
        this.mSteps = steps;
    }

    ////创建新View，被LayoutManager所调用
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_steps, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.asi_steps.setText(mSteps.get(position).getSteps());
        holder.asi_steps.requestFocus();
    }
    public void addData(int position, Steps s){
        mSteps.add(position, s);
        notifyItemInserted(position);
    }
    public void deleteData(int position){
        mSteps.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText asi_steps;
        LinearLayout mItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            asi_steps = (EditText) itemView.findViewById(R.id.asi_steps);
            mItem = (LinearLayout) itemView.findViewById(R.id.add_material_item);
        }
    }
}
