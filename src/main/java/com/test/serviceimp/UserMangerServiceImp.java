/**
 * Copyright (C), 2018-2018, 中兴微品
 * FileName: UserMangerServiceImp
 * Author:   Administrator
 * Date:     2018/7/25 12:40
 * Description: UserMangerServiceImp
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.serviceimp;

import com.test.dao.UsermangerMapper;
import com.test.dao.UsertimeMapper;
import com.test.po.Usermanger;
import com.test.service.UserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈UserMangerServiceImp〉
 *
 * @author Administrator
 * @create 2018/7/25
 * @since 1.0.0
 */
@Transactional
@Service
public class UserMangerServiceImp implements UserMangerService {
    @Autowired
    private UsermangerMapper usermangerMapper;

    @Override
    public Boolean selectByPrimaryKey(String username,String password) {
        Usermanger usermanger = usermangerMapper.selectByPrimaryKey(username);
        if (usermanger.getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
