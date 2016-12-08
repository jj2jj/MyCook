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

import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.bean.Materials;

/**
 * AddMaterialsAdapter
 * Created by Jessica0906zjj on 2016-09-18.
 */
public class AddMaterialsAdapter extends RecyclerView.Adapter<AddMaterialsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Materials> mDatas;


    public AddMaterialsAdapter(List<Materials> datas, Context context) {
        this.mContext = context;
        this.mDatas = datas;
    }

    ////创建新View，被LayoutManager所调用
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_materials, parent, false);
        return new MyViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.ami_Portion.setText(mDatas.get(holder.getAdapterPosition()).getPortion());

        holder.ami_Portion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mDatas.get(holder.getAdapterPosition()).setPortion(holder.ami_Portion.getText().toString());
            }
        });

        holder.ami_Food.setText(mDatas.get(position).getFood());
        holder.ami_Food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mDatas.get(holder.getAdapterPosition()).setFood(holder.ami_Food.getText().toString());
            }
        });
        holder.ami_Food.requestFocus();
    }

    public void addData(int position, Materials m) {
        mDatas.add(position, m);
        notifyItemInserted(position);

    }

    public void deleteData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class MyViewHolder extends RecyclerView.ViewHolder {
        EditText ami_Food;
        EditText ami_Portion;
        LinearLayout mItem;

        MyViewHolder(View itemView) {
            super(itemView);
            ami_Food = (EditText) itemView.findViewById(R.id.ami_materials);
            ami_Portion = (EditText) itemView.findViewById(R.id.ami_dosages);
            mItem = (LinearLayout) itemView.findViewById(R.id.add_material_item);
        }
    }
}
