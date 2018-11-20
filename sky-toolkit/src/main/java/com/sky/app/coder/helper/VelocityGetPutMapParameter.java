package com.sky.app.coder.helper;

import java.util.HashMap;
import java.util.Map;

import com.sky.app.coder.model.Element;

public class VelocityGetPutMapParameter {
	public static Map<String, String> getMap(Element el) {
		// 将模块代码首字母大写
		String cModuCode = ConvertString.convertFirstCharUpper(el.getModuCode());
		// 将模块代码字母全部小写
		String lModuCode = el.getModuCode().toLowerCase();
		// 替换字符串中‘\’为‘/’
		String vuepath = ConvertString.replace(el.getVuePath());
		String javapath = ConvertString.replace(el.getJavaPath());
		// 替换字符串中‘.’为‘/’
		String packpath = el.getPackName().replace(".", "/");
		// 将velocity生成文件后面两个参数放入map key(包名+模板名称)->value(文件路径+文件名)
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("com/sky/app/coder/templates/a-vue.vm", vuepath + "/" + lModuCode + "/" + lModuCode + ".vue");
		cmap.put("com/sky/app/coder/templates/a-js.vm", vuepath + "/" + lModuCode + "/" + lModuCode + "-column.js");
		cmap.put("com/sky/app/coder/templates/a-controller.java.vm",
				javapath + "/" + packpath + "/" + lModuCode + "/controller/" + cModuCode + "Controller.java");
		cmap.put("com/sky/app/coder/templates/a-Mapper.xml.vm",
				javapath + "/" + packpath + "/" + lModuCode + "/mapper/" + cModuCode + "Mapper.mysql.xml");
		cmap.put("com/sky/app/coder/templates/a-IService.java.vm",
				javapath + "/" + packpath + "/" + lModuCode + "/service/I" + cModuCode + "Service.java");
		cmap.put("com/sky/app/coder/templates/a-ServiceImpl.java.vm",
				javapath + "/" + packpath + "/" + lModuCode + "/service/impl/" + cModuCode + "ServiceImpl.java");
		cmap.put("com/sky/app/coder/templates/a-dao.java.vm",
				javapath + "/" + packpath + "/" + lModuCode + "/dao/" + cModuCode + "Dao.java");
		cmap.put("com/sky/app/coder/templates/a-model.java.vm",
				javapath + "/" + packpath + "/" + lModuCode + "/model/" + cModuCode + ".java");
		return cmap;
	}
}
