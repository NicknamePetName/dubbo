package com.youkeda.comment.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youkeda.comment.model.User;
import com.youkeda.comment.model.Paging;
import com.youkeda.comment.model.Result;
import com.youkeda.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/users")
    public Result<Paging<User>> getAll(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "15") Integer pageSize) {
        Result<Paging<User>> result = new Result<>();
        Page<User> page = PageHelper.startPage(pageNum,pageSize).doSelectPage(() -> userService.findAll());

        System.out.println(page);

        result.setSuccess(true);
        result.setData(new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(),page.getResult()));
        return  result;
    }

    @PostMapping("/user")
    @ResponseBody
    public User save(@RequestBody User userDO) {
        userService.add(userDO);
        return userDO;
    }

    @PostMapping("/user/batchAdd")
    @ResponseBody
    public List<User> batchAdd(@RequestBody List<User> userDOS) {
        userService.batchAdd(userDOS);
        return userDOS;
    }

    @PostMapping("/user/update")
    @ResponseBody
    public User update(@RequestBody User userDO) {
        userService.update(userDO);
        return userDO;
    }

    @GetMapping("/user/del")
    @ResponseBody
    public boolean delete(@RequestParam("id") Long id) {
        return userService.delete(id) > 0;
    }

    @GetMapping("/user/findByUserName")
    @ResponseBody
    public User findByUserName(@RequestParam("userName") String userName) {
        return userService.findByUserName(userName);
    }

    @GetMapping("/user/search")
    @ResponseBody
    public List<User> search(@RequestParam("keyWord") String keyWord,
                             @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                             LocalDateTime startTime,
                             @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                             LocalDateTime endTime) {
        return userService.search(keyWord, startTime, endTime);
    }

    @GetMapping("/user/findByIds")
    @ResponseBody
    public List<User> findByIds(@RequestParam("ids") List<Long> ids) {
        return userService.findByIds(ids);
    }
}
