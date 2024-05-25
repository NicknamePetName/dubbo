package com.youkeda.comment.service;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import org.apache.ibatis.annotations.Param;

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

    int batchAdd(@Param("list") List<User> users);

    List<User> findByIds(@Param("ids") List<Long> ids);

    int add(User user);

    int update(User user);

    int delete(@Param("id") long id);

    List<User> findAll();

    User findByUserName(@Param("userName") String name);

    List<User> query(@Param("keyWord") String keyWord);

    List<User> search(@Param("keyWord") String keyWord, @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

}
