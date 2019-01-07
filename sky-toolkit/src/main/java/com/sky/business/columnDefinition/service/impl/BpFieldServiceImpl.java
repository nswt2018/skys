package com.sky.business.columnDefinition.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.business.columnDefinition.model.BpField;
import com.sky.business.columnDefinition.service.IBpFieldService;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("bpFieldService")
public class BpFieldServiceImpl extends BaseServiceImpl<BpField> implements IBpFieldService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int delField(String sqlId, Map<String, String> map) {
	
		return sqlSessionTemplate.delete(sqlId, map);
	}

	@Override
	public int backupTab(String sqlId, Map<String, String> map) {
		return sqlSessionTemplate.insert(sqlId, map);
	}

}

