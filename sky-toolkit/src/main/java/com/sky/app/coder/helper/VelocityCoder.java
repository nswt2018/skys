package com.sky.app.coder.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	public static void velocity(VelocityContext vcx, String templatename, String path) throws IOException {
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
		// 创建文件
		File saveFile = new File(path);
		// 获得它的父类文件，如果不存在，就创建
		if (!saveFile.getParentFile().exists()) {
			saveFile.getParentFile().mkdirs();
		}
		// 创建文件输出流
		FileOutputStream outStream = new FileOutputStream(saveFile);
		// 因为模板整合的时候，需要提供一个Writer，所以创建一个Writer
		OutputStreamWriter writer = new OutputStreamWriter(outStream);
		// 创建一个缓冲流
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		t.merge(vcx, bufferWriter);
		bufferWriter.flush();// 强制刷新
		outStream.close();
		bufferWriter.close();
	}

}
