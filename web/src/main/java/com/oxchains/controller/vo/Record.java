package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import lombok.*;

/**
 * 电子病历的来源记录
 *
 * @author liuruichao
 * Created on 2017/3/11 17:14
 */
@Data
public class Record extends BaseEntity {
    // recordId，ownerId，providerId，accessInfo，permission[dataItem,  query, hash, deadline]
    // "00002","xiaoming","his1","ip:port","name-sex-more","select name,sex,more from xx;","123123","2020/10/10"
    private String hospitalName;

    private String recordId;

    private String ownerId;

    private String providerId;

    private String accessInfo;

    private String dataItem;

    private String query;
}