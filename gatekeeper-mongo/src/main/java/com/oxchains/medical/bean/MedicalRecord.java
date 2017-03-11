package com.oxchains.medical.bean;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * MedicalRecord
 *
 * @author liuruichao
 * Created on 2017/3/11 11:50
 */
@Data
@Entity(value = "medical_record", noClassnameStored = true)
public class MedicalRecord{
    @Id
    private ObjectId _id;

    private Integer id;

    @Property("user_id")
    private Integer userId;

    @Property("outpatient_info")
    private String outpatientInfo; // 门诊信息

    @Property("diagnose_info")
    private String diagnoseInfo; // 诊断信息

    @Property("medicine_info")
    private String medicineInfo; // 处方信息

    @Property("hospitalization_info")
    private String hospitalizationInfo; // 住院信息

    @Property("pic_info")
    private String picInfo; // 拍片、图片等信息

    @Property("operation_info")
    private String operationInfo; // 治疗信息

    @Property("doctor_message")
    private String doctorMessage; // 医生描述

    @Property("treat_duration")
    private String treatDuration; // 治疗时间

    private String remark; // 备注

    @Property("create_time")
    private Date createTime;
}