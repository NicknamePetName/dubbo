package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDAO {

    int insert(CommentDO commentDO);

    int update(CommentDO commentDO);

    int delete(@Param("id") long id);

    List<CommentDO> findAll();

    List<Comment> findByRefId(@Param("refId") String refId);

    int batchAdd(@Param("commentDOS") List<CommentDO> commentDOS);

    List<CommentDO> findByUserIds(@Param("userIds") List<Long> userIds);
}
