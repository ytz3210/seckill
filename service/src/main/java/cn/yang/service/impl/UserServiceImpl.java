package cn.yang.service.impl;

import cn.yang.common.source.ResTO;
import cn.yang.common.utils.RUtil;
import cn.yang.dao.UserMapper;
import cn.yang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDao;

    @Override
    public ResTO getUsers() {
        return RUtil.success(userDao.getUsers());
    }
}
