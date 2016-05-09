package com.ms.admin.vo.user;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * Created by mark.zhu on 2016/2/29.
 */
public class UserVo {
    private Date beginTime;
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("beginTime", beginTime)
                .add("endTime", endTime)
                .toString();
    }
}
