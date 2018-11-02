package com.sky.app.parm.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.app.parm.model.Parm;
import com.sky.app.parm.service.IParmService;
import com.sky.core.base.service.impl.BaseServiceImpl;
import com.sky.system.logdb.model.Loggg;

/*
 *系统管理->系统参数    参数表service实现类
 */
@Service("parmservice")
public class ParmServiceImpl extends BaseServiceImpl<Parm> implements IParmService {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlsession;

	@Override
	@Loggg
	public int insertParm(String sqlId, Object parameter) throws RuntimeException {
		int insertrows = sqlsession.insert(sqlId, parameter);
		return insertrows;
	}

	@Override
	@Loggg
	public int updateParm(String sqlId, Object parameter) throws RuntimeException {
		int updaterows = sqlsession.update(sqlId, parameter);
		return updaterows;
	}

	@Override
	@Loggg
	public int deleteParm(String sqlId, String paracode) throws RuntimeException {
		int deleterows = sqlsession.delete(sqlId, paracode);
		return deleterows;
	}
}
