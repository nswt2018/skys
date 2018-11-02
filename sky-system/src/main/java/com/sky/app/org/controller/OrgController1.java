package com.sky.app.org.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.app.org.model.Org1;
import com.sky.app.org.service.IOrgService1;
import com.sky.core.base.controller.BaseController;
import com.sky.core.exception.BusinessException;
import com.sky.core.message.Message;
import com.sky.core.page.Page;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/system")
public class OrgController1 {
	@Resource(name = "orgservice1")
	private IOrgService1 orgservice1;

	@RequestMapping(value = "/SY0003L1.do")
	@ResponseBody
	public Mono<Page<Org1>> getOrgList(@RequestBody Page<Org1> page, HttpServletRequest request,
			HttpServletResponse response) {
		orgservice1.findForPageList("com.sky.app.org.mapper.orgmapper.findForOrgList", page);
		return Mono.justOrEmpty(page);
	}

	@RequestMapping(value = "/SY0003I1.do")
	@ResponseBody
	public Mono<Message> insertOrg(@RequestBody Org1 org, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (org.getOrgId() != null) {
				int insertrows = orgservice1.insertOrg("com.sky.app.org.mapper.orgmapper.insertOrgId", org);
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
			throw new BusinessException("000004",e.getMessage());
		}
	}
	@RequestMapping(value = "/SY0003U1.do")
	@ResponseBody
	public Mono<Message> updateOrg(@RequestBody Org1 org, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (org.getOrgId() != null) {
				int updaterows = orgservice1.updateOrg("com.sky.app.org.mapper.orgmapper.updateOrgId", org);
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
			throw new BusinessException("000006",e.getMessage());
		}
	}
	@RequestMapping(value = "/SY0003D1.do")
	@ResponseBody
	public Mono<Message> deleteOrg(@RequestParam String[] orgid, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (orgid!= null) {
				for(String key:orgid){
					orgservice1.deleteOrg("com.sky.app.org.mapper.orgmapper.deleteOrgId", key);
				}
				return Mono.justOrEmpty(new Message("000002"));
			} else {
				return Mono.justOrEmpty(new Message("000005"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("000005",e.getMessage());
		}
	}
}
