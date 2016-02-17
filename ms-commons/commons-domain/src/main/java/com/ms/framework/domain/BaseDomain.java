package com.ms.framework.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * 领域模型基类
 * User: mark.zhu
 * Date: 14-3-17
 * Time: 下午1:39
 */
public abstract class BaseDomain implements Serializable {
    public static final int DELETED_YES = 1;
    public static final int DELETED_NO = -1;
    /**
     * 主键id
     */
    protected long id;
    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 更新时间
     */
    protected Date lastUpdateTime;
    /**
     * 删除标识
     */
    protected int deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return  MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("createTime", createTime)
                .add("lastUpdateTime", lastUpdateTime)
                .add("deleted", deleted)
                .toString();
    }
}
