package com.sky.business.commonfield.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sky.business.columnDefinition.model.BpField;
import com.sky.business.columnDefinition.service.IBpFieldService;
import com.sky.business.commonfield.model.BpCommonfield;
import com.sky.business.commonfield.service.IBpCommonfieldService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpCommonfieldController extends BaseController{
	@Resource(name = "bpCommonfieldService")
	private IBpCommonfieldService bpCommonfieldService;
	
	@Resource(name = "bpFieldService")
	private IBpFieldService bpFieldService;
	
	@RequestMapping(value = "/TK0009L.do", method = RequestMethod.POST)
	@ResponseBody
	public Mono<Page<BpCommonfield>> getCommonfieldPageList(@RequestBody Page<BpCommonfield> page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		page.setRequest(request);
		bpCommonfieldService.findForPageList("com.sky.business.commonfield.dao.BpCommonfieldDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}

	@PutMapping(value = "/TK0009I.do")
	@ResponseBody
	public Mono<Message> insertCommonfield(@RequestBody BpCommonfield bpCommonfield, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (bpCommonfield!= null) {
				int insertrows = bpCommonfieldService.save(bpCommonfield);
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
	
	@PutMapping(value = "/TK0009U.do")
	@ResponseBody
	public Mono<Message> updateCommonfield(@RequestBody BpCommonfield bpCommonfield, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (bpCommonfield != null) {
				int updaterows = bpCommonfieldService.update(bpCommonfield);
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
	
	@DeleteMapping("/TK0009D.do")
	@ResponseBody
	public Mono<Message> deleteCommonfield(@RequestParam String[] delKeys, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (delKeys == null || delKeys.length == 0)
				throw new BusinessException("000005");
			for (String delKey : delKeys) {
				bpCommonfieldService.delete(delKey);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0009I1.do")
	@ResponseBody
	public Mono<Message> insertCol(@RequestParam String[] colCode, @RequestParam String tabCode, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("colCode", colCode);
			
			List<BpField> bcpList = bpFieldService.findForList("com.sky.business.commonfield.dao.BpCommonfieldDao.findColByTabCode", map);
			for (BpField bpField : bcpList) {
				bpField.setTabCode(tabCode);
				bpFieldService.save(bpField);
			}
			
			return Mono.justOrEmpty(new Message("000001"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}

}
