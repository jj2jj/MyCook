package sihuan.com.mycookassistant.activity;


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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;

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



    /*
            mRegisterActivity = (RegisterActivity) mContext;
        mRegisterActivity.setSupportActionBar(mToolbar_Rgst);
        mRegisterActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegisterActivity.getSupportActionBar().setTitle(mContext.getString(R.string.register));


    @BindView(R.id.toolbar_login)
    Toolbar mToolbar_Rgst;

     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_view);
        mToolbar_publish = (Toolbar) findViewById(R.id.toolbar_pub);
        setSupportActionBar(mToolbar_publish);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("上传");
//

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
                uploadInfo();
            }
        });
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
        mProgerss.setVisibility(View.VISIBLE);
// TODO: 2016-11-09 实将下面代码抽成实体类  类似mydomain
        AVObject works = new AVObject("Works");
        works.put("title", mTitleEdit.getText().toString());
        works.put("step", mStepEdit.getText().toString());
        works.put("description", mDescribeEdit.getText().toString());
        works.put("owner", AVUser.getCurrentUser());
        Logger.d("mImageBytes: " + mImageBytes);
        works.put("image", new AVFile("workPic", mImageBytes));
        works.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mProgerss.setVisibility(View.GONE);
                    Toast.makeText(PublishActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    PublishActivity.this.finish();
                } else {
                    mProgerss.setVisibility(View.GONE);
                    Toast.makeText(PublishActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();//创建一个AlertDialog对象
        View view = getLayoutInflater().inflate(R.layout.picture_dialog, null);//自定义布局
        dialog.setView(view, 0, 0, 0, 0);//把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        dialog.show();//一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        int width = getWindowManager().getDefaultDisplay().getWidth();//得到当前显示设备的宽度，单位是像素
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();//得到这个dialog界面的参数对象
        params.width = width - (width / 6);//设置dialog的界面宽度
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//设置dialog高度为包裹内容
        params.gravity = Gravity.CENTER;//设置dialog的重心
        //dialog.getWindow().setLayout(width-(width/6),  LayoutParams.WRAP_CONTENT);//用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        dialog.getWindow().setAttributes(params);//最后把这个参数对象设置进去，即与dialog绑定
        dialogEvents(dialog, view);
    }

    private void dialogEvents(final AlertDialog dialog, View view) {
        Button takephotoBtn;
        Button choosephotoBtn;
        takephotoBtn = (Button) view.findViewById(R.id.takephotoBtn);
        takephotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                dialog.dismiss();
            }

        });

        choosephotoBtn = (Button) view.findViewById(R.id.choosephotoBtn);
        choosephotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //该常量让用户选择特定类型的数据，并返回该数据的URI.我们利用该常量，
                // 然后设置类型为“image/*”，就可获得Android手机内的所有image。
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PICTURE);
                dialog.dismiss();
            }
        });
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
