package com.appian.footballnewsdaily.app.team.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.team.view.TeamDetailView;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appian.footballnewsdaily.data.interactor.TeamInteractor;
import com.appnet.android.football.sofa.data.Team;

public class TeamDetailPresenter extends BasePresenter<TeamDetailView> implements OnResponseListener<Team> {
    private final TeamInteractor mInteractor;

    public TeamDetailPresenter(TeamInteractor interactor) {
        mInteractor = interactor;
    }

    public void loadTeamDetail(int teamId) {
        if(getView() == null) {
            return;
        }
        mInteractor.loadTeamDetail(teamId, this);
    }

    @Override
    public void onSuccess(Team data) {
        if(getView() != null) {
            getView().showTeamDetail(data);
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
