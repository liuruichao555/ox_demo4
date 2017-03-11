package com.oxchains.enums;

/**
 * RespStatusEnum
 *
 * @author liuruichao
 * Created on 2016/11/29 18:09
 */
public enum RespStatusEnum {
    SUCCESS(1, "操作成功."),
    FAIL(0, "操作失败");

    private int status;

    private String message;

    RespStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}