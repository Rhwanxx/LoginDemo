package com.management.student.com.management.student.dao;

import com.management.student.com.management.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 13:49
 */
@Repository
public interface StudentDao extends JpaRepository<Student,String> {

    /***
     *  查询所有用户信息
     */
    @Query(value = "SELECT * FROM s_student",nativeQuery = true)
    List<Student> findAll();
}
