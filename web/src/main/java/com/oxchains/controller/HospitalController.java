package com.oxchains.controller;

import com.oxchains.bean.dto.RespDTO;
import com.oxchains.common.BaseController;
import com.oxchains.controller.vo.Record;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.mapper.UserMapper;
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
 * HospitalController
 *
 * @author liuruichao
 * Created on 2017/3/11 11:12
 */
@Controller
@RequestMapping("/hospital")
@Slf4j
public class HospitalController extends BaseController {
    @Resource
    private HospitalService hospitalService;

    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private CustomerService customerService;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute(LOGIN_USER_SESSION_KEY);
        if (customer == null) {
            return "redirect:/";
        }

        List<MedicalRecord> list = hospitalService.getRecords(customer.getCusName());
        request.setAttribute("list", list);
        return "hospital/home";
    }

    @RequestMapping("/recordDetail")
    public String recordDetail(@RequestParam String recordId, HttpServletRequest request) {

        String query = "select * from medical_record where id = " + recordId;

        MedicalRecord medicalRecord = medicalRecordMapper.execSql(query);
        medicalRecord.setUser(userMapper.findById(medicalRecord.getUserId()));
        request.setAttribute("detail", medicalRecord);

        return "hospital/detail";
    }

    @RequestMapping("/record/get")
    @ResponseBody
    public RespDTO<String> getRecord(String name, String recordId) {
        return hospitalService.getRecord(name, recordId);
    }

    @RequestMapping("/havePermission")
    @ResponseBody
    public RespDTO<String> havePermission(@RequestParam String name, @RequestParam String recordId, @RequestParam String query) {
        return hospitalService.havePermission(name, recordId, query);
    }

    @RequestMapping("/consent")
    @ResponseBody
    public RespDTO<String> consent(@RequestParam String recordId, @RequestParam String ownerName, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute(LOGIN_USER_SESSION_KEY);
        if (customer == null) {
            return RespDTO.fail("请登录");
        }
        return customerService.consent(recordId, customer.getId() + "", ownerName);
    }
}
