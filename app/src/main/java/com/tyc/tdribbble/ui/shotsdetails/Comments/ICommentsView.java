package com.tyc.tdribbble.ui.shotsdetails.Comments;

import com.tyc.tdribbble.entity.CommentsEntity;
import com.tyc.tdribbble.entity.FollowersEntity;
import com.tyc.tdribbble.entity.TTEntity;

import java.util.List;

/**
 * 作者：tangyc on 2017/6/21
 * 邮箱：874500641@qq.com
 */
public interface ICommentsView {
    public void showComments(List<CommentsEntity> commentsEntities);

    public void likeComment(TTEntity ttEntity);
    public void showEmpty();
    public void showError();
}
