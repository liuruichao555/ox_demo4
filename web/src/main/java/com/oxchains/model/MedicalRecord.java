package com.oxchains.model;

import com.oxchains.bean.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * MedicalRecord
 *
 * @author liuruichao
 * Created on 2017/3/11 11:04
 */
@Data
public class MedicalRecord extends BaseEntity {
    private Integer id;

    private Integer userId;

    private String outpatientInfo; // 门诊信息

    private String diagnoseInfo; // 诊断信息

    private String medicineInfo; // 处方信息

    private String hospitalizationInfo; // 住院信息

    private String picInfo; // 拍片、图片等信息

    private String operationInfo; // 治疗信息

    private String doctorMessage; // 医生描述

    private String treatDuration; // 治疗时间

    private String remark; // 备注

    private Date createTime;

    private String hospitalName; // 医院名称

    private Float price; // 价格, 供第三方机构购买

    private Customer customer;

    private User user;

    private String source;

    private String query;
}
