package com.oxchains.medical.controller;

import com.oxchains.bean.dto.RespDTO;
import lombok.extern.slf4j.Slf4j;
import com.oxchains.medical.bean.MedicalRecord;
import com.oxchains.medical.repository.MedicalRecordRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * DataController
 *
 * @author liuruichao
 * Created on 2017/3/11 15:35
 */
@RestController
@Slf4j
public class DataController {
    @Resource
    private MedicalRecordRepository medicalRecordRepository;

    @RequestMapping("/getRecord")
    @ResponseBody
    public RespDTO<MedicalRecord> getRecord(@RequestParam Integer recordId, @RequestParam String userName, @RequestParam String query) {
        log.info("get record, userName: {}, recordId: {}, query: {}", userName, recordId, query);
        try {
            // TODO 链上鉴权
            return RespDTO.success(medicalRecordRepository.findOne(recordId));
        } catch (Exception e) {
            log.error(String.format("get record error! userName: %s, recordId: %s, query: %s.", userName, recordId, query), e);
            return RespDTO.fail();
        }
    }
}
