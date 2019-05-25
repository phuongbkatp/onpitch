package com.appian.footballnewsdaily.app.followsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.BaseFragment;
import com.appian.footballnewsdaily.app.ToolbarViewListener;
import com.appian.footballnewsdaily.app.adapter.AdapterViewPager;
import com.appian.footballnewsdaily.app.news.NewsFollowFragment;
import com.appian.footballnewsdaily.app.news.presenter.ListNewsPresenter;
import com.appian.footballnewsdaily.data.app.AppConfig;
import com.appian.footballnewsdaily.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import static com.appian.footballnewsdaily.Constant.KEY_SELECTED_LIST;
import static com.appian.footballnewsdaily.Constant.KEY_NAME_LIST;

public class HomeFollowFragment extends BaseFragment{
    private View mViewNoConnectivity;

    private ToolbarViewListener mToolbar;

    private AdapterViewPager mNewsAdapterViewPager;
    private List<String> mFollowingList;
    private List<String> mTeamNameList;
    private AppConfig appConfig;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig = AppConfig.getInstance();
        mFollowingList = appConfig.getArrayList(getActivity().getApplicationContext(), KEY_SELECTED_LIST);
        List<Fragment> fList = new ArrayList<>();
        for (String app_key : mFollowingList) {
            fList.add(NewsFollowFragment.newInstance(ListNewsPresenter.TYPE_APP, app_key));
        }
        mNewsAdapterViewPager = new AdapterViewPager(getChildFragmentManager(), fList);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarViewListener) {
            mToolbar = (ToolbarViewListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
    }

    private void initLayout(View view) {
        TabLayout tabLayout = view.findViewById(R.id.materialTabHost);
        mViewNoConnectivity = view.findViewById(R.id.view_no_internet_connection);
        ViewPager newsViewPager = view.findViewById(R.id.viewpager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Context context = getContext();
        newsViewPager.setAdapter(mNewsAdapterViewPager);
        newsViewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(newsViewPager);
        mTeamNameList = appConfig.getArrayList(getActivity().getApplicationContext(), KEY_NAME_LIST);
        int index = 0;
        for(String team_name : mTeamNameList) {
            tabLayout.getTabAt(index).setText(team_name);
            index++;
        }

        setTitle();
    }

    @Override
    protected int getLayout() {
        return R.layout.home_layout;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setTitle() {
        if (mToolbar != null) {
            mToolbar.changeToolbarTitle(getString(R.string.follow_tab));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkInternetConnection(NetworkHelper.isNetworkAvailable(getContext()));
    }

    private void checkInternetConnection(boolean isConnected) {
        if (mViewNoConnectivity == null) {
            return;
        }
        int visible = (isConnected) ? View.GONE : View.VISIBLE;
        mViewNoConnectivity.setVisibility(visible);
    }

}
