package com.management.student.com.management.student.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 13:36
 */
@Data
@Entity
@Table(name = "student")
public class Student {

    /***
     *字段长度均采用默认长度，可自行进行设定
     */

    //学生id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //角色id
    @Column(name = "roleId")
    private Long roleId;

    //学生姓名
    @Column(name = "name")
    private String name;

    //用户名
    @Column(name = "username")
    private String username;

    //密码
    @Column(name = "password")
    private String password;

    //邮箱
    @Column(name = "email")
    private String email;

    //电话
    @Column(name = "phone")
    private String phone;

    //登录失败次数
    @Column(name = "missNumber")
    private Integer missNumber;

    //上次登录时间
    @Column(name = "missTime")
    private Date missTime;

    //允许登录时间
    @Column(name = "allowTime")
    private Date allowTime;

}
