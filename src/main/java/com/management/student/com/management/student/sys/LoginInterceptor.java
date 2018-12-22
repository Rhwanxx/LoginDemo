package com.management.student.com.management.student.sys;

import com.management.student.com.management.student.domain.Student;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登录验证拦截器
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 18:43
 */
@Controller
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    Logger logger = Logger.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String basePath = request.getContextPath();
        String path = request.getRequestURI();

        if (!doLoginInterceptor(path,basePath)){
            return true;
        }

        HttpSession session = request.getSession();
        List<Student> studentList = (List<Student>) session.getAttribute("studentList");

        if (CollectionUtils.isEmpty(studentList)){
            //未登录,进行跳转
            String requestType = request.getHeader("X-Requested-With");
            if (!StringUtils.isEmpty(requestType) && requestType.equals("XMLHttpRequest")){
                response.setHeader("sessionstatus","timeout");
                response.getWriter().print("LoginTimeout");
                return false;
            }else{
                logger.info("未登录，跳转到登录页面");
                response.sendRedirect(request.getContextPath()+"signin");
            }
            return false;
        }

        return true;
    }

    /***
     * 登录拦截
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path, String basePath) {
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();

        notLoginPaths.add("/index");
        notLoginPaths.add("/signin");
        notLoginPaths.add("/login");
        notLoginPaths.add("/register");
        notLoginPaths.add("/kaptcha.jpg");
        notLoginPaths.add("/kaptcha");

        if(notLoginPaths.contains(path)){
            return false;
        }
        return true;

    }
}
