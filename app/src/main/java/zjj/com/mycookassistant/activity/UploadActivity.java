package zjj.com.mycookassistant.activity;


import android.app.ProgressDialog;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.adapter.AddMaterialsAdapter;
import zjj.com.mycookassistant.adapter.AddStepsAdapter;
import zjj.com.mycookassistant.base.BaseActivity;
import zjj.com.mycookassistant.bean.Materials;
import zjj.com.mycookassistant.bean.Steps;
import zjj.com.mycookassistant.bean.Works;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-01.
 */
public class UploadActivity extends BaseActivity {
    Toolbar mToolbar_publish;
    ActionBar actionBar;
    ProgressDialog progress;


    private static final int CHOOSE_PICTURE = 3;
    public static final int TAKE_PHOTO = 1;
    private ImageView mImage;
    private Uri imageUri;
    private byte[] mImageBytes = null;
    EditText mTitleEdit;
    EditText mDescribeEdit;
    EditText mDishesType;

    LinearLayoutManager layoutManager_addmaterial, layoutManager_addSteps;
    EditText ami_Food, ami_Portion, asi_Steps;
    private RecyclerView mRv_addmaterial, mRv_addSteps;
    private List<Materials> materialsList;
    private AddMaterialsAdapter addMaterialsAdapter;
    private List<Steps> stepsList;
    private AddStepsAdapter addStepsAdapter;
    int position_m = 0, position_s = 0;


    String str;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_view);

        mToolbar_publish = (Toolbar) findViewById(R.id.toolbar_pub);
        setSupportActionBar(mToolbar_publish);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("上传");

        initDatas();
        findViews();
        initEvent();
        //recyclerview的布局管理
        layoutManager_addmaterial = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv_addmaterial.setLayoutManager(layoutManager_addmaterial);

        layoutManager_addSteps = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv_addSteps.setLayoutManager(layoutManager_addSteps);

        addMaterialsAdapter = new AddMaterialsAdapter(materialsList, this);
        mRv_addmaterial.setAdapter(addMaterialsAdapter);

        addStepsAdapter = new AddStepsAdapter(stepsList, this);
        mRv_addSteps.setAdapter(addStepsAdapter);

        //设置增删动画
        mRv_addmaterial.setItemAnimator(new DefaultItemAnimator());
        mRv_addSteps.setItemAnimator(new DefaultItemAnimator());
    }

    private void initDatas() {
        materialsList = new ArrayList<>();
        materialsList.add(new Materials("用料：", "用量："));

        stepsList = new ArrayList<>();
        stepsList.add(new Steps("步骤："));
    }

    private void findViews() {
        mDishesType = (EditText) findViewById(R.id.type_upload);
        mImage = (ImageView) findViewById(R.id.image_upload);
        mTitleEdit = (EditText) findViewById(R.id.title_upload);
        mDescribeEdit = (EditText) findViewById(R.id.describe_upload);

        mRv_addmaterial = (RecyclerView) findViewById(R.id.rv_addM);
//        View view1 = LayoutInflater.from(this).inflate(R.layout.item_add_materials, null);
         View view1 = View.inflate(this,R.layout.item_add_materials, null);
        ami_Food = (EditText) view1.findViewById(R.id.ami_materials);
        ami_Portion = (EditText) view1.findViewById(R.id.ami_dosages);

        mRv_addSteps = (RecyclerView) findViewById(R.id.rv_addS);
        View view2 =View.inflate(this,R.layout.item_add_steps, null);
//        View view2 = LayoutInflater.from(this).inflate(R.layout.item_add_steps, null);
        asi_Steps = (EditText) view2.findViewById(R.id.asi_steps);
    }

    private void initEvent() {
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mDishesType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDishType();
            }
        });

    }

    private void showDishType() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("请选择");
        final String[] choices = getResources().getStringArray(R.array.dishesType);
        builder2.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //拿到被选择项的值
                str = choices[i];
                //把该值传给 TextView
                mDishesType.setText(str);
            }
        });
        AlertDialog dialog2 = builder2.create();
        dialog2.show();
    }

    private void uploadInfo() throws AVException {
        if (TextUtils.isEmpty(mTitleEdit.getText().toString())) {
            Toast.makeText(UploadActivity.this, "请输入菜名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mDescribeEdit.getText().toString())) {
            Toast.makeText(UploadActivity.this, "请描述此菜", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog();
        uploadData();
    }

    private void showProgressDialog() {
        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("上传中，请等待~");
        //设置ProgressDialog 的进度条是否不明确
        progress.setIndeterminate(false);
//设置ProgressDialog 是否可以按退回按键取消
        progress.setCancelable(true);
//显示
        progress.show();
    }

    private void uploadData() {
        final Works myWorks = new Works("Works");
        myWorks.setDishestype(mDishesType.getText().toString());
        myWorks.setTitle(mTitleEdit.getText().toString());
        myWorks.setSteps(stepsList);
        myWorks.setMaterials(materialsList);
        myWorks.setDescribe(mDescribeEdit.getText().toString());
        myWorks.setUser(AVUser.getCurrentUser());
        myWorks.setImage(new AVFile("workPic", mImageBytes));
        myWorks.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    progress.dismiss();
                    Toast.makeText(UploadActivity.this, "上传成功^_^", Toast.LENGTH_SHORT).show();
                    UploadActivity.this.finish();
                } else {
                    progress.dismiss();
                    Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(UploadActivity.this, "上传失败(′⌒`)", Toast.LENGTH_SHORT).show();
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
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressedSupport();
                break;
            case R.id.action_add:
                addItems();
                break;
            case R.id.action_delete:
                deleteItems();
                break;
            case R.id.action_submit:
                try {
                    uploadInfo();
                } catch (AVException e) {
                    e.printStackTrace();
                }

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteItems() {
        if (layoutManager_addSteps.hasFocus() && position_s > 0) {
            addStepsAdapter.deleteData(position_s);
            position_s--;
        } else if (layoutManager_addmaterial.hasFocus() && position_m > 0) {
            addMaterialsAdapter.deleteData(position_m);
            position_m--;
        }
    }

    private void addItems() {
        if (layoutManager_addmaterial.hasFocus()) {
            position_m++;
            addMaterialsAdapter.addData(position_m, new Materials(ami_Food.getText().toString(), ami_Portion.getText().toString()));
        } else if (layoutManager_addSteps.hasFocus()) {
            position_s++;
            addStepsAdapter.addData(position_s, new Steps(asi_Steps.getText().toString()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pub, menu);
        return true;
    }

}
