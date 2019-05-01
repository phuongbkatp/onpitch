package com.appian.footballnewsdaily.app.player.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.player.view.PlayerTransferView;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appian.footballnewsdaily.data.interactor.PlayerInteractor;
import com.appnet.android.football.sofa.data.Transfer;

import java.util.List;

public class PlayerTransfersPresenter extends BasePresenter<PlayerTransferView> implements OnResponseListener<List<Transfer>> {
    private final PlayerInteractor mPlayerInteractor;

    public PlayerTransfersPresenter(PlayerInteractor playerInteractor) {
        mPlayerInteractor = playerInteractor;
    }

    public void loadPlayerTransfers(int playerId) {
        if(playerId == 0) {
            return;
        }
        mPlayerInteractor.loadPlayerTransfers(playerId, this);
    }

    @Override
    public void onSuccess(List<Transfer> data) {
        if(getView() != null) {
            getView().showPlayerTransfer(data);
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
