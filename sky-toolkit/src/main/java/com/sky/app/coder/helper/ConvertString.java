package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConvertString {
	// 判断字符串中是否有大写字母
	public static boolean isAcronym(String word) {
		if(word==null){
			return false;
		}
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			// 如果有大写字符则返回真
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		// 如果没有大写字符则返回真
		return false;
	}

	// 将字符串中的大写字符(一个或多个大写字母都可以)转为"_小写字母"
	public static String convertCharlower(String str) {
		if(str==null){
			return null;
		}
		// 定义字符数组存放大写字符，长度为字符串长度
		char[] a = new char[str.length()];
		char temporary = 0;
		// 存放大写字符数组的下标
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			temporary = str.charAt(i);
			if (Character.isUpperCase(temporary)) {
				a[index] = temporary;
				index++;
			}
		}
		// 将大写字符转为“_小写”
		for (int i = 0; i < index; i++) {
			String b = String.valueOf(a[i]);
			str = str.replace(b, "_" + b.toLowerCase());
		}

		return str;
	}

	// 将字符串中一个或多个“_”去掉，并将之后的一个字符转为大写
	public static String convertSomeCharUpper(String str) {
		if(str==null){
			return null;
		}
		String temporary;
		// 存放大写字符数组的下标
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			temporary = String.valueOf(str.charAt(i));
			if ("_".equals(temporary)) {
				str = str.substring(0, i) + str.substring(i + 1, i + 2).toUpperCase() + str.substring(i + 2);
			}
		}
		return str;
	}
	//判断字符串首字符是否大写，如果不是则大写，并返回
	public static String convertFirstCharUpper(String str){
		if(str==null){
			return null;
		}
		if (!Character.isUpperCase(str.charAt(0))) {
			str=str.substring(0, 1).toUpperCase()+str.substring(1);
		}
		return str;
	}
	//替换字符串中‘\’为‘/’
	public static String replace(String str){
		if(str==null){
			return null;
		}
		str=str.replaceAll("\\\\", "/");
		return str;
	}
	//替换字符串中的特殊字符如‘@’，‘:’
	public static String replaceSomeChar(String str){
		String temporary;
		// 存放特殊字符数组的下标
		for (int i = 0; i < str.length(); i++) {
			temporary = String.valueOf(str.charAt(i));
			if ("@".equals(temporary)||"*".equals(temporary)) {
				str = str.substring(0, i) +  str.substring(i + 1);
			}
		}
		return str;
	}
	//将java字符串每两个分为一组
	public static List<String>  groupString(String str){
		List<String> list = new ArrayList<String>();
		for(;str.length()>2;){
		list.add(str.substring(str.length()-2));
		str = str.substring(0,str.length()-2);
		}
		if(str.length()>0){
		list.add(str);
		}
		return list;
	}
	//获取数值字符串的2位,4位,8位....依次往后
	public static List<String>  subString(String str){
		List<String> list = new ArrayList<String>();
		int j=1;
		for(;j<str.length()/2;j++){
			list.add(str.substring(0,2*j));
		}
		return list;
		/*List<Map<String, String>> list1 = null;
		Map<String,Map> mapAll = null;
		String str1 = "";
		for( int i=0; i<list1.size(); i++) {
			Map<String, String> map = list1.get(i);
			if(!map.get("pack_name").equals("") ) {
				String uper = map.get("upper_sys");				
				while(uper!=null) {
					str1 += uper+"/";
					uper = (String)mapAll.get(uper).get("upper_sys");
				}
			}
		}*/
		
	}
	public static void main(String[] args) {
		System.out.println("010201".substring(0,2));
	}
}
