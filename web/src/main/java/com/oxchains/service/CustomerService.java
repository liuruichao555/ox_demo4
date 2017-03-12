package com.oxchains.service;

import com.oxchains.bean.dto.RespDTO;
import com.oxchains.controller.vo.CusShareInfoVO;
import com.oxchains.controller.vo.Summary;
import com.oxchains.controller.vo.UpdatePermissionVO;
import com.oxchains.controller.vo.UpdatePriceVO;
import com.oxchains.mapper.CustomerMapper;
import com.oxchains.mapper.MedicalRecordMapper;
import com.oxchains.mapper.MessageMapper;
import com.oxchains.mapper.UserMapper;
import com.oxchains.model.Customer;
import com.oxchains.model.MedicalRecord;
import com.oxchains.model.Message;
import com.oxchains.model.User;
import com.oxchains.service.dto.ChaincodeResultDTO;
import com.oxchains.util.ChaincodeJsonrpcUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * UserService
 *
 * @author liuruichao
 * Created on 2017/3/11 11:40
 */
@Service
@Slf4j
public class CustomerService extends BaseService {
    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private HospitalService hospitalService;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    public int addCustomer(Customer customer) {
        return customerMapper.save(customer);
    }

    public RespDTO<String> registerHis(String name) {
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genInvokeJsonReqStr(chaincodeID, "register", name, "1000");
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (IOException e) {
            log.error("register fabric error! name: " + name, e);
            return RespDTO.fail();
        }
    }

    public RespDTO<List<Summary>> getSummary(String name, String loginName) {
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getSummary", name);
            RespDTO<String> stringRespDTO = sendChaincodeJsonrpcReq(jsonrpc);
            String message = stringRespDTO.getMessage();
            if (stringRespDTO.getStatus() == 0) {
                return RespDTO.fail(message);
            }
            // 查询sql
            String list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[2];
            String[] items = list.split(ChaincodeJsonrpcUtils.LIST_SP);
            List<Summary> summaryList = new ArrayList<>();
            for (String item : items) {
                String[] attr = item.split(ChaincodeJsonrpcUtils.MIN_SP);
                String recordId = attr[0];
                String status = attr[1];
                if (!"0".equals(recordId)) {
                    RespDTO<String> recordDTO = hospitalService.getRecord(name, recordId);
                    message = recordDTO.getMessage();
                    if (recordDTO.getStatus() == 0) {
                        return RespDTO.fail(message);
                    }
                    list = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[3];
                    String[] recordItems = list.split(ChaincodeJsonrpcUtils.LIST_SP);
                    for (String recordItem : recordItems) {
                        String[] recordAttr = recordItem.split(ChaincodeJsonrpcUtils.MIN_SP);
                        String userId = recordAttr[0];
                        String query = recordAttr[2];
                        Summary summary = new Summary();
                        summary.setRecordId(recordId);
                        summary.setQuery(query);
                        RespDTO<String> permissionDTO = hospitalService.havePermission(loginName, recordId, query);
                        summary.setHavePermission(permissionDTO.getMessage());
                        summary.setUserId(userId);
                        summary.setOwnerId(name);
                        summary.setMedicalRecord(medicalRecordMapper.findById(Integer.valueOf(recordId)));

                        summaryList.add(summary);
                    }
                }
            }
            return RespDTO.success(summaryList);
        } catch (Exception e) {
            log.error("get summary error! name: " + name, e);
            return RespDTO.fail();
        }
    }

    public RespDTO<String> confirmSummary(String ownerId, String recordId, String op) {
        // 接收为“ac”，拒绝为“re”
        try {
            String jsonrpc = ChaincodeJsonrpcUtils.genInvokeJsonReqStr(chaincodeID, "confirmSummary", ownerId, recordId, op);
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error(String.format("confirm summary error! ownerId: %s, recordId: %s, op: %s.", ownerId, recordId, op));
            return RespDTO.fail();
        }
    }

    public RespDTO<String> updatePermission(UpdatePermissionVO updatePermissionVO) {
        try {
            // recordId，ownerId，[consumer, dataItem, query, hash, deadline]
            String jsonrpc = ChaincodeJsonrpcUtils.genInvokeJsonReqStr(chaincodeID, "updatePermission",
                                                   updatePermissionVO.getRecordId(), updatePermissionVO.getOwnerId(),
                                                   updatePermissionVO.getConsumer(), updatePermissionVO.getDataItem(),
                                                   genQuerySql(updatePermissionVO.getDataItem(), updatePermissionVO.getRecordId()), updatePermissionVO.hashCode() + "",
                                                   "2020/10/10");
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error("update permission error! updatePermissionVO: " + updatePermissionVO, e);
            return RespDTO.fail();
        }
    }

    public Customer getUserById(Integer id) {
        return customerMapper.findById(id);
    }

    public RespDTO<Customer> login(String username, String password) {
        try {
            Customer customer = customerMapper.findByUsername(username);
            if (customer == null) {
                return RespDTO.fail("没有这个用户!");
            }
            if (!password.equals(customer.getPassword())) {
                return RespDTO.fail("密码错误!");
            }
            return RespDTO.success(customer);
        } catch (Exception e) {
            log.error(String.format("login error.username: %s, password: %s.", username, password), e);
            return RespDTO.fail("系统繁忙，请稍后再试!");
        }
    }

    public RespDTO<String> consent(String recordId, String userId, String ownerName) {
        Message message = new Message();
        Customer customer = customerMapper.findById(Integer.valueOf(userId));
        Customer owner = customerMapper.findByUsername(ownerName);
        MedicalRecord medicalRecord = medicalRecordMapper.findById(Integer.valueOf(recordId));

        message.setFromUserId(Integer.valueOf(userId));
        message.setToUserId(owner.getId());
        message.setContent(customer.getRealName() + " 想要访问您“" + medicalRecord.getDiagnoseInfo() + "”》的病历，是否同意？");
        message.setStatus(0);
        message.setRemark(recordId);
        message.setCreateTime(new Date());

        messageMapper.save(message);
        return RespDTO.success();
    }

    public List<CusShareInfoVO> getShareInfoList(Customer customer) {
        List<CusShareInfoVO> list = null;

        List<MedicalRecord> medicalRecords = medicalRecordMapper.findByUserId(customer.getId());
        if (medicalRecords != null && medicalRecords.size() > 0) {
            list = new ArrayList<>();
            for (MedicalRecord record : medicalRecords) {
                String jsonrpc = ChaincodeJsonrpcUtils.genQueryJsonReqStr(chaincodeID, "getRecord", customer.getCusName(), record.getId() + "");
                try {
                    RespDTO<String> respDTO = sendChaincodeJsonrpcReq(jsonrpc);
                    String message = respDTO.getMessage();
                    if (StringUtils.isNotBlank(message)) {
                        // xingshi^&**^&*select * from medical_record where id = 2^&*596317730^&*2020/10/10!@#$bjdxgjyy^&**^&*SELECT * FROM medical_record where id^&*-914286821^&*2020/10/10
                        String listStr = message.split(ChaincodeJsonrpcUtils.ITEM_SP)[3];
                        String[] items = listStr.split(ChaincodeJsonrpcUtils.LIST_SP);
                        for (String item : items) {
                            String[] attr = item.split(ChaincodeJsonrpcUtils.MIN_SP);
                            String name = attr[0];
                            if (!customer.getCusName().equals(name)) {
                                CusShareInfoVO vo = new CusShareInfoVO();
                                vo.setDiagnoseInfo(record.getDiagnoseInfo());
                                Customer cus = customerMapper.findByUsername(name);
                                vo.setHospitalName(cus.getRealName());
                                list.add(vo);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(String.format("get record error! cusName: %s, recordId: %s.", customer.getCusName(), record.getId()), e);
                }
            }
        }

        return list;
    }

    /**
     * 电子病历定价
     * @param updatePriceVO
     * @return
     */
    public RespDTO<String> cusSetPrice(UpdatePriceVO updatePriceVO) {
        try {
            MedicalRecord medicalRecord = medicalRecordMapper.findById(Integer.valueOf(updatePriceVO.getRecordId()));
            medicalRecord.setPrice(Float.valueOf(updatePriceVO.getPrice()));
            medicalRecordMapper.updatePrice(medicalRecord);
            // userId, recordId, price, condition[dataItem, query, hash, deadline]
            String jsonrpc = ChaincodeJsonrpcUtils.genInvokeJsonReqStr(chaincodeID, "setCondition",
                    updatePriceVO.getUserId(), updatePriceVO.getRecordId(), updatePriceVO.getPrice(),
                    updatePriceVO.getDataItem(), genQuerySql(updatePriceVO.getDataItem(), updatePriceVO.getRecordId()),
                    updatePriceVO.hashCode() + "", "2020/10/10");
            return sendChaincodeJsonrpcReq(jsonrpc);
        } catch (Exception e) {
            log.error("customer set Price error! updatePriceVO: " + updatePriceVO, e);
            return RespDTO.fail();
        }
    }
}
