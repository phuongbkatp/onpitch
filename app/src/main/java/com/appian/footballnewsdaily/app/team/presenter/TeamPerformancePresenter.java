package com.appian.footballnewsdaily.app.team.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.team.view.TeamPerformanceView;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appian.footballnewsdaily.data.interactor.TeamInteractor;
import com.appnet.android.football.sofa.data.Performance;

import java.util.List;

public class TeamPerformancePresenter extends BasePresenter<TeamPerformanceView> implements OnResponseListener<List<Performance>> {
    private final TeamInteractor mInteractor;

    public TeamPerformancePresenter(TeamInteractor interactor) {
        mInteractor = interactor;
    }

    public void loadTeamPerformance(int teamId) {
        if(getView() == null) {
            return;
        }
        mInteractor.loadTeamPerformance(teamId, this);
    }

    @Override
    public void onSuccess(List<Performance> data) {
        if(getView() != null) {
            getView().showTeamPerformance(data);
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
