package com.Yukang.crm.workbench.dao;

import com.Yukang.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    List<Activity> getDataListByCondition(Map<String, Object> map);

    int getTotalByConditions(Map<String, Object> map);

    int delete(String[] ids);

    Activity getActivity(String id);

    int update(Activity a);

    Activity detail(String id);
}
