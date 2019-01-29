package com.sky.db.as.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.core.base.controller.BaseController;
import com.sky.db.as.model.AsAstest;
import com.sky.db.as.service.IAsAstestService;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;

@RestController
public class AsAstestController extends BaseController{
	@Resource(name = "AsAstestService")
	private IAsAstestService AsAstestService;
	@RequestMapping(value = "/AsAS0001L.do", method = RequestMethod.POST)
	@ResponseBody
	public Mono<Page<AsAstest>> getAsAstestPageList(@RequestBody Page<AsAstest> page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		page.setRequest(request);
		AsAstestService.findForPageList("com.sky.app.mapper.AsAstestMapper.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}

	@PutMapping(value = "/AsAS0001I.do")
	@ResponseBody
	public Mono<Message> insertAsAstest(@RequestBody AsAstest Astest, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (Astest!= null) {
				int insertrows = AsAstestService.save(Astest);
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
	@PutMapping(value = "/AsAS0001U.do")
	@ResponseBody
	public Mono<Message> updateAsAstest(@RequestBody AsAstest Astest, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (Astest != null) {
				int updaterows = AsAstestService.update(Astest);
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
	@DeleteMapping("/AsAS0001D.do")
	@ResponseBody
	public Mono<Message> deleteAsAstest(@RequestParam String[] delKeys, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (delKeys == null || delKeys.length == 0)
				throw new BusinessException("000005");
			for (String delKey : delKeys) {
				AsAstestService.delete(delKey);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-000000", e.getMessage());
		}
	}

}
