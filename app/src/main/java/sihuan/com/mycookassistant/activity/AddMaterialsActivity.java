package sihuan.com.mycookassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.adapter.AddMaterialsAdapter;
import sihuan.com.mycookassistant.base.BaseActivity;
import sihuan.com.mycookassistant.bean.Materials;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-23.
 */

public class AddMaterialsActivity extends BaseActivity {
    Toolbar mToolbar;
    ActionBar actionBar;
    private RecyclerView mRecyclerview;
    private List<Materials> mDatas;
    private AddMaterialsAdapter mAdapter;
    EditText ami_Materials;
    EditText ami_Dosages;
//    LinearLayout linearLayout;
//    View v;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_materials);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_add);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("添加用料");


        initDatas();
        initViews();
        mAdapter = new AddMaterialsAdapter(mDatas,this);
        mRecyclerview.setAdapter(mAdapter);
        //recyclerview的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);

        mRecyclerview.setLayoutManager(linearLayoutManager);
//设置增删动画
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        linearLayout = (LinearLayout) findViewById(R.id.add_activity);
    }

    private void initViews() {
        mRecyclerview = (RecyclerView) findViewById(R.id.rv_addmaterial);
        View view = LayoutInflater.from(this).inflate(R.layout.item_add_materials,null);
        ami_Materials = (EditText) view.findViewById(R.id.ami_materials);
        ami_Dosages = (EditText) view.findViewById(R.id.ami_dosages);
    }

    protected void initDatas(){
        mDatas = new ArrayList<>();
        mDatas.add(new Materials("用料：","用量："));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressedSupport();
                break;
            case R.id.action_add:
//                v = View.inflate(this, R.layout.item_add_materials, null);
                position++;
//                linearLayout.addView(v, position);
//                mDatas.add(new Materials("用料：","用量："));
                mAdapter.addData(position, new Materials(ami_Materials.getText().toString(),ami_Dosages.getText().toString()));


                break;
            case R.id.action_delete:
//                if (position > 1) {
//                    linearLayout.removeViewAt(position);
//                    position--;
//                }
                if (position > 0  ) {
                    mAdapter.deleteData(position);
                    position--;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        Intent intent = new Intent(AddMaterialsActivity.this, PublishActivity.class);
        startActivity(intent);
        AddMaterialsActivity.this.finish();
    }

    private void add(Materials m) {
        mDatas.add(m);
    }
}
