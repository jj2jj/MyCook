package sihuan.com.mycookassistant.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.common.base.Preconditions;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;
import sihuan.com.mycookassistant.activity.CookBookActivity;
import sihuan.com.mycookassistant.adapter.ContentPagerAdapter;
import sihuan.com.mycookassistant.base.RootView;
import sihuan.com.mycookassistant.fragment.Fragment2;
import sihuan.com.mycookassistant.fragment.HomePageFragment;
import sihuan.com.mycookassistant.presenter.contract.CookBookContract;
import sihuan.com.mycookassistant.widget.UnScrollViewPager;

/**
 * sihuan.com.mycookassistant
 * Created by sihuan on 2016/10/25.
 */

public class CookBookView extends RootView<CookBookContract.Presenter> implements CookBookContract.View, RadioGroup.OnCheckedChangeListener {

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;

    //   @BindView(R.id.main_content)
//    FrameLayout mainContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @BindView(R.id.main_page)
    RadioButton mainPage;

    @BindView(R.id.works)
    RadioButton works;

    @BindView(R.id.collect)
    RadioButton collect;

    @BindView(R.id.classification)
    RadioButton classification;

    @BindView(R.id.mine)
    RadioButton mine;

    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;

    ContentPagerAdapter mPagerAdapter;

    CookBookActivity mActivity;

    Drawer.Result drawerResult = null;

    public CookBookView(Context context) {
        super(context);
    }

    public CookBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CookBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.act_cook_book_view, this);
    }

    @Override
    protected void initView() {
        mActivity = (CookBookActivity) mContext;
        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(false);
        mPagerAdapter = new ContentPagerAdapter(mActivity.getSupportFragmentManager(), fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
        mActivity.setSupportActionBar(mToolbar);
//        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new Fragment2());
        fragments.add(new Fragment2());
        fragments.add(new Fragment2());
        fragments.add(new Fragment2());
        return fragments;
    }


    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(this);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) mRadioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void setPresenter(CookBookContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_page:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.works:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.collect:
                vpContent.setCurrentItem(2, false);
                break;
            case R.id.classification:
                vpContent.setCurrentItem(3, false);
                break;
            case R.id.mine:
                vpContent.setCurrentItem(3, false);
                break;
        }
    }


    public void initNavigationDrawer() {
        drawerResult = new Drawer()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        //打开 navigation Drawer隐藏键盘
                        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // 处理集合
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(mContext, mContext.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                // 如果有 "+"，不要这样处理
                                try {
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d("test", "Не нажимайте на бейдж, содержащий плюс! :)");
                                }
                            }
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // 事件处理
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(mContext, mContext.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();
    }
}
