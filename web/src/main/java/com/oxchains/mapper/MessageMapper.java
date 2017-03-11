package com.oxchains.mapper;

import com.oxchains.model.Message;

import java.util.List;

/**
 * MessageMapper
 *
 * @author liuruichao
 * Created on 2017/3/11 13:09
 */
public interface MessageMapper {
    List<Message> findByUserId(Integer userId);

    void save(Message message);

    void updateStatus(Integer id);
}
