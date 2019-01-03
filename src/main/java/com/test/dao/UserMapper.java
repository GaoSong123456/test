package com.test.dao;

import com.test.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {
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
     * @param username
     * @return
     */
//    List<User> updateByPrimaryKey();

    /**
     * 根据姓名修改员工信息
     * @param username
     * @return
     */
//   int  updateByUsername();
    /**
     * 删除
     * @param userid
     * @return
     */

    int deleteByPrimaryKey(Integer userid);
}