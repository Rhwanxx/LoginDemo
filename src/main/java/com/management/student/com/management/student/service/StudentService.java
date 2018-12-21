package com.management.student.com.management.student.service;

import com.management.student.com.management.student.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 13:32
 */
public interface StudentService {

    /**
     * 查询所有学生信息
     * @return
     */
    List<Student> findAll();

    /***
     * 校验名字是否重复
     * @param name
     * @return
     */
    boolean userRegister(String name);

    /***
     * 注册
     * @param student
     * @return
     */
    int insert(Student student);

    /***
     * 根据用户名查询用户
     * @param name
     * @return
     */
    List<Student> selectByNameForId(String name);

    /***
     * 登录验证
     * @param name
     * @param password
     * @return
     */
    int selectByNamePassword(String name, String password);

    /***
     * 修改信息
     * @param student
     * @return
     */
    int updateByPrimaryKeySelective(Student student);
}
