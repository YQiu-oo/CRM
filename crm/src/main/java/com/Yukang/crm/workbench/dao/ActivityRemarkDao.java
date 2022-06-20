package com.Yukang.crm.workbench.dao;

import com.Yukang.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListById(String aid);

    int deleteRemark(String id);

    int addRemark(ActivityRemark ar);

    int editRemark(ActivityRemark ar);
}
