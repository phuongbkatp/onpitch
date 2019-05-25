package com.appian.footballnewsdaily.app.followsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.BaseFragment;
import com.appian.footballnewsdaily.data.app.AppConfig;
import com.appian.footballnewsdaily.data.app.FollowItem;

import java.util.ArrayList;
import java.util.List;

import static com.appian.footballnewsdaily.Constant.KEY_SELECTED_LIST;
import static com.appian.footballnewsdaily.Constant.KEY_NAME_LIST;

public class FollowSettingFragment extends BaseFragment {

    private FollowSettingAdapter mFollowSettingAdapter;
    private ListView mFollowListView;
    private List<String> mSelectedList = new ArrayList<>();
    private List<String> mNameList = new ArrayList<>();
    private int followType;
    private AppConfig appConfig;

    public static FollowSettingFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        FollowSettingFragment fragment = new FollowSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.follow_setting_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle agrs = getArguments();
        followType = agrs.getInt("type");
        appConfig = AppConfig.getInstance();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSelectedList = appConfig.getArrayList(getActivity().getApplicationContext(), KEY_SELECTED_LIST);

        if (followType == 0) {
            mNameList = appConfig.getArrayList(getActivity().getApplicationContext(), KEY_NAME_LIST);

            final List<FollowItem> mListItem = appConfig.getLeagueList();
            mFollowSettingAdapter = new FollowSettingAdapter(getContext(), mListItem , mSelectedList);
            mFollowListView = (ListView) view.findViewById(R.id.follow_team_list);
            mFollowListView.setAdapter(mFollowSettingAdapter);
            mFollowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ImageView mCheckView = view.findViewById(R.id.img_follow_check);
                    if (!mListItem.get(position).isCheck()) {
                        mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_following));
                        mListItem.get(position).setCheck(true);
                        mSelectedList.add(mListItem.get(position).getApp_key());
                        mNameList.add(mListItem.get(position).getName());
                    } else {
                        mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_follow));
                        mListItem.get(position).setCheck(false);
                        mSelectedList.remove(mListItem.get(position).getApp_key());
                        mNameList.remove(mListItem.get(position).getName());

                    }
                }
            });

        } else if (followType == 1) {
            mNameList = appConfig.getArrayList(getActivity().getApplicationContext(), KEY_NAME_LIST);

            final List<FollowItem> mListItem = appConfig.getTeamList();
            mFollowSettingAdapter = new FollowSettingAdapter(getContext(), mListItem, mSelectedList);
            mFollowListView = (ListView) view.findViewById(R.id.follow_team_list);
            mFollowListView.setAdapter(mFollowSettingAdapter);
            mFollowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ImageView mCheckView = view.findViewById(R.id.img_follow_check);
                    if (!mListItem.get(position).isCheck()) {
                        mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_following));
                        mListItem.get(position).setCheck(true);
                        mSelectedList.add(mListItem.get(position).getApp_key());
                        mNameList.add(mListItem.get(position).getName());
                    } else {
                        mCheckView.setBackground(getResources().getDrawable(R.drawable.wh_tu03_follow));
                        mListItem.get(position).setCheck(false);
                        mSelectedList.remove(mListItem.get(position).getApp_key());
                        mNameList.remove(mListItem.get(position).getName());

                    }
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appConfig.saveArrayList(getActivity().getApplicationContext(), mSelectedList, KEY_SELECTED_LIST);
        appConfig.saveArrayList(getActivity().getApplicationContext(), mNameList, KEY_NAME_LIST);

    }
}
