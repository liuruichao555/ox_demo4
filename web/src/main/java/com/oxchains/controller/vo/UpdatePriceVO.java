package com.oxchains.controller.vo;

import com.oxchains.bean.base.BaseEntity;
import lombok.Data;

/**
 * UpdatePriceVO
 *
 * @author liuruichao
 * Created on 2017/3/11 15:17
 */
@Data
public class UpdatePriceVO extends BaseEntity {
    // userId, recordId, price, condition[dataItem, query, hash, deadline])
    private String userId;

    private String recordId;

    private String price;

    private String dataItem;
}
