package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UpdatePermissionVO
 *
 * @author liuruichao
 * Created on 2017/3/11 20:09
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdatePermissionVO extends BaseEntity {
    // recordId，ownerId，[consumer, dataItem, query, hash, deadline]
    private String recordId;

    private String ownerId;

    private String consumer;

    private String dataItem;

    private String query;
}
