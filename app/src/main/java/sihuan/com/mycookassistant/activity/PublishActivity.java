package sihuan.com.mycookassistant.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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

// TODO: 2016-11-07 测试能否上传成功

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-01.
 */
public class PublishActivity extends BaseActivity {
    private static final int CHOOSE_PICTURE = 3;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    private ImageView mImage;
    private Uri imageUri;
    private byte[] mImageBytes = null;
    private ProgressBar mProgerss;
    private Context mContext = null;
    EditText mTitleEdit;
    EditText mStepEdit;
    EditText mDescribeEdit;
    Button mSubmitBtn;
    PopupWindow popupWindow;
    File cameraFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_view);
        mContext = this;
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
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.photodialog, null);
                showPopupWindow(contentView);
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadInfo();
            }
        });
    }

    private void showPopupWindow(View view) {
        ListView listView = (ListView) view.findViewById(R.id.lv_menu);
        listView.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, new String[]{"拍照上传", "从本地图库选择"}));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
        // popupWindow.showAsDropDown(view);
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

        AVObject works = new AVObject("Works");
        works.put("title", mTitleEdit.getText().toString());
        works.put("step", mStepEdit.getText().toString());
        works.put("description", mDescribeEdit.getText().toString());
        works.put("owner", AVUser.getCurrentUser());
//// TODO: 2016-11-08  上传问题
        Logger.d("mImageBytes: "+ mImageBytes);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        popupWindow.dismiss();
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
