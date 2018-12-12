package cn.rf.loan.server.service.impl;

import cn.rf.loan.server.dao.model.BaseModel;
import cn.rf.loan.server.service.BaseService;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuzaizhou
 * @date 2018/12/3
 */
public class BaseServiceImpl<T extends BaseModel, M extends BaseMapper<T>> implements BaseService<T> {
    Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected M mapper;

    public BaseServiceImpl() {
    }
}
