/**
 * Copyright(C) 2018 Fugle Technology Co., Ltd. All rights reserved.
 */
package cn.rf.loan.server.service;

import cn.rf.loan.server.dao.model.ZfbIndex;

/**
 * @create: 2018-12-12 21:01
 * @author: KongMingliang
 * @since: jdk 1.8
 **/
public interface TestService {

    ZfbIndex getZfbIndex(String orgId,String code);
}
