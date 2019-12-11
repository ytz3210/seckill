package cn.yang.entity;

import lombok.Data;


@Data
public class User extends BaseEntity {
    private String userName;
    private String password;
}
