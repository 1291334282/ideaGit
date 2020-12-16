package com.graduation.enums;


import lombok.Getter;


public enum CodeEnum {
    /**
     * 成功
     */
    SUCCESS("200", "成功"),
    REGISTER_SUCCESS("201", "注册成功"),
    ADD_SUCCESS("202", "添加成功"),
    DELETE_SUCCESS("203", "删除成功"),
    UPDATE_SUCCESS("204", "更新成功"),
    LOGIN_SUCCESS("205", "登陆成功"),
    LOGINOUT_SUCCESS("206", "注销成功"),
    UPLOAD_SUCCESS("207", "上传成功"),
    /**
     * 操作失败
     */
    ERROR("500", "操作失败"),
    USER_NOT_EXIST("501", "用户名不存在"),
    USER_IS_EXISTS("502", "用户名已存在"),
    DATA_IS_NULL("503", "数据为空"),
    STOCK_EMPTY("504", "库存不足"),
    REGISTER_FAIL("505", "注册失败"),
    ADD_FAIL("506", "添加失败"),
    UPDATE_FAIL("507", "更新失败"),
    NO_AUTH("508", "没有权限"),
    NO_LOGIN("509", "没有登陆"),
    LOGIN_FAIL("510", "登录失败"),
    PASSWORD_FAIL("511", "密码错误"),
    UNKNOW_FAIL("512", "未知错误"),
    DELETE_FAIL("513", "删除失败"),
    UPLOAD_FAIL("514", "上传失败"),
    FILE_EMPTY("515", "空文件"),
    ;

    CodeEnum(String value, String msg) {
        this.val = value;
        this.msg = msg;
    }

    public String val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private String val;
    private String msg;
}


