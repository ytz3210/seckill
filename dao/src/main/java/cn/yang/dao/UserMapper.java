package cn.yang.dao;

import cn.yang.entity.User;
import org.springframework.stereotype.Repository;
//import cn.yang.dao.annotation.InterceptAnnotation;

import java.util.List;
@Repository
public interface UserMapper {
//    @InterceptAnnotation
    List<User> getUsers();
    Integer insertUser(User user);
    Integer selectCount(String id);
    Integer updateAmount(User user);
    User getUser(String id);
}
