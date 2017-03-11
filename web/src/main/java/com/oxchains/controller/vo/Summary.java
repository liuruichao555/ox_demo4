package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import com.oxchains.model.MedicalRecord;
import com.oxchains.model.User;
import lombok.Data;

/**
 * Summary
 *
 * @author liuruichao
 * Created on 2017/3/11 10:34
 */
@Data
public class Summary extends BaseEntity {
    private String userId;

    private String recordId;

    private String havePermission;

    private String query;

    private String ownerId;

    private MedicalRecord medicalRecord;
}
