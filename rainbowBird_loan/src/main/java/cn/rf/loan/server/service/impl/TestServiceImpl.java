/**
 * Copyright(C) 2018 Fugle Technology Co., Ltd. All rights reserved.
 */
package cn.rf.loan.server.service.impl;

import cn.rf.loan.server.dao.mapper.ZfbIndexMapper;
import cn.rf.loan.server.dao.model.ZfbIndex;
import cn.rf.loan.server.dao.model.ZfbIndexKey;
import cn.rf.loan.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @create: 2018-12-12 21:02
 * @author: KongMingliang
 * @since: jdk 1.8
 **/
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private ZfbIndexMapper zfbIndexMapper;

    @Override
    public ZfbIndex getZfbIndex(String orgId,String code) {
        ZfbIndexKey key = new ZfbIndexKey();
        key.setOrgId(orgId);
        key.setCode(code);
        return zfbIndexMapper.selectByPrimaryKey(key);
    }
}
