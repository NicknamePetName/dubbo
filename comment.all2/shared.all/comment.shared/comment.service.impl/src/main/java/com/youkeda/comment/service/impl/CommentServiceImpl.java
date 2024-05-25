package com.youkeda.comment.service.impl;

import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.model.Comment;
import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import com.youkeda.comment.service.CommentService;
import com.youkeda.comment.service.UserService;
import com.youkeda.comment.util.CommentDOUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;
    @DubboReference(version = "${user.service.version}")
    private UserService userService;

    @Override
    public Result<Comment> post(String refId, long userId, long parentId, String content) {
        Result<Comment> result = new Result<>();
        if (StringUtils.isEmpty(refId) || userId == 0 || StringUtils.isEmpty(content)) {
            result.setCode("500");
            result.setMessage("refId、userId、content 不能为空");
            return result;
        }

        String body = StringEscapeUtils.escapeHtml4(content);

        CommentDO commentDO = new CommentDO();
        commentDO.setUserId(userId);
        commentDO.setRefId(refId);
        commentDO.setParentId(parentId);
        commentDO.setContent(body);
        commentDAO.insert(commentDO);

        result.setData(commentDO.toModel());

        return result;
    }

    @Override
    public Result<List<Comment>> query(String refId) {

        Result<List<Comment>> result = new Result<>();
        //查询所有的评论记录包含回复的
        List<Comment> comments = commentDAO.findByRefId(refId);
        //构建 map 结构
        Map<Long, Comment> commentMap = new HashMap<>();
        //初始化一个虚拟根节点，0 可以对应的是所有一级评论的父亲
        commentMap.put(0L, new Comment());
        //把所有的评论转换为 map 数据
        comments.forEach(comment -> commentMap.put(comment.getId(), comment));
        // 再次遍历评论数据
        comments.forEach(comment -> {
            //得到父评论
            Comment parent = commentMap.get(comment.getParentId());
            if (parent != null) {
                // 初始化 children 变量
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                // 在父评论里添加回复数据
                parent.getChildren().add(comment);
            }
        });
        // 得到所有的一级评论
        List<Comment> data = commentMap.get(0L).getChildren();

        result.setSuccess(true);
        result.setData(data);

        return result;
    }

    @Override
    public int insert(Comment comment) {
        return commentDAO.insert(CommentDOUtil.toDO(comment));
    }

    @Override
    public int update(Comment comment) {
        return commentDAO.update(CommentDOUtil.toDO(comment));
    }

    @Override
    public int delete(long id) {
        return commentDAO.delete(id);
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        List<CommentDO> commentDOS = commentDAO.findAll();
        List<Long> userIds = commentDOS.stream().map(CommentDO::getUserId).collect(Collectors.toList());
        return getComments(userIds, comments, commentDOS);

//        return commentDAO.findAll().stream().map(CommentDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByRefId(String refId) {
        return commentDAO.findByRefId(refId);
    }

    @Override
    public int batchAdd(List<Comment> comments) {
        return commentDAO.batchAdd(comments.stream().map(CommentDOUtil::toDO).collect(Collectors.toList()));
    }

    @Override
    public List<Comment> findByUserIds(List<Long> userIds) {
        List<Comment> comments = new ArrayList<>();
        List<CommentDO> commentDOS = commentDAO.findByUserIds(userIds);
        return getComments(userIds, comments, commentDOS);

//        return commentDAO.findByUserIds(userIds).stream().map(CommentDOUtil::toModel).collect(Collectors.toList());
    }

    /**
     *
     * 组装 commentDO 和 user 到 comment中
     *
     * @param userIds
     * @param comments
     * @param commentDOS
     * @return
     */
    private List<Comment> getComments(List<Long> userIds, List<Comment> comments, List<CommentDO> commentDOS) {
        List<User> users = userService.findByIds(userIds);
        Map<Long, User> userMap = new HashMap<>();
        users.forEach(user -> userMap.put(user.getId(), user));
        commentDOS.forEach(commentDO -> {
            Comment comment = CommentDOUtil.toModel(commentDO);
            comment.setAuthor(userMap.get(commentDO.getUserId()));
            comments.add(comment);

        });
        return comments;
    }
}
