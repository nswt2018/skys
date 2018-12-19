package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;

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
	public static List<Object[]> getMap(String cModuCode, String vuePath, String javaPath, String[] converTableArr,
			Model model,String uppersyscode) throws Exception {
		// 将模块代码全部小写
		String lModuCode =cModuCode.toLowerCase();
		// 替换字符串中‘\’为‘/’
		String vuepath = ConvertString.replace(vuePath);
		String javapath = ConvertString.replace(javaPath);
		// 生成文件的数量
		int fileNum = 7 + 3 * converTableArr.length;
		// 用于存放模板
		String[] templateArr = new String[fileNum];
		// 用于存放生成文件路径及文件名
		String[] pathArr = new String[fileNum];
		// 用于存放实体类model
		Model[] modelArr = new Model[fileNum];
		templateArr[0] = "com/sky/app/coder/templates/b/b-vue.vm";
		templateArr[1] = "com/sky/app/coder/templates/a-js.vm";
		templateArr[2] = "com/sky/app/coder/templates/b/b-controller.java.vm";
		templateArr[3] = "com/sky/app/coder/templates/b/b-Mapper.xml.vm";
		templateArr[4] = "com/sky/app/coder/templates/b/b-IService.java.vm";
		templateArr[5] = "com/sky/app/coder/templates/b/b-ServiceImpl.java.vm";
		templateArr[6] = "com/sky/app/coder/templates/b/b-dao.java.vm";
		pathArr[0] = vuepath + "/" + lModuCode + ".vue";
		pathArr[1] = vuepath + "/" + lModuCode + "-column.js";
		pathArr[2] = javapath + "/controller/" + uppersyscode +cModuCode + "Controller.java";
		pathArr[3] = javapath + "/mapper/" + uppersyscode +cModuCode + "Mapper.mysql.xml";
		pathArr[4] = javapath + "/service/I" + uppersyscode +cModuCode + "Service.java";
		pathArr[5] = javapath + "/service/impl/" + uppersyscode +cModuCode + "ServiceImpl.java";
		pathArr[6] = javapath + "/dao/" + uppersyscode +cModuCode + "Dao.java";
		for (int j = 0; j < fileNum; j++) {
			modelArr[j] = model;
		}
		for (int i = 0; i < converTableArr.length; i++) {
			Model cmodel=new Model();
			CopyUtils.Copy(model, cmodel);
			cmodel.setConverTableName(converTableArr[i]);
			// service接口
			templateArr[7 + i*3] = "com/sky/app/coder/templates/b/b-IService.java.vm";
			pathArr[7 + i*3] = javapath + "/service/I" + uppersyscode +cModuCode + converTableArr[i] + "Service.java";
			modelArr[7 + i*3] = cmodel;
			// service实现类
			templateArr[8 + i*3] = "com/sky/app/coder/templates/b/b-ServiceImpl.java.vm";
			pathArr[8 + i*3] = javapath + "/service/impl/" + uppersyscode +cModuCode + converTableArr[i] + "ServiceImpl.java";
			modelArr[8 + i*3] = cmodel;
			// dao接口
			templateArr[9 + i*3] = "com/sky/app/coder/templates/b/b-dao.java.vm";
			pathArr[9 + i*3] = javapath + "/dao/" + uppersyscode +cModuCode + converTableArr[i] + "Dao.java";
			modelArr[9 + i*3] = cmodel;
		}
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(modelArr);
		list.add(templateArr);
		list.add(pathArr);
		return list;
	}
}
