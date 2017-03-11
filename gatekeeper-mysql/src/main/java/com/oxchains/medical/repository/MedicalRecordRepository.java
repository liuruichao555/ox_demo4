package com.oxchains.medical.repository;

import com.oxchains.medical.bean.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * MedicalRecordRepository
 *
 * @author liuruichao
 * Created on 2017/3/11 16:03
 */
@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Integer> {
}
