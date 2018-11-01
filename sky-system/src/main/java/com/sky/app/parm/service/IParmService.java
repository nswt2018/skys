package com.sky.app.parm.service;

import com.sky.app.parm.model.Parm;
import com.sky.core.base.service.IBaseService;

/*
 *系统管理->系统参数    参数表service接口
 */

public interface IParmService extends IBaseService<Parm> {
	int insertParm(String sqlId,Object parameter);
	int updateParm(String sqlId,Object parameter);
	int deleteParm(String sqlId,String id);
}
