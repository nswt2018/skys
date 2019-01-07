package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.app.coder.model.Model;

public class VelocityGetPutMapMulitParameter {
	/**
	 * 
	 * @param cModuCode
	 * @param vuePath
	 * @param javaPath
	 * @param converTableArr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMap(String cModuCode, String vuePath, String javaPath, String uppersyscode)
			throws Exception {
		// 将模块代码全部小写
		String lModuCode = cModuCode.toLowerCase();
		// 替换字符串中‘\’为‘/’
		String vuepath = ConvertString.replace(vuePath);
		String javapath = ConvertString.replace(javaPath);

		// 将velocity生成文件后面两个参数放入map (包名+模板名称)->value(文件路径+文件名)
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("com/sky/app/coder/templates/b/b-vue.vm", vuepath + "/" + lModuCode + ".vue");
		cmap.put("com/sky/app/coder/templates/a-js.vm", vuepath + "/" + lModuCode + "-column.js");
		cmap.put("com/sky/app/coder/templates/b/b-controller.java.vm",
				javapath + "/controller/" + uppersyscode + cModuCode + "Controller.java");
		cmap.put("com/sky/app/coder/templates/b/b-Mapper.xml.vm",
				javapath + "/mapper/" + uppersyscode + cModuCode + "Mapper.mysql.xml");
		cmap.put("com/sky/app/coder/templates/b/b-IService.java.vm",
				javapath + "/service/I" + uppersyscode + cModuCode + "Service.java");
		cmap.put("com/sky/app/coder/templates/b/b-ServiceImpl.java.vm",
				javapath + "/service/impl/" + uppersyscode + cModuCode + "ServiceImpl.java");
		cmap.put("com/sky/app/coder/templates/b/b-model.java.vm",
				javapath + "/model/" + uppersyscode +cModuCode + ".java");
		cmap.put("com/sky/app/coder/templates/b/b-dao.java.vm",
				javapath + "/dao/" + uppersyscode +cModuCode + "Dao.java");
		return cmap;
	}
}
