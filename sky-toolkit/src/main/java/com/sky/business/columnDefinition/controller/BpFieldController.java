package com.sky.business.columnDefinition.controller;

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

import com.sky.business.columnDefinition.model.BpField;
import com.sky.business.columnDefinition.service.IBpFieldService;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value="/business")
@CacheConfig(cacheNames = "skyCache")
public class BpFieldController extends BaseController {
	Log logger = LogFactory.getLog(BpFieldController.class);
	
	private final IBpFieldService bpFieldService;

	@Autowired
	public BpFieldController(final IBpFieldService bpFieldService) {
		this.bpFieldService = bpFieldService;
	}

	@RequestMapping(value="/TK0008L.do")
	@ResponseBody
	public Mono<Page<BpField>> getFieldList(@RequestBody Page<BpField> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		page.setRequest(request);
		bpFieldService.findForPageList("com.sky.business.columnDefinition.dao.BpFieldDao.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}
	
	@PutMapping("/TK0008I.do")
	@ResponseBody
	public Mono<Message> saveField(@RequestBody BpField bpField, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(bpFieldService.save(bpField) > 0)
				return Mono.justOrEmpty(new Message("000001"));
			else
				return Mono.justOrEmpty(new Message("100001"));
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000004", e.getMessage());
		}
	}
	
	@DeleteMapping("/TK0008D.do")
	@ResponseBody
	public Mono<Message> deleteByColCode(@RequestParam String[] codes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if(codes==null || codes.length == 0)
				throw new BusinessException("000005");
			for(String code : codes) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("colCode", code.split("/")[0]);
				map.put("tabCode", code.split("/")[1]);
				bpFieldService.delField("com.sky.business.columnDefinition.dao.BpFieldDao.deleteById", map);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}
	
	@RequestMapping("/TK0008U.do")
	@ResponseBody
	public Mono<Message> update(@RequestBody BpField bpField, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bpFieldService.update(bpField)>0) {
				return Mono.justOrEmpty(new Message("000003"));
			} else {
				return Mono.justOrEmpty(new Message("000006"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000006", e.getMessage());
		}
	}
	
	@RequestMapping(value="/TK0008L1.do")
	@ResponseBody
	public Mono<Message> findTable(@RequestParam String tabCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//查询数据库 该表是否存在
		List<BpField> list = bpFieldService.findForList("com.sky.business.columnDefinition.dao.BpFieldDao.findTable", tabCode);
		//存在则报错返回
		if(list != null && list.size() > 0){
			return Mono.justOrEmpty(new Message("100001", "该表已经存在"));
		}else{
			return Mono.justOrEmpty(new Message("000001"));
		}
	}
}
