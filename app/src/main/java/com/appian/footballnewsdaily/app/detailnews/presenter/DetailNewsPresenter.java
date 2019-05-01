package com.appian.footballnewsdaily.app.detailnews.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.detailnews.view.DetailNewsView;
import com.appian.footballnewsdaily.data.interactor.NewsInteractor;
import com.appian.footballnewsdaily.data.interactor.OnDetailNewsResponseListener;
import com.appnet.android.football.fbvn.data.DetailNewsAuto;

public class DetailNewsPresenter extends BasePresenter<DetailNewsView> implements OnDetailNewsResponseListener<DetailNewsAuto> {
    private final NewsInteractor mInteractor;

    public DetailNewsPresenter(NewsInteractor interactor) {
        mInteractor = interactor;
    }

    public void loadNewsDetail(String link) {
        if(link == "") {
            return;
        }
        mInteractor.loadNewsDetail(link, this);
    }

    @Override
    public void onSuccess(DetailNewsAuto data) {
        if(getView() == null) {
            return;
        }
        getView().showNews(data);
    }

    @Override
    public void onFailure(String error) {

    }
}
