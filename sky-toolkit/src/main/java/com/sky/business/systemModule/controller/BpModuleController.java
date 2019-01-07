package com.sky.business.systemModule.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.sky.business.systemModule.model.BpTransaction;
import com.sky.business.systemModule.service.IBpModuleService;
import com.sky.business.systemModule.service.IBpTransactionService;
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
	private final IBpTransactionService bpTransactionService;

	@Autowired
	public BpModuleController(final IBpModuleService bpModuleService, final IBpModelService bpModelService, final IBpComponetService bpComponetService,
			final IBpUnitService bpUnitService, final IBpElementService bpElementService, final IBpTransactionService bpTransactionService) {
		this.bpModuleService = bpModuleService;
		this.bpModelService = bpModelService;
		this.bpComponetService = bpComponetService;
		this.bpUnitService = bpUnitService;
		this.bpElementService = bpElementService;
		this.bpTransactionService = bpTransactionService;
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
		Map<String, String> map = new HashMap<>();
		map.put("modCode", bpModule.getModuModel());
		List<BpComponet> componetList = bpComponetService.findForList("com.sky.business.componetDefinition.dao.BpComponetDao.findComponetByModCode", map);
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
	public Mono<Message> deleteByModCode(@RequestParam String moduCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(moduCode==null || moduCode.length() == 0)
				throw new BusinessException("000005");
			
			bpModuleService.delete(moduCode);
			this.delData(moduCode);
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
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
			throw new BusinessException("-000000", e.getMessage());
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
	
	@RequestMapping(value="/TK0010L1.do")
	@ResponseBody
	public Mono<List<String>> getDoMainList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<String> list = bpModuleService.getTabInfo("com.sky.business.systemModule.dao.BpModuleDao.getDoMainInfo", "");
		
		return Mono.justOrEmpty(list);
	}
	
	/**
	 * 获取树数据
	 * @param moduTC
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/TK0004L3.do")
	@ResponseBody
	public Mono<List<Map<String, Object>>> getTreeData(@RequestParam String relInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<BpTransaction> treeData = bpModuleService.getData("com.sky.business.systemModule.dao.BpModuleDao.getTreeData", relInfo);
		
		List<Map<String, Object>> list = this.asTree(treeData);
		
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()){
			if("1".equals(iterator.next().get("isRoot")))
				iterator.remove();
		}

		return Mono.justOrEmpty(list);
	}

	/**
	 * 将数据转为树形结构
	 * @param treeData
	 * @return
	 */
	public List<Map<String, Object>> asTree(List<BpTransaction> treeData) {
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap;
		
		for(BpTransaction bt: treeData){
			
			tempMap = new HashMap<String, Object>();
			
			String nodCode = bt.getNodCode();
			String nodName = bt.getNodName();
			String upNodCode = bt.getUpNodCode();
			String tranCode = bt.getTranCode();
			String isRoot = (upNodCode == null || "".equals(upNodCode)) ? "0" : "1";
			tempMap.put("nodCode", nodCode);
			tempMap.put("nodName", nodName);
			tempMap.put("upNodCode", upNodCode);
			tempMap.put("isRoot", isRoot);
			tempMap.put("tranCode", tranCode);
			
			List<BpTransaction> cList = bpModuleService.getData("com.sky.business.systemModule.dao.BpModuleDao.getTreeChildren", nodCode);
			//如果有子节点 递归
			if(cList != null && cList.size() > 0){
				tempMap.put("children", asTree(cList));
			}
			
			listMap.add(tempMap);
		}
		return listMap;
	}
	
	
	/**
	 * 获取节点详细信息
	 * @param nodCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/TK0004L4.do")
	@ResponseBody
	public Mono<BpTransaction> getNodeInfo(@RequestParam String nodCode, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		List<BpTransaction> tList = bpModuleService.getData("com.sky.business.systemModule.dao.BpModuleDao.getNodeInfo", nodCode);

		return Mono.justOrEmpty(tList.get(0));
	}
	
	
	/**
	 * 获取根节点编号和模块交易号
	 * @param nodCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/TK0004L5.do")
	@ResponseBody
	public Mono<List<Map<String, Object>>> getTranCodeList(@RequestParam("nodCode") String nodCode, @RequestParam("moduTC") String moduTC, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		List<Map<String, Object>> listMap = new ArrayList<>();
		Map<String, Object> tempMap = new HashMap<>();
		Map<String, Object> tempMap1 = new HashMap<>();
		
		if(nodCode != null && !("".equals(nodCode)))
			nodCode = nodCode.substring(0, 3);
			
		//根节点编号
		Map<String, String> map = new HashMap<>();
		map.put("nodCode", nodCode);
		List<BpTransaction> tList = bpModuleService.getData("com.sky.business.systemModule.dao.BpModuleDao.getTranCode", map);
		
		tempMap.put("value", "nc");
		tempMap.put("label", "根节点编号");
		if(tList != null && tList.size() > 0){
			List<Map<String, Object>> childList = new ArrayList<>();
			Map<String, Object> childMap;
			for (BpTransaction bpTransaction : tList) {
				childMap = new HashMap<>();
				childMap.put("value", bpTransaction.getNodCode());
				childMap.put("label", bpTransaction.getNodCode());
				childList.add(childMap);
			}
			
			tempMap.put("children", childList);
		}
		
		listMap.add(tempMap);
		
		//模块交易号
		tempMap1.put("value", "mc");
		tempMap1.put("label", "模块交易号");
		
		map.clear();
		map.put("moduTC", moduTC);
		List<BpModule> mList = bpModuleService.findForList("com.sky.business.systemModule.dao.BpModuleDao.getModuTC", map);
		if(mList != null && mList.size() > 0){
			List<Map<String, Object>> childList = new ArrayList<>();
			Map<String, Object> childMap;
			for (BpModule bpModule : mList) {
				childMap = new HashMap<>();
				childMap.put("value", bpModule.getModuCode());
				childMap.put("label", bpModule.getModuCode());
				childList.add(childMap);
			}
			
			tempMap1.put("children", childList);
		}
		
		listMap.add(tempMap1);
		
		return Mono.justOrEmpty(listMap);
	}
	
	@RequestMapping("/TK0004U1.do")
	@ResponseBody
	public Mono<Message> updTransaction(@RequestBody BpTransaction bpTransaction, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(bpTransactionService.update(bpTransaction) > 0){;
				return Mono.justOrEmpty(new Message("000003"));
			}else{
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}
	
	@PutMapping("/TK0004I1.do")
	@ResponseBody
	public Mono<Map<String, String>> saveTransaction(@RequestBody BpTransaction bpTransaction, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> map = new HashMap<>();
		try {
			String upNodCode = bpTransaction.getUpNodCode();
			List<String> findMax = bpModuleService.findMax("com.sky.business.systemModule.dao.BpModuleDao.findMax", upNodCode);
			
			String nodCode;
			if(findMax.get(0) != null && findMax.get(0).length() > 0) {
				nodCode = getNodCode(findMax.get(0));
			} else{
				if(upNodCode != null && !("".equals(upNodCode))){
					nodCode = upNodCode + "01";
				}else{
					nodCode = "r01";
				}
			}
				
			bpTransaction.setNodCode(nodCode);
			if(bpTransactionService.save(bpTransaction) > 0){
				map.put("code", "000001");
				if(nodCode.length() == 3){
					map.put("nodCode", nodCode);
				}
			}else{
				map.put("code", "100001");
				map.put("message", "添加失败！");
			}
			return Mono.justOrEmpty(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000001", e.getMessage());
		}
	}

	public String getNodCode(String nodCode) {
		
		nodCode = nodCode.substring(1, nodCode.length());
		int len = nodCode.length();
		
		int code = Integer.parseInt(nodCode) + 1;
		
		nodCode = code + "";
		if(nodCode.length() == len)
			return "r" + nodCode;
		else
			return "r0" + nodCode;
	}
	
	/**
	 * 根据节点编号删除节点及子节点
	 * @param nodCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/TK0004D1.do")
	@ResponseBody
	public Mono<Message> delTrans(@RequestParam String nodCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(nodCode==null || nodCode.length() == 0)
				throw new BusinessException("000005");
			
			//删除节点及子节点
			Map<String, String> map = new HashMap<>();
			map.put("tabName", "bp_transaction");
			map.put("code", nodCode);
			bpModuleService.delData("com.sky.business.systemModule.dao.BpModuleDao.deleteByNodCode", map);
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}
}
