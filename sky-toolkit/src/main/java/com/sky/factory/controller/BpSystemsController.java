package com.sky.factory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.factory.model.BpSystems;
import com.sky.factory.service.IBpSystemsService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/factory")
@CacheConfig(cacheNames = "skyCache")
public class BpSystemsController extends BaseController {
	Log logger = LogFactory.getLog(BpSystemsController.class);
	private final IBpSystemsService bpSystemsService;

	@Autowired
	public BpSystemsController(final IBpSystemsService bpSystemsService) {
		this.bpSystemsService = bpSystemsService;
	}
	
	/**
	 * 获取系统树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/AF0001T.do")
	@ResponseBody
	public Mono<List<Map<String, Object>>> getSysTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<BpSystems> list = bpSystemsService.findForList("com.sky.factory.dao.BpSystemsDao.findSystems", null);
		
		List<Map<String, Object>> tagTree = this.asTree(list);
		Iterator<Map<String, Object>> iterator = tagTree.iterator();
		while(iterator.hasNext()){
			if("1".equals(iterator.next().get("isRoot")))
				iterator.remove();
		}

		return Mono.justOrEmpty(tagTree);
	}
	
	/**
	 * 将数据转为树形结构
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> asTree(List<BpSystems> list) throws Exception {
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap;
		
		for(BpSystems bst: list){
			
			tempMap = new HashMap<String, Object>();
			
			String sysKey = bst.getSysKey();
			String sysName = bst.getSysName();
			String upperSys = bst.getUpperSys();
			String isRoot = (upperSys == null || "".equals(upperSys)) ? "0" : "1";
			tempMap.put("sysKey", sysKey);
			tempMap.put("sysName", sysName);
			tempMap.put("upperSys", upperSys);
			tempMap.put("isRoot", isRoot);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("upperSys", sysKey);
			List<BpSystems> slist = bpSystemsService.findForList("com.sky.factory.dao.BpSystemsDao.findSystems", map);
			if(slist != null && slist.size() > 0){
				tempMap.put("children", asTree(slist));
			}
			
			listMap.add(tempMap);
		}
		return listMap;
	}  
	
	
	/**
	 * 获取节点详细信息
	 * @param sysKey
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/AF0001L.do")
	@ResponseBody
	public Mono<BpSystems> getNodeInfo(@RequestParam String sysKey ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("sysKey", sysKey);
		List<BpSystems> slist = bpSystemsService.findForList("com.sky.factory.dao.BpSystemsDao.findSystems", map);
		
		BpSystems bpSystems = slist.get(0);
		
		return Mono.justOrEmpty(bpSystems);
	}
	
	/**
	 * 新增
	 * @param BpSystems
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/AF0001I.do")
	@ResponseBody
	public Mono<Message> saveSystem(@RequestBody BpSystems BpSystems, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String upperSys = BpSystems.getUpperSys();
			List<String> list = bpSystemsService.findMaxKey("com.sky.factory.dao.BpSystemsDao.findMaxKey", upperSys);
			
			String sysKey;
			if(list.get(0) != null && list.get(0).length() > 0) {
				sysKey = getKey(list.get(0));
			} else{
				sysKey = upperSys + "01";
			}
				
			BpSystems.setSysKey(sysKey);
			if(bpSystemsService.save(BpSystems)>0){
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}

	public String getKey(String sysKey) {
	
		int len = sysKey.length();
		
		int key = Integer.parseInt(sysKey) + 1;
		
		sysKey = key + "";
		if(sysKey.length() == len)
			return sysKey;
		else
			return "0" + sysKey;
	}
	
	@RequestMapping("/AF0001U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpSystems bpSystems, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpSystemsService.update(bpSystems)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}
	
	@DeleteMapping("/AF0001D.do")
	@ResponseBody
	public Mono<Message> deleteByComCode(@RequestParam String sysKey, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(sysKey==null || sysKey.length() == 0)
				throw new BusinessException("000005");
			
			//删除节点及子节点
			bpSystemsService.deleteBySysKey("com.sky.factory.dao.BpSystemsDao.deleteBySysKey", sysKey);
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/AF0004G2.do")
	@ResponseBody
	public Mono<Message> sysDeployment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			List<BpSystems> sList = bpSystemsService.findForList("com.sky.factory.dao.BpSystemsDao.findAll", "");
			List<BpSystems> sysList = this.getList(sList);
		
			for (BpSystems bpSystems : sysList) {
				System.out.println(bpSystems.toString());
			}
			if( bpSystemsService.flushRouter(sysList)) return Mono.justOrEmpty(new Message("000001"));
			else return Mono.justOrEmpty(new Message("100001"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}

	/**
	 * 去掉无效的记录
	 * @param sList
	 * @return
	 */
	private List<BpSystems> getList(List<BpSystems> sList) {
		
		List<BpSystems> sysList = new ArrayList<>();
		
		for (BpSystems bpSystems : sList) {
			String sysKey = bpSystems.getSysKey();
			String modCode = bpSystems.getModCode();
			if(modCode != null && !("".equals(modCode)))
				sysList.add(bpSystems);
			else{
				List<BpSystems> sList1 = bpSystemsService.findForList("com.sky.factory.dao.BpSystemsDao.findChildren", sysKey);
				if(sList1 != null && sList1.size() > 0){
					for (BpSystems bpSystems2 : sList1) {
						String modCode2 = bpSystems2.getModCode();
						String sysKey2 = bpSystems2.getSysKey();
						if(modCode2 != null && !("".equals(modCode2))){
							sysList.add(bpSystems);
							break;
						}else{
							List<BpSystems> sList2 = bpSystemsService.findForList("com.sky.factory.dao.BpSystemsDao.findChildren", sysKey2);
							if(sList2 != null && sList2.size() > 0){
								for (BpSystems bpSystems3 : sList2) {
									String modCode3 = bpSystems3.getModCode();
									if(modCode3 != null && !("".equals(modCode3))){
										sysList.add(bpSystems);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return sysList;
	}
}
