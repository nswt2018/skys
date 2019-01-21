package com.sky.business.pageElement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.business.pageElement.model.BpElement;
import com.sky.business.pageElement.service.IBpElementService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpElementController extends BaseController {
	Log logger = LogFactory.getLog(BpElementController.class);
	private final IBpElementService bpElementService;
	
	@Autowired
	public BpElementController(final IBpElementService bpElementService) {
		this.bpElementService = bpElementService;
	}
	
	@RequestMapping(value="/TK0006L.do")
	@ResponseBody
	public Mono<List<BpElement>> getElementList(@RequestBody Page<BpElement> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<BpElement> elementList = bpElementService.findForList("com.sky.business.pageElement.dao.BpElementDao.findForList", page);
		return Mono.justOrEmpty(elementList);
	}
	
	@RequestMapping("/TK0006U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpElement bpElement, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpElementService.update(bpElement)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping(value="/TK0003L2.do")
	@ResponseBody
	public Mono<List<BpElement>> getElementById(@RequestParam("eleCode") String eleCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<BpElement> findForList = bpElementService.findForList("com.sky.business.pageElement.dao.BpElementDao.findElementById", eleCode);
		return Mono.justOrEmpty(findForList);
	}
}
