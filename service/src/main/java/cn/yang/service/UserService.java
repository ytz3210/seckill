package cn.yang.service;

import cn.yang.common.source.ResTO;
import cn.yang.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    ResTO getUsers();
    ResTO setUser(User user);
}
