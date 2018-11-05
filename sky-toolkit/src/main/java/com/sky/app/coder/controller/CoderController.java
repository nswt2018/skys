package com.sky.app.coder.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.app.coder.congfig.Configuration;
import com.sky.app.coder.helper.VelocityGetTemplateData;
import com.sky.app.coder.helper.VelocityCoder;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.service.ICoderService;

@RestController
@RequestMapping(value = "/business")
public class CoderController {
	@Resource(name = "CoderService")
	private ICoderService CoderService;

	/*
	 * 1.从数据库取出模板中前端组件的标签信息。
	 * 2.解析配置文件（coderConfig.xml），读出配置参数：是否生成文件、文件生成路径、模板名称、包名。
	 * 3.将模板所需要的全部信息封装在model实体类中 。
	 * 4.通过velocity根据传入的数据、模板、路径生成相应的文件。
	 */
	@RequestMapping(value = "/TK0004G.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean getSysParmList(@RequestParam String moduCode, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			//从数据库中取数据
			List<Element> list = CoderService.getTagInfo("com.sky.app.core.CoderMapper.findBpForList", moduCode);
			Element el=CoderService.getElement("com.sky.app.core.CoderMapper.findBpForOne", moduCode);
			// 将数据库取出来的值(list)解析并放入model,然后取得model
			Model model = new VelocityGetTemplateData().getModel(list,el);
			// 将model里面的值放入VelocityContext
			VelocityContext vcx = new VelocityContext();
			vcx.put("models", model);
			// 读取配置文件coderConfig.xml,将配置文件的参数放入map  key(模板名称)->value(文件生成路径)
			Map<String, String> cmap = Configuration.loadConfig("a");
			for (String key : cmap.keySet()) { 
				// 根据传入的数据、模板、路径生成相应的文件
				VelocityCoder.velocity(vcx, "com/sky/app/coder/templates/"+ key, cmap.get(key));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
