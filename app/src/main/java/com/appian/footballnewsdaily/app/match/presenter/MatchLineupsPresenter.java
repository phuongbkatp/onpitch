package com.appian.footballnewsdaily.app.match.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.match.view.MatchLineupsView;
import com.appian.footballnewsdaily.data.interactor.MatchInteractor;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appnet.android.football.sofa.data.LineupsData;

public class MatchLineupsPresenter extends BasePresenter<MatchLineupsView> implements OnResponseListener<LineupsData> {
    private final MatchInteractor mInteractor;

    public MatchLineupsPresenter() {
        mInteractor = new MatchInteractor();
    }

    public MatchLineupsPresenter(MatchInteractor interactor) {
        mInteractor = interactor;
    }

    public void loadMatchLineups(int matchId) {
        if (matchId == 0 || getView() == null) {
            return;
        }
        mInteractor.loadLineups(matchId, this);
    }

    @Override
    public void onSuccess(LineupsData data) {
        if (getView() != null) {
            getView().showMatchLineups(data);
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
