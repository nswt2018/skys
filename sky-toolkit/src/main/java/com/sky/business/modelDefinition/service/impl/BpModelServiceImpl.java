package com.sky.business.modelDefinition.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.business.modelDefinition.model.BpModel;
import com.sky.business.modelDefinition.service.IBpModelService;
import com.sky.business.systemModule.model.BpModule;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("bpModelService")
public class BpModelServiceImpl extends BaseServiceImpl<BpModel> implements IBpModelService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<BpModule> findModuForPageList(String sqlId, Map<String, Object> map) {
		return sqlSessionTemplate.selectList(sqlId, map);
	}

	@Override
	public int countModu(String sqlId, Map<String, Object> map) {
		return sqlSessionTemplate.selectOne(sqlId, map);
	}

}

