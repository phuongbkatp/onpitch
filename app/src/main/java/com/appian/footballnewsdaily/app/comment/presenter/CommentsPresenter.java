package com.appian.footballnewsdaily.app.comment.presenter;

import com.appian.footballnewsdaily.app.BasePresenter;
import com.appian.footballnewsdaily.app.comment.view.CommentsView;
import com.appian.footballnewsdaily.data.interactor.CommentsInteractor;
import com.appian.footballnewsdaily.data.interactor.OnResponseListener;
import com.appnet.android.football.fbvn.data.Comment;

import java.util.List;

public class CommentsPresenter extends BasePresenter<CommentsView> {
    private final CommentsInteractor mCommentsInteractor;
    private OnResponseListener<List<Comment>> mOnLoadComments;

    public CommentsPresenter(CommentsInteractor commentsInteractor) {
        mCommentsInteractor = commentsInteractor;
        mOnLoadComments = new OnResponseListener<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> data) {
                if (getView() == null) {
                    return;
                }
                getView().showComments(data);
            }

            @Override
            public void onFailure(String error) {
                if(getView() != null) {
                    getView().onLoadCommentsFail();
                }
            }
        };
    }

    public void loadComments(String objectType, int objectId, int page, int limit) {
        mCommentsInteractor.loadComments(objectType, objectId, page, limit, mOnLoadComments);
    }
}
