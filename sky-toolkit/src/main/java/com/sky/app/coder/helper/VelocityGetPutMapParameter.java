package com.sky.app.coder.helper;

import java.util.HashMap;
import java.util.Map;

import com.sky.app.coder.model.Element;

public class VelocityGetPutMapParameter {
	public static Map<String, String> getMap(String moduCode, String vuePath, String javaPath) {
		// 将模块代码字母全部小写
		String lModuCode = moduCode.toLowerCase();
		// 先小写，模块代码首字母大写
		String cModuCode = ConvertString.convertFirstCharUpper(lModuCode);
		// 替换字符串中‘\’为‘/’
		String vuepath = ConvertString.replace(vuePath);
		String javapath = ConvertString.replace(javaPath);
		// 将velocity生成文件后面两个参数放入map (包名+模板名称)->value(文件路径+文件名)
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("com/sky/app/coder/templates/a-vue.vm", vuepath + "/" + "/" + lModuCode + ".vue");
		cmap.put("com/sky/app/coder/templates/a-js.vm", vuepath + "/" + "/" + lModuCode + "-column.js");
		cmap.put("com/sky/app/coder/templates/a-controller.java.vm",
				javapath + "/controller/" + cModuCode + "Controller.java");
		cmap.put("com/sky/app/coder/templates/a-Mapper.xml.vm", javapath + "/mapper/" + cModuCode + "Mapper.mysql.xml");
		cmap.put("com/sky/app/coder/templates/a-IService.java.vm",
				javapath + "/service/I" + cModuCode + "Service.java");
		cmap.put("com/sky/app/coder/templates/a-ServiceImpl.java.vm",
				javapath + "/service/impl/" + cModuCode + "ServiceImpl.java");
		cmap.put("com/sky/app/coder/templates/a-dao.java.vm", javapath + "/dao/" + cModuCode + "Dao.java");
		cmap.put("com/sky/app/coder/templates/a-model.java.vm", javapath + "/model/" + cModuCode + ".java");
		return cmap;
	}
}
