package com.oxchains.service.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oxchains.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * chaincode 返回结果
 *
 * @author liuruichao
 * Created on 2017/3/11 14:22
 */
@Getter
@Setter
@Slf4j
@ToString
public class ChaincodeResultDTO extends BaseEntity {
    private int status = 0;

    private String message;

    private String data;

    private ChaincodeResultDTO(){
    }

    public static ChaincodeResultDTO parseObject(String json) {
        ChaincodeResultDTO chaincodeResultDTO = new ChaincodeResultDTO();

        try {
            // {"jsonrpc":"2.0","error":{"code":-32003,"message":"Query failure","data":"Error when querying chaincode: Error:Transaction or query returned with failure: don't have this record"},"id":5}
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject resultObj = jsonObject.getJSONObject("result");
            if (resultObj != null) {
                if ("OK".equals(resultObj.getString("status"))) {
                    chaincodeResultDTO.status = 1;
                }
                chaincodeResultDTO.message = resultObj.getString("message");
            } else {
                JSONObject errorObj = jsonObject.getJSONObject("error");
                chaincodeResultDTO.message = errorObj.getString("message");
                chaincodeResultDTO.data = errorObj.getString("data");
            }
        } catch (Exception e) {
            log.error("parse chaincode json result error!", e);
            chaincodeResultDTO.status = 0;
        }

        return chaincodeResultDTO;
    }
}