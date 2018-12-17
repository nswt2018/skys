package com.sky.app.coder.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.app.coder.helper.VelocityGetTemplateData;
import com.sky.app.coder.helper.VelocityGetTemplateMulitData;
import com.sky.app.coder.helper.ConvertString;
import com.sky.app.coder.helper.VelocityCoder;
import com.sky.app.coder.helper.VelocityGetPutMapMulitParameter;
import com.sky.app.coder.helper.VelocityGetPutMapParameter;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.model.Systems;
import com.sky.app.coder.service.ICoderService;

@RestController
@RequestMapping(value = "/business")
public class CoderController {
	@Resource(name = "CoderService")
	private ICoderService CoderService;
	/*
	 * 1.从数据库取出模板中前端组件的标签信息。
	 * 2.解析配置文件（coderConfig.xml），读出配置参数：是否生成文件、文件生成路径、模板名称、包名。
	 * 3.将模板所需要的全部信息封装在model实体类中 。 
	 * 4.通过velocity根据传入的数据、模板、路径生成相应的文件。
	 */
	@RequestMapping(value = "/TK0004G.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean getSysParmList(@RequestParam String sysKey, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			List<Systems> syslist=CoderService.getSystems("com.sky.app.core.CoderMapper.findBpSystemsList", sysKey.substring(0, 2));
			for(int i=0;i<syslist.size();i++){
				if(!syslist.get(i).getModCode().equals(null)&&!syslist.get(i).getModCode().equals("")){
					List<String> upperSysList=ConvertString.subString(syslist.get(i).getSysKey());
					String vuePath=null;
					String javaPath=null;
					String sysCode="";
					String tempSysCode=null;
					String packName=null;
					String lastSysCode=null;
					for(int j=0;j<upperSysList.size();j++){
						Systems system=CoderService.getSystemsOne("com.sky.app.core.CoderMapper.findBpSystemsOne", upperSysList.get(j));
						if(!system.getVuePath().equals("") && !system.getJavaPath().equals("")){
							vuePath=system.getVuePath();
							javaPath=system.getJavaPath();
							packName=system.getPackName();
						}
						tempSysCode=system.getSysCode().toLowerCase();
						packName+="."+tempSysCode;
						if(sysCode.equals("")){
							sysCode="\\"+tempSysCode;
						}else{
							sysCode+="\\"+tempSysCode;
						}
						if(j==upperSysList.size()-1){
							lastSysCode=system.getSysCode().toLowerCase();
						}
					}
					vuePath=vuePath+sysCode;
					javaPath=javaPath+"\\"+packName.replace(".", "\\");
					//模块代码
					String moduCode=syslist.get(i).getModCode();
					//放模板所需要的全部变量
					Model model =null;
					// 将model里面的变量值放入VelocityContext
					VelocityContext vcx = new VelocityContext();
					//根据模块代码获得该模块的模型，判断单表还是多表
					Element element = CoderService.getModuleOne("com.sky.app.core.CoderMapper.findBpModuleForOne",moduCode);
					if(element!=null){
						//模块模型
						String modName=element.getModName();
						//先判断该模块的模型,进行不同的处理
						if(modName.equals("多表模型")){
							// 根据模块代码从页面元素表中取出该模块全部的字段信息，并根据字段关联字段定义表(多个)，获得字段在数据库的类型
							List<Element> mulilist = CoderService.getMultiTagInfo("com.sky.app.core.CoderMapper.findBpElementForList",moduCode);
							//存放表的主键字段
							List<String> primlist=new ArrayList<String>();
							//存放表的表名加上主键组合的字段
							List<String> tableprimlist=new ArrayList<String>();
							//用于放表的主键策略
							List<String> tableprimpklist=new ArrayList<String>();
							// 将模块的关联表转放入数组中
							String[] tableArr = element.getRelTable().split(",");
							//存放转换后的关联表，用于dao、service接口、service实现类、实体类类名的组装
							String[] converTableArr=new String[tableArr.length];
							for(int j=0;j<tableArr.length;j++){
								//根据模块关联表表名，从字段定义表中查询每个表的主键，放入parmlist集合中
								Element muliel = CoderService.getMultiFieldOne("com.sky.app.core.CoderMapper.findBpFieldForOne", tableArr[j]);
								primlist.add(muliel.getColCode());
								tableprimpklist.add(muliel.getPkGen());
								tableprimlist.add(ConvertString.convertSomeCharUpperReplace(tableArr[j]+"."+muliel.getColCode()));
								converTableArr[j]=ConvertString.convertStringByCombin(tableArr[j]);
							}
							// 将字段的一些信息放入model解析,然后取得model
							model=new VelocityGetTemplateMulitData(mulilist,tableprimlist,tableprimpklist).getModel(element,packName);
							// 系统简码
							model.setSysCode(lastSysCode);
							//模块关联表(多表)，放入list集合中
							model.setTableListName(Arrays.asList(converTableArr));
							//设置mapper映射文件，查询的字段以表加字段作为别名
							model.setMapperSelectField(CoderService.getMultiMapperSelectField(tableArr));
							//对模块代码进行处理
							String cmoduCode=ConvertString.convertFirstCharUpper(moduCode.toLowerCase());
							List<Object[]> list=new ArrayList<Object[]>();
							//多表模型， 获得velocity生成文件所需要的三个参数（模板变量值，模板，路径）,放在list集合中
							list=VelocityGetPutMapMulitParameter.getMap(cmoduCode,vuePath,javaPath,converTableArr,model);
							Model[] modelArr = (Model[])list.get(0);
							String[] templateArr = (String[])list.get(1);
							String[] pathArr = (String[])list.get(2);
							for(int h=0;h<list.get(0).length;h++){
								vcx.put("models", modelArr[h]);
								VelocityCoder.velocity(vcx, templateArr[h], pathArr[h]);
							}
							//存放实体类内容
							String classstr=null;
							//多表模型会生成多个 实体类文件，需做特殊处理
							for(int j = 0; j < converTableArr.length + 1; j++){
								if (j < converTableArr.length) {
									//根据传入的模块关联表名、主键，生成实体类中的内容(属性和get/set方法)
									classstr=CoderService.getMultiClassStrBytable(tableArr[j],primlist.get(j));
									model.setConverTableName(converTableArr[j]);
									model.setTableName(tableArr[j]);
									model.setModelClassStr(classstr);
									vcx.put("models", model);
									VelocityCoder.velocity(vcx, "com/sky/app/coder/templates/b/b-model.java.vm", javaPath + "/model/" + cmoduCode+ converTableArr[j] + ".java");
								} else {
									//根据传入的模块关联表、模块数据库表主键，生成实体类中的内容(属性和get/set方法)
									classstr=CoderService.getMultiClassStr(tableArr,primlist);
									model.setModelClassStr(classstr);
									model.setConverTableName("");
									vcx.put("models", model);
									VelocityCoder.velocity(vcx, "com/sky/app/coder/templates/b/b-model.java.vm", javaPath + "/model/" + cmoduCode + ".java");
								}
							}
						}else if(modName.equals("单表模型")){
							// 根据模块代码从页面元素表中取出该模块全部的字段信息，并关联字段定义表，获得字段在数据库的类型
							List<Element> list = CoderService.getTagInfo("com.sky.app.core.CoderMapper.findBpForList",moduCode);
							Element el = CoderService.getElement("com.sky.app.core.CoderMapper.findBpForOne", moduCode);
							//根据传入的模块关联表、模块数据库表主键，生成实体类中的内容(属性和get/set方法)
							String str=CoderService.getClassStr(el.getRelTable(),el.getColCode());
							// 将数据库取出来的值放入model解析,然后取得model
							model = new VelocityGetTemplateData().getModel(list, el,str,packName,lastSysCode);
							// 获得velocity生成文件所需要的两个参数（模板，路径）,放在map中，key(包路径+模板名称)->value(文件路径+文件名)
							Map<String, String> cmap=VelocityGetPutMapParameter.getMap(moduCode,vuePath,javaPath);
							vcx.put("models", model);
							for (String key : cmap.keySet()) {
								// 根据传入的数据、模板、路径生成相应的文件
								VelocityCoder.velocity(vcx, key, cmap.get(key));
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
