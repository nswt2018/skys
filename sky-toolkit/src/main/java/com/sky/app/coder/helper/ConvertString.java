package com.sky.app.coder.helper;

public class ConvertString {
	// 判断字符串中是否有大写字母
	public static boolean isAcronym(String word) {
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

	public static void main(String[] args) {
		System.out.println(convertSomeCharUpper("test_key_one"));
	}
}
