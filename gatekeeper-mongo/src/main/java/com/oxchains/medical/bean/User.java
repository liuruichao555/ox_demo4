package com.oxchains.medical.bean;

import lombok.Data;

import java.util.Date;

/**
 * User
 *
 * @author liuruichao
 * Created on 2017/3/11 11:50
 */
@Data
public class User {
    private Integer id;

    private String name;

    private String identityId; // 身份证

    private String gender;

    private String birthPlace; // 出生地

    private String birthDate; // 出生时间

    private String tel; // 电话

    private String bloodType; // 血型

    private String medicalHistory; // 病史信息

    private String remark; // 备注

    private Date createTime;
}
