package com.youkeda.comment.util;

import com.youkeda.comment.dataobject.UserDO;
import com.youkeda.comment.model.User;
import org.springframework.beans.BeanUtils;

public class UserDOUtil {

    /**
     * 模型转换为 DO 对象
     *
     * @param user
     * @return
     */
    public static UserDO toDO(User user) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        return userDO;
    }

    /**
     *
     * DO 对象转换为 模型
     *
     * @param userDO
     * @return
     */
    public static User toModel(UserDO userDO) {
        User user = new User();
        BeanUtils.copyProperties(userDO,user);
        return user;
    }
}