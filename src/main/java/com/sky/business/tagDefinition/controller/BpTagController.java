package com.sky.business.tagDefinition.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.business.pageElement.model.BpColumns;
import com.sky.business.pageElement.model.BpElement;
import com.sky.business.pageElement.service.IBpColumnsService;
import com.sky.business.pageElement.service.IBpElementService;
import com.sky.business.tagDefinition.model.BpTag;
import com.sky.business.tagDefinition.model.BpTagTree;
import com.sky.business.tagDefinition.service.IBpTagService;
import com.sky.business.tagDefinition.service.IBpTagTreeService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpTagController extends BaseController {
	Log logger = LogFactory.getLog(BpTagController.class);
	private final IBpTagService bpTagService;
	private final IBpTagTreeService bpTagTreeService;
	private final IBpColumnsService bpColumnsService;
	private final IBpElementService bpElementService;

	@Autowired
	public BpTagController(final IBpTagService bpTagService, final IBpTagTreeService bpTagTreeService, final IBpColumnsService bpColumnsService,final IBpElementService bpElementService) {
		this.bpTagService = bpTagService;
		this.bpTagTreeService = bpTagTreeService;
		this.bpColumnsService = bpColumnsService;
		this.bpElementService = bpElementService;
	}
	
	@RequestMapping(value="/TK0003L.do")
	@ResponseBody
	public Mono<Page<BpTag>> getTagList(@RequestBody Page<BpTag> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpTagService.findForPageList("com.sky.business.tagDefinition.dao.BpTagDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	
	@PutMapping("/TK0003I.do")
	@ResponseBody
	public Mono<Message> saveTag(@RequestBody BpTag bpTag, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(bpTagService.save(bpTag)>0){
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	@DeleteMapping("/TK0003D.do")
	@ResponseBody
	public Mono<Message> deleteByModCode(@RequestParam String[] tagKey, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(tagKey==null || tagKey.length == 0)
				throw new BusinessException("000005");
			for(String id : tagKey) {
				bpTagService.delete(id);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0003U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpTag bpTag, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpTagService.update(bpTag)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0003T.do")
	@ResponseBody
	public Mono<List<Map<String, Object>>> getTagTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<BpTagTree> list = bpTagTreeService.findForList("com.sky.business.tagDefinition.dao.BpTagDao.findTagTree", "");
		
		List<Map<String, Object>> tagTree = this.asTree(list);
		Iterator<Map<String, Object>> iterator = tagTree.iterator();
		while(iterator.hasNext()){
			if("1".equals(iterator.next().get("isRoot")))
				iterator.remove();
		}

		return Mono.justOrEmpty(tagTree);
	}
	
	
	public List<Map<String, Object>> asTree(List<BpTagTree> list) throws Exception {
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap;
		
		for(BpTagTree btt: list){
			
			tempMap = new HashMap<String, Object>();
			
			String tagId = btt.getTagID();
			tempMap.put("tagId", tagId);
			tempMap.put("tagName", btt.getTagName());
			tempMap.put("upTagID", btt.getUpTagID());
			tempMap.put("isRoot", btt.getIsRoot());
			
			if(btt.getUpTagID() == null){
				List<BpTagTree> clist = bpTagTreeService.findForList("com.sky.business.tagDefinition.dao.BpTagDao.selectAllChildren", tagId);
				if(clist.size() > 0){
					tempMap.put("children", asTree(clist));
				}
			}
			listMap.add(tempMap);
		}
		return listMap;
	}  
	
	@RequestMapping(value="/TK0003L1.do")
	@ResponseBody
	public List<Map<String, String>> getTagList1(@RequestParam("tagCode") String tagCode, @RequestParam("eleCode") String eleCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<BpTag> tlist = bpTagService.findForList("com.sky.business.tagDefinition.dao.BpTagDao.selectTagById", tagCode);
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		Map<String, String> tempMap;
		for (BpTag bpTag : tlist) {
			map = new HashMap<>();
			map.put("tagKey", bpTag.getTagKey());
			map.put("eleCode", eleCode);
			List<BpColumns> clist = bpColumnsService.findForList("com.sky.business.pageElement.dao.BpElementDao.findAllColumns", map);
			tempMap = new HashMap<String, String>();
			tempMap.put("tagKey", bpTag.getTagKey());
			tempMap.put("tagProp", bpTag.getTagProp());
			tempMap.put("propType", bpTag.getPropType());
			tempMap.put("propVal", bpTag.getPropVal());
			tempMap.put("useRule", bpTag.getUseRule());
			tempMap.put("tagName", bpTag.getTagName());
			if(clist != null && clist.size() > 0){
				tempMap.put("tagValue", clist.get(0).getTagValue());
			}else{
				tempMap.put("tagValue", "");
			}
			listMap.add(tempMap);
		}
		return listMap;
	}
	
	@PutMapping("/TK0003I1.do")
	@ResponseBody
	public Mono<Message> saveColumns(@RequestBody BpColumns bpColumns, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("tagKey", bpColumns.getTagKey());
			map.put("eleCode", bpColumns.getEleCode());
			List<BpColumns> clist = bpColumnsService.findForList("com.sky.business.pageElement.dao.BpElementDao.findAllColumns", map);
			if (clist != null && clist.size() > 0) {
				if (bpColumnsService.update(bpColumns)>0) {
					return Mono.justOrEmpty(new Message("000001"));
				}else{
					return Mono.justOrEmpty(new Message("100001"));
				}
			}else{
				if(bpColumnsService.save(bpColumns)>0){
					return Mono.justOrEmpty(new Message("000001"));
				} else {
				    return Mono.justOrEmpty(new Message("100001"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	@PutMapping("/TK0003I2.do")
	@ResponseBody
	public Mono<Message> assembleJson(@RequestParam("eleCode") String eleCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<BpColumns> clist = bpColumnsService.findForList("com.sky.business.pageElement.dao.BpElementDao.findColumnsGroupbyName", eleCode);
			
			JSONArray jarr = new JSONArray();
			JSONObject json = new JSONObject();
			if (clist != null && clist.size() > 0) {
				for (BpColumns bpColumns : clist) {
					String tagName = bpColumns.getTagName();
					Map<String, String> map = new HashMap<>();
					map.put("tagName", tagName);
					map.put("eleCode", eleCode);
					List<BpColumns> list = bpColumnsService.findForList("com.sky.business.pageElement.dao.BpElementDao.findColumnsByName", map);
					JSONObject json1 = new JSONObject();
					for (BpColumns bpColumns2 : list) {
						String propType = bpColumns2.getPropType();
						String tagValue = bpColumns2.getTagValue();
						String tagProp = bpColumns2.getTagProp();
						if("1".equals(propType)){
							json1.put(tagProp, tagValue);
						}else if("2".equals(propType)){
							json1.put(":" + tagProp, tagValue);
						}else if("3".equals(propType)){
							json1.put("@" + tagProp, tagValue);
						}
					}
					json.put(tagName, json1);
				}
				jarr.put(json);
				List<BpElement> list2 = bpElementService.findForList("com.sky.business.pageElement.dao.BpElementDao.findElementById", eleCode);
				BpElement bpElement = list2.get(0);
				bpElement.setTagInfo(jarr.toString());
				
				if (bpElementService.update(bpElement)>0) {
					return Mono.justOrEmpty(new Message("000001"));
				}else{
					return Mono.justOrEmpty(new Message("100001"));
				}
			}else{
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
}
