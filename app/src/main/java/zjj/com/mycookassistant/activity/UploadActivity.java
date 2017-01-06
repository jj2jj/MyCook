package zjj.com.mycookassistant.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class UploadActivity extends BaseActivity{
    Toolbar mToolbar_publish;
    ActionBar actionBar;
    ProgressDialog progress;

    private ImageView mImage;
    private static final String iconPath = Environment.getExternalStorageDirectory() + "/Image";//图片的存储目录
    private static final int CHOOSE_PICTURE = 22;
    public static final int TAKE_PHOTO = 11;

    private Uri imageUri;
    private byte[] mImageBytes = null;
    EditText mTitleEdit;
    EditText mDescribeEdit;
    EditText mDishesType;

    LinearLayoutManager layoutManager_addmaterial, layoutManager_addSteps;
    EditText itemFoodEdit, itemPortionEdit, itemStepsEdit;
    private ImageView itemStepsImag;
    private RecyclerView addMaterialRecyclerView, addStepsRecyclerView;
    private List<Materials> materialsList;
    private AddMaterialsAdapter addMaterialsAdapter;
    private List<Steps> stepsList;
    private AddStepsAdapter addStepsAdapter;
    int positionM = 0, positionS = 0;

    String str;//存放所选择的菜谱类别


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
        addMaterialRecyclerView.setLayoutManager(layoutManager_addmaterial);

        layoutManager_addSteps = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        addStepsRecyclerView.setLayoutManager(layoutManager_addSteps);

        addMaterialsAdapter = new AddMaterialsAdapter(materialsList, this);
        addMaterialRecyclerView.setAdapter(addMaterialsAdapter);

        addStepsAdapter = new AddStepsAdapter(stepsList, this);
        addStepsRecyclerView.setAdapter(addStepsAdapter);

        //设置增删动画
        addMaterialRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

        addMaterialRecyclerView = (RecyclerView) findViewById(R.id.rv_addM);
//        View view1 = LayoutInflater.from(this).inflate(R.layout.item_add_materials, null);
        View view1 = View.inflate(this, R.layout.item_add_materials, null);
        itemFoodEdit = (EditText) view1.findViewById(R.id.ami_materials);
        itemPortionEdit = (EditText) view1.findViewById(R.id.ami_dosages);

        addStepsRecyclerView = (RecyclerView) findViewById(R.id.rv_addS);
        View view2 = View.inflate(this, R.layout.item_add_steps, null);
//        View view2 = LayoutInflater.from(this).inflate(R.layout.item_add_steps, null);
        itemStepsEdit = (EditText) view2.findViewById(R.id.item_steps);
//        itemStepsImag = (ImageView) view2.findViewById(R.id.item_steps_image);

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

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        String[] choices = getResources().getStringArray(R.array.photo_choice);
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        fromCamera();
                        break;
                    case 1:
                        formGallery();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void formGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
        }
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private void fromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序
    }

    /**
     * 给拍的照片命名
     */
    public String createPhotoName() {
        //以系统的当前时间给图片命名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = format.format(date) + ".jpg";
        return fileName;
    }

    /**
     * 把拍的照片保存到SD卡
     */
    public void saveToSDCard(Bitmap bitmap) {
        //先要判断SD卡是否存在并且挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(iconPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(createPhotoName());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//把图片数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        mImageBytes = stream.toByteArray();
    }


    /**
     * 获取选择的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;//当data为空的时候，不做任何处理
        }
        Bitmap bitmap = null;
        if (requestCode == CHOOSE_PICTURE && resultCode == RESULT_OK) {
            //获取从相册界面返回的缩略图
            bitmap = data.getParcelableExtra("data");
            if (bitmap == null) {//如果返回的图片不够大，就不会执行缩略图的代码，因此需要判断是否为null,如果是小图，直接显示原图即可
                try {
                    //通过URI得到输入流
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    //通过输入流得到bitmap对象
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    getImageBytes(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            getImageBytes(bitmap);
            saveToSDCard(bitmap);
        }
        //将选择的图片设置到控件上
        mImage.setImageBitmap(bitmap);
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
        if (layoutManager_addSteps.hasFocus() && positionS > 0) {
            addStepsAdapter.deleteData(positionS);
            positionS--;
        } else if (layoutManager_addmaterial.hasFocus() && positionM > 0) {
            addMaterialsAdapter.deleteData(positionM);
            positionM--;
        }
    }

    private void addItems() {
        if (layoutManager_addmaterial.hasFocus()) {
            positionM++;
            addMaterialsAdapter.addData(positionM, new Materials(itemFoodEdit.getText().toString(), itemPortionEdit.getText().toString()));
        } else if (layoutManager_addSteps.hasFocus()) {
            positionS++;
            addStepsAdapter.addData(positionS, new Steps(itemStepsEdit.getText().toString()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pub, menu);
        return true;
    }

}
