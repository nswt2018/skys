package com.sky.business.modelDefinition.controller;

import java.util.List;

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
import com.sky.business.systemModule.service.IBpModuleService;
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
	private final IBpModuleService bpModuleService;

	@Autowired
	public BpModelController(final IBpModelService bpModelService, final IBpModuleService bpModuleService) {
		this.bpModelService = bpModelService;
		this.bpModuleService = bpModuleService;
		
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
	public Mono<Message> deleteByModCode(@RequestParam String modCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(modCode==null || modCode.length() == 0)
				throw new BusinessException("000005");
			
			bpModelService.delete(modCode);
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
	public Mono<Page<BpModule>> getModuDataList(@RequestBody Page<BpModule> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		page.setRequest(request);
		bpModuleService.findForPageList("com.sky.business.systemModule.dao.BpModuleDao.findModuPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
}
