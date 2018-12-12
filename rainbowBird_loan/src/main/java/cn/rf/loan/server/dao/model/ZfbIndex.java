package cn.rf.loan.server.dao.model;

import java.util.Date;

public class ZfbIndex extends ZfbIndexKey {
    private String name;

    private String shortName;

    private String desc;

    private String realCode;

    private Byte realField;

    private Byte type;

    private String relationIndex;

    private String collectIndex;

    private Byte status;

    private Date creationDate;

    private Date modificationDate;

    private Integer revision;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getRealCode() {
        return realCode;
    }

    public void setRealCode(String realCode) {
        this.realCode = realCode == null ? null : realCode.trim();
    }

    public Byte getRealField() {
        return realField;
    }

    public void setRealField(Byte realField) {
        this.realField = realField;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getRelationIndex() {
        return relationIndex;
    }

    public void setRelationIndex(String relationIndex) {
        this.relationIndex = relationIndex == null ? null : relationIndex.trim();
    }

    public String getCollectIndex() {
        return collectIndex;
    }

    public void setCollectIndex(String collectIndex) {
        this.collectIndex = collectIndex == null ? null : collectIndex.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
}