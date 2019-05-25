package com.appian.footballnewsdaily.app.league;

import android.content.Context;
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
import com.appian.footballnewsdaily.app.fixture.FixtureFragment;
import com.appian.footballnewsdaily.app.fixture.MatchDayFragment;
import com.appian.footballnewsdaily.app.table.TableFragment;
import com.appian.footballnewsdaily.data.app.AppConfig;
import com.appian.footballnewsdaily.util.Utils;
import com.appnet.android.ads.OnAdLoadListener;
import com.appnet.android.ads.admob.BannerAdMob;

import java.util.List;

import static com.appian.footballnewsdaily.Constant.KEY_SELECTED_LIST;

public class LeagueFragment extends BaseStateFragment {
    private ViewGroup mAdViewContainer;

    private MatchPagerAdapter adapter;
    private ToolbarViewListener mToolBar;

    private BannerAdMob mBannerAdMob;

    private int mLeagueId;
    private int mSeasonId;
    private String mLeagueName;
    private int mTeamId;
    private List<String> mFollowingList;
    private List<String> mTeamNameList;
    private AppConfig appConfig;

    public static LeagueFragment newInstance(Bundle args) {
        LeagueFragment fragment = new LeagueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        adapter = new MatchPagerAdapter(fm);
        appConfig = AppConfig.getInstance();
        mFollowingList = appConfig.getArrayList(getActivity().getApplicationContext(), KEY_SELECTED_LIST);

        Bundle args = getArguments();
        if (args != null) {
            mLeagueId = args.getInt("league_id");
            mSeasonId = args.getInt("season_id");
            mLeagueName = args.getString("league_name", "");
        }

    }


    public void updateLeagueSeason(Bundle args) {
        if (args != null) {
            mLeagueId = args.getInt("league_id");
            mSeasonId = args.getInt("season_id");
            mLeagueName = args.getString("league_name", "");
        }
        updateTitle();
        List<Fragment> fragments = adapter.getStateFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof OnLeagueUpdatedListener) {
                ((OnLeagueUpdatedListener) fragment).onLeagueUpdated(appConfig.getLeagueIdFromKey(mFollowingList.get(i)), mSeasonId);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        /*mBannerAdMob.addView(mAdViewContainer);
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
        mBannerAdMob.loadAd();*/
    }

    private void initView(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_match);
        ViewPager viewPager = view.findViewById(R.id.view_pager_match);
        mAdViewContainer = view.findViewById(R.id.admob_banner_container);
        Context context = getContext();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
/*        View view0 = View.inflate(context, R.layout.custom_tab_layout, null);
        view0.findViewById(R.id.icon_tab).setBackgroundResource(R.drawable.calendar);
        TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        if(tab0 != null) {
            tab0.setCustomView(view0);
        }
        View view1 = View.inflate(context, R.layout.custom_tab_layout, null);
        view1.findViewById(R.id.icon_tab).setBackgroundResource(R.drawable.award);
        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        if(tab1 != null) {
            tab1.setCustomView(view1);
        }
        View view2 = View.inflate(context, R.layout.custom_tab_layout, null);
        view2.findViewById(R.id.icon_tab).setBackgroundResource(R.drawable.list);
        TabLayout.Tab tab2 = tabLayout.getTabAt(2);
        if(tab2 != null) {
            tab2.setCustomView(view2);
        }*/

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        updateTitle();
    }


    @Override
    public void onResume() {
        super.onResume();
        List<Fragment> fragments = adapter.getStateFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof OnLeagueUpdatedListener) {
                ((OnLeagueUpdatedListener) fragment).onLeagueUpdated(appConfig.getLeagueIdFromKey(mFollowingList.get(i)), mSeasonId);
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.league_fragment;
    }

    private class MatchPagerAdapter extends BaseStateFragmentPagerAdapter {

        MatchPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return MatchDayFragment.newInstance(appConfig.getLeagueIdFromKey(mFollowingList.get(position)), mSeasonId, this);

        }


        @Override
        public int getCount() {
            return mFollowingList.size();
        }

        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarViewListener) {
            mToolBar = (ToolbarViewListener) context;
        }
        AppConfig appConfig = AppConfig.getInstance();
        mTeamId = appConfig.getTeamId(context);
/*        mBannerAdMob = new BannerAdMob(context, appConfig.getAdbMobMatchDetail(context));
        Utils.addAdmobTestDevice(mBannerAdMob);*/
    }

    private void updateTitle() {
        if (mToolBar != null) {
            mToolBar.changeToolbarTitle(mLeagueName);
        }
    }

}
