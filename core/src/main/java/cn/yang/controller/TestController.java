package cn.yang.controller;

import cn.yang.common.source.ResTO;
import cn.yang.common.utils.RUtil;
import cn.yang.entity.User;
import cn.yang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/get_user")
    public ResTO getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/in_user")
    public ResTO insertUser(){
        User user = new User();
        user.setUserName("yang");
        user.setPassword("12345678");
        return userService.setUser(user);
    }
    @GetMapping("/in_amount")
    public ResTO getAmount(){
        return userService.selectCount("88ef12a3-181f-433e-b700-1a8bed038e32");
    }
    @GetMapping("/update")
    public ResTO updateAmount(){
        User user = new User();
        user.setId("88ef12a3-181f-433e-b700-1a8bed038e32");
        return userService.updateAmount(user);
    }
}
