package zjj.com.mycookassistant.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.base.BaseActivity;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-28.
 */

public class DetailPageActivity extends BaseActivity {
    //    Toolbar mToolbar;
    //    ActionBar actionBar;
    private ImageView image;
    private TextView title;
    private TextView describe;
    private TextView author;
    private TextView material;
    private TextView tvSteps;
    private TextView dish_type;
    private ImageView star_btn;
    private ImageView play_btn;
    private String mObjectId;
    private int flag;
    private String steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);

        mObjectId = getIntent().getStringExtra("itemObjectId");

        findViews();
        onClickEvents();
        initItemData();

    }

    /**
     * onClickEvents()
     */
    private void onClickEvents() {
        flag = 0;
        star_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    //添加收藏
                    createCollection(true);
                    star_btn.setBackgroundResource(R.drawable.star_sel);

                } else if (flag == 1) {
                    //取消收藏
                    deleteCollection();
                    star_btn.setBackgroundResource(R.drawable.star_nor);
                }
                flag = (flag + 1) % 2;//其余得到循环执行上面3个不同的功能
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String steps = tvSteps.getText().toString();
                if (!TextUtils.isEmpty(steps)) {
//                    RobotManager.getInstance().startSpeaking(steps);
                    // TODO: 2017/4/5 加入语音库直接播放语音
                }
            }
        });
    }

    /**
     * 获取对应的item的data，将其放入TextView material & TextView steps中显示
     * initItemData()
     */
    private void initItemData() {
        AVObject avObject = AVObject.createWithoutData("Works", mObjectId);
        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                Glide.with(DetailPageActivity.this)
                        .load(avObject.getAVFile("image") == null ? "www" : avObject
                                .getAVFile("image")
                                .getUrl())
                        .into(image);

                ActionBar actionBar = getSupportActionBar();
                assert actionBar != null;
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(avObject == null ? "详情" : avObject.getString("title"));
//                title.setText(avObject.getString("title"));//菜名
                describe.setText(avObject.getString("describe"));//菜品描述
//                dish_type.setText(avObject.getString("dishestype"));
//                author.setText(avObject
//                        .getAVUser("owner") == null ? "" : avObject
//                        .getAVUser("owner")
//                        .getUsername());//作者名

                String m = avObject.getList("materials").toString();
                m = m.replace("[", "");
                m = m.replace("]", "");
                m = m.replace("food=", "");
                m = m.replace("portion=", "");
                m = m.replace("{", "\t\t\t\t\t\t\t\t");
                m = m.replace("},", "\n");
                m = m.replace("}", "");
                m = m.replace(",", "\t\t\t\t\t\t\t\t\t\t");
                material.setText(m);

                steps = avObject.getList("steps").toString();
                steps = steps.replace("steps=", "");
                steps = steps.replace("[", "");
                steps = steps.replace("]", "");
                steps = steps.replace("{", "");
                steps = steps.replace("}", "");
                steps = steps.replace(",", "\n");
                tvSteps.setText(steps);
            }
        });
    }

    /**
     * 取消收藏,删除collection数据表中的内容
     */
    private void deleteCollection() {
        AVQuery<AVObject> worksIdQuery = new AVQuery<>("Collections");
        worksIdQuery.whereEqualTo("worksObjectId", AVObject.createWithoutData("Works", mObjectId));

        AVQuery<AVObject> collectorQuery = new AVQuery<>("Collections");
        collectorQuery.whereEqualTo("collector", AVUser.getCurrentUser());

        AVQuery<AVObject> collectionQuery = AVQuery.and(Arrays.asList(worksIdQuery, collectorQuery));
        collectionQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).deleteInBackground();
                    }
                }
                Toast.makeText(DetailPageActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加收藏
     *
     * @param isStared 是否被添加收藏
     */
    private void createCollection(final boolean isStared) {
        AVObject myCollections = new AVObject("Collections");
        myCollections.put("isStared", isStared);
        myCollections.put("worksObjectId", AVObject.createWithoutData("Works", mObjectId));
        myCollections.put("collector", AVUser.getCurrentUser());
        myCollections.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                Toast.makeText(DetailPageActivity.this, "收藏成功^_^", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * cookBookPlayed(boolean isPlayed)
     *
     * @param isPlayed 是否点击播放按钮，控制语音播报处
     */
    private void cookBookPlayed(boolean isPlayed) {
        if (isPlayed) {
            play_btn.setBackgroundResource(R.drawable.play_sel);
        } else {
            play_btn.setBackgroundResource(R.drawable.play_nor);
        }
    }


    private void findViews() {
//        dish_type = (TextView) findViewById(R.id.dish_type);
        image = (ImageView) findViewById(R.id.image_detail);
//        title = (TextView) findViewById(R.id.title_detail);
        describe = (TextView) findViewById(R.id.description_detail);
//        author = (TextView) findViewById(R.id.author_detail);
        material = (TextView) findViewById(R.id.material_detail);
        tvSteps = (TextView) findViewById(R.id.steps_detail);
        star_btn = (ImageView) findViewById(R.id.star_detail);
        play_btn = (ImageView) findViewById(R.id.play_detail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressedSupport();
        }
        return super.onOptionsItemSelected(item);
    }
}
