package com.oxchains.controller;

import com.oxchains.common.BaseController;
import com.oxchains.bean.dto.RespDTO;
import com.oxchains.controller.vo.Record;
import com.oxchains.controller.vo.Summary;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.model.Customer;
import com.oxchains.model.MedicalRecord;
import com.oxchains.service.CustomerService;
import com.oxchains.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * CommonController
 *
 * @author liuruichao
 * Created on 2017/3/11 10:02
 */
@Controller
@RequestMapping("")
@Slf4j
public class CommonController extends BaseController {
    @Resource
    private CustomerService customerService;

    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private HospitalService hospitalService;

    @RequestMapping("/")
    public String login() {
        return "customer/login";
    }

    @RequestMapping("/getSummary")
    @ResponseBody
    public RespDTO<List<Summary>> test3(@RequestParam String name, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute(LOGIN_USER_SESSION_KEY);
        if (customer == null) {
            return RespDTO.fail("请登录");
        }
        return customerService.getSummary(name, customer.getCusName());
    }

    @RequestMapping("/predata")
    @ResponseBody
    public String predata() {
        customerService.registerHis("xingshi");
        customerService.registerHis("zilong");
        customerService.registerHis("binghe");
        customerService.registerHis("yihui");
        customerService.registerHis("shun");
        customerService.registerHis("bjdxgjyy");
        customerService.registerHis("fddxfshsyy");
        customerService.registerHis("bjdxszyy");

        List<MedicalRecord> list1 = medicalRecordMapper.findByHis1();
        List<MedicalRecord> list2 = medicalRecordMapper.findByHis2();
        list1.addAll(list2);
        setCustomer(list1);

        for (MedicalRecord medicalRecord : list1) {
            Record record = new Record();
            record.setRecordId(medicalRecord.getId() + "");
            record.setOwnerId(medicalRecord.getCustomer().getCusName());
            if (medicalRecord.getCustomer().getId() == 2
                    || medicalRecord.getCustomer().getId() == 4) {
                record.setProviderId("bjdxgjyy");
            } else if (medicalRecord.getCustomer().getId() == 1
                    || medicalRecord.getCustomer().getId() == 3
                    || medicalRecord.getCustomer().getId() == 5) {
                record.setProviderId("fddxfshsyy");
            }
            record.setAccessInfo("127.0.0.1:8080");
            record.setDataItem("*");
            hospitalService.addRecord(record);
        }
        return "success";
    }

    private void setCustomer(List<MedicalRecord> list) {
        for (MedicalRecord medicalRecord : list) {
            medicalRecord.setCustomer(customerService.getUserById(medicalRecord.getUserId()));
        }
    }
}
