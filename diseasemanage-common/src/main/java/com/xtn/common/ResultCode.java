package com.xtn.common;

/**
 * 公共的返回结果枚举类
 * 时间：2021年2月14日 19:36:21
 */
public enum  ResultCode {
    /**
     * 状态码： 200->成功
     *         400->失败
     *         401->密码不正确
     *         402->尚未登录
     *         403->没有找到数据
     *         404->服务器异常
     *         405->未知错误，请联系管理员
     *         406->用户已存在
     *         407->部门不存在
     */
    SUCCESS(200,"成功"),
    ERROR(400,"失败"),
    PASSWORD_ERROR(401,"密码不正确"),
    LOGIN_ERROR(402,"尚未登录"),
    DATA_ERROR(403,"没有找到数据"),
    SERVER_ERROR(404,"服务器异常"),
    UNKNOWN_ERROR(405,"未知错误，请联系管理员"),
    USER_ALREADY_EXISTS(406,"用户已存在"),
    DEPART_NO_EXISTS(407,"部门不存在"),
    ;

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
