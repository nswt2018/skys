package com.sky.business.pageElement.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.business.pageElement.model.BpElement;
import com.sky.business.pageElement.service.IBpElementService;
import com.sky.core.base.service.impl.BaseServiceImpl;

@Service("bpElementService")
public class BpElementServiceImpl extends BaseServiceImpl<BpElement> implements IBpElementService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int delData(String sqlId, Map<String, String> map) {
		
		return sqlSessionTemplate.delete(sqlId, map);
	}
}

