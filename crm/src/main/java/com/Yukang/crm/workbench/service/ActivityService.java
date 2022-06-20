package com.Yukang.crm.workbench.service;

import com.Yukang.crm.vo.PaginationVo;
import com.Yukang.crm.workbench.domain.Activity;
import com.Yukang.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListById(String aid);

    boolean deleteRemark(String id);

    boolean addRemark(ActivityRemark ar);

    boolean editRemark(ActivityRemark ar);
}
