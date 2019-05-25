package com.appian.footballnewsdaily.app.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.BaseListAdapter;
import com.appian.footballnewsdaily.data.app.FollowItem;
import com.appian.footballnewsdaily.data.app.LanguageSetting;
import com.appian.footballnewsdaily.util.ImageLoader;
import com.appnet.android.football.sofa.helper.SofaImageHelper;

import java.util.List;

public class LanguageSettingAdapter extends BaseListAdapter<LanguageSetting> {

    private List<LanguageSetting> mLanguageSettingList;
    private List<String> mSelectedList;
    public LanguageSettingAdapter(Context context, List<LanguageSetting> data, List<String> selectedList) {
        super(context, data);
        mLanguageSettingList = data;
        mSelectedList = selectedList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.language_setting_item, null);
            holder = new ViewHolder();
            holder.logo = view.findViewById(R.id.img_language_logo);
            holder.name = view.findViewById(R.id.tv_language_name);
            holder.check = view.findViewById(R.id.img_follow_check);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.logo.setBackground(mContext.getResources().getDrawable(((LanguageSetting) (mLanguageSettingList.get(position))).getLogo()));
        holder.name.setText(((LanguageSetting) (mLanguageSettingList.get(position))).getName());
        if (mSelectedList != null) {
            if (mSelectedList.contains(mLanguageSettingList.get(position).getId())) {
                holder.check.setBackground(mContext.getResources().getDrawable(R.drawable.wh_tu03_following));
            } else {
                holder.check.setBackground(mContext.getResources().getDrawable(R.drawable.wh_tu03_follow));
            }
        }
        return view;
    }

    private static class ViewHolder {
        ImageView logo;
        TextView name;
        ImageView check;

    }
}
