package com.sky.business.systemModule.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.business.systemModule.model.BpModule;
import com.sky.business.systemModule.service.IBpModuleService;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("bpModuleService")
public class BpModuleServiceImpl extends BaseServiceImpl<BpModule> implements IBpModuleService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<String> getTabInfo(String sqlId, String parameter) {
		return sqlSessionTemplate.selectList(sqlId, parameter);
	}

	public int delData(String sqlId, Map<String, String> map) {
		return sqlSessionTemplate.delete(sqlId, map);
	}
}

