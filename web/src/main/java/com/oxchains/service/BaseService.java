package com.oxchains.service;

import com.google.common.base.Charsets;
import com.oxchains.bean.dto.RespDTO;
import com.oxchains.handler.FluentResponseHandler;
import com.oxchains.model.MedicalRecord;
import com.oxchains.service.dto.ChaincodeResultDTO;
import com.oxchains.util.ChaincodeJsonrpcUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

/**
 * BaseService
 *
 * @author liuruichao
 * Created on 2017/3/11 11:41
 */
@Slf4j
public abstract class BaseService {
    @Value("${chaincodeID}")
    protected String chaincodeID;

    @Value("${fabric.peer.address}")
    protected String fabricPeerAddress;

    protected RespDTO<String> sendChaincodeJsonrpcReq(String jsonrpc) throws IOException {
        String result = Request.Post(fabricPeerAddress + "/chaincode")
                .bodyByteArray(jsonrpc.getBytes(Charsets.UTF_8))
                .execute()
                .handleResponse(new FluentResponseHandler());
        ChaincodeResultDTO chaincodeResultDTO = ChaincodeResultDTO.parseObject(result);
        if (chaincodeResultDTO.getStatus() == 1) {
            return RespDTO.success(chaincodeResultDTO.getMessage(), chaincodeResultDTO.getData());
        }
        return RespDTO.fail(chaincodeResultDTO.getMessage() + "_" + chaincodeResultDTO.getData());
    }

    protected String genQuerySql(String dataItem, String id) {
        StringBuilder sbu = new StringBuilder();
        sbu.append("SELECT ");
        if (!dataItem.equals("*")) {
            sbu.append("user_id, ");
        }
        sbu.append(dataItem)
                .append(" FROM medical_record where id=").append(id);
        return sbu.toString();
    }

    public String getBalance(String cusName) {
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getSummary", cusName);
            RespDTO<String> stringRespDTO = sendChaincodeJsonrpcReq(jsonrpc);
            String message = stringRespDTO.getMessage();
            if (stringRespDTO.getStatus() == 1) {
                // 查询sql
                return message.split(ChaincodeJsonrpcUtils.ITEM_SP)[3];
            }
        } catch (Exception e) {
            log.error("getBalance error!", e);
        }
        return null;
    }

    protected String getQuerySql(String cusName, String recordId) {
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
        }
        return null;
    }
}
