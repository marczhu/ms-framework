package com.ms.framework.domain.account;

import com.google.common.base.MoreObjects;
import com.ms.framework.domain.BaseDomain;

/**
 * Created by mark.zhu on 2015/12/15.
 */
public class Permission extends BaseDomain {
    public static int PERMISSION_TYPE_MENU_RESOURCE = 1;
    private int type;
    private int resourceId;
    private String expression;
    private String remark;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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
                .add("type", type)
                .add("resourceId", resourceId)
                .add("expression", expression)
                .add("remark", remark)
                .toString();
    }
}
