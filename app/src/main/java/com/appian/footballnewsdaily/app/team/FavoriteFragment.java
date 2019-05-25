package com.appian.footballnewsdaily.app.team;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.BaseStateFragment;
import com.appian.footballnewsdaily.app.BaseStateFragmentPagerAdapter;
import com.appian.footballnewsdaily.app.ToolbarViewListener;
import com.appian.footballnewsdaily.app.news.NewsFragment;
import com.appian.footballnewsdaily.app.news.presenter.ListNewsPresenter;
import com.appian.footballnewsdaily.util.Utils;
import com.appnet.android.ads.OnAdLoadListener;
import com.appnet.android.ads.admob.BannerAdMob;

public class FavoriteFragment extends BaseStateFragment {
    private NewsPagerAdapter mAdapter;
    private ToolbarViewListener mToolBar;

    private int mTeamId = 0;

    private ViewGroup mAdViewContainer;
    private BannerAdMob mBannerAdMob;

    public static FavoriteFragment newInstance(int teamId) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt("team_id", teamId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mAdapter = new NewsPagerAdapter(fm);
        if(getArguments() != null) {
            mTeamId = getArguments().getInt("team_id");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_team);
        ViewPager viewPager = view.findViewById(R.id.view_pager_team);
        Resources res = getResources();
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        if(tab0 != null) {
            tab0.setText(res.getString(R.string.news_tab));
        }
        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        if(tab1 != null) {
            tab1.setText(res.getString(R.string.info));
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        updateTitle();
        //initAds(view);
    }

    private void initAds(View view) {
        Context context = getContext();
        if(context == null) {
            return;
        }
        mAdViewContainer = view.findViewById(R.id.admob_banner_container);
        mBannerAdMob.addView(mAdViewContainer);
        mBannerAdMob.setOnLoadListener(new OnAdLoadListener() {
            @Override
            public void onAdLoaded() {
                mAdViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailed() {
                mAdViewContainer.setVisibility(View.GONE);
            }
        });
        mBannerAdMob.loadAd();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayout() {
        return R.layout.team_fragment;
    }

    private class NewsPagerAdapter extends BaseStateFragmentPagerAdapter {

        NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsFragment.newInstance(ListNewsPresenter.TYPE_APP);
                case 1:
                    return new SquadFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ToolbarViewListener) {
            mToolBar = (ToolbarViewListener) context;
        }
        mBannerAdMob = new BannerAdMob(context, context.getString(R.string.admob_match_detail_banner));
        Utils.addAdmobTestDevice(mBannerAdMob);
    }

    private void updateTitle() {
        if(mToolBar != null) {
            mToolBar.changeToolbarTitle(getResources().getString(R.string.red_devil_menu));
        }
    }
}
