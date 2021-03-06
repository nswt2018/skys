package com.sky.factory.service.impl;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.core.base.service.impl.BaseServiceImpl;
import com.sky.factory.model.BpModuleModel;
import com.sky.factory.model.BpSystems;
import com.sky.factory.service.IBpSystemsService;

@Service("bpSystemsService")
public class BpSystemsServiceImpl extends BaseServiceImpl<BpSystems> implements IBpSystemsService{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<String> findMaxKey(String sqlId, String parameter) {
		return sqlSessionTemplate.selectList(sqlId, parameter);
	}

	public int deleteBySysKey(String sqlId, String parameter) {
		return sqlSessionTemplate.delete(sqlId, parameter);
	}
	@Override
	public List<BpModuleModel> getTreeRouter(String sqlId, String parameter){
		return sqlSessionTemplate.selectList(sqlId, parameter);
	}
	@Override
	public int updChildren(String sqlId, Map<String, String> map) {
		return sqlSessionTemplate.update(sqlId, map);
	}
	
	public boolean flushRouter(List<BpSystems> sList){
		
		File file = new File("D:/sky-plat/softfactory/sofa/src/router/router.js");//定义一个file对象，用来初始化FileInputStream
		FileInputStream is = null;
		BufferedReader bReader = null;
		InputStreamReader isr = null;
		BufferedWriter writer = null;
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "utf-8");
			bReader = new BufferedReader(isr); //实例化一个BufferedReader对象，将文件内容读取到缓存
			StringBuilder sb = new StringBuilder(); //定义一个字符串缓存，将字符串存放缓存中
			String str = null;
			String begin = "//BEGIN"; //开始标识
			String end = "//END"; //结束标识
			while ((str = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
				sb.append(str + "\n"); //将读取的字符串添加换行符后累加存放在缓存中
			}
			
			List<BpSystems> sList1 = new ArrayList<BpSystems>();
			List<BpSystems> sList2 = new ArrayList<BpSystems>();
			List<BpSystems> sList3 = new ArrayList<BpSystems>();
			List<BpSystems> sList4 = new ArrayList<BpSystems>();
			
			for (BpSystems bpSystems : sList) {
				String sKey = bpSystems.getSysKey();
				if(sKey.length() == 2) sList1.add(bpSystems);
				else if(sKey.length() == 4) sList2.add(bpSystems);
				else if(sKey.length() == 6) sList3.add(bpSystems);
				else if(sKey.length() == 8) sList4.add(bpSystems);
			}
			
			//拼接菜单
			StringBuilder menu = new StringBuilder();
			for (int i = 0; i < sList1.size(); i++) {
				BpSystems bpSystems = sList1.get(i);
				String modCode = bpSystems.getModCode();
				menu.append("\n");
				menu.append("\t{\n");
				menu.append("\t\tpath: '/"+ bpSystems.getSysCode().toLowerCase() +"',\n");
				menu.append("\t\ticon: 'android-checkbox',\n");
				menu.append("\t\tname: '"+ bpSystems.getSysCode() +"',\n");
				menu.append("\t\ttitle: '"+ bpSystems.getSysName() +"',\n");
				menu.append("\t\tcomponent: Main,\n");
				menu.append("\t\tchildren: [\n");
				
				if(modCode != null && !("".equals(modCode))){ //一级菜单
					menu.append("\t\t\t{ path: '" + bpSystems.getSysCode().toLowerCase() +"list', title: '"+ bpSystems.getSysName() + "', "
						+ "name: '" + bpSystems.getSysCode() + "-info',icon: 'android-checkbox', component: () => "
						+ "import('@/views/" + bpSystems.getSysCode().toLowerCase() + "/" + bpSystems.getModCode().toLowerCase() + ".vue') },\n");
				}else{
					for (int j = 0; j <sList2.size(); j++) {
						BpSystems bpSystems2 = sList2.get(j);
						String modCode2 = bpSystems2.getModCode();
						if(bpSystems2.getUpperSys().equals(bpSystems.getSysKey())){ //子节点
							if(modCode2 != null && !("".equals(modCode2))){ //二级菜单
								menu.append("\t\t\t{ path: '" + bpSystems2.getSysCode().toLowerCase() +"list', title: '"+ bpSystems2.getSysName() + "', "
									+ "name: '" + bpSystems2.getSysCode() + "-info',icon: 'navicon-round', component: () => "
									+ "import('@/views/" + bpSystems.getSysCode().toLowerCase() + "/" + bpSystems2.getSysCode().toLowerCase() + "/" + bpSystems2.getModCode().toLowerCase() + ".vue') },\n");
							}else{ //三、四级菜单公共部分
								menu.append("\t\t\t{\n");
								menu.append("\t\t\t\tpath: '" + bpSystems2.getSysCode().toLowerCase() +"list', title: '"+ bpSystems2.getSysName() 
									+ "', name: '" + bpSystems2.getSysCode() + "-info',icon: 'navicon-round', component: () => "
									+ "import('@/views/system/business/artical-publish-center.vue'),\n");
								menu.append("\t\t\t\tchildren: [\n");
								
								for (int k = 0; k < sList3.size(); k++) {
									BpSystems bpSystems3 = sList3.get(k);
									String modCode3 = bpSystems3.getModCode();
									if(bpSystems3.getUpperSys().equals(bpSystems2.getSysKey())){
										
										if(modCode3 != null && !("".equals(modCode3))){ //三级菜单
											
											menu.append("\t\t\t\t\t{ path: '" + bpSystems3.getSysCode().toLowerCase() +"list', title: '"+ bpSystems3.getSysName() + "', "
												+ "name: '" + bpSystems3.getSysCode() + "-info',icon: 'navicon-round', component: () => "
												+ "import('@/views/" + bpSystems.getSysCode().toLowerCase() + "/" + bpSystems2.getSysCode().toLowerCase() + "/" 
												+ bpSystems3.getSysCode().toLowerCase() + "/" + bpSystems3.getModCode().toLowerCase() + ".vue') },\n");
											
										}else{
											menu.append("\t\t\t\t\t{\n");
											menu.append("\t\t\t\t\t\tpath: '" + bpSystems3.getSysCode().toLowerCase() +"list', title: '"+ bpSystems3.getSysName() 
												+ "', name: '" + bpSystems3.getSysCode() + "-info',icon: 'navicon-round', component: () => "
												+ "import('@/views/system/business/artical-publish-center.vue'),\n");
											menu.append("\t\t\t\t\t\tchildren: [\n");
											
											for (int l = 0; l < sList4.size(); l++) {
												BpSystems bpSystems4 = sList4.get(l);
												if(bpSystems4.getUpperSys().equals(bpSystems3.getSysKey())){
													for (BpSystems bpSystems5 : sList4) {
														if(bpSystems5.getUpperSys().equals(bpSystems3.getSysKey())){
															menu.append("\t\t\t\t\t\t\t{ path: '" + bpSystems5.getSysCode().toLowerCase() +"list', title: '"+ bpSystems5.getSysName() + "', "
																+ "name: '" + bpSystems5.getSysCode() + "-info',icon: 'navicon-round', component: () => "
																+ "import('@/views/" + bpSystems.getSysCode().toLowerCase() + "/" + bpSystems2.getSysCode().toLowerCase() + "/" 
																+ bpSystems3.getSysCode().toLowerCase() + "/" + bpSystems5.getSysCode().toLowerCase() +"/" + bpSystems5.getModCode().toLowerCase() + ".vue') },\n");
														}
													}
													
													menu.append("\t\t\t\t\t\t]\n");
													menu.append("\t\t\t\t\t},\n");
													break;
												}
											}
										}
									}
								}
								
								menu.append("\t\t\t\t]\n");
								menu.append("\t\t\t},\n");
							}
						}
					}
				}
				
				menu.append("\t\t]\n");
				menu.append("\t},\n");
				menu.append("\n");
			}
			
			//替换路由
			sb.replace(sb.indexOf(begin)+7, sb.indexOf(end), menu.toString());
			System.out.println(menu.toString());
			
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); 
			writer=new BufferedWriter(out);   
			writer.write("");
			writer.write(sb.toString());
			writer.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
public boolean flushOtherRouter(List<BpModuleModel> moduleList){
		
		File file = new File("D:/sky-plat/softfactory/sofa/src/router/router.js");//定义一个file对象，用来初始化FileInputStream
		FileInputStream is = null;
		BufferedReader bReader = null;
		InputStreamReader isr = null;
		BufferedWriter writer = null;
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "utf-8");
			bReader = new BufferedReader(isr); //实例化一个BufferedReader对象，将文件内容读取到缓存
			StringBuilder sb = new StringBuilder(); //定义一个字符串缓存，将字符串存放缓存中
			String str = null;
			String begin = "//otherRouterBegin"; //开始标识
			String end = "//otherRouterEnd"; //结束标识
			while ((str = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
				sb.append(str + "\n"); //将读取的字符串添加换行符后累加存放在缓存中
			}
			StringBuilder menu = new StringBuilder();
			menu.append("\n");
			for (int i = 0; i < moduleList.size(); i++) {
				BpModuleModel bpModule = moduleList.get(i);
				if(bpModule.getModName().equals("树模型")){
					menu.append("\t\t{ path: '" + bpModule.getModuCode().toLowerCase() +"treemode', title: '"+ bpModule.getModuCName() + "树节点信息', "
							+ "name: '" + bpModule.getModuCode().toLowerCase() +"treemode', component: () => "
							+ "import('@/views/treemode/" + bpModule.getModuCode().toLowerCase() +"treemode.vue') },\n");

				}
			}
			menu.append("\n");
			
			//替换路由
			sb.replace(sb.indexOf(begin)+18, sb.indexOf(end), menu.toString());
			System.out.println(menu.toString());
			
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); 
			writer=new BufferedWriter(out);   
			writer.write("");
			writer.write(sb.toString());
			writer.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}

