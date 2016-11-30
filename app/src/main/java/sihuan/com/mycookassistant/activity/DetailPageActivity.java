package sihuan.com.mycookassistant.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-28.
 */

public class DetailPageActivity  extends BaseActivity{
    Toolbar mToolbar;
    ActionBar actionBar;

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView author;
    private TextView material;
    private TextView steps;
    private ToggleButton star_btn;
    private ToggleButton play_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("详情");

        findViews();

        /*
         AVQuery<AVObject> avQuery = new AVQuery<>("Todo");
        avQuery.getInBackground("558e20cbe4b060308e3eb36c", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                // object 就是 id 为 558e20cbe4b060308e3eb36c 的 Todo 对象实例
            }
        });
         */
        getItemData();
        onClickEvents();
    }

    /**
     * getItemData()
     * 获取对应的item的data，将其放入TextView material & TextView steps中显示
     */
    private void getItemData() {
        String mObjectId = getIntent().getStringExtra("itemObjectId");
        AVQuery<AVObject> avQuery = new AVQuery<>("Works");
        avQuery.getInBackground(mObjectId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                Glide.with(DetailPageActivity.this).load(avObject.getAVFile("image") == null
                        ? "www" : avObject.getAVFile("image").getUrl()).into(image);

                title.setText(avObject.getString("title"));
                description.setText(avObject.getString("description"));
                author.setText(avObject.getAVUser("owner") == null ? "" : avObject.getAVUser("owner").getUsername());

                String m = avObject.getList("materials").toString();
                m = m.replace("[","");
                m = m.replace("]","");
                m=m.replace("food=","");
                m=m.replace("portion=","");
                m=m.replace("{","\t\t\t\t\t\t");
                m=m.replace("},","\n");
                m=m.replace("}","");
                m=m.replace(",","\t\t\t\t\t\t\t\t\t\t");
                material.setText(m);
                String s = avObject.getList("steps").toString();
                s=s.replace("steps=","");
                s=s.replace("[","");
                s=s.replace("]","");
                s=s.replace("{","");
                s=s.replace("}","");
                s=s.replace(",","\n");
                steps.setText(s);
            }
        });
    }

    /**
     * onClickEvents()
     * ToggleButton 点击事件监听
     */
    private void onClickEvents() {
        star_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cookBookStared(b);
            }
        });

        play_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cookBookPlayed(b);
            }
        });
    }

    /**
     * cookBookStared(boolean isStared)
     * @param isStared
     * 判断是都点击收藏按钮
     */
    private void cookBookStared(boolean isStared) {
        if (isStared){
            star_btn.setBackgroundResource(R.drawable.star_sel);
        }else {
            star_btn.setBackgroundResource(R.drawable.star_nor);
        }
    }

    /**
     *  cookBookPlayed(boolean isPlayed)
     * @param isPlayed
     * 是否点击播放按钮，控制语音播报处
     */
    private void cookBookPlayed(boolean isPlayed) {
        if (isPlayed){
            play_btn.setBackgroundResource(R.drawable.play_sel);
        }else {
            play_btn.setBackgroundResource(R.drawable.play_nor);
        }
    }

    private void findViews() {
        image = (ImageView) findViewById(R.id.image_detail);
        title = (TextView) findViewById(R.id.title_detail);
        description = (TextView) findViewById(R.id.description_detail);
        author = (TextView) findViewById(R.id.author_detail);
        material = (TextView) findViewById(R.id.material_detail);
        steps = (TextView) findViewById(R.id.steps_detail);
        star_btn = (ToggleButton) findViewById(R.id.star_detail);
        play_btn = (ToggleButton) findViewById(R.id.play_detail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressedSupport();
        }
        return super.onOptionsItemSelected(item);
    }
}
