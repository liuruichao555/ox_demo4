package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import lombok.Data;

/**
 * CusShareInfoVO
 *
 * @author liuruichao
 * Created on 2017/3/11 14:29
 */
@Data
public class CusShareInfoVO extends BaseEntity {
    private String diagnoseInfo; // 病历信息

    private String hospitalName; // 医院名称
}
