package com.sky.business.modelDefinition.controller;

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

import com.sky.business.modelDefinition.model.BpModel;
import com.sky.business.modelDefinition.service.IBpModelService;
import com.sky.business.systemModule.model.BpModule;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpModelController extends BaseController {
	Log logger = LogFactory.getLog(BpModelController.class);
	private final IBpModelService bpModelService;

	@Autowired
	public BpModelController(final IBpModelService bpModelService) {
		this.bpModelService = bpModelService;
		
	}
	
	@RequestMapping(value="/TK0001L.do")
	@ResponseBody
	public Mono<Page<BpModel>> getModelList(@RequestBody Page<BpModel> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpModelService.findForPageList("com.sky.business.modelDefinition.dao.BpModelDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	
	@PutMapping("/TK0001I.do")
	@ResponseBody
	public Mono<Message> saveModel(@RequestBody BpModel bpModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(bpModelService.save(bpModel)>0){
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	@DeleteMapping("/TK0001D.do")
	@ResponseBody
	public Mono<Message> deleteByModCode(@RequestParam String[] modCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(modCode==null || modCode.length == 0)
				throw new BusinessException("000005");
			for(String id : modCode) {
				bpModelService.delete(id);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0001U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpModel bpModel, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpModelService.update(bpModel)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping(value="/TK0001T.do")
	@ResponseBody
	public Mono<List<BpModel>> getModelList1(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<BpModel> list = bpModelService.findForList("com.sky.business.modelDefinition.dao.BpModelDao.findAllModel", "");
		return Mono.justOrEmpty(list);
	}
	
	
	@RequestMapping(value="/TK0001L1.do")
	@ResponseBody
	public Mono<Map<String, Object>> getModuDataList(@RequestParam String[] modCode, @RequestParam Integer currentPage, @RequestParam Integer pageSize,
			@RequestParam String sModelCode, @RequestParam String sModuCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		if(modCode.length == 0){
			modCode = new String[]{"-1"}; //如果传入的modCode数组为空,则赋值,保证查询不到结果
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("arr", modCode);
		map.put("sModelCode", sModelCode);
		map.put("sModuCode", sModuCode);
        
		//查询总笔数
		int totalCount = bpModelService.countModu("com.sky.business.modelDefinition.dao.BpModelDao.countModu", map);
		
		//计算总页数
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		
		
		//查询分页记录
		map.put("limit", (currentPage-1)*10);
		map.put("page", pageSize);
		List<BpModule> mlist = bpModelService.findModuForPageList("com.sky.business.modelDefinition.dao.BpModelDao.findModu", map);
		
		
		Map<String, Object> rMap = new HashMap<>();
		rMap.put("totalCount", totalCount);
		rMap.put("totalPage", totalPage);
		rMap.put("rows", mlist);
		
		return Mono.justOrEmpty(rMap);
	}
}
