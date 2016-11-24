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
import sihuan.com.mycookassistant.bean.Materials;

/**
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
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.ami_Materials.setText(mDatas.get(position).getMaterial());
        holder.ami_Dosages.setText(mDatas.get(position).getDosages());
    }

    public void addData(int position, Materials m){
        mDatas.add(position, m);


        notifyItemInserted(position);

    }

    public void deleteData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText ami_Materials;
        EditText ami_Dosages;
        LinearLayout mItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ami_Materials = (EditText) itemView.findViewById(R.id.ami_materials);
            ami_Dosages = (EditText) itemView.findViewById(R.id.ami_dosages);
            mItem = (LinearLayout) itemView.findViewById(R.id.add_material_item);
        }
    }
}
