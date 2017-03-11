package com.oxchains.service;

import com.oxchains.bean.dto.RespDTO;
import com.oxchains.controller.vo.SearchVO;
import com.oxchains.controller.vo.Summary;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.model.MedicalRecord;
import com.oxchains.util.ChaincodeJsonrpcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * ThirdpartyService
 *
 * @author liuruichao
 * Created on 2017/3/11 16:10
 */
@Service
@Slf4j
public class ThirdpartyService extends BaseService {
    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private HospitalService hospitalService;

    public RespDTO<List<SearchVO>> search(String name, String loginName) {
        List<MedicalRecord> records = medicalRecordMapper.search(name);
        String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getSummary", loginName);
        List<SearchVO> searchVOS = null;
        try {
            if (records != null && records.size() > 0) {
                searchVOS = new ArrayList<>(records.size());
                RespDTO<String> stringRespDTO = sendChaincodeJsonrpcReq(jsonrpc);
                String message = stringRespDTO.getMessage();
                if (stringRespDTO.getStatus() == 0) {
                    return RespDTO.fail(message);
                }
                // 查询sql
                String list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[2];
                String[] items = list.split(ChaincodeJsonrpcUtils.LIST_SP);
                Map<Integer, String> map = new HashMap<>();
                for (String item : items) {
                    String[] attr = item.split(ChaincodeJsonrpcUtils.MIN_SP);
                    String recordId = attr[0];
                    String status = attr[1];

                    // 有权限
                    map.put(Integer.valueOf(recordId), status);
                }
                for (MedicalRecord medicalRecord : records) {
                    SearchVO searchVO = new SearchVO();
                    searchVO.setMedicalRecord(medicalRecord);
                    if (map.containsKey(medicalRecord.getId())) {
                        searchVO.setHavePermission("True");
                        // 查询query
                        jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getRecord",
                                loginName, medicalRecord.getId() + "");
                        RespDTO<String> chainRecordResp = sendChaincodeJsonrpcReq(jsonrpc);
                        message = chainRecordResp.getMessage();
                        if (chainRecordResp.getStatus() == 0) {
                            return RespDTO.fail(message);
                        }
                        list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[3];
                        String[] recordItems = list.split(ChaincodeJsonrpcUtils.LIST_SP);
                        for (String recordItem : recordItems) {
                            String[] recordAttr = recordItem.split(ChaincodeJsonrpcUtils.MIN_SP);
                            String userId = recordAttr[0];
                            String query = recordAttr[2];
                            if (userId.equals(loginName)) {
                                searchVO.setQuery(query);
                                break;
                            }
                        }
                    } else {
                        searchVO.setHavePermission("False");
                    }
                    searchVOS.add(searchVO);
                }
            }
            return RespDTO.success(searchVOS);
        } catch (IOException e) {
            log.error("");
            return RespDTO.fail("系统繁忙，请稍后再试！");
        }
    }

    public RespDTO<String> payRecord(Integer recordId, String cusName) {
        MedicalRecord medicalRecord = medicalRecordMapper.findById(recordId);
        String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getSummary", cusName);
        try {
            RespDTO<String> stringRespDTO = sendChaincodeJsonrpcReq(jsonrpc);
            String message = stringRespDTO.getMessage();
            if (stringRespDTO.getStatus() == 0) {
                return RespDTO.fail(message);
            }
            // 查询sql
            String balance = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[3];
            if (Integer.valueOf(balance) < medicalRecord.getPrice()) {
                return RespDTO.fail("余额、积分不足");
            }
            jsonrpc = ChaincodeJsonrpcUtils.genInvokeJsonReqStr(chaincodeID, "payForRecord", cusName, recordId + "");
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error("payrecord error!", e);
            return RespDTO.fail();
        }
    }

    public List<MedicalRecord> getRecords(String cusName) {
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getSummary", cusName);
            RespDTO<String> stringRespDTO = sendChaincodeJsonrpcReq(jsonrpc);
            String message = stringRespDTO.getMessage();
            if (stringRespDTO.getStatus() == 0) {
                return null;
            }
            String list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[2];
            String[] items = list.split(ChaincodeJsonrpcUtils.LIST_SP);

            List<MedicalRecord> result = new ArrayList<>(items.length);
            Map<Integer, String> map = new HashMap<>();
            for (String item : items) {
                String[] attr = item.split(ChaincodeJsonrpcUtils.MIN_SP);
                String recordId = attr[0];
                String status = attr[1];

                if (!recordId.equals("0") && !map.containsKey(Integer.valueOf(recordId))) {
                    MedicalRecord medicalRecord = medicalRecordMapper.findById(Integer.valueOf(recordId));
                    medicalRecord.setQuery(querySql(cusName, recordId));
                    result.add(medicalRecord);
                }
                map.put(Integer.valueOf(recordId), cusName);
            }
            return result;
        } catch (Exception e) {
            log.error("get records error!", e);
            return null;
        }
    }

    private String querySql(String cusName, String recordId) {
        try {
            String query = null;
            // 查询query
            String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getRecord", cusName, recordId);
            RespDTO<String> chainRecordResp = sendChaincodeJsonrpcReq(jsonrpc);
            String message = chainRecordResp.getMessage();
            String list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[3];
            String[] recordItems = list.split(ChaincodeJsonrpcUtils.LIST_SP);
            for (String recordItem : recordItems) {
                String[] recordAttr = recordItem.split(ChaincodeJsonrpcUtils.MIN_SP);
                String userId = recordAttr[0];
                String query2 = recordAttr[2];
                if (userId.equals(cusName)) {
                    query = query2;
                }
            }
            return query;
        } catch (Exception e) {
            log.error("queySql error!", e);
            return null;
        }
    }
}
