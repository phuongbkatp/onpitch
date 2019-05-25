package com.appian.footballnewsdaily.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.followsetting.FollowSettingAdapter;
import com.appian.footballnewsdaily.app.setting.LanguageSettingAdapter;
import com.appian.footballnewsdaily.data.app.AppConfig;
import com.appian.footballnewsdaily.data.app.FollowItem;
import com.appian.footballnewsdaily.data.app.Language;
import com.appian.footballnewsdaily.data.app.LanguageSetting;
import com.appian.footballnewsdaily.service.app.AppHelper;

import java.util.ArrayList;
import java.util.List;

import static com.appian.footballnewsdaily.Constant.KEY_SELECTED_LIST;
import static com.appian.footballnewsdaily.Constant.KEY_NAME_LIST;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private LinearLayout.LayoutParams params;

    private FollowSettingAdapter mFollowSettingTeamAdapter;
    private LanguageSettingAdapter mLanguageSettingAdapter;

    private ListView mFollowTeamListView, mFollowLeagueListView, mFollowLanguageListView;
    private List<String> mSelectedTeamList = new ArrayList<>();
    private List<String> mTeamNameList = new ArrayList<>();

    private FollowSettingAdapter mFollowSettingLeagueAdapter;
    private List<String> mSelectedList = new ArrayList<>();
    private List<String> mLeagueNameList = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.onAttach(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Checking for first time launch - before calling setContentView()
        if (!AppHelper.isFirstTime(this)) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);


        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        params = new LinearLayout.LayoutParams(50, 50);
        params.setMargins(0, 0, 40, 0);
        btnNext.setText("");
        btnNext.setBackground(getResources().getDrawable(R.drawable.ic_next));
        btnNext.setLayoutParams(params);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.language_setting_layout,
                R.layout.follow_league_welcome,
                R.layout.follow_team_welcome};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
                AppConfig.getInstance().saveArrayList(getApplicationContext(), mSelectedTeamList, KEY_SELECTED_LIST);
                AppConfig.getInstance().saveArrayList(getApplicationContext(), mTeamNameList, KEY_NAME_LIST);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                    AppConfig.getInstance().saveArrayList(getApplicationContext(), mSelectedTeamList, KEY_SELECTED_LIST);
                    AppConfig.getInstance().saveArrayList(getApplicationContext(), mTeamNameList, KEY_NAME_LIST);
                }
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.globalWhite));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.array_dot_active));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        AppHelper.setIsFirstTime(this, false);
        startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btnNext.setBackground(null);
                btnNext.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setLayoutParams(params);
                btnNext.setText("");
                btnNext.setBackground(getResources().getDrawable(R.drawable.ic_next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            View view = layoutInflater.inflate(layouts[position], container, false);

            AppConfig appConfig = AppConfig.getInstance();
            mSelectedList = appConfig.getArrayList(getApplicationContext(), KEY_SELECTED_LIST);

            if (position == 0) {

                //setting league
                final List<LanguageSetting> mListLeague = appConfig.getLanguageList();
                mLanguageSettingAdapter = new LanguageSettingAdapter(getBaseContext(), mListLeague, mSelectedList );
                mFollowLanguageListView = (ListView) view.findViewById(R.id.follow_league_list);
                mFollowLanguageListView.setAdapter(mFollowSettingLeagueAdapter);
                mFollowLanguageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            } else if (position == 1) {

                //setting league
                final List<FollowItem> mListLeague = appConfig.getLeagueList();
                mFollowSettingLeagueAdapter = new FollowSettingAdapter(getBaseContext(), mListLeague, mSelectedList );
                mFollowLeagueListView = (ListView) view.findViewById(R.id.follow_league_list);
                mFollowLeagueListView.setAdapter(mFollowSettingLeagueAdapter);
                mFollowLeagueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ImageView mCheckView = view.findViewById(R.id.img_follow_check);
                        if (!mListLeague.get(position).isCheck()) {
                            mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_following));
                            mListLeague.get(position).setCheck(true);
                            mSelectedTeamList.add(mListLeague.get(position).getApp_key());
                            mTeamNameList.add(mListLeague.get(position).getName());
                        } else {
                            mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_follow));
                            mListLeague.get(position).setCheck(false);
                            mSelectedTeamList.remove(mListLeague.get(position).getApp_key());
                            mTeamNameList.remove(mListLeague.get(position).getName());

                        }
                    }
                });
            } else if (position == 2) {
                //setting team
                final List<FollowItem> mTeamItem = appConfig.getTeamList();
                mFollowSettingTeamAdapter = new FollowSettingAdapter(getBaseContext(), mTeamItem, mSelectedList);
                mFollowTeamListView = (ListView) view.findViewById(R.id.follow_team_list);
                mFollowTeamListView.setAdapter(mFollowSettingTeamAdapter);
                mFollowTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ImageView mCheckView = view.findViewById(R.id.img_follow_check);
                        if (!mTeamItem.get(position).isCheck()) {
                            mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_following));
                            mTeamItem.get(position).setCheck(true);
                            mSelectedTeamList.add(mTeamItem.get(position).getApp_key());
                            mTeamNameList.add(mTeamItem.get(position).getName());
                        } else {
                            mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_follow));
                            mTeamItem.get(position).setCheck(false);
                            mSelectedTeamList.remove(mTeamItem.get(position).getApp_key());
                            mTeamNameList.remove(mTeamItem.get(position).getName());

                        }
                    }
                });
            }

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}