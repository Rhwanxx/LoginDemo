package com.management.student.com.management.student.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回信息
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 17:35
 */
public class Result<T> {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
