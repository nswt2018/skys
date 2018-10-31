package com.sky.business.componetDefinition.controller;

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

import com.sky.business.componetDefinition.model.BpComponet;
import com.sky.business.componetDefinition.service.IBpComponetService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpComponetController extends BaseController {
	Log logger = LogFactory.getLog(BpComponetController.class);
	private final IBpComponetService bpComponetService;

	@Autowired
	public BpComponetController(final IBpComponetService bpComponetService) {
		this.bpComponetService = bpComponetService;
	}
	
	@RequestMapping(value="/TK0002L.do")
	@ResponseBody
	public Mono<Page<BpComponet>> getComponetList(@RequestBody Page<BpComponet> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpComponetService.findForPageList("com.sky.business.componetDefinition.dao.BpComponetDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	@PutMapping("/TK0002I.do")
	@ResponseBody
	public Mono<Message> saveComponet(@RequestBody BpComponet bpComponet, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(bpComponetService.save(bpComponet)>0){
				return Mono.justOrEmpty(new Message("000001"));
			} else {
			    return Mono.justOrEmpty(new Message("100001"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	@DeleteMapping("/TK0002D.do")
	@ResponseBody
	public Mono<Message> deleteByComCode(@RequestParam String[] comCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(comCode==null || comCode.length == 0)
				throw new BusinessException("000005");
			for(String id : comCode) {
				bpComponetService.delete(id);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0002U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpComponet bpComponet, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpComponetService.update(bpComponet)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
}
