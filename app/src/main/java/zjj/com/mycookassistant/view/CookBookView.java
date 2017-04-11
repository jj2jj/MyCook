package zjj.com.mycookassistant.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.avos.avoscloud.AVUser;
import com.google.common.base.Preconditions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.activity.CookBookActivity;
import zjj.com.mycookassistant.activity.LoginActivity;
import zjj.com.mycookassistant.activity.SearchActivity;
import zjj.com.mycookassistant.activity.UploadActivity;
import zjj.com.mycookassistant.adapter.ContentPagerAdapter;
import zjj.com.mycookassistant.app.Config;
import zjj.com.mycookassistant.base.RootView;
import zjj.com.mycookassistant.fragment.ClassificationFragment;
import zjj.com.mycookassistant.fragment.HomePageFragment;
import zjj.com.mycookassistant.fragment.MyCollectionFragment;
import zjj.com.mycookassistant.fragment.MyProductFragment;
import zjj.com.mycookassistant.presenter.contract.CookBookContract;
import zjj.com.mycookassistant.utils.PreUtils;
import zjj.com.mycookassistant.widget.UnScrollViewPager;

/**
 * sihuan.com.mycookassistant
 * Created by sihuan on 2016/10/25.
 */

public class CookBookView extends RootView<CookBookContract.Presenter> implements CookBookContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;


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

    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;
//
//    @BindView(R.id.spaceTabLayout)
//    SpaceTabLayout tabLayout;
//
//
//    @BindView(R.id.viewPager)
//    ViewPager viewPager;

    ContentPagerAdapter mPagerAdapter;

    CookBookActivity mActivity;


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
//        savedInstanceState = mActivity.getSavedInstanceState();
//        tabLayout.initialize(viewPager, mActivity.getSupportFragmentManager(), fragments,savedInstanceState );
        vpContent.setScrollable(true);
        mPagerAdapter = new ContentPagerAdapter(mActivity.getSupportFragmentManager(), fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
        mActivity.setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.homePage);


    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new MyProductFragment());
        fragments.add(new MyCollectionFragment());
        fragments.add(new ClassificationFragment());
        return fragments;
    }

    public MaterialSearchView getSearchView() {
        return searchView;
    }
//
//    public SpaceTabLayout getTabLayout() {
//        return tabLayout;
//    }
//
//    public ViewPager getViewPager() {
//        return viewPager;
//    }


    @Override
    protected void initEvent() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 搜索功能，点击跳转到SearchActivity
                Intent intent = new Intent(mActivity, SearchActivity.class);
                intent.putExtra("search_result", query);
                mActivity.startActivity(intent);
//                Snackbar.make(findViewById(R.id.container), "Query: " + query,
//                        Snackbar.LENGTH_LONG)
//                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
//
            }

            @Override
            public void onSearchViewClosed() {
//
            }
        });
        mRadioGroup.setOnCheckedChangeListener(this);

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                mToolbar.setTitle(R.string.homePage);
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.works:
                mToolbar.setTitle(R.string.worksPage);
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.collect:
                mToolbar.setTitle(R.string.collectionPage);
                vpContent.setCurrentItem(2, false);
                break;
            case R.id.classification:
                mToolbar.setTitle(R.string.classificationPage);
                vpContent.setCurrentItem(3, false);
                break;
        }
    }

    Drawer drawer = null;


    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void initNavigationDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(mActivity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(AVUser.getCurrentUser().getUsername()).withEmail("jjzjj@163.com")
                                .withIcon(getResources().getDrawable(R.drawable.taylor))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //首页
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_home)
                .withIcon(FontAwesome.Icon.faw_home)
                .withIdentifier(1);

        //我的信息
        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_self)
                .withIcon(FontAwesome.Icon.faw_user)
                .withIdentifier(2);

        //添加菜谱
        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_add_product)
                .withIcon(FontAwesome.Icon.faw_plus_circle)
                .withIdentifier(3);

        SecondaryDrawerItem item4 = new SecondaryDrawerItem()
                .withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog)
                .withIdentifier(4);

        SecondaryDrawerItem item5 = new SecondaryDrawerItem()
                .withName(R.string.drawer_item_open_source)
                .withIcon(FontAwesome.Icon.faw_question)
                .withIdentifier(5);

        //退出当前账户
        SecondaryDrawerItem item6 = new SecondaryDrawerItem()
                .withName(R.string.drawer_item_logout)
                .withIcon(FontAwesome.Icon.faw_sign_out)
                .withIdentifier(6);

        drawer = new DrawerBuilder()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        item4,
                        item5,
                        new DividerDrawerItem(),
                        item6
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item_add_materials :D
                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                mToolbar.setTitle(R.string.homePage);
                                vpContent.setCurrentItem(0, false);
                                break;
                            case 2:
                                showUserDialog();
                                break;
                            case 3:
                                mActivity.startActivity(new Intent(mContext, UploadActivity.class));
                                break;
                            case 4:
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                mActivity.startActivity(intent);
                                break;
                            case 5:
                                break;
                            case 6:
                                //注销当前账户，返回登录界面
                                AVUser.logOut();
                                PreUtils.removeAll(mActivity);
                                // startActivity(new Intent(CookBookActivity.this, LoginActivity.class));
                                mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
                                mActivity.finish();

                                break;
                        }

                        return false;
                    }
                })
                .build();
    }

    private void showUserDialog() {
        String username = PreUtils.getString(mActivity, Config.USERNAME, null);
        new AlertDialog.Builder(mContext).setTitle(R.string.drawer_item_self)
                .setMessage("姓名：" + username + "\n"
                        + "性别：女" + "\n"
                        + "邮箱：jjzjj@163.com"
                )
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

//    @Override
//    public List<Fragment> getFragments() {
//        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new HomePageFragment());
//        fragmentList.add(new MyProductFragment());
//        fragmentList.add(new MyCollectionFragment());
//        fragmentList.add(new ClassificationFragment());
////        fragmentList.add(new FragmentE());
//        return fragmentList;
//    }
}