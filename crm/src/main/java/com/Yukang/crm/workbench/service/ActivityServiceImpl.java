package com.Yukang.crm.workbench.service;

import com.Yukang.crm.settings.dao.UserDao;
import com.Yukang.crm.settings.domain.User;
import com.Yukang.crm.utils.ServiceFactory;
import com.Yukang.crm.utils.SqlSessionUtil;
import com.Yukang.crm.vo.PaginationVo;
import com.Yukang.crm.workbench.dao.ActivityDao;
import com.Yukang.crm.workbench.dao.ActivityRemarkDao;
import com.Yukang.crm.workbench.domain.Activity;
import com.Yukang.crm.workbench.domain.ActivityRemark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService{
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private  UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity a) {

        int count = activityDao.save(a);
        if (count != 1) {
            return false;

        }
        return true;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        int total = activityDao.getTotalByConditions(map);
        List<Activity> list = activityDao.getDataListByCondition(map);

        PaginationVo<Activity> pg = new PaginationVo<>();

        pg.setTotal(total);
        pg.setDataList(list);

        return pg;
    }

    @Override
    public boolean delete(String[] ids) {

        int count = activityRemarkDao.getCountByAids(ids);

        int count2 = activityRemarkDao.deleteByAids(ids);


        if (count != count2) {
            return false;

        }

        int count3 = activityDao.delete(ids);
        return true;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
       List<User> uList =  userDao.getUserList();
       Activity a = activityDao.getActivity(id);

        Map<String, Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("a", a);
        return map;
    }

    @Override
    public boolean update(Activity a) {

        int count = activityDao.update(a);
        if (count == 1) {
            return true;

        }
        return false;
    }

    @Override
    public Activity detail(String id) {
        Activity a = activityDao.detail(id);


        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListById(String aid) {

        List<ActivityRemark> l = activityRemarkDao.getRemarkListById(aid);
        return l;
    }

    @Override
    public boolean deleteRemark(String id) {

        int count = activityRemarkDao.deleteRemark(id);
        if (count == 1) {
            return true;

        }
        return false;

    }

    @Override
    public boolean addRemark(ActivityRemark ar) {

        int count = activityRemarkDao.addRemark(ar);

        if (count == 1) {
            return true;

        }
        return false;
    }

    @Override
    public boolean editRemark(ActivityRemark ar) {
        int count = activityRemarkDao.editRemark(ar);

        if (count == 1) {
            return true;

        }
        return false;



    }
}
