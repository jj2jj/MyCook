package sihuan.com.mycookassistant.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;


/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-01.
 */

public class PublishActivity extends BaseActivity {
    private static final int FOOD_IMAGE = 42;

    private ImageView mImage;
    private byte[] mImageBytes = null;
    private ProgressBar mProgerss;
    EditText mTitleEdit;
    EditText mStepEdit;
    EditText mDescribeEdit;
    Button mSubmitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_view);

        findViews();


        initEvent();


    }

    private void initEvent() {
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 2016/11/4 跳出对话框，拍照上传，选择照片
                //该常量让用户选择特定类型的数据，并返回该数据的URI.我们利用该常量，
                // 然后设置类型为“image/*”，就可获得Android手机内的所有image。
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, FOOD_IMAGE);
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadInfo();
            }
        });
    }

    private void findViews() {
        mImage = (ImageView) findViewById(R.id.image_publish);
        mProgerss = (ProgressBar) findViewById(R.id.mProgess);
        mTitleEdit = (EditText) findViewById(R.id.title_publish);
        mStepEdit = (EditText) findViewById(R.id.step_publish);
        mDescribeEdit = (EditText) findViewById(R.id.description_publish);
        mSubmitBtn = (Button) findViewById(R.id.submit_publish);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOOD_IMAGE && resultCode == RESULT_OK) {
            Glide.with(this).load(data.getData()).into(mImage);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void uploadInfo() {
        if (TextUtils.isEmpty(mTitleEdit.getText().toString())) {
            Toast.makeText(PublishActivity.this, "请输入菜名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mDescribeEdit.getText().toString())) {
            Toast.makeText(PublishActivity.this, "请描述此菜", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mStepEdit.getText().toString())) {
            Toast.makeText(PublishActivity.this, "请输入步骤", Toast.LENGTH_SHORT).show();
            return;
        }
        //// TODO: 2016/11/4 看看是否还要留判断照片是默认图片
//                if (mImageBytes == null) {
//                    Toast.makeText(PublishActivity.this, "请选择一张照片", Toast.LENGTH_SHORT).show();
//                    return;
//                }

        mProgerss.setVisibility(View.VISIBLE);

        //presenter
        AVObject works = new AVObject("Works");
        works.put("title", mTitleEdit.getText().toString());
        works.put("step", mStepEdit.getText().toString());
        works.put("description", mDescribeEdit.getText().toString());
        works.put("owner", AVUser.getCurrentUser());
        works.put("image", new AVFile("workPic", mImageBytes));
        works.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mProgerss.setVisibility(View.GONE);
                    PublishActivity.this.finish();
                } else {
                    mProgerss.setVisibility(View.GONE);
                    Toast.makeText(PublishActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
