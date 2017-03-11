package com.oxchains.controller.vo;

import com.oxchains.bean.dto.RespDTO;
import com.oxchains.common.BaseController;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.model.Customer;
import com.oxchains.model.MedicalRecord;
import com.oxchains.service.BaseService;
import com.oxchains.service.CustomerService;
import com.oxchains.service.HospitalService;
import com.oxchains.service.ThirdpartyService;
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
 * ThirdpartyController
 *
 * @author liuruichao
 * Created on 2017/3/11 14:50
 */
@Controller
@RequestMapping("/thirdparty")
@Slf4j
public class ThirdpartyController extends BaseController {
    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private ThirdpartyService thirdpartyService;

    @RequestMapping("")
    public String home() {
        return "thirdparty/home";
    }

    @RequestMapping("/search")
    @ResponseBody
    public RespDTO<List<SearchVO>> search(@RequestParam String name, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute(LOGIN_USER_SESSION_KEY);
            if (customer == null) {
                return RespDTO.fail("请登录");
            }
            return thirdpartyService.search(name, customer.getCusName());
        } catch (Exception e) {
            log.error("thirdparty serach error!", e);
            return RespDTO.fail("系统繁忙，请稍后再试!");
        }
    }
}