package com.sky.app.coder.helper;

import java.util.HashMap;
import java.util.Map;


public class VelocityGetPutMapParameter {
	public static Map<String, String> getMap(String moduCode, String vuePath, String javaPath, String uppersyscode,String moduname,String vuePathBef) {
		// 将模块代码字母全部小写
		String lModuCode = moduCode.toLowerCase();
		// 先小写，模块代码首字母大写
		String cModuCode = ConvertString.convertFirstCharUpper(lModuCode);
		// 替换字符串中‘\’为‘/’
		vuePathBef = ConvertString.replace(vuePathBef.replace(" ", ""));
		vuePath = ConvertString.replace(vuePath.replace(" ", ""));
		javaPath = ConvertString.replace(javaPath.replace(" ", ""));
		
		// 将velocity生成文件后面两个参数放入map (包名+模板名称)->value(文件路径+文件名)
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("com/sky/app/coder/templates/a-vue.vm", vuePath + "/" + lModuCode + ".vue");
		if("树模型".equals(moduname)){
			cmap.put("com/sky/app/coder/templates/d/treemode.vm", vuePathBef + "/treemode/" + lModuCode+ "treemode" + ".vue");
			cmap.put("com/sky/app/coder/templates/d/treemode_column.vm", vuePathBef + "/treemode/" + lModuCode+ "treemode-column" + ".js");
		}
		cmap.put("com/sky/app/coder/templates/a-js.vm", vuePath + "/" + lModuCode + "-column.js");
		cmap.put("com/sky/app/coder/templates/a-controller.java.vm",
				javaPath + "/controller/" + uppersyscode + cModuCode + "Controller.java");
		cmap.put("com/sky/app/coder/templates/a-Mapper.xml.vm",
				javaPath + "/mapper/" + uppersyscode + cModuCode + "Mapper.mysql.xml");
		cmap.put("com/sky/app/coder/templates/a-IService.java.vm",
				javaPath + "/service/I" + uppersyscode + cModuCode + "Service.java");
		cmap.put("com/sky/app/coder/templates/a-ServiceImpl.java.vm",
				javaPath + "/service/impl/" + uppersyscode + cModuCode + "ServiceImpl.java");
		cmap.put("com/sky/app/coder/templates/a-dao.java.vm",
				javaPath + "/dao/" + uppersyscode + cModuCode + "Dao.java");
		cmap.put("com/sky/app/coder/templates/a-model.java.vm",
				javaPath + "/model/" + uppersyscode + cModuCode + ".java");
		return cmap;
	}
}
