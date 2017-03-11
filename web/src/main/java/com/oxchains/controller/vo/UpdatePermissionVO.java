package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import lombok.*;

/**
 * UpdatePermissionVO
 *
 * @author liuruichao
 * Created on 2017/3/11 20:09
 */
@Data
public class UpdatePermissionVO extends BaseEntity {
    // recordId，ownerId，[consumer, dataItem, query, hash, deadline]
    private String recordId;

    private String ownerId;

    private String consumer;

    private String dataItem;

    private String query;
}
