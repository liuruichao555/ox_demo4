package com.oxchains.service;

import com.oxchains.bean.dto.RespDTO;
import com.oxchains.controller.vo.Record;
import com.oxchains.controller.vo.SearchVO;
import com.oxchains.controller.vo.ShareVO;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.mapper.UserMapper;
import com.oxchains.model.MedicalRecord;
import com.oxchains.util.ChaincodeJsonrpcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * HospitalService
 *
 * @author liuruichao
 * Created on 2017/3/11 11:13
 */
@Service
@Slf4j
public class HospitalService extends BaseService {
    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * !!!初始化chain记录使用!!!
     * @param record
     * @return
     */
    public RespDTO<String> addRecord(Record record) {
        try {
            // recordId，ownerId，providerId，accessInfo，permission[dataItem,  query, hash, deadline]
            // "00002","xiaoming","his1","ip:port","name-sex-more","select name,sex,more from xx;","123123","2020/10/10"
            //String querySql = genQuerySql(record.getDataItem());
            String querySql = "select * from medical_record where id = " + record.getRecordId();
            String jsonrpc = ChaincodeJsonrpcUtils.genInvokeJsonReqStr(chaincodeID, "add", record.getRecordId(),
                                                       record.getOwnerId(), record.getProviderId(), record.getAccessInfo(),
                                                       record.getDataItem(), querySql, record.hashCode() + "", "2020/10/10");
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error("add record error! record: " + record, e);
            return RespDTO.fail();
        }
    }

    public RespDTO<String> getRecord(String name, String recordId) {
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getRecord", name, recordId);
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error(String.format("get record error! name: %s, recordId: %s.", name, recordId), e);
            return RespDTO.fail();
        }
    }

    public RespDTO<String> havePermission(String name, String recordId, String query) {
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "havePermission", name, recordId, query);
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error(String.format("have permission error! name: %s, recordId: %s, query: %s.", name, recordId, query), e);
            return RespDTO.fail();
        }
    }

    public List<MedicalRecord> getRecords(String cusName) {
        List<MedicalRecord> list = null;
        if (cusName.equals("bjdxgjyy")) {
            list = medicalRecordMapper.findByHis1();
        } else if (cusName.equals("fddxfshsyy")) {
            list = medicalRecordMapper.findByHis2();
        }
        if (list != null && list.size() > 0) {
            for (MedicalRecord medicalRecord : list) {
                medicalRecord.setUser(userMapper.findById(medicalRecord.getUserId()));
                medicalRecord.setSource("本地");
            }
        }
        return list;
    }

    public List<ShareVO> getShareRecords(String cusName, List<MedicalRecord> localData) {
        String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getSummary", cusName);
        List<ShareVO> result = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        try {
            localData.forEach(medicalRecord -> map.put(medicalRecord.getId(), "ok"));
            RespDTO<String> stringRespDTO = sendChaincodeJsonrpcReq(jsonrpc);
            String message = stringRespDTO.getMessage();
            if (stringRespDTO.getStatus() == 0) {
                return null;
            }
            // 查询sql
            String list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[2];
            String[] items = list.split(ChaincodeJsonrpcUtils.LIST_SP);
            for (String item : items) {
                String[] attr = item.split(ChaincodeJsonrpcUtils.MIN_SP);
                String recordId = attr[0];
                String status = attr[1];

                if (!recordId.equals("0") && !map.containsKey(Integer.valueOf(recordId))) {
                    ShareVO shareVO = new ShareVO();
                    MedicalRecord medicalRecord = medicalRecordMapper.findById(Integer.valueOf(recordId));
                    medicalRecord.setUser(userMapper.findById(medicalRecord.getUserId()));
                    shareVO.setMedicalRecord(medicalRecord);
                    shareVO.setQuery(getQuerySql(cusName, recordId));

                    result.add(shareVO);
                }
            }
            return result;
        } catch (IOException e) {
            log.error("search error!", e);
        }
        return null;
    }
}
