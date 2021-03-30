package com.yh.svc.gov.test.springboot1.manager;

import com.yh.svc.gov.test.springboot1.command.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 15:07 2019/12/30
 * @Modified by:
 */
@Service
public class LogDiagnoseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDiagnoseManager.class);

    public String process(Integer id, OrderVo orderVo){
        LOGGER.info("Start processing");
        return "success";
    }
}
