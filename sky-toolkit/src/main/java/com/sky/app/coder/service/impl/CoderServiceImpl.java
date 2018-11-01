package com.sky.app.coder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.app.coder.model.Element;
import com.sky.app.coder.service.ICoderService;

@Service("CoderService")
public class CoderServiceImpl implements ICoderService {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlsession;

	@Override
	public List<Element> getTagInfo(String sqlId,String moduCode) {
		return sqlsession.selectList(sqlId,moduCode);
	}
}
