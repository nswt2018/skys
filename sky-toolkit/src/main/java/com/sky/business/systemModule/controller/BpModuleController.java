package com.sky.business.systemModule.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.sky.business.businessUnit.model.BpUnit;
import com.sky.business.businessUnit.service.IBpUnitService;
import com.sky.business.componetDefinition.model.BpComponet;
import com.sky.business.componetDefinition.service.IBpComponetService;
import com.sky.business.modelDefinition.model.BpModel;
import com.sky.business.modelDefinition.service.IBpModelService;
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
public class BpModuleController extends BaseController {
	Log logger = LogFactory.getLog(BpModuleController.class);
	private final IBpModuleService bpModuleService;
	private final IBpModelService bpModelService;
	private final IBpComponetService bpComponetService;
	private final IBpUnitService bpUnitService;
	private final IBpElementService bpElementService;

	@Autowired
	public BpModuleController(final IBpModuleService bpModuleService, final IBpModelService bpModelService, final IBpComponetService bpComponetService,
			final IBpUnitService bpUnitService, final IBpElementService bpElementService) {
		this.bpModuleService = bpModuleService;
		this.bpModelService = bpModelService;
		this.bpComponetService = bpComponetService;
		this.bpUnitService = bpUnitService;
		this.bpElementService = bpElementService;
	}
	
	@RequestMapping(value="/TK0004L.do")
	@ResponseBody
	public Mono<Page<BpModule>> getModelList(@RequestBody Page<BpModule> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpModuleService.findForPageList("com.sky.business.systemModule.dao.BpModuleDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	
	@PutMapping("/TK0004I.do")
	@ResponseBody
	public Mono<Message> saveModel(@RequestBody BpModule bpModule, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String modCode = bpModule.getModuModel();
			List<BpModel> list = bpModelService.findForList("com.sky.business.modelDefinition.dao.BpModelDao.findModel", modCode);
			bpModule.setModName(list.get(0).getModName());
			if(bpModuleService.save(bpModule)>0){
				this.setData(bpModule);
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	/**
	 * 将默认数据插入到业务单元和页面元素表中
	 * @param bpModule
	 */
	private void setData(BpModule bpModule) {
		//取组件类型
		List<BpComponet> componetList = bpComponetService.findForList("com.sky.business.componetDefinition.dao.BpComponetDao.findAllComponet", "");
		//取关联表所有字段名
		String relTable = bpModule.getRelTable();
		List<String> eNameList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		if(relTable.indexOf(",") != -1){ //多表
			String[] relArr = relTable.split(",");
			
			for (String tabCode : relArr) {
				List<String> colList = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getColumnName", tabCode);
				List<String> cList = new ArrayList<String>(); 
				for (String str : colList) {
					str = tabCode + "." + str;
					cList.add(str);
				}
				
				
				nameList.addAll(cList);
			}
		}else{
			nameList = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getColumnName", relTable);
		}
		
		for (String name : nameList) {
			eNameList.add(name.split(",")[0]);
		}
		
		String relColumn= StringUtils.join(eNameList,",");
		//插入到业务平台-业务单元表中
		for (BpComponet bpComponet : componetList) {
			BpUnit bpUnit = new BpUnit();
			bpUnit.setCrtDate(new Date());
			bpUnit.setModuCode(bpModule.getModuCode());
			bpUnit.setUnitName(bpModule.getModuCName() + "-" + bpComponet.getComName());
			bpUnit.setComCode(bpComponet.getComCode());
			bpUnit.setComName(bpComponet.getComName());
			bpUnit.setRelColumn(relColumn);
			bpUnit.setRelTable(bpModule.getRelTable());
			bpUnit.setCrtDate(new Date());
			
			bpUnitService.save(bpUnit);
		}
		
		//插入到业务平台-页面元素表中
		List<BpUnit> unitList = bpUnitService.findForList("com.sky.business.businessUnit.dao.BpUnitDao.findAllChildren", bpModule.getModuCode());
		for (BpUnit bpUnit2 : unitList) {
			for (String nList : nameList) {
				BpElement bpElement = new BpElement();
				bpElement.setEleEName(nList.split(",")[0]);
				bpElement.setEleCName(nList.split(",")[1]);
				bpElement.setModuCode(bpModule.getModuCode());
				bpElement.setUnitCode(bpUnit2.getUnitCode());
				bpElement.setUnitName(bpUnit2.getUnitName());
				bpElement.setComCode(bpUnit2.getComCode());
				bpElement.setComName(bpUnit2.getComName());
				bpElement.setCrtDate(new Date());
				bpElementService.save(bpElement);
			}
		}
	}

	@DeleteMapping("/TK0004D.do")
	@ResponseBody
	public Mono<Message> deleteByModCode(@RequestParam String[] moduCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(moduCode==null || moduCode.length == 0)
				throw new BusinessException("000005");
			for(String id : moduCode) {
				bpModuleService.delete(id);
				this.delData(id);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	/**
	 * 删除业务单元和页面元素表中的相关记录
	 * @param id
	 */
	private void delData(String id) {
		//删除业务平台-业务单元表中的相关记录
		Map<String, String> map = new HashMap<>();
		map.put("tabName", "bp_unit");
		map.put("code", id);
		bpModuleService.delData("com.sky.business.systemModule.dao.BpModuleDao.delData", map);
		//删除业务平台-页面元素表中的相关记录
		map.clear();
		map.put("tabName", "bp_element");
		map.put("code", id);
		bpModuleService.delData("com.sky.business.systemModule.dao.BpModuleDao.delData", map);
	}

	@RequestMapping("/TK0004U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpModule bpModule, HttpServletRequest request, HttpServletResponse response) {
		try {
			String modCode = bpModule.getModuModel();
			List<BpModel> list = bpModelService.findForList("com.sky.business.modelDefinition.dao.BpModelDao.findModel", modCode);
			bpModule.setModName(list.get(0).getModName());
			
			if (bpModuleService.update(bpModule)>0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("moduCode", bpModule.getModuCode());
				map.put("relTable", bpModule.getRelTable());
				List<BpUnit> unitList = bpUnitService.findForList("com.sky.business.businessUnit.dao.BpUnitDao.findUnit", map);
				if(unitList == null || unitList.size() == 0){
					this.delData(bpModule.getModuCode());
					this.setData(bpModule);
				}
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping(value="/TK0004T.do")
	@ResponseBody
	public Mono<List<BpModule>> getModelList1(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<BpModule> list = bpModuleService.findForList("com.sky.business.systemModule.dao.BpModuleDao.findAllModule", "");
		return Mono.justOrEmpty(list);
	}
	
	@RequestMapping(value="/TK0004L1.do")
	@ResponseBody
	public Mono<List<String>> getTabName(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<String> list = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getTabName", "");
		return Mono.justOrEmpty(list);
	}
	
	@RequestMapping(value="/TK0004L2.do")
	@ResponseBody
	public Mono<List<String>> getColList(@RequestParam String tabCode, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<String> list = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getColName", tabCode);
		
		return Mono.justOrEmpty(list);
	}
}
