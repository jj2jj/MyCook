package sihuan.com.mycookassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-23.
 */

public class AddMatrialActivity extends BaseActivity {
    Toolbar mToolbar;
    ActionBar actionBar;
    LinearLayout linearLayout;
    View v;
    int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_matrial);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_add);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("添加用料");
        linearLayout = (LinearLayout) findViewById(R.id.add_activity);
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
                v = View.inflate(this, R.layout.item, null);
                position++;
                linearLayout.addView(v, position);
                break;
            case R.id.action_delete:
                if (position > 1) {
                    linearLayout.removeViewAt(position);
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
        Intent intent = new Intent(AddMatrialActivity.this, PublishActivity.class);
        startActivity(intent);
        AddMatrialActivity.this.finish();
    }
}
