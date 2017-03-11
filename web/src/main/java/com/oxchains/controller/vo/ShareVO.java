package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import com.oxchains.model.MedicalRecord;
import lombok.Data;

/**
 * ShareVO
 *
 * @author liuruichao
 * Created on 2017/3/11 23:57
 */
@Data
public class ShareVO extends BaseEntity {
    private MedicalRecord medicalRecord;

    private String query;
}
