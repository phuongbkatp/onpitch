package com.appian.footballnewsdaily.app.followsetting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.BaseListAdapter;
import com.appian.footballnewsdaily.data.app.FollowItem;
import com.appian.footballnewsdaily.util.ImageLoader;
import com.appnet.android.football.sofa.helper.SofaImageHelper;

import java.util.List;

public class FollowSettingAdapter extends BaseListAdapter<FollowItem> {

    private List<FollowItem> mFollowItemList;
    private List<String> mSelectedList;
    public FollowSettingAdapter(Context context, List<FollowItem> data, List<String> selectedList) {
        super(context, data);
        mFollowItemList = data;
        mSelectedList = selectedList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.follow_item, null);
            holder = new ViewHolder();
            holder.logo = view.findViewById(R.id.img_team_logo);
            holder.name = view.findViewById(R.id.tv_team_name);
            holder.check = view.findViewById(R.id.img_follow_check);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.displayImage(SofaImageHelper.getSofaImgTeam(mFollowItemList.get(position).getId()), holder.logo);

        holder.name.setText(((FollowItem) (mFollowItemList.get(position))).getName());
        if (mSelectedList != null) {
            if (mSelectedList.contains(mFollowItemList.get(position).getApp_key())) {
                holder.check.setBackground(mContext.getResources().getDrawable(R.drawable.wh_tu03_following));
                mFollowItemList.get(position).setCheck(true);
            } else {
                holder.check.setBackground(mContext.getResources().getDrawable(R.drawable.wh_tu03_follow));
                mFollowItemList.get(position).setCheck(false);
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
