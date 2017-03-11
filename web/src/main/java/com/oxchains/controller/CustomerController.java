package com.oxchains.controller;

import com.oxchains.bean.dto.RespDTO;
import com.oxchains.common.BaseController;
import com.oxchains.controller.vo.CusShareInfoVO;
import com.oxchains.controller.vo.UpdatePermissionVO;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.mapper.MessageMapper;
import com.oxchains.model.Customer;
import com.oxchains.model.MedicalRecord;
import com.oxchains.model.Message;
import com.oxchains.service.CustomerService;
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
 * CustomerController
 *
 * @author liuruichao
 * Created on 2017/3/11 10:53
 */
@Controller
@RequestMapping("/customer")
@Slf4j
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private MessageMapper messageMapper;

    @RequestMapping("")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute(LOGIN_USER_SESSION_KEY);
        if (customer == null) {
            return "redirect:/";
        }
        List<MedicalRecord> list = medicalRecordMapper.findByUserId(customer.getId());
        List<Message> messages = messageMapper.findByUserId(customer.getId());
        if (messages != null && messages.size() > 0) {
            for (Message message : messages) {
                message.setFromUser(customerService.getUserById(message.getFromUserId()));
            }
        }

        request.setAttribute("list", list);
        request.setAttribute("messages", messages);
        request.setAttribute("shareInfoList", customerService.getShareInfoList(customer));
        return "customer/home";
    }

    @RequestMapping("/share")
    public String share(@RequestParam Integer id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "customer/share";
    }

    @RequestMapping("/shareMedicalRecord")
    @ResponseBody
    public RespDTO<String> shareMedicalRecord(@RequestParam Integer id,
                                              @RequestParam String consumer,
                                              @RequestParam String dataItem,
                                              HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute(LOGIN_USER_SESSION_KEY);
            if (customer == null) {
                return RespDTO.fail("请登录后重试！");
            }
            // recordId=his1_0002&ownerId=xiaoming&consumer=his2&dataItem=name-age
            UpdatePermissionVO updatePermissionVO = new UpdatePermissionVO();
            updatePermissionVO.setRecordId(id + "");
            updatePermissionVO.setOwnerId(customer.getCusName());
            updatePermissionVO.setConsumer(consumer);
            updatePermissionVO.setDataItem(dataItem);
            return customerService.updatePermission(updatePermissionVO);
        } catch (Exception e) {
            log.error("share error!", e);
            return RespDTO.fail("系统繁忙，请稍后再试！");
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public RespDTO<Customer> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        RespDTO<Customer> respDTO = customerService.login(username, password);
        if (respDTO.getStatus() == 1) {
            HttpSession session = request.getSession();
            session.setAttribute(LOGIN_USER_SESSION_KEY, respDTO.getData());
        }
        return respDTO;
    }

    @RequestMapping("/confirmSummary")
    @ResponseBody
    public RespDTO<String> confirmSummary(@RequestParam String ownerId, @RequestParam String recordId, @RequestParam String op) {
        return customerService.confirmSummary(ownerId, recordId, op);
    }

    @RequestMapping("/updatePermission")
    @ResponseBody
    public RespDTO<String> updatePermission(UpdatePermissionVO updatePermissionVO) {
        return customerService.updatePermission(updatePermissionVO);
    }

    @RequestMapping("/updateMsgStatus")
    @ResponseBody
    public RespDTO<String> updateMsgStatus(@RequestParam Integer id) {
        messageMapper.updateStatus(id);
        return RespDTO.success();
    }
}
