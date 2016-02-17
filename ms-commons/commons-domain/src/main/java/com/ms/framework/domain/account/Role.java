package com.ms.framework.domain.account;

import com.google.common.base.MoreObjects;
import com.ms.framework.domain.BaseDomain;

/**
 * Created by mark.zhu on 2015/12/15.
 */
public class Role extends BaseDomain {
    private String name;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("remark", remark)
                .toString();
    }
}
