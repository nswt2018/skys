package com.sky.business.systemModule.controller;

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
public class BpModuleController extends BaseController {
	Log logger = LogFactory.getLog(BpModuleController.class);
	private final IBpModuleService bpModuleService;
	private final IBpModelService bpModelService;

	@Autowired
	public BpModuleController(final IBpModuleService bpModuleService, final IBpModelService bpModelService) {
		this.bpModuleService = bpModuleService;
		this.bpModelService = bpModelService;
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
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
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
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0004U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpModule bpModule, HttpServletRequest request, HttpServletResponse response) {
		try {
			String modCode = bpModule.getModuModel();
			List<BpModel> list = bpModelService.findForList("com.sky.business.modelDefinition.dao.BpModelDao.findModel", modCode);
			bpModule.setModName(list.get(0).getModName());
			if (bpModuleService.update(bpModule)>0) {
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
}
