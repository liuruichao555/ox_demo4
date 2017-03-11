package com.oxchains.enums;

/**
 * ajax返回message
 *
 * @author liuruichao
 * Created on 2016/12/1 15:40
 */
public enum RespDTOMessageEnum {
    SYSTEM_ERROR("系统繁忙，请稍后再试！"),
    CP_EXISTS("票据已存在"),
    CP_NOT_EXISTS("票据不存在"),
    PARAM_IS_BLANK("参数不能为空"),
    CUSTOMER_NOT_EXISTS("没有这个用户"),
    CHAINCODE_EXISTS("chaincode已存在"),
    CHAINCODE_PARAM_ERROR("chaincode参数错误"),
    CUSTOMER_LOGIN_ERROR("用户名密码错误"),
    CUSTOMER_EXISTS("用户已经存在"),
    CUSTOMER_LOGINED("该用户已经登录完成"),
    CUSTOMER_STATUS_DEL("该用户已经被冻结");

    private String message;

    RespDTOMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
