package com.sky.app.coder.velocity;

import java.util.HashMap;
import java.util.Map;

import com.sky.app.coder.helper.ConvertString;

public class VelocityGetPutMapMsParameter {
	public static Map<String, String> getMap(String moduCode, String vuePath, String javaPath, String uppersyscode) {
		// 将模块代码字母全部小写
		String lModuCode = moduCode.toLowerCase();
		// 先小写，模块代码首字母大写
		String cModuCode = ConvertString.convertFirstCharUpper(lModuCode);
		// 替换字符串中‘\’为‘/’
		String vuepath = ConvertString.replace(vuePath.replace(" ", ""));
		String javapath = ConvertString.replace(javaPath.replace(" ", ""));
		// 将velocity生成文件后面两个参数放入map (包名+模板名称)->value(文件路径+文件名)
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("com/sky/app/coder/templates/c/c-vue.vm", vuepath + "/" + lModuCode + ".vue");
		cmap.put("com/sky/app/coder/templates/a-js.vm", vuepath + "/" + lModuCode + "-column.js");
		cmap.put("com/sky/app/coder/templates/c/c-js.vm", vuepath + "/" + "ms" + lModuCode + "-column.js");
		// 主表contriller
		cmap.put("com/sky/app/coder/templates/a-controller.java.vm",
				javapath + "/controller/" + uppersyscode + cModuCode + "Controller.java");
		// 从表controller
		cmap.put("com/sky/app/coder/templates/c/c-controller.java.vm",
				javapath + "/controller/" + "Ms" + uppersyscode + cModuCode + "Controller.java");
		// 主表Mapper
		cmap.put("com/sky/app/coder/templates/a-Mapper.xml.vm",
				javapath + "/mapper/" + uppersyscode + cModuCode + "Mapper.mysql.xml");
		// 从表Mapper
		cmap.put("com/sky/app/coder/templates/c/c-Mapper.xml.vm",
				javapath + "/mapper/" + "Ms" + uppersyscode + cModuCode + "Mapper.mysql.xml");
		// 主表service接口
		cmap.put("com/sky/app/coder/templates/a-IService.java.vm",
				javapath + "/service/I" + uppersyscode + cModuCode + "Service.java");
		// 从表service接口
		cmap.put("com/sky/app/coder/templates/c/c-IService.java.vm",
				javapath + "/service/I" + "Ms" + uppersyscode + cModuCode + "Service.java");
		// 主表service实现类
		cmap.put("com/sky/app/coder/templates/a-ServiceImpl.java.vm",
				javapath + "/service/impl/" + uppersyscode + cModuCode + "ServiceImpl.java");
		// 从表service实现类
		cmap.put("com/sky/app/coder/templates/c/c-ServiceImpl.java.vm",
				javapath + "/service/impl/" + "Ms" + uppersyscode + cModuCode + "ServiceImpl.java");
		// 主表dao
		cmap.put("com/sky/app/coder/templates/a-dao.java.vm",
				javapath + "/dao/" + uppersyscode + cModuCode + "Dao.java");
		// 从表dao
		cmap.put("com/sky/app/coder/templates/c/c-dao.java.vm",
				javapath + "/dao/" + "Ms" + uppersyscode + cModuCode + "Dao.java");
		// 主表实体类
		cmap.put("com/sky/app/coder/templates/a-model.java.vm",
				javapath + "/model/" + uppersyscode + cModuCode + ".java");
		// 从表实体类
		cmap.put("com/sky/app/coder/templates/c/c-model.java.vm",
				javapath + "/model/" + "Ms" + uppersyscode + cModuCode + ".java");
		return cmap;
	}
}
