package com.sky.app.coder.helper;

import java.io.StringWriter;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/*
 * velocity初始化操作
 * 1.初始化模板引擎
 * 2.获取模板文件
 * 3.输出,根据模板生成相应的文件
 */
public class VelocityCoder {
	// velocity初始化操作公共方法
	public static void velocity(VelocityContext vcx, String templatename, String path) {
		// 初始化模板引擎
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.setProperty(Velocity.ENCODING_DEFAULT, "utf-8");
		ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");
		ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
		ve.init();
		// 获取模板文件
		Template t = ve.getTemplate(templatename);
		// 输出
		StringWriter sw = new StringWriter();
		t.merge(vcx, sw);
		if (path.equals("") || path == null) {
			System.out.println(sw.toString());
		} else {
			// 创建文件并写入内容
			VelocityFileCreateByTemplate vtfc = new VelocityFileCreateByTemplate();
			vtfc.createFile(path, sw);
		}
	}

}
