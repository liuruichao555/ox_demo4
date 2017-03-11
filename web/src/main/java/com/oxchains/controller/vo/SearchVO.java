package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import com.oxchains.model.MedicalRecord;
import lombok.Data;

/**
 * SearchVO
 *
 * @author liuruichao
 * Created on 2017/3/11 16:11
 */
@Data
public class SearchVO extends BaseEntity {
    private MedicalRecord medicalRecord;

    private String havePermission;

    private String query;
}
