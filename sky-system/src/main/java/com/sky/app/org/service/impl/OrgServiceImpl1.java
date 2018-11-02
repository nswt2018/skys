package com.sky.app.org.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.app.org.model.Org1;
import com.sky.app.org.service.IOrgService1;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("orgservice1")
public class OrgServiceImpl1 extends BaseServiceImpl<Org1> implements IOrgService1{
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlsession;
	@Override
	public List<Org1> getOrgList(String sqlid, Object obj) {
		return sqlsession.selectList(sqlid, obj);
	}
	@Override
	public int insertOrg(String sqlid, Object obj) {
		return sqlsession.insert(sqlid, obj);
	}
	@Override
	public int updateOrg(String sqlid, Object obj) {
		return sqlsession.update(sqlid,obj);
	}
	@Override
	public int deleteOrg(String sqlid, String orgid) {
		return sqlsession.delete(sqlid, orgid);
	}

}
