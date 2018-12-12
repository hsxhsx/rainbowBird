/**
 * Copyright(C) 2018 Fugle Technology Co., Ltd. All rights reserved.
 */
package cn.rf.loan.server.controller;

import cn.rf.loan.server.dao.model.ZfbIndex;
import cn.rf.loan.server.service.TestService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @create: 2018-12-12 20:17
 * @author: KongMingliang
 * @since: jdk 1.8
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public String test(){
        return "666";
    }

    @RequestMapping(value = "/getZfbIndex",method = RequestMethod.GET)
    public Map<String,Object> getZfbIndex(String orgId,String code,Long id){
        LOGGER.debug("orgId={},code={},id={}",orgId,code,id);
        Map<String,Object> result = new HashMap<>();
        ZfbIndex zfbIndex = testService.getZfbIndex(orgId, code);
        result.put("data",zfbIndex);
        return result;
    }
}
