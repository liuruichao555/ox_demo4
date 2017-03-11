package com.oxchains.medical.mongo;

import com.alibaba.fastjson.JSONObject;
import com.oxchains.medical.bean.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hugo on 3/11/17.
 */
@Service
public class MongoDataFetcher {
    public String getMedicalDataByKeys(int id, String[] keys) throws Exception {
        List<MedicalRecord> result = MongoDBClient.getInstance().datastore.createQuery(MedicalRecord.class).field("id").equal(id).asList();
        if (result.size() != 1){
            throw new Exception("Id Not Found.");
        }

        MedicalRecord medicalRecord = result.get(0);
        JSONObject queryJSON = JSONObject.parseObject(JSONObject.toJSONString(medicalRecord));

        JSONObject resutJSON = new JSONObject();
        for (String key : keys){
            resutJSON.put(key, queryJSON.get(key.trim()));
        }
        return resutJSON.toJSONString();
    }

    public static void main(String[] args) throws Exception {
        String[] list = new String[2];
        list[0] = "outpatientInfo";
        list[1] = "medicineInfo";
        String re = new MongoDataFetcher().getMedicalDataByKeys(1, list);
    }
}
