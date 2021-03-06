package com.appian.footballnewsdaily.app.match.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.match.view.MatchStatisticView;
import com.appian.footballnewsdaily.data.interactor.MatchInteractor;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appnet.android.football.sofa.data.StatisticsData;

public class MatchStatisticPresenter extends BasePresenter<MatchStatisticView> implements OnResponseListener<StatisticsData> {
    private final MatchInteractor mInteractor;

    public MatchStatisticPresenter(MatchInteractor interactor ) {
        mInteractor = interactor;
    }


    public void loadMatchStatic(int matchId) {
        if(matchId == 0 || getView() == null) {
            return;
        }
        mInteractor.loadStatistics(matchId, this);
    }

    @Override
    public void onSuccess(StatisticsData data) {
        if(getView() != null) {
            getView().showMatchStatistic(data);
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
