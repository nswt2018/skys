package com.sky.app.coder.controller;

import java.util.HashMap;
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

import com.sky.app.coder.helper.VelocityGetTemplateData;
import com.sky.app.coder.helper.ConvertString;
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
	 * 3.将模板所需要的全部信息封装在model实体类中 。 4.通过velocity根据传入的数据、模板、路径生成相应的文件。
	 */
	@RequestMapping(value = "/TK0004G.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean getSysParmList(@RequestParam String moduCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 从数据库中取数据
			List<Element> list = CoderService.getTagInfo("com.sky.app.core.CoderMapper.findBpForList", moduCode);
			Element el = CoderService.getElement("com.sky.app.core.CoderMapper.findBpForOne", moduCode);
			// 将数据库取出来的值放入model解析,然后取得model
			Model model = new VelocityGetTemplateData().getModel(list, el);
			// 将model里面的值放入VelocityContext
			VelocityContext vcx = new VelocityContext();
			vcx.put("models", model);
			// 将模块代码首字母大写
			String cModuCode = ConvertString.convertFirstCharUpper(el.getModuCode());
			String lModuCode = el.getModuCode().toLowerCase();
			String vuepath = ConvertString.replace(el.getVuePath());
			String javapath = ConvertString.replace(el.getJavaPath());
			// 将velocity生成文件后面两个参数放入map key(包名+模板名称)->value(文件路径+文件名)
			Map<String, String> cmap = new HashMap<String, String>();
			cmap.put("com/sky/app/coder/templates/a-vue.vm", vuepath +"/"+ lModuCode + ".vue");
			cmap.put("com/sky/app/coder/templates/a-js.vm", vuepath + "/"+lModuCode + "-column.js");
			cmap.put("com/sky/app/coder/templates/a-controller.java.vm",
					javapath + lModuCode + "/controller/" + cModuCode + "Controller.java");
			cmap.put("com/sky/app/coder/templates/a-Mapper.xml.vm",
					javapath + lModuCode + "/mapper/" + cModuCode + "Mapper.mysql.xml");
			cmap.put("com/sky/app/coder/templates/a-IService.java.vm",
					javapath + lModuCode + "/service/I" + cModuCode + "Service.java");
			cmap.put("com/sky/app/coder/templates/a-ServiceImpl.java.vm",
					javapath + lModuCode + "/service/impl/" + cModuCode + "ServiceImpl.java");
			cmap.put("com/sky/app/coder/templates/a-dao.java.vm",
					javapath + lModuCode + "/dao/" + cModuCode + "Dao.java");
			cmap.put("com/sky/app/coder/templates/a-model.java.vm",
					javapath + lModuCode + "/model/" + cModuCode + ".java");
			for (String key : cmap.keySet()) {
				// 根据传入的数据、模板、路径生成相应的文件
				VelocityCoder.velocity(vcx, key, cmap.get(key));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
