package zjj.com.mycookassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.Drawer;

import butterknife.BindView;
import butterknife.ButterKnife;
import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.base.BaseActivity;
import zjj.com.mycookassistant.presenter.CookBookPresenter;
import zjj.com.mycookassistant.view.CookBookView;

public class CookBookActivity extends BaseActivity {
    MaterialSearchView searchView;
    Drawer drawer;
    CookBookPresenter mPresenter;

    @BindView(R.id.cook_book_view)
    CookBookView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        mUnbinder = ButterKnife.bind(this);//绑定view

        mPresenter = new CookBookPresenter(mView);
        searchView = mView.getSearchView();
        drawer = mView.getDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_add) {
//            startActivity(new Intent(CookBookActivity.this, UploadActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 返回键作用于search view
     */
    @Override
    public void onBackPressedSupport() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
        drawer.closeDrawer();//返回键 close drawer
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再点一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
