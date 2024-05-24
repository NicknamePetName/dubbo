package com.youkeda.comment.util;

import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.model.Comment;
import org.springframework.beans.BeanUtils;

public class CommentDOUtil {

    /**
     * 模型转换为 DO 对象
     *
     * @param comment
     * @return
     */
    public static CommentDO toDO(Comment comment) {
        CommentDO commentDO = new CommentDO();
        BeanUtils.copyProperties(comment, commentDO);
        return commentDO;
    }

    /**
     *
     * DO 对象转换为 模型
     *
     * @param commentDO
     * @return
     */
    public static Comment toModel(CommentDO commentDO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDO,comment);
        return comment;
    }
}