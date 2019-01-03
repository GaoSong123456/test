package com.test.serviceimp;

import com.test.dao.UserMapper;
import com.test.po.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

//    @Override
//    public List<User> updateUser() {
//        return userMapper.updateUser();
//    }

    @Override
    public int deleteByPrimaryKey(Integer userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }
}
