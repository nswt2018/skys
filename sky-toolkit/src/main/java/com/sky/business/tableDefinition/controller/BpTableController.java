package com.sky.business.tableDefinition.controller;

import java.util.ArrayList;
import java.util.Date;
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
			Map<String, String> map = new HashMap<>();
			map.put("tabCode", bpTable.getTabCode());
			List<BpTable> list = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findTable", map);
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
	public Mono<Message> deleteByTabCode(@RequestParam String tabCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(tabCode==null || tabCode.length() == 0)
				throw new BusinessException("000005");
				//从库里删除
				//查询数据库 该表是否存在
				Map<String, String> map = new HashMap<>();
				/*map.put("tabCode", tabCode);
				List<BpTable> tList = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findTable", map);
				//存在则删除
				if(tList != null && tList.size() > 0 && "1".equals(tList.get(0).getIsExist())){
					bpTableService.dropTab("com.sky.business.tableDefinition.dao.BpTableDao.dropTable", tabCode);
				}*/
				
				//删除表定义
				bpTableService.delete(tabCode);
				//查询字段定义表,有记录删除,没有返回
				List<BpField> list = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findAllField", tabCode);
				if(list != null && list.size() > 0){
					map.clear();
					map.put("tabCode", tabCode);
					bpFieldService.delField("com.sky.business.columnDefinition.dao.BpFieldDao.deleteById", map);
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
		String tabCode = "";
		try {
			List<BpTable> list = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findAllTab", tabCode);
			return Mono.justOrEmpty(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	@RequestMapping("/TK0007G.do")
	@ResponseBody
	public Mono<Message> createTab(@RequestParam String tabCode, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(tabCode==null || tabCode.length() == 0)
				return Mono.justOrEmpty(new Message("100002", "必须选中一条记录！"));
			
				//查询数据库 该表是否存在
				Map<String, String> map = new HashMap<>();
				map.put("tabCode", tabCode);
				List<BpTable> list = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findTable", tabCode);
				
				boolean flag = false;
				
				//存在则备份 删除
				if(list != null && list.size() > 0){
					
					map.clear();
					map.put("oldTabCode", tabCode);
					map.put("newTabCode", tabCode + "_backup");
					
					//备份
					bpFieldService.backupTab("com.sky.business.columnDefinition.dao.BpFieldDao.backupTab", map);
					
					//删除原表
					map.clear();
					map.put("tabCode", tabCode);
					bpTableService.dropTab("com.sky.business.tableDefinition.dao.BpTableDao.dropTable", tabCode);
				
					//修改bp_tables表字段 ISEXIST 表存在标识
					bpTableService.updTab("com.sky.business.tableDefinition.dao.BpTableDao.updTab1", tabCode);
					
					flag = true;
				}
					
				
				//查询字段定义表 
				List<BpField> list1 = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findAllField", tabCode);
				//没有记录则返回
				if(list1 == null || list1.size() == 0)
					return Mono.justOrEmpty(new Message("100002", tabCode + "表还未录入字段！"));
				
				//建表
				this.tabFactory(list1);
				
				//修改bp_tables表字段 ISEXIST 表存在标识
				bpTableService.updTab("com.sky.business.tableDefinition.dao.BpTableDao.updTab", tabCode);
				
				if(flag){
					
					//插入备份数据
					insertBackUp(tabCode);
					
					//删除备份表
					tabCode = tabCode + "_backup";
					bpTableService.dropTab("com.sky.business.tableDefinition.dao.BpTableDao.dropTable", tabCode);
				}
				
			return Mono.justOrEmpty(new Message("000001", "成功"));
		} catch (Exception e) {
			throw new BusinessException("100001", e.getMessage());
		}
	}

	/**
	 * 插入备份数据
	 * @param tabCode
	 */
	private void insertBackUp(String tabCode) {
		List<String> newlist = bpTableService.findColumns("com.sky.business.tableDefinition.dao.BpTableDao.findColumns", tabCode);
		List<String> oldlist = bpTableService.findColumns("com.sky.business.tableDefinition.dao.BpTableDao.findColumns", tabCode + "_backup");
	
		List<String> sameList = new ArrayList<>();
		for (int i = 0; i < newlist.size(); i++) {
			for (int j = 0; j < oldlist.size(); j++) {
				if(newlist.get(i).equals(oldlist.get(j)))
					sameList.add(newlist.get(i));
			}
		}
		
		if(sameList.size() > 0){
			Map<String, Object> map = new HashMap<>();
			map.put("newTabCode", tabCode);
			map.put("oldTabCode", tabCode + "_backup");
			map.put("list", sameList);
			bpTableService.insertBackUp("com.sky.business.tableDefinition.dao.BpTableDao.insertBackUp", map);
		}
	}

	/**
	 * 建表
	 * @param list
	 */
	private void tabFactory(List<BpField> list) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String tabCode = list.get(0).getTabCode();
		
		List<BpTable> tList = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findAllTab", "");
		
		String tabName = tList.get(0).getTabName();
		map.put("tabCode", tabCode);
		map.put("tabName", tabName);
		
		String[][] keys = new String[list.size()][];
		
		for (int i = 0; i < list.size(); i++) {
			BpField bpField = list.get(i);
			String colCode = bpField.getColCode();
			String colName = bpField.getColName();
			String dataLen = bpField.getDataLen();
			String dataType = bpField.getDataType();
			String pkGen = bpField.getPkGen();
			String data = "";
			
			if ("char".equals(dataType) || "varchar".equals(dataType)) {
				data = dataType + "(" + dataLen +  ")";
			}else if("decimal".equals(dataType)){
				if(dataLen.indexOf(",") == -1)
					data = dataType + "(" + dataLen + ", 0)";
				else
					data = dataType + "(" + dataLen + ")";
			}else if("date".equals(dataType) || "datetime".equals(dataType) || "int".equals(dataType)){
				data = dataType;
			}
			
			if("0".equals(pkGen) || "1".equals(pkGen))
				pkGen = "PRIMARY KEY";
			else
				pkGen = "";
			
			String[] arr = {colCode, colName, data, pkGen};
			keys[i] = arr;
		}
		
		map.put("keys", keys);
		
		//建表
		bpTableService.creatTable("com.sky.business.tableDefinition.dao.BpTableDao.creatTable", map);
	}
	
	@RequestMapping(value="/TK0007F.do")
	@ResponseBody
	public Mono<Message> findTab(@RequestParam String tabCode, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		//查询数据库 该表是否存在
		Map<String, String> map = new HashMap<>();
		map.put("tabCode", tabCode);
		List<BpTable> list = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findTable", map);
		
		if(list != null && list.size() > 0)
			return Mono.justOrEmpty(new Message("000001", tabCode + "表已经存在！"));
		else
			return Mono.justOrEmpty(new Message("100001", tabCode + "表不存在！"));
	}
	
	@RequestMapping(value="/TK0008L1.do")
	@ResponseBody
	public Mono<Message> findTable(@RequestParam String tabCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//查询数据库 该表是否存在
		Map<String, String> map = new HashMap<>();
		map.put("tabCode", tabCode);
		List<BpTable> list = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findTable", map);
		//存在则报错返回
		if(list != null && list.size() > 0){
			return Mono.justOrEmpty(new Message("100001", "该表已经存在"));
		}else{
			return Mono.justOrEmpty(new Message("000001"));
		}
	}
	
	@RequestMapping(value="/TK0007L1.do")
	@ResponseBody
	public Mono<Page<BpTable>> getTabList1(@RequestBody Page<BpTable> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpTableService.findForPageList("com.sky.business.tableDefinition.dao.BpTableDao.findTabPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	@PutMapping("/TK0007I1.do")
	@ResponseBody
	public Mono<Message> addTable(@RequestParam String[] tabCode,  HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String[]> map = new HashMap<>();
			map.put("arr", tabCode);
			List<BpTable> tList = bpTableService.findForList("com.sky.business.tableDefinition.dao.BpTableDao.findTabList", map);
			Date date = new Date();
			for (BpTable bpTable : tList) {
				bpTable.setCrtDate(date);
				bpTable.setIsExist("1");
				
				bpTableService.save(bpTable);
				
				List<BpField> cList = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findColList", bpTable.getTabCode());
				int i = 1;
				for (BpField bpField : cList) {
					String dataType = bpField.getDataType();
					if(dataType.equals("char") || dataType.equals("decimal") || dataType.equals("varchar")){
						
						String dataLen = bpField.getDataLen().replace(dataType, "");
						dataLen = dataLen.substring(1, dataLen.length()-1);
						bpField.setDataLen(dataLen);
						
					}else  bpField.setDataLen("");
					
					bpField.setCrtDate(date);
					bpField.setUiType("A1");
					bpField.setUiOrder(i);
					i++;
					
					bpFieldService.save(bpField);
				}
			}
			
			
			return Mono.justOrEmpty(new Message("000001"));
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
}
