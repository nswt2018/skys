package com.sky.business.domain.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.business.domain.model.BpDomain;
import com.sky.business.domain.service.IBpDomainService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpDomainController extends BaseController{
	
	@Resource(name = "bpDomainService")
	private IBpDomainService bpDomainService;
	
	@RequestMapping(value = "/TK0010L.do", method = RequestMethod.POST)
	@ResponseBody
	public Mono<Page<BpDomain>> getBpDomainPageList(@RequestBody Page<BpDomain> page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		page.setRequest(request);
		bpDomainService.findForPageList("com.sky.business.domain.dao.BpDomainDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}

	@PutMapping(value = "/TK0010I.do")
	@ResponseBody
	public Mono<Message> insertBpDomain(@RequestBody BpDomain bpDomain, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (bpDomain!= null) {
				int insertrows = bpDomainService.save(bpDomain);
				if (insertrows > 0) {
					return Mono.justOrEmpty(new Message("000001"));
				} else {
					return Mono.justOrEmpty(new Message("000004"));
				}
			} else {
				return Mono.justOrEmpty(new Message("000004"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}
	
	@PutMapping(value = "/TK0010U.do")
	@ResponseBody
	public Mono<Message> updateBpDomain(@RequestBody BpDomain bpDomain, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (bpDomain != null) {
				int updaterows = bpDomainService.update(bpDomain);
				if (updaterows > 0) {
					return Mono.justOrEmpty(new Message("000003"));
				} else {
					return Mono.justOrEmpty(new Message("000006"));
				}
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}
	
	@DeleteMapping("/TK0010D.do")
	@ResponseBody
	public Mono<Message> deleteBpDomain(@RequestParam String[] delKeys, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (delKeys == null || delKeys.length == 0)
				throw new BusinessException("000005");
			for (String delKey : delKeys) {
				bpDomainService.delete(delKey);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}

}
