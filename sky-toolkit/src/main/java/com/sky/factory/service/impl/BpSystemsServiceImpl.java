package com.sky.factory.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.core.base.service.impl.BaseServiceImpl;
import com.sky.factory.model.BpSystems;
import com.sky.factory.service.IBpSystemsService;

@Service("bpSystemsService")
public class BpSystemsServiceImpl extends BaseServiceImpl<BpSystems> implements IBpSystemsService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<String> findMaxKey(String sqlId, String parameter) {
		return sqlSessionTemplate.selectList(sqlId, parameter);
	}

	public int deleteBySysKey(String sqlId, String parameter) {
		return sqlSessionTemplate.delete(sqlId, parameter);
	}
}

