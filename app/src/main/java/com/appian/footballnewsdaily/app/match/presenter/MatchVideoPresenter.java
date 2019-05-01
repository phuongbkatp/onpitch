package com.appian.footballnewsdaily.app.match.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.match.view.MatchVideoView;
import com.appian.footballnewsdaily.data.interactor.NewsInteractor;
import com.appian.footballnewsdaily.data.interactor.OnDetailNewsResponseListener;
import com.appnet.android.football.fbvn.data.DetailNewsAuto;

public class MatchVideoPresenter extends BasePresenter<MatchVideoView> implements OnDetailNewsResponseListener<DetailNewsAuto> {
    private final NewsInteractor mInteractor;

    public MatchVideoPresenter(NewsInteractor interactor) {
        mInteractor = interactor;
    }

    public void loadMatchVideo(String home, String away) {
        if(getView() == null) {
            return;
        }
        mInteractor.loadMatchVideo(home, away, this);
    }


    @Override
    public void onSuccess(DetailNewsAuto data) {
        if(getView() != null) {
            getView().showMatchVideo(data);
        }
    }

    @Override
    public void onFailure(String error) {
    }
}
