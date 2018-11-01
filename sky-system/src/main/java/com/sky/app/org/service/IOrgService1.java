package com.sky.app.org.service;

import java.util.List;

import com.sky.app.org.model.Org1;
import com.sky.core.base.service.IBaseService;

/*
 * 系统管理-机构信息  机构表service借口
 */
public interface IOrgService1 extends IBaseService<Org1>{
	List<Org1> getOrgList(String sqlid,Object obj);
	int insertOrg(String sqlid,Object obj);
	int updateOrg(String sqlid,Object obj);
	int deleteOrg(String sqlid,String id);
}
