package com.sky.business.tableDefinition.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.business.tableDefinition.model.BpTable;
import com.sky.business.tableDefinition.service.IBpTableService;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("bpTableService")
public class BpTableServiceImpl extends BaseServiceImpl<BpTable> implements IBpTableService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int creatTable(String sqlId, Map<String, Object> map) {
		return sqlSessionTemplate.insert(sqlId, map);
	}

	public int dropTab(String sqlId, String parameter) {
		return sqlSessionTemplate.update(sqlId, parameter);
	}
	
	public int updTab(String sqlId, String parameter) {
		return sqlSessionTemplate.update(sqlId, parameter);
	}

	@Override
	public List<String> findColumns(String sqlId, String parameter) {
		return sqlSessionTemplate.selectList(sqlId, parameter);
	}

	@Override
	public int insertBackUp(String sqlId, Map<String, Object> map) {
		return sqlSessionTemplate.insert(sqlId, map);
	}

}

