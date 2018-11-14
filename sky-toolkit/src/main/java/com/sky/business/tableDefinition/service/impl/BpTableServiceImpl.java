package com.sky.business.tableDefinition.service.impl;


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

}

