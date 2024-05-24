package com.youkeda.comment.dataobject;

import com.youkeda.comment.model.Comment;
import com.youkeda.comment.model.User;

import java.time.LocalDateTime;

public class CommentDO {

    private long id;

    private String refId;

    private long userId;

    private String content;

    private long parentId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * DO 转换为 Model
     *
     * @return
     */
    public Comment toModel() {
        Comment comment = new Comment();
        comment.setId(getId());
        comment.setGmtCreated(getGmtCreated());
        comment.setGmtModified(getGmtModified());

        User user = new User();
        user.setId(getUserId());
        comment.setAuthor(user);

        comment.setContent(getContent());
        comment.setRefId(getRefId());
        return comment;
    }
}
