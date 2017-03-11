package com.oxchains.mapper;

import com.oxchains.model.Customer;
import org.springframework.stereotype.Repository;

/**
 * UserMapper
 *
 * @author liuruichao
 * Created on 2017/3/11 11:40
 */
@Repository
public interface CustomerMapper {
    int save(Customer customer);

    Customer findById(Integer id);

    Customer findByUsername(String cusName);
}
