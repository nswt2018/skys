package com.sky.business.tableDefinition.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.business.columnDefinition.model.BpField;
import com.sky.business.columnDefinition.service.IBpFieldService;
import com.sky.business.tableDefinition.model.BpTable;
import com.sky.business.tableDefinition.service.IBpTableService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpTableController extends BaseController {
	Log logger = LogFactory.getLog(BpTableController.class);
	
	private final IBpTableService bpTableService;
	private final IBpFieldService bpFieldService;

	@Autowired
	public BpTableController(final IBpTableService bpTableService, final IBpFieldService bpFieldService) {
		this.bpTableService = bpTableService;
		this.bpFieldService = bpFieldService;
	}
	
	@RequestMapping(value="/TK0007L.do")
	@ResponseBody
	public Mono<Page<BpTable>> getTabList(@RequestBody Page<BpTable> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpTableService.findForPageList("com.sky.business.tableDefinition.dao.BpTableDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	@PutMapping("/TK0007I.do")
	@ResponseBody
	public Mono<Message> saveTable(@RequestBody BpTable bpTable, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//查询数据库 该表是否存在
			List<BpField> list = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findTable", bpTable.getTabCode());
			//存在则报错返回
			if(list != null && list.size() > 0){
				return Mono.justOrEmpty(new Message("100001", "该表已经存在"));
			}
			//不存在插入到表定义中
			if(bpTableService.save(bpTable) > 0)
				return Mono.justOrEmpty(new Message("000001"));
			else
				return Mono.justOrEmpty(new Message("100001"));
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	@DeleteMapping("/TK0007D.do")
	@ResponseBody
	public Mono<Message> deleteByTabCode(@RequestParam String[] tabCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(tabCode==null || tabCode.length == 0)
				throw new BusinessException("000005");
			for(String id : tabCode) {
				bpTableService.delete(id);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0007U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpTable bpTable, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpTableService.update(bpTable)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping(value="/TK0007T.do")
	@ResponseBody
	public Mono<List<BpTable>> getTabList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<BpTable> list = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findAllTab", "");
		return Mono.justOrEmpty(list);
	}
	
	@RequestMapping("/TK0007G.do")
	@ResponseBody
	public Mono<Message> createTab(@RequestParam String[] tabCode, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(tabCode==null || tabCode.length == 0)
				return Mono.justOrEmpty(new Message("100001", "必须选中一条记录！"));
			
			for (String code : tabCode) {
				//查询数据库 该表是否存在
				List<BpField> list = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findTable", code);
				//存在则报错返回
				if(list != null && list.size() > 0)
					return Mono.justOrEmpty(new Message("100001", code + "表已经存在！"));
				
				//查询字段定义表 
				List<BpField> list1 = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findAllField", code);
				//没有记录则返回
				if(list1 == null || list1.size() == 0)
					return Mono.justOrEmpty(new Message("100001", code + "表还未录入字段！"));
				
				this.tabFactory(list1);
			}
			return Mono.justOrEmpty(new Message("000001", "成功"));
		} catch (Exception e) {
			throw new BusinessException("000006", e.getMessage());
		}
	}

	private void tabFactory(List<BpField> list) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tabCode", list.get(0).getTabCode());
		
		String[][] keys = new String[list.size()][];
		
		for (int i = 0; i < list.size(); i++) {
			BpField bpField = list.get(i);
			String colCode = bpField.getColCode();
			String colName = bpField.getColName();
			String dataLen = bpField.getDataLen();
			String dataType = bpField.getDataType();
			String pkGen = bpField.getPkGen();
			String data = "";
			
			if ("char".equals(dataType) || "varchar".equals(dataType) || "int".equals(dataType)) {
				data = dataType + "(" + dataLen +  ")";
			}else if("decimal".equals(dataType)){
				if(dataLen.indexOf(",") == -1)
					data = dataType + "(" + dataLen + ", 0)";
				else
					data = dataType + "(" + dataLen + ")";
			}else if("date".equals(dataType) || "datetime".equals(dataType)){
				data = dataType;
			}
			
			if("0".equals(pkGen))
				pkGen = "PRIMARY KEY";
			else if("1".equals(pkGen))
				pkGen = "PRIMARY KEY AUTO_INCREMENT";
			else
				pkGen = "";
			
			String[] arr = {colCode, colName, data, pkGen};
			keys[i] = arr;
		}
		
		map.put("keys", keys);
		
		bpTableService.creatTable("com.sky.business.tableDefinition.dao.BpTableDao.creatTable", map);
	}
	
	@RequestMapping("/TK0007R.do")
	@ResponseBody
	public Mono<Message> reset(HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}
}
