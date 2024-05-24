package com.youkeda.comment.service;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    /**
     * 注册用户
     *
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> register(String userName, String pwd);

    /**
     * 执行登录逻辑，登录成功返回 User 对象
     *
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> login(String userName, String pwd);

    int batchAdd(List<User> users);

    List<User> findByIds(List<Long> ids);

    int add(User user);

    int update(User user);

    int delete(long id);

    List<User> findAll();

    User findByUserName(String name);

    List<User> query(String keyWord);

    List<User> search(String keyWord, LocalDateTime startTime, LocalDateTime endTime);

}
