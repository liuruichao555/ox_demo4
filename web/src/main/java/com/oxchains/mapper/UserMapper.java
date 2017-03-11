package com.oxchains.mapper;

import com.oxchains.model.User;

/**
 * UserMapper
 *
 * @author liuruichao
 * Created on 2017/3/11 13:38
 */
public interface UserMapper {
    User findById(Integer id);

    User findByName(String userId);
}
