package com.sky.business.businessUnit.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.business.businessUnit.model.BpUnit;
import com.sky.business.businessUnit.service.IBpUnitService;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("bpUnitService")
public class BpUnitServiceImpl extends BaseServiceImpl<BpUnit> implements IBpUnitService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int delUnit(String sqlId, Map<String, String> map) {
		return sqlSessionTemplate.delete(sqlId, map);
	}

}

