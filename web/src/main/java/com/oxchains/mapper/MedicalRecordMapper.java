package com.oxchains.mapper;

import com.oxchains.model.MedicalRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MedicalRecordMapper
 *
 * @author liuruichao
 * Created on 2017/3/11 12:08
 */
public interface MedicalRecordMapper {
    MedicalRecord findById(Integer id);

    List<MedicalRecord> findByUserId(Integer id);

    List<MedicalRecord> findByHis1();

    List<MedicalRecord> findByHis2();

    MedicalRecord execSql(String sql);

    void updatePrice(@Param("medicalRecord") MedicalRecord medicalRecord);

    List<MedicalRecord> search(String name);
}
