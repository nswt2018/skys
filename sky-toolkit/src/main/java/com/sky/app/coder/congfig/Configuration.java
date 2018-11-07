/**
 * Project Name:qyk-util-base
 * File Name:Configuration.java
 * Package Name:org.qyk.base.util
 * Date:2017年2月16日下午5:49:13
 * Copyright (c) 2017, Thinkive(http://www.thinkive.com/) All Rights Reserved.
 *
*/

package com.sky.app.coder.congfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sky.app.coder.congfig.XMLHelper;

/**
 * ClassName:Configuration <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年2月16日 下午5:49:13 <br/>
 * 
 * @author qiyongkang
 * @version
 * @since JDK 1.6
 * @see
 */
public class Configuration {

	private static Logger logger = LogManager.getLogger(Configuration.class);
	private static Map<String, String> items = new HashMap<String, String>();

	private static String CONFIG_FILE_PATH = "src\\main\\resouces\\config\\coderConfig.xml";

	/**
	 * 读入配置文件
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map<String, String> loadConfig(String type) {
		try {
			Document document = XMLHelper.getDocument(Configuration.class, CONFIG_FILE_PATH);
			if (document != null) {
				Element systemElement = document.getRootElement();
				List catList = systemElement.elements("category");
				for (Iterator catIter = catList.iterator(); catIter.hasNext();) {
					Element catElement = (Element) catIter.next();
					String catName = catElement.attributeValue("name");
					if (catName.isEmpty()) {
						continue;
					}

					List itemList = catElement.elements("item");
					for (Iterator itemIter = itemList.iterator(); itemIter.hasNext();) {
						Element itemElement = (Element) itemIter.next();
						String itemName = itemElement.attributeValue("name");
						String value = itemElement.attributeValue("value");
						if (!itemName.isEmpty()) {
							items.put(catName + "." + itemName, value);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("读入配置文件出错", ex);
		} finally {
		}
		if ("a".equals(type)) {
			// 将模板名称和文件路径以键值对的形式放入map中
			Map<String, String> cmap = new HashMap<String, String>();
			if ("true".equals(items.get("vue.flag"))) {
				cmap.put(items.get("vue.template"), items.get("vue.path"));
			}
			if ("true".equals(items.get("js.flag"))) {
				cmap.put(items.get("js.template"), items.get("js.path"));
			}
			if ("true".equals(items.get("controller.flag"))) {
				cmap.put(items.get("controller.template"), items.get("controller.path"));
			}
			if ("true".equals(items.get("service.flag"))) {
				cmap.put(items.get("service.template"), items.get("service.path"));
			}
			if ("true".equals(items.get("serviceImpl.flag"))) {
				cmap.put(items.get("serviceImpl.template"), items.get("serviceImpl.path"));
			}
			if ("true".equals(items.get("mapperXml.flag"))) {
				cmap.put(items.get("mapperXml.template"), items.get("mapperXml.path"));
			}
			if ("true".equals(items.get("dao.flag"))) {
				cmap.put(items.get("dao.template"), items.get("dao.path"));
			}
			return cmap;
		} else {
			return items;
		}

	}
}
