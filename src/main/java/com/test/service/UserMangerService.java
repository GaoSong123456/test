package com.test.service;

import com.test.po.Usermanger;

public interface UserMangerService {

    Boolean selectByPrimaryKey(String username,String password);
}
