package com.youkeda.comment.service.impl;

import com.youkeda.comment.dao.UserDAO;
import com.youkeda.comment.dataobject.UserDO;
import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import com.youkeda.comment.service.UserService;
import com.youkeda.comment.util.UserDOUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@DubboService(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @PostConstruct
    public void init() {
        System.out.println("UserServiceImpl init successful.");
    }

    @Override
    public Result<User> register(String userName, String pwd) {
        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userName)) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd)) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO = userDAO.findByUserName(userName);
        if (userDO!=null){
            result.setCode("602");
            result.setMessage("用户名已经存在");
            return result;
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "_ykd2050";
        // 生成md5值，并转大写字母
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        UserDO userDO1 = new UserDO();
        userDO1.setUserName(userName);
        // 初始昵称等于用户名
        userDO1.setNickName(userName);
        userDO1.setPwd(md5Pwd);

        userDAO.add(userDO1);

        result.setSuccess(true);

        result.setData(UserDOUtil.toModel(userDO1));

        return result;
    }

    @Override
    public Result<User> login(String userName, String pwd) {

        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userName)) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd)) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO = userDAO.findByUserName(userName);
        if (userDO==null){
            result.setCode("602");
            result.setMessage("用户名不存在");
            return result;
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "_ykd2050";
        // 生成md5值，并转大写字母
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        if (!StringUtils.equals(md5Pwd,userDO.getPwd())){
            result.setCode("603");
            result.setMessage("密码不对");
            return result;
        }


        result.setSuccess(true);

        result.setData(UserDOUtil.toModel(userDO));

        return result;
    }

    @Override
    public int batchAdd(List<User> users) {
        return userDAO.batchAdd(users.stream().map(UserDOUtil::toDO).collect(Collectors.toList()));
    }

    @Override
    public List<User> findByIds(List<Long> ids) {
        return userDAO.findByIds(ids).stream().map(UserDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public int add(User user) {
        return userDAO.add(UserDOUtil.toDO(user));
    }

    @Override
    public int update(User user) {
        return userDAO.update(UserDOUtil.toDO(user));
    }

    @Override
    public int delete(long id) {
        return userDAO.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll().stream().map(UserDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public User findByUserName(String name) {
        return UserDOUtil.toModel(userDAO.findByUserName(name));
    }

    @Override
    public List<User> query(String keyWord) {
        return userDAO.query(keyWord).stream().map(UserDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public List<User> search(String keyWord, LocalDateTime startTime, LocalDateTime endTime) {
        return userDAO.search(keyWord, startTime, endTime).stream().map(UserDOUtil::toModel).collect(Collectors.toList());
    }

}
