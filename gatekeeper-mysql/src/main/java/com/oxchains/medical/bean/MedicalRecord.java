package com.oxchains.medical.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * MedicalRecord
 *
 * @author liuruichao
 * Created on 2017/3/11 11:55
 */
@Entity
@Table(name = "medical_record")
@Data
public class MedicalRecord{

    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "outpatient_info")
    private String outpatientInfo; // 门诊信息

    @Column(name = "diagnose_info")
    private String diagnoseInfo; // 诊断信息

    @Column(name = "medicine_info")
    private String medicineInfo; // 处方信息

    @Column(name = "hospitalization_info")
    private String hospitalizationInfo; // 住院信息

    @Column(name = "pic_info")
    private String picInfo; // 拍片、图片等信息

    @Column(name = "operation_info")
    private String operationInfo; // 治疗信息

    @Column(name = "doctor_message")
    private String doctorMessage; // 医生描述

    @Column(name = "treat_duration")
    private String treatDuration; // 治疗时间

    @Column(name = "remark")
    private String remark; // 备注

    @Column(name = "create_time")
    private Date createTime;
}