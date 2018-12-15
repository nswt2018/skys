package com.sky.app.coder.helper;

import java.util.HashMap;
import java.util.Map;

import com.sky.app.coder.model.DatePicker;
import com.sky.app.coder.model.FormItem;
import com.sky.app.coder.model.Input;
import com.sky.app.coder.model.InputNumber;
import com.sky.app.coder.model.Model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ParseJsonString {
	/**
	 * 将这种JSON字符串[{"input":{"value":"paraValue"}}]，转为map,并将数据转到model实体类中
	 * @param tagInfo 字段的标签信息
	 * @return 返回model对象
	 */
	public Model parseJsonTagInfo(String tagInfo) {
		// JSONObject
		JSONArray jsonArray = JSONArray.fromObject(tagInfo);
		// 获得jsonArray的第一个元素
		Object o = jsonArray.get(0);
		JSONObject jsonObject = JSONObject.fromObject(o);
		Map map = new HashMap();
		// 参数必须小写
		map.put("input", Input.class);
		map.put("formitem", FormItem.class);
		map.put("datepicker", DatePicker.class);
		map.put("inputnumber", InputNumber.class);
		// 使用了toBean方法，需要三个参数
		Model model = (Model) JSONObject.toBean(jsonObject, Model.class, map);
		return model;
	}
}
