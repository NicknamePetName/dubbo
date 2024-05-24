package com.youkeda.comment.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youkeda.comment.service.CommentService;
import com.youkeda.comment.model.Comment;
import com.youkeda.comment.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    @ResponseBody
    public Paging<Comment> getAll(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }

        // 设置当前页数为1，以及每页3条记录
        Page<Comment> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> commentService.findAll());

        return new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult());
    }

    @PostMapping("/comment")
    @ResponseBody
    public Comment save(@RequestBody Comment commentDO) {
        commentService.insert(commentDO);
        return commentDO;
    }

    @PostMapping("/comment/batchAdd")
    @ResponseBody
    public List<Comment> batchAdd(@RequestBody List<Comment> commentDOS) {
        commentService.batchAdd(commentDOS);
        return commentDOS;
    }

    @PostMapping("/comment/update")
    @ResponseBody
    public Comment update(@RequestBody Comment commentDO) {
        commentService.update(commentDO);
        return commentDO;
    }

    @GetMapping("/comment/del")
    @ResponseBody
    public boolean delete(@RequestParam("id") Long id) {
        return commentService.delete(id) > 0;
    }

    @GetMapping("/comment/findByRefId")
    @ResponseBody
    public List<Comment> findByRefId(@RequestParam("refId") String refId) {
        return commentService.findByRefId(refId);
    }

    @GetMapping("/comment/findByUserIds")
    @ResponseBody
    public List<Comment> findByUserIds(@RequestParam("userIds") List<Long> ids) {
        return commentService.findByUserIds(ids);
    }
}
