package com.sky.app.coder.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.app.coder.helper.ConvertString;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Systems;
import com.sky.app.coder.service.ICoderService;

@Service("CoderService")
public class CoderServiceImpl implements ICoderService {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlsession;
	@javax.annotation.Resource
	private SqlSessionFactory sqlSessionFactory;
	// 数据库的列名称
	private String[] colnames; // 列名数组
	// 列名类型数组
	private String[] colTypes;
	// 存放加上表名的列名数组  ->对应实体类的属性名
	private String[] tablecolnames;
	// 用于放 加上别名的字段名称
	private String[] aliscolnames;
	
	// 用于存放列名数组重复元素的下标
	private static List<Integer> lists = new ArrayList<Integer>();

	@Override
	public List<Systems> getSystems(String sqlId, String sysKey) {
		return sqlsession.selectList(sqlId, sysKey);
	}

	@Override
	public Systems getSystemsOne(String sqlId, String upperSys) {
		return sqlsession.selectOne(sqlId, upperSys);
	}

	@Override
	public List<Element> getTagInfo(String sqlId, String moduCode) {
		return sqlsession.selectList(sqlId, moduCode);
	}

	@Override
	public Element getElement(String sqlId, String moduCode) {
		return sqlsession.selectOne(sqlId, moduCode);
	}

	@Override
	public Element getModuleOne(String sqlId, String moduCode) {
		return sqlsession.selectOne(sqlId, moduCode);
	}

	@Override
	public List<Element> getMultiTagInfo(String sqlId, String moduCode) {
		return sqlsession.selectList(sqlId, moduCode);
	}

	@Override
	public Element getMultiFieldOne(String sqlId, String tabCode) {
		return sqlsession.selectOne(sqlId, tabCode);
	}

	// 单表模型
	@Override
	public String getClassStr(String tablename, String tablepri) {
		// 输出的类字符串
		// 模块数据库表主键
		String convertTablePri = ConvertString.convertSomeCharUpper(tablepri.toLowerCase());
		StringBuffer str = new StringBuffer("");
		// 获取表类型和表名的字段名
		try {
			Connection conn = sqlSessionFactory.openSession().getConnection();
			String sql = "select * from " + tablename;
			PreparedStatement statement = conn.prepareStatement(sql);
			// 获取数据库的元数据
			ResultSetMetaData metadata = statement.getMetaData();
			// 数据库的字段个数
			int len = metadata.getColumnCount();
			// 字段名称
			colnames = new String[len + 1];
			// 字段类型 --->已经转化为java中的类名称了
			colTypes = new String[len + 1];
			for (int i = 1; i <= len; i++) {
				// System.out.println(metadata.getColumnName(i)+":"+metadata.getColumnTypeName(i)+":"+sqlType2JavaType(metadata.getColumnTypeName(i).toLowerCase())+":"+metadata.getColumnDisplaySize(i));
				// metadata.getColumnDisplaySize(i);
				colnames[i] = ConvertString.convertSomeCharUpper(metadata.getColumnName(i).toLowerCase()); // 获取字段名称
				System.out.println(colnames[i]);
				if (colnames[i].equals(convertTablePri)) {
					colTypes[i] = "String"; // 如果是主键,则类型全为字符串
				} else {
					colTypes[i] = sqlType2JavaType(metadata.getColumnTypeName(i)); // 获取字段类型
				}
				System.out.println(metadata.getColumnTypeName(i));
				System.out.println(colTypes[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 校验
		if (null == colnames && null == colTypes)
			return null;
		// 拼接属性
		for (int index = 1; index < colnames.length; index++) {
			if (colnames[index].equals(convertTablePri)) {
				str.append("    @Id\r\n" + "    @GeneratedValue(generator=\"UUID\")\n"
						+ getAttrbuteString(colnames[index], colTypes[index]));
			} else {
				str.append(getAttrbuteString(colnames[index], colTypes[index]));
			}

		}
		// 拼接get，Set方法
		for (int index = 1; index < colnames.length; index++) {
			str.append(getGetMethodString(colnames[index], colTypes[index]));
			str.append(getSetMethodString(colnames[index], colTypes[index]));
		}
		return str.toString();
	}

	// 多表模型，根据表名和主键字段生成实体类内容
	public String getMultiClassStrBytable(String tablename, String tablepri) {
		// 输出的类字符串
		StringBuffer str = new StringBuffer("");
		// 获取表类型和表名的字段名
		try {
			Connection conn = sqlSessionFactory.openSession().getConnection();
			String sql = "select * from " + tablename;
			PreparedStatement statement = conn.prepareStatement(sql);
			// 获取数据库的元数据
			ResultSetMetaData metadata = statement.getMetaData();
			// 数据库的字段个数
			int len = metadata.getColumnCount();
			// 字段名称
			colnames = new String[len];
			// 加上表名字段名称->对应实体类的属性名
			tablecolnames = new String[len];
			// 字段类型 --->已经转化为java中的类名称了
			colTypes = new String[len];
			for (int i = 1; i <= len; i++) {
				// System.out.println(metadata.getColumnName(i)+":"+metadata.getColumnTypeName(i)+":"+sqlType2JavaType(metadata.getColumnTypeName(i).toLowerCase())+":"+metadata.getColumnDisplaySize(i));
				// metadata.getColumnDisplaySize(i);
				colnames[i - 1] = metadata.getColumnName(i);
				tablecolnames[i - 1] = ConvertString.convertSomeCharUpperReplace(tablename+"."+metadata.getColumnName(i));
				if (colnames[i - 1].equals(tablepri)) {
					colTypes[i - 1] = "String"; // 如果是主键,则类型全为字符串
				} else {
					colTypes[i - 1] = sqlType2JavaType(metadata.getColumnTypeName(i)); // 获取字段类型
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 校验
		if (null == colnames && null == colTypes)
			return null;
		// 拼接属性
		for (int index = 0; index < colnames.length; index++) {
			if (colnames[index].equals(tablepri)) {
				str.append("    @Id\r\n" + "    @GeneratedValue(generator=\"UUID\")\n" + "    @Column(name=\""
						+ colnames[index] + "\")\r\n" + getAttrbuteString(tablecolnames[index], colTypes[index]));
			} else {
				str.append("    @Column(name=\"" + colnames[index] + "\")\r\n"
						+ getAttrbuteString(tablecolnames[index], colTypes[index]));
			}

		}
		// 拼接get，Set方法
		for (int index = 0; index < colnames.length; index++) {
			str.append(getGetMethodString(tablecolnames[index], colTypes[index]));
			str.append(getSetMethodString(tablecolnames[index], colTypes[index]));
		}
		return str.toString();
	}

	// 多表模型,通过表名数据生成实体类内容
	@Override
	public String getMultiClassStr(String[] tablenames) {
		// 用于存放多个表列名数组
		List<String[]> listnamearr = new ArrayList<String[]>();
		// 用于存放多个表列名(加上表名组合的)数组
		List<String[]> listtablenamearr = new ArrayList<String[]>();
		// 用于存放多个表类型数组
		List<String[]> listtypearr = new ArrayList<String[]>();
		// 输出的类字符串
		StringBuffer str = new StringBuffer("");
		for (String tablename : tablenames) {
			try {
				Connection conn = sqlSessionFactory.openSession().getConnection();
				String sql = "select * from " + tablename;
				PreparedStatement statement = conn.prepareStatement(sql);
				// 获取数据库的元数据
				ResultSetMetaData metadata = statement.getMetaData();
				// 数据库的字段个数
				int len = metadata.getColumnCount();
				// 加上表名的字段名称->对应实体类的属性名
				tablecolnames = new String[len];
				// 字段类型 --->已经转化为java中的类名称了
				colTypes = new String[len];
				for (int i = 1; i <= len; i++) {
					tablecolnames[i-1]=ConvertString.convertSomeCharUpperReplace(tablename+"."+metadata.getColumnName(i));//获取加上表名字段名称
					colTypes[i - 1] = sqlType2JavaType(metadata.getColumnTypeName(i)); // 获取字段类型
				}
				listnamearr.add(colnames);
				listtablenamearr.add(tablecolnames);
				listtypearr.add(colTypes);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//合并
		if(listtablenamearr.size()>0){
			tablecolnames = concatAll(listtablenamearr);
		}
		// 合并多个表列类型数组
		if (listtypearr.size() > 0) {
			colTypes = concatAll(listtypearr);
		}
		// 校验
		if (null == tablecolnames && null == colTypes)
			return null;
		// 拼接属性
		for (int index = 0; index < tablecolnames.length; index++) {
			str.append(getAttrbuteString(tablecolnames[index], colTypes[index]));
		}
		// 拼接get，Set方法
		for (int index = 0; index < tablecolnames.length; index++) {
			str.append(getGetMethodString(tablecolnames[index], colTypes[index]));
			str.append(getSetMethodString(tablecolnames[index], colTypes[index]));
		}
		return str.toString();
	}

	// 多表模型，获得映射文件mapper中select后面的每个表的字段
	public String getMultiMapperSelectField(String[] tablenames) {
		// 用于存放多个表列名数组
		List<String[]> listnamearr = new ArrayList<String[]>();
		// 用于存放多个表加上别名的列名数组
		List<String[]> listalisnamearr = new ArrayList<String[]>();
		StringBuffer str = new StringBuffer("");
		for (int j = 0; j < tablenames.length; j++) {
			try {
				Connection conn = sqlSessionFactory.openSession().getConnection();
				String sql = "select * from " + tablenames[j];
				PreparedStatement statement = conn.prepareStatement(sql);
				// 获取数据库的元数据
				ResultSetMetaData metadata = statement.getMetaData();
				// 数据库的字段个数
				int len = metadata.getColumnCount();
				// 加上表名的字段名称
				tablecolnames = new String[len];
				// 加上表名的字段且有别名->对应实体类
				aliscolnames = new String[len];
				for (int i = 1; i <= len; i++) {
					tablecolnames[i - 1] = tablenames[j] + "." + metadata.getColumnName(i); // 获取表名加上字段组合的字段
					aliscolnames[i - 1] = ConvertString.convertSomeCharUpperReplace(tablenames[j] + "." + metadata.getColumnName(i));
				}
				listnamearr.add(tablecolnames);
				listalisnamearr.add(aliscolnames);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 合并多个表列名数组,并获得重复元素的下标
		if (listnamearr.size() > 0) {
			tablecolnames=concatAll(listnamearr);
		}
		if(listalisnamearr.size()>0){
			aliscolnames=concatAll(listalisnamearr);
		}
		// 校验
		if (null == tablecolnames)
			return null;
		// 拼接带有别名的查询字段
		for (int index = 0; index < tablecolnames.length; index++) {
			if (index < tablecolnames.length - 1) {
				str.append("\r" + (tablecolnames[index]) +"  as  "+aliscolnames[index]+ ",");
			} else {
				str.append("\r" + (tablecolnames[index])+"  as "+aliscolnames[index]);
			}
		}
		return str.toString();

	}

	/*
	 * mysql的字段类型转化为java的类型
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit") || sqlType.equalsIgnoreCase("boolean")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("float") || sqlType.equalsIgnoreCase("double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("blob")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Timestamp";
		}

		return null;
	}

	/*
	 * 获取字段字符串
	 */
	public StringBuffer getAttrbuteString(String name, String type) {
		if (!check(name, type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		}
		;
		String format = String.format("    private %s %s;\n\r", new String[] { type, name });
		return new StringBuffer(format);
	}

	/*
	 * 校验name和type是否合法
	 */
	public boolean check(String name, String type) {
		if ("".equals(name) || name == null || name.trim().length() == 0) {
			return false;
		}
		if ("".equals(type) || type == null || type.trim().length() == 0) {
			return false;
		}
		return true;

	}

	/*
	 * 获取get方法字符串
	 */
	private StringBuffer getGetMethodString(String name, String type) {
		if (!check(name, type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		}
		;
		String Methodname = "get" + GetTuoFeng(name);
		String format = String.format("    public %s %s(){\n\r", new Object[] { type, Methodname });
		format += String.format("        return this.%s;\r\n", new Object[] { name });
		format += "    }\r\n";
		return new StringBuffer(format);
	}

	// 将名称首字符大写
	private String GetTuoFeng(String name) {
		name = name.trim();
		if (name.length() > 1) {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		} else {
			name = name.toUpperCase();
		}
		return name;
	}

	/*
	 * 获取字段的get方法字符串
	 */
	private Object getSetMethodString(String name, String type) {
		if (!check(name, type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		}
		;
		String Methodname = "set" + GetTuoFeng(name);
		String format = String.format("    public void %s(%s %s){\n\r", new Object[] { Methodname, type, name });
		format += String.format("        this.%s = %s;\r\n", new Object[] { name, name });
		format += "    }\r\n";
		return new StringBuffer(format);
	}

	/*
	 * 将多个数组合并，按传入参数的顺序合并
	 */
	public static String[] concatAll(List<String[]> listarr) {
		// 存放最终合并后的数组
		String[] result = null;
		if (listarr.size() > 0) {
			int totalLength = 0;
			for (String[] array : listarr) {
				totalLength += array.length;
			}
			result = new String[totalLength];
			int offset = 0;
			for (String[] array : listarr) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
			return result;
		} else {
			return result;
		}

	}

	/*
	 * 传入可能有重复的数组，返回无重复的数组，并将重复的元素下标放入list集合中
	 */
	// 需要传入一个Object数组，然后返回去重后的数组
	public static String[] ifRepeat(String[] arr) {
		// 用来记录去除重复之后的数组长度和给临时数组作为下标索引
		int t = 0;
		// 临时数组
		String[] tempArr = new String[arr.length];
		// 遍历原数组
		for (int i = 0; i < arr.length; i++) {
			// 声明一个标记，并每次重置
			boolean isTrue = true;
			// 内层循环将原数组的元素逐个对比
			for (int j = i + 1; j < arr.length; j++) {
				// 如果发现有重复元素，改变标记状态并结束当次内层循环
				if (arr[i].endsWith(arr[j])) {
					isTrue = false;
					// 将重复的元素下标赋给list集合
					lists.add(i);
					break;
				}
			}
			// 判断标记是否被改变，如果没被改变就是没有重复元素
			if (isTrue) {
				// 没有元素就将原数组的元素赋给临时数组
				tempArr[t] = arr[i];
				// 走到这里证明当前元素没有重复，那么记录自增
				t++;
			}
		}
		// 声明需要返回的数组，这个才是去重后的数组
		String[] newArr = new String[t];
		// 用arraycopy方法将刚才去重的数组拷贝到新数组并返回
		System.arraycopy(tempArr, 0, newArr, 0, t);
		return newArr;
	}

	/*
	 * 将合并后的列类型数组，按指定的下标删除
	 */
	public static String[] replaceArrayByIndex(String[] str, List<Integer> lists) {
		List<String> inpal = Arrays.asList(str);
		List<String> outPa = new ArrayList<String>();
		for (int i = 0; i < inpal.size(); i++) {
			if (lists.contains(i)) {

			} else {
				outPa.add(inpal.get(i));
			}

		}
		return outPa.toArray(new String[outPa.size()]);
	}

}
