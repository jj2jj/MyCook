package sihuan.com.mycookassistant.activity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sihuan.com.mycookassistant.R;


/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-01.
 */

public class PublishActivity extends AppCompatActivity {

    private ImageView mImage;
    private byte[] mImageBytes = null;
    private ProgressBar mProgerss;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_view);

        mImage = (ImageView) findViewById(R.id.image_publish);
        mProgerss = (ProgressBar) findViewById(R.id.mProgess);

        final EditText mTitleEdit = (EditText) findViewById(R.id.title_publish);
        final EditText mStepEdit = (EditText) findViewById(R.id.step_publish);
        final EditText mDescribeEdit = (EditText) findViewById(R.id.description_publish);


        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //该常量让用户选择特定类型的数据，并返回该数据的URI.我们利用该常量，
                // 然后设置类型为“image/*”，就可获得Android手机内的所有image。
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
            }
        });


        Button mSubmitBtn = (Button) findViewById(R.id.submit_publish);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mTitleEdit.getText().toString())) {
                    Toast.makeText(PublishActivity.this, "请输入菜名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(mDescribeEdit.getText().toString())) {
                    Toast.makeText(PublishActivity.this, "请描述此菜", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(mStepEdit.getText().toString())) {
                    Toast.makeText(PublishActivity.this, "请输入步骤", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mImageBytes == null) {
                    Toast.makeText(PublishActivity.this, "请选择一张照片", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgerss.setVisibility(View.VISIBLE);

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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            try {
                mImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                mImageBytes = getBytes(getContentResolver().openInputStream(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
