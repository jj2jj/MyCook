package sihuan.com.mycookassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.base.BaseActivity;
import sihuan.com.mycookassistant.presenter.CookBookPresenter;
import sihuan.com.mycookassistant.view.CookBookView;

public class CookBookActivity extends BaseActivity {
    MaterialSearchView searchView;

    @BindView(R.id.cook_book_view)
    CookBookView mView;
    CookBookPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new CookBookPresenter(mView);
        searchView = mView.getSearchView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){
                startActivity(new Intent(CookBookActivity.this, PublishActivity.class));
        }else if ((item.getItemId() == R.id.action_search)){
            //
        }

        return super.onOptionsItemSelected(item);
    }
//
    @Override
    public void onBackPressedSupport() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
    }
}
