package com.management.student.com.management.student.controller;

import com.management.student.com.management.student.domain.Student;
import com.management.student.com.management.student.service.StudentService;
import com.management.student.com.management.student.utils.Result;
import com.management.student.com.management.student.utils.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生Controller
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 13:42
 */
@RestController
public class StudentController {

    private static  final transient Logger logger = Logger.getLogger(KaptchaController.class);

    @Autowired
    StudentService studentService;
    /**
     * 查看所有学生信息
     * @return
     */
    @RequestMapping("/findall")
    public List<Student> findAll(){

        return studentService.findAll();
    }

    @ResponseBody
    @RequestMapping("/register")
    public Result<String>  Register(String name,String password) {
        //判断该用户名是否已被注册
        boolean num = studentService.userRegister(name);

        if (num == false) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("username", "用户名已被注册");
            return ResultUtil.error(1,"用户名已被注册");
        } else {
            Student student = new Student();
            student.setName(name);
            student.setPassword(password);
            //新注册用户错误次数都为0
            //student.setMissNumber(0);
            //1超级管理员:直接修改数据库的用户，只能打开mySQL改的
            //2普通会员:通过请求注册的用户
            student.setRoleId(2L);
            int flag = studentService.insert(student);

            if (flag == 1) {
                return ResultUtil.successNoData();
            } else {
                return ResultUtil.error(2,"注册失败");
            }
        }
    }


    @ResponseBody
    @RequestMapping("/login")
    public Result<String> Login(HttpServletRequest request, String name, String password){

        //根据账号判断数据库中是否存在该用户
        List<Student> userList = studentService.selectByNameForId(name);
        //如果不存在该用户
        if (userList.size() == 0) {
            //返回登录页面
            return ResultUtil.error(3,"该用户不存在");

            //如果存在该用户
        } else {

            //获得登录失败的次数
            int intMissNumber = userList.get(0).getMissNumber();

            //因为不能有相同的用户名，所以该List<User>只有一个值，可以直接使用获得id值
            long intUserId = userList.get(0).getId();

            //获得该用户上一次登录的时间
            Date dateLogin = userList.get(0).getMissTime();

            //获得允许登录时间的字段:allow_time
            Date dateAllowTime = userList.get(0).getAllowTime();

            //获得当前时间
            Date dateNow = new Date();

            //根据账号和密码判断是否输入的都正确
            int num = studentService.selectByNamePassword(name,password);

            //begin:对能否登录时间的判断

            //如果该时间允许登录
            //如果现在的时间大于允许登录的时间
            if (dateAllowTime == null ||dateNow.getTime() > dateAllowTime.getTime()) {

                //begin:对错误登录次数的判断
                //判断错误次数是否大于等于3
                if (intMissNumber >= 3 ) {
                    //已经登录失败了三次及以上，锁定账号，不允许登录
                    //允许登录的时间加2分钟
                    logger.info("允许登录的时间没有加2分钟前是:"+dateAllowTime);
                    Date dateAfterAllowTime = new Date(dateNow .getTime() + 120000);
                    logger.info("允许登录的时间加2分钟后是:"+dateAfterAllowTime);
                    //修改数据库中的miss_number错误记录的数目
                    //把错误次数清0
                    intMissNumber = 0;
                    Student student = new Student();
                    student.setId(intUserId);
                    student.setMissNumber(intMissNumber);
                    student.setAllowTime(dateAfterAllowTime);
                    int intFlag = studentService.updateByPrimaryKeySelective(student);
                    logger.info("intFlag:"+intFlag);
                    logger.info("222时间允许登录，但是错误次数超过三次！");
                    return ResultUtil.error(4,"错误三次以上，请稍后再试");

                    //错误次数小于三次，可以登录
                }else {

                    //begin:对密码是否正确的判断
                    //如果密码对了
                    if (num != 0) {
                        //把错误次数清0
                        intMissNumber = 0;
                        //记录最新登录的时间
                        dateLogin = new Date();
                        //记录最新的允许登录时间
                        dateAllowTime = new Date();

                        //修改数据库中的miss_number错误记录的数目
                        Student student = new Student();
                        student.setId(intUserId);
                        student.setMissTime(dateLogin);
                        student.setMissNumber(intMissNumber);
                        student.setAllowTime(dateAllowTime);

                        int intFlag = studentService.updateByPrimaryKeySelective(student);
                        logger.info("intFlag:"+intFlag);
                        //把id保存进session，在后面的文章发表、评论发表时候会用到
                        HttpSession session = request.getSession();
                        session.setAttribute("intUserId", intUserId);

                        //begin:拦截器所需
                        session.setAttribute("userList", userList);
                        //end:拦截器所需
                        return ResultUtil.successNoData();

                        //如果密码错了
                    }else {
                        //把错误次数+1
                        intMissNumber = intMissNumber + 1;
                        //修改数据库中的miss_number错误记录的数目
                        Student student = new Student();
                        student.setId(intUserId);
                        student.setMissNumber(intMissNumber);
                        int intFlag = studentService.updateByPrimaryKeySelective(student);
                        logger.info("密码错误的intFlag:"+intFlag);

                        return ResultUtil.error(5,"密码错误，请重试");
                    }
                    //end:对密码是否正确的判断
                }
                //end:错误登录次数的判断

                //该时间不允许登录
            }else {
                //TODO 定时任务，可暂时不加
                logger.info("111对时间的判断结果：当前时间不允许登录!");
                return ResultUtil.error(5,"当前时间不允许登录");
            }

            //end:对能否登录时间的判断
        }


    }

}
