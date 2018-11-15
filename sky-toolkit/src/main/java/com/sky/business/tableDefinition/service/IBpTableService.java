package com.sky.business.tableDefinition.service;

import java.util.Map;

import com.sky.business.tableDefinition.model.BpTable;
import com.sky.core.base.service.IBaseService;

public interface IBpTableService extends IBaseService<BpTable> {

	int creatTable(String sqlId, Map<String, Object> map);

	int dropTab(String sqlId, String parameter);
}

