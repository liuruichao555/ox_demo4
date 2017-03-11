package com.oxchains.medical.controller;

import com.alibaba.fastjson.JSONObject;
import com.oxchains.medical.mongo.MongoDataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hugo on 3/11/17.
 */
@RestController
@Slf4j
public class DataController {
    @Autowired
    private MongoDataFetcher mongoDataFetcher;

    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/getRecord")
    @ResponseBody
    public String getRecord(@RequestBody String para) {
        log.info("GetRecord Request, parameter:{}", para);
        JSONObject result = new JSONObject();
        try {
            JSONObject paraJson = JSONObject.parseObject(para);
            int id = paraJson.getInteger("id");
            String keys = paraJson.getString("keys");
            String record = mongoDataFetcher.getMedicalDataByKeys(id, keys.trim().split(","));
            result.put("status", 0);
            result.put("data", record);
        } catch (Exception e) {
            result.put("status", -1);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        return result.toJSONString();
    }
}