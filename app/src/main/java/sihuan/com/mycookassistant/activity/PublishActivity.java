package sihuan.com.mycookassistant.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;
import sihuan.com.mycookassistant.bean.Works;

// TODO: 2016-11-09  到27号之前完成此界面优化


/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-01.
 */
public class PublishActivity extends BaseActivity {
    Toolbar mToolbar_publish;
    ActionBar actionBar;

    private static final int CHOOSE_PICTURE = 3;
    public static final int TAKE_PHOTO = 1;
    private ImageView mImage;
    private Uri imageUri;
    private byte[] mImageBytes = null;
    private ProgressBar mProgerss;
    EditText mTitleEdit;
    EditText mStepEdit;
    EditText mDescribeEdit;
    Button mSubmitBtn;
    TextView mAddMaterial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_view);
        mToolbar_publish = (Toolbar) findViewById(R.id.toolbar_pub);
        setSupportActionBar(mToolbar_publish);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("上传");

        findViews();
        initEvent();
    }
    private void findViews() {
        mImage = (ImageView) findViewById(R.id.image_publish);
        mProgerss = (ProgressBar) findViewById(R.id.mProgess);
        mTitleEdit = (EditText) findViewById(R.id.title_publish);
        mStepEdit = (EditText) findViewById(R.id.step_publish);
        mDescribeEdit = (EditText) findViewById(R.id.description_publish);
        mSubmitBtn = (Button) findViewById(R.id.submit_publish);
        mAddMaterial= (TextView) findViewById(R.id.add_material);
    }
    private void initEvent() {
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    uploadInfo();
                } catch (AVException e) {
                    e.printStackTrace();
                }
            }
        });
        mAddMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016-11-23 添加用料部分 ，跳到另外一个Activity
                Intent intent = new Intent(PublishActivity.this,AddMatrialActivity.class);
                startActivity(intent);
            }
        });
    }

    private void uploadInfo() throws AVException {
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
        mProgerss.setVisibility(View.VISIBLE);


// TODO: 2016-11-09 实将下面代码抽成实体类  类似mydomain
        uploadData();
    }

    private void uploadData() {
        Works myWorks  = new Works("Works");
        myWorks.setTitle(mTitleEdit.getText().toString());
        myWorks.setStep(mStepEdit.getText().toString());
        myWorks.setDescription(mDescribeEdit.getText().toString());
        myWorks.setUser(AVUser.getCurrentUser());
        myWorks.setImage( new AVFile("workPic", mImageBytes));
        myWorks.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    mProgerss.setVisibility(View.GONE);
                    Toast.makeText(PublishActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    PublishActivity.this.finish();
                }else {
                    mProgerss.setVisibility(View.GONE);
                    Toast.makeText(PublishActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        String[] choices = getResources().getStringArray(R.array.photo_choice);
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        takePhoto();
                        break;
                    case 1:
                        //该常量让用户选择特定类型的数据，并返回该数据的URI.我们利用该常量，
                        // 然后设置类型为“image/*”，就可获得Android手机内的所有image。
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, CHOOSE_PICTURE);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takePhoto() {
        // 创建File对象，用于存储拍照后的图片,
        // 存放在手机SD卡的根目录下，调用Environment的getExternalStorageDirectory()方法获取到的就是手机 SD 卡的根目录。
        File outputImage = new File(Environment.getExternalStorageDirectory(), "workPic");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);//再调用 Uri.fromFile()方法将 File 对象转换成 Uri对象
        //这个 Uri对象标识着 output_image.jpg 这张图片 的唯一地址
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //imageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PICTURE && resultCode == RESULT_OK) {
            Glide.with(this).load(data.getData()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    //上传操作
                    mImage.setImageBitmap(resource);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    mImageBytes = stream.toByteArray();
                }
            });
        } else if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                mImageBytes = stream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mImage.setImageBitmap(bitmap);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressedSupport();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pub, menu);
        return true;
    }
}
