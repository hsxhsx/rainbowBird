package cn.rf.loan.server.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuzaizhou
 * @date 2018/12/3
 */
public abstract class BaseModel implements Serializable {

    private Long id;
    private String createUser;
    private Date   createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
