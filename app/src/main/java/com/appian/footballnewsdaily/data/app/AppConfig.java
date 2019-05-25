package com.appian.footballnewsdaily.data.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.appian.footballnewsdaily.BuildConfig;
import com.appian.footballnewsdaily.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    private static final String TEST_ADMOB_BANNER = "ca-app-pub-3940256099942544/2934735716";
    private static final String TEST_ADMOB_INTERSTITIAL = "ca-app-pub-3940256099942544/4411468910";

    private RemoteConfigData mConfigData;

    private static AppConfig sInstance;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (sInstance == null) {
            sInstance = new AppConfig();
        }
        return sInstance;
    }

    public int getTeamId(Context context) {
        return context.getResources().getInteger(R.integer.team_id);
    }

    public String getAppKey() {
        return BuildConfig.FLAVOR;
    }

    public String getAdbMobMatchDetail(Context context) {
        if (isDebug()) {
            return TEST_ADMOB_BANNER;
        }
        return context.getResources().getString(R.string.admob_match_detail_banner);
    }

    public String getAdbMobNewsDetail(Context context) {
        if (isDebug()) {
            return TEST_ADMOB_BANNER;
        }
        return context.getResources().getString(R.string.admob_news_detail_banner);
    }

    public String getAdmobInterstitial(Context context) {
        if (isDebug()) {
            return TEST_ADMOB_INTERSTITIAL;
        }
        return context.getResources().getString(R.string.admob_interstitial);
    }

    public String getFbAdsNative1(Context context) {
        return context.getResources().getString(R.string.facebook_ads_list_news_feed);
    }

    public String getFbAdsNative2(Context context) {
        return context.getResources().getString(R.string.facebook_ads_match_detail);
    }

    public String getPolicyUrl() {
        return "";
    }

    public void setRemoteConfigData(RemoteConfigData dataConfig) {
        mConfigData = dataConfig;
    }

    public List<RemoteConfigData.League> getLeagues() {
        if (mConfigData == null || mConfigData.getLeagues() == null) {
            return new ArrayList<>();
        }
        return mConfigData.getLeagues();
    }

    public List<LanguageSetting> getLanguageList () {
        ArrayList mLanguageList = new ArrayList();
        mLanguageList.add(new LanguageSetting(1, "English", R.drawable.english));
        mLanguageList.add(new LanguageSetting(1, "Vietnam", R.drawable.flag_vn));
        mLanguageList.add(new LanguageSetting(1, "English", R.drawable.flag_th));
        return mLanguageList;
    }

    public List<FollowItem> getTeamList() {
        ArrayList mTeamList = new ArrayList();
        mTeamList.add(new FollowItem(35, "manutd", "Manchester United", false));
        mTeamList.add(new FollowItem(17, "mancity", "Manchester City", false));
        mTeamList.add(new FollowItem(42, "arsenal", "Arsenal", false));
        mTeamList.add(new FollowItem(38, "chelsea", "Chelsea", false));
        mTeamList.add(new FollowItem(33, "tottenham", "Tottenham", false));
        mTeamList.add(new FollowItem(44, "liverpool", "Liverpool", false));
        mTeamList.add(new FollowItem(2829, "real", "Real Madrid", false));
        mTeamList.add(new FollowItem(2817, "barca", "Barcelona", false));
        mTeamList.add(new FollowItem(2836, "atletico", "Atletico Madrid", false));
        mTeamList.add(new FollowItem(2687, "juventus", "Juventus", false));
        mTeamList.add(new FollowItem(2697, "inter_milan", "Inter Milan", false));
        mTeamList.add(new FollowItem(2692, "ac_milan", "AC Milan", false));
        mTeamList.add(new FollowItem(2672, "bayern", "Bayern Munich", false));
        mTeamList.add(new FollowItem(2673, "dortmund", "Bayern Munich", false));
        mTeamList.add(new FollowItem(144, "psg", "Paris S.Germain", false));

        return mTeamList;
    }

    public  List<FollowItem> getLeagueList() {
        ArrayList mLeagueList = new ArrayList() ;
        mLeagueList.add(new FollowItem(17, "premier", "Premier League", false));
        mLeagueList.add(new FollowItem(8, "laliga", "La Liga", false));
        mLeagueList.add(new FollowItem(23, "seriea", "Serie A", false));
        mLeagueList.add(new FollowItem(35, "bundesliga", "Bundesliga", false));
        mLeagueList.add(new FollowItem(34, "ligue1", "Ligue 1", false));
        mLeagueList.add(new FollowItem(7, "c1", "UEFA Champions League", false));
        return mLeagueList;
    }

    public int getLeagueIdFromKey (String key) {
        for (FollowItem followItem : getLeagueList()) {
            if (followItem.getApp_key().equals(key)) {
                return followItem.getId();
            }
        }
        return 0;
    }

    public void saveArrayList(Context context, List<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public List<String> getArrayList(Context context, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}