package com.sky.app.coder.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
/*
 * 1.创建文件路径
 * 2.创建文件
 * 3.往文件写入数据
 */

public class VelocityFileCreateByTemplate {
	// 创建文件路径
	public void createFile(String path, StringWriter sw) {
		boolean flag=false;
		File file = new File(path);
		//判断文件路径是否存在，不存在则自己创建
		if (!file.getParentFile().exists()) {
			boolean result = file.getParentFile().mkdirs();
			if (result) {
				flag=true;
			} else {
				System.out.println("创建文件目录失败");
			}
		} else {
			flag=true;
		}
		//目录创建成功后才判断文件是否存在
		if(flag){
			this.isFileExists(file, sw);
		}
	}

	// 判断文件是否存在
	public void isFileExists(File file, StringWriter sw) {
		try {
			boolean flag=false;
			// 先判断文件是否存在
			if (file.exists()) {
				// 如果文件存在则删除文件
				file.delete();
				flag=true;
			} else {
				flag=true;
			}
			// 创建文件
			if(flag){
				this.creatFileWrite(file, sw);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 创建文件并往文件写入数据
	public void creatFileWrite(File file, StringWriter sw) throws IOException {
		if (file.createNewFile()) {
			// 文件创建成功后就往文件写入
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(sw.toString(), 0, sw.toString().length() - 1);
			out.close();
		} else {
			System.out.println("文件创建失败！");
		}
	}
}
