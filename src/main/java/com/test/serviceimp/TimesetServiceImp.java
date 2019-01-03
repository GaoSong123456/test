package com.test.serviceimp;

import com.test.dao.TimesetMapper;
import com.test.po.Timeset;
import com.test.service.TimesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class TimesetServiceImp implements TimesetService{

    @Autowired
    private TimesetMapper timesetMapper;

    @Override
    public Timeset selectByprimarykey() {
        return timesetMapper.selectByPrimarykey();
    }

    @Override
    public int updateTimeset(Timeset timeset) {
        return timesetMapper.updateTimeset(timeset);
    }
}
