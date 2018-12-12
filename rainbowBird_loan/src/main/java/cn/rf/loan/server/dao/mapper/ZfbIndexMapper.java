package cn.rf.loan.server.dao.mapper;

import cn.rf.loan.server.dao.model.ZfbIndex;
import cn.rf.loan.server.dao.model.ZfbIndexKey;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ZfbIndexMapper {

    int deleteByPrimaryKey(ZfbIndexKey key);

    int insert(ZfbIndex record);

    int insertSelective(ZfbIndex record);

    ZfbIndex selectByPrimaryKey(ZfbIndexKey key);

    int updateByPrimaryKeySelective(ZfbIndex record);

    int updateByPrimaryKey(ZfbIndex record);
}