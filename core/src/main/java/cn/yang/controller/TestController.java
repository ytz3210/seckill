package cn.yang.controller;

import cn.yang.common.source.ResTO;
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
}
