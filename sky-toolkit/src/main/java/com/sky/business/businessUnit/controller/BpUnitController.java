package com.sky.business.businessUnit.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.business.businessUnit.model.BpUnit;
import com.sky.business.businessUnit.service.IBpUnitService;
import com.sky.business.columnDefinition.model.BpField;
import com.sky.business.columnDefinition.service.IBpFieldService;
import com.sky.business.componetDefinition.model.BpComponet;
import com.sky.business.componetDefinition.service.IBpComponetService;
import com.sky.business.pageElement.model.BpElement;
import com.sky.business.pageElement.service.IBpElementService;
import com.sky.business.systemModule.model.BpModule;
import com.sky.business.systemModule.service.IBpModuleService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpUnitController extends BaseController {
	Log logger = LogFactory.getLog(BpUnitController.class);
	private final IBpUnitService bpUnitService;
	private final IBpModuleService bpModuleService;
	private final IBpElementService bpElementService;
	private final IBpComponetService bpComponetService;
	private final IBpFieldService bpFieldService;

	@Autowired
	public BpUnitController(final IBpUnitService bpUnitService, final IBpFieldService bpFieldService, 
			final IBpModuleService bpModuleService, final IBpElementService bpElementService, final IBpComponetService bpComponetService) {
		this.bpUnitService = bpUnitService;
		this.bpModuleService = bpModuleService;
		this.bpElementService = bpElementService;
		this.bpComponetService = bpComponetService;
		this.bpFieldService = bpFieldService;
	}
	
	@RequestMapping(value="/TK0005L.do")
	@ResponseBody
	public Mono<Page<BpUnit>> getComponetList(@RequestBody Page<BpUnit> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpUnitService.findForPageList("com.sky.business.businessUnit.dao.BpUnitDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	/*@PutMapping("/TK0005I.do")
	@ResponseBody
	public Mono<Message> saveComponet(@RequestBody BpUnit bpUnit, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String comCode = bpUnit.getComCode();
			List<BpComponet> list = bpComponetService.findForList("com.sky.business.componetDefinition.dao.BpComponetDao.findComponet", comCode);
			bpUnit.setComName(list.get(0).getComName());
			int i = bpUnitService.save(bpUnit);
			String relColumn = bpUnit.getRelColumn();
			
			if(relColumn != null && relColumn != ""){
				String[] split = relColumn.split(",");
				for (String rInfo : split) {
					BpElement bpElement = new BpElement();
					bpElement.setEleEName(rInfo);
					bpElement.setUnitCode(bpUnit.getUnitCode());
					bpElement.setUnitName(bpUnit.getUnitName());
					bpElement.setEleCName(rInfo);
					bpElement.setModuCode(bpUnit.getModuCode());
					bpElement.setComCode(bpUnit.getComCode());
					bpElement.setComName(bpUnit.getComName());
					bpElementService.save(bpElement);
				}
			}
			
			if(i>0){
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}*/
	
	@DeleteMapping("/TK0005D.do")
	@ResponseBody
	public Mono<Message> deleteByComCode(@RequestParam String[] unitCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(unitCode==null || unitCode.length == 0)
				throw new BusinessException("000005");
			for(String id : unitCode) {
				Map<String, String> map = new HashMap<>();
				map.put("tabName", "bp_unit");
				map.put("code", id);
				bpUnitService.delUnit("com.sky.business.businessUnit.dao.BpUnitDao.delUnit", map);
				//删除页面元素表中相关记录
				map.clear();
				map.put("tabName", "bp_element");
				map.put("code", id);
				bpUnitService.delUnit("com.sky.business.businessUnit.dao.BpUnitDao.delUnit", map);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0005U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpUnit bpUnit, HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("moduCode", bpUnit.getModuCode());
			map.put("unitCode", bpUnit.getUnitCode());
			List<BpUnit> unitList = bpUnitService.findForList("com.sky.business.businessUnit.dao.BpUnitDao.findUnit", map);
			String relColumn = bpUnit.getRelColumn();
			
			//如果关联字段发生变化，则先删除页面元素表中的相关记录，然后再插入新的数据
			if(!(relColumn.equals(unitList.get(0).getRelColumn()))){
				Map<String, String> map1 = new HashMap<>();
				map1.put("tabName", "bp_element");
				map1.put("code", "" + bpUnit.getUnitCode());
				bpUnitService.delUnit("com.sky.business.businessUnit.dao.BpUnitDao.delUnit", map1);
				
				String relTable = bpUnit.getRelTable();
				String[] split = relColumn.split(",");
				for (String rInfo : split) {
					map1.clear();
					if(relTable.indexOf(",") != -1){
						String[] colArr = rInfo.split("\\.");
						map1.put("tabCode", colArr[0]);
						map1.put("colCode", colArr[1]);
					}else{
						map1.put("tabCode", relTable);
						map1.put("colCode", rInfo);
					}
					List<BpField> fList = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findField", map1);
					BpElement bpElement = new BpElement();
					bpElement.setEleEName(rInfo);
					bpElement.setUnitCode(bpUnit.getUnitCode());
					bpElement.setUnitName(bpUnit.getUnitName());
					bpElement.setEleCName(fList.get(0).getColName());
					bpElement.setModuCode(bpUnit.getModuCode());
					bpElement.setComCode(bpUnit.getComCode());
					bpElement.setComName(bpUnit.getComName());
					bpElement.setCrtDate(new Date());
					bpElementService.save(bpElement);
				}
			}
			
			String comCode = bpUnit.getComCode();
			List<BpComponet> list = bpComponetService.findForList("com.sky.business.componetDefinition.dao.BpComponetDao.findComponet", comCode);
			bpUnit.setComName(list.get(0).getComName());
			if (bpUnitService.update(bpUnit)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0005T.do")
	@ResponseBody
	public Mono<List<Map<String, Object>>> getTagTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<BpModule> list = bpModuleService.findForList("com.sky.business.businessUnit.dao.BpUnitDao.findAll", "");
		List<Map<String, Object>> rList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(list != null && list.size() > 0){
			for (BpModule bpModule : list) {
				map = new HashMap<String, Object>();
				map.put("code", bpModule.getModuCode());
				map.put("value", bpModule.getModuCName());
				map.put("children", putChildren(bpModule));
				map.put("isRoot", "0");
				
				rList.add(map);
			}
			
		}
		return Mono.justOrEmpty(rList);
	}
	
	public List<Map<String, Object>> putChildren(BpModule bpModule) throws Exception {
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap;
		
		String moduCode = bpModule.getModuCode();
		List<BpUnit> findForList = bpUnitService.findForList("com.sky.business.businessUnit.dao.BpUnitDao.findAllChildren", moduCode);
		
		if(findForList != null && findForList.size() > 0){
			for (BpUnit bpUnit : findForList) {
				tempMap = new HashMap<String, Object>();
				tempMap.put("code", bpUnit.getUnitCode());
				tempMap.put("value", bpUnit.getUnitName());
				tempMap.put("isRoot", "1");
				
				listMap.add(tempMap);
			}
		}
		
		return listMap;
	} 
	
	/**
	 * 获取所有字段
	 * @param relTable
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/TK0005S1.do")
	@ResponseBody
	public Mono<List<String>> getColumnList(@RequestParam String relTable, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<String> columnList = new ArrayList<String>();
		if(relTable.indexOf(",") != -1){
			String[] tabArr = relTable.split(",");
			for (String tabCode : tabArr) {
				List<String> cList = new ArrayList<String>();
				cList = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getColumnName", tabCode);
				for (String col : cList) {
					col = tabCode + "." + col;
					columnList.add(col);
				}
			}
		}else{
			columnList = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getColumnName", relTable);
		}
		List<String> cList = new ArrayList<String>();
		
		for (String column : columnList) {
			column = column.split(",")[0];
			cList.add(column);
		}
		return Mono.justOrEmpty(cList);
	}
}
