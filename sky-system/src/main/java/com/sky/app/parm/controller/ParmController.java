package com.sky.app.parm.controller;


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

import com.sky.app.parm.model.Parm;
import com.sky.app.parm.service.IParmService;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/system")
public class ParmController {
	@Resource(name = "parmservice")
	private IParmService parmService;

	@RequestMapping(value = "/SY0002L3.do", method = RequestMethod.POST)
	@ResponseBody
	public Mono<Page<Parm>> getParmPageList(@RequestBody Page<Parm> page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		page.setRequest(request);
		parmService.findForPageList("com.sky.app.parm.mapper.parmmapper.findForPageList", page);
		page.setRequest(null);
		return Mono.justOrEmpty(page);
	}

	@PutMapping(value = "/SY0002I2.do")
	@ResponseBody
	public Mono<Message> insertParm(@RequestBody Parm parm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (parm.getParaCode() != null) {
				int insertrows = parmService.insertParm("com.sky.app.parm.mapper.parmmapper.insertParmId", parm);
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
			throw new BusinessException("000004", e.getMessage());
		}
	}

	@PutMapping(value = "/SY0002U2.do")
	@ResponseBody
	public Mono<Message> updateParm(@RequestBody Parm parm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (parm.getParaCode() != null) {
				int updaterows = parmService.updateParm("com.sky.app.parm.mapper.parmmapper.updateParmId", parm);
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
			throw new BusinessException("000006", e.getMessage());
		}
	}

	@DeleteMapping("/SY0002D2.do")
	@ResponseBody
	public Mono<Message> deleteParm(@RequestParam String[] paraCode, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (paraCode == null || paraCode.length == 0)
				throw new BusinessException("000005");
			for (String id : paraCode) {
				parmService.deleteParm("com.sky.app.parm.mapper.parmmapper.deleteParmId", id);
			}
			return Mono.justOrEmpty(new Message("000002"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005", e.getMessage());
		}
	}

}
