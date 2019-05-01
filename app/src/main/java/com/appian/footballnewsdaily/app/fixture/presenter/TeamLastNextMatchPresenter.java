package com.appian.footballnewsdaily.app.fixture.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.fixture.view.TeamLastNextMatchView;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appian.footballnewsdaily.data.interactor.TeamInteractor;
import com.appnet.android.football.sofa.data.Event;

import java.util.List;

public class TeamLastNextMatchPresenter extends BasePresenter<TeamLastNextMatchView> implements OnResponseListener<List<Event>> {
    private final TeamInteractor mTeamInteractor;

    public TeamLastNextMatchPresenter() {
        mTeamInteractor = new TeamInteractor();
    }

    public void loadMatchLastNext(int teamId) {
        mTeamInteractor.loadLastNextMatch(teamId, this);
    }

    @Override
    public void onSuccess(List<Event> data) {
        if(getView() != null) {
            getView().showMatchLastNext(data);
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
