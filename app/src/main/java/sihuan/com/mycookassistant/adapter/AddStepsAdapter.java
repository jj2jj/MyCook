package sihuan.com.mycookassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.bean.Steps;

/**
 * AddStepsAdapter
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
        return new MyViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.asi_steps.setText(mSteps.get(position).getSteps());
        holder.asi_steps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mSteps.get(position).setSteps(holder.asi_steps.getText().toString());

            }
        });
        holder.asi_steps.requestFocus();
    }

    public void addData(int position, Steps s) {
        mSteps.add(position, s);
        notifyItemInserted(position);
    }

    public void deleteData(int position) {
        mSteps.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mSteps==null?0:mSteps.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class MyViewHolder extends RecyclerView.ViewHolder {
        EditText asi_steps;
        LinearLayout mItem;

        MyViewHolder(View itemView) {
            super(itemView);
            asi_steps = (EditText) itemView.findViewById(R.id.asi_steps);
            mItem = (LinearLayout) itemView.findViewById(R.id.add_steps_item);
        }
    }
}
