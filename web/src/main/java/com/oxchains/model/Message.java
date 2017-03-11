package com.oxchains.model;

import com.oxchains.bean.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * Message
 *
 * @author liuruichao
 * Created on 2017/3/11 11:26
 */
@Data
public class Message extends BaseEntity {
    private Integer id;

    private Integer fromUserId;

    private Integer toUserId;

    private String content;

    private Integer status; // 0: 未处理   1:已处理

    private String remark; // 备注

    private Date createTime;

    private Customer fromUser;
}
