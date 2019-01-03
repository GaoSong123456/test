package com.test.service;

import com.test.po.User;

import java.util.List;

public interface UserService {

    /**
     * 增加员工
     * @param userid
     * @param username
     * @param department
     * @return
     */
    int addUser(User user);

    /**
     * 查询所有员工
     * @param userid
     * @param username
     * @param department
     * @return
     */
    List<User> selectAllUser();

    /**
     * 根据id修改员工信息
     * @param userid
     * @return
     */
//    List<User> updateUser();

    /**
     * 删除
     * @param userid
     * @return
     */
    int deleteByPrimaryKey(Integer userid);
}
