package com.management.student.com.management.student.service.impl;

import com.management.student.com.management.student.dao.StudentDao;
import com.management.student.com.management.student.domain.Student;
import com.management.student.com.management.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 13:35
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 查询所有学生信息
     *
     * @return
     */
    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    /***
     * 校验名字是否重复
     * @param name
     * @return
     */
    @Override
    public boolean userRegister(String name) {
        return false;
    }

    /***
     * 注册
     * @param student
     * @return
     */
    @Override
    public int insert(Student student) {
        return 0;
    }

    /***
     * 根据用户名查询用户
     * @param name
     * @return
     */
    @Override
    public List<Student> selectByNameForId(String name) {
        return null;
    }

    /***
     * 登录验证
     * @param name
     * @param password
     * @return
     */
    @Override
    public int selectByNamePassword(String name, String password) {
        return 0;
    }

    /***
     * 修改信息
     * @param student
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Student student) {
        return 0;
    }
}
